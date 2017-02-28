package alai.localhost.GD_wsdl;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;



public class Client {
	public static void main(String ss[]){
		GDLocator gd=new GDLocator();
		try {
		    while(true){
			System.out.println(gd.getGD().getState(10));
			//alai.GDT.Resint[] D=gd.getGD().getSirIntValuesFromCTR("D1000", 10, 16, 1);
			//System.out.println(D.length);
			Thread.sleep(100);
		    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

}
