package com.coder.log.aspect;

import com.coder.log.config.LogProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
//@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private LogProperties logProperties;

    public LogAspect(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    @Pointcut("@annotation(com.coder.log.annotation.LogAnnotation)")
    public void logAnnotationPointcutLog() {
    }

    @Around(value = "logAnnotationPointcutLog()", argNames = "proceedingJoinPoint")
    public Object doAfter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info(logProperties.getPrefix(), Arrays.toString(proceedingJoinPoint.getArgs()));
        Object proceed = proceedingJoinPoint.proceed();
        logger.info(logProperties.getSuffix(), proceed.toString());
        return proceed;
    }
}