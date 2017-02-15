package com.sygf.erp;

public class ParamResources {
	
	/**
	 * 下面是session中存放�?�的key
	 */
	public static final String USER_NAME = "user_name";	
	public static final String USER_LABEL = "user_label";	
	public static final String LOGIN_TIME = "LoginTime";
	public static final String REMOTE_IP = "RemoteIP";
	public static final String ROLE_ID = "role_id";
	
	public static final String HTTP_LOGIN = "2";
	
	/**
	 * 操作类型
	 */
	public static final String[] OPERS = {
											"登录",
											"�?�?",
											"增加",
											"修改",
											"删除"
										  };	
	/**
	 * 登录操作
	 */
	public static final String OPER_LOGIN = OPERS[0];	
	/**
	 * �?出操�?
	 */
	public static final String OPER_LOGOUT = OPERS[1];	
	/**
	 * 增加操作
	 */
	public static final String OPER_ADD = OPERS[2];	
	/**
	 * 修改操作
	 */
	public static final String OPER_MODIFY = OPERS[3];	
	/**
	 * 删除操作
	 */
	public static final String OPER_DELETE = OPERS[4];
	
	/**
	 * 系统模块字典
	 */
	public static final String[] MODULES = {
												"登录模块",
												"新闻公告管理",
												"文档中心管理",
												"图片中心管理",
												"工作室管�?",
												"工作任务管理",
												"权限管理"
											  };	
	/**
	 * 登录模块
	 */
	public static final String MODULE_LOGIN = MODULES[0];	
	/**
	 * 新闻公告管理模块
	 */
	public static final String MODULE_NEWS = MODULES[1];	
	/**
	 * 文档中心管理模块
	 */
	public static final String MODULE_DOCUMENT = MODULES[2];	
	/**
	 * 图片中心管理模块
	 */
	public static final String MODULE_PICTURE = MODULES[3];	
	/**
	 * 工作室管理模�?
	 */
	public static final String MODULE_WORKROOM = MODULES[4];	
	/**
	 * 工作任务管理模块
	 */
	public static final String MODULE_ASSIGNMENT  = MODULES[5];	
	/**
	 * 权限管理模块
	 */
	public static final String MODULE_POPEDOM = MODULES[6];	
	
	/**
	 * 日志级别
	 */
	public static final String[] LOG_LEVELS = {
													"DEBUG",
													"INFO",
													"ERROR",
													"FATAL",
													"WARN"
												 };
	/**
	 * 调试级别
	 */
	public static final String LOG_DEBUG = LOG_LEVELS[0];	
	/**
	 * 信息级别
	 */
	public static final String LOG_INFO = LOG_LEVELS[1];	
	/**
	 * 错误级别
	 */
	public static final String LOG_ERROR = LOG_LEVELS[2];	
	/**
	 * 严重级别
	 */
	public static final String LOG_FATAL = LOG_LEVELS[3];	
	/**
	 * 经过级别
	 */
	public static final String LOG_WARN = LOG_LEVELS[4];
}
