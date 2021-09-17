package com.days.moment.board.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.days.moment.board.config.BoardRootConfig;
import com.days.moment.common.config.RootConfig;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {BoardRootConfig.class, RootConfig.class})
public class TimeServiceTests {

    @Autowired
    TimeService timeService;

    @Test
    public void testAdd() {

        String str = "2021-09-07 11:04:24  INFO [com.days.moment.board.aop.TimeLogAspect] TimeLogAspect...................\n" +
                "2021-09-07 11:04:24  INFO [com.days.moment.board.aop.TimeLogAspect] TimeLogAspect...................\n";

        timeService.addString(str);
    }
}
