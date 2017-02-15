package com.sygf.util.security;

import java.io.IOException;
import java.util.Properties;

import com.sygf.util.ResourceReader;
import com.sygf.util.security.Security;
import com.sygf.util.security.SecurityImpl;

public abstract class Security{
	
	private static Security instance = null;
	private static Properties prop = null;
	private static final String defaultClass = "com.sygf.util.security.SecurityImpl";
	
	/**
	 * 获得唯一的一个加解密的类
	 * 
	 * @return Security
	 */
	public static final Security getInstance() {
		if (prop == null) {
			prop = ResourceReader.getResourcesFromPackage(
					"/com/sygf/util/security/config.properties",
					Security.class);
		}
		if (instance == null) {
			String implClass = prop.getProperty("security.implclass");
			try {
				if (implClass == null) {
					instance = (Security) Class.forName(defaultClass)
							.newInstance();
				} else {
					instance = (Security) Class.forName(implClass)
							.newInstance();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new SecurityImpl();
			}
		}
		return instance;
	}
	
	/**
	 * 将给定的字符解密
	 * 
	 * @param original
	 * @return String
	 * @throws IOException
	 */
	public abstract String decodeString(String original);
	
	/**
	 * 判断当前字符串是否加密过 true：加密过了的 false：没有加密的，原始字符串
	 * 
	 * @param source
	 * @return
	 */
	public abstract boolean isEncoded(String source);
	
	/**
	 * 将给定的字符加密�?
	 * 
	 * @param original
	 * @return String
	 * @throws IOException
	 */
	public abstract String encodeString(String original);

}
