package com.days.moment.board.controller;

import com.days.moment.board.dto.ReplyDTO;
import com.days.moment.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("")
    public String[] doA() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new String[]{"AAA", "BBB", "CCC"};
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public int add(@RequestBody ReplyDTO replyDTO) {

        log.info("========================");
        log.info(replyDTO);



        return replyService.addReply(replyDTO);
    }

    @DeleteMapping("/{rno}")
    public String remove(@PathVariable(name="rno") Long rno) {//@PathVariable >> name의 파라미터를 받아온다.
        //Json으로 받을건지, 파라미터로만 반환 받을것인지 결정
    log.info("----------------------reply remove-----------------------");

    log.info("rno : " + rno);

    replyService.remove(rno);

    return "success";
    }//일관성이 중요함. ajax는 json으로 모두 쓴다, 파라미터 1개 는 그냥 파라미터로 사용한다 등....

    @PutMapping("/{rno}")
    public String modify(@PathVariable(name="rno") Long rno,
                         @RequestBody ReplyDTO replyDTO){ // @RequestBody Json타입 데이터로 변환

        log.info("-------------------reply modify----------------------" + rno);
        log.info(replyDTO);

        replyService.modify(replyDTO);

        return "success";
    }

    @GetMapping("/list/{bno}") // replies/list/bno 의 주소가 됨.
    public List<ReplyDTO> getBoardReplies(@PathVariable(name = "bno") Long bno) {
        //서비스 계층 호출
        return replyService.getRepliesWithBno(bno);
    }

}