package com.sygf.util;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sygf.erp.ParamResources;
import com.sygf.erp.util.SysMaint;

public class Log {
	
	private static String logfile = "log4j"; // 配置文件的目
	
	/**
	 * 使用log4j记录日志
	 */
	public static Logger logger = null;

	private static final String SEP = "\\|";

	static {
		logger = getInstance();
	}

	public static Logger getInstance() {
		if (logger == null) {
			Properties prop = getProperties(logfile);
			PropertyConfigurator.configure(prop);
			logger = Logger.getLogger(Log.class);
		}
		return logger;
	}
	
	/**
	 * 将指定的文件名的文件里的数据转换为Properties对象
	 * 
	 * @param filename
	 * @return Properties
	 */
	public static Properties getProperties(String filename) {
		ResourceBundle rb = ResourceBundle.getBundle(filename);
		Properties prop = new Properties();
		try {
			Enumeration propEnum = rb.getKeys();
			while (propEnum.hasMoreElements()) {
				String key = (String) propEnum.nextElement();
				String value = (String) rb.getString(key);
				prop.setProperty(key, value); // 封装Properties对象
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * 信息级别
	 * 
	 * @param msg
	 */
	public static void info(Object msg) {
		logger = getInstance();
		logger.info(msg);
		logToDB(msg, ParamResources.LOG_INFO);
	}
	
	/**
	 * 错误级别
	 * 
	 * @param msg
	 */
	public static void error(Object msg) {
		logger.error(msg);
		logToDB(msg, ParamResources.LOG_ERROR);
	}
	
	/**
	 * 记录数据库日
	 * 
	 * @param msg
	 */
	public static void logToDB(Object msg, String log_level) {
		if (SysMaint.getProperty("LOG_DB").equals("true")
				&& msg.getClass().getName().equals("java.lang.String")) { // 是否记录数据库日
			String logMsg = (String) msg;
			if (logMsg.split(SEP).length == 6) { // 如果信息满足条件，记录数据库日志
				String[] messages = logMsg.split(SEP);
				String user_name = messages[0];
				String ip = messages[1];
				String url = messages[2];
				String content = messages[3];
				String oper_module = messages[4];
				String oper_type = messages[5];
			}
		}
	}

}
