package com.days.moment.common.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.days.moment.common.dto.UploadResponseDTO;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Log4j2
public class UploadController {

    @GetMapping("/sample/upload")
    public void uploadGET() {
    }

    @ResponseBody
    @PostMapping("/removeFile")
    public ResponseEntity<String> removeFile(@RequestBody Map<String, String> data) throws Exception{

        //파일 탐색
        File file = new File("C:\\upload" + File.separator + data.get("fileName"));

        boolean checkImage = Files.probeContentType(file.toPath()).startsWith("image");

        file.delete();

        if(checkImage){
            File thumbnail = new File(file.getParent(), "s_"+file.getName());
            log.info(thumbnail);
            thumbnail.delete();
        }
        return ResponseEntity.ok().body("deleted");
    }

    @GetMapping("/downFile")
    public ResponseEntity<byte[]> download(@RequestParam("file") String fileName) throws Exception{

        //파일 탐색
        File file = new File("C:\\upload" + File.separator + fileName);

        //파일네임을 _까지 잘라줌.
        String originalFileName = fileName.substring(fileName.indexOf("_") + 1);

        HttpHeaders httpHeaders = new HttpHeaders();
        //알려지지않은 파일타입에 사용하는 기본값
        httpHeaders.add("Content-Type", "application/octet-stream");
        httpHeaders.add("Content-Disposition", "attachment; filename="
                + new String(originalFileName.getBytes(StandardCharsets.UTF_8), "ISO-8859-1"));
        byte[] data = FileCopyUtils.copyToByteArray(file);

        return ResponseEntity.ok().headers(httpHeaders).body(data);

    }

    @GetMapping("/viewFile")
    @ResponseBody
    public ResponseEntity<byte[]> viewFile(@RequestParam("file") String fileName) throws Exception{
        //파라미터 받을때는 file로 받는데 실제로 출력될때는 fileName으로 출력

        // c:\\upload\\2021/09/08/파일명.확장자
        File file = new File("C:\\upload" + File.separator + fileName);

        log.info(file);

        ResponseEntity<byte[]> result = null;

        //파일을 잃어와서 바이트 배열로 변환해줌, 예외처리 걸림. 던져줌
        byte[] data = FileCopyUtils.copyToByteArray(file);
        //mime type에 맞춰서 보내주어야함.
        //mime type식별
        String mimeType = Files.probeContentType(file.toPath());

        log.info("mime type: " + mimeType);

        //ok = 200응답
        result = ResponseEntity.ok().header("Content-Type", mimeType).body(data);

        return result;
    }

    @ResponseBody
    @PostMapping("/upload")
    public List<UploadResponseDTO> uploadPost(MultipartFile[] uploadFiles){ //upload.jsp의 append에 주었던 name을 그대로 사용해야 업로드 성공.
        log.info("------------------------------");

        if(uploadFiles != null && uploadFiles.length > 0){

            List<UploadResponseDTO> uploadedList = new ArrayList<>();

            for (MultipartFile multipartFile: uploadFiles) {
                try {
                    uploadedList.add(uploadProcess(multipartFile));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return uploadedList;
        }//if end
        return null;
    }

    private UploadResponseDTO uploadProcess(MultipartFile multipartFile) throws Exception {

        String uploadPath = "C:\\upload";

        String folderName = makeFolder(uploadPath); // 2021-09-07

        log.info(multipartFile.getContentType());
        log.info(multipartFile.getOriginalFilename());
        log.info(multipartFile.getSize());
        log.info("----------------------------end");

        String fileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String originalFileName = fileName;

        fileName = uuid +"_"+fileName;

        File savedFile = new File(uploadPath+File.separator+folderName, fileName);//원본파일

        FileCopyUtils.copy(multipartFile.getBytes(), savedFile);

        //Thumbnail 처리
        String mimeType = multipartFile.getContentType();
        boolean checkImage = mimeType.startsWith("image");
        if(checkImage){
            File thumbnailFile = new File(uploadPath+File.separator+folderName, "S_"+fileName);
            Thumbnailator.createThumbnail(savedFile, thumbnailFile,100,100);
        }

        return UploadResponseDTO.builder()
                .uuid(uuid)
                .uploadPath(folderName.replace(File.separator, "/"))
                .fileName(originalFileName)
                .image(checkImage)
                .build();

    }

    private String makeFolder(String uploadPath) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = simpleDateFormat.format(date); // 2021-09-07
        String folderName = str.replace("-", File.separator);//window는 \ ,  Mac은 /
        File uploadFolder = new File(uploadPath, folderName);
        if(uploadFolder.exists() == false){ //업로드 폴더 경로가 없으면
            uploadFolder.mkdirs(); // 폴더 생성
        }
        return folderName;
    }
}
