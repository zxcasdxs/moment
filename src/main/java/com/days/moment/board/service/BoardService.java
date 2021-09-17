package com.days.moment.board.service;

import org.springframework.transaction.annotation.Transactional;
import com.days.moment.board.dto.BoardDTO;
import com.days.moment.common.dto.PageRequestDTO;
import com.days.moment.common.dto.PageResponseDTO;

@Transactional
public interface BoardService {

    Long register(BoardDTO boardDTO);

    PageResponseDTO<BoardDTO> getDTOList(PageRequestDTO pageRequestDTO);

    BoardDTO read(Long bno);

    Boolean remove(Long bno);

    Boolean modify(BoardDTO boardDTO);
}
