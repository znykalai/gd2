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
					if(SqlPro.isָ�����)
					 start�Ѷ��ָ��();
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
				System.out.println("������ָ������");
				while(true){
					if(SqlPro.is������)
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
			System.out.println("�ⷿ��ʼ��---------------");
			
			return INSTANCE;
		}
		
	}
	
//����������7���������ϵ�λ��	
public void startLine(int machineID){
	//����ͨ��
	if(machineID==1){	
   try{
	  String ss= ClientSer.getIntance().getState(10);
	  if(ss==null||ss.equals("-2")){
	  System.out.println("startLine �Ͽ���ȡ��ʱ,״̬��="+ss);
	  return;}
	  if(ss==null||ss.equals("-1")){
		  System.out.println("startLine ����Ͽ�,״̬��="+ss);
		  return;}
	  String sm[]=ss.split("\\|");
	  if(sm.length<2){
		  SqlPro.getLog().error("startLine ����Ͽ�,״̬��=A����ȡ����״̬����");
		  System.out.println("startLine ����Ͽ�,״̬��=A����ȡ����״̬����");
		  return;}
	  
	String sql1= "select  ��λ���,���̱��   from ��λ��  where  ��λ���  between 501 and 514 order by ��λ���";
	
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
				 * ��PLC����д��PLC��ַ
				 * **/
				if(!firstTP.equals("")){
					SqlTool.update7Line(firstTP, fromID, toID);
					// String sql2="select ���̱��  from ��λ��   where  ��λ���='"+toID+"'";	
				  String wuliao= SqlTool.findOneRecord("select ����  from �������  where ���̱��="+"'"+firstTP+"'");
				  String leixing=SqlTool.findOneRecord("select ����  from ͨ������  where ���ϱ���="+"'"+wuliao+"'");
				  SqlTool.clearValueForPalet(toID, 1);
				  SqlTool.WriteAddresInPaletToPLC(leixing, firstTP, toID, 1);
					
				  }
			}
			
		 }
		
	 } }catch(Exception ex){
		 SqlPro.getLog().error("A���������Ƿ������̣�",ex);
		
		 ex.printStackTrace();}
   
	}
   
	if(machineID==2){
   try{
	
	 String ss2= ClientSer.getIntance().getState(11);
	  if(ss2==null||ss2.equals("-2")){
		  System.out.println("startLine �Ͽ���ȡ��ʱ,״̬��="+ss2);
		  return;}
		  if(ss2==null||ss2.equals("-1")){
		  System.out.println("startLine ����Ͽ�,״̬��="+ss2);
			  return;}
	 
	 String sm2[]=ss2.split("\\|"); 
	  if(sm2.length<2){
		  SqlPro.getLog().error("startLine ����Ͽ�,״̬��=B����ȡ����״̬����");
		  System.out.println("startLine ����Ͽ�,״̬��=B����ȡ����״̬����");
		  return;}
	 String sql2= "select  ��λ���,���̱��   from ��λ��  where  ��λ���  between 601 and 614 order by ��λ���";
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
				 * ��PLC����д��PLC��ַ
				 * **/
				if(!firstTP.equals("")){
					System.out.println("update7Line,�������̴ӵ�1λ��ڶ�λ�ƶ����ڷ���startLine��B��");
					SqlTool.update7Line(firstTP, fromID, toID);
					System.out.println("update7Line,ִ����ɣ��ڷ���startLine��B��");
					// String sql2="select ���̱��  from ��λ��   where  ��λ���='"+toID+"'";	
				  String wuliao= SqlTool.findOneRecord("select ����  from �������  where ���̱��="+"'"+firstTP+"'");
				  String leixing=SqlTool.findOneRecord("select ����  from ͨ������  where ���ϱ���="+"'"+wuliao+"'");
				  SqlTool.clearValueForPalet(toID, 2);
				  SqlTool.WriteAddresInPaletToPLC(leixing, firstTP, toID, 2);
				  System.out.println("update7Line,д��PLC�����ַ �ڷ���startLine���"); 
				  }
			}
			
		 }
		
	 } 
	
   }catch(Exception ex){
		 SqlPro.getLog().error("B���������Ƿ������̣�",ex);
		
		 ex.printStackTrace();}
   
	}
	
	if(line==0)
     System.out.println("�����λ������λ�Զ������������");
   
	line=1;
		
	
}

public void start�Ѷ��ָ��(){
	
	 Vector ��1��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�ϻ�' and ������= '1' order by idEvent");
     Vector ��1��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�»�' and ������= '1' order by idEvent");
    
     boolean run=false;
     boolean run2=false;
     
     boolean up1=false;
     boolean up2=false;
     boolean dw1=false;
     boolean dw2=false;
     int last1=1;
     if(��1��.size()>0){
    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
    	 Vector up=(Vector)��1��.get(0);
    	 if(up.get(8).equals("ִ����")||up.get(8).equals("�ѷ���")){run=true;up1=true;}
      }
     
     if(��1��.size()>0){
    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
    	 Vector down=(Vector)��1��.get(0);
    	 if(down.get(8).equals("ִ����")||down.get(8).equals("�ѷ���")){run=true;dw1=true;}
      
     }
     
     
     Vector ��2��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�ϻ�' and ������= '2' order by idEvent");
     Vector ��2��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�»�' and ������= '2' order by idEvent");
     
    
     int last2=1;
     if(��2��.size()>0){
    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
    	 Vector up=(Vector)��2��.get(0);
    	 if(up.get(8).equals("ִ����")||up.get(8).equals("�ѷ���")){run2=true;up2=true;}
      }
     
     if(��2��.size()>0){
    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
    	 Vector down=(Vector)��2��.get(0);
    	 if(down.get(8).equals("ִ����")||down.get(8).equals("�ѷ���")){run2=true;dw2=true;}
      
     }
     
    //�������Ѷ��û��ִ�е�ָ���ô���� 
    if(last1==1){//�ϻ�
    if(!run) {
      //	if(!up2){
    	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
    	if(��1��.size()>0){
    	try {
    		//����ʱ����������������ж�
    		
			String state=ClientSer.getIntance().getState(SqlPro.�Ѷ��1״̬);//��ȡ�Ѷ��1��״̬
			if(state.equals(SqlPro.�Ѷ������)){
				 if(��1��.size()>0){
			    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��1��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	int bak= ClientSer.getIntance().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 1);
			    	if(bak==1){
			    		run=true;
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
			      }
				
			}
		   } catch (Exception e) {
			   SqlPro.getLog().error("A�ϻ�ָ����쳣��",e);
			  
			   e.printStackTrace();}
    	
    	
    	}
    	last1=2;
    //	}
       }
	
  }
    if(last1==2){//�»�
        if(!run) {
        	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
        	//if(!dw2){
        	if(��1��.size()>0){
        	try {
    			String state=ClientSer.getIntance().getState(SqlPro.�Ѷ��1״̬);//��ȡ�Ѷ��1��״̬
    			if(state.equals(SqlPro.�Ѷ������)){
    				//�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��1��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 int bak=ClientSer.getIntance().getPallet(Integer.parseInt(eventID), 
			    			 fromID, Integer.parseInt(toID), 1);
			    	 if(bak==1){
			    		 run=true;
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
    				
    			}
    		   } catch (Exception e) {
    			   SqlPro.getLog().error("A�»�ָ����쳣��",e);
    			  
    			   e.printStackTrace();}
        	
        	}
        	last1=1;
         //  }
        	}
    	
      }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   //�������Ѷ��û��ִ�е�ָ���ô���� 
   if(last2==1){//�ϻ�
   if(!run2) {
   	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
	 //  if(!up1){
	   if(��2��.size()>0){
   	try {
			String state=ClientSer.getIntance().getState(SqlPro.�Ѷ��2״̬);//��ȡ�Ѷ��2��״̬
			if(state.equals(SqlPro.�Ѷ������)){
				 if(��2��.size()>0){
			    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��2��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 int bak= ClientSer.getIntance().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 2);
			    	if(bak==1){
			    		run2=true;
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
			      }
				
			}
		   } catch (Exception e) {
			   SqlPro.getLog().error("B�ϻ�ָ����쳣��",e);
			 
			   e.printStackTrace();}
	   }
   	       last2=2;
   	   // }
      }
	
 }
   if(last2==2){//�»�
       if(!run2) {
       	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
    	//   if(!dw1){
       	if(��2��.size()>0){
       	try {
   			String state=ClientSer.getIntance().getState(SqlPro.�Ѷ��2״̬);//��ȡ�Ѷ��2��״̬
   			if(state.equals(SqlPro.�Ѷ������)){
   				//�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��2��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	int bak= ClientSer.getIntance().getPallet(Integer.parseInt(eventID), 
			    			 fromID, Integer.parseInt(toID), 2);
			    	if(bak==1){
			    		run2=true;
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
   				
   			}
   		   } catch (Exception e) {
   			 SqlPro.getLog().error("B�»�ָ����쳣��",e);
			 
   			   e.printStackTrace();}
       	}
       	
       	last2=1;
      // 	}
          }
   	
     } 
   if(zl==0)
      System.out.println("ָ������������");
   zl=1;
     }

//������14�������߻ؿ������
public void startlineAGV(){
	
	 Vector ��1��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�����߻���' and ������= '1' order by idEvent");
     Vector ��2��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�����߻���' and ������= '2' order by idEvent");
   
    boolean run=false;
    boolean run2=false;
   
    if(��1��.size()>0){
   	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
   	 Vector up=(Vector)��1��.get(0);
   	 if(up.get(8).equals("ִ����")||up.get(8).equals("�ѷ���")){run=true;}
     }
    
    if(��2��.size()>0){
      	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
      	 Vector up=(Vector)��2��.get(0);
      	 if(up.get(8).equals("ִ����")||up.get(8).equals("�ѷ���")){run2=true;}
        }
    
 if(!run) {
   	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
	 if(��1��.size()>0){
   	try {
			String state=ClientSer.getIntance().getState(SqlPro.AGV1);//��ȡAGV1��״̬
			if(state.equals(SqlPro.AGV������)){
				 if(��1��.size()>0){
			    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��1��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	int bak= ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	if(bak==1){
			    	String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
			      }
				
			}
		   } catch (Exception e) {
			   SqlPro.getLog().error("A�����߻��������쳣��",e);
			  
			   e.printStackTrace();}
   	             
	   }
  
      }
	
 //////////////////////////////////////////////////////////////////////////////////////////////////////////
 
       if(!run2) {
       	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
    	   if(��2��.size()>0){
       	try {
   			String state=ClientSer.getIntance().getState(SqlPro.AGV2);//��ȡAGV2��״̬
   			if(state.equals(SqlPro.AGV������)){
   				//�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��2��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 int bak=ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 if(bak==1){
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			    	 }
   				
   			}
   		   } catch (Exception e) {
   			   SqlPro.getLog().error("B�����߻��������쳣��",e);
			 
   			   e.printStackTrace();}
       	
    	   }
       
          }
   	if(agv==0)
       System.out.println("AGV��������������");
      agv=1;
   
    }

  //�������������ͺż��
  public void startUpRFFID(){
	  
	  try {
		  if(SqlPro.autoRFIDup){
			String b=ClientSer.getIntance().getState(SqlPro.������);
			if(b.equals("1")){//����̨���л���
			String tp=ClientSer.getIntance().ReadFromRffid("", 1);
			if(isRffid2!=null&&isRffid2.equals(tp))return;
		      isRffid2=tp;
				if(tp.equals(""))return;
			//�ȴӿ�����̱����濴����û���������
			Vector tem=SqlTool.findInVector("select ����  from �������  where ���̱��="+"'"+tp+"'"  );
			if(tem.size()>0){
			    Vector row=(Vector)tem.get(0);
				SqlTool.autoUpPallet(tp, row.get(0)+"", "60001",/* machineID*/"1");
				
			}else{
				String sm[]=SqlTool.getWuliaoFromLK(tp).split("!_!");//����!_!����
				SqlTool.autoUpPallet(tp, sm[0], "60001",/* machineID*/"1");
			}
			
			
				
			}
			
		  }
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	  
	  if(rf1==0)
	       System.out.println("������������������������");
	      rf1=1;
     }
  
 
//�������������ͺż��
  public void startDownRFFID(){
	  //����ͨ��
	  /*  String sql="select idEvent,����,״̬,״̬2,�Ƿ�ش��,��Դ��λ��,�Żػ�λ��,���̱��,"
        		+"������  from ���⶯��ָ��   where ����='Ԥ�ϻ�' and ״̬2<>'1' and ״̬='���'"
        		+" and ���̱��='"+tp+"' order by idEvent";
        		*/
	 /* String sql3="select max(idEvent),����,״̬,״̬2,�Ƿ�ش��,��Դ��λ��,�Żػ�λ��,���̱��,"
       		+"������  from ���⶯��ָ��   where   ����='�����߻���' and ״̬='���' and ״̬2<>'1'"
       		+" and ���̱��='"+tp+"'";*/
	  
	  
	    try {
			String b=ClientSer.getIntance().getState(4);
			
			
			if(b.equals("1")){//����̨���л���
				 System.out.println("A�����ﵽλ-----------------");
				String tp="";
			if(ClientSer.isOpenRfid){
				 System.out.println("A�����ﵽλisOpenRfid=true-----------------");
			  tp=ClientSer.getIntance().ReadFromRffid("", 1);
			if(!tp.equals("")){
				String sq="select ���̱�� "
			      		+"  from ���⶯��ָ��   where ���� ='�����߻���' and ״̬2<>'1' and ״̬='���'"
			      		+" and ������=1  and ���̱��='"+tp+"'  order by idEvent";
				String hui=SqlTool.findOneRecord(sq);
				
				if(hui==null){//�ж���������ǲ��ǻ��������̣�����ǣ���ô�Ͳ�����Ԥװָ��
				   String wl=SqlTool.findOneRecord("Select ���ϱ���  from ��������map where ���̱��='"+tp+"'");
				if(wl!=null)
				   {
					SqlTool.manUpPallet(tp,wl,30,"0","1");
				   }
				}
			}
			
			}
				
				
			 String sql="select idEvent,���̱��,"
			      		+"������  from ���⶯��ָ��   where ���� IN('�����߻���','Ԥ�ϻ�') and ״̬2<>'1' and ״̬='���'"
			      		+" and ������=1  order by idEvent";
			  System.out.println("A��RFID+++++");
			
			
			String res=SqlTool.findOneRecord(sql);
			if(res!=null){
				if(!ClientSer.isOpenRfid)
				 tp=res.split("!_!")[1];
		    	System.out.println("A��RFID+++++="+tp);
			
			  if(tp.equals(""))return;
			    String back=SqlTool.exeRffid2(tp);
			
				if(back.contains("�ɹ�")){
					
				}
				
			    }
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    if(rf2==0)
	    	 System.out.println("A��AGV��������������");
		      rf2=1;
	    
     }
  
  public void startDownRFFID2(){
	  //����ͨ��
	    try {
			String b=ClientSer.getIntance().getState(5);
			
			
			if(b.equals("1")){//����̨���л���
				
				   String tp="";
			 if(ClientSer.isOpenRfid){
				   tp=ClientSer.getIntance().ReadFromRffid("", 2);
				if(!tp.equals("")){
					String sq="select ���̱�� "
				      		+"  from ���⶯��ָ��   where ���� ='�����߻���' and ״̬2<>'1' and ״̬='���'"
				      		+" and ������=2  and ���̱��='"+tp+"'  order by idEvent";
					String hui=SqlTool.findOneRecord(sq);
					if(hui==null){
					String wl=SqlTool.findOneRecord("Select ���ϱ���  from ��������map where ���̱��='"+tp+"'");
					if(wl!=null)
					{
						SqlTool.manUpPallet(tp,wl,30,"0","2");
					}
					
					}
					
				}
				}
				
	            String sql="select idEvent,���̱��,"
			      		+"������  from ���⶯��ָ��   where ���� IN('�����߻���','Ԥ�ϻ�') and ״̬2<>'1' and ״̬='���'"
			      		+" and ������=2  order by idEvent";
				System.out.println("B��RFID2+++++");
			
				
				String res=SqlTool.findOneRecord(sql);
				if(res!=null){
					if(!ClientSer.isOpenRfid)
					 tp=res.split("!_!")[1];
			   System.out.println("B��RFID2+++++="+tp);
			     
			      if(tp.equals(""))return;
			 
			    String back=SqlTool.exeRffid2(tp);
			
				if(back.contains("�ɹ�")){
					
				}
				
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    if(rf1==0)
		       System.out.println("B��AGV��������������");
		      rf1=1;
	    
     }
  
  
    public  void startDakuQingqiu(){
    	 //	��һ����ȡ�����������������ݡ��ŵ�һ��hashtable���档
    	 //  �ڶ�������ģ������˳������������ϡ�
    	 //  �õ���������ж��ٿ�λ
    	//��ȡװ��ϵ��
    	//��ȡ��λ��λ
    	 Hashtable<String,Integer>sumWL1=new Hashtable<String,Integer>();//װ����A���������������ܺ�
    	 Hashtable<String,Integer>sumWL2=new Hashtable<String,Integer>();//װ����B���������������ܺ�
    	String sql0="select  ��λ���,���̱��  from ��λ��  where ���̱�� IS NULL  OR  ���̱�� =''" +" and ��λ���  between 1 and 28";
    	 Vector v0=SqlTool.findInVector(sql0);
    	 int kong=v0.size()-1;
    	// int kong=2;
    	 if(kong>0){
    		 Hashtable<String,Integer>h=new Hashtable<String,Integer>();
    		 String sql4="select  ���ϱ���,װ��ϵ��  from ͨ������  ";
    		  Vector v4=SqlTool.findInVector(sql4);
    		  for(int i=0;i<v4.size();i++){
    			  Vector row=(Vector)v4.get(i);
    			  String num= row.get(1)==null?"0.0":row.get(1).toString();
    			  h.put(row.get(0).toString(),num.equals("0.0")?10:Math.round(Float.parseFloat(num)));
    			  
    		  }
    		   
    		  Hashtable<String,Integer>temp1=new Hashtable<String,Integer>();
    		  Vector<Hashtable<String,Integer>> zl=new Vector<Hashtable<String,Integer>>();
    	    	String sql="select  ����,����-�������,װ���� from �䷽ָ�����  where IFNULL(����,0)-IFNULL(�������,0)>0 and װ����='1' ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 50";
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
    		    //����˳�򣬱�֤ģ�鰴�����ϵ�˳������
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
    		    String sql2="select  ����,����-�������,װ���� from �䷽ָ����� where IFNULL(����,0)-IFNULL(�������,0)>0 and װ����='2' ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 50";
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
    		    //����˳�򣬱�֤ģ�鰴�����ϵ�˳������
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
    		  
    		    //�õ��������δ�������������,�������������������
    		    String sql3="select  ����,SUM(����) from ������� where  ״̬<>'1' and ����=1 GROUP BY ����  ORDER BY ID  LIMIT 50"; 
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
    		    String sql5="select  ����,SUM(����) from ������� where  ״̬<>'1' and ����=2 GROUP BY ����  ORDER BY ID  LIMIT 50"; 
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
    		  //����������ָ��ϲ�����һ��װ������
    		 
    		   
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
    	//���ݻ�λ����Ŀ���װ�ص�����������  
    		   Vector<String> sqlc=new Vector<String>();//����!_!
    		    for(int i=0;i<kong;i++){
    		    	if(zl.size()>i){
    		    		Hashtable<String,Integer>tem= zl.get(i);	
    		    		String key=tem.keys().nextElement();
    		    		if(key.startsWith("2_")){
    		    			String sq="insert into ������� (����,����,���� ) values("+"'"+key.replace("2_", "")+"',"
    		    					+"'"+tem.get(key)+"',"+"'"+2+"')";
    		    			sqlc.addElement(sq);
    		    			
    		    		  }else{
    		    			  
    		    			  String sq="insert into ������� (����,����,���� ) values("+"'"+key+"',"
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
    					//��λ����ʼ��ַ����ע��û�г�ʼ���������λ����ʼ��ַ��D200����ôD200�ͱ�עΪ��û�г�ʼ����
    			   String palet=SqlTool.findOneRecord("select ���̱�� from �������  where  ��λ��='"+huoweiA[r]+"'");
    			   String val=SqlTool.findOneRecord("select address from �������  where  ���̱��='"+palet+"'");
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
    	    					//��λ����ʼ��ַ����ע��û�г�ʼ���������λ����ʼ��ַ��D200����ôD200�ͱ�עΪ��û�г�ʼ����
    	    			   String palet=SqlTool.findOneRecord("select ���̱�� from �������  where  ��λ��='"+huoweiB[r]+"'");
    	    			   String val=SqlTool.findOneRecord("select address from �������  where  ���̱��='"+palet+"'");
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
	    		   
	    			  String tem2=SqlTool.findOneRecord("select  ���̱��,����,��λ��,����  from �������"
	    			  		+ "   where  ��λ�� in (514,512,510,508,506,504,502) and  ���̱��=8888 and ����=1");   
	    			    if(tem2!=null){
	    			    	//�������������������ϣ���ô���������߻���
	    			    	String sm[]=tem2.split("!_!");
	    			    	String tem=SqlTool.findOneRecord("select  ��λ���,���̱��  from ��λ��   where  ��λ��� BETWEEN 1 AND 28  AND (���̱�� IS NULL OR  ���̱��='')  and ��λ���>'"+huojia1+"' ORDER BY  ��λ��� " );   
	    			    	  if(tem!=null){
	    			    		  String sm2[]=tem.split("!_!");
	    			    		  String b=SqlTool.add����ָ��(sm[0], sm[2], sm2[0], "�����߻���", 0, 1+"");
	    			    		  if(b.contains("�ɹ�")){
	    			    		  huojia1++;}
	    			    		  if(huojia1==28)huojia1=0;
	    			    	     }
	    			            
	    			         }else{
	    			          //�������̲����������ϣ���ô�����»�
	    			        	 tem2=SqlTool.findOneRecord("select  ���̱��,����,��λ��,����  from �������"
	    			        		  		+ "   where  ��λ�� between 1 and 28 and  ���̱��=8888 and ����=1");  
	    			        	 if(tem2!=null){
	    			        		 String sm[]=tem2.split("!_!");
	    			               Vector v=SqlTool.findInVector("select  ��λ���,���̱��  from ��λ��   where  ��λ���  between 501 and 514 AND ��λ���>"+(500+startLine1*2)+"  ORDER BY  ��λ��� " ); 
	    			               int maxCols=7-startLine1;
	    			               
	    			               for(int i=0;i<maxCols;i++){
	    			            	   Vector row1=(Vector)v.get(i*2); //����λ
	    			            	   Vector row2=(Vector)v.get(i*2+1); //ż��λ
	    			            	 String tp1=(row1.get(1)==null?"": row1.get(1).toString());
	    			            	 String tp2=(row2.get(1)==null?"": row2.get(1).toString());
	    			            	 if(tp1.equals("")&&tp2.equals("")){
	    			            		 String b=SqlTool.add����ָ��(sm[0], sm[2], row1.get(0).toString(), "�»�", 0, 1+""); 
	    			            		 if(b.contains("�ɹ�")){
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
	    		   
	    			  String tem2=SqlTool.findOneRecord("select  ���̱��,����,��λ��,����  from �������"
	    			  		+ "   where  ��λ�� in (614,612,610,608,606,604,602) and  ���̱��=8888 and ����=2");   
	    			    if(tem2!=null){
	    			    	//�������������������ϣ���ô���������߻���
	    			    	String sm[]=tem2.split("!_!");
	    			    	String tem=SqlTool.findOneRecord("select  ��λ���,���̱��  from ��λ��   where  ��λ��� BETWEEN 1 AND 28  AND (���̱�� IS NULL OR  ���̱��='')  and ��λ���>'"+huojia2+"' ORDER BY  ��λ��� " );   
	    			    	  if(tem!=null){
	    			    		  String sm2[]=tem.split("!_!");
	    			    		  String b=SqlTool.add����ָ��(sm[0], sm[2], sm2[0], "�����߻���", 0, 2+"");
	    			    		  if(b.contains("�ɹ�")){
	    			    		  huojia2++;}
	    			    		  if(huojia2==28)huojia2=0;
	    			    	     }
	    			            
	    			         }else{
	    			          //�������̲����������ϣ���ô�����»�
	    			        	 tem2=SqlTool.findOneRecord("select  ���̱��,����,��λ��,����  from �������"
	    			        		  		+ "   where  ��λ�� between 1 and 28 and  ���̱��=8888 and ����=2");  
	    			        	 if(tem2!=null){
	    			        		 String sm[]=tem2.split("!_!");
	    			               Vector v=SqlTool.findInVector("select  ��λ���,���̱��  from ��λ��   where  ��λ���  between 601 and 614 AND ��λ���>"+(600+startLine2*2)+"  ORDER BY  ��λ��� " ); 
	    			               int maxCols=7-startLine2;
	    			               
	    			               for(int i=0;i<maxCols;i++){
	    			            	   Vector row1=(Vector)v.get(i*2); //����λ
	    			            	   Vector row2=(Vector)v.get(i*2+1); //ż��λ
	    			            	 String tp1=(row1.get(1)==null?"": row1.get(1).toString());
	    			            	 String tp2=(row2.get(1)==null?"": row2.get(1).toString());
	    			            	 if(tp1.equals("")&&tp2.equals("")){
	    			            		 String b=SqlTool.add����ָ��(sm[0], sm[2], row1.get(0).toString(), "�»�", 0, 2+""); 
	    			            		 if(b.contains("�ɹ�")){
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
