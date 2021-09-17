package com.days.moment.board.domain;

import com.days.moment.board.dto.BoardDTO;
import com.days.moment.common.dto.UploadResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    private Long bno;
    private String title, content, writer;
    private LocalDateTime regDate, modDate;
    private int replyCnt;

    @Builder.Default
    private List<BoardAttach> attachList = new ArrayList<>();

    public BoardDTO getDTO() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(bno)
                .title(title)
                .content(content)
                .writer(writer)
                .regDate(regDate)
                .modDate(modDate)
                .build();

        //BoardAttach -> UploadResponseDTO -> List
        List<UploadResponseDTO> uploadResponseDTOList =  attachList.stream().map(boardAttach -> {
            UploadResponseDTO uploadResponseDTO = UploadResponseDTO.builder()
                    .uuid(boardAttach.getUuid())
                    .fileName(boardAttach.getFileName())
                    .uploadPath(boardAttach.getPath())
                    .image(boardAttach.isImage())
                    .build();
            return uploadResponseDTO;
        }).collect(Collectors.toList());

        boardDTO.setFiles(uploadResponseDTOList);


        return boardDTO;
    }

    public void setBno(Long bno) {
        this.bno = bno;
    }

    public void addAttach(BoardAttach boardAttach) {

        attachList.add(boardAttach);

    }
}

