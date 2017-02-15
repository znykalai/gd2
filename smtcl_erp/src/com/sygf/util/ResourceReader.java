package com.sygf.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import com.sygf.util.FileUtil;
import com.sygf.util.security.Security;

public class ResourceReader {
	
	private static Security security = Security.getInstance();
	
	/**
	 * 将给定的资源文件转换成Properties类，提供应用使用，filepath中必须是"/"头的根路
	 * 系统将首先从压缩包中获取配置文件，如果压缩包中没有，那么接着从文件系统中查找
	 * 
	 * @param filepath
	 * @param c
	 * @return Properties
	 */
	public static Properties getResourcesFromPackage(String filepath, Class c) {
		boolean flag = true;
		if (filepath == null || filepath.equals("")) {
			return null;
		}
		Properties prop = new Properties();
		try {
			prop.load(c.getResourceAsStream(filepath));
		} catch (IOException eo) {
			flag = false;
			Log.info("类路径文件中的压缩包无法发现给定的配置文件！下面将从类路径中的文件系统中查找");
		} catch (Exception e) {
			flag = false;
			Log.info("类路径文件中的压缩包无法发现给定的配置文件！下面将从类路径中的文件系统中查找");
		}
		// 在包中无法找到配置文件，从CLASSPATH中寻
		if(!flag){
			try {
				Log.info("从类路径中获取配置信息！");
				String configFile = FileUtil.getClassPath() + filepath.substring(1, filepath.length());
				FileInputStream inStream = new FileInputStream(configFile);
//				FileInputStream inStream = new FileInputStream(filepath.substring(1, filepath.length()));
				prop.load(inStream);
				inStream.close();
			} catch (Exception ex) {
				Log.error("没有在类路径中找到给定的配置文件" + ex.getMessage());
			}
		}
		return prop;
	}

	/**
	 * 判断当前的Properties中的value值，是否有加密过的，如果有，将加密文字解密并重新封装成Properties类?
	 * 
	 * @param prop
	 * @return Properties
	 */
	public static Properties getDecodedResources(Properties prop) {
		Properties decodedProp = new Properties();
		if (prop == null) {
			return null;
		}
		Enumeration keys = prop.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement().toString();
			String value = prop.getProperty(key);
			if (security.isEncoded(value)) {
				value = security.decodeString(value);
			}
			decodedProp.setProperty(key, value);
		}
		return decodedProp;
	}
}
