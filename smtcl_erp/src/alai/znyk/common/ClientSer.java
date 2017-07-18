
package alai.znyk.common;


import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Properties;
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
	public static boolean isOpenPlc=false;
	public static boolean isOpenRfid=false;
	public static Hashtable<String,String> th=new Hashtable<String,String>();
	long fCount=0;
	long wCount=0;
	long ftime=0;
	long wtime=0;
	long fCount2=0;
	long wCount2=0;
	long ftime2=0;
	long wtime2=0;
	long nCount=0;
	long ntime=0;
	long nCount2=0;
	long ntime2=0;
	private ClientSer(){
        try{
        	Properties pro=SqlPro.loadProperties(SqlPro.class.getResource("conf.pro").getFile());
    		String OpenPlc =pro.getProperty("isOpenPlc");	
    		String OpenRfid =pro.getProperty("isOpenPlc");	
    		//System.out.println(OpenPlc+"/====");
    		if(OpenPlc==null){
    			isOpenPlc=false;
    			isOpenRfid=false;
    		}else{
    			if(OpenPlc.equals("1")){
    				System.out.println("连接PLC");
    				isOpenPlc=true;}
    			else{isOpenPlc=false;}
    			
    			if(OpenRfid.equals("1")){
    				System.out.println("连接Rfid");
    				isOpenRfid=true;}
    			else{isOpenRfid=false;}
    			
    		}
			
		}catch(Exception ex){ex.printStackTrace();}
		
		
		//gd.setGDEndpointAddress("http://192.168.1.222:9005/GD?cgi");
	//	 ((BindingProvider)gd).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"serviceUrl");
		try{//启动时先预热一下，避免首例处罚。
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
	public static  synchronized ClientSer getIntance(){
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
	int nI=0;
	int nI2=0;
	String LOCK1="A";
	String LOCK2="B";
	private void sub1(){synchronized (LOCK1) {nI--;}}
	private void sub2(){synchronized (LOCK2) {nI2--;}}
	private void add1(){synchronized (LOCK1) {nI++;}}
	private void add2(){synchronized (LOCK2) {nI2++;}}
	public String getState(int t) throws RemoteException, ServiceException{
		long time1=System.currentTimeMillis();
		try{
		if(isOpenPlc){
			
			 if(t==2||t==4||t==6||t==8||t==10||t==12){
				   add1();
			      nCount++;
			      th.put("1立库访问", "堆垛机1访问开始,等待返回"+",访问类别="+t+",接口等待数="+nI);	    
			 }
			 else {
				 nCount2++;
				  add2();
			     th.put("2立库访问", "堆垛机2访问开始,等待返回"+",访问类别="+t+",接口等待数="+nI2);	
			 }
			 long time3=0;
			
			String bak=gd.getGD().getState(t);
			long time2=System.currentTimeMillis();
			time3=time2-time1;
			 if(t==2||t==4||t==6||t==8||t==10||t==12){
			       ntime=ntime+time3;
			       sub1();      
			 }
			 else  {ntime2=ntime2+time3;sub2();}
			 
			 if(bak==null){
				 if(t==2||t==4||t==6||t==8||t==10||t==12)
				 th.put("1立库访问", "堆垛机1返回值空异常，当前起始地址="+"访问类别="+t+",接口等待数="+nI);	
				 else  th.put("2立库访问", "堆垛机2返回值空异常，当前起始地址="+"访问类别="+t+",接口等待数="+nI2);	
			 }
			 if(bak!=null){
				 if(bak.equals("-2")){
					 if(t==2||t==4||t==6||t==8||t==10||t==12)
				 th.put("1立库访问", "堆垛机1返回值-2异常，当前起始地址="+"访问类别="+t+",接口等待数="+nI);	
					 else  th.put("2立库访问", "堆垛机2返回值-2异常，当前起始地址="+"访问类别="+t+",接口等待数="+nI2);	
					  }
			     if(bak.equals("-1")){
			    	 if(t==2||t==4||t==6||t==8||t==10||t==12)
			     th.put("1立库访问", "堆垛机1返回值-1异常，当前起始地址="+"访问类别="+t+",接口等待数="+nI);	
			    	 else  th.put("2立库访问", "堆垛机2返回值-1异常，当前起始地址="+"访问类别="+t+",接口等待数="+nI2);	
						  } 
			     if(bak.contains("&")||bak.equals("")){
			    	 if(t==2||t==4||t==6||t==8||t==10||t==12) 
				 th.put("1立库访问", "堆垛机1返回值字符异常，当前起始地址="+"访问类别="+t+",接口等待数="+nI);	
			    	 else  th.put("2立库访问", "堆垛机2返回值字符异常，当前起始地址="+"访问类别="+t+",接口等待数="+nI2);	
							  } 
				 
			  }
			 if(t==2||t==4||t==6||t==8||t==10||t==12)
			 th.put("1立库访问", "堆垛机1访问次数="+nCount+" 速度(ms)="+time3+" 平均速度(ms)="+(ntime/nCount)+"访问类别="+t+",接口等待数="+nI);
			 else  th.put("2立库访问", "堆垛机2访问次数="+nCount2+" 速度(ms)="+time3+" 平均速度(ms)="+(ntime2/nCount2)+"访问类别="+t+",接口等待数="+nI2);
			 return bak;
		   }
		
		}catch( Exception e){
			System.out.println("--------------------------------------------");
			if(t==2||t==4||t==6||t==8||t==10||t==12){
				sub1();
			 th.put("1立库访问", "堆垛机1中断异常，当前起始地址="+"访问类别="+t+",接口等待数="+nI);	
			 ntime=ntime+System.currentTimeMillis()-time1;
			}
			else {
				sub2();
				th.put("2立库访问", "堆垛机2中断异常，当前起始地址="+"访问类别="+t+",接口等待数="+nI2);
				 ntime2=ntime2+System.currentTimeMillis()-time1;
			}
			throw e;
			
		}
		
		
		if(t==SqlPro.A区输送线){
			String s="501=1|502=0|503=1|504=0|505=1|506=0|507=1|508=0|"+
					"509=1|510=0|511=1|512=0|513=1|514=0";
			Vector v=SqlTool.findInVector("select 工位,信号 from 有货信号  order by 工位");
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
		
		if(t==SqlPro.B区输送线){
			return "601=0|602=0|603=0|604=0|605=0|606=0|607=0|608=0|"+
					"609=0|610=0|611=0|612=0|613=0|614=0"
		;
		}
		
		if(t==10){
			String s="502=1|504=1|506=1|508=0|510=1|512=0|514=1";
			Vector v=SqlTool.findInVector("select 工位,信号 from 到位信号  order by 工位");
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
		
		if(t==SqlPro.来料升){return rffid1; }//返回升降机是否有信号
		if(t==SqlPro.去料升){return rffid2;}//返回升降机是否有信号
		return "1";
	}
	  public static String TP="";
	public String ReadFromRffid(String message,int id) throws RemoteException, ServiceException{
		if(isOpenPlc) return gd.getGD().readFromRffid(message, id);
      
		return TP;
		
	}
	
	public int upPallet(int idEvent,int fromID,int toLocID,int machineID){
		try{
		if(isOpenPlc) return gd.getGD().upPallet(idEvent, fromID, toLocID, machineID);}
		catch(Exception ex){
			SqlPro.getLog().error("error 调用SERVICE上托盘发送异常",ex);
			 ex.printStackTrace();}
		System.out.println("上货");
		return -1;
	}
	public int getPallet(int idEvent,String fromLocID,int toLocID,int machineID){
		try{
			if(isOpenPlc) return gd.getGD().getPallet(idEvent, fromLocID, toLocID, machineID);}
			catch(Exception ex){
				SqlPro.getLog().error("error 调用SERVICE下托盘发送异常",ex);
				ex.printStackTrace();}
		System.out.println("下货");
		return -1;}
	
	public int toBackBuffer(int idEvent, int fromLocID,int toLocID){ 
		 try{
			if(isOpenPlc) return gd.getGD().toBackBuffer(idEvent, fromLocID, toLocID);
			}
			catch(Exception ex){
				SqlPro.getLog().error("error 调用SERVICE回流发送异常",ex);
				ex.printStackTrace();}
		System.out.println("回货");
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
	
	public static Resint RST2[]=new Resint[]{
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
					if(machineID==1){
					Resint[] tem=gd.getGD().getSirIntValuesFromCTR(startAddress, nums, valueLen, machineID);
					for(int m=0;m<tem.length;m++){
						if(tem[m].resInt==-1||tem[m].resInt==-2)
							{
							SqlPro.getLog().error("error 调用SERVICE读取"+machineID+"号PLC异常 返回值="+tem[m].resInt);
							return RST1;
							}
					}
					
					if(tem[0].resInt!=-1)
					  RST1=tem;
				 return RST1;
				
					}
					 else{
					 Resint[] tem=	gd.getGD().getSirIntValuesFromCTR(startAddress, nums, valueLen, machineID);
					 for(int m=0;m<tem.length;m++){
							if(tem[m].resInt==-1||tem[m].resInt==-2) {
								SqlPro.getLog().error("error 调用SERVICE读取"+machineID+"号PLC异常 返回值="+tem[m].resInt);	
								return RST2;
								}	
						}	
					   if(tem[0].resInt!=-1)
							  RST2=tem;
							return RST2;
						  
					  }
				
				}
				
			}catch(Exception ex){
				SqlPro.getLog().error("error 调用SERVICE读取"+machineID+"号PLC异常",ex);
			
				ex.printStackTrace();}
			
			return machineID==1?RST1:RST2;
		
		}
	
    int pfI=0;
    int pfI2=0;
	public alai.GDT.Resint[] getSirIntValuesFromCTR(String startAddress,int nums,int valueLen,
          int machineID){
		long time1=System.currentTimeMillis();
		try{
			if(isOpenPlc){
				if(machineID==1){
				fCount++;pfI++;
				 th.put(machineID+"PLC读", "PLC1访问开始,读取等待返回，当前起始地址="+startAddress+",接口等待数="+pfI);	
				}
				else {
					pfI2++;
					fCount2++;
				 th.put(machineID+"PLC读", "PLC2访问开始,读取等待返回，当前起始地址="+startAddress+",接口等待数="+pfI2);	
				
				}
				
				Resint[] bak=gd.getGD().getSirIntValuesFromCTR(startAddress, nums, valueLen, machineID);
				long time2=System.currentTimeMillis();
				long time3=time2-time1;
				if(machineID==1){
				ftime=ftime+time3;
				pfI--;
				}
				else {ftime2=ftime2+time3;
				pfI2--;
				}
				if(bak[0].getResInt()==-1||bak[bak.length-1].getResInt()==-1){
				th.put(machineID+"PLC读", "PLC"+machineID+"读取返回值异常-1，当前起始地址="+startAddress+",接口等待数="+(machineID==1?pfI:pfI2));	
				}
				else{
					if(machineID==1)	
				th.put(machineID+"PLC读", "PLC1读取次数="+fCount+" 速度(ms)="+time3+" 平均速度(ms)="+(ftime/fCount)+"当前起始地址="+startAddress+",接口等待数="+pfI);
					else
				th.put(machineID+"PLC读", "PLC2读取次数="+fCount2+" 速度(ms)="+time3+" 平均速度(ms)="+(ftime2/fCount2)+"当前起始地址="+startAddress+",接口等待数="+pfI2);
				}
				return bak;
			
			}
			
		}catch(Exception ex){
			
			if(machineID==1){
				ftime=ftime+System.currentTimeMillis()-time1;
				pfI--;
				th.put(machineID+"PLC读", "PLC1读取中断异常，当前起始地址="+startAddress+",接口等待数="+pfI);	
			}
			 else {ftime2=ftime2+System.currentTimeMillis()-time1;
			    pfI--;
			    th.put(machineID+"PLC读", "PLC2读取中断异常，当前起始地址="+startAddress+",接口等待数="+pfI2);
			 }
			SqlPro.getLog().error("error 调用SERVICE读取"+machineID+"号PLC异常 在304行");
			//SqlPro.getLog().error(ex.getMessage());
			}
		
		
		return machineID==1?RST1:RST2;
	
	}
	int pwI=0;
	int pwI2=0;
	 public int writeSirIntToCTR(String strAddress, int valuseLeng, int[] invalues, int machineID) 
	 {   long time1=System.currentTimeMillis();
		 if(isOpenPlc){
		 try{   alai.GDT.Inint tem []=new alai.GDT.Inint[invalues.length];
		        for(int i=0;i<tem.length;i++){tem[i]=new alai.GDT.Inint(invalues[i]);}
		        int back=-1;
		        if(machineID==1){
		         wCount++; pwI++;
		         th.put(machineID+"PLC写", "PLC1访问开始,写入等待返回，当前起始地址="+strAddress+",接口等待数="+pwI);	
		        }
		        else  {wCount2++;pwI2++;
		         th.put(machineID+"PLC写", "PLC2访问开始,写入等待返回，当前起始地址="+strAddress+",接口等待数="+pwI);	
		        }
				
		         back=gd.getGD().writeSirIntToCTR(strAddress, valuseLeng, tem, machineID);
		         long time2=System.currentTimeMillis();
				 long time3=time2-time1;
				  if(machineID==1){
				 wtime=wtime+time3; pwI--;}
				  else { wtime2=wtime2+time3;pwI2--;} 
		    if(back!=0){
		    	th.put(machineID+"PLC写", "PLC"+machineID+"写人异常，当前起始地址="+strAddress+",接口等待数="+(machineID==1?pwI:pwI2));	
		    	SqlPro.getLog().error("返回值="+back+" 调用SERVICE写入 开始地址="+strAddress+" 长度="+invalues.length+" 第"+machineID+"号PLC异常");
		    	return -1;
		    	}
		    if(machineID==1)
		    th.put(machineID+"PLC写", "写入次数="+wCount+" 速度(ms)="+time3+" 平均速度(ms)="+(wtime/wCount)+"当前起始地址="+strAddress+",接口等待数="+pwI);
		    else
		    th.put(machineID+"PLC写", "写入次数="+wCount2+" 速度(ms)="+time3+" 平均速度(ms)="+(wtime2/wCount2)+"当前起始地址="+strAddress+",接口等待数="+pwI2);
		    return back;
				
			}catch(Exception ex){
				 if(machineID==1){
					 wtime=wtime+System.currentTimeMillis()-time1; pwI--;
					 th.put(machineID+"PLC写", "PLC1写人中断异常，当前起始地址="+strAddress+",接口等待数="+pwI);		 
				 }
					else  {wtime2=wtime2+System.currentTimeMillis()-time1;pwI2--;
					 th.put(machineID+"PLC写", "PLC2写人中断异常，当前起始地址="+strAddress+",接口等待数="+pwI2);	
					}
				
				SqlPro.getLog().error("error 调用SERVICE写入"+machineID+"号PLC异常 在334行");
			    ex.printStackTrace();}
		   return -1;  
		 }else{
			 if(!strAddress.equals("D11999"));
			 //System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+strAddress+",nums="+invalues.length+",lengths="+valuseLeng+">>>>>>>>>>>>>>>>>>>>"+((invalues[0]&0b01)==1));
			 //alai.GDT.Inint tem []=new alai.GDT.Inint[invalues.length];
	        //  for(int i=0;i<tem.length;i++){tem[i]=new alai.GDT.Inint(invalues[i]);}
			
		 }
	     return 1;  
		
	 }
	 
	 public int writeValueToCTR(int type1, java.lang.String address, int value){
		 try{
				return gd.getGD().writeValueToCTR(type1, address, value);
				
			}catch(Exception ex){
				SqlPro.getLog().error(ex.getMessage());}
		 
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
		   if(type==1){//急停
			   if(isOpenPlc){
				   try{
				   return gd.getGD().wexeComment(comment, type);
					     
				   }catch(Exception ex){
					   SqlPro.getLog().error(ex.getMessage());
					   ex.printStackTrace();}
			   }
			   
		   }
          if(type==6){//松开急停
			   
        	  if(isOpenPlc){
				   try{
				   return gd.getGD().wexeComment(comment, type);
					     
				   }catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
			   }   
		   }
          if(type==2){//A堆垛机复位
        	  
        	  if(isOpenPlc){
				   try{
				   return gd.getGD().wexeComment(comment, type);
					     
				   }catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
			   }
			   
		   }
          if(type==3){//B堆垛机复位
        	  
        	  if(isOpenPlc){
				   try{
				   return gd.getGD().wexeComment(comment, type);
					     
				   }catch(Exception ex){SqlPro.getLog().error(ex.getMessage());}
			   }
			   
		   }
          if(type==4||type==5){//故障后断点启动,返回-1说明方法执行不成功
			//comment=eventID|fromID|toID|machineID|1=上货，2=下货，3=回流   
        	 //不判断回流，回流不需中断点
        		  Vector 堆1=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态='执行中' and 动作<>'输送线回流' and 请求区= '"+machineID+"' order by idEvent");		
  				if(堆1.size()>0){
  					Vector row=(Vector)堆1.get(0);
  					try{
  					String back= row.get(0)+"|"+row.get(5)+"|"+row.get(6)+"|"+machineID+"|"+(row.get(3).equals("上货")?1:2);
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
         
		   
		   return "成功";
		   
	   }
}
