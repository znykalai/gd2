package alai.znyk.test;



import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import alai.znyk.server.webService.WebServiceAPP;




public class Test3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String name= Test3.class.getResource("lo4j.pro").getFile();
		 PropertyConfigurator.configure(name);
		
		 System.out.println(name);
		 Logger logger = Logger.getLogger(Test3.class);  
		
		  // logger.debug( " debug " );
	       logger.error( " errorALAI " );

	}

}
