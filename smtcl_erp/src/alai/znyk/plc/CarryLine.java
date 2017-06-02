package alai.znyk.plc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import alai.znyk.common.SqlPro;
import alai.znyk.server.SqlTool;

public class CarryLine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2995468924150571066L;

	/**
	 * 
	 */


	private  Carry[]cont=new Carry[15];
	int machineID=0;
    private Carry lastMoveCarry;
	public Carry buffer;
	public CarryLine(int machineID){
		 this.machineID=machineID;
	} 
	public boolean addFist(Carry carr){
		if(1==1){
			cont[0]=carr;
			if(carr!=null)
			 lastMoveCarry=carr;
			return true;
		}else{return false;}
	}
	public Carry getLastMoveCarry() {
		return lastMoveCarry;
	}
	public void setLastMoveCarry(Carry lastMoveCarry) {
		this.lastMoveCarry = lastMoveCarry;
	}
	public int getLength(){return cont.length;}
	public void setBuffer(Carry carr){this.buffer=carr;}
	
	public Carry getCarry(int index){
		if(index==-1)return buffer;
		return cont[index];
	}
	public boolean removeToNext(int fromSt){
		if(fromSt==cont.length-1){removeLast();
				return true;}
		if(cont[fromSt]==null) {
		   //	SqlPro.getLog().error("�ؾ��ƶ�ʧ����->����λΪ��"+fromSt+"ST");	

			return false;}
		if(fromSt>cont.length-1){
			SqlPro.getLog().error("�ؾ��ƶ�ʧ����->�����߽�"+fromSt+"ST");	
			return false;}
		
		 if(cont[fromSt+1]==null){
			cont[fromSt+1]=cont[fromSt];
			cont[fromSt]=null;
			return true;
		}
		 SqlPro.getLog().error("�ؾ��ƶ�ʧ����->ǰ�����ؾ�"+fromSt+"ST");	
		return false;
	}
	public void removeLast(){
		cont[cont.length-1]=null;
		
	}
	
	public void setCarryAt(Carry ca,int index){
		cont[index]=ca;
	}
	
	public String setCarryAt(int index,String ����ID,String ģ��ID,String �ֽ��,String �ؾߺ�){
		//�������
		if(index==-2){
			//���ؾ����������,������������0�Ź�λ����Ҫ����Ӧ����
			for(int i=-1;i<15;i++){
				Carry car=this.getCarry(i);
				if(car!=null){
					if(car.getName().equals(����ID+"="+ģ��ID+"="+�ֽ��+"="+�ؾߺ�)){
						if(i==-1){
							this.buffer=null;
							System.out.println(i+":"+machineID);
							if(machineID==1){
								PLC.getIntance().ST0_1.secondST.write=false;
								// String sql="update �䷽ָ�����  set ǰ������־=NULL where ����ID="+"'"+����ID+"' and �ֽ��="+�ֽ��+" and ģ����ID="+ģ��ID+" and �ؾ����="+�ؾߺ�+" and װ����="+machineID;
						    	 // System.out.println(i+":"+sql);
								// SqlTool.insert(new String[]{sql});						
							   }else{
								PLC.getIntance().ST0_2.secondST.write=false; 
								// String sql="update �䷽ָ�����  set ǰ������־=NULL where ����ID="+"'"+����ID+"' and �ֽ��="+�ֽ��+" and ģ����ID="+ģ��ID+" and �ؾ����="+�ؾߺ�+" and װ����="+2;
						    	// SqlTool.insert(new String[]{sql});	
							   }
						    }
						else{
							cont[i]=null;
							if(i==0){
								if(machineID==1){
									PLC.getIntance().ST0_1.firstST.write=false;
									// String sql="update �䷽ָ�����  set ǰ������־=NULL where ����ID="+"'"+����ID+"' and �ֽ��="+�ֽ��+" and ģ����ID="+ģ��ID+" and �ؾ����="+�ؾߺ�+" and װ����="+machineID;
									// System.out.println(i+":"+SqlTool.insert(new String[]{sql}));
									 
								   }else{
									PLC.getIntance().ST0_2.firstST.write=false;  
									 //String sql="update �䷽ָ�����  set ǰ������־=NULL where ����ID="+"'"+����ID+"' and �ֽ��="+�ֽ��+" and ģ����ID="+ģ��ID+" and �ؾ����="+�ؾߺ�+" and װ����="+2;
							    	 //SqlTool.insert(new String[]{sql});	
								   }
								
							}
						}
						
					}
					
				   }
				
			}
			
			return "�ɹ�";
		}
		
		else{
			//������������������е�����ؾ�
			for(int i=-1;i<15;i++){
				Carry car=this.getCarry(i);
				if(car!=null){
					if(car.getName().equals(����ID+"="+ģ��ID+"="+�ֽ��+"="+�ؾߺ�)){
						if(i==-1){
						  this.buffer=null;
							
						}
						else{
							cont[i]=null;
						}
						
					}
					
				   } }
				
			
			//Ȼ��������λ��ɱ��ؾ�
	 Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ,����ID,ģ����ID,IFNULL(�ٵ�о1,0),IFNULL(�ٵ�о2,0),��оλ��1,��оλ��2,��оλ��3,��оλ��4,IFNULL(��װ��,'��'),ģ������,��о����  ,pack����,COUNT(DISTINCT �������,ģ����ID,�ֽ��,�ؾ���� )"
	 		+ "  from �䷽ָ�����   where  ����ID='"+����ID+"' and ģ����ID='"+ģ��ID+
	 		"'"+" and �ֽ��='"+�ֽ��+"' and �ؾ���� ='"+�ؾߺ�+"' GROUP BY �������,�ֽ��,�ؾ����   ORDER BY �������,ģ�����,�ֽ��,�ؾ���� LIMIT 10");		
	  
	 if(tem.size()>0){    
	       Vector row=(Vector)tem.get(0);
	       Carry carr=new Carry(row.get(1)==null?0:(int)row.get(1), Integer.parseInt(�ֽ��),Integer.parseInt(�ؾߺ�),Integer.parseInt(ģ��ID));
	         carr.set�ٵ�о1(row.get(12).toString().equals("0")?0:(int)row.get(12));
	     	 carr.set�ٵ�о2(row.get(13).toString().equals("0")?0:(int)row.get(13));
	     	 carr.set��оλ��1(row.get(14)==null?null:row.get(14).toString());
	     	 carr.set��оλ��2(row.get(15)==null?null:row.get(15).toString());
	     	 carr.set��оλ��3(row.get(16)==null?null:row.get(16).toString());
	     	 carr.set��оλ��4(row.get(17)==null?null:row.get(17).toString());
	     	 carr.set��װ��(row.get(18).equals("��")?true:false);
	     	 carr.set����ID(row.get(10)==null?0:Integer.parseInt(row.get(10).toString()));
	     	 carr.setģ������(row.get(19)==null?0:Integer.parseInt(row.get(19).toString()));
	     	 carr.set��о����(row.get(20)==null?0:Integer.parseInt(row.get(20).toString()));
	     	 carr.setPack����(row.get(21)==null?0:Integer.parseInt(row.get(21).toString()));
	     	 Vector<Vector> tost=SqlTool.findInVector("select ��λ,���� from �䷽ָ�����   where ����ID='"+
	     	 carr.get����ID()+"' and �ֽ��='"+carr.get�ֽ��()+"' and �ؾ����='"+carr.get�ؾ����()+"' order by ��λ");
	     	 for(int m=0;m<tost.size();m++){
	     		 Vector vv=(Vector)tost.get(m);
	     	     carr.set��λ(carr.get��λ()+""+vv.get(0));
	     	 
	         }
	     	if(index==-1) {
	     		this.buffer=carr;
	     	}else{
	     		 cont[index]=carr;
	     	}
	     	return "�ɹ�"; 
	     }
	
			
		}//end else
		
		 return "δ����";
		
	}
	

	public  static void main(String ss[]){
		CarryLine l=new CarryLine(1);
		l.setBuffer( new Carry(1,1,1,1));
		l.addFist(l.buffer);
//		l.buffer=null;
		try{
			long time1=System.currentTimeMillis();
		    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\objectFile.obj"));
			out.writeObject( l);
			out.flush();
			System.out.println(System.currentTimeMillis()-time1);
			out.close();
			
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\objectFile.obj"));
			CarryLine carr=  (CarryLine)in.readObject();
			in.close();
			Carry carr2=l.buffer;
		     System.out.println(carr2.get�ؾ����());
			
		   }catch(Exception ex){ex.printStackTrace();}
		
	}
}
