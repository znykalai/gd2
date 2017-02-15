/**   
* 文件名：exchange.java
*   
* 版本信息�?   v1.0
* 日期：Jun 1, 2011   
* Copyright 足下 Corporation 2011    
* 版权�?�?   
*   
*/
package com.sygf.erp.util;

import java.util.regex.Matcher;
//import java.util.regex.Pattern;

/**
 * Description:文本域中文字显示预处理，使之识别换行和行首空
 * <br/>Date:20096310:19:54
 * @author  xiulj
 * @version  1.0
 */
public class exchange{
	  public exchange() {}
	  public static String change(String s) {
		  s = toHtml(s);
		  return s;
	  }
	//特殊字符转为Html
	public static String toHtml(String s) {
	    //s = Replace(s,"<","&lt;");
	    //s = Replace(s,">","&gt;");
	    s = Replace(s,"\t","    ");
	    s = Replace(s,"\r\n","\n");
	    //s = Replace(s,"\n"," ");
	    //s = Replace(s,": ",":");
	    //s = Replace(s,"'","\\'");
	    //s = Replace(s,"\\","&#92;");
		//s = Replace(s,"\"","\\\"");
	    return s;
    }

	public static String toHtmlFCK(String s) {

    s = Replace(s,"\r\n","\n");
    s = Replace(s,"\n","<br>");
    s = Replace(s," ","&nbsp;");
    
    return s;
    }
	//
    public static String unHtml(String s){
	s = Replace(s,"<br>","\n");
	s = Replace(s,"&nbsp;"," ");
	return s;
  	}

	public static String unHtmls(String s){
	s = Replace(s,"<br>","");
	s = Replace(s,"&nbsp;","");
	s = Replace(s," ","");
	return s;
  	}

	public static String toJS(String s) {

	s = Replace(s,"\"","");
    
    return s;
    }

	public static String toHtmlJS(String s) {

    s = Replace(s,"<","&lt;");
    s = Replace(s,">","&gt;");
    s = Replace(s,"\t","    ");
    s = Replace(s,"\r\n","\n");
    s = Replace(s,"\n","<br>");
    s = Replace(s," ","&nbsp;");
    s = Replace(s,"'","&#39;");
    s = Replace(s,"\\","&#92;");
	s = Replace(s,"\"","");
    
    return s;
    }

	public static String unJS(String s) {

	s = Replace(s,"","\"");
    
    return s;
    }

	public static String toURL(String s){
	s = Replace(s,"<","&lt;");
    s = Replace(s,">","&gt;");
    s = Replace(s,"\t","    ");
    s = Replace(s,"\r\n","\n");
    s = Replace(s,"\n","<br>");
	s = Replace(s," ","");
    s = Replace(s,"\"","&quot;");
	s = Replace(s,"%","");
	s = Replace(s,"+","");
    s = Replace(s,"&","");
	return s;
  	}
	public static String unURL(String s){
	s = Replace(s,"","&");
	s = Replace(s,"","%");
	s = Replace(s,"","+");
	s = Replace(s,"<br>","\n");
	s = Replace(s,"&lt;","<");
    s = Replace(s,"&gt;",">");
    s = Replace(s,"    ","\t");
    s = Replace(s,"\n","\r\n");    
	s = Replace(s,""," ");
    s = Replace(s,"&quot;","\"");    
	return s;
  	}

  //Replace
   public static String Replace(String source,String oldString,String newString) {
    if(source == null) return null;
    StringBuffer output = new StringBuffer();
    int lengOfsource = source.length();
    int lengOfold = oldString.length();
    int posStart = 0;
    int pos;
    while((pos = source.indexOf(oldString,posStart)) >= 0) {
      output.append(source.substring(posStart,pos));
      output.append(newString);
      posStart = pos + lengOfold;
    }
    if(posStart < lengOfsource) {
      output.append(source.substring(posStart));
    }
    return output.toString();
  }
   /**
    * 去除字符串中的空格回车换行符、制表符
    * @author 史玉
    *
    */
//   public static String replaceBlank(String str)
//	{
//		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	//	System.out.println("before:"+str);
//		Matcher m = p.matcher(str);
//		String after = m.replaceAll("");
//	//	System.out.println("after:"+after);
//		return after;
//	}
   /**openflashchart处理符号
    * @author 史玉
    * @return
    */
//   public static String replaceSign(String str){
//	   str = str.replace(",", "");
//	   str = str.replace("%", "");
//	   return str;
//   }
//
//	//将查询数据库条件的中处理特殊字符
//	public static String toSqlCondition(String s) {
//		s = Replace(s,"\'","\'\'");
//		s = Replace(s,"\\","\\\\");
//		return s;
//	}
}