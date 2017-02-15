package alai.znyk.server.webService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import javax.xml.ws.Endpoint;



public class WebServiceAPP {
	private static WebServiceAPP service;
	private WebServiceAPP() throws Exception{
		  File f=new File(WebServiceAPP.class.getResource("IP.txt").getFile());
   	   FileInputStream in=new FileInputStream(f);
   	   BufferedReader read=new BufferedReader(new InputStreamReader(in)) ;
   	   String address = read.readLine();
   	   System.out.println(  address);
   	   read.close();in.close();
                System.out.println("web service start");
                ServiceServerImp implementor= new ServiceServerImp();
             // String address="http://192.168.3.10:8080/helloWorld";
                Endpoint.publish(address, implementor);
                
                System.out.println("web service started");
		
	}
	
	public static WebServiceAPP getWebService(){
		try{
		if(service==null){
			service=new WebServiceAPP();
			return service;
			
		}else{return service;}
		}catch(Exception ex){ex.printStackTrace();}
		
		 return null;
	}
	
public static void main(String ss[]){
	  WebServiceAPP.getWebService();
	
}
   }
