package com.sygf.erp.util;

import java.util.ResourceBundle;

public class SysMaint {
	
	private static String filename = "init";
	private static ResourceBundle resource = ResourceBundle.getBundle(filename);
	/** 是否要设置编码，默认为false */
	private static boolean encode = false;
	
	/**
	 * 获得指定key的value值?
	 * 
	 * @param key
	 * @return String
	 */
	public static String getProperty(String key) {
		String value = resource.getString(key);
		if (value == null) {
			value = "";
		} else {
			if (encode) {
				try {
					value = new String(value.getBytes("ISO8859_1"), "gb2312");
				} catch (Exception e) {
				}
			}
		}
		return value;
	}
	
	/**
	 * 获得左侧的字符串
	 * 
	 * @param source
	 * @param sep
	 */
	public static String left(String source, String sep){
		if(source == null)
			return null;
		return source.substring(0, source.indexOf(sep));
	}

}
