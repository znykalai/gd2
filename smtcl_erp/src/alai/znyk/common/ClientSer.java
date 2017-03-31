package alai.znyk.common;


import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.rpc.ServiceException;
import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import alai.GDT.Resint;
import alai.localhost.GD_wsdl.GDLocator;
import alai.localhost.GD_wsdl.GDPortType;
import alai.znyk.kufang.KuFang;

import alai.znyk.server.SqlTool;



public class ClientSer {

	public GDLocator gd =new GDLocator();
	private static ClientSer INSTANCE;
	private boolean isOpenPlc=true;
	private ClientSer(){
		
		
		//gd.setGDEndpointAddress("http://192.168.1.222:9005/GD?cgi");
	//	 ((BindingProvider)gd).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"serviceUrl");
		try{//����ʱ��Ԥ��һ�£���������������
			getState(10000);
			KuFang.getIntance();
		   }catch(Exception ex){ex.printStackTrace();}
		/*
		 Client client = ClientProxy.getClient(gd);
    	 HTTPConduit http = (HTTPConduit) client.getConduit();   
    	 HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();   
    	 httpClientPolicy.setConnectionTimeout( 20000 );   
    	 httpClientPolicy.setAllowChunking( false );   
    	 httpClientPolicy.setReceiveTimeout( 40000 );   
    	 http.setClient(httpClientPolicy);
    	 */
    	// HttpClient cl;
    	
	}
	public static synchronized ClientSer getIntance(){
		if(INSTANCE!=null){
			return INSTANCE;
			}else{
				
			INSTANCE=new ClientSer();
			
			return INSTANCE;
		}
		
	}
	
	public GDPortType getGD() throws Exception{
		return gd.getGD();
	}
	public static String rffid1="0";
	public static String rffid2="0";
	public String getState(int t) throws RemoteException, ServiceException{
		
		if(isOpenPlc)return gd.getGD().getState(t);
		
		if(t==SqlPro.A��������){
			String s="501=1|502=0|503=1|504=0|505=1|506=0|507=1|508=0|"+
					"509=1|510=0|511=1|512=0|513=1|514=0";
			Vector v=SqlTool.findInVector("select ��λ,�ź� from �л��ź�  order by ��λ");
			String tem="";
			for(int i=0;i<v.size();i++){
				
				Vector row=(Vector)v.get(i);
				if(i==v.size()-1){
					tem=tem+row.get(0)+"="+row.get(1);
				}else{
				tem=tem+row.get(0)+"="+row.get(1)+"|";}
			}
			if(!s.equals(""))
			s=tem;
			
			return s
		;
		}
		
		if(t==SqlPro.B��������){
			return "601=1|602=0|603=1|604=0|605=1|606=0|607=1|608=0|"+
					"609=1|610=0|611=1|612=0|613=1|614=0"
		;
		}
		
		if(t==10){
			String s="502=1|504=0|506=1|508=0|510=1|512=0|514=1";
			Vector v=SqlTool.findInVector("select ��λ,�ź� from ��λ�ź�  order by ��λ");
			String tem="";
			for(int i=0;i<v.size();i++){
				
				Vector row=(Vector)v.get(i);
				if(i==v.size()-1){
					tem=tem+row.get(0)+"="+row.get(1);
				}else{
				tem=tem+row.get(0)+"="+row.get(1)+"|";}
			}
			if(!s.equals(""))
			s=tem;
			
			return s;
		
		}
		
		if(t==11){
			return "602=1|604=0|606=1|608=0|610=1|612=0|614=1";
		
		}
		
		if(t==SqlPro.������){return rffid1;}//�����������Ƿ����ź�
		if(t==SqlPro.ȥ����){return rffid2;}//�����������Ƿ����ź�
		return "1";
	}
	  public static String TP="1";
	public String ReadFromRffid(String message,int id) throws RemoteException, ServiceException{
		if(isOpenPlc) return gd.getGD().readFromRffid(message, id);
        if(id==1)
         TP= Math.round(Math.random()*10000)+"";
		return TP;
		
	}
	
	public int upPallet(int idEvent,int fromID,int toLocID,int machineID){
		try{
		if(isOpenPlc) return gd.getGD().upPallet(idEvent, fromID, toLocID, machineID);}
		catch(Exception ex){
			SqlPro.getLog().error(ex.getMessage());
			 ex.printStackTrace();}
		System.out.println("�ϻ�");
		return -1;
	}
	public int getPallet(int idEvent,String fromLocID,int toLocID,int machineID){
		try{
			if(isOpenPlc) return gd.getGD().getPallet(idEvent, fromLocID, toLocID, machineID);}
			catch(Exception ex){
				SqlPro.getLog().error(ex.getMessage());
				ex.printStackTrace();}
		System.out.println("�»�");
		return -1;}
	
	public int toBackBuffer(int idEvent, int fromLocID,int toLocID){ 
		 try{
			if(isOpenPlc) return gd.getGD().toBackBuffer(idEvent, fromLocID, toLocID);
			}
			catch(Exception ex){
				SqlPro.getLog().error(ex.getMessage());
				ex.printStackTrace();}
		System.out.println("�ػ�");
		return -1;}
	
	public static Resint RST1[]=new Resint[]{
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),new Resint(),
			
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),new Resint(),
			
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),new Resint(),
			
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),
			new Resint(), new Resint(),new Resint(),new Resint()
			
	};
	
	public alai.GDT.Resint[] getReturnPlc(String startAddress,int nums,int valueLen,
	          int machineID){
			try{
				if(isOpenPlc){
					RST1=gd.getGD().getSirIntValuesFromCTR(startAddress, nums, valueLen, machineID);
				return RST1;}
				
			}catch(Exception ex){
				SqlPro.getLog().error(ex.getMessage());
				ex.printStackTrace();}
			return RST1;
		
		}
	

	public alai.GDT.Resint[] getSirIntValuesFromCTR(String startAddress,int nums,int valueLen,
          int machineID){
		try{
			if(isOpenPlc)
				return gd.getGD().getSirIntValuesFromCTR(startAddress, nums, valueLen, machineID);
			
			
		}catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
		return RST1;
	
	}
	 public int writeSirIntToCTR(String strAddress, int valuseLeng, int[] invalues, int machineID) 
	 { if(isOpenPlc){
		 try{   alai.GDT.Inint tem []=new alai.GDT.Inint[invalues.length];
		         for(int i=0;i<tem.length;i++){tem[i]=new alai.GDT.Inint(invalues[i]);}
			 
				return gd.getGD().writeSirIntToCTR(strAddress, valuseLeng, tem, machineID);
				
			}catch(Exception ex){
				SqlPro.getLog().error(ex.getMessage());
				ex.printStackTrace();}
		 }
		 
		 return 1;
	 }
	 
	 public int writeValueToCTR(int type1, java.lang.String address, int value){
		 try{
				return gd.getGD().writeValueToCTR(type1, address, value);
				
			}catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
		 
		 return -1; 
		 
	 }
	 public int writeValuesToCTR(int type1, alai.GDT.Instr[] inaddress, alai.GDT.Inint[] inValues, int machineID)
	 {
		 try{
				return gd.getGD().writeValuesToCTR(type1, inaddress, inValues,machineID);
				
			}catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
		 
		 return -1;  
		 
	 }
	 
	 public int getValueFromCTR(int type1, java.lang.String address, int machineID) {
		 try{
				return gd.getGD().getValueFromCTR(type1, address, machineID);
				
			}catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
		 
		 return -1; 
	 }

	    /**
	     * Service definition of function ns__getValuesFromCTR
	     */
	    public alai.GDT.Resint[] getValuesFromCTR(int type1, alai.GDT.Instr[] inaddress, int machineID) {
	    	 try{
					return gd.getGD().getValuesFromCTR(type1, inaddress, machineID);
					
				}catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
			 
			 return null; 
	    	
	    }
	    
	   public String c_exeComment(String comment, int type,int machineID){
		   if(type==1){//��ͣ
			   if(isOpenPlc){
				   try{
				   return gd.getGD().wexeComment(comment, type);
					     
				   }catch(Exception ex){
					   SqlPro.getLog().error(ex.getMessage());
					   ex.printStackTrace();}
			   }
			   
		   }
          if(type==6){//�ɿ���ͣ
			   
        	  if(isOpenPlc){
				   try{
				   return gd.getGD().wexeComment(comment, type);
					     
				   }catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
			   }   
		   }
          if(type==2){//A�Ѷ����λ
        	  
        	  if(isOpenPlc){
				   try{
				   return gd.getGD().wexeComment(comment, type);
					     
				   }catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
			   }
			   
		   }
          if(type==3){//B�Ѷ����λ
        	  
        	  if(isOpenPlc){
				   try{
				   return gd.getGD().wexeComment(comment, type);
					     
				   }catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
			   }
			   
		   }
          if(type==4||type==5){//���Ϻ�ϵ�����,����-1˵������ִ�в��ɹ�
			//comment=eventID|fromID|toID|machineID|1=�ϻ���2=�»���3=����   
        	 //���жϻ��������������жϵ�
        		  Vector ��1=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬='ִ����' and ����<>'�����߻���' and ������= '"+machineID+"' order by idEvent");		
  				if(��1.size()>0){
  					Vector row=(Vector)��1.get(0);
  					try{
  					String back= row.get(0)+"|"+row.get(5)+"|"+row.get(6)+"|"+machineID+"|"+(row.get(3).equals("�ϻ�")?1:2);
  					 if(isOpenPlc){
  						return gd.getGD().wexeComment(back, type);
  					 }
  					}catch(Exception e){
  						SqlPro.getLog().error(e.getMessage());
  						e.printStackTrace();
  						return "-1";
  					}
  				}else{
  					 if(isOpenPlc){
  						 try{
   						return gd.getGD().wexeComment("-1", type);
   						}catch(Exception ex){
   							SqlPro.getLog().error(ex.getMessage());
   							ex.printStackTrace();
   						}
   					 }	
  					
  				}
        		  
  				return "-1";
			   
		   }
         
		   
		   return "�ɹ�";
		   
	   }
}
