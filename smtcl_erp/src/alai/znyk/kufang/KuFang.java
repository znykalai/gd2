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
					 start�Ѷ��ָ��();
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
	
//����������7���������ϵ�λ��	
public void startLine(){
	//����ͨ��
		
   try{
	  String ss= ClientSer.getIntance().getState(SqlPro.A��������);
	  String sm[]=ss.split("\\|");
	String sql1= "select  ��λ���,���̱��   from ��λ��  where  ��λ���  between 501 and 514 order by ��λ���";
	
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
	
	 String ss2= ClientSer.getIntance().getState(SqlPro.B��������);
	 String sm2[]=ss2.split("\\|");
	 String sql2= "select  ��λ���,���̱��   from ��λ��  where  ��λ���  between 601 and 614 order by ��λ���";
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
     System.out.println("�����λ������λ�Զ������������");
   
	line=1;
		
	
}

public void start�Ѷ��ָ��(){
	
	 Vector ��1��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�ϻ�' and ������= '1' order by idEvent");
     Vector ��1��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�»�' and ������= '1' order by idEvent");
    
     boolean run=false;
     int last1=1;
     if(��1��.size()>0){
    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
    	 Vector up=(Vector)��1��.get(0);
    	 if(up.get(8).equals("ִ����")||up.get(8).equals("�ѷ���")){run=true;}
      }
     
     if(��1��.size()>0){
    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
    	 Vector down=(Vector)��1��.get(0);
    	 if(down.get(8).equals("ִ����")||down.get(8).equals("�ѷ���")){run=true;}
      
     }
    //�������Ѷ��û��ִ�е�ָ���ô���� 
    if(last1==1){//�ϻ�
    if(!run) {
    	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
    	
    	try {
			String state=ClientSer.getIntance().getState(SqlPro.�Ѷ��1״̬);//��ȡ�Ѷ��1��״̬
			if(state.equals(SqlPro.�Ѷ������)){
				 if(��1��.size()>0){
			    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��1��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 1);
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {e.printStackTrace();}
    	last1=2;
       }
	
  }
    if(last1==2){//�»�
        if(!run) {
        	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
        	
        	try {
    			String state=ClientSer.getIntance().getState(SqlPro.�Ѷ��1״̬);//��ȡ�Ѷ��1��״̬
    			if(state.equals(SqlPro.�Ѷ������)){
    				//�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��1��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().getPallet(Integer.parseInt(eventID), 
			    			 fromID, Integer.parseInt(toID), 1);
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
    				
    			}
    		   } catch (Exception e) {e.printStackTrace();}
        	last1=1;
           }
    	
      }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    Vector ��2��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�ϻ�' and ������= '2' order by idEvent");
    Vector ��2��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ����='�»�' and ������= '2' order by idEvent");
    
    boolean run2=false;
    int last2=1;
    if(��2��.size()>0){
   	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
   	 Vector up=(Vector)��2��.get(0);
   	 if(up.get(8).equals("ִ����")||up.get(8).equals("�ѷ���")){run2=true;}
     }
    
    if(��2��.size()>0){
   	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
   	 Vector down=(Vector)��2��.get(0);
   	 if(down.get(8).equals("ִ����")||down.get(8).equals("�ѷ���")){run2=true;}
     
    }
   //�������Ѷ��û��ִ�е�ָ���ô���� 
   if(last2==1){//�ϻ�
   if(!run2) {
   	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
   	
   	try {
			String state=ClientSer.getIntance().getState(SqlPro.�Ѷ��2״̬);//��ȡ�Ѷ��2��״̬
			if(state.equals(SqlPro.�Ѷ������)){
				 if(��2��.size()>0){
			    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��2��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 2);
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {e.printStackTrace();}
   	last2=2;
      }
	
 }
   if(last2==2){//�»�
       if(!run2) {
       	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
       	
       	try {
   			String state=ClientSer.getIntance().getState(SqlPro.�Ѷ��2״̬);//��ȡ�Ѷ��2��״̬
   			if(state.equals(SqlPro.AGV������)){
   				//�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��2��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().getPallet(Integer.parseInt(eventID), 
			    			 fromID, Integer.parseInt(toID), 2);
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
   				
   			}
   		   } catch (Exception e) {e.printStackTrace();}
       	last2=1;
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
   	
   	try {
			String state=ClientSer.getIntance().getState(SqlPro.AGV1);//��ȡAGV1��״̬
			if(state.equals(SqlPro.AGV������)){
				 if(��1��.size()>0){
			    	 //�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��1��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {e.printStackTrace();}
  
      }
	
 //////////////////////////////////////////////////////////////////////////////////////////////////////////
 
       if(!run2) {
       	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
       	
       	try {
   			String state=ClientSer.getIntance().getState(SqlPro.AGV2);//��ȡAGV2��״̬
   			if(state.equals(SqlPro.AGV������)){
   				//�����жϵ�ǰָ����û����ִ���У�����У���ô�Ͳ��ڷ���
			    	 Vector up=(Vector)��2��.get(0);
			    	 String eventID=up.get(0).toString();
			    	 String fromID=up.get(5).toString();
			    	 String toID=up.get(6).toString();
			    	 ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
   				
   			}
   		   } catch (Exception e) {e.printStackTrace();}
       
          }
   	if(agv==0)
       System.out.println("AGV��������������");
      agv=1;
   
    }

  //�������������ͺż��
  public void startUpRFFID(){
	  try {
			String b=ClientSer.getIntance().getState(SqlPro.������);
			if(b.equals("1")){//����̨���л���
			String tp=ClientSer.getIntance().ReadFromRffid("", 1);
			if(isRffid2.get(tp)!=null)return;
			isRffid2.put(tp, tp);
			//�ȴӿ�����̱����濴����û���������
			Vector tem=SqlTool.findInVector("select ����  from �������  where ���̱��="+"'"+tp+"'"  );
			if(tem.size()>0){
			    Vector row=(Vector)tem.get(0);
				SqlTool.autoUpPallet(tp, row.get(0)+"", "60001",/* machineID*/"1");
				
			}else{
				String sm[]=SqlTool.getWuliaoFromLK(tp).split("!_!");//����=����
				SqlTool.autoUpPallet(tp, sm[0], "60001",/* machineID*/"1");
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
	    try {
			String b=ClientSer.getIntance().getState(5);
			
			
			if(b.equals("1")){//����̨���л���
			String tp=ClientSer.getIntance().ReadFromRffid("", 2);
			if(isRffid1.get(tp)!=null)return;
			String back=SqlTool.exeRffid2(tp);
			isRffid1.put(tp, "");
				if(back.contains("�ɹ�")){}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    if(rf2==0)
		       System.out.println("ȥ����������������������");
		      rf2=1;
	    
     }
  
  
	
}
