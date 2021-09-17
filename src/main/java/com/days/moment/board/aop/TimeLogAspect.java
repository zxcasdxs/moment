package com.days.moment.board.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component
public class TimeLogAspect {

    {
        log.info("TimeLogAspect...................");
        log.info("TimeLogAspect...................");
        log.info("TimeLogAspect...................");
        log.info("TimeLogAspect...................");
        log.info("TimeLogAspect...................");
    }

    @Before("execution(* org.zerock.jex01.board.service.*.*(..))")//pointcut -- 어떤것이랑 기능을 합칠것이냐?
    public void logBefore(JoinPoint joinPoint){
        log.info("logBefore.....................");

        log.info(joinPoint.getTarget());//getTarget() 실제 객체를 찍어줌
        log.info(Arrays.toString(joinPoint.getArgs()));
        log.info("logBefore.....................END");
    }

    @AfterReturning("execution(* org.zerock.jex01.board.service.*.*(..))")
    public void logAfter(){
        log.info("logAfter.....................");
    }
}