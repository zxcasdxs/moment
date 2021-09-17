package com.days.moment.board.controller;

import com.days.moment.board.dto.BoardDTO;
import com.days.moment.board.service.BoardService;
import com.days.moment.board.service.TimeService;
import com.days.moment.common.dto.PageMaker;
import com.days.moment.common.dto.PageRequestDTO;
import com.days.moment.common.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/*")
@Log4j2
@RequiredArgsConstructor

public class BoardController {

    private final TimeService timeService;//autowired 대신, 실제 개발할때는 private final을 autowired대신에 씀

    private final BoardService boardService;

    @GetMapping("/time")
    public void getTime(Model model){
        log.info("================controller getTime==========");//no Mapping에러, component Scan을 해줘야함.


        model.addAttribute("time", timeService.getNow());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public void registerGet() {

    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {

        log.info("boardDTOM " + boardDTO);

        Long bno = boardService.register(boardDTO);

        log.info("=================c         registerPost================");
        log.info(bno);
        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("c         getList.........................." + pageRequestDTO);

        PageResponseDTO<BoardDTO> responseDTO = boardService.getDTOList(pageRequestDTO);


        model.addAttribute("dtoList", responseDTO.getDtoList());

        int total = responseDTO.getCount();
        int page = pageRequestDTO.getPage();
        int size = pageRequestDTO.getSize();

        model.addAttribute("pageMaker", new PageMaker(page, size, total));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = {"/read", "/modify", "/read2"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {
        log.info("c       read " + bno);
        log.info("c       read " + pageRequestDTO);
        model.addAttribute("boardDTO", boardService.read(bno));
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes){
        log.info("c               remove : " + bno);

        if(boardService.remove(bno)){
            log.info("remove success");
            log.info("remove success");
            redirectAttributes.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";
    }

    @PreAuthorize("principal.mid == #boardDTO.writer")
    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO,PageRequestDTO pageRequestDTO , RedirectAttributes redirectAttributes){
        log.info("c               modify : " + boardDTO);
        if(boardDTO.getFiles().size() > 0) {
            boardDTO.getFiles().forEach(dto -> log.info(dto));
        }

        if(boardService.modify(boardDTO)){
            log.info("modify success");
            redirectAttributes.addFlashAttribute("result", "modified");
        }

        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size", pageRequestDTO.getSize());

        if(pageRequestDTO.getType() != null) {
            redirectAttributes.addAttribute("type", pageRequestDTO.getType());
            redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
        }

        return "redirect:/board/read";
    }


}
