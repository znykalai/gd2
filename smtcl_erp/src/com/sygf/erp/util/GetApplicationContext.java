package com.sygf.erp.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetApplicationContext {

	/**
	 * 数据库连接池
	 */
	 private GetApplicationContext(){}
	 private static ApplicationContext context = null;
	 public static ApplicationContext getContext(HttpServletRequest request){
		 if(context==null){
			 context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
		 }
		 return context;
	 }
}