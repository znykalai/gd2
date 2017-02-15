package com.sygf.erp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * 实现文件、文件夹的相关操
 * @author Aeolus
 * @date 200922222:38:47
 *	
 */


public class OperateFile {
	
	/**
	 * 
	 */
	
	public boolean copyFile(String strSouFile, String strAimFile){
		
		String strFileName = "";
		
		try{
			//获取源文
			File souFile = new File(strSouFile);
			//获取目标文件
			File aimFile = new File(strAimFile);
			
			if(aimFile.exists()){
				//目标文件存在
				aimFile.mkdirs();
			}
			
			
			
			
		}catch(Exception e){
			System.out.println("Exception in OperateFile.copyFile");
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	/**
	 * 删除指定文件夹下的所有文
	 * @param strPath
	 * @return
	 */
	
	
	public boolean delAllFile(String strPath) {
		
		boolean flag = false;
		
		File file = new File(strPath);
		
		if (!file.exists()) {
			return flag;
		}else if (!file.isDirectory()){
			return flag;
		}else{
			String[] strTempArray = file.list();
			
			File tempFile = null;
			
			for (int i = 0; i < strTempArray.length; i++) {
				
				if (strPath.endsWith(File.separator)) {
					tempFile = new File(strPath + strTempArray[i]);
				}else{
					tempFile = new File(strPath + File.separator + strTempArray[i]);
				}
				if (tempFile.isFile()) {
					tempFile.delete();
				}
				if (tempFile.isDirectory()) {
					this.delAllFile(strPath + "/" + strTempArray[i]);		// 先删除文件夹里面的文
					this.delFolder(strPath + "/" + strTempArray[i]);		// 再删除空文件
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 删除文件
	 * @param strfolderPath "c:\\1\\"
	 * @return
	 */
	
	
	public boolean delFolder(String strfolderPath) {
		try {
			delAllFile(strfolderPath); 		// 删除完里面所有内			
			java.io.File myFilePath = new java.io.File(strfolderPath);
			myFilePath.delete(); 			// 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	/**
	 * 删除文件
	 * @param strFilePath
	 * @return
	 */
	
	public boolean delFile(String strFilePath){
		
		try{
			java.io.File myFilePath = new java.io.File(strFilePath);
			
			if(myFilePath.exists()){
				myFilePath.delete();
				return true;
			}else{
				return false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	/**
	 * 
	 * 在指定路径下，创建指定名称的文件
	 * @param strFolderPath ：要创建文件夹的路径，必须以"\\"结尾 "c:\\windows\\"
	 * @param strFolderName ：要创建文件夹的名称 "1"
	 * @return true ：创建成 ；false ：创建失
	 * 
	 */
	
	public boolean createFolder(String strFolderPath, String strFolderName){

		try{
			String path = strFolderPath + strFolderName + "\\";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();			//如果该文件夹不存在，则创建文件夹
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			Object file = null;
		}

		return true;
	}
	
	/**
	 * 创建文件夹，根据参数提供的所要求的文件夹路径
	 * @param strFolderPath
	 * @return
	 */
	
	public boolean createFolderByPath(String strFolderPath){
		//System.out.println("createFolderByPath");
		
		try{
			File file = new File(strFolderPath);
			if(!file.exists()){
				file.mkdirs();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Object file = null;
		}
		return true;
	}
	
	
	/**
	 * 判定指定的路径是文件还是文件
	 * @param strPath	指定的路
	 * @return	true : 文件 ; false : 文件
	 */
	
	public boolean judgeFileOrFolder(String strPath){
		boolean bResult = false;
		
		File file = new File(strPath);            
		if(file.isFile()) {// 是否为档
			//为档
			System.out.println(strPath + " is File");
		}else{
			//为文件夹
			bResult = true;
			System.out.println(strPath + "is Folder");
		}
		
		return bResult;
	}
	
	/**
	 * 获取指定路径下文件的名称
	 * @param strPath ：指定文件夹的路
	 * @return ：文件名组合的字符串 "|1.xml|RoleInforAction.java|RoleInforDAO.java"
	 */
	
	
	public String readFileInFolder(String strPath){
		String strResult = new String();
		
		File file = new File(strPath);            

		if(file.isFile() || !file.exists()){
			//是文件或者文件夹不存
			strResult = "null";
		}else{
			File[] files = file.listFiles();
			ArrayList<File> fileList = new ArrayList<File>(); 
			
			//读取文件夹内的文件名
			for(int i = 0; i < files.length; i++) {// 先列出目
				if(files[i].isDirectory()) { //是否为目 
					// 取得路径     ȡ��·����            
					//System.out.println("[" +files[i].getPath() + "]");                     
				}else {
					// 档案先存入fileList，待会再列出
					fileList.add(files[i]);
				}
			}
			
			
			//获取文件夹内的文件名
			for(File f: fileList) {
				//System.out.println(f.toString());
				String strTemp = f.toString();
				
				int iBegin = strTemp.lastIndexOf("\\") + 1;
				int iEnd = strTemp.length();
				String strFileName = strTemp.substring(iBegin, iEnd);
				
				
				
				//System.out.println(strFileName);
				strResult = strResult + strFileName + "@";
				//System.out.println("############strResult==="+strResult);
			}
			
		}
		
		return strResult;
	}
	
	

	/**
	 * 在指定路径下创建指定文件名称的文
	 * @param strPath	指定路径 注意：务必以"\\"结尾
	 * @param strFileName	文件名称
	 * @return	true  创建成功  false  创建失败
	 */
	
	public boolean createFile(String strPath , String strFileName){
		
		try{
			StringBuilder path = new StringBuilder();
			path.append(strPath);				 	//保存生成文件的目
			
			//System.out.println(path.toString());
			
			File file = new File(path.toString());
			
			
			//判断此文件夹是否存在
			if(file.exists() && file.isDirectory()){
				//System.out.println("Yes");
				//添加文件名称
				path.append(strFileName);
				//创建文件
				File cFile = new File(path.toString());
				
				cFile.createNewFile();
				
				return true;
				
			}else{
				
				//System.out.println("No");
			}
			
			
		}catch(Exception e){
			System.out.println("Exception in OperateFile.createFile");
			e.printStackTrace();
		}
		
		
		
		return false;
		
	}
	
	/**
	 * 用于为指定的文件写入内容
	 * @param strPath	文件路径
	 * @param strContent	要添加的内容
	 * @return	true  添加成功  false ：添加失
	 */
	public boolean writeFile(String strPath, String _strContent){
		//System.out.println("_strContent_strContent_strContent======="+_strContent);
		StringBuilder strContent = new StringBuilder();
		strContent.append("<%@ page language='java' import='java.util.*' pageEncoding='gb2312'%>");
		 strContent.append("<%String path = request.getContextPath();%>");
		    strContent.append("<html><head>");
		    strContent.append("</head><body>");
		strContent.append(_strContent);
		strContent.append("</body></html>");
		File file = new File(strPath);
		
		try{
			
			if(file.isFile()){
				//文件存在
				PrintStream printStream = new PrintStream(new FileOutputStream(file));
				
				printStream.println(strContent.toString());
				
			}else{
				//文件不存
				//System.out.println(strPath + "is not File");
			}
		}catch(Exception e){
			System.out.println("Exception in OperateFile.writeFile");
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public String creatForm(String strVariable){
		
		StringBuilder strContent = new StringBuilder();
		
		strContent.append("");
		
		
		
		return null;
	}
	
	
	public static void main(String[] args){
		
		//System.out.println("OperateFile.main");
		
		OperateFile file = new OperateFile();
		
		//file.judgeFileOrFolder("c:\\1.xls");
		//System.out.println(file.readFileInFolder("E:\\Temp\\11\\1"));
		
		String strContent = "<html><head></head>";
		//strContent = strContent + "<body><table border=\"1\"><tr><td>" + strName + </td></tr></table></body></html>";
		
		
		//file.createFile("c:\\", "3.html");
		
		file.writeFile("c:\\1.jsp", strContent);
		System.out.println("do");
	}
	
	
	
}
