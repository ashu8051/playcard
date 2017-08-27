package com.card.app.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CardAspect {

	private static final Logger log = Logger.getLogger(CardAspect.class);
	public void myInformation()
	{
		
	}
	
	@Pointcut(value="@annotation(logExecutionTime)")
	protected void logAnnotatedMethods(LogExecutionTime  logExecutionTime) {    }
	
		@Pointcut("execution(public String com.card.app.controller.UserController.*(..))")
	protected void logBefore(){
		
		
	}
		
		//@Before("execution(public String com.card.app.controller.UserController.*(..))")
		@Before("logBefore()")
		public void logExecutionTime() throws Throwable {
		   
		 
		    if(log.isDebugEnabled())
		    {
		    	log.info("Before Advice is Called ");
		    }
		 
		   
		    
		}
		
	/*	@Before("args(msg)")
		protected void logBeforedata(String msg){
			
			 if(log.isDebugEnabled())
			    {
			    	log.info("Before Advice is Called args "+msg);
			    }
		}*/
		
		@Pointcut("execution(public String com.card.app.controller.UserController.*(..))")
		protected void logAfetr(){
			
			 if(log.isDebugEnabled())
			    {
			    	log.info("After Advice is Called ");
			    }
		}
		@After("logAfetr()")
		public void logAfterData()
		{
			 if(log.isDebugEnabled())
			    {
			    	log.info("logAfterData Advice is Called ");
			    }
		}
		
		@Pointcut("execution(public String com.card.app.controller.UserController.*(..))")
		protected void logAfetrReturn(){
			
			 if(log.isDebugEnabled())
			    {
			    	log.info("logAfetrReturn Advice is Called ");
			    }
		}
		
		@AfterReturning(pointcut="logAfetrReturn() ",returning="msg")
		public void logAfterDataReturn(String msg)
		{
			 if(log.isDebugEnabled())
			    {
			    	log.info("logAfetrReturn Advice is Called "+msg);
			    }
		}
		
		@Pointcut("within(com.card.app.model.User)")
		protected void logAfetrThrough(){
			
			 if(log.isDebugEnabled())
			    {
			    	log.info("logAfetrThrough Advice is Called ");
			    }
		}

	
	@Around("logAnnotatedMethods(logExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint,LogExecutionTime  logExecutionTime) throws Throwable {
	    long start = System.currentTimeMillis();
	 
	    Object proceed = joinPoint.proceed();
	 
	    long executionTime = System.currentTimeMillis() - start;
	 
	    if(log.isDebugEnabled())
	    {
	    	log.info("Class Name :: "+logExecutionTime.name() 
	    +"Level :: "+logExecutionTime.level()
	    +"Message :: "+logExecutionTime.message()
	    + " executed in " + executionTime + "ms");
	    }
	    System.out.println("Class Name :: "+logExecutionTime.name() 
	    +"Level :: "+logExecutionTime.level()
	    +"Message :: "+logExecutionTime.message()
	    + " executed in " + executionTime + "ms");
	    return proceed;
	}
	
	@Before("logAnnotatedMethods(logExecutionTime)")
	public void logExecutionTime(LogExecutionTime  logExecutionTime) throws Throwable {
	    long start = System.currentTimeMillis();
	 
	 //   Object proceed = joinPoint.proceed();
	 
	    long executionTime = System.currentTimeMillis() - start;
	 
	    if(log.isDebugEnabled())
	    {
	    	log.info(" @Before Class Name :: "+logExecutionTime.name() 
	    +"Level :: "+logExecutionTime.level()
	    +"Message :: "+logExecutionTime.message()
	    + " executed in " + executionTime + "ms");
	    }
	    System.out.println("@Before Class Name :: "+logExecutionTime.name() 
	    +"Level :: "+logExecutionTime.level()
	    +"Message :: "+logExecutionTime.message()
	    + " executed in " + executionTime + "ms");
	    
	}
	
	/*	@Before("argNames(msg) && args(param)")
	protected void logBeforedata(String msg,String param){
		
		 if(log.isDebugEnabled())
		    {
		    	log.info("Before Advice is Called "+msg+" param "+param);
		    }
	}
	
	
	
	@After("argNames(msg) ")
	public void logAfterDataArgs(String msg)
	{
		 if(log.isDebugEnabled())
		    {
		    	log.info("Before Advice is Called "+msg);
		    }
	}
	
	*/
	
	@AfterThrowing(pointcut="logAfetrThrough()",throwing="msg")
	public void logAfterDataThrowing(String msg)
	{
		 if(log.isDebugEnabled())
		    {
		    	log.info("logAfterDataThrowing Advice is Called "+msg);
		    }
	}
	
	
}
