package alai.znyk.kufang;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import alai.znyk.common.ClientSer;
import alai.znyk.common.SqlPro;
import alai.znyk.plc.PLC;
import alai.znyk.server.SqlTool;

public class KuFang {
	int zl=0;
	int agv=0;
	private static KuFang INSTANCE;
	int rf1=0;
	int rf2=0;
	int line=0;
	String isRffid1="";
	String isRffid2="";
	String isRffid3="";
	String isRffid4="";
	public static void main(String ss[]){
		
		//KuFang.getIntance();
		//PLC.getIntance();
		//startDakuQingqiu();
	}
	private KuFang(){
		
		new Thread(){
			public void run(){
				try{
				while(true){
					startLine(1);
					
				    Thread.sleep(500);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
				try{
				while(true){
					
					startLine(2);
				    Thread.sleep(500);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
	
		
		new Thread(){
			public void run(){
			try{
				while(true){
					if(SqlPro.is指令调度)
					 start堆垛机指令();
				Thread.sleep(500);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
			try{
				while(true){
					startlineAGV();
				Thread.sleep(500);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
			try{
				while(true){
				  startDownRFFID2();
				Thread.sleep(500);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
			try{
				while(true){
				startDownRFFID();
				Thread.sleep(500);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
			try{
				while(true){
					statPaletToPLC(1);
				Thread.sleep(2000);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
			try{
				while(true){
					statPaletToPLC(2);
				Thread.sleep(2000);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
			try{
				System.out.println("大库调度指令启动");
				while(true){
					if(SqlPro.is大库调度)
						startDakuQingqiu();
				Thread.sleep(30000);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
	}
	
	public static synchronized KuFang getIntance(){
		if(INSTANCE!=null){return INSTANCE;}else{
			INSTANCE=new KuFang();
			System.out.println("库房初始化---------------");
			
			return INSTANCE;
		}
		
	}
	
//更新托盘在7条输送线上的位置	
public void startLine(int machineID){
	//测试通过
	if(machineID==1){	
   try{
	  String ss= ClientSer.getIntance().getState(10);
	  if(ss==null||ss.equals("-2")){
	  System.out.println("startLine 断开读取超时,状态码="+ss);
	  return;}
	  if(ss==null||ss.equals("-1")){
		  System.out.println("startLine 立库断开,状态码="+ss);
		  return;}
	  String sm[]=ss.split("\\|");
	  if(sm.length<2){
		  SqlPro.getLog().error("startLine 立库断开,状态码=A区获取立库状态不对");
		  System.out.println("startLine 立库断开,状态码=A区获取立库状态不对");
		  return;}
	  
	String sql1= "select  货位序号,托盘编号   from 货位表  where  货位序号  between 501 and 514 order by 货位序号";
	
	Vector<Vector> tem1=SqlTool.findInVector(sql1);
	
   
	for(int r=0;r<tem1.size()/2;r++){
		Vector line1=tem1.get(r*2);
		Vector line2=tem1.get(r*2+1);
		String firstTP=line1.get(1)==null?"":line1.get(1).toString();
		String secTP=line2.get(1)==null?"":line2.get(1).toString();
		if(secTP.equals("")){
			if(sm[r].split("=")[1].equals("1")){
				//System.out.println((r*2+1)+"="+sm[r*2+1]+"="+ss);
				String fromID=line1.get(0)+"";
				String toID=line2.get(0)+"";
				
				/*
				 * 像PLC里面写入PLC地址
				 * **/
				if(!firstTP.equals("")){
					SqlTool.update7Line(firstTP, fromID, toID);
					// String sql2="select 托盘编号  from 货位表   where  货位序号='"+toID+"'";	
				  String wuliao= SqlTool.findOneRecord("select 物料  from 库存托盘  where 托盘编号="+"'"+firstTP+"'");
				  String leixing=SqlTool.findOneRecord("select 类型  from 通用物料  where 物料编码="+"'"+wuliao+"'");
				  SqlTool.clearValueForPalet(toID, 1);
				  SqlTool.WriteAddresInPaletToPLC(leixing, firstTP, toID, 1);
					
				  }
			}
			
		 }
		
	 } }catch(Exception ex){
		 SqlPro.getLog().error("A区输送线是否有托盘：",ex);
		
		 ex.printStackTrace();}
   
	}
   
	if(machineID==2){
   try{
	
	 String ss2= ClientSer.getIntance().getState(11);
	  if(ss2==null||ss2.equals("-2")){
		  System.out.println("startLine 断开读取超时,状态码="+ss2);
		  return;}
		  if(ss2==null||ss2.equals("-1")){
		  System.out.println("startLine 立库断开,状态码="+ss2);
			  return;}
	 
	 String sm2[]=ss2.split("\\|"); 
	  if(sm2.length<2){
		  SqlPro.getLog().error("startLine 立库断开,状态码=B区获取立库状态不对");
		  System.out.println("startLine 立库断开,状态码=B区获取立库状态不对");
		  return;}
	 String sql2= "select  货位序号,托盘编号   from 货位表  where  货位序号  between 601 and 614 order by 货位序号";
	Vector<Vector> tem2=SqlTool.findInVector(sql2);
	for(int r=0;r<tem2.size()/2;r++){
		Vector line1=tem2.get(r*2);
		Vector line2=tem2.get(r*2+1);
		String firstTP=line1.get(1)==null?"":line1.get(1).toString();
		String secTP=line2.get(1)==null?"":line2.get(1).toString();
		if(secTP.equals("")){
			if(sm2[r].split("=")[1].equals("1")){
				String fromID=line1.get(0)+"";
				String toID=line2.get(0)+"";
				
				/*
				 * 像PLC里面写入PLC地址
				 * **/
				if(!firstTP.equals("")){
					System.out.println("update7Line,进入托盘从第1位向第二位移动，在方法startLine，B区");
					SqlTool.update7Line(firstTP, fromID, toID);
					System.out.println("update7Line,执行完成，在方法startLine，B区");
					// String sql2="select 托盘编号  from 货位表   where  货位序号='"+toID+"'";	
				  String wuliao= SqlTool.findOneRecord("select 物料  from 库存托盘  where 托盘编号="+"'"+firstTP+"'");
				  String leixing=SqlTool.findOneRecord("select 类型  from 通用物料  where 物料编码="+"'"+wuliao+"'");
				  SqlTool.clearValueForPalet(toID, 2);
				  SqlTool.WriteAddresInPaletToPLC(leixing, firstTP, toID, 2);
				  System.out.println("update7Line,写人PLC料箱地址 在方法startLine完成"); 
				  }
			}
			
		 }
		
	 } 
	
   }catch(Exception ex){
		 SqlPro.getLog().error("B区输送线是否有托盘：",ex);
		
		 ex.printStackTrace();}
   
	}
	
	if(line==0)
     System.out.println("缓存货位与上料位自动更新启动完成");
   
	line=1;
		
	
}

public void start堆垛机指令(){
	
	 Vector 堆1上=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 动作='上货' and 请求区= '1' order by idEvent");
     Vector 堆1下=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 动作='下货' and 请求区= '1' order by idEvent");
    
     boolean run=false;
     boolean run2=false;
     
     boolean up1=false;
     boolean up2=false;
     boolean dw1=false;
     boolean dw2=false;
     int last1=1;
     if(堆1上.size()>0){
    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
    	 Vector up=(Vector)堆1上.get(0);
    	 if(up.get(8).equals("执行中")||up.get(8).equals("已发送")){run=true;up1=true;}
      }
     
     if(堆1下.size()>0){
    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
    	 Vector down=(Vector)堆1下.get(0);
    	 if(down.get(8).equals("执行中")||down.get(8).equals("已发送")){run=true;dw1=true;}
      
     }
     
     
     Vector 堆2上=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 动作='上货' and 请求区= '2' order by idEvent");
     Vector 堆2下=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 动作='下货' and 请求区= '2' order by idEvent");
     
    
     int last2=1;
     if(堆2上.size()>0){
    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
    	 Vector up=(Vector)堆2上.get(0);
    	 if(up.get(8).equals("执行中")||up.get(8).equals("已发送")){run2=true;up2=true;}
      }
     
     if(堆2下.size()>0){
    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
    	 Vector down=(Vector)堆2下.get(0);
    	 if(down.get(8).equals("执行中")||down.get(8).equals("已发送")){run2=true;dw2=true;}
      
     }
     
    //如果这个堆垛机没有执行的指令，那么继续 
    if(last1==1){//上货
    if(!run) {
      //	if(!up2){
    	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
    	if(堆1上.size()>0){
    	try {
    		//在临时方案里面加入如下判断
    		
			String state=ClientSer.getIntance().getState(SqlPro.堆垛机1状态);//获取堆垛机1的状态
			if(state.equals(SqlPro.堆垛空闲码)){
				 if(堆1上.size()>0){
			    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆1上.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	int bak= ClientSer.getIntance().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 1);
			    	if(bak==1){
			    		run=true;
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
			      }
				
			}
		   } catch (Exception e) {
			   SqlPro.getLog().error("A上货指令发送异常：",e);
			  
			   e.printStackTrace();}
    	
    	
    	}
    	last1=2;
    //	}
       }
	
  }
    if(last1==2){//下货
        if(!run) {
        	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
        	//if(!dw2){
        	if(堆1下.size()>0){
        	try {
    			String state=ClientSer.getIntance().getState(SqlPro.堆垛机1状态);//获取堆垛机1的状态
    			if(state.equals(SqlPro.堆垛空闲码)){
    				//首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆1下.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 int bak=ClientSer.getIntance().getPallet(Integer.parseInt(eventID), 
			    			 fromID, Integer.parseInt(toID), 1);
			    	 if(bak==1){
			    		 run=true;
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
    				
    			}
    		   } catch (Exception e) {
    			   SqlPro.getLog().error("A下货指令发送异常：",e);
    			  
    			   e.printStackTrace();}
        	
        	}
        	last1=1;
         //  }
        	}
    	
      }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   //如果这个堆垛机没有执行的指令，那么继续 
   if(last2==1){//上货
   if(!run2) {
   	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
	 //  if(!up1){
	   if(堆2上.size()>0){
   	try {
			String state=ClientSer.getIntance().getState(SqlPro.堆垛机2状态);//获取堆垛机2的状态
			if(state.equals(SqlPro.堆垛空闲码)){
				 if(堆2上.size()>0){
			    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆2上.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 int bak= ClientSer.getIntance().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 2);
			    	if(bak==1){
			    		run2=true;
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
			      }
				
			}
		   } catch (Exception e) {
			   SqlPro.getLog().error("B上货指令发送异常：",e);
			 
			   e.printStackTrace();}
	   }
   	       last2=2;
   	   // }
      }
	
 }
   if(last2==2){//下货
       if(!run2) {
       	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
    	//   if(!dw1){
       	if(堆2下.size()>0){
       	try {
   			String state=ClientSer.getIntance().getState(SqlPro.堆垛机2状态);//获取堆垛机2的状态
   			if(state.equals(SqlPro.堆垛空闲码)){
   				//首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆2下.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	int bak= ClientSer.getIntance().getPallet(Integer.parseInt(eventID), 
			    			 fromID, Integer.parseInt(toID), 2);
			    	if(bak==1){
			    		run2=true;
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
   				
   			}
   		   } catch (Exception e) {
   			 SqlPro.getLog().error("B下货指令发送异常：",e);
			 
   			   e.printStackTrace();}
       	}
       	
       	last2=1;
      // 	}
          }
   	
     } 
   if(zl==0)
      System.out.println("指令队列启动完成");
   zl=1;
     }

//启动从14条输送线回库的流程
public void startlineAGV(){
	
	 Vector 堆1上=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 动作='输送线回流' and 请求区= '1' order by idEvent");
     Vector 堆2上=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 动作='输送线回流' and 请求区= '2' order by idEvent");
   
    boolean run=false;
    boolean run2=false;
   
    if(堆1上.size()>0){
   	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
   	 Vector up=(Vector)堆1上.get(0);
   	 if(up.get(8).equals("执行中")||up.get(8).equals("已发送")){run=true;}
     }
    
    if(堆2上.size()>0){
      	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
      	 Vector up=(Vector)堆2上.get(0);
      	 if(up.get(8).equals("执行中")||up.get(8).equals("已发送")){run2=true;}
        }
    
 if(!run) {
   	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
	 if(堆1上.size()>0){
   	try {
			String state=ClientSer.getIntance().getState(SqlPro.AGV1);//获取AGV1的状态
			if(state.equals(SqlPro.AGV空闲码)){
				 if(堆1上.size()>0){
			    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆1上.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	int bak= ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	if(bak==1){
			    	String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
			      }
				
			}
		   } catch (Exception e) {
			   SqlPro.getLog().error("A输送线回流发送异常：",e);
			  
			   e.printStackTrace();}
   	             
	   }
  
      }
	
 //////////////////////////////////////////////////////////////////////////////////////////////////////////
 
       if(!run2) {
       	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
    	   if(堆2上.size()>0){
       	try {
   			String state=ClientSer.getIntance().getState(SqlPro.AGV2);//获取AGV2的状态
   			if(state.equals(SqlPro.AGV空闲码)){
   				//首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆2上.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 int bak=ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 if(bak==1){
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
   				
   			}
   		   } catch (Exception e) {
   			   SqlPro.getLog().error("B输送线回流发送异常：",e);
			 
   			   e.printStackTrace();}
       	
    	   }
       
          }
   	if(agv==0)
       System.out.println("AGV处理程序启动完成");
      agv=1;
   
    }

  //启动升降机的型号检测
  public void startUpRFFID(){
	  
	  try {
		  if(SqlPro.autoRFIDup){
			String b=ClientSer.getIntance().getState(SqlPro.来料升);
			if(b.equals("1")){//升降台上有货物
			String tp=ClientSer.getIntance().ReadFromRffid("", 1);
			if(isRffid2!=null&&isRffid2.equals(tp))return;
		      isRffid2=tp;
				if(tp.equals(""))return;
			//先从库存托盘表里面看看有没有这个托盘
			Vector tem=SqlTool.findInVector("select 物料  from 库存托盘  where 托盘编号="+"'"+tp+"'"  );
			if(tem.size()>0){
			    Vector row=(Vector)tem.get(0);
				SqlTool.autoUpPallet(tp, row.get(0)+"", "60001",/* machineID*/"1");
				
			}else{
				String sm[]=SqlTool.getWuliaoFromLK(tp).split("!_!");//物料!_!数量
				SqlTool.autoUpPallet(tp, sm[0], "60001",/* machineID*/"1");
			}
			
			
				
			}
			
		  }
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	  
	  if(rf1==0)
	       System.out.println("来料升降机处理程序启动完成");
	      rf1=1;
     }
  
 
//启动升降机的型号检测
  public void startDownRFFID(){
	  //测试通过
	  /*  String sql="select idEvent,动作,状态,状态2,是否回大库,来源货位号,放回货位号,托盘编号,"
        		+"请求区  from 立库动作指令   where 动作='预上货' and 状态2<>'1' and 状态='完成'"
        		+" and 托盘编号='"+tp+"' order by idEvent";
        		*/
	 /* String sql3="select max(idEvent),动作,状态,状态2,是否回大库,来源货位号,放回货位号,托盘编号,"
       		+"请求区  from 立库动作指令   where   动作='输送线回流' and 状态='完成' and 状态2<>'1'"
       		+" and 托盘编号='"+tp+"'";*/
	  
	  
	    try {
			String b=ClientSer.getIntance().getState(4);
			
			
			if(b.equals("1")){//升降台上有货物
				 System.out.println("A区货物到位-----------------");
				String tp="";
			if(ClientSer.isOpenRfid){
				 System.out.println("A区货物到位isOpenRfid=true-----------------");
			  tp=ClientSer.getIntance().ReadFromRffid("", 1);
			if(!tp.equals("")){
				String sq="select 托盘编号 "
			      		+"  from 立库动作指令   where 动作 ='输送线回流' and 状态2<>'1' and 状态='完成'"
			      		+" and 请求区=1  and 托盘编号='"+tp+"'  order by idEvent";
				String hui=SqlTool.findOneRecord(sq);
				
				if(hui==null){//判断这个托盘是不是回流的托盘，如果是，那么就不加入预装指令
				   String wl=SqlTool.findOneRecord("Select 物料编码  from 托盘物料map where 托盘编号='"+tp+"'");
				if(wl!=null)
				   {
					SqlTool.manUpPallet(tp,wl,30,"0","1");
				   }
				}
			}
			
			}
				
				
			 String sql="select idEvent,托盘编号,"
			      		+"请求区  from 立库动作指令   where 动作 IN('输送线回流','预上货') and 状态2<>'1' and 状态='完成'"
			      		+" and 请求区=1  order by idEvent";
			  System.out.println("A区RFID+++++");
			
			
			String res=SqlTool.findOneRecord(sql);
			if(res!=null){
				if(!ClientSer.isOpenRfid)
				 tp=res.split("!_!")[1];
		    	System.out.println("A区RFID+++++="+tp);
			
			  if(tp.equals(""))return;
			    String back=SqlTool.exeRffid2(tp);
			
				if(back.contains("成功")){
					
				}
				
			    }
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    if(rf2==0)
	    	 System.out.println("A区AGV处理程序启动完成");
		      rf2=1;
	    
     }
  
  public void startDownRFFID2(){
	  //测试通过
	    try {
			String b=ClientSer.getIntance().getState(5);
			
			
			if(b.equals("1")){//升降台上有货物
				
				   String tp="";
			 if(ClientSer.isOpenRfid){
				   tp=ClientSer.getIntance().ReadFromRffid("", 2);
				if(!tp.equals("")){
					String sq="select 托盘编号 "
				      		+"  from 立库动作指令   where 动作 ='输送线回流' and 状态2<>'1' and 状态='完成'"
				      		+" and 请求区=2  and 托盘编号='"+tp+"'  order by idEvent";
					String hui=SqlTool.findOneRecord(sq);
					if(hui==null){
					String wl=SqlTool.findOneRecord("Select 物料编码  from 托盘物料map where 托盘编号='"+tp+"'");
					if(wl!=null)
					{
						SqlTool.manUpPallet(tp,wl,30,"0","2");
					}
					
					}
					
				}
				}
				
	            String sql="select idEvent,托盘编号,"
			      		+"请求区  from 立库动作指令   where 动作 IN('输送线回流','预上货') and 状态2<>'1' and 状态='完成'"
			      		+" and 请求区=2  order by idEvent";
				System.out.println("B区RFID2+++++");
			
				
				String res=SqlTool.findOneRecord(sql);
				if(res!=null){
					if(!ClientSer.isOpenRfid)
					 tp=res.split("!_!")[1];
			   System.out.println("B区RFID2+++++="+tp);
			     
			      if(tp.equals(""))return;
			 
			    String back=SqlTool.exeRffid2(tp);
			
				if(back.contains("成功")){
					
				}
				
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    if(rf1==0)
		       System.out.println("B区AGV处理程序启动完成");
		      rf1=1;
	    
     }
  
  
    public  void startDakuQingqiu(){
    	 //	第一步读取大库请求表格里面的内容。放到一个hashtable里面。
    	 //  第二步按着模组配套顺序请求大库的物料。
    	 //  得到大库里面有多少空位
    	//读取装载系数
    	//读取货位空位
    	 Hashtable<String,Integer>sumWL1=new Hashtable<String,Integer>();//装配区A的已有物料请求总和
    	 Hashtable<String,Integer>sumWL2=new Hashtable<String,Integer>();//装配区B的已有物料请求总和
    	String sql0="select  货位序号,托盘编号  from 货位表  where 托盘编号 IS NULL  OR  托盘编号 =''" +" and 货位序号  between 1 and 28";
    	 Vector v0=SqlTool.findInVector(sql0);
    	 int kong=v0.size()-1;
    	// int kong=2;
    	 if(kong>0){
    		 Hashtable<String,Integer>h=new Hashtable<String,Integer>();
    		 String sql4="select  物料编码,装载系数  from 通用物料  ";
    		  Vector v4=SqlTool.findInVector(sql4);
    		  for(int i=0;i<v4.size();i++){
    			  Vector row=(Vector)v4.get(i);
    			  String num= row.get(1)==null?"0.0":row.get(1).toString();
    			  h.put(row.get(0).toString(),num.equals("0.0")?10:Math.round(Float.parseFloat(num)));
    			  
    		  }
    		   
    		  Hashtable<String,Integer>temp1=new Hashtable<String,Integer>();
    		  Vector<Hashtable<String,Integer>> zl=new Vector<Hashtable<String,Integer>>();
    	    	String sql="select  物料,数量-完成数量,装配区 from 配方指令队列  where IFNULL(数量,0)-IFNULL(完成数量,0)>0 and 装配区='1' ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 50";
    		    Vector v1=SqlTool.findInVector(sql);
    		  
    		    for(int i=0;i<v1.size();i++){
    		    	Vector row=(Vector)v1.get(i);
    		    	String key=(String)row.get(0);
    		    	int val=Integer.parseInt(row.get(1).toString());
    		    	if(temp1.get(key)==null){
    		    		temp1.put(key, val);
    		    	}else{
    		    		temp1.put(key, val+temp1.get(key));
    		    	}
    		    	
    		    }
    		    //按着顺序，保证模组按着物料的顺序配齐
    		    for(int i=0;i<v1.size();i++){
    		    	Vector row=(Vector)v1.get(i);
    		    	String key=(String)row.get(0);
    		    	int val=temp1.get(key);
    		    	int tpshul=h.get(key);
    		    	if(val>0){
    		    		 Hashtable<String,Integer>t=new Hashtable<String,Integer>();
    		    		 t.put(key,tpshul);
    		    		 zl.addElement(t);
    		    		 temp1.put(key, val-tpshul);
    		    		
    		    	}
    		    	
    		    	
    		    }
    	
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   		    
    		    Hashtable<String,Integer>temp2=new Hashtable<String,Integer>();
      		    Vector<Hashtable<String,Integer>> zl2=new Vector<Hashtable<String,Integer>>(); 
    		    String sql2="select  物料,数量-完成数量,装配区 from 配方指令队列 where IFNULL(数量,0)-IFNULL(完成数量,0)>0 and 装配区='2' ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 50";
    		    Vector v2=SqlTool.findInVector(sql2);
    		    for(int i=0;i<v2.size();i++){
    		    	Vector row=(Vector)v2.get(i);
    		    	String key=(String)row.get(0);
    		    	int val=Integer.parseInt(row.get(1).toString());
    		    	if(temp2.get(key)==null){
    		    		temp2.put(key, val);
    		    	}else{
    		    		temp2.put(key, val+temp2.get(key));
    		    	}
    		    	
    		    }
    		    //按着顺序，保证模组按着物料的顺序配齐
    		    for(int i=0;i<v2.size();i++){
    		    	Vector row=(Vector)v2.get(i);
    		    	String key=(String)row.get(0);
    		    	int val=temp2.get(key);
    		    	int tpshul=h.get(key);
    		    	if(val>0){
    		    		 Hashtable<String,Integer>t=new Hashtable<String,Integer>();
    		    		 t.put(key,tpshul);
    		    		 zl2.addElement(t);
    		    		 temp2.put(key, val-tpshul);
    		    		
    		    		
    		    	}
    		    	
    		    	
    		    }
    	    
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		    
    		  
    		    //得到大库里面未处理的物料请求,并清除包含的物料请求
    		    String sql3="select  物料,SUM(数量) from 大库请求 where  状态<>'1' and 方向=1 GROUP BY 物料  ORDER BY ID  LIMIT 50"; 
    		    Vector v3=SqlTool.findInVector(sql3);
    		    for(int i=0;i<v3.size();i++){
    		    	Vector row=(Vector)v3.get(i);
    		    	String key=(String)row.get(0);
    		    	int val=Integer.parseInt(row.get(1).toString());
    		    	sumWL1.put(key, val);
    		    	
    		    }
    		    
    		    for(int i=0;i<zl.size();i++){
    		    	Hashtable<String,Integer> hh=zl.get(i);
    		          String key= hh.keys().nextElement();
    		          int val=hh.get(key);
    		          if(sumWL1.get(key)!=null){
    		          if(val<=sumWL1.get(key)){
    		        	  hh.put(key, 0);
    		        	  sumWL1.put(key, sumWL1.get(key)-val);
    		        	  
    		          }
    		          
    		          }
    		    	
    		    }
    		    System.out.println(sumWL1);
    		  ///////////////  
    		    String sql5="select  物料,SUM(数量) from 大库请求 where  状态<>'1' and 方向=2 GROUP BY 物料  ORDER BY ID  LIMIT 50"; 
    		    Vector v5=SqlTool.findInVector(sql5);
    		    for(int i=0;i<v5.size();i++){
    		    	Vector row=(Vector)v5.get(i);
    		    	String key=(String)row.get(0);
    		    	int val=Integer.parseInt(row.get(1).toString());
    		    	sumWL2.put(key, val);
    		    	
    		    }
    		  
    		    
    		    for(int i=0;i<zl2.size();i++){
    		    	Hashtable<String,Integer> hh=zl2.get(i);
    		          String key= hh.keys().nextElement();
    		          int val=hh.get(key);
    		          if(sumWL2.get(key)!=null){
    		          if(val<=sumWL2.get(key)){
    		        	  hh.put(key, 0);
    		        	  sumWL2.put(key, sumWL1.get(key)-val);
    		        	  
    		          }
    		          
    		          }
    		    	
    		    }
    		    
    		    removeNull(zl);
    		    removeNull(zl2);
    		  ///////////////////////////////////////////////////////////////////////////////
    		  //把两个区的指令合并到第一个装配区内
    		 
    		   
    		   if(zl.size()>zl2.size()){
		    	for(int i=0;i<zl2.size();i++){
		    		Hashtable<String,Integer> r=zl2.get(i);
		    		String key=r.keys().nextElement();
		    		int val=r.get(key);
		    		r.remove(key);
		    		r.put("2_"+key, val);
		    		zl.insertElementAt(zl2.get(i), i*2);
		    		
		    	}
		    }else{
		    	 int tem=zl.size(); 
		    	for(int i=0;i<tem;i++){
		    		Hashtable<String,Integer> r=zl2.get(i);
		    		String key=r.keys().nextElement();
		    		int val=r.get(key);
		    		r.remove(key);
		    		r.put("2_"+key, val);
		    		zl.insertElementAt(zl2.get(i), i*2);
		    		
		    	} 
		    	for(int i=tem;i<zl2.size();i++){
		    		Hashtable<String,Integer> r=zl2.get(i);
		    		String key=r.keys().nextElement();
		    		int val=r.get(key);
		    		r.remove(key);
		    		r.put("2_"+key, val);
		    		 zl.addElement(zl2.get(i));
		    	}
		    	     }
    		 //  System.out.println(zl);
    		
		///////////////////////////////////////////////////////////////////    	
    	//根据货位里面的可以装载的托盘数量，  
    		   Vector<String> sqlc=new Vector<String>();//物料!_!
    		    for(int i=0;i<kong;i++){
    		    	if(zl.size()>i){
    		    		Hashtable<String,Integer>tem= zl.get(i);	
    		    		String key=tem.keys().nextElement();
    		    		if(key.startsWith("2_")){
    		    			String sq="insert into 大库请求 (物料,数量,方向 ) values("+"'"+key.replace("2_", "")+"',"
    		    					+"'"+tem.get(key)+"',"+"'"+2+"')";
    		    			sqlc.addElement(sq);
    		    			
    		    		  }else{
    		    			  
    		    			  String sq="insert into 大库请求 (物料,数量,方向 ) values("+"'"+key+"',"
      		    					+"'"+tem.get(key)+"',"+"'"+1+"')";	
    		    			  sqlc.addElement(sq);
    		    		  }
    		    		
    		    	  }
    		    	
    		      }
    		    String s[]=new String[sqlc.size()];
    		    for(int i=0;i<sqlc.size();i++){
    		    	s[i]=sqlc.get(i);
    		    	
    		    }
    		    SqlTool.insert(s);
    		   //System.out.println(sqlc);
    	 }
    	
    }
    
    private  void removeNull( Vector<Hashtable<String,Integer>> zl){
    	   if(zl==null)return;
    	   for(int i=0;i<zl.size();i++){
    		   Hashtable<String,Integer> h=zl.get(i);
    		   String key=h.keys().nextElement();
    		   if(h.get(key)==0){
    			   zl.remove(i);
    			   removeNull(zl);
    		   }
    		   
    		   
    	   }
    	
    }
    Properties pro;
    public void statPaletToPLC(int machineID){
    	try{
    		alai.GDT.Resint[] bak=ClientSer.getIntance().getReturnPlc("D11998", 1, 16, machineID);
    		if(bak[0].getResInt()==1){
    			if(pro==null){pro=SqlPro.loadProperties(SqlPro.class.getResource("conf.pro").getFile());}
    			if(pro!=null){
    				String huoweiA[]=new String[]{"514","512","510","508","506","504","502"};
    				String huoweiB[]=new String[]{"614","612","610","608","606","604","602"};
    				if(machineID==1){
    			    for(int r=0;r<huoweiA.length;r++){
    					//货位的起始地址，标注有没有初始化，比如货位的起始地址是D200，那么D200就标注为有没有初始化。
    			   String palet=SqlTool.findOneRecord("select 托盘编号 from 库存托盘  where  货位号='"+huoweiA[r]+"'");
    			   String val=SqlTool.findOneRecord("select address from 库存托盘  where  托盘编号='"+palet+"'");
    			   if(val!=null){
    				   String sm[]=val.split(",");
    				   int length=sm.length+1;
    				   int to[]=new int[length];
    				   to[0]=1;
    				   for(int i=1;i<to.length;i++){
    					     to[i]=Integer.parseInt(sm[i-1].split("=")[1]);
    					   
    				     }
    				   
    				   ClientSer.getIntance().writeSirIntToCTR(pro.getProperty(huoweiA[r]), length, to,  machineID)  ;
    				   
    			   }
    			   
    					
    				}//
    			    
    				}
    				
    				if(machineID==2){
    					  for(int r=0;r<huoweiB.length;r++){
    	    					//货位的起始地址，标注有没有初始化，比如货位的起始地址是D200，那么D200就标注为有没有初始化。
    	    			   String palet=SqlTool.findOneRecord("select 托盘编号 from 库存托盘  where  货位号='"+huoweiB[r]+"'");
    	    			   String val=SqlTool.findOneRecord("select address from 库存托盘  where  托盘编号='"+palet+"'");
    	    			   if(val!=null){
    	    				   String sm[]=val.split(",");
    	    				   int length=sm.length+1;
    	    				   int to[]=new int[length];
    	    				   to[0]=1;
    	    				   for(int i=1;i<to.length;i++){
    	    					     to[i]=Integer.parseInt(sm[i-1].split("=")[1]);
    	    					   
    	    				     }
    	    				   
    	    				   ClientSer.getIntance().writeSirIntToCTR(pro.getProperty(huoweiB[r]), length, to,  machineID)  ;
    	    				   
    	    			   }
    	    			   
    	    					
    	    				}//	
    					
    					
    				}
    			    
    			}
    		    
    			 ClientSer.getIntance().writeSirIntToCTR("D11998", 10, new int[]{0},  machineID)  ;
    			
    		}
    		
    	}catch(Exception ex){}
    	
    }
    
    int startLine1=0; int startLine2=0;
    int huojia1=0; int huojia2=0;
   public void testLiKu(int machinID){
	       try{
	    	   if(machinID==1){
	    		   
	    			  String tem2=SqlTool.findOneRecord("select  托盘编号,物料,货位号,方向  from 库存托盘"
	    			  		+ "   where  货位号 in (514,512,510,508,506,504,502) and  托盘编号=8888 and 方向=1");   
	    			    if(tem2!=null){
	    			    	//如果这个托盘在输送线上，那么就是输送线回流
	    			    	String sm[]=tem2.split("!_!");
	    			    	String tem=SqlTool.findOneRecord("select  货位序号,托盘编号  from 货位表   where  货位序号 BETWEEN 1 AND 28  AND (托盘编号 IS NULL OR  托盘编号='')  and 货位序号>'"+huojia1+"' ORDER BY  货位序号 " );   
	    			    	  if(tem!=null){
	    			    		  String sm2[]=tem.split("!_!");
	    			    		  String b=SqlTool.add动作指令(sm[0], sm[2], sm2[0], "输送线回流", 0, 1+"");
	    			    		  if(b.contains("成功")){
	    			    		  huojia1++;}
	    			    		  if(huojia1==28)huojia1=0;
	    			    	     }
	    			            
	    			         }else{
	    			          //若果托盘不在输送线上，那么就是下货
	    			        	 tem2=SqlTool.findOneRecord("select  托盘编号,物料,货位号,方向  from 库存托盘"
	    			        		  		+ "   where  货位号 between 1 and 28 and  托盘编号=8888 and 方向=1");  
	    			        	 if(tem2!=null){
	    			        		 String sm[]=tem2.split("!_!");
	    			               Vector v=SqlTool.findInVector("select  货位序号,托盘编号  from 货位表   where  货位序号  between 501 and 514 AND 货位序号>"+(500+startLine1*2)+"  ORDER BY  货位序号 " ); 
	    			               int maxCols=7-startLine1;
	    			               
	    			               for(int i=0;i<maxCols;i++){
	    			            	   Vector row1=(Vector)v.get(i*2); //奇数位
	    			            	   Vector row2=(Vector)v.get(i*2+1); //偶数位
	    			            	 String tp1=(row1.get(1)==null?"": row1.get(1).toString());
	    			            	 String tp2=(row2.get(1)==null?"": row2.get(1).toString());
	    			            	 if(tp1.equals("")&&tp2.equals("")){
	    			            		 String b=SqlTool.add动作指令(sm[0], sm[2], row1.get(0).toString(), "下货", 0, 1+""); 
	    			            		 if(b.contains("成功")){
	    			            			/* if(row1.get(0).toString().equals("501")) startLine1=1;
	    			            			 if(row1.get(0).toString().equals("503")) startLine1=2;
	    			            			 if(row1.get(0).toString().equals("505")) startLine1=3;
	    			            			 if(row1.get(0).toString().equals("507")) startLine1=4;
	    			            			 if(row1.get(0).toString().equals("509")) startLine1=5;
	    			            			 if(row1.get(0).toString().equals("511")) startLine1=6;
	    			            			 if(row1.get(0).toString().equals("513")) startLine1=7;*/
	    			            		   startLine1++;
	    			            		 System.out.println("startLine1="+startLine1);
	    			            		 }
	    			            		 if(startLine1==7)startLine1=0;
	    			            		  break;
	    			            	 }
	    			                 }
	    			        	   }
	    			        	 
	    			         }

	    		          } 
	    	   
	    	  /////////////////////////////////////////////////////////////////////////////////
	    	   
	    	   if(machinID==2){
	    		   
	    			  String tem2=SqlTool.findOneRecord("select  托盘编号,物料,货位号,方向  from 库存托盘"
	    			  		+ "   where  货位号 in (614,612,610,608,606,604,602) and  托盘编号=8888 and 方向=2");   
	    			    if(tem2!=null){
	    			    	//如果这个托盘在输送线上，那么就是输送线回流
	    			    	String sm[]=tem2.split("!_!");
	    			    	String tem=SqlTool.findOneRecord("select  货位序号,托盘编号  from 货位表   where  货位序号 BETWEEN 1 AND 28  AND (托盘编号 IS NULL OR  托盘编号='')  and 货位序号>'"+huojia2+"' ORDER BY  货位序号 " );   
	    			    	  if(tem!=null){
	    			    		  String sm2[]=tem.split("!_!");
	    			    		  String b=SqlTool.add动作指令(sm[0], sm[2], sm2[0], "输送线回流", 0, 2+"");
	    			    		  if(b.contains("成功")){
	    			    		  huojia2++;}
	    			    		  if(huojia2==28)huojia2=0;
	    			    	     }
	    			            
	    			         }else{
	    			          //若果托盘不在输送线上，那么就是下货
	    			        	 tem2=SqlTool.findOneRecord("select  托盘编号,物料,货位号,方向  from 库存托盘"
	    			        		  		+ "   where  货位号 between 1 and 28 and  托盘编号=8888 and 方向=2");  
	    			        	 if(tem2!=null){
	    			        		 String sm[]=tem2.split("!_!");
	    			               Vector v=SqlTool.findInVector("select  货位序号,托盘编号  from 货位表   where  货位序号  between 601 and 614 AND 货位序号>"+(600+startLine2*2)+"  ORDER BY  货位序号 " ); 
	    			               int maxCols=7-startLine2;
	    			               
	    			               for(int i=0;i<maxCols;i++){
	    			            	   Vector row1=(Vector)v.get(i*2); //奇数位
	    			            	   Vector row2=(Vector)v.get(i*2+1); //偶数位
	    			            	 String tp1=(row1.get(1)==null?"": row1.get(1).toString());
	    			            	 String tp2=(row2.get(1)==null?"": row2.get(1).toString());
	    			            	 if(tp1.equals("")&&tp2.equals("")){
	    			            		 String b=SqlTool.add动作指令(sm[0], sm[2], row1.get(0).toString(), "下货", 0, 2+""); 
	    			            		 if(b.contains("成功")){
	    			            		 startLine2++;
	    			            		 System.out.println("startLine2="+startLine2);
	    			            		 }
	    			            		 if(startLine2==7)startLine2=0;
	    			            		  break;
	    			            	 }
	    			                 }
	    			        	   }
	    			        	 
	    			         }

	    		               } 
	    			    	      
	         }catch(Exception ex){ex.printStackTrace();}
	   
       }
	
}
