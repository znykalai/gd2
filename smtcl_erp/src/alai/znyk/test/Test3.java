package alai.znyk.test;



import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import alai.znyk.common.ClientSer;
import alai.znyk.common.SqlPro;
import alai.znyk.plc.PLC;
import alai.znyk.plc._1_6ST;
import alai.znyk.server.SqlTool;
import alai.znyk.server.webService.WebServiceAPP;




public class Test3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* String name= Test3.class.getResource("lo4j.pro").getFile();
		 PropertyConfigurator.configure(name);
		
		 System.out.println(name);
		 Logger logger = Logger.getLogger(Test3.class);  
         // logger.debug( " debug " );
	       logger.error( " errorALAI " );*/
		
		/*
		Properties pro=SqlPro.loadProperties(SqlPro.class.getResource("conf.pro").getFile());
		System.out.println(pro.getProperty("serviceIP"));
		Enumeration en=pro.keys();
		while(en.hasMoreElements()){
			
			System.out.println(en.nextElement());*/
		try{
		//System.out.println("=="+ClientSer.getIntance().getSirIntValuesFromCTR("D10001", 1, 16, 1));
		    // String val2="";
			//String val=val2+",3=0,2=3";
			//SqlTool.setStateForEventID(84, 3, "");
		    // System.out.println( SqlTool.findOneRecord("select 类型  from 通用物料  where 物料编码='TIBAN'"));
			//System.out.println(PLC.getIntance());
			
			String val="1!_!";
			val=val.substring(0, val.length()-3);
			//if(val.equals(""))
			System.out.println(Integer.toHexString(16));
			
			//String back= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+11+"'");
				//if(back!=null&&back.equals("1000")) {
				// }
			mode();
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		}
	
	public  static void mode(){
		int a = 10;
		int b = 0;
		
		try {
			
			int test = a/b;
		} catch (Exception e) {
			//log.error("error" ,e);
			 SqlPro.getLog().error("error大数",e);
			//main  ERROR c.d.s.LogThrowableRule -
			//			error
			//java.lang.ArithmeticException: / by zero
			//          at com.doctor.slf4j.LogThrowableRule.test_rightWayGetInfoForThrowable(LogThrowableRule.java:35) ~[classes/:na]
			//          at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_11]
			//          .............
			
			String msg = String.format("{error:'%s/%s'}", a,b);//string带参数，利用String.format
			 SqlPro.getLog().error( msg);
		}
	}

	}


