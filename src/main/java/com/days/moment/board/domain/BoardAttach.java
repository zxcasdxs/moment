package com.days.moment.board.domain;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardAttach {

    private String uuid;
    private Long bno;
    private String fileName;
    private String path;
    private boolean image;


    public void setBno(Long bno) {
        this.bno = bno;
    }
}
