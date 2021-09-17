package com.days.moment.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;

    private String type;

//    if(type == null || type.trim().length == 0) {
//
//    }
    private String keyword;

    public int getSkip() {
        return (page - 1) * size;
    }

    public String[] getArr(){
        return type ==null ? new String[]{} : type.split("");
    }
}
