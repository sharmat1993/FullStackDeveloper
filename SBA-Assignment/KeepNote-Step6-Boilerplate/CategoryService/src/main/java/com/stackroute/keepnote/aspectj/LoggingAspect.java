package com.stackroute.keepnote.aspectj;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */

@Aspect
@Component
public class LoggingAspect {
	/*
	 * Write loggers for each of the methods of User controller, any particular
	 * method will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */

	@Before("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void logBefore() {
		System.out.println("logBefore executed");
	}

	@After("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void logAfter() {
		System.out.println("logAfter executed");
	}

	@AfterReturning("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void logAfterReturning() {
		System.out.println("logAfterReturning executed");
	}

	@AfterThrowing("execution(* com.stackroute.keepnote.controller.*.*(..))")
	public void logAfterThrowing() {
		System.out.println("logAfterReturning executed");
	}
}