package com.days.moment.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.days.moment.board.dto.ReplyDTO;
import com.days.moment.board.mapper.BoardMapper;
import com.days.moment.board.mapper.ReplyMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService{

    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;
    //생성자 주입이 가장 안전, setter보다 안전함.
    //하지만 코드가 짧은것으로 유용


    @Override
    public int addReply(ReplyDTO replyDTO) {
        int count = replyMapper.insert(dtoToEntity(replyDTO));
        boardMapper.updateReplyCnt(replyDTO.getBno(), 1);

        return count;
    }

    @Override
    public List<ReplyDTO> getRepliesWithBno(Long bno) {
        return replyMapper.getListWithBoard(bno).stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    }

    @Override
    public int remove(Long rno) {
        return replyMapper.delete(rno);
    }

    @Override
    public int modify(ReplyDTO replyDTO) {
        return replyMapper.update(dtoToEntity(replyDTO));
    }
}
