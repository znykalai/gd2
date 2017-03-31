package alai.znyk.kufang;

import java.rmi.RemoteException;
import java.util.Hashtable;
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
					startLine();
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
					startUpRFFID();
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
		
	 } }catch(Exception ex){
		 SqlPro.getLog().error("A区输送线是否有托盘："+ex.getMessage());
		 ex.printStackTrace();}
   
   
   try{
	
	 String ss2= ClientSer.getIntance().getState(SqlPro.B区输送线);
	 System.out.println(ss2);
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
		
	 } }catch(Exception ex){
		 SqlPro.getLog().error("B区输送线是否有托盘："+ex.getMessage());
		 ex.printStackTrace();}
	
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
    	if(堆1上.size()>0){
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
    	}
    	last1=2;
       }
	
  }
    if(last1==2){//下货
        if(!run) {
        	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
        	if(堆1下.size()>0){
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
        	
        	}
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
			    	 ClientSer.getIntance().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 2);
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {e.printStackTrace();}
	   }
   	last2=2;
      }
	
 }
   if(last2==2){//下货
       if(!run2) {
       	//如果没有运行中的指令,那么就优先上料，上完料了在看看有没有下货的指令，如果有运行下货
       	if(堆2下.size()>0){
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
       	}
       	
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
			    	 ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {e.printStackTrace();}
   	             
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
			    	 ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 String sql2="update 立库动作指令  set 状态='已发送',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
   				
   			}
   		   } catch (Exception e) {e.printStackTrace();}
       	
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
	    try {
			String b=ClientSer.getIntance().getState(5);
			
			
			if(b.equals("1")){//升降台上有货物
				System.out.println("RFID2+++++");
			String tp=ClientSer.getIntance().ReadFromRffid("", 2);
			System.out.println("RFID2+++++="+tp);
			if(isRffid1!=null&&isRffid1.equals(tp))return;
			 isRffid1=tp;
			if(tp.equals(""))return;
			 
			String back=SqlTool.exeRffid2(tp);
			
				if(back.contains("成功")){
					
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    if(rf2==0)
		       System.out.println("去料升降机处理程序启动完成");
		      rf2=1;
	    
     }
  
    public  void startDakuQingqiu(){
    	 //	第一步读取大库请求表格里面的内容。放到一个hashtable里面。
    	 //  第二步按着模组配套顺序请求大库的物料。
    	 // 得到大库里面有多少空位
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
    	    	String sql="select  物料,数量-完成数量,装配区 from 配方指令队列 where IFNULL(数量,0)-IFNULL(完成数量,0)>0 and 装配区='1' ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 50";
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
    		    System.out.println(sqlc);
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
	
}
