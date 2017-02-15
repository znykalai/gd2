/**   
* 文件名：GetParam.java
*   
* 版本信息�?   v1.0
* 日期：Jun 1, 2011   
* Copyright 足下 Corporation 2011    
* 版权�?�?   
*   
*/
package com.sygf.erp.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**   
*    
* 项目名称：H3ERP   
* 类名称：GetParam   
* 类描述：获取前台传的有参
* 创建人：郭峰   
* 创建时间：Jun 1, 2011 3:10:02 PM   
* 修改人：郭峰   
* 修改时间：Jun 1, 2011 3:10:02 PM   
* 修改备注   
* 版本信息   
*    
*/
public class GetParam {

	/**   
	  
	* GetParamValue(获取前台传的有参) 
	* 创建人：郭峰   
	* 创建时间：Jun 1, 2011 2:48:17 PM  
	* 修改人：郭峰   
	* 修改时间：Jun 1, 2011 2:48:17 PM   
	* 修改备注      
	* @param  HttpServletRequest request
	* @return HashMap
	* @Exception 异常对象    
	* @Ver 1.0
	  
	*/
	public static HashMap GetParamValue(HttpServletRequest request) {
		try{
			HashMap map = new HashMap();
			Enumeration keys = request.getParameterNames();
			while(keys.hasMoreElements()){
				String keyTemp = (String)keys.nextElement();
				String temp = (request.getParameter(keyTemp) == null) ? "" : new String(request.getParameter(keyTemp).getBytes("iso-8859-1"),"utf-8");
				map.put(keyTemp, temp);
			}
			return map;
			
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;
		
	}

	/**   
	  
	* GetParamValue(获取前台传的有参数，可择转换钱和转换后的编码) 
	* 创建人：郭峰   
	* 创建时间：Jun 1, 2011 2:48:17 PM  
	* 修改人：郭峰   
	* 修改时间：Jun 1, 2011 2:48:17 PM   
	* 修改备注      
	* @param  HttpServletRequest request,String oldCode,String newCode
	* @return HashMap
	* @Exception 异常对象    
	* @Ver 1.0
	  
	*/
	public static HashMap GetParamValue(HttpServletRequest request,String oldCode,String newCode) {
		try{
			HashMap map = new HashMap();
			Enumeration keys = request.getParameterNames();
			while(keys.hasMoreElements()){
				String keyTemp = (String)keys.nextElement();
				String temp = (request.getParameter(keyTemp) == null) ? "" : new String(request.getParameter(keyTemp).getBytes(oldCode),newCode);
				map.put(keyTemp, temp);
			}
			return map;
			
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;
		
	}

	/**   
	  
	* createJSON(生成JSON字符) 
	* 创建人：郭峰   
	* 创建时间：Jun 1, 2011 2:48:17 PM  
	* 修改人：郭峰   
	* 修改时间：Jun 1, 2011 2:48:17 PM   
	* 修改备注      
	* @param  List list, String strPra, int index,int pageSize
	* @return String
	* @Exception 异常对象    
	* @Ver 1.0
	  
	*/
	public static String createJSON(List list, String strPra, int index,int pageSize){
		try{
			String[] strAray = strPra.split(",");
			
			String strJSON = "{totalProperty:";
			if(list == null || list.isEmpty()){
				strJSON = strJSON + "0,root:[]}";
			}else{
				strJSON = strJSON + list.size() + ",root:[";
				
				for(int i=index; i<list.size()&&i<pageSize+index ;i++){
					
					strJSON = strJSON + "{";
					
					HashMap map = (HashMap)list.get(i);
					
					for(int j = 0; j < strAray.length; j++){
						
						String strTemp = "";
						
						if(map.get(strAray[j]) == null){
		
						}else{
							strTemp = (String)map.get(strAray[j]);
						}				
						strJSON = strJSON + strAray[j].toString() + ":'" + exchange.toHtml(strTemp) + "'";
						if(j != strAray.length - 1){
							strJSON = strJSON + ",";
						}
					}
					
					strJSON = strJSON + "}";
					
					if(i != pageSize + index - 1 && i != list.size()-1){
						strJSON = strJSON + ",";
					}
				}
				
				strJSON = strJSON + "]}";
			}
			
			return strJSON;
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;	
	}
	/**   
	  
	* createJSON(生成JSON字符串，无分) 
	* 创建人：郭峰   
	* 创建时间：Jul 5, 2011 4:29:42 PM  
	* 修改人：郭峰   
	* 修改时间：Jul 5, 2011 4:29:42 PM   
	* 修改备注      
	* @param  List list, String strPra
	* @return String
	* @Exception 异常对象    
	* @Ver 1.0
	  
	*/
	public static String createJSON(List list, String strPra){
		try{
			String[] strAray = strPra.split(",");
			
			String strJSON = "{totalProperty:";
			if(list == null || list.isEmpty()){
				strJSON = strJSON + "0,root:[]}";
			}else{
				strJSON = strJSON + list.size() + ",root:[";
				
				for(int i=0; i<list.size();i++){
					
					strJSON = strJSON + "{";
					
					HashMap map = (HashMap)list.get(i);
					
					for(int j = 0; j < strAray.length; j++){
						
						String strTemp = "";
						
						if(map.get(strAray[j]) == null){
		
						}else{
							strTemp = (String)map.get(strAray[j]);
						}				
						strJSON = strJSON + strAray[j].toString() + ":'" + exchange.toHtml(strTemp) + "'";
						if(j != strAray.length - 1){
							strJSON = strJSON + ",";
						}
					}
					
					strJSON = strJSON + "}";
					
					if(i != list.size()-1){
						strJSON = strJSON + ",";
					}
				}
				
				strJSON = strJSON + "]}";
			}
			
			return strJSON;
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;	
	}
	
	/**   
	  
	* createJqueryJSON(读取jquery能够解析的json) 
	* 创建人：郭峰  
	* 创建时间：Jun 1, 2011 3:05:13 PM  
	* 修改人：郭峰  
	* 修改时间：Jun 1, 2011 3:05:13 PM   
	* 修改备注      
	* @param   list,strPra  
	* @return String    json   
	*/
	public static String createJqueryJSON(List list, String strPra){
		try{
			String[] strAray = strPra.split(",");
			
			String strJSON = "";
			if(list!=null&&list.size()>0){
				strJSON += "[";
				for(int i=0; i<list.size() ;i++){
					
					strJSON = strJSON + "{";
					
					HashMap map = (HashMap)list.get(i);
					
					for(int j = 0; j < strAray.length; j++){
						
						String strTemp = "";
						
						if(map.get(strAray[j]) == null){
		
						}else{
							strTemp = (String)map.get(strAray[j]);
						}				
						strJSON = strJSON + "\""+strAray[j].toString()+"\"" + ":\"" + exchange.toHtml(strTemp) + "\"";
						if(j != strAray.length - 1){
							strJSON = strJSON + ",";
						}
					}
					
					strJSON = strJSON + "}";
					if(i < list.size()-1){
						strJSON = strJSON + ",";
					}
				}
				
				strJSON = strJSON + "]";
			}
			
			return strJSON;
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;	
	}
	
	/**   
	  
	* createunrootJSON(生成个没有root的JSON) 
	* 创建人：郭峰   
	* 创建时间2012-6-19 下午12:52:23  
	* 修改人：郭峰   
	* 修改时间2012-6-19 下午12:52:23   
	* 修改备注         
	* @param   name  
	* @return String    DOM对象    
	* @Exception 异常对象    
	* @since  (版本)1.0   
	  
	*/
	public static String createunrootJSON(List list, String strPra){
		try{
			String[] strAray = strPra.split(",");
			
			String strJSON = "";
			if(list == null || list.isEmpty()){
				strJSON = strJSON + "[]}";
			}else{
				strJSON = strJSON + "[";
				
				for(int i=0; i<list.size();i++){
					
					strJSON = strJSON + "{";
					
					HashMap map = (HashMap)list.get(i);
					
					for(int j = 0; j < strAray.length; j++){
						
						String strTemp = "";
						
						if(map.get(strAray[j]) == null){
		
						}else{
							strTemp = (String)map.get(strAray[j]);
						}				
						strJSON = strJSON + strAray[j].toString() + ":'" + exchange.toHtml(strTemp) + "'";
						if(j != strAray.length - 1){
							strJSON = strJSON + ",";
						}
					}
					
					strJSON = strJSON + "}";
					
					if(i != list.size()-1){
						strJSON = strJSON + ",";
					}
				}
				
				strJSON = strJSON + "]";
			}
			
			return strJSON;
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;	
	}
	/**   
	  
	* out(向前台返回提交信) 
	* 创建人：郭峰   
	* 创建时间：Jun 1, 2011 2:48:17 PM  
	* 修改人：郭峰   
	* 修改时间：Jun 1, 2011 2:48:17 PM   
	* 修改备注      
	* @param  HttpServletResponse response,Boolean flag,String context
	* @return String
	* @Exception 异常对象    
	* @Ver 1.0
	  
	*/
	public static String out(HttpServletResponse response,boolean b,String context) {
		
		try{
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("{success:" + b + ", msg:\"" + context + "\"}");
			response.getWriter().close();
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;
		
	}

	/**   
	  
	* outJSON(向前台返回JSON) 
	* 创建人：郭峰   
	* 创建时间：Jun 1, 2011 2:48:17 PM  
	* 修改人：郭峰   
	* 修改时间：Jun 1, 2011 2:48:17 PM   
	* 修改备注      
	* @param  HttpServletResponse response,String json
	* @return String
	* @Exception 异常对象    
	* @Ver 1.0
	  
	*/
	public static String outJSON(HttpServletResponse response,String json) {
		
		try{
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(json); 
				response.getWriter().close();
		}catch(Exception e){
			System.out.println("Exception in GetParam.outJSON");
			e.printStackTrace();
		}
		return null;
		
	}
	
	//郭峰
	public static String createJSONwithJQuery(List list, String strPra, int index,int pageSize){
		try{
			String[] strAray = strPra.split(",");
			
			String strJSON = "{\"totalProperty\":";
			if(list == null || list.isEmpty()){
				strJSON = strJSON + "\"0\",\"root\":[]}";
			}else{
				strJSON = strJSON + "\""+list.size()+"\"" + ",\"root\":[";
				
				for(int i=index; i<list.size()&&i<pageSize+index ;i++){
					
					strJSON = strJSON + "{\"";
					
					HashMap map = (HashMap)list.get(i);
					
					for(int j = 0; j < strAray.length; j++){
						
						String strTemp = "";
						
						if(map.get(strAray[j]) == null){
		
						}else{
							strTemp = (String)map.get(strAray[j]);
						}				
						strJSON = strJSON + strAray[j].toString() + "\":\"" + exchange.toHtml(strTemp) + "\"";
						if(j != strAray.length - 1){
							strJSON = strJSON + ",\"";
						}
					}
					
					strJSON = strJSON + "}";
					
					if(i != pageSize + index - 1 && i != list.size()-1){
						strJSON = strJSON + ",";
					}
				}
				
				strJSON = strJSON + "]}";
			}
			
			return strJSON;
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;	
	}



	/**   
	  
	* createJSON(生成JSON字符) 
	* 创建人：郭峰   
	* 创建时间：Apr 23, 2013 3:54:00 PM  
	* 修改人：郭峰   
	* 修改时间：Apr 23, 2013 3:54:00 PM   
	* 修改备注         
	* @param   name  
	* @return String    DOM对象    
	* @Exception 异常对象    
	* @since  (版本)1.0   
	  
	*/
	public static String createJSON(List list, int index,int pageSize){
		try{
			Object [] strAray_obj;
			if(list != null && !list.isEmpty()){
				strAray_obj = ((HashMap)list.get(0)).keySet().toArray();
			}else{
				strAray_obj = new Object[0];
			}
			
			String strJSON = "{totalProperty:";
			if(list == null || list.isEmpty()){
				strJSON = strJSON + "0,root:[]}";
			}else{
				strJSON = strJSON + list.size() + ",root:[";
				
				for(int i=index; i<list.size()&&i<pageSize+index ;i++){
					
					strJSON = strJSON + "{";
					
					HashMap map = (HashMap)list.get(i);
					
					for(int j = 0; j < strAray_obj.length; j++){
						
						String strAray = strAray_obj[j].toString();
						
						String strTemp = "";
						
						if(map.get(strAray) == null){
		
						}else{
							strTemp = (String)map.get(strAray);
						}				
						strJSON = strJSON + strAray.toString() + ":'" + exchange.toHtml(strTemp) + "'";
						if(j != strAray_obj.length - 1){
							strJSON = strJSON + ",";
						}
					}
					
					strJSON = strJSON + "}";
					
					if(i != pageSize + index - 1 && i != list.size()-1){
						strJSON = strJSON + ",";
					}
				}
				
				strJSON = strJSON + "]}";
			}
			
			return strJSON;
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;	
	}
	

	/**   
	  
	* createJSON(生成JSON字符串，无分) 
	* 创建人：郭峰   
	* 创建时间：Apr 23, 2013 3:59:40 PM  
	* 修改人：郭峰   
	* 修改时间：Apr 23, 2013 3:59:40 PM   
	* 修改备注         
	* @param   name  
	* @return String    DOM对象    
	* @Exception 异常对象    
	* @since  (版本)1.0   
	  
	*/
	public static String createJSON(List list){
		try{
			Object [] strAray_obj;
			if(list != null && !list.isEmpty()){
				strAray_obj = ((HashMap)list.get(0)).keySet().toArray();
			}else{
				strAray_obj = new Object[0];
			}
			
			String strJSON = "{totalProperty:";
			if(list == null || list.isEmpty()){
				strJSON = strJSON + "0,root:[]}";
			}else{
				strJSON = strJSON + list.size() + ",root:[";
				
				for(int i=0; i<list.size();i++){
					
					strJSON = strJSON + "{";
					
					HashMap map = (HashMap)list.get(i);
					
					for(int j = 0; j < strAray_obj.length; j++){
						
						String strAray = strAray_obj[j].toString();
						
						String strTemp = "";
						
						if(map.get(strAray) == null){
		
						}else{
							strTemp = (String)map.get(strAray);
						}				
						strJSON = strJSON + strAray.toString() + ":'" + exchange.toHtml(strTemp) + "'";
						if(j != strAray_obj.length - 1){
							strJSON = strJSON + ",";
						}
					}
					
					strJSON = strJSON + "}";
					
					if(i != list.size()-1){
						strJSON = strJSON + ",";
					}
				}
				
				strJSON = strJSON + "]}";
			}
			
			return strJSON;
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;	
	}



	/**   
	  
	* createunrootJSON(生成没有root的JSON字符) 
	* 创建人：郭峰   
	* 创建时间：Apr 23, 2013 3:54:00 PM  
	* 修改人：郭峰   
	* 修改时间：Apr 23, 2013 3:54:00 PM   
	* 修改备注         
	* @param   name  
	* @return String    DOM对象    
	* @Exception 异常对象    
	* @since  (版本)1.0   
	  
	*/
	public static String createunrootJSON(List list, int index,int pageSize){
		try{
			Object [] strAray_obj;
			if(list != null && !list.isEmpty()){
				strAray_obj = ((HashMap)list.get(0)).keySet().toArray();
			}else{
				strAray_obj = new Object[0];
			}
			
			String strJSON = "";
			if(list == null || list.isEmpty()){
				strJSON = "[]";
			}else{
				strJSON = "[";
				
				for(int i=index; i<list.size()&&i<pageSize+index ;i++){
					
					strJSON = strJSON + "{";
					
					HashMap map = (HashMap)list.get(i);
					
					for(int j = 0; j < strAray_obj.length; j++){
						
						String strAray = strAray_obj[j].toString();
						
						String strTemp = "";
						
						if(map.get(strAray) == null){
		
						}else{
							strTemp = (String)map.get(strAray);
						}				
						strJSON = strJSON + strAray.toString() + ":'" + exchange.toHtml(strTemp) + "'";
						if(j != strAray_obj.length - 1){
							strJSON = strJSON + ",";
						}
					}
					
					strJSON = strJSON + "}";
					
					if(i != pageSize + index - 1 && i != list.size()-1){
						strJSON = strJSON + ",";
					}
				}
				
				strJSON = strJSON + "]";
			}
			
			return strJSON;
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;	
	}
	

	/**   
	  
	* createJSON(生成没有root的JSON字符串，无分) 
	* 创建人：郭峰   
	* 创建时间：Apr 23, 2013 3:59:40 PM  
	* 修改人：郭峰   
	* 修改时间：Apr 23, 2013 3:59:40 PM   
	* 修改备注         
	* @param   name  
	* @return String    DOM对象    
	* @Exception 异常对象    
	* @since  (版本)1.0   
	  
	*/
	public static String createunrootJSON(List list){
		try{
			Object [] strAray_obj;
			if(list != null && !list.isEmpty()){
				strAray_obj = ((HashMap)list.get(0)).keySet().toArray();
			}else{
				strAray_obj = new Object[0];
			}
			
			String strJSON = "";
			if(list == null || list.isEmpty()){
				strJSON = "[]";
			}else{
				strJSON = "[";
				
				for(int i=0; i<list.size();i++){
					
					strJSON = strJSON + "{";
					
					HashMap map = (HashMap)list.get(i);
					
					for(int j = 0; j < strAray_obj.length; j++){
						
						String strAray = strAray_obj[j].toString();
						
						String strTemp = "";
						
						if(map.get(strAray) == null){
		
						}else{
							strTemp = (String)map.get(strAray);
						}				
						strJSON = strJSON + strAray.toString() + ":'" + exchange.toHtml(strTemp) + "'";
						if(j != strAray_obj.length - 1){
							strJSON = strJSON + ",";
						}
					}
					
					strJSON = strJSON + "}";
					
					if(i != list.size()-1){
						strJSON = strJSON + ",";
					}
				}
				
				strJSON = strJSON + "]";
			}
			
			return strJSON;
		}catch(Exception e){
			System.out.println("Exception in GetParam.GetParam");
			e.printStackTrace();
		}
		return null;	
	}
}
