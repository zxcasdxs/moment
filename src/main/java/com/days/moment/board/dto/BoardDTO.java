package com.days.moment.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.days.moment.board.domain.Board;
import com.days.moment.board.domain.BoardAttach;
import com.days.moment.common.dto.UploadResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int replyCnt;

    @Builder.Default//초기화 필요하여 사용
    private List<UploadResponseDTO> files = new ArrayList<>();

    public Board getDomain() {

        Board board = Board.builder()
                .bno(bno)
                .title(title)
                .content(content)
                .writer(writer)
                .regDate(regDate)
                .modDate(modDate)
                .build();

        files.forEach(uploadResponseDTO -> {
            BoardAttach boardAttach = BoardAttach.builder()
                    .fileName(uploadResponseDTO.getFileName())
                    .uuid(uploadResponseDTO.getUuid())
                    .image(uploadResponseDTO.isImage())
                    .path(uploadResponseDTO.getUploadPath())
                    .build();

            board.addAttach(boardAttach);
        });

        return board;
    }
}
