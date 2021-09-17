package com.days.moment.board.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.days.moment.board.config.BoardRootConfig;
import com.days.moment.board.domain.Reply;
import com.days.moment.common.config.RootConfig;

import java.util.stream.IntStream;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {BoardRootConfig.class, RootConfig.class})
public class ReplyMapperTests {

    @Autowired(required = false)//스프링이 로딩시에 Autowired 가능 유무 확인 하지 않음.
    ReplyMapper replyMapper;

    @Test
    public void insertDummies() {

        Long[] arr = {100L, 99L, 98L, 97L, 96L};

        IntStream.rangeClosed(1, 100).forEach(num ->{//intStream 특정 숫자 사이의 루프

            long bno = arr[(int)((Math.random() * 1000)) % 5];

            Reply reply = Reply.builder()
                    .bno(bno)
                    .reply("댓글 ....." + num)
                    .replyer("user" + (num % 10))
                    .build();

            replyMapper.insert(reply);
        });
    }

    @Test
    public void testList(){
        Long bno = 100L;

        replyMapper.getListWithBoard(bno).forEach(reply -> log.info(reply));
    }
}
