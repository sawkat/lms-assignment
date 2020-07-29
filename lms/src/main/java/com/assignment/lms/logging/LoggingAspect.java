package com.assignment.lms.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * This class will log the API call details like Which API called, Execution time, if any Exception then Exception details
 * 
 * @author 703177676
 *
 */
@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	@Around("execution(* com.assignment.lms.controller..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        	MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
          
        	//Get intercepted method details
        	String className = methodSignature.getDeclaringType().getSimpleName();
        	String methodName = methodSignature.getName();
        	
        	LOGGER.info("Called - " + className + "." + methodName);
        
        	final StopWatch stopWatch = new StopWatch();
          
        	//Measure method execution time
        	stopWatch.start();
        	Object result = proceedingJoinPoint.proceed();
        	stopWatch.stop();
  
        	//Log method execution time
        	LOGGER.info("Execution time of - " + className + "." + methodName + " "
                            + ":: " + stopWatch.getTotalTimeMillis() + " ms");
  
        	return result;
        }
	
	@AfterThrowing(pointcut = "execution(* com.assignment.lms.controller..*(..)))", throwing = "exp")
    public void logAfterThrowingAllMethods(JoinPoint joinPoint, Exception exp) throws Throwable {
        	MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
          
        	//Get intercepted method details
        	String className = methodSignature.getDeclaringType().getSimpleName();
        	String methodName = methodSignature.getName();
        	
        	LOGGER.info("Got Exception - " + className + "." + methodName +" Exception Details - "+exp.getMessage());
        }

}
