package alai.znyk.test;

import java.lang.reflect.InvocationTargetException;

import alai.znyk.common.ClientSer;
import alai.znyk.plc.PLC;
import alai.znyk.server.SqlTool;

public class Test2 {
	String lock1="";
	public void print1(int i){
	                 
					int s=0;
					synchronized(lock1){
						System.out.println("进入"+i+"_"+Thread.currentThread());
					 while(s<100){
					//System.out.println("1");
				      	s++;
				      	try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }
					 System.out.println("end"+i+"_"+Thread.currentThread());
					}
					
					
	
		
	}
		
	public void print2(){
		
		synchronized(lock1){
		     print1(2);
		     System.out.println("end------------");
		  }
	
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String back=SqlTool.add动作指令("1", "60001", "501", "上货", 0, "1");
		 // String back=SqlTool.add动作指令("100", "25", "60002", "下货", 1, "1");
		 // System.out.println(back);
		//String sql="update 库存托盘  set 货位号=NULL,方向=NULL where 托盘编号="+"'"+1+"'";
		 
		//String back=SqlTool.insert(new String[]{sql});
		//System.out.println(back);
		
		//String s="501=1|502=1|503=1|504=0|505=1|506=0|507=1|508=0|509=1|510=0|511=1|512=0|513=1|514=0";
		//String sm[]=s.split("=");
		//for(int i=0;i<sm.length;i++)
		//System.out.println(sm[i]);
		
		//String st="1";
		//System.out.println(st.equals(1+""));
		//System.out.println(0b1000);
		//ClientSer.getIntance();
		//String nam="1";Integer i=1;
		//System.out.println(i.toString());
		 
		//System.out.println(nam.equals(i+""+""));
		 
			 System.out.println(PLC.getIntance().ST2_1.firstST.getMap());
		//System.out.println(PLC.getIntance().ST2_1.firstST.setValueByName("需求数量", "1","0"));
		
		try{
		//	String s=ClientSer.getIntance().ReadFromRffid("", 1);
		  // System.out.println(s);
			Test2 t=new Test2();
			new Thread(){public void run(){
				//t.print1(1);
				
			}}.start();
			
			new Thread(){public void run(){
				//t.print2();
				
			}}.start();
			
		}catch(Exception ex){}
		
	}

}
