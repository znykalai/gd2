package com.sygf.erp.util;
import java.text.*;
import java.util.Calendar;

public class GetRecentDate {
	//日期格式：yyyy-MM-dd HH:mm:ss
	public static String getRecentDate(String format){
		try{
			String time = "";
			java.util.Date now = new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			time=formatter.format(now);
			return time;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	//将原时间格式字符串转换成新时间格式字符串
	public static String getFormatDateStr(String date,String oldformat,String newformat){
		try{
			String time = "";
			SimpleDateFormat sdf = new SimpleDateFormat(oldformat);
			java.util.Date dateD = sdf.parse(date);
			SimpleDateFormat formatter = new SimpleDateFormat(newformat);
			time=formatter.format(dateD);
			return time;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 把String times = "2010-02-05 02:54"类型的字符串转换?"201002050254"
	 */
	public static String TimeToNum(String times){
		String result="";
		if(times!=null&&!times.equals("")){
			String temp = times.replaceAll("-",""); 
			temp = temp.replaceAll(":", ""); 
			result = temp.replaceAll(" ", ""); 
		}
		return result;
		}
	/*
	 * 把字符串"201002050254"转换为时间字符串"2010-02-05 02:54"
	 */
	public static String NumToTime(String num){
		String result="";
//		java.text.SimpleDateFormat formatter=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(num!=null&&!num.equals("")){
			StringBuffer sb=new StringBuffer("");
			sb.append(num.subSequence(0, 4));
			sb.append("-");
			sb.append(num.subSequence(4, 6));
			sb.append("-");
			sb.append(num.subSequence(6, 8));
			sb.append(" ");
			sb.append(num.substring(8, 10));
			sb.append(":");
			sb.append(num.substring(10));
			result=String.valueOf(sb);
		}
		return result;
	}
	
    // 上月第一?   
    public static String getPreviousMonthFirst(){     
        String str = "";   
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");       
   
        Calendar lastDate = Calendar.getInstance();   
        lastDate.set(Calendar.DATE,1);//设为当前月的1?   
        lastDate.add(Calendar.MONTH,-1);//减一个月，变为下月的1?   
          
        str=sdf.format(lastDate.getTime());   
       return str;     
     } 

    //得到指定月的天数
	public static int getMonthLastDay(int year, int month){
		try{
			Calendar a = Calendar.getInstance();
			a.set(Calendar.YEAR, year);
			a.set(Calendar.MONTH, month - 1);
			a.set(Calendar.DATE, 1);//把日期设置为当月第一?
			a.roll(Calendar.DATE, -1);//日期回滚?天，也就是最后一?
			int maxDate = a.get(Calendar.DATE);
			return maxDate;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
		
    }
}
