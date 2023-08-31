package com.rustemsarica.ATMProject.providers;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    
    @Before(value = "execution(* com.rustemsarica.ATMProject.business.services.*.*(..))")
    public void logAfterMethodCall(JoinPoint joinPoint){
        System.out.println("************");        
        logger.info("Called method :" + joinPoint.getSignature().getName());
        System.out.println("************");
    }

    @AfterReturning(value = "execution(* com.rustemsarica.ATMProject.business.services.*.*(..))", returning = "result")
    public void logAfterMethodCall(Object result){
        System.out.println("************");
        logger.info("After method call. Result: {}", result);
        System.out.println("************");
    }
    
}
