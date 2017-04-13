package alai.znyk.test;



import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import alai.znyk.common.ClientSer;
import alai.znyk.common.SqlPro;
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
		System.out.println("=="+ClientSer.getIntance().getSirIntValuesFromCTR("D10001", 1, 16, 1));
		
		}catch(Exception e){
			
			e.printStackTrace();
		}
		}

	}


