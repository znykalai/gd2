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
		
	 } }catch(Exception ex){
		 SqlPro.getLog().error("A���������Ƿ������̣�"+ex.getMessage());
		 ex.printStackTrace();}
   
   
   try{
	
	 String ss2= ClientSer.getIntance().getState(SqlPro.B��������);
	 System.out.println(ss2);
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
		
	 } }catch(Exception ex){
		 SqlPro.getLog().error("B���������Ƿ������̣�"+ex.getMessage());
		 ex.printStackTrace();}
	
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
    	if(��1��.size()>0){
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
    	}
    	last1=2;
       }
	
  }
    if(last1==2){//�»�
        if(!run) {
        	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
        	if(��1��.size()>0){
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
        	
        	}
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
			    	 ClientSer.getIntance().upPallet(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID), 2);
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {e.printStackTrace();}
	   }
   	last2=2;
      }
	
 }
   if(last2==2){//�»�
       if(!run2) {
       	//���û�������е�ָ��,��ô���������ϣ����������ڿ�����û���»���ָ�����������»�
       	if(��2��.size()>0){
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
       	}
       	
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
			    	 ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
			      }
				
			}
		   } catch (Exception e) {e.printStackTrace();}
   	             
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
			    	 ClientSer.getIntance().toBackBuffer(Integer.parseInt(eventID), 
			    			 Integer.parseInt(fromID), Integer.parseInt(toID));
			    	 String sql2="update ���⶯��ָ��  set ״̬='�ѷ���',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+eventID+"'"; 
			    	 SqlTool.insert(new String[]{sql2});
   				
   			}
   		   } catch (Exception e) {e.printStackTrace();}
       	
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
	    try {
			String b=ClientSer.getIntance().getState(5);
			
			
			if(b.equals("1")){//����̨���л���
				System.out.println("RFID2+++++");
			String tp=ClientSer.getIntance().ReadFromRffid("", 2);
			System.out.println("RFID2+++++="+tp);
			if(isRffid1!=null&&isRffid1.equals(tp))return;
			 isRffid1=tp;
			if(tp.equals(""))return;
			 
			String back=SqlTool.exeRffid2(tp);
			
				if(back.contains("�ɹ�")){
					
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    if(rf2==0)
		       System.out.println("ȥ����������������������");
		      rf2=1;
	    
     }
  
    public  void startDakuQingqiu(){
    	 //	��һ����ȡ�����������������ݡ��ŵ�һ��hashtable���档
    	 //  �ڶ�������ģ������˳������������ϡ�
    	 // �õ���������ж��ٿ�λ
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
    	    	String sql="select  ����,����-�������,װ���� from �䷽ָ����� where IFNULL(����,0)-IFNULL(�������,0)>0 and װ����='1' ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 50";
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
