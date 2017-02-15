package alai.znyk.kufang;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import alai.znyk.common.ClientSer;
import alai.znyk.common.SqlPro;
import alai.znyk.server.SqlTool;

public class KuFang {
	int zl=0;
	int agv=0;
	
	int rf1=0;
	int rf2=0;
	int line=0;
	Hashtable<String,String> isRffid1=new Hashtable<String,String>();
	Hashtable<String,String> isRffid2=new Hashtable<String,String>();
	public static void main(String ss[]){
		
		new KuFang();
	}
	public KuFang(){
		
		new Thread(){
			public void run(){
			try{
				while(true){
					startLine();
				    Thread.sleep(100);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
			try{
				while(true){
					 start堆垛机指令();
				Thread.sleep(100);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
			try{
				while(true){
					startlineAGV();
				Thread.sleep(100);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
			try{
				while(true){
					startUpRFFID();
				Thread.sleep(100);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
		
		new Thread(){
			public void run(){
			try{
				while(true){
					startDownRFFID();
				Thread.sleep(100);
				
				}
			}catch(Exception ex){}
			
			}
			
		}.start();
	}
	
//更新托盘在7条输送线上的位置	
public void startLine(){
	//测试通过
		
   try{
	  String ss= ClientSer.getIntance().getState(SqlPro.A区输送线);
	  String sm[]=ss.split("\\|");
	String sql1= "select  货位序号,托盘编号   from 货位表  where  货位序号  between 501 and 514 order by 货位序号";
	
	Vector<Vector> tem1=SqlTool.findInVector(sql1);
	
   
	for(int r=0;r<tem1.size()/2;r++){
		Vector line1=tem1.get(r*2);
		Vector line2=tem1.get(r*2+1);
		String firstTP=line1.get(1)==null?"":line1.get(1).toString();
		String secTP=line2.get(1)==null?"":line2.get(1).toString();
		if(secTP.equals("")){
			if(sm[r*2+1].split("=")[1].equals("0")){
				//System.out.println((r*2+1)+"="+sm[r*2+1]+"="+ss);
				String fromID=line1.get(0)+"";
				String toID=line2.get(0)+"";
				SqlTool.update7Line(firstTP, fromID, toID);
			}
			
		 }
		
	 } }catch(Exception ex){ex.printStackTrace();}
   
   
   try{
	
	 String ss2= ClientSer.getIntance().getState(SqlPro.B区输送线);
	 String sm2[]=ss2.split("\\|");
	 String sql2= "select  货位序号,托盘编号   from 货位表  where  货位序号  between 601 and 614 order by 货位序号";
	Vector<Vector> tem2=SqlTool.findInVector(sql2);
	for(int r=0;r<tem2.size()/2;r++){
		Vector line1=tem2.get(r*2);
		Vector line2=tem2.get(r*2+1);
		String firstTP=line1.get(1)==null?"":line1.get(1).toString();
		String secTP=line2.get(1)==null?"":line2.get(1).toString();
		if(secTP.equals("")){
			if(sm2[r*2+1].split("=")[1].equals("0")){
				String fromID=line1.get(0)+"";
				String toID=line2.get(0)+"";
				SqlTool.update7Line(firstTP, fromID, toID);
			}
			
		 }
		
	 } }catch(Exception ex){ex.printStackTrace();}
	
	if(line==0)
     System.out.println("缓存货位与上料位自动更新启动完成");
   
	line=1;
		
	
}

public void start堆垛机指令(){
	
	 Vector 堆1上=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 动作='上货' and 请求区= '1' order by idEvent");
     Vector 堆1下=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 动作='下货' and 请求区= '1' order by idEvent");
    
     boolean run=false;
     int last1=1;
     if(堆1上.size()>0){
    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
    	 Vector up=(Vector)堆1上.get(0);
    	 if(up.get(8).equals("执行中")||up.get(8).equals("已发送")){run=true;}
      }
     
     if(堆1下.size()>0){
    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
    	 Vector down=(Vector)堆1下.get(0);
    	 if(down.get(8).equals("执行中")||down.get(8).equals("已发送")){run=true;}
      
     }
    //如果这个堆垛机没有执行的指令，那么继续 
    if(last1==1){//上货
    if(!run) {
    	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
    	
    	try {
			String state=ClientSer.getIntance().getState(SqlPro.堆垛机1状态);//获取堆垛机1的状态
			if(state.equals(SqlPro.堆垛空闲码)){
				 if(堆1上.size()>0){
			    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆1上.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 1);
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {e.printStackTrace();}
    	last1=2;
       }
	
  }
    if(last1==2){//下货
        if(!run) {
        	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
        	
        	try {
    			String state=ClientSer.getIntance().getState(SqlPro.堆垛机1状态);//获取堆垛机1的状态
    			if(state.equals(SqlPro.堆垛空闲码)){
    				//首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆1下.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().getPallet(Integer.parseInt(eventID), 
			    			 fromID, Integer.parseInt(toID), 1);
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
    				
    			}
    		   } catch (Exception e) {e.printStackTrace();}
        	last1=1;
           }
    	
      }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    Vector 堆2上=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 动作='上货' and 请求区= '2' order by idEvent");
    Vector 堆2下=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 动作='下货' and 请求区= '2' order by idEvent");
    
    boolean run2=false;
    int last2=1;
    if(堆2上.size()>0){
   	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
   	 Vector up=(Vector)堆2上.get(0);
   	 if(up.get(8).equals("执行中")||up.get(8).equals("已发送")){run2=true;}
     }
    
    if(堆2下.size()>0){
   	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
   	 Vector down=(Vector)堆2下.get(0);
   	 if(down.get(8).equals("执行中")||down.get(8).equals("已发送")){run2=true;}
     
    }
   //如果这个堆垛机没有执行的指令，那么继续 
   if(last2==1){//上货
   if(!run2) {
   	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
   	
   	try {
			String state=ClientSer.getIntance().getState(SqlPro.堆垛机2状态);//获取堆垛机2的状态
			if(state.equals(SqlPro.堆垛空闲码)){
				 if(堆2上.size()>0){
			    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆2上.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 2);
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {e.printStackTrace();}
   	last2=2;
      }
	
 }
   if(last2==2){//下货
       if(!run2) {
       	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
       	
       	try {
   			String state=ClientSer.getIntance().getState(SqlPro.堆垛机2状态);//获取堆垛机2的状态
   			if(state.equals(SqlPro.AGV空闲码)){
   				//首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆2下.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().getPallet(Integer.parseInt(eventID), 
			    			 fromID, Integer.parseInt(toID), 2);
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
   				
   			}
   		   } catch (Exception e) {e.printStackTrace();}
       	last2=1;
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
   	
   	try {
			String state=ClientSer.getIntance().getState(SqlPro.AGV1);//获取AGV1的状态
			if(state.equals(SqlPro.AGV空闲码)){
				 if(堆1上.size()>0){
			    	 //首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆1上.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {e.printStackTrace();}
  
      }
	
 //////////////////////////////////////////////////////////////////////////////////////////////////////////
 
       if(!run2) {
       	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
       	
       	try {
   			String state=ClientSer.getIntance().getState(SqlPro.AGV2);//获取AGV2的状态
   			if(state.equals(SqlPro.AGV空闲码)){
   				//首先判断当前指令有没有在执行中，如果有，那么就不在发送
			    	 Vector up=(Vector)堆2上.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
   				
   			}
   		   } catch (Exception e) {e.printStackTrace();}
       
          }
   	if(agv==0)
       System.out.println("AGV处理程序启动完成");
      agv=1;
   
    }

  //启动升降机的型号检测
  public void startUpRFFID(){
	  try {
			String b=ClientSer.getIntance().getState(SqlPro.来料升);
			if(b.equals("1")){//升降台上有货物
			String tp=ClientSer.getIntance().ReadFromRffid("", 1);
			if(isRffid2.get(tp)!=null)return;
			isRffid2.put(tp, tp);
			//先从库存托盘表里面看看有没有这个托盘
			Vector tem=SqlTool.findInVector("select 物料  from 库存托盘  where 托盘编号="+"'"+tp+"'"  );
			if(tem.size()>0){
			    Vector row=(Vector)tem.get(0);
				SqlTool.autoUpPallet(tp, row.get(0)+"", "60001",/* machineID*/"1");
				
			}else{
				String sm[]=SqlTool.getWuliaoFromLK(tp).split("!_!");//物料=数量
				SqlTool.autoUpPallet(tp, sm[0], "60001",/* machineID*/"1");
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
	    try {
			String b=ClientSer.getIntance().getState(5);
			
			
			if(b.equals("1")){//升降台上有货物
			String tp=ClientSer.getIntance().ReadFromRffid("", 2);
			if(isRffid1.get(tp)!=null)return;
			String back=SqlTool.exeRffid2(tp);
			isRffid1.put(tp, "");
				if(back.contains("成功")){}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    if(rf2==0)
		       System.out.println("去料升降机处理程序启动完成");
		      rf2=1;
	    
     }
  
  
	
}
