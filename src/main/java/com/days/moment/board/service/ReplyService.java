package com.days.moment.board.service;

import com.days.moment.board.domain.Reply;
import com.days.moment.board.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {

    int addReply(ReplyDTO replyDTO);

    List<ReplyDTO> getRepliesWithBno(Long bno);

    int remove(Long rno);

    int modify(ReplyDTO replyDTO);

    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .bno(replyDTO.getBno())
                .reply(replyDTO.getReply())
                .replyer(replyDTO.getReplyer())
                .replyDate(replyDTO.getReplyDate())
                .modDate(replyDTO.getModDate())
                .build();

        return reply;
    }

    default ReplyDTO entityToDTO(Reply reply) {
        //인터페이스는 몸체를 가지는것이 불가능하나, default를 사용하면 몸체를 가질 수 있게됨.
        //java8버전 이후 지원되는 기능
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .bno(reply.getBno())
                .reply(reply.getReply())
                .replyer(reply.getReplyer())
                .replyDate(reply.getReplyDate())
                .modDate(reply.getModDate())
                .build();

        return replyDTO;
    }
}
