package com.example.WebFlux_Crud_SpringSecurity.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.WebFlux_Crud_SpringSecurity.service.*.*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.info("Method called: {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        long endTime=System.currentTimeMillis()-startTime;
        logger.info("Method executed: {} in {} ms", joinPoint.getSignature().getName(), endTime);
        return result;
    }
}
