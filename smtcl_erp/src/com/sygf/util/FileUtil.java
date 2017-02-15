package com.sygf.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import com.sygf.util.FileUtil;

public class FileUtil {
	
	/**
	 * 私有构方,防止类的实例
	 */
	private FileUtil() {}
	
	/**
	 * 获取服务器端的classes目录路径
	 * 例如：C:/sygf/tomcat6.0.13/webapps/sygfweb/WEB-INF/classes
	 * @return ~/classes/
	 */
	public static String getClassPath() {
		FileUtil j = new FileUtil();
		String t = j.getRealPath();
		System.out.println(t);
		if(System.getProperty("file.separator").equals("/")){
			t = t.replaceAll("file:", ""); // linux,unix
		}else{
			t = t.replaceAll("file:/", ""); // windows
		}
		t = t.replaceAll("wsjar:", ""); // websphere wsjar: has to at jar:
		t = t.replaceAll("jar:", ""); // tomcat,jboss,resin,wasce,apusic
		t = t.replaceAll("zip:", ""); // weblogic
		t = t.replaceAll("/./", "/"); // weblogic
		t = t.split("/classes/")[0] + "/classes/";
		try {
			t = java.net.URLDecoder.decode(t, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("配置文件目录编码错误" + e.getMessage());
		}
		System.out.println("返回的路径为" + t);
		return t;
	}
	
	/**
	 * 获取服务器端真实路径
	 * @return
	 */
	private String getRealPath() {
		String strClassName = getClass().getName();
		
		String strPackageName = "";
		if (getClass().getPackage() != null){
			
			strPackageName = getClass().getPackage().getName();
			System.out.println("strPackageName="+strPackageName);
		}
		String strClassFileName = "";
		if (!"".equals(strPackageName)){
			strClassFileName = strClassName.substring(
					strPackageName.length() + 1, strClassName.length());
		}
		else{
			strClassFileName = strClassName;
		}
		
		URL url = getClass().getResource(strClassFileName + ".class");
		String strURL = url.toString();
		
		strURL = strURL.replaceAll("%20", "   ");
		
		System.out.println("从服务器端获取的绝对真实路径为：" + strURL);
		return strURL;
	}
	
	/**
	 * 在给定的目录中，如果有超过给定日期给定天数的文件，删除? 如果删除了文件，返回真?
	 * 
	 * @param dir
	 * @param days
	 * @param date
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean deleteFileDays(File dir, int days, Date date) throws IOException {
		boolean flag = false;
		long terminalDate = date.getTime()
				- ((long) days * 24 * 60 * 60 * 1000);
		Collection coll = FileUtils.listFiles(dir, null, false);
		Iterator iter = coll.iterator();
		while (iter.hasNext()) {
			File one = (File) iter.next();
			if (FileUtils.isFileOlder(one, terminalDate)) { 
				flag = true;
				FileUtils.forceDelete(one);
			}
		}
		return flag;
	}

}

