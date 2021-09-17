package com.days.moment.common.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageMaker {

    private int start, end, page, size, total;
    private boolean prev, next;

    public PageMaker(int page, int size, int total) {

        this.page = page;
        this.size = size;
        this.total = total;

        //마지막페이지 계산
        end = (int)(Math.ceil(page / 10.0) * 10);
        start = end - 9;

        prev = start > 1;
        next = (total / (double)size) > end;

        end =  end * size > total ? (int)(total/(double)size) : end; //3항 연산자
    }

}
