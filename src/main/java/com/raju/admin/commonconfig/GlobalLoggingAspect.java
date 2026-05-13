package com.raju.admin.commonconfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Order(1)
@Component
public class GlobalLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(GlobalLoggingAspect.class);

    @Around("execution(* com.raju.admin..*(..)) && !within(com.raju.admin.security..*)")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        logger.debug("➡️ Entering method: {}", methodName);
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            logger.debug("✅ Exiting method: {} ({} ms)", methodName, duration);
            return result;
        } catch (Exception e) {
            logger.error("❌ Exception in {}: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }
}
