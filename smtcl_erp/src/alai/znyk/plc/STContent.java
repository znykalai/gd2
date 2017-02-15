package alai.znyk.plc;

import java.io.Serializable;
import java.util.Vector;

import alai.znyk.server.SqlTool;

public class STContent implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ST_Father firstST;
    public ST_Father secondST;
    public int stNum;
    public PLC plc;
    public int װ����;
    public STContent(PLC plc,ST_Father first,ST_Father second,int stNum,int װ����){
    	this.firstST=first;
    	this.secondST=second;
    	this.stNum=stNum;
    	this.װ����=װ����;
    	this.plc=plc;
       }
    public void update��־(ST_Father st,int mode){
    	//System.out.println("update-----");
    	if(mode==1){//���� ǰ������־
    	 String sql="update �䷽ָ�����  set ǰ������־=1 where ����ID="+"'"+st.get����ID()+"' and �ֽ��="+st.get�ֽ��()+" and ģ����ID="+st.getģ����ID()+" and �ؾ����="+st.get�ؾ����()+" and װ����="+st.machineID;
    	 System.out.println(sql);
    	 SqlTool.insert(new String[]{sql});
    	 }
    	if(mode==2){//���� 1ST-6ST�Ķ�ȡ��־
       	 String sql="update �䷽ָ�����  set ST��ȡ��־=1 where ID="+"'"+st.getId()+"'";
       	 System.out.println(sql);
       	 SqlTool.insert(new String[]{sql});
       	 }
    }
   
  
    public void initFromSql(){
    	
    	if(plc.stop1){//���������ݿ���������ˣ����ǲ�Ӱ����˻����ļ���ִ��
    		if(װ����==1){
    			if(stNum==1){ 
    				 firstST.writeifChangeToPLC();
    		    	 secondST.writeifChangeToPLC();
    				return;}	
    			if(stNum>=2&&stNum<=8){
    				updata����();
    				return;}
    			
    		}
    	}
    	if(plc.stop2){
    		if(װ����==1){
    			if(stNum==1){
    				 firstST.writeifChangeToPLC();
    		    	 secondST.writeifChangeToPLC();
    				return;}	
    			if(stNum>=2&&stNum<=8){
    				updata����();
    				return;}
    			
    		}
    	}
    	
    	if(stNum==1){
         //ǰ����̨,��������+�ֽ��+ģ����루ģ��ţ�����������ؾߣ����ؾ��������
    	//�����ݿ��ȡ�ɹ���,"ǰ��������ȡ��־=1",ϵͳ������ǰ�ǲ����ڴ��¶�ȡ�����ܵ��ؾ߷��к󣬰������־����Ϊ2��ʾ��ɣ��Ժ���Ҳ��������ݿ��ж�ȡ
    	if(!firstST.isWrite()&&!secondST.isWrite()){
    		 //��ȡǰ����ָ���˳��д��1��2��ָ����У��������ֻ�ᷢ����ϵͳ��һ������	
    		 //Ȼ���Write=true;
    		// �������=PACK��ţ��ֽ��=��������������ģ��ֳɵ���ţ�
    		//����ID+ģ����ID+�ֽ��+�ؾ����,��4��Ҳ������Ψһ���ؾ�
    		
    	 Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ,����ID,ģ����ID,IFNULL(�ٵ�о1,0),IFNULL(�ٵ�о2,0),��оλ��1,��оλ��2,��оλ��3,��оλ��4,IFNULL(��װ��,'��'),ģ������,��о����  ,COUNT(DISTINCT �������,ģ����ID,�ֽ��,�ؾ���� )  from �䷽ָ�����   where  װ����="+װ����+" and IFNULL(ǰ������־,0)<>1 GROUP BY �������,�ֽ��,�ؾ����   ORDER BY �������,ģ�����,�ֽ��,�ؾ���� LIMIT 10");
    	 //System.out.println(tem);	
    	 if(tem.size()>1){
    	 		 Vector row=(Vector)tem.get(0);
    	 		 Vector row2=(Vector)tem.get(1);
    	 		 System.out.println(row);
    	 		 System.out.println(row2);
    	 		 ((_FST)firstST).clear();
    	 		 ((_FST)firstST).setId(row.get(0)==null?0:(int)row.get(0));
    	 		 ((_FST)firstST).set������(row.get(1)==null?0:(int)row.get(1));
    	 		 ((_FST)firstST).set�ֽ��(row.get(2)==null?0:(int)row.get(2));
    	 		 ((_FST)firstST).set�ؾ����(row.get(3)==null?0:(int)row.get(3));
    	 		 ((_FST)firstST).set��B��(row.get(8).equals("��")?true:false);
    	 		 ((_FST)firstST).set����λ������־(true);
    	 		 ((_FST)firstST).set����RDY(plc.getSTRdy(װ����,stNum));
    	 		  firstST.set����ID(row.get(10)==null?0:(int)row.get(10));
    	 		  firstST.setģ����ID(row.get(11)==null?0:(int)row.get(11));
    	 		  firstST.setWrite(true);
    	 		 // firstST.writeToPLC();
    	 		  update��־(firstST,1);
    	 		 //�����Ϊ��ʱ��Ӳ��˵ģ�ÿ���յ����е��źź󣬻��Զ��ƶ�����ʱ��������ΪNULL�ġ�
    	     	 Carry carr=new Carry(firstST.get������(), firstST.get�ֽ��(), firstST.get�ؾ����(),firstST.getģ����ID());
    	     	 carr.set�ٵ�о1((int)row.get(12));carr.set�ٵ�о2((int)row.get(13));
    	     	 carr.set��оλ��1(row.get(14)==null?null:row.get(14).toString());
    	     	 carr.set��оλ��2(row.get(15)==null?null:row.get(15).toString());
    	     	 carr.set��оλ��3(row.get(16)==null?null:row.get(16).toString());
    	     	 carr.set��оλ��4(row.get(17)==null?null:row.get(17).toString());
    	     	 carr.set��װ��(row.get(18).equals("��")?true:false);
    	     	 carr.set����ID(row.get(10)==null?0:(int)row.get(10));
    	     	 carr.setģ������(row.get(19)==null?0:(int)row.get(19));
    	     	 carr.set��о����(row.get(20)==null?0:(int)row.get(20));
    	  		 PLC.getIntance().line.addFist(carr);
    	 		 
    	 	 ((_FST)secondST).clear();
   	 		 ((_FST)secondST).setId(row2.get(0)==null?0:(int)row2.get(0));
   	 		 ((_FST)secondST).set������(row2.get(1)==null?0:(int)row2.get(1));
   	 		 ((_FST)secondST).set�ֽ��(row2.get(2)==null?0:(int)row2.get(2));
   	 		 ((_FST)secondST).set�ؾ����(row2.get(3)==null?0:(int)row2.get(3));
   	 		 ((_FST)secondST).set��B��(row2.get(8).equals("��")?true:false);
   	 		 ((_FST)secondST).set����λ������־(true);
   	 		 ((_FST)secondST).set����RDY(plc.getSTRdy(װ����,stNum));
   	 	     secondST.set����ID(row2.get(10)==null?0:(int)row2.get(10));
   	         secondST.setģ����ID(row2.get(11)==null?0:(int)row2.get(11));
   	 		 secondST.setWrite(true);
   	 		 update��־(secondST,1);
   	 		 
   	 	 Carry carr2=new Carry( secondST.get������(),  secondST.get�ֽ��(),  secondST.get�ؾ����(), secondST.getģ����ID());
     	 carr2.set�ٵ�о1((int)row2.get(12));carr2.set�ٵ�о2((int)row2.get(13));
     	 carr2.set��оλ��1(row2.get(14)==null?null:row2.get(14).toString());
     	 carr2.set��оλ��2(row2.get(15)==null?null:row2.get(15).toString());
     	 carr2.set��оλ��3(row2.get(16)==null?null:row2.get(16).toString());
     	 carr2.set��оλ��4(row2.get(17)==null?null:row2.get(17).toString());
     	 carr2.set��װ��(row2.get(18).equals("��")?true:false);
     	 carr2.setģ������(row2.get(19)==null?0:(int)row2.get(19));
     	 carr2.set��о����(row2.get(20)==null?0:(int)row2.get(20));
     	 carr2.set����ID(row2.get(10)==null?0:(int)row2.get(10));
  		 PLC.getIntance().line.setBuffer(carr2);
    	 		 
    	 	}else{
    	 		if(tem.size()==1){
    	 		 Vector row=(Vector)tem.get(0);
    	 		 ((_FST)firstST).clear();
    	 		 ((_FST)firstST).setId(row.get(0)==null?0:(int)row.get(0));
    	 		 ((_FST)firstST).set������(row.get(1)==null?0:(int)row.get(1));
    	 		 ((_FST)firstST).set�ֽ��(row.get(2)==null?0:(int)row.get(2));
    	 		 ((_FST)firstST).set�ؾ����(row.get(3)==null?0:(int)row.get(3));
       	 		 ((_FST)firstST).set��B��(row.get(8).equals("��")?true:false);
       	 		 ((_FST)firstST).set����λ������־(true);
       	 		 ((_FST)firstST).set����RDY(plc.getSTRdy(װ����,stNum));
       	 	    firstST.set����ID(row.get(10)==null?0:(int)row.get(10));
	 		    firstST.setģ����ID(row.get(11)==null?0:(int)row.get(11));
       	 		firstST.setWrite(true);
       	 	    update��־(firstST,1);
       	 	    
       	 	 Carry carr=new Carry(firstST.get������(), firstST.get�ֽ��(), firstST.get�ؾ����(),firstST.getģ����ID());
	     	 carr.set�ٵ�о1((int)row.get(12));carr.set�ٵ�о2((int)row.get(13));
	     	 carr.set��оλ��1(row.get(14)==null?null:row.get(14).toString());
	     	 carr.set��оλ��2(row.get(15)==null?null:row.get(15).toString());
	     	 carr.set��оλ��3(row.get(16)==null?null:row.get(16).toString());
	     	 carr.set��оλ��4(row.get(17)==null?null:row.get(17).toString());
	     	 carr.set��װ��(row.get(18).equals("��")?true:false);
	     	 carr.set����ID(row.get(10)==null?0:(int)row.get(10));
	     	 carr.setģ������(row.get(19)==null?0:(int)row.get(19));
	     	 carr.set��о����(row.get(20)==null?0:(int)row.get(20));
	  		 PLC.getIntance().line.addFist(carr);
    	 		}
    	 		
    	 	}
                 }
    	
    	 if(firstST.isWrite()&&!secondST.isWrite()){
    		  //�����һ��ָ���ж��У��ڶ���û�У���ô��д��ڶ���ָ���ȡ��һ�����ݿ�Ķ�����ǰ��������ȡ��־=0�ĵ�һ����¼��
    		 Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ,����ID,ģ����ID,IFNULL(�ٵ�о1,0),IFNULL(�ٵ�о2,0),��оλ��1,��оλ��2,��оλ��3,��оλ��4,IFNULL(��װ��,'��') ,ģ������,��о���� , COUNT(DISTINCT �������,ģ����ID,�ֽ��,�ؾ���� )  from �䷽ָ�����   where  װ����="+װ����+" and IFNULL(ǰ������־,0)<>1 GROUP BY �������,�ֽ��,�ؾ����   ORDER BY �������,ģ�����,�ֽ��,�ؾ���� LIMIT 10");
    		 if(tem.size()>0){
    	 		 Vector row=(Vector)tem.get(0);
    	 		 ((_FST)secondST).clear();
    	 		 ((_FST)secondST).setId(row.get(0)==null?0:(int)row.get(0));
       	 		 ((_FST)secondST).set������(row.get(1)==null?0:(int)row.get(1));
       	 		 ((_FST)secondST).set�ֽ��(row.get(2)==null?0:(int)row.get(2));
       	 		 ((_FST)secondST).set�ؾ����(row.get(3)==null?0:(int)row.get(3));
       	 		 ((_FST)secondST).set��B��(row.get(8).equals("��")?true:false);
       	 		 ((_FST)secondST).set����λ������־(true);
       	 		 ((_FST)secondST).set����RDY(plc.getSTRdy(װ����,stNum));
       	 		secondST.set����ID(row.get(10)==null?0:(int)row.get(10));
       	 	    secondST.setģ����ID(row.get(11)==null?0:(int)row.get(11));
       	 		secondST.setWrite(true);
       	 	     update��־(secondST,1);
       	 	 Carry carr2=new Carry( secondST.get������(),  secondST.get�ֽ��(),  secondST.get�ؾ����(), secondST.getģ����ID());
         	 carr2.set�ٵ�о1((int)row.get(12));carr2.set�ٵ�о2((int)row.get(13));
         	 carr2.set��оλ��1(row.get(14)==null?null:row.get(14).toString());
         	 carr2.set��оλ��2(row.get(15)==null?null:row.get(15).toString());
         	 carr2.set��оλ��3(row.get(16)==null?null:row.get(16).toString());
         	 carr2.set��оλ��4(row.get(17)==null?null:row.get(17).toString());
         	 carr2.set��װ��(row.get(18).equals("��")?true:false);
         	 carr2.setģ������(row.get(19)==null?0:(int)row.get(19));
        	 carr2.set��о����(row.get(20)==null?0:(int)row.get(20));
        	 carr2.set����ID(row.get(10)==null?0:(int)row.get(10));
      		 PLC.getIntance().line.setBuffer(carr2);
        	 		 
    	 		}
    	 		
    	  }
    	 
    	 if(!firstST.isWrite()&&secondST.isWrite()){
    		  //�����һ��ָ���ж��У��ڶ���û�У���ô�ѵڶ���ָ���Ƶ���һ������ָ�Ȼ����д��ڶ�������ָ��
    		 firstST.intFromST(secondST);
    		 secondST.setWrite(false);
    		 secondST.clear();
    		 PLC.getIntance().line.addFist(PLC.getIntance().line.buffer);
    		 PLC.getIntance().line.buffer=null;
    		 
    	 }
    	
    	}
    	///////////////////////////////////////////////////////
    	
    	if(stNum>=2&&stNum<=7){
    		
    		if(!firstST.isWrite()&&!secondST.isWrite()){
    Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,����ID,ģ����ID from �䷽ָ�����  where  װ����="+װ����+" and IFNULL(ST��ȡ��־,0)<>1  and ��λ='"+(stNum-1)+"ST' ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");
    	    	 	if(tem.size()>1){
    	    	 		 Vector row=(Vector)tem.get(0);
    	    	 		 Vector row2=(Vector)tem.get(1);
    	    	 		 ((_1_6ST)firstST).clear();
    	    	 		 ((_1_6ST)firstST).setId((int)row.get(0));
    	    	 		 ((_1_6ST)firstST).set������((int)row.get(1));
    	    	 		 ((_1_6ST)firstST).set�ֽ��((int)row.get(2));
    	    	 		 ((_1_6ST)firstST).set�ؾ����((int)row.get(3));
    	    	 		 ((_1_6ST)firstST).set����λ������־(false);
    	    	 		 ((_1_6ST)firstST).setͶ����ǻ��־(false);//����
    	    	 		// System.out.println(row.get(10).getClass());
    	    	 		 ((_1_6ST)firstST).setģ�����ͱ�־((int)row.get(10));
    	    	 		 
    	    	 		 ((_1_6ST)firstST).set��о���ͱ�־((int)row.get(11));
    	    	 		 ((_1_6ST)firstST).set��������((int)row.get(7));
    	    	 		 ((_1_6ST)firstST).set����RDY(plc.getSTRdy(װ����,stNum));
    	    	 		 firstST.set����ID((int)row.get(12));
    	    	 		 firstST.setģ����ID((int)row.get(13));
    	    	 		 firstST.set���ϱ���((String)row.get(6));
    	    	 		 firstST.setWrite(true);
    	    	 		  update��־(firstST,2);
    	    	 		
    	    	 		 
    	    	 		 ((_1_6ST)secondST).clear();
    	    	 		 ((_1_6ST)secondST).setId((int)row2.get(0));
    	    	 		 ((_1_6ST)secondST).set������((int)row2.get(1));
    	    	 		 ((_1_6ST)secondST).set�ֽ��((int)row2.get(2));
    	    	 		 ((_1_6ST)secondST).set�ؾ����((int)row2.get(3));
    	    	 		 ((_1_6ST)secondST).set����λ������־(false);
    	    	 		 ((_1_6ST)secondST).setͶ����ǻ��־(false);//����
    	    	 		 ((_1_6ST)secondST).setģ�����ͱ�־((int)row2.get(10));
    	    	 		 ((_1_6ST)secondST).set��о���ͱ�־((int)row2.get(11));
    	    	 		 ((_1_6ST)secondST).set��������((int)row2.get(7));
    	    	 		 ((_1_6ST)secondST).set����RDY(plc.getSTRdy(װ����,stNum));
    	    	 		 secondST.set����ID((int)row2.get(12));
    	    	 		 secondST.setģ����ID((int)row2.get(13));
    	    	 		 secondST.set���ϱ���((String)row2.get(6));
    	    	 		 secondST.setWrite(true);
    	    	 		 update��־(secondST,2);
    	    	 		 
    	    	 	}else{
    	    	 		if(tem.size()==1){
    	    	 		 Vector row=(Vector)tem.get(0);
    	    	 		 ((_1_6ST)firstST).clear();
    	    	 		 ((_1_6ST)firstST).setId((int)row.get(0));
    	    	 		 ((_1_6ST)firstST).set������((int)row.get(1));
    	    	 		 ((_1_6ST)firstST).set�ֽ��((int)row.get(2));
    	    	 		 ((_1_6ST)firstST).set�ؾ����((int)row.get(3));
    	    	 		 ((_1_6ST)firstST).set����λ������־(false);
    	    	 		 ((_1_6ST)firstST).setͶ����ǻ��־(false);//����
    	    	 		 ((_1_6ST)firstST).setģ�����ͱ�־((int)row.get(10));
    	    	 		 ((_1_6ST)firstST).set��о���ͱ�־((int)row.get(11));
    	    	 		 ((_1_6ST)firstST).set��������((int)row.get(7));
    	    	 		 ((_1_6ST)firstST).set����RDY(plc.getSTRdy(װ����,stNum));
    	    	 		firstST.set����ID((int)row.get(12));
    	    	 		firstST.setģ����ID((int)row.get(13));
    	    	 		firstST.set���ϱ���((String)row.get(6));
    	    	 		firstST.setWrite(true);
    	    	 		  update��־(firstST,2);
    	    	 		}
    	    	 		
    	    	 	}
    			
    		 }
    		
    		if(firstST.isWrite()&&!secondST.isWrite()){
				 Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,����ID,ģ����ID from �䷽ָ�����  where  װ����="+װ����+" and IFNULL(ST��ȡ��־,0)<>1  and ��λ='"+(stNum-1)+"ST' ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");		
				 if(tem.size()>0){
				    Vector row=(Vector)tem.get(0); 
				   ((_1_6ST)secondST).clear();
				   ((_1_6ST)secondST).setId((int)row.get(0));
				   ((_1_6ST)secondST).set������((int)row.get(1));
				   ((_1_6ST)secondST).set�ֽ��((int)row.get(2));
				   ((_1_6ST)secondST).set�ؾ����((int)row.get(3));
				   ((_1_6ST)secondST).set����λ������־(false);
				   ((_1_6ST)secondST).setͶ����ǻ��־(false);//����
				   ((_1_6ST)secondST).setģ�����ͱ�־((int)row.get(10));
				   ((_1_6ST)secondST).set��о���ͱ�־((int)row.get(11));
				   ((_1_6ST)secondST).set��������((int)row.get(7));
				   ((_1_6ST)secondST).set����RDY(plc.getSTRdy(װ����,stNum));
				   secondST.set����ID((int)row.get(12));
	    	 	   secondST.setģ����ID((int)row.get(13));
	    	 	   secondST.set���ϱ���((String)row.get(6));
				   secondST.setWrite(true);	
				   update��־(secondST,2);
				 
				 }
    		}
    		if(!firstST.isWrite()&&secondST.isWrite()){
    			//��ôsecondST�ƶ���first,Ȼ��secondST�ض�
    			
       		 firstST.intFromST(secondST);
       		 secondST.setWrite(false);
       		 secondST.clear();
    		}
    		
    		updata����();
    		
    	}
    
    	////////////////////////////////////
    	 if(stNum==8){
   		    //�ٵ�о��λ
           if(!firstST.isWrite()&&!secondST.isWrite()){
     Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о����,�ٵ�о1,�ٵ�о2,����ID,ģ����ID "
     		+"  from �䷽ָ�����  where  װ����="+װ����+" and IFNULL(ST��ȡ��־,0)<>3  and ��λ='7ST' ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");
     	    	 	if(tem.size()>1){
     	    	 		 Vector row=(Vector)tem.get(0);
     	    	 		 Vector row2=(Vector)tem.get(1);
     	    	 
     	    	 		 
     	    	 		 ((_7ST)firstST).clear();
     	    	 		 ((_7ST)firstST).setId((int)row.get(0));
     	    	 		 ((_7ST)firstST).set������((int)row.get(1));
     	    	 		 ((_7ST)firstST).set�ֽ��((int)row.get(2));
     	    	 		 ((_7ST)firstST).set�ؾ����((int)row.get(3));
     	    	 		 ((_7ST)firstST).set����λ������־(false);
     	    	 		((_7ST)firstST).set��1���ٵ�оλ��(row.get(12)==null?0:(int)row.get(12));
     	    	 	    ((_7ST)firstST).set��2���ٵ�оλ��(row.get(13)==null?0:(int)row.get(13));
     	    	 		 ((_7ST)firstST).setģ�����ͱ�־((int)row.get(10));
     	    	 		 ((_7ST)firstST).set��о���ͱ�־((int)row.get(11));
     	    	 		 ((_7ST)firstST).set��������((int)row.get(7));
     	    	 		 ((_7ST)firstST).set����RDY(plc.getSTRdy(װ����,stNum));
     	    	 		 firstST.set����ID((int)row.get(14));
    	    	 		 firstST.setģ����ID((int)row.get(15));
    	    	 		firstST.set���ϱ���((String)row.get(6));
     	    	 		 firstST.setWrite(true);
     	    	 		 update��־(firstST,2);
     	    	 		
     	    	 	
     	    	 		 ((_7ST)secondST).clear();
     	    	 		 ((_7ST)secondST).setId((int)row2.get(0));
     	    	 		 ((_7ST)secondST).set������((int)row2.get(1));
     	    	 		 ((_7ST)secondST).set�ֽ��((int)row2.get(2));
     	    	 		 ((_7ST)secondST).set�ؾ����((int)row2.get(3));
     	    	 		 ((_7ST)secondST).set����λ������־(false);
     	    	 		((_7ST)secondST).set��1���ٵ�оλ��(row2.get(12)==null?0:(int)row2.get(12));
     	    	 	    ((_7ST)secondST).set��2���ٵ�оλ��(row2.get(13)==null?0:(int)row2.get(13));
     	    	 		 ((_7ST)secondST).setģ�����ͱ�־((int)row2.get(10));
     	    	 		 ((_7ST)secondST).set��о���ͱ�־((int)row2.get(11));
     	    	 		 ((_7ST)secondST).set��������((int)row2.get(7));
     	    	 		 ((_7ST)secondST).set����RDY(plc.getSTRdy(װ����,stNum));
     	    	 		 secondST.set����ID((int)row2.get(14));
     	    	 		 secondST.setģ����ID((int)row2.get(15));
     	    	 		 secondST.set���ϱ���((String)row2.get(6));
     	    	 		 secondST.setWrite(true);
     	    	 		 update��־(secondST,2);
     	    	 		 
     	    	 	}else{
     	    	 		if(tem.size()==1){
     	    	 		 Vector row=(Vector)tem.get(0);
     	    	 		
     	    	 		 ((_7ST)firstST).clear();
     	    	 		 ((_7ST)firstST).setId((int)row.get(0));
     	    	 		 ((_7ST)firstST).set������((int)row.get(1));
     	    	 		 ((_7ST)firstST).set�ֽ��((int)row.get(2));
     	    	 		 ((_7ST)firstST).set�ؾ����((int)row.get(3));
     	    	 		 ((_7ST)firstST).set����λ������־(false);
     	    	 		((_7ST)firstST).set��1���ٵ�оλ��(row.get(12)==null?0:(int)row.get(12));
     	    	 	    ((_7ST)firstST).set��2���ٵ�оλ��(row.get(13)==null?0:(int)row.get(13));
     	    	 		 ((_7ST)firstST).setģ�����ͱ�־((int)row.get(10));
     	    	 		 ((_7ST)firstST).set��о���ͱ�־((int)row.get(11));
     	    	 		 ((_7ST)firstST).set��������((int)row.get(7));
     	    	 		 ((_7ST)firstST).set����RDY(plc.getSTRdy(װ����,stNum));
     	    	 		 firstST.set����ID((int)row.get(14));
    	    	 		 firstST.setģ����ID((int)row.get(15));
    	    	 		 firstST.set���ϱ���((String)row.get(6));
     	    	 		 firstST.setWrite(true);
     	    	 		 update��־(firstST,2);
     	    	 		}
     	    	 		
     	    	 	}
     			
     		 }
     		
     		if(firstST.isWrite()&&!secondST.isWrite()){
     			 Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о����,�ٵ�о1,�ٵ�о2,����ID,ģ����ID "
     		     		+"  from �䷽ָ�����  where  װ����="+װ����+" and IFNULL(ST��ȡ��־,0)<>1  and ��λ='7ST' ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");
 				 if(tem.size()>0){
 				    Vector row=(Vector)tem.get(0); 
 				    
 				   ((_7ST)secondST).clear();
 				   ((_7ST)secondST).setId((int)row.get(0));
 				   ((_7ST)secondST).set������((int)row.get(1));
 				   ((_7ST)secondST).set�ֽ��((int)row.get(2));
 				   ((_7ST)secondST).set�ؾ����((int)row.get(3));
 				   ((_7ST)secondST).set����λ������־(false);
 				   ((_7ST)secondST).set��1���ٵ�оλ��(row.get(12)==null?0:(int)row.get(12));
	    	 	   ((_7ST)secondST).set��2���ٵ�оλ��(row.get(13)==null?0:(int)row.get(13));
 				   ((_7ST)secondST).setģ�����ͱ�־((int)row.get(10));
 				   ((_7ST)secondST).set��о���ͱ�־((int)row.get(11));
 				   ((_7ST)secondST).set��������((int)row.get(7));
 				   ((_7ST)secondST).set����RDY(plc.getSTRdy(װ����,stNum));
 				   secondST.set����ID((int)row.get(14));
 				   secondST.setģ����ID((int)row.get(15));
 				   secondST.set���ϱ���((String)row.get(6));
 				   secondST.setWrite(true);	
 				   update��־(secondST,2);
 				 
 				 }
     		}
     		if(!firstST.isWrite()&&secondST.isWrite()){
     			//��ôsecondST�ƶ���first,Ȼ��secondST�ض�
     			
        		 firstST.intFromST(secondST);
        		 secondST.setWrite(false);
        		 secondST.clear();
     		}
     		
     		updata����();
   	     }
    	 
    	 
    	 if(stNum==9||stNum==11||stNum==12){
    		 ((_1_6ST)firstST).clear();
    		 ((_1_6ST)firstST).set����λ������־(true);
    		 ((_1_6ST)firstST).set����RDY(plc.getSTRdy(װ����,stNum));
    		 Carry ca= plc.line.getCarry(stNum-1);//����λ
    		 if(ca!=null){
    			
    	 		 ((_1_6ST)firstST).setId(ca.����ID);
    	 		 ((_1_6ST)firstST).set������(ca.������);
    	 		 ((_1_6ST)firstST).set����ID(ca.get����ID());
    	 		 ((_1_6ST)firstST).set�ֽ��(ca.�ֽ��);
    	 		 ((_1_6ST)firstST).set�ؾ����(ca.�ؾ����);	 
    			 
    		 }
    		 
    	 }
    	
    	 if(stNum==10){
    		   //ͬʱ���첽�����ߵĶ���Ҳ������
    		 int next=stNum-1;
    		 if(!firstST.isWrite()&&!secondST.isWrite()){
    			 for(int i=9;i>=0;i--){
    			 Carry car=plc.line.getCarry(i);
    			 if(car==null){continue;}
    			 String ss1= car.get��оλ��1(); String ss2= car.get��оλ��2();
    			 String ss3= car.get��оλ��3(); String ss4= car.get��оλ��4();
    			// System.out.println(car.�ؾ����+"=====================");
    		if(ss1!=null||ss2!=null||ss3!=null||ss4!=null){
    			int d1=0;int d2=0;int d3=0;int d4=0;
    			if(ss1!=null){d1=Integer.parseInt(ss1.split("=")[0]);}
    			if(ss2!=null){d2=Integer.parseInt(ss2.split("=")[0]);}
    			if(ss3!=null){d3=Integer.parseInt(ss3.split("=")[0]);}
    			if(ss4!=null){d4=Integer.parseInt(ss4.split("=")[0]);}
    		 					 
    	    ((_9ST)firstST).clear();
			((_9ST)firstST).setId(car.get����ID());
			((_9ST)firstST).set������(car.get������());
			((_9ST)firstST).set����ID(car.get����ID());
			((_9ST)firstST).setģ����ID(car.getģ����ID());
			((_9ST)firstST).set�ֽ��(car.get�ֽ��());
			((_9ST)firstST).set�ؾ����(car.�ؾ����);
			((_9ST)firstST).set����λ������־(false);
			((_9ST)firstST).setPACK��(car.get������());
			((_9ST)firstST).setģ���(car.get�ֽ��());
			((_9ST)firstST).setģ�����ͱ�־(car.getģ������());
			((_9ST)firstST).set��о���ͱ�־(car.get��о����());
			((_9ST)firstST).set��������(d1+d2+d3+d4);
			((_9ST)firstST).set��1����оλ��(d1);
			((_9ST)firstST).set��2����оλ��(d2);
			((_9ST)firstST).set��3����оλ��(d3);
			((_9ST)firstST).set��4����оλ��(d4);
			((_9ST)firstST).set����RDY(plc.getSTRdy(װ����,stNum));
			((_9ST)firstST).setWrite(true);
			  next=i;
			    break;
    			 }
    		
    			 }//end for
    	 for(int i=next-1;i>=0;i--){
        			 Carry car=plc.line.getCarry(i);
        			 if(car==null){continue;}
        			 String ss1= car.get��оλ��1(); String ss2= car.get��оλ��2();
        			 String ss3= car.get��оλ��3(); String ss4= car.get��оλ��4();
        			// System.out.println(car.�ؾ����+"=2====================");
        		if(ss1!=null||ss2!=null||ss3!=null||ss4!=null){
        			int d1=0;int d2=0;int d3=0;int d4=0;
        			if(ss1!=null){d1=Integer.parseInt(ss1.split("=")[0]);}
        			if(ss2!=null){d2=Integer.parseInt(ss2.split("=")[0]);}
        			if(ss3!=null){d3=Integer.parseInt(ss3.split("=")[0]);}
        			if(ss4!=null){d4=Integer.parseInt(ss4.split("=")[0]);}
        		 					 
        			    ((_9ST)secondST).clear();
        				((_9ST)secondST).setId(car.get����ID());
        				((_9ST)secondST).set������(car.get������());
        				((_9ST)secondST).set����ID(car.get����ID());
        				((_9ST)secondST).setģ����ID(car.getģ����ID());
        				((_9ST)secondST).set�ֽ��(car.get�ֽ��());
        				((_9ST)secondST).set�ؾ����(car.�ؾ����);
        				((_9ST)secondST).set����λ������־(false);
        				((_9ST)secondST).setPACK��(car.get������());
        				((_9ST)secondST).setģ���(car.get�ֽ��());
        				((_9ST)secondST).setģ�����ͱ�־(car.getģ������());
        				((_9ST)secondST).set��о���ͱ�־(car.get��о����());
        				((_9ST)secondST).set��������(d1+d2+d3+d4);
        				((_9ST)secondST).set��1����оλ��(d1);
        				((_9ST)secondST).set��2����оλ��(d2);
        				((_9ST)secondST).set��3����оλ��(d3);
        				((_9ST)secondST).set��4����оλ��(d4);
        				((_9ST)secondST).set����RDY(plc.getSTRdy(װ����,stNum));
        				((_9ST)secondST).setWrite(true);
    			  
    			    break;
        			 }
        		
        			 }//end for
        		
    			
    		 }
    
   if(firstST.isWrite()&&!secondST.isWrite()){
    			 for(int i=9;i>=0;i--){
        			 Carry car=plc.line.getCarry(i);
        			 if(car==null){continue;}
        			 String s=firstST.get����ID()+""+firstST.getģ����ID()+""+firstST.get�ֽ��()+""+firstST.get�ؾ����();
        			 String s2=car.get����ID()+""+car.getģ����ID()+""+car.get�ֽ��()+""+car.get�ؾ����();
        			 if(s.equals(s2)){continue;}
        			 String ss1= car.get��оλ��1(); String ss2= car.get��оλ��2();
        			 String ss3= car.get��оλ��3(); String ss4= car.get��оλ��4();
        			 //System.out.println(s+"="+s2+"=3====================");
        		if(ss1!=null||ss2!=null||ss3!=null||ss4!=null){
        			int d1=0;int d2=0;int d3=0;int d4=0;
        			if(ss1!=null){d1=Integer.parseInt(ss1.split("=")[0]);}
        			if(ss2!=null){d2=Integer.parseInt(ss2.split("=")[0]);}
        			if(ss3!=null){d3=Integer.parseInt(ss3.split("=")[0]);}
        			if(ss4!=null){d4=Integer.parseInt(ss4.split("=")[0]);}
        	 	 					 
        		((_9ST)secondST).clear();
 				((_9ST)secondST).setId(car.get����ID());
 				((_9ST)secondST).set������(car.get������());
 				((_9ST)secondST).set����ID(car.get����ID());
 				((_9ST)secondST).setģ����ID(car.getģ����ID());
 				((_9ST)secondST).set�ֽ��(car.get�ֽ��());
 				((_9ST)secondST).set�ؾ����(car.�ؾ����);
 				((_9ST)secondST).set����λ������־(false);
 				((_9ST)secondST).setPACK��(car.get������());
 				((_9ST)secondST).setģ���(car.get�ֽ��());
 				((_9ST)secondST).setģ�����ͱ�־(car.getģ������());
 				((_9ST)secondST).set��о���ͱ�־(car.get��о����());
 				((_9ST)secondST).set��������(d1+d2+d3+d4);
 				((_9ST)secondST).set��1����оλ��(d1);
 				((_9ST)secondST).set��2����оλ��(d2);
 				((_9ST)secondST).set��3����оλ��(d3);
 				((_9ST)secondST).set��4����оλ��(d4);
 				((_9ST)secondST).set����RDY(plc.getSTRdy(װ����,stNum));
 				((_9ST)secondST).setWrite(true);
    			    break;
        			 }
        		
        			 }//end for 
    			 
    		 }
    		 
    		 if(!firstST.isWrite()&&secondST.isWrite()){
    			 firstST.intFromST(secondST);
        		 secondST.setWrite(false);
        		 secondST.clear();
    			 
    		 }
    		
    		 updata����();
    	 }
    	 
    	 if(stNum==13){
   		    //��װ��λ,���ؾߵ���ʱ��
    		 if(!firstST.isWrite()){
    			 Carry car=plc.line.getCarry(12); 
    			 if(car!=null){
    				 ((_12ST)firstST).clear();
	    	 		 ((_12ST)firstST).setId(car.get����ID());
	    	 		 ((_12ST)firstST).set������(car.get������());
	    	 		 ((_12ST)firstST).set�ֽ��(car.get�ֽ��());
	    	 		 ((_12ST)firstST).set�ؾ����(car.get�ؾ����());
	    	 		 ((_12ST)firstST).set����λ������־(true);
	    	 		 //((_12ST)firstST).setͶ����ǻ��־(false);
	    	 		 ((_12ST)firstST).setģ�����ͱ�־(car.getģ������());
	    	 		 ((_12ST)firstST).set��о���ͱ�־(car.get��о����());
                    // ((_12ST)firstST).set��������(0);
	    	 		 ((_12ST)firstST).set����RDY(plc.getSTRdy(װ����,stNum));
	    	 		 firstST.set����ID(car.get����ID());
	    	 		 firstST.setģ����ID(car.getģ����ID());
	    	 		 firstST.setWrite(true);	 
    				 
    			 }
    		 }
   	      }
    	 
    	 if(stNum==14){
    		    //ȡ����λ
    		   Carry car=plc.line.getCarry(13); 
    		   
    	      }
    	 if(stNum==15){
 		    //��������
    		  Carry car=plc.line.getCarry(14); 
 	      }
    	 
    	 if(stNum==16){
  		    //ͬ��������
    		
    		 if(!firstST.isWrite()&&!secondST.isWrite()){
    			 Vector tem=new Vector();
    			 for(int i=12;i>=0;i--){
    			 Carry car=plc.line.getCarry(i);
    			
    			 if(car==null){continue;}
    			 String ss1= car.get��оλ��1(); String ss2= car.get��оλ��2();
    			 String ss3= car.get��оλ��3(); String ss4= car.get��оλ��4();
    			 if(ss1!=null){
    				 String sm[]=ss1.split("=");
    				 if(sm.length==2){
    					 Vector row=new Vector();
    					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
    					 tem.add(row);
    					 System.out.println(tem);
    					 car.set��оλ��1(ss1+"=���ϴ�");
    					 if(tem.size()==2){break;}
    				 }
    			 }
    			 if(ss2!=null){
    				 String sm[]=ss2.split("=");
    				 if(sm.length==2){
    					 Vector row=new Vector();
    					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
    					 tem.add(row);
    					 car.set��оλ��2(ss2+"=���ϴ�");
    					 if(tem.size()==2){break;}
    				 }
    			 }
    			 if(ss3!=null){
    				 String sm[]=ss3.split("=");
    				 if(sm.length==2){
    					 Vector row=new Vector();
    					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
    					 tem.add(row);
    					 car.set��оλ��2(ss3+"=���ϴ�");
    					 if(tem.size()==2){break;}
    				 }
    			 }
    			 
    			 if(ss4!=null){
    				 String sm[]=ss4.split("=");
    				 if(sm.length==2){
    					 Vector row=new Vector();
    					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
    					 tem.add(row);
    					 car.set��оλ��2(ss4+"=���ϴ�");
    					 if(tem.size()==2){break;}
    				 }
    			 }
    			 }
    		if(tem.size()>0){
    			Vector row=(Vector)tem.get(0);
    			Carry car=(Carry)row.get(0);
    			boolean fan=(boolean)row.get(1);
    	    ((_DST)firstST).clear();
			((_DST)firstST).setId(car.get����ID());
			((_DST)firstST).set������(car.get������());
			((_DST)firstST).setģ����ID(car.getģ����ID());
			((_DST)firstST).set�ֽ��(car.get�ֽ��());
			((_DST)firstST).set�ؾ����(car.�ؾ����);
			((_DST)firstST).set����λ������־(true);
		    ((_DST)firstST).set��о���ͱ�־(car.get��о����());
			((_DST)firstST).set��B��(fan);
			
			((_DST)firstST).set����RDY(plc.getSTRdy(װ����,stNum));
			((_DST)firstST).setWrite(true);
			  
    			 }
    		
    		if(tem.size()>1){
    			Vector row=(Vector)tem.get(1);
    			Carry car=(Carry)row.get(0);
    			boolean fan=(boolean)row.get(1);
        	    ((_DST)secondST).clear();
    			((_DST)secondST).setId(car.get����ID());
    			((_DST)secondST).set������(car.get������());
    			((_DST)secondST).setģ����ID(car.getģ����ID());
    			((_DST)secondST).set�ֽ��(car.get�ֽ��());
    			((_DST)secondST).set�ؾ����(car.�ؾ����);
    			((_DST)secondST).set����λ������־(true);
    		    ((_DST)secondST).set��о���ͱ�־(car.get��о����());
    			((_DST)secondST).set��B��(fan);
    			
    			((_DST)secondST).set����RDY(plc.getSTRdy(װ����,stNum));
    			((_DST)secondST).setWrite(true);
    			  
        			 }
        		
    		
    			
    
        		
    			
    		 }
    		 if(firstST.isWrite()&&!secondST.isWrite()){
    			 Vector tem=new Vector();
    			 for(int i=12;i>=0;i--){
        			 Carry car=plc.line.getCarry(i);
        			
        			 if(car==null){continue;}
        			 String ss1= car.get��оλ��1(); String ss2= car.get��оλ��2();
        			 String ss3= car.get��оλ��3(); String ss4= car.get��оλ��4();
        			 if(ss1!=null){
        				 String sm[]=ss1.split("=");
        				 if(sm.length==2){
        					 Vector row=new Vector();
        					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
        					 tem.add(row);
        					 car.set��оλ��1(ss1+"=���ϴ�");
        					 if(tem.size()==1){break;}
        				 }
        			 }
        			 if(ss2!=null){
        				 String sm[]=ss2.split("=");
        				 if(sm.length==2){
        					 Vector row=new Vector();
        					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
        					 tem.add(row);
        					 car.set��оλ��2(ss2+"=���ϴ�");
        					 if(tem.size()==1){break;}
        				 }
        			 }
        			 if(ss3!=null){
        				 String sm[]=ss3.split("=");
        				 if(sm.length==2){
        					 Vector row=new Vector();
        					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
        					 tem.add(row);
        					 car.set��оλ��2(ss3+"=���ϴ�");
        					 if(tem.size()==1){break;}
        				 }
        			 }
        			 
        			 if(ss4!=null){
        				 String sm[]=ss4.split("=");
        				 if(sm.length==2){
        					 Vector row=new Vector();
        					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
        					 tem.add(row);
        					 car.set��оλ��2(ss4+"=���ϴ�");
        					 if(tem.size()==1){break;}
        				 }
        			 }
    			 }
        		if(tem.size()==1){
        			Vector row=(Vector)tem.get(0);
        			Carry car=(Carry)row.get(0);
        			boolean fan=(boolean)row.get(1);
        	    ((_DST)secondST).clear();
    			((_DST)secondST).setId(car.get����ID());
    			((_DST)secondST).set������(car.get������());
    			((_DST)secondST).setģ����ID(car.getģ����ID());
    			((_DST)secondST).set�ֽ��(car.get�ֽ��());
    			((_DST)secondST).set�ؾ����(car.�ؾ����);
    			((_DST)secondST).set����λ������־(true);
    		    ((_DST)secondST).set��о���ͱ�־(car.get��о����());
    			((_DST)secondST).set��B��(fan);
    			
    			((_DST)secondST).set����RDY(plc.getSTRdy(װ����,stNum));
    			((_DST)secondST).setWrite(true);
    			  
        			 }
        	
        		
        		
    			 
    		 }
    		 
    		 if(!firstST.isWrite()&&secondST.isWrite()){
    			 firstST.intFromST(secondST);
        		 secondST.setWrite(false);
        		 secondST.clear();
    			 
    		 }
     		  
  	      }
    	 
    	 
    	 firstST.writeifChangeToPLC();
    	 secondST.writeifChangeToPLC();
    	 
    	
    }
    
    public void updata����(){//���¶��������־
    	
    	int next=stNum-1;
    	
		//���µ�һ����λ���������־
		if(firstST.isWrite()){
			Carry car=plc.line.getCarry(stNum-1);
			if(car!=null){
			String id1=firstST.get����ID()+""+firstST.getģ����ID()+""+firstST.get�ֽ��()+firstST.get�ؾ����();
			String id2=car.get����ID()+""+car.getģ����ID()+""+car.get�ֽ��()+car.get�ؾ����();
			next=stNum-1;
			 if(id1.equals(id2)){
				
    				 if(firstST instanceof _1_6ST )
    				( (_1_6ST)firstST).set����λ������־(true);
    				 if(firstST instanceof _7ST )
		    		 ( (_7ST)firstST).set����λ������־(true);
    				 if(firstST instanceof _9ST ){
			    	 ( (_9ST)firstST).set����λ������־(true);
			    	
    				 }
    			   
			    }
			  }else{
				  Carry car2=plc.line.getCarry(stNum-2);  
				  if(car2!=null){
					  String id1=firstST.get����ID()+""+firstST.getģ����ID()+""+firstST.get�ֽ��()+firstST.get�ؾ����();
		    		  String id2=car2.get����ID()+""+car2.getģ����ID()+""+car2.get�ֽ��()+car2.get�ؾ����();
		    		  next=stNum-2;
		    			 if(id1.equals(id2)){
		    				 if(firstST instanceof _1_6ST )
		    				( (_1_6ST)firstST).set����λ������־(true);
		    				 if(firstST instanceof _7ST )
				    		 ( (_7ST)firstST).set����λ������־(true);
		    				 if(firstST instanceof _9ST ){
					    		 ( (_9ST)firstST).set����λ������־(true);
					    		 
		    				   }
		    			    }
					  
				  }
			  }
			firstST.writeifChangeToPLC();
		}
		
		//���µڶ�����λ���������־
		if(secondST.isWrite()){
			if(next==0) return;
			Carry car=plc.line.getCarry(next-1);
			if(car!=null){
			String id1=secondST.get����ID()+""+secondST.getģ����ID()+""+secondST.get�ֽ��()+secondST.get�ؾ����();
			String id2=car.get����ID()+""+car.getģ����ID()+""+car.get�ֽ��()+car.get�ؾ����();
			
			 if(id1.equals(id2)){
				 if(secondST instanceof _1_6ST )
				( (_1_6ST)secondST).set����λ������־(true);
				 if(secondST instanceof _7ST )
				( (_7ST)secondST).set����λ������־(true);
				 if(secondST instanceof _9ST ){
				( (_9ST)secondST).set����λ������־(true);
				System.out.println("____+++++++___");
				
				 }
			    }
			  }
			secondST.writeifChangeToPLC();
		}
    }
    
    
    
    private boolean updataSTDB(int ��λ, ST_Father st){
    	if(stNum<2||stNum>8){return false;}
    	if(stNum>=2&&stNum<=7){
  	  String tem=SqlTool.findOneRecord("select  ����,����  from �䷽ָ�����   where  ID='"+st.getId()+"'");
  	  if(tem!=null){
  		String wuliao=tem.split("!_!")[0];
  		int shul=Integer.parseInt(tem.split("!_!")[1]);
  		String tem2=SqlTool.findOneRecord("select  ���̱��,����,����  from �������   where  ��λ��='"+��λ+"'");
  		
  		if(tem2!=null){
  			String tp=tem2.split("!_!")[0];
  			String wuliao2=tem2.split("!_!")[1];	
  			int shul2=Integer.parseInt(tem2.split("!_!")[2]);
  			if(shul2==0){return false;}
  			if(wuliao.equals(wuliao2)){
  			 //������������	
  			   int ��������=((_1_6ST)st).get��������();
  			   int �������=((_1_6ST)st).get�������();
  			   int fshul=��������-�������;
  			   
  			   if(shul2>=fshul){
  				   String sql="update ������� set ����="+(shul2-fshul)+" where ���̱��="+"'"+tp+"'";
  				   String sql2="update �䷽ָ�����  set �������="+(�������+fshul)+" where ID="+"'"+st.getId()+"'";
  				   SqlTool.insert(new String[]{sql});
  				   SqlTool.insert(new String[]{sql2});
  				   ((_1_6ST)st).set�������(�������+fshul);
  				    return true;
  			   }else{
  				 /* String sql="update ������� set ����="+0+" where ���̱��="+"'"+tp+"'";
  				  String sql2="update �䷽ָ�����  set �������="+(�������+shul2)+" where ID="+"'"+st.getId()+"'";
  				  SqlTool.insert(new String[]{sql});
  				  SqlTool.insert(new String[]{sql2});
  				   ((_1_6ST)st).set�������(�������+shul2);*/
  				   //���������������̵�������ʱ������������������Ӧ����
  				    return false;  
  				   
  			   }
  				
  			 }
  		}
  		  
  	  }
  	  }
    	
    	if(stNum==8){//��о������һ��ֻ��ȡһ��
    		 String tem=SqlTool.findOneRecord("select  ����,����  from �䷽ָ�����   where  ID='"+st.getId()+"'");
    	  	  if(tem!=null){
    	  		String wuliao=tem.split("!_!")[0];
    	  		int shul=Integer.parseInt(tem.split("!_!")[1]);
    	  		String tem2=SqlTool.findOneRecord("select  ���̱��,����,����  from �������   where  ��λ��='"+��λ+"'");
    	  		
    	  		if(tem2!=null){
    	  			String tp=tem2.split("!_!")[0];
    	  			String wuliao2=tem2.split("!_!")[1];	
    	  			int shul2=Integer.parseInt(tem2.split("!_!")[2]);
    	  			if(shul2==0){return false;}
    	  			if(wuliao.equals(wuliao2)){
    	  			 //������������	
    	  			   int ��������=((_7ST)st).get��������();
    	  			   int �������=((_7ST)st).get�������();
    	  			   int fshul=��������-�������;
    	  			   if(shul2>=1){
    	  				   String sql="update ������� set ����="+(shul2-1)+" where ���̱��="+"'"+tp+"'";
    	  				   String sql2="update �䷽ָ�����  set �������="+(�������+1)+" where ID="+"'"+st.getId()+"'";
    	  				   SqlTool.insert(new String[]{sql});
    	  				   SqlTool.insert(new String[]{sql2});
    	  				   ((_7ST)st).set�������(�������+1);
    	  				    return true;
    	  			   }else{
    	  				  /*String sql="update ������� set ����="+0+" where ���̱��="+"'"+tp+"'";
    	  				  String sql2="update �䷽ָ�����  set �������="+(�������+shul2)+" where ID="+"'"+st.getId()+"'";
    	  				  SqlTool.insert(new String[]{sql});
    	  				  SqlTool.insert(new String[]{sql2});
    	  				  ((_7ST)st).set�������(�������+shul2);*/
    	  				//���������������̵�������ʱ������������������Ӧ����
    	  				    return false;  
    	  				   
    	  			   }
    	  				
    	  			 }
    	  		}
    	  		  
    	  	  }		
    		
    	}
    	return false;
    }
    
    public boolean  updataDB(ST_Father st){
    	if(װ����==1){
    		//�������������̵�����
    	if(stNum==2){//1ST
    		return updataSTDB(502, st);
    	 
    	}
        if(stNum==3){//2ST
        	return updataSTDB(504, st);
    		
    	 }
        if(stNum==4){//2ST
        	return updataSTDB(506, st);
    		
    	 }
        if(stNum==5){//2ST
        	return updataSTDB(508, st);
    		
    	 }
        if(stNum==6){//2ST
        	return updataSTDB(510, st);
    		
    	 }
        if(stNum==7){//2ST
        	return updataSTDB(512, st);
    		
    	 }
        if(stNum==8){//2ST
        	return updataSTDB(514, st);
    		
    	 }
        
    		}
    	
    	if(װ����==2){
    		//�������������̵�����
    	if(stNum==2){//1ST
    		return updataSTDB(602, st);
    	 
    	}
        if(stNum==3){//2ST
        	return updataSTDB(604, st);
    		
    	 }
        if(stNum==4){//2ST
        	return updataSTDB(606, st);
    		
    	 }
        if(stNum==5){//2ST
        	return updataSTDB(608, st);
    		
    	 }
        if(stNum==6){//2ST
        	return updataSTDB(610, st);
    		
    	 }
        if(stNum==7){//2ST
        	return updataSTDB(612, st);
    		
    	 }
        if(stNum==8){//2ST
        	return updataSTDB(614, st);
    		
    	 }
        
    		}
    	
    	  return false;
    }
   
}
