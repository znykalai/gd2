package com.sygf.erp.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import alai.znyk.common.SqlPro;

public class GetError implements Serializable {
	
	/**
	 * 获取异常
	 */
	private static final long serialVersionUID = 1L;
	private static String errHtml;
	private static String date;
	private static GetError err;
	private static Properties getFile=new Properties();
	private GetError(){
		new Thread(){public void run(){while(true){
			try{
				getFile=SqlPro.loadProperties2(SqlPro.class.getResource("error.pro").getFile());
				
				Thread.sleep(20000);
				
			}catch(Exception ex){}
			
		}}}.start();
		
	}
	public synchronized static GetError getInstace(){
		if(date==null){date=DateFormat.getDateTimeInstance().format(new Date());};
		
		if(err==null){err=new GetError(); return err;}else{return err;}
	};
	
	public String getArm(int con,String title){
		errHtml="";
		if(((con&0b01)==1)){	//堆垛机故障
			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("1")+" &nbsp;"+date+"</span></br>";
		};
		if(((con&0b10)==2)){	//输送线故障
			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("2")+" &nbsp;"+date+"</span></br>";
		};
		if(((con&0b100)==4)){	//x小车故障
			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("3")+"&nbsp;"+date+"</span></br>";
		};
		if(((con&0b1000)==8)){	//y小车故障
			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("4")+" &nbsp;"+date+"</span></br>";
		};
		if(((con&0b10000)==16)){
			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("5")+" &nbsp;"+date+"</span></br>";
		};//5
		if(((con&0b100000)==32)){
			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("6")+" &nbsp;"+date+"</span></br>";
		};//6
		if(((con&0b1000000)==64)){
			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("7")+" &nbsp;"+date+"</span></br>";
		};//7
		if(((con&0b10000000)==128)){
			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("8")+" &nbsp;"+date+"</span></br>";
		};//8
		if(((con&0b100000000)==256)){
			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("9")+" &nbsp;"+date+"</span></br>";
		};//9
		if(((con&0b1000000000)==512)){
			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("10")+" &nbsp;"+date+"</span></br>";
		};//10
//		if(((con&0b10000000000)==1024)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("11")+" &nbsp;"+date+"</span></br>";
//		};//11
//		if(((con&0b100000000000)==2048)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("12")+" &nbsp;"+date+"</span></br>";
//		};//12
//		if(((con&0b1000000000000)==4096)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("13")+" &nbsp;"+date+"</span></br>";
//		};//13
//		if(((con&0b10000000000000)==8192)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("14")+" &nbsp;"+date+"</span></br>";
//		};//14
//		if(((con&0b100000000000000)==16384)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("15")+" &nbsp;"+date+"</span></br>";
//		};//15
//		if(((con&0b1000000000000000)==32768)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("16")+" &nbsp;"+date+"</span></br>";
//		};//16
//		if(((con&0b10000000000000000)==65536)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("17")+" &nbsp;"+date+"</span></br>";
//		};//17
//		if(((con&0b100000000000000000)==131072)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("18")+" &nbsp;"+date+"</span></br>";
//		};//18
//		if(((con&0b1000000000000000000)==262144)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("19")+" &nbsp;"+date+"</span></br>";
//		};//19
//		if(((con&0b10000000000000000000)==524288)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("20")+" &nbsp;"+date+"</span></br>";
//		};//20
//		if(((con&0b100000000000000000000)==1048576)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("21")+" &nbsp;"+date+"</span></br>";
//		};//21
//		if(((con&0b1000000000000000000000)==2097152)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("22")+" &nbsp;"+date+"</span></br>";
//		};//22
//		if(((con&0b10000000000000000000000)==4194304)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("23")+" &nbsp;"+date+"</span></br>";
//		};//23
//		if(((con&0b100000000000000000000000)==8388608)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("24")+" &nbsp;"+date+"</span></br>";
//		};//24
//		if(((con&0b1000000000000000000000000)==16777216)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("25")+" &nbsp;"+date+"</span></br>";
//		};//25
//		if(((con&0b10000000000000000000000000)==33554432)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("26")+" &nbsp;"+date+"</span></br>";
//		};//26
//		if(((con&0b100000000000000000000000000)==67108864)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("27")+" &nbsp;"+date+"</span></br>";
//		};//27
//		if(((con&0b1000000000000000000000000000)==134217728)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("28")+" &nbsp;"+date+"</span></br>";
//		};//28
//		if(((con&0b10000000000000000000000000000)==268435456)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("29")+" &nbsp;"+date+"</span></br>";
//		};//29
//		if(((con&0b100000000000000000000000000000)==536870912)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("30")+" &nbsp;"+date+"</span></br>";
//		};//30
//		if(((con&0b1000000000000000000000000000000)==1073741824)){
//			errHtml+="<span style='margin:15px;'>"+title+"区异常："+getFile.getProperty("31")+" &nbsp;"+date+"</span></br>";
//		};//31
		if("".equals(errHtml)){date=null;};
		return errHtml;
	};
	
	
}
