package com.days.moment.board.mapper;

import org.apache.ibatis.annotations.Param;
import com.days.moment.board.domain.Board;
import com.days.moment.board.domain.BoardAttach;
import com.days.moment.common.dto.PageRequestDTO;

import java.util.List;

public interface BoardMapper {

    void insert(Board board);

    List<Board> getList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);

    Board select(Long bno);

    int delete(Long bno);

    int update(Board board);

    int updateReplyCnt(@Param("bno") Long bno,@Param("num") int num);

    int insertAttach(BoardAttach boardAttach);

    int deleteAttach(Long bno);
}
