package alai.znyk.test;



import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;




public class Test3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String name= Test3.class.getResource("lo4j.pro").getFile();
		 PropertyConfigurator.configure("D:/gdgit/smtcl_erp/build/classes/alai/znyk/test/lo4j.pro");
		
		 System.out.println(name);
		 Logger logger = Logger.getLogger(Test3.class);  
		
		   logger.debug( " debug " );
	      // logger.error( " errorALAI " );

	}

}
