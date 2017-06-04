package alai.znyk.plc;

import java.io.Serializable;
import java.util.Vector;

import alai.znyk.server.SqlTool;

public class STContent implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int checkNum_Ԥװ=8;
	public static int checkNum_ͬ��=6;
	
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
   
  
    public synchronized void initFromSql(){
    	
    	if(plc.getIntance().stop1){//���������ݿ���������ˣ����ǲ�Ӱ����˻����ļ���ִ��
    		if(װ����==1){
    		   //System.out.println("return----");
    			if(stNum==1){ 
    				// if(PLC.getIntance().line.getCarry(0)==null){
    	    			 ((_FST)firstST).set����λ������־(false);
    	    			 ((_FST)secondST).set����λ������־(false); 
    	    			 
    	    		// }
    				 firstST.writeifChangeToPLC();
    		    	 secondST.writeifChangeToPLC();
    		    	 ////
    				return;}	
    			if(stNum>=2&&stNum<=8){
    				updata����();
    				return;}
    			
    		}
    	}else{ 
    		if(װ����==1){ 
     		   //System.out.println("return----");
     			if(stNum==1){ 
     				 if(PLC.getIntance().line.getCarry(0)!=null){
     	    			 ((_FST)firstST).set����λ������־(true);
     	    			 ((_FST)secondST).set����λ������־(true); 
     	    			 
     	    		 }
     				
     		    	  }	
     			
     			
     		}	
    		
    	}
    	
    	if(plc.getIntance().stop2){ 
    		if(װ����==2){
    			if(stNum==1){
    				// if(PLC.getIntance().line2.getCarry(0)==null){
    	    			 ((_FST)firstST).set����λ������־(false); 
    	    			 ((_FST)secondST).set����λ������־(false); 
    	    			 
    	    		// }
    				 firstST.writeifChangeToPLC();
    		    	 secondST.writeifChangeToPLC();
    		    	 
    				return;}	
    			if(stNum>=2&&stNum<=8){
    				updata����();
    				return;}
    			
    		}
    		
    	}else{
    		if(װ����==2){ 
      		   //System.out.println("return----");
      			if(stNum==1){ 
      				 if(PLC.getIntance().line2.getCarry(0)!=null){
      	    			 ((_FST)firstST).set����λ������־(true);
      	    			 ((_FST)secondST).set����λ������־(true); 
      	    			 
      	    		 }
      				
      		    	  }	
      			
      			
      		}	
    		
    	}
    	
    	
    	if(stNum==1){
    		//System.out.println("--------------------"+plc.getIntance().stop1);
    		//���������־Ĭ��TURE
         //ǰ����̨,��������+�ֽ��+ģ����루ģ��ţ�����������ؾߣ����ؾ��������
    	//�����ݿ��ȡ�ɹ���,"ǰ��������ȡ��־=1",ϵͳ������ǰ�ǲ����ڴ��¶�ȡ�����ܵ��ؾ߷��к󣬰������־����Ϊ2��ʾ��ɣ��Ժ���Ҳ��������ݿ��ж�ȡ
    	if(!firstST.isWrite()&&!secondST.isWrite()){
    		 //��ȡǰ����ָ���˳��д��1��2��ָ����У��������ֻ�ᷢ����ϵͳ��һ������	
    		 //Ȼ���Write=true;
    		// �������=PACK��ţ��ֽ��=��������������ģ��ֳɵ���ţ�
    		//����ID+ģ����ID+�ֽ��+�ؾ����,��4��Ҳ������Ψһ���ؾ�
    		
    	 firstST.set���ݴ�����(false);secondST.set���ݴ�����(false);
    	 Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ,����ID,ģ����ID,IFNULL(�ٵ�о1,0),IFNULL(�ٵ�о2,0),��оλ��1,��оλ��2,��оλ��3,��оλ��4,IFNULL(��װ��,'��'),ģ������,��о����  ,pack����,ģ�����,�ؾ�λ��, ģ�����,COUNT(DISTINCT �������,ģ����ID,�ֽ��,�ؾ���� )  from �䷽ָ�����    where  װ����="+װ����+"   and IFNULL(ǰ������־,0)<>1 GROUP BY �������,ģ�����,�ֽ��,�ؾ����   ORDER BY �������,ģ�����,�ֽ��,�ؾ���� LIMIT 10");
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
    	 		 ((_FST)firstST).set����λ������־(false);
    	 		 ((_FST)firstST).set����RDY(false);
    	 		  firstST.set����ID(row.get(10)==null?0:(int)row.get(10));
    	 		  firstST.setģ����ID(row.get(11)==null?0:(int)row.get(11));
    	 		  firstST.setWrite(true);
    	 		 // firstST.writeToPLC();
    	 		  update��־(firstST,1);
    	 		 //�����Ϊ��ʱ��Ӳ��˵ģ�ÿ���յ����е��źź󣬻��Զ��ƶ�����ʱ��������ΪNULL�ġ�
    	     	 Carry carr=new Carry(firstST.get������(), firstST.get�ֽ��(), firstST.get�ؾ����(),firstST.getģ����ID());
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
    	     	 carr.setģ�����(row.get(22)==null?0:Integer.parseInt(row.get(22).toString()));
    	     	 carr.setģ�����(row.get(24)==null?0:Integer.parseInt(row.get(24).toString()));
    	     	 carr.setģ�����(row.get(5)==null?"0":row.get(5).toString());
    	     	 Vector<Vector> tost=SqlTool.findInVector("select ��λ,���� from �䷽ָ�����   where ����ID='"+
    	     	 carr.get����ID()+"' and ģ����ID='"+carr.getģ����ID()+"' and �ֽ��='"+carr.get�ֽ��()+"' and �ؾ����='"+carr.get�ؾ����()+"' order by ��λ");
    	     	 String beifangs="";
    	     	 for(int m=0;m<tost.size();m++){
    	     		 Vector vv=(Vector)tost.get(m);
    	     	     carr.set��λ(carr.get��λ()+""+vv.get(0)); 
    	     	     beifangs= (vv.get(1)==null?"":vv.get(1))+"!_!"+beifangs;
    	         }
    	     	 if(!beifangs.equals("")){
    	     		beifangs=beifangs.substring(0,  beifangs.length()-3) ;
    	     	    }
    	     	 carr.set�䷽(beifangs);
    	     	 if(װ����==1){
    	  		 PLC.getIntance().line.addFist(carr);
    	  		 ((_FST)firstST).set����λ������־(true);
    	  		 }
    	     	 else{
    	     	 PLC.getIntance().line2.addFist(carr); 
    	     	 ((_FST)firstST).set����λ������־(true);
    	     	 System.out.println("line2---------");
    	  		 }
    	 		 
    	 	 ((_FST)secondST).clear();
   	 		 ((_FST)secondST).setId(row2.get(0)==null?0:(int)row2.get(0));
   	 		 ((_FST)secondST).set������(row2.get(1)==null?0:(int)row2.get(1));
   	 		 ((_FST)secondST).set�ֽ��(row2.get(2)==null?0:(int)row2.get(2));
   	 		 ((_FST)secondST).set�ؾ����(row2.get(3)==null?0:(int)row2.get(3));
   	 		 ((_FST)secondST).set��B��(row2.get(8).equals("��")?true:false);
   	 		 ((_FST)secondST).set����λ������־(false);
   	 		 ((_FST)secondST).set����RDY(false);
   	 	     secondST.set����ID(row2.get(10)==null?0:(int)row2.get(10));
   	         secondST.setģ����ID(row2.get(11)==null?0:(int)row2.get(11));
   	 		 secondST.setWrite(true);
   	 		 update��־(secondST,1);
   	 		 
   	 	 Carry carr2=new Carry( secondST.get������(),  secondST.get�ֽ��(),  secondST.get�ؾ����(), secondST.getģ����ID());
   	 	 carr2.set�ٵ�о1(row2.get(12).toString().equals("0")?0:(int)row2.get(12));
    	 carr2.set�ٵ�о2(row2.get(13).toString().equals("0")?0:(int)row2.get(13));
     	 carr2.set��оλ��1(row2.get(14)==null?null:row2.get(14).toString());
     	 carr2.set��оλ��2(row2.get(15)==null?null:row2.get(15).toString());
     	 carr2.set��оλ��3(row2.get(16)==null?null:row2.get(16).toString());
     	 carr2.set��оλ��4(row2.get(17)==null?null:row2.get(17).toString());
     	 carr2.set��װ��(row2.get(18).equals("��")?true:false);
     	 carr2.setģ������(row2.get(19)==null?0:(int)row2.get(19));
     	 carr2.set��о����(row2.get(20)==null?0:(int)row2.get(20));
     	 carr2.set����ID(row2.get(10)==null?0:(int)row2.get(10));
     	 carr2.setPack����(row2.get(21)==null?0:Integer.parseInt(row2.get(21).toString()));
     	 carr2.setģ�����(row2.get(22)==null?0:Integer.parseInt(row2.get(22).toString()));
     	 carr2.setģ�����(row2.get(24)==null?0:Integer.parseInt(row2.get(24).toString()));
     	 carr2.setģ�����(row2.get(5)==null?"0":row2.get(5).toString());
     	 String beifangs2="";	 
     	 Vector<Vector> tost2=SqlTool.findInVector("select ��λ,���� from �䷽ָ�����   where ����ID='"+
    	     	 carr2.get����ID()+"' and ģ����ID='"+carr.getģ����ID()+"' and �ֽ��='"+carr2.get�ֽ��()+"' and �ؾ����='"+carr2.get�ؾ����()+"' order by ��λ");
    	     	 for(int m=0;m<tost2.size();m++){
    	     		 Vector vv=(Vector)tost2.get(m);
    	     		 carr2.set��λ(carr2.get��λ()+""+vv.get(0));
    	     		 beifangs2= (vv.get(1)==null?"":vv.get(1))+"!_!"+beifangs2;
    	         }
    	     	 
    	     	 if(!beifangs2.equals("")){
     	     		beifangs2=beifangs2.substring(0,  beifangs2.length()-3) ;
     	     	    }
     	     	 carr2.set�䷽(beifangs2);
    	  
    	    if(װ����==1) 	 {
  		          PLC.getIntance().line.setBuffer(carr2);
  		        ((_FST)secondST).set����λ������־(true);}
    	          
    	    else  {PLC.getIntance().line2.setBuffer(carr2);
    	         ((_FST)secondST).set����λ������־(true);}
    	 		 
    	 	}else{
    	 		if(tem.size()==1){
    	 		 Vector row=(Vector)tem.get(0);
    	 		 ((_FST)firstST).clear();
    	 		 ((_FST)firstST).setId(row.get(0)==null?0:(int)row.get(0));
    	 		 ((_FST)firstST).set������(row.get(1)==null?0:(int)row.get(1));
    	 		 ((_FST)firstST).set�ֽ��(row.get(2)==null?0:(int)row.get(2));
    	 		 ((_FST)firstST).set�ؾ����(row.get(3)==null?0:(int)row.get(3));
       	 		 ((_FST)firstST).set��B��(row.get(8).equals("��")?true:false);
       	 		 ((_FST)firstST).set����λ������־(false);
       	 		 ((_FST)firstST).set����RDY(false);
       	 	    firstST.set����ID(row.get(10)==null?0:(int)row.get(10));
	 		    firstST.setģ����ID(row.get(11)==null?0:(int)row.get(11));
       	 		firstST.setWrite(true);
       	 	    update��־(firstST,1);
       	 	    
       	 	 Carry carr=new Carry(firstST.get������(), firstST.get�ֽ��(), firstST.get�ؾ����(),firstST.getģ����ID());
       	 	 carr.set�ٵ�о1(row.get(12).toString().equals("0")?0:(int)row.get(12));
	     	 carr.set�ٵ�о2(row.get(13).toString().equals("0")?0:(int)row.get(13));
	     	 carr.set��оλ��1(row.get(14)==null?null:row.get(14).toString());
	     	 carr.set��оλ��2(row.get(15)==null?null:row.get(15).toString());
	     	 carr.set��оλ��3(row.get(16)==null?null:row.get(16).toString());
	     	 carr.set��оλ��4(row.get(17)==null?null:row.get(17).toString());
	     	 carr.set��װ��(row.get(18).equals("��")?true:false);
	     	 carr.set����ID(row.get(10)==null?0:(int)row.get(10));
	     	 carr.setģ������(row.get(19)==null?0:(int)row.get(19));
	     	 carr.set��о����(row.get(20)==null?0:(int)row.get(20));
	     	 carr.setPack����(row.get(21)==null?0:Integer.parseInt(row.get(21).toString()));
	     	 carr.setģ�����(row.get(22)==null?0:Integer.parseInt(row.get(22).toString()));
	     	 carr.setģ�����(row.get(24)==null?0:Integer.parseInt(row.get(24).toString()));
	     	 carr.setģ�����(row.get(5)==null?"0":row.get(5).toString());
	     	Vector<Vector> tost=SqlTool.findInVector("select ��λ,���� from �䷽ָ�����   where ����ID='"+
	    	     	 carr.get����ID()+"' and ģ����ID='"+carr.getģ����ID()+"' and �ֽ��='"+carr.get�ֽ��()+"' and �ؾ����='"+carr.get�ؾ����()+"' order by ��λ");
	    	     	
	    	     	 String beifangs="";
	    	     	 for(int m=0;m<tost.size();m++){
	    	     		 Vector vv=(Vector)tost.get(m);
	    	     	     carr.set��λ(carr.get��λ()+""+vv.get(0)); 
	    	     	     beifangs= (vv.get(1)==null?"":vv.get(1))+"!_!"+beifangs;
	    	         }
	    	     	 if(!beifangs.equals("")){
	    	     		beifangs=beifangs.substring(0,  beifangs.length()-3) ;
	    	     	    }
	    	     	 carr.set�䷽(beifangs);     	 
	    	     	 if(װ����==1) {	 
	  		             PLC.getIntance().line.addFist(carr);
	  		           ((_FST)firstST).set����λ������־(true);}
	    	     	 else  {PLC.getIntance().line2.addFist(carr);
	    	     	      ((_FST)firstST).set����λ������־(true);
	    	     	 }
    	 		}
    	 		
    	 	}
                 }
    	
    	 if(firstST.isWrite()&&!secondST.isWrite()){
    		  //�����һ��ָ���ж��У��ڶ���û�У���ô��д��ڶ���ָ���ȡ��һ�����ݿ�Ķ�����ǰ��������ȡ��־=0�ĵ�һ����¼��
    		 Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ,����ID,ģ����ID,IFNULL(�ٵ�о1,0),IFNULL(�ٵ�о2,0),��оλ��1,��оλ��2,��оλ��3,��оλ��4,IFNULL(��װ��,'��') ,ģ������,��о���� ,pack����,ģ�����,�ؾ�λ��,ģ�����,COUNT(DISTINCT �������,ģ����ID,�ֽ��,�ؾ���� )  from �䷽ָ�����  where  װ����="+װ����+"      and IFNULL(ǰ������־,0)<>1 GROUP BY �������,ģ�����,�ֽ��,�ؾ����   ORDER BY �������,ģ�����,�ֽ��,�ؾ���� LIMIT 10");
    		 if(tem.size()>0){
    	 		 Vector row=(Vector)tem.get(0);
    	 		 ((_FST)secondST).clear();
    	 		 ((_FST)secondST).setId(row.get(0)==null?0:(int)row.get(0));
       	 		 ((_FST)secondST).set������(row.get(1)==null?0:(int)row.get(1));
       	 		 ((_FST)secondST).set�ֽ��(row.get(2)==null?0:(int)row.get(2));
       	 		 ((_FST)secondST).set�ؾ����(row.get(3)==null?0:(int)row.get(3));
       	 		 ((_FST)secondST).set��B��(row.get(8).equals("��")?true:false);
       	 		 ((_FST)secondST).set����λ������־(true);
       	 		 ((_FST)secondST).set����RDY(false);
       	 		secondST.set����ID(row.get(10)==null?0:(int)row.get(10));
       	 	    secondST.setģ����ID(row.get(11)==null?0:(int)row.get(11));
       	 		secondST.setWrite(true);
       	 	     update��־(secondST,1);
       	 	 Carry carr2=new Carry( secondST.get������(),  secondST.get�ֽ��(),  secondST.get�ؾ����(), secondST.getģ����ID());
       	 	 carr2.set�ٵ�о1(row.get(12).toString().equals("0")?0:(int)row.get(12));
	     	 carr2.set�ٵ�о2(row.get(13).toString().equals("0")?0:(int)row.get(13));
         	 carr2.set��оλ��1(row.get(14)==null?null:row.get(14).toString());
         	 carr2.set��оλ��2(row.get(15)==null?null:row.get(15).toString());
         	 carr2.set��оλ��3(row.get(16)==null?null:row.get(16).toString());
         	 carr2.set��оλ��4(row.get(17)==null?null:row.get(17).toString());
         	 carr2.set��װ��(row.get(18).equals("��")?true:false);
         	 carr2.setģ������(row.get(19)==null?0:(int)row.get(19));
        	 carr2.set��о����(row.get(20)==null?0:(int)row.get(20));
        	 carr2.set����ID(row.get(10)==null?0:(int)row.get(10));
        	 carr2.setPack����(row.get(21)==null?0:Integer.parseInt(row.get(21).toString()));
        	 carr2.setģ�����(row.get(22)==null?0:Integer.parseInt(row.get(22).toString()));
        	 carr2.setģ�����(row.get(24)==null?0:Integer.parseInt(row.get(24).toString()));
        	 carr2.setģ�����(row.get(5)==null?"0":row.get(5).toString());
        	 Vector<Vector> tost=SqlTool.findInVector("select ��λ,���� from �䷽ָ�����   where ����ID='"+
        	     	 carr2.get����ID()+"' and ģ����ID='"+carr2.getģ����ID()+"' and �ֽ��='"+carr2.get�ֽ��()+"' and �ؾ����='"+carr2.get�ؾ����()+"' order by ��λ");
        	     	
        	     	 String beifangs="";
        	     	 for(int m=0;m<tost.size();m++){
        	     		 Vector vv=(Vector)tost.get(m);
        	     	     carr2.set��λ(carr2.get��λ()+""+vv.get(0)); 
        	     	     beifangs= (vv.get(1)==null?"":vv.get(1))+"!_!"+beifangs;
        	         }
        	     	 if(!beifangs.equals("")){
        	     		beifangs=beifangs.substring(0,  beifangs.length()-3) ;
        	     	    }
        	     	 carr2.set�䷽(beifangs);
        	     	 
        	     	if(װ����==1) {
      		               PLC.getIntance().line.setBuffer(carr2);
      		               ((_FST)secondST).set����λ������־(true);   
        	     	    }
        	     	else { PLC.getIntance().line2.setBuffer(carr2);
        	     	        ((_FST)secondST).set����λ������־(true);   
        	          	}
        	 		 
    	 		}
    	 		
    	  }
    	 
    	 if(!firstST.isWrite()&&secondST.isWrite()){ 
    		  //�����һ��ָ���ж��У��ڶ���û�У���ô�ѵڶ���ָ���Ƶ���һ������ָ�Ȼ����д��ڶ�������ָ��
    		 ((_FST)firstST).set����λ������־(false);   
    		 if(װ����==1) {
    		 PLC.getIntance().line.addFist(PLC.getIntance().line.buffer);
    		 PLC.getIntance().line.buffer=null;}
    		 else{ 
    			 
    			 PLC.getIntance().line2.addFist(PLC.getIntance().line2.buffer);
        		 PLC.getIntance().line2.buffer=null;	 
    		 }
    		 
    		 firstST.intFromST(secondST);
    		 secondST.setWrite(false);
    		 secondST.clear();
    		 
    	 }
    	 
    	 
    	 ////////
    	 if(װ����==1) {
    		 if(PLC.getIntance().line.getCarry(0)==null){
    			 ((_FST)firstST).set����λ������־(false); 
    			 
    		 }
    		
    		 
    	 }
    		 else{
    			 
    			 if(PLC.getIntance().line2.getCarry(0)==null){
        			 ((_FST)firstST).set����λ������־(false); 
        			 
        		 }
    		 }
    	 
    	}
    	
    	
    	///////////////////////////////////////////////////////
    	
    	if(stNum>=2&&stNum<=7){//���������־Ĭ��false����Ҫ�ж�
    		
    		if(!firstST.isWrite()&&!secondST.isWrite()){
    			
    			 firstST.set���ݴ�����(false);secondST.set���ݴ�����(false);
    			 
    			Vector<Vector> tem=new Vector<Vector>();
    			Carry cc=null;
    			if(װ����==1){cc=PLC.getIntance().line.getCarry(0);
    			}else{
    				       cc=PLC.getIntance().line2.getCarry(0);
    			}
    			if(false/*cc!=null*/){
          tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,����ID,ģ����ID,ģ�����,�ؾ�λ�� ,�ڶ�����  from �䷽ָ�����,ͨ������ "+
    "  where  ����ID="+cc.get����ID()+" and IFNULL(ST��ȡ��־,0)<>1  and װ����='"+װ����+"' and ģ����ID='"+cc.getģ����ID()+"' and �ֽ��='"+cc.get�ֽ��()+"' "
    		+ " and ��λ='"+(stNum-1)+"ST'  and ����=���ϱ��� ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");
          
    			}else{
        tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,����ID,ģ����ID,ģ�����,�ؾ�λ��,�ڶ����� from �䷽ָ�����,ͨ������ "+
    			 "  where   ��λ='"+(stNum-1)+"ST'"+" and IFNULL(ST��ȡ��־,0)<>1  and װ����='"+װ����+"' "
    				+ " and ����=���ϱ���  ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");				
    				
    			}
    	    	 	if(tem.size()>1){
    	    	 		 Vector row=(Vector)tem.get(0);
    	    	 		 Vector row2=(Vector)tem.get(1);
    	    	 		 ((_1_6ST)firstST).clear();
    	    	 		 ((_1_6ST)firstST).setId((int)row.get(0));
    	    	 		 ((_1_6ST)firstST).set������((int)row.get(1));
    	    	 		 ((_1_6ST)firstST).set�ֽ��((int)row.get(2));
    	    	 		 ((_1_6ST)firstST).set�ؾ����((int)row.get(3));
    	    	 		 ((_1_6ST)firstST).set����λ������־(false);
    	    	 		 ((_1_6ST)firstST).setͶ����ǻ��־((row.get(15)==null?0:Integer.parseInt(row.get(15).toString()))==2);//����
    	    	 		// System.out.println(row.get(10).getClass());
    	    	 		 ((_1_6ST)firstST).setģ�����ͱ�־((int)row.get(10));
    	    	 		 
    	    	 		 ((_1_6ST)firstST).set��о���ͱ�־((int)row.get(11));
    	    	 		 ((_1_6ST)firstST).set��������((int)row.get(7));
    	    	 		 ((_1_6ST)firstST).set����RDY(false);
    	    	 		  ((_1_6ST)firstST).set�䷽����(row.get(16)==null?0:(row.get(16).equals("")?0:Integer.parseInt(row.get(16)+"")));
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
    	    	 		 ((_1_6ST)secondST).setͶ����ǻ��־((row2.get(15)==null?0:Integer.parseInt(row2.get(15).toString()))==2);//����
    	    	 		 ((_1_6ST)secondST).setģ�����ͱ�־((int)row2.get(10));
    	    	 		 ((_1_6ST)secondST).set��о���ͱ�־((int)row2.get(11));
    	    	 		 ((_1_6ST)secondST).set��������((int)row2.get(7));
    	    	 		 ((_1_6ST)secondST).set����RDY(false);
    	    	 		 secondST.set����ID((int)row2.get(12));
    	    	 		 secondST.setģ����ID((int)row2.get(13));
    	    	 		 secondST.set���ϱ���((String)row2.get(6));
    	    	 		 ((_1_6ST)secondST).set�䷽����(row2.get(16)==null?0:(row2.get(16).equals("")?0:Integer.parseInt(row2.get(16)+"")));
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
    	    	 		 ((_1_6ST)firstST).setͶ����ǻ��־((row.get(15)==null?0:Integer.parseInt(row.get(15).toString()))==2);//����
    	    	 		 ((_1_6ST)firstST).setģ�����ͱ�־((int)row.get(10));
    	    	 		 ((_1_6ST)firstST).set��о���ͱ�־((int)row.get(11));
    	    	 		 ((_1_6ST)firstST).set��������((int)row.get(7));
    	    	 		 ((_1_6ST)firstST).set����RDY(false);
    	    	 		firstST.set����ID((int)row.get(12));
    	    	 		firstST.setģ����ID((int)row.get(13));
    	    	 		firstST.set���ϱ���((String)row.get(6));
    	    	 		firstST.setWrite(true);
    	    	 		 ((_1_6ST)firstST).set�䷽����(row.get(16)==null?0:(row.get(16).equals("")?0:Integer.parseInt(row.get(16)+"")));
    	    	 		update��־(firstST,2);
    	    	 		}
    	    	 		
    	    	 	}
    			
    		 }
    		
    		if(firstST.isWrite()&&!secondST.isWrite()){
    			Vector<Vector> tem=new Vector<Vector>();
    			Carry cc=null;
    			if(װ����==1){cc=PLC.getIntance().line.getCarry(0);
    			}else{
    				cc=PLC.getIntance().line2.getCarry(0);
    			}
    			if(false/*cc!=null*/){
          tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,����ID,ģ����ID,ģ�����,�ؾ�λ�� ,�ڶ�����  from �䷽ָ����� ,ͨ������"+
    "  where  ����ID="+cc.get����ID()+" and IFNULL(ST��ȡ��־,0)<>1  and װ����='"+װ����+"' and ģ����ID='"+cc.getģ����ID()+"' and �ֽ��='"+cc.get�ֽ��()+"' "
    		+ " and ��λ='"+(stNum-1)+"ST' and ����=���ϱ���  ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");
          
    			}else{
    	  tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,����ID,ģ����ID,ģ�����,�ؾ�λ�� ,�ڶ�����  from �䷽ָ�����,ͨ������ "+
    			  "  where   ��λ='"+(stNum-1)+"ST'"+" and IFNULL(ST��ȡ��־,0)<>1  and װ����='"+װ����+"'  and ����=���ϱ���  "
  				+ " ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");	
    				
    			}
		// Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,����ID,ģ����ID from �䷽ָ�����  where  װ����="+װ����+" and IFNULL(ST��ȡ��־,0)<>1  and ��λ='"+(stNum-1)+"ST' ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");		
				 if(tem.size()>0){
				    Vector row=(Vector)tem.get(0); 
				   ((_1_6ST)secondST).clear();
				   ((_1_6ST)secondST).setId((int)row.get(0));
				   ((_1_6ST)secondST).set������((int)row.get(1));
				   ((_1_6ST)secondST).set�ֽ��((int)row.get(2));
				   ((_1_6ST)secondST).set�ؾ����((int)row.get(3));
				   ((_1_6ST)secondST).set����λ������־(false);
				   ((_1_6ST)secondST).setͶ����ǻ��־((row.get(15)==null?0:Integer.parseInt(row.get(15).toString()))==2);//����
				   ((_1_6ST)secondST).setģ�����ͱ�־((int)row.get(10));
				   ((_1_6ST)secondST).set��о���ͱ�־((int)row.get(11));
				   ((_1_6ST)secondST).set��������((int)row.get(7));
				   ((_1_6ST)secondST).set����RDY(false);
				   secondST.set����ID((int)row.get(12));
	    	 	   secondST.setģ����ID((int)row.get(13));
	    	 	   secondST.set���ϱ���((String)row.get(6));
				   secondST.setWrite(true);	
				   ((_1_6ST)secondST).set�䷽����(row.get(16)==null?0:(row.get(16).equals("")?0:Integer.parseInt(row.get(16)+"")));
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
    	 if(stNum==8){//���������־Ĭ��false����Ҫ�ж�
   		    //�ٵ�о��λ
           if(!firstST.isWrite()&&!secondST.isWrite()){
        	   firstST.set���ݴ�����(false);secondST.set���ݴ�����(false);
        	   Vector<Vector> tem=new Vector<Vector>();
   			Carry cc=null;
   			if(װ����==1){cc=PLC.getIntance().line.getCarry(0);
   			}else{
   				cc=PLC.getIntance().line2.getCarry(0);
   			}
   			if(false/*cc!=null*/){
         tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,�ٵ�о1,�ٵ�о2,����ID,ģ����ID,ģ�����,�ؾ�λ��,�ڶ�����  from �䷽ָ�����,ͨ������ "+
   "  where ����ID="+cc.get����ID()+" and IFNULL(ST��ȡ��־,0)<>1  and װ����='"+װ����+"' and ģ����ID='"+cc.getģ����ID()+"' and �ֽ��='"+cc.get�ֽ��()+"' "
   		+ " and ��λ='"+(stNum-1)+"ST'  and ����=���ϱ���  ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");
         
   			}else{
   	tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,�ٵ�о1,�ٵ�о2,����ID,ģ����ID,ģ�����,�ؾ�λ��,�ڶ�����  from �䷽ָ�����,ͨ������  "+
   		 "  where   ��λ='"+(stNum-1)+"ST'"+" and IFNULL(ST��ȡ��־,0)<>1  and װ����='"+װ����+"'  and ����=���ϱ���  "
			+ " ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");					
   				
   			}    	   
    // Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о����,�ٵ�о1,�ٵ�о2,����ID,ģ����ID "
     	//	+"  from �䷽ָ�����  where  װ����="+װ����+" and IFNULL(ST��ȡ��־,0)<>3  and ��λ='7ST' ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");
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
     	    	 		 ((_7ST)firstST).set����RDY(false);
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
     	    	 		 ((_7ST)secondST).set����RDY(false);
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
     	    	 		 ((_7ST)firstST).set����RDY(false);
     	    	 		 firstST.set����ID((int)row.get(14));
    	    	 		 firstST.setģ����ID((int)row.get(15));
    	    	 		 firstST.set���ϱ���((String)row.get(6));
     	    	 		 firstST.setWrite(true);
     	    	 		 update��־(firstST,2);
     	    	 		}
     	    	 		
     	    	 	}
     			
     		 }
     		
     		if(firstST.isWrite()&&!secondST.isWrite()){
     			  Vector<Vector> tem=new Vector<Vector>();
     	   			Carry cc=null;
     	   			if(װ����==1){cc=PLC.getIntance().line.getCarry(0);
     	   			}else{
     	   				cc=PLC.getIntance().line2.getCarry(0);
     	   			}
     	   		if(false/*cc!=null*/){
     	         tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,�ٵ�о1,�ٵ�о2,����ID,ģ����ID,ģ�����,�ؾ�λ�� ,�ڶ����� from �䷽ָ����� ,ͨ������"+
     	   "  where  ����ID="+cc.get����ID()+" and IFNULL(ST��ȡ��־,0)<>1  and װ����='"+װ����+"' and ģ����ID='"+cc.getģ����ID()+"' and �ֽ��='"+cc.get�ֽ��()+"' "
     	   		+ " and ��λ='"+(stNum-1)+"ST' and ����=ͨ������ ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");
     	         
     	   			} 
     	   		else{
     	   		tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о���� ,�ٵ�о1,�ٵ�о2,����ID,ģ����ID,ģ�����,�ؾ�λ�� ,�ڶ����� from �䷽ָ����� ,ͨ������"+
     	   			 "  where   ��λ='"+(stNum-1)+"ST'"+" and IFNULL(ST��ȡ��־,0)<>1  and װ����='"+װ����+"' and ����=ͨ������  "
     				+ " ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");	 				
     	   			} 			
     			// Vector<Vector> tem=SqlTool.findInVector("select  ID,�������,�ֽ��,�ؾ����,pack����,ģ�����,����,����,�����,��λ ,ģ������,��о����,�ٵ�о1,�ٵ�о2,����ID,ģ����ID "
     		  //   		+"  from �䷽ָ�����  where  װ����="+װ����+" and IFNULL(ST��ȡ��־,0)<>1  and ��λ='7ST' ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 3");
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
 				   ((_7ST)secondST).set����RDY(false);
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
    	 
    	 
    	 if(stNum==9||stNum==11||stNum==12){//���������־Ĭ��false�����̵����ֱ�ӷ���
    		 boolean ���ݸ������ =((_1_6ST)firstST).is���ݸ������();
    		// boolean  ���ݴ�����=((_1_6ST)firstST).is���ݴ�����();
    		 ((_1_6ST)firstST).clear();
    		 ((_1_6ST)firstST).set����λ������־(false);
    		 ((_1_6ST)firstST).set���ݸ������(���ݸ������);
    		// ((_1_6ST)firstST).set���ݴ�����(���ݴ�����);
    		 ((_1_6ST)firstST).set����RDY(true);
    		 Carry ca= plc.line.getCarry(stNum-1);//����λ,ֻҪ���ؾߵ��˱���λ�Ž��д���
    		 if(ca!=null){
    			
    	 		 ((_1_6ST)firstST).setId(ca.����ID);
    	 		 ((_1_6ST)firstST).set������(ca.������);
    	 		 ((_1_6ST)firstST).set�ֽ��(ca.�ֽ��);
    	 		 ((_1_6ST)firstST).set����ID(ca.get����ID());
    	 		 ((_1_6ST)firstST).setģ����ID(ca.getģ����ID());
    	 		 ((_1_6ST)firstST).set�ֽ��(ca.get�ֽ��());
    	 		 ((_1_6ST)firstST).set�ؾ����(ca.�ؾ����); 
    	 		 
    	 		 if(stNum==9){//��8ST�Ĺ�λ���ǰ��ؾ߸�ֵ
    	 			String pei=ca.get�䷽(); 
    	 			if(pei!=null){
    	 				 String sm[]=pei.split("!_!")	;
    	 				 if(sm.length==1){
    	 					String back= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[0]+"'");
    	 					if(back!=null&&back.equals("1000")) {
    	 					    ((_1_6ST)firstST).set���ǰ��ؾ�(true);}
    	 					else{
    	 						((_1_6ST)firstST).set���ǰ��ؾ�(false);  
    	 					  }
    	 					
    	 					 
    	 				   }else{
    	 					  ((_1_6ST)firstST).set���ǰ��ؾ�(false);  
    	 					   
    	 				   }
    	 				
    	 			  }
    	 			 
    	 		   }
    			 
    		 }
    		 
    	 }
    	
    	 if(stNum==10){//Ԥװ��λ��9ST
    		  //���������־Ĭ��false�����̵�����ж�����ֵ
    		   //ͬʱ���첽�����ߵĶ���Ҳ������
    		 int next=stNum-1;
    		 if(!firstST.isWrite()&&!secondST.isWrite()){
    			 firstST.set���ݴ�����(false);secondST.set���ݴ�����(false);
    			 for(int i=stNum-1;i>=checkNum_Ԥװ;i--){//��9��ǰ�ж�
    				 Carry car=null; 
    				 if(װ����==1)
    			  car=plc.line.getCarry(i);
    				 else  car=plc.line2.getCarry(i);
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
			((_9ST)firstST).setPACK���ͱ�־(car.getPack����());
			((_9ST)firstST).setģ���(car.get�ֽ��());
			 int xuhao=car.getģ�����();int fenjie=car.get�ֽ��();
  			 try{
  				 if(fenjie<9){
  					((_9ST)firstST).setģ���(Integer.parseInt((xuhao+"0"+fenjie))) ;
  					 
  				 }else{
  					((_9ST)firstST).setģ���(Integer.parseInt((xuhao+""+fenjie))) ;	 
  					 
  				 }
  				 
  			   }catch(Exception e){}
			
			((_9ST)firstST).setģ�����ͱ�־(car.getģ������());
			((_9ST)firstST).set��о���ͱ�־(car.get��о����());
			((_9ST)firstST).set��������(d1+d2+d3+d4);
			((_9ST)firstST).set��1����оλ��(d1);
			((_9ST)firstST).set��2����оλ��(d2);
			((_9ST)firstST).set��3����оλ��(d3);
			((_9ST)firstST).set��4����оλ��(d4);
			((_9ST)firstST).set����RDY(true);
			((_9ST)firstST).setWrite(true);
		///////////////////////////////////////////////////////////////////////////////////////////////////	
			String pei=car.get�䷽(); 
	 			if(pei!=null){
	 				 String sm[]=pei.split("!_!")	;
	 				 if(sm.length==2){
	 					 boolean is�иǰ�=false;
	 					String back= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[0]+"'");
	 					String back2= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[1]+"'");
	 					if(back!=null&&back.equals("1000")) {
	 						   is�иǰ�=true;  }
	 					if(back2!=null&&back2.equals("1000")) {
	 						   is�иǰ�=true;  }
	 					
	 					if(!is�иǰ�)
	 					  {
	 						
	 					
	 					if(back!=null&&!back.equals("0")) {
	 						((_9ST)firstST).set�䷽����(Integer.parseInt(back.equals("")?"0":back)); 
	 						   
	 					}
	 					if(back2!=null&&!back2.equals("0")) {
	 						((_9ST)firstST).set�䷽����(Integer.parseInt(back2.equals("")?"0":back2)); 
	 						}
	 					
	 					  }
	 					 
	 				   }else{
	 					  if(sm.length==1){
	 						 String back= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[0]+"'");
	 						if(back!=null&&!back.equals("1000")){
	 							((_9ST)firstST).set�䷽����(Integer.parseInt(back.equals("")?"0":back)); 
	 							
	 						 }
	 						  
	 					  }
	 					   
	 				   }
	 				
	 			  }
		////////////////////////////////////////////////////////////////////////////////////////	
			
			  next=i;
			    break;
    			 }
    		
    	}//////////////////////////////////////end for
    	 for(int i=next-1;i>=checkNum_Ԥװ;i--){
    		 Carry car=null; 
			 if(װ����==1)
		           car=plc.line.getCarry(i);
			 else  car=plc.line2.getCarry(i);
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
        				((_9ST)secondST).setPACK���ͱ�־(car.getPack����());
        				((_9ST)secondST).setģ���(car.get�ֽ��());
        				 int xuhao=car.getģ�����();int fenjie=car.get�ֽ��();
        	  			 try{
        	  				 if(fenjie<9){
        	  					((_9ST)secondST).setģ���(Integer.parseInt((xuhao+"0"+fenjie))) ;
        	  					 
        	  				 }else{
        	  					((_9ST)secondST).setģ���(Integer.parseInt((xuhao+""+fenjie))) ;	 
        	  					 
        	  				 }
        	  				 
        	  			   }catch(Exception e){}
        				
        				((_9ST)secondST).setģ�����ͱ�־(car.getģ������());
        				((_9ST)secondST).set��о���ͱ�־(car.get��о����());
        				((_9ST)secondST).set��������(d1+d2+d3+d4);
        				((_9ST)secondST).set��1����оλ��(d1);
        				((_9ST)secondST).set��2����оλ��(d2);
        				((_9ST)secondST).set��3����оλ��(d3);
        				((_9ST)secondST).set��4����оλ��(d4);
        				((_9ST)secondST).set����RDY(true);
        				((_9ST)secondST).setWrite(true);
        				
        				///////////////////////////////////////////////////////////////////////////////////////////////////	
        				String pei=car.get�䷽(); 
        		 			if(pei!=null){
        		 				 String sm[]=pei.split("!_!")	;
        		 				 if(sm.length==2){
        		 					 boolean is�иǰ�=false;
        		 					String back= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[0]+"'");
        		 					String back2= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[1]+"'");
        		 					if(back!=null&&back.equals("1000")) {
        		 						   is�иǰ�=true;  }
        		 					if(back2!=null&&back2.equals("1000")) {
        		 						   is�иǰ�=true;  }
        		 					
        		 					if(!is�иǰ�)
        		 					  {
        		 						
        		 					
        		 					if(back!=null&&!back.equals("0")) {
        		 						((_9ST)secondST).set�䷽����(Integer.parseInt(back.equals("")?"0":back)); 
        		 						   
        		 					}
        		 					if(back2!=null&&!back2.equals("0")) {
        		 						((_9ST)secondST).set�䷽����(Integer.parseInt(back2.equals("")?"0":back2)); 
        		 						}
        		 					
        		 					  }
        		 					 
        		 				   }else{
        		 					  if(sm.length==1){
        		 						 String back= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[0]+"'");
        		 						if(back!=null&&!back.equals("1000")){
        		 							((_9ST)secondST).set�䷽����(Integer.parseInt(back.equals("")?"0":back)); 
        		 							
        		 						 }
        		 						  
        		 					  }
        		 					   
        		 				   }
        		 				
        		 			  }
        			////////////////////////////////////////////////////////////////////////////////////////	
        				
    			  
    			    break;
        			 }
        		
        			 }//end for
        		
    			
    		 }
    
   if(firstST.isWrite()&&!secondST.isWrite()){
    			 for(int i=stNum-1;i>=checkNum_Ԥװ;i--){
    				 Carry car=null; 
    				 if(װ����==1)
    			           car=plc.line.getCarry(i);
    				 else  car=plc.line2.getCarry(i);
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
 				((_9ST)secondST).setPACK���ͱ�־(car.getPack����());
 				((_9ST)secondST).setģ���(car.get�ֽ��());
 				 int xuhao=car.getģ�����();int fenjie=car.get�ֽ��();
	  			 try{
	  				 if(fenjie<9){
	  					((_9ST)secondST).setģ���(Integer.parseInt((xuhao+"0"+fenjie))) ;
	  					 
	  				 }else{
	  					((_9ST)secondST).setģ���(Integer.parseInt((xuhao+""+fenjie))) ;	 
	  					 
	  				 }
	  				 
	  			   }catch(Exception e){}
 				((_9ST)secondST).setģ�����ͱ�־(car.getģ������());
 				((_9ST)secondST).set��о���ͱ�־(car.get��о����());
 				((_9ST)secondST).set��������(d1+d2+d3+d4);
 				((_9ST)secondST).set��1����оλ��(d1);
 				((_9ST)secondST).set��2����оλ��(d2);
 				((_9ST)secondST).set��3����оλ��(d3);
 				((_9ST)secondST).set��4����оλ��(d4);
 				((_9ST)secondST).set����RDY(true);
 				((_9ST)secondST).setWrite(true);
 				///////////////////////////////////////////////////////////////////////////////////////////////////	
				String pei=car.get�䷽(); 
		 			if(pei!=null){
		 				 String sm[]=pei.split("!_!")	;
		 				 if(sm.length==2){
		 					 boolean is�иǰ�=false;
		 					String back= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[0]+"'");
		 					String back2= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[1]+"'");
		 					if(back!=null&&back.equals("1000")) {
		 						   is�иǰ�=true;  }
		 					if(back2!=null&&back2.equals("1000")) {
		 						   is�иǰ�=true;  }
		 					
		 					if(!is�иǰ�)
		 					  {
		 						
		 					
		 					if(back!=null&&!back.equals("0")) {
		 						((_9ST)secondST).set�䷽����(Integer.parseInt(back.equals("")?"0":back)); 
		 						   
		 					}
		 					if(back2!=null&&!back2.equals("0")) {
		 						((_9ST)secondST).set�䷽����(Integer.parseInt(back2.equals("")?"0":back2)); 
		 						}
		 					
		 					  }
		 					 
		 				   }else{
		 					  if(sm.length==1){
		 						 String back= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[0]+"'");
		 						if(back!=null&&!back.equals("1000")){
		 							((_9ST)secondST).set�䷽����(Integer.parseInt(back.equals("")?"0":back)); 
		 							
		 						 }
		 						  
		 					  }
		 					   
		 				   }
		 				
		 			  }
			////////////////////////////////////////////////////////////////////////////////////////	
				
 				
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
    	 
    	 if(stNum==13){//��װ��λ
   		    //��װ��λ,���ؾߵ���ʱ��
    		
    		 Carry car=null; 
			 if(װ����==1)
		           car=plc.line.getCarry(12);
			 else  car=plc.line2.getCarry(12);
    			 if(car!=null){
    				 if(!firstST.isWrite()){
    				 ((_12ST)firstST).clear();
	    	 		 ((_12ST)firstST).setId(car.get����ID());
	    	 		 ((_12ST)firstST).set������(car.get������());
	    	 		 ((_12ST)firstST).set�ֽ��(car.get�ֽ��());
	    	 		 ((_12ST)firstST).set�ؾ����(car.get�ؾ����());
	    	 		 if(car.is��װ��()){
	    	 		 ((_12ST)firstST).set����λ������־(true);
	    	 		 
	    	 		   }
	    	 		 else{
	    	 	     ((_12ST)firstST).set����λ������־(false); 
	    	 	     
	    	 		 }
	    	 		 //((_12ST)firstST).setͶ����ǻ��־(false);
	    	 		 ((_12ST)firstST).setģ�����ͱ�־(car.getģ������());
	    	 		 ((_12ST)firstST).set��о���ͱ�־(car.get��о����());
                    // ((_12ST)firstST).set��������(0);
	    	 		 ((_12ST)firstST).set����RDY(true);
	    	 		 firstST.set����ID(car.get����ID());
	    	 		 firstST.setģ����ID(car.getģ����ID());
	    	 		 
	    	 		
	     	 			String pei=car.get�䷽(); 
	     	 			if(pei!=null){
	     	 				 String sm[]=pei.split("!_!")	;
	     	 				 if(sm.length==2){
	     	 					 boolean is�иǰ�=false;
	     	 					String back= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[0]+"'");
	     	 					String back2= SqlTool.findOneRecord("Select �ڶ����� from ͨ������  where ���ϱ���='"+sm[1]+"'");
	     	 					if(back!=null&&back.equals("1000")) {
	     	 						   is�иǰ�=true;  }
	     	 					if(back2!=null&&back2.equals("1000")) {
	     	 						   is�иǰ�=true;  }
	     	 					
	     	 					if(is�иǰ�){ ((_12ST)firstST).set�иǰ��װ(true ); }
	     	 					else{
	     	 						
	     	 					
	     	 					if(back!=null&&!back.equals("0")) {
	     	 						((_12ST)firstST).set�䷽����(Integer.parseInt(back.equals("")?"0":back)); 
	     	 						   
	     	 					}
	     	 					if(back2!=null&&!back2.equals("0")) {
	     	 						((_12ST)firstST).set�䷽����(Integer.parseInt(back2.equals("")?"0":back2));
	     	 						}
	     	 					
	     	 					  }
	     	 					 
	     	 				   }else{
	     	 					 ((_12ST)firstST).set�иǰ��װ(false );  
	     	 					 
	     	 					  if(sm.length==1){
	     	 						 String back= SqlTool.findOneRecord("Select �ڶ�����  from ͨ������  where ���ϱ���='"+sm[0]+"'");
	     	 						if(back!=null&&!back.equals("1000")){
	     	 							((_12ST)firstST).set�䷽����(Integer.parseInt(back.equals("")?"0":back)); 
	     	 							
	     	 						 }
	     	 						  
	     	 					  }
	     	 					   
	     	 				   }
	     	 				
	     	 			  }
	     	 			 
	     	 		  
	    	 		 
	    	 		 
	    	 		 
	    	 		 firstST.setWrite(true);	 
    				 }
    			 }else{
    				 boolean f=((_12ST)firstST).is���ݸ������();
    				 firstST.setWrite(false);	
    				 ((_12ST)firstST).clear();
    				 ((_12ST)firstST).set���ݸ������(f);
    				 
    			 }
    		 
   	      }
    	 
    	 if(stNum==14){//����14��ʱ�����ж�_13ST
    		    //ȡ����λ
    		 Carry car=null; 
			 if(װ����==1)
		           car=plc.line.getCarry(13);
			 else  car=plc.line2.getCarry(13);
    		   if(car!=null){
    			   

    	    		  if(!firstST.isWrite()){
    	    		((_13ST)firstST).clear();
    	            ((_13ST)firstST).setId(car.get����ID());
    	  			((_13ST)firstST).set������(car.get������());
    	  			((_13ST)firstST).set����ID(car.get����ID());
    	  			((_13ST)firstST).setģ����ID(car.getģ����ID());
    	  			((_13ST)firstST).set�ֽ��(car.get�ֽ��());
    	  			((_13ST)firstST).set�ؾ����(car.�ؾ����);
    	  			((_13ST)firstST).set����λ������־(true);
    	  			((_13ST)firstST).setPACK��(car.get������());
    	  			((_13ST)firstST).setPACK���ͱ�־(car.getPack����());
    	  			((_13ST)firstST).setģ���(car.get�ֽ��());
    	  			((_13ST)firstST).setģ�����ͱ�־(car.getģ������());
    	  			((_13ST)firstST).set��о���ͱ�־(car.get��о����());
    	  			((_13ST)firstST).set��Ч��ǻ��(car.get��Ч��ǻ��());
    	  			((_13ST)firstST).set����RDY(true);
    	  			((_13ST)firstST).setģ�����(car.getģ�����());  
    	  			 int xuhao=car.getģ�����();int fenjie=car.get�ֽ��();
    	  			 try{
    	  				 if(fenjie<9){
    	  					((_13ST)firstST).setģ���(Integer.parseInt((xuhao+"0"+fenjie))) ;
    	  					 
    	  				 }else{
    	  					((_13ST)firstST).setģ���(Integer.parseInt((xuhao+""+fenjie))) ;	 
    	  					 
    	  				 }
    	  				 
    	  			   }catch(Exception e){}
    	  			
    	  			((_13ST)firstST).setWrite(true);
    	  			
    	    		      }  
    		   }else{
    			   boolean f=((_13ST)firstST).is���ݸ������();
    			   ((_13ST)firstST).setWrite(false);  
     			   ((_13ST)firstST).clear();
     			   ((_13ST)firstST).set���ݸ������(f);
     			   
    			   
    		   }
    		 //  ((_13ST)firstST).set����λ������־(true);
    		   
    	      }
    	 
    	 if(stNum==15){//���﹤λʱ�����ź�
 		    //��������_BST
    		 Carry car=null; 
			 if(װ����==1)
		           car=plc.line.getCarry(14);
			 else  car=plc.line2.getCarry(14);
    		  if(car!=null){
    		
    		  if(!firstST.isWrite()){
    		((_BST)firstST).clear();
            ((_BST)firstST).setId(car.get����ID());
  			((_BST)firstST).set������(car.get������());
  			((_BST)firstST).set����ID(car.get����ID());
  			((_BST)firstST).setģ����ID(car.getģ����ID());
  			((_BST)firstST).set�ֽ��(car.get�ֽ��());
  			((_BST)firstST).set�ؾ����(car.�ؾ����);
  			((_BST)firstST).set����λ������־(true);
  			((_BST)firstST).setPACK��(car.get������());
  			((_BST)firstST).setPACK���ͱ�־(car.getPack����());
  			((_BST)firstST).setģ���(car.get�ֽ��());
  			((_BST)firstST).setģ�����ͱ�־(car.getģ������());
  			((_BST)firstST).set��о���ͱ�־(car.get��о����());
  			((_BST)firstST).set��Ч��ǻ��(car.get��Ч��ǻ��());
  			((_BST)firstST).setģ�����(car.getģ�����());
  			((_BST)firstST).set����RDY(true);
  			((_BST)firstST).setWrite(true);
  			
    		      }
    	    }else{
    			 // ((_BST)firstST).setWrite(false);  
    			 // ((_BST)firstST).clear();
    			  
    		  }
    		  
 	      }
    	 
    	 if(stNum==16){ 
  		    //ͬ��������
    		//�ӵ�װ��λ��ǰ�ж�
    		 if(!firstST.isWrite()&&!secondST.isWrite()){
    			 firstST.set���ݴ�����(false);secondST.set���ݴ�����(false);
    			 Vector tem=new Vector();
    			 for(int i=10;i>=checkNum_ͬ��;i--){
    				 Carry car=null; 
    				 if(װ����==1)
    			           car=plc.line.getCarry(i);
    				 else  car=plc.line2.getCarry(i);
    			
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
			((_DST)firstST).set����ID(car.get����ID());
			((_DST)firstST).setģ����ID(car.getģ����ID());
			((_DST)firstST).set�ֽ��(car.get�ֽ��());
			((_DST)firstST).set�ؾ����(car.�ؾ����);
			((_DST)firstST).set����λ������־(true);
		    ((_DST)firstST).set��о���ͱ�־(car.get��о����());
			((_DST)firstST).set��B��(fan);
			
			((_DST)firstST).set����RDY(true);
			((_DST)firstST).setWrite(true);
			  
    			 }
    		
    		if(tem.size()>1){
    			Vector row=(Vector)tem.get(1);
    			Carry car=(Carry)row.get(0);
    			boolean fan=(boolean)row.get(1);
        	    ((_DST)secondST).clear();
    			((_DST)secondST).setId(car.get����ID());
    			((_DST)secondST).set������(car.get������());
    			((_DST)secondST).set����ID(car.get����ID());
    			((_DST)secondST).setģ����ID(car.getģ����ID());
    			((_DST)secondST).set�ֽ��(car.get�ֽ��());
    			((_DST)secondST).set�ؾ����(car.�ؾ����);
    			((_DST)secondST).set����λ������־(true);
    		    ((_DST)secondST).set��о���ͱ�־(car.get��о����());
    			((_DST)secondST).set��B��(fan);
    			
    			((_DST)secondST).set����RDY(true);
    			((_DST)secondST).setWrite(true);
    			  
        			 }
        		
    		
    			
    
        		
    			
    		 }
    		 
    		 if(firstST.isWrite()&&!secondST.isWrite()){
    			 Vector tem=new Vector();
    			 for(int i=10;i>=checkNum_ͬ��;i--){
    				 Carry car=null; 
    				 if(װ����==1)
    			           car=plc.line.getCarry(i);
    				 else  car=plc.line2.getCarry(i);
        			
        			 if(car==null){continue;}
        			 String ss1= car.get��оλ��1(); String ss2= car.get��оλ��2();
        			 String ss3= car.get��оλ��3(); String ss4= car.get��оλ��4();
        			 if(ss1!=null){
        				 String sm[]=ss1.split("=");
        				 if(sm.length==2){//Ҫ�ǵ���3�Ļ�˵���Ѿ��ϴ���
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
    			((_DST)secondST).set����ID(car.get����ID());
    			
    			((_DST)secondST).set����RDY(true);
    			((_DST)secondST).setWrite(true);
    			  
        			 }
        	
        		
        		
    			 
    		 }
    		 
    		 if(!firstST.isWrite()&&secondST.isWrite()){
    			 firstST.intFromST(secondST);
        		 secondST.setWrite(false);
        		 secondST.clear();
    			 
    		 }
    		// updata����();  
  	      }
    	 
    	 
    	 firstST.writeifChangeToPLC();
    	 secondST.writeifChangeToPLC();
    	 
    	
    }
    
    public void updata����(){//���¶��������־ 
    	
    	int next=stNum-1;
    	
		//���µ�һ����λ���������־
		if(firstST.isWrite()){
			
			Carry car=this.װ����==1?plc.line.getCarry(stNum-1):plc.line2.getCarry(stNum-1);
			if(car!=null){
			String id1=firstST.get����ID()+""+firstST.getģ����ID()+""+firstST.get�ֽ��()+firstST.get�ؾ����();
			String id2=car.get����ID()+""+car.getģ����ID()+""+car.get�ֽ��()+car.get�ؾ����();
			next=stNum-1;
			 if(id1.equals(id2)){
				//System.out.println("first="+id1);
    				 if(firstST instanceof _1_6ST )
    				( (_1_6ST)firstST).set����λ������־(true);
    				 if(firstST instanceof _7ST )
		    		 ( (_7ST)firstST).set����λ������־(true);
    				 if(firstST instanceof _9ST ){
			    	 ( (_9ST)firstST).set����λ������־(true);
			    	 
    				 }
    			    if(firstST instanceof _DST ){
    			    	// ( (_DST)firstST).set����λ������־(true);
    			    	
        				 }
    			   
			    }
			  }else{
				//  if(!firstST.is����λ������־()){
				  Carry car2=this.װ����==1?plc.line.getCarry(stNum-2):plc.line2.getCarry(stNum-2);
				  if(car2!=null){
					  String id1=firstST.get����ID()+""+firstST.getģ����ID()+""+firstST.get�ֽ��()+firstST.get�ؾ����();
		    		  String id2=car2.get����ID()+""+car2.getģ����ID()+""+car2.get�ֽ��()+car2.get�ؾ����();
		    		  next=stNum-2;
		    			 if(id1.equals(id2)){
		    				// System.out.println("second="+id1);
		    				 if(firstST instanceof _1_6ST )
		    				( (_1_6ST)firstST).set����λ������־(true);
		    				 if(firstST instanceof _7ST )
				    		 ( (_7ST)firstST).set����λ������־(true);
		    				 if(firstST instanceof _9ST ){
					    	 ( (_9ST)firstST).set����λ������־(true);
					    		 
		    				   }
		    				 if(firstST instanceof _DST ){
		    			    	// ( (_DST)firstST).set����λ������־(true);
		    			    	
		        				 }
		    			    }
					  
				  }
				  //}
				  
			  }
			firstST.writeifChangeToPLC();
		}else{
		   // firstST.set����λ������־(false);
			//firstST.writeifChangeToPLC();
			
		}
		
		//���µڶ�����λ���������־
		if(secondST.isWrite()){
			
			if(next==0) return;
			Carry car=this.װ����==1?plc.line.getCarry(next-1):plc.line2.getCarry(next-1);
			
			if(car!=null){
				 //System.out.println("firstST==="+firstST.getName());
				 //System.out.println("secondST==="+secondST.getName());
				//System.out.println("==="+car.getName());
			String id1=secondST.get����ID()+""+secondST.getģ����ID()+""+secondST.get�ֽ��()+secondST.get�ؾ����();
			String id2=car.get����ID()+""+car.getģ����ID()+""+car.get�ֽ��()+car.get�ؾ����();
			
			 if(id1.equals(id2)){
				
				 if(secondST instanceof _1_6ST )
				( (_1_6ST)secondST).set����λ������־(true);
				 if(secondST instanceof _7ST )
				( (_7ST)secondST).set����λ������־(true);
				 if(secondST instanceof _9ST ){
				( (_9ST)secondST).set����λ������־(true);
				//System.out.println("____+++++++___");
				 if(firstST instanceof _DST ){
			    // ( (_DST)secondST).set����λ������־(true);
			    	
    				 }
				
				 }
			    }
			  }
			 secondST.writeifChangeToPLC();
		}else{
			
			//secondST.set����λ������־(false);
			//secondST.writeifChangeToPLC();
		}
    }
    

    private synchronized boolean updataSTDB(int ��λ, ST_Father st){
    	if(stNum<2||stNum>8){return false;}
    	if(stNum>=2&&stNum<=7){
    		//System.out.println("id="+st.getId());
  	  String tem=SqlTool.findOneRecord("select  ����,����  from �䷽ָ�����   where  ID='"+st.getId()+"'");
  	  if(tem!=null){
  		String wuliao=tem.split("!_!")[0];
  	   //System.out.println("id2="+wuliao);
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
  				  // ((_1_6ST)st).set����RDY(false);
  				 /*******
  		  	  	 ������Ҫ���»��������̵�λ�ã���PLC����
  		  		 * 
  		  		 * *********/	
  				   if(stNum==2){//��ȡPLC��Ȼ�󱣴浽���ݿ�����
  					   
  				   }
  				   if(stNum==3){}
  				   if(stNum==4){}
  				   if(stNum==5){}
  				   if(stNum==6){}
  				   if(stNum==7){}
  				  //��������
  				   String leixing=SqlTool.findOneRecord("select ����  from ͨ������  where ���ϱ���="+"'"+wuliao2+"'");
  				   SqlTool.readAddresInPaletFromPLC(leixing, tp, ��λ+"", װ����);
  				   
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
  	  
    	
    	
    	if(stNum==8){//�ٵ�о������һ��ֻ��ȡһ��
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
    	  				 /*******
    	    		  	  	 ������Ҫ���»��������̵�λ�ã���PLC����
    	    		  		 * 
    	    		  		 * *********/	
    	  				  String leixing=SqlTool.findOneRecord("select ����  from ͨ������  where ���ϱ���="+"'"+wuliao2+"'");
    	  				  SqlTool.readAddresInPaletFromPLC(leixing, tp, ��λ+"", װ����);
    	  				  
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
    		System.out.println(stNum);
    		//�������������̵�����
    	if(stNum==2){//1ST
    		return updataSTDB(514, st);
    	 
    	}
        if(stNum==3){//2ST
        	return updataSTDB(512, st);
    		
    	 }
        if(stNum==4){//2ST
        	return updataSTDB(510, st);
    		
    	 }
        if(stNum==5){//2ST
        	return updataSTDB(508, st);
    		
    	 }
        if(stNum==6){//2ST
        	return updataSTDB(506, st);
    		
    	 }
        if(stNum==7){//2ST
        	return updataSTDB(504, st);
    		
    	 }
        if(stNum==8){//2ST
        	return updataSTDB(502, st);
    		
    	 }
        
    		}
    	
    	if(װ����==2){
    		//�������������̵�����
    	if(stNum==2){//1ST
    		return updataSTDB(614, st);
    	 
    	}
        if(stNum==3){//2ST
        	return updataSTDB(612, st);
    		
    	 }
        if(stNum==4){//2ST
        	return updataSTDB(610, st);
    		
    	 }
        if(stNum==5){//2ST
        	return updataSTDB(608, st);
    		
    	 }
        if(stNum==6){//2ST
        	return updataSTDB(606, st);
    		
    	 }
        if(stNum==7){//2ST
        	return updataSTDB(604, st); 
    		
    	 }
        if(stNum==8){//2ST
        	return updataSTDB(602, st);
    		
    	 }
        
    		}
    	
    	  return false;
    }
   
}
