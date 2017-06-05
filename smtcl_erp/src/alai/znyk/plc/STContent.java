package alai.znyk.plc;

import java.io.Serializable;
import java.util.Vector;

import alai.znyk.server.SqlTool;

public class STContent implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int checkNum_预装=8;
	public static int checkNum_同步=6;
	
	public ST_Father firstST; 
    public ST_Father secondST;
    public int stNum;
    public PLC plc;
    public int 装配区;
    public STContent(PLC plc,ST_Father first,ST_Father second,int stNum,int 装配区){
    	this.firstST=first;
    	this.secondST=second;
    	this.stNum=stNum;
    	this.装配区=装配区;
    	this.plc=plc;
       }
    public void update标志(ST_Father st,int mode){
    	//System.out.println("update-----");
    	if(mode==1){//更新 前升读标志
    	 String sql="update 配方指令队列  set 前升读标志=1 where 工单ID="+"'"+st.get工单ID()+"' and 分解号="+st.get分解号()+" and 模组序ID="+st.get模组序ID()+" and 载具序号="+st.get载具序号()+" and 装配区="+st.machineID;
    	 System.out.println(sql);
    	 SqlTool.insert(new String[]{sql});
    	 }
    	if(mode==2){//更新 1ST-6ST的读取标志
       	 String sql="update 配方指令队列  set ST读取标志=1 where ID="+"'"+st.getId()+"'";
       	 System.out.println(sql);
       	 SqlTool.insert(new String[]{sql});
       	 }
    }
   
  
    public synchronized void initFromSql(){
    	
    	if(plc.getIntance().stop1){//不再重数据库里面读数了，单是不影响搬运机构的继续执行
    		if(装配区==1){
    		   //System.out.println("return----");
    			if(stNum==1){ 
    				// if(PLC.getIntance().line.getCarry(0)==null){
    	    			 ((_FST)firstST).set允许工位动作标志(false);
    	    			 ((_FST)secondST).set允许工位动作标志(false); 
    	    			 
    	    		// }
    				 firstST.writeifChangeToPLC();
    		    	 secondST.writeifChangeToPLC();
    		    	 ////
    				return;}	
    			if(stNum>=2&&stNum<=8){
    				updata动作();
    				return;}
    			
    		}
    	}else{ 
    		if(装配区==1){ 
     		   //System.out.println("return----");
     			if(stNum==1){ 
     				 if(PLC.getIntance().line.getCarry(0)!=null){
     	    			 ((_FST)firstST).set允许工位动作标志(true);
     	    			 ((_FST)secondST).set允许工位动作标志(true); 
     	    			 
     	    		 }
     				
     		    	  }	
     			
     			
     		}	
    		
    	}
    	
    	if(plc.getIntance().stop2){ 
    		if(装配区==2){
    			if(stNum==1){
    				// if(PLC.getIntance().line2.getCarry(0)==null){
    	    			 ((_FST)firstST).set允许工位动作标志(false); 
    	    			 ((_FST)secondST).set允许工位动作标志(false); 
    	    			 
    	    		// }
    				 firstST.writeifChangeToPLC();
    		    	 secondST.writeifChangeToPLC();
    		    	 
    				return;}	
    			if(stNum>=2&&stNum<=8){
    				updata动作();
    				return;}
    			
    		}
    		
    	}else{
    		if(装配区==2){ 
      		   //System.out.println("return----");
      			if(stNum==1){ 
      				 if(PLC.getIntance().line2.getCarry(0)!=null){
      	    			 ((_FST)firstST).set允许工位动作标志(true);
      	    			 ((_FST)secondST).set允许工位动作标志(true); 
      	    			 
      	    		 }
      				
      		    	  }	
      			
      			
      		}	
    		
    	}
    	
    	
    	if(stNum==1){
    		//System.out.println("--------------------"+plc.getIntance().stop1);
    		//动作容许标志默认TURE
         //前升降台,按工单号+分解号+模组编码（模组号）来分类汇总载具，按载具序号排序
    	//从数据库读取成功后,"前升降机读取标志=1",系统不从启前是不会在从新读取，接受到载具放行后，把这个标志更新为2表示完成，以后再也不会从数据库中读取
    	if(!firstST.isWrite()&&!secondST.isWrite()){
    		 //读取前两条指令，按顺序写入1和2个指令队列，这种情况只会发生在系统第一次启动	
    		 //然后把Write=true;
    		// 工单序号=PACK序号，分解号=这个工单号下面的模组分成的序号，
    		//工单ID+模组序ID+分解号+载具序号,这4个也决定了唯一的载具
    		
    	 firstST.set数据处理中(false);secondST.set数据处理中(false);
    	 Vector<Vector> tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位,工单ID,模组序ID,IFNULL(假电芯1,0),IFNULL(假电芯2,0),电芯位置1,电芯位置2,电芯位置3,电芯位置4,IFNULL(叠装否,'否'),模组类型,电芯类型  ,pack类型,模组层数,载具位置, 模组序号,COUNT(DISTINCT 工单序号,模组序ID,分解号,载具序号 )  from 配方指令队列    where  装配区="+装配区+"   and IFNULL(前升读标志,0)<>1 GROUP BY 工单序号,模组序号,分解号,载具序号   ORDER BY 工单序号,模组序号,分解号,载具序号 LIMIT 10");
    	 //System.out.println(tem);	
    	 if(tem.size()>1){
    	 		 Vector row=(Vector)tem.get(0);
    	 		 Vector row2=(Vector)tem.get(1);
    	 		 System.out.println(row);
    	 		 System.out.println(row2);
    	 		 ((_FST)firstST).clear();
    	 		 ((_FST)firstST).setId(row.get(0)==null?0:(int)row.get(0));
    	 		 ((_FST)firstST).set工单号(row.get(1)==null?0:(int)row.get(1));
    	 		 ((_FST)firstST).set分解号(row.get(2)==null?0:(int)row.get(2));
    	 		 ((_FST)firstST).set载具序号(row.get(3)==null?0:(int)row.get(3));
    	 		 ((_FST)firstST).set翻B面(row.get(8).equals("是")?true:false);
    	 		 ((_FST)firstST).set允许工位动作标志(false);
    	 		 ((_FST)firstST).set立库RDY(false);
    	 		  firstST.set工单ID(row.get(10)==null?0:(int)row.get(10));
    	 		  firstST.set模组序ID(row.get(11)==null?0:(int)row.get(11));
    	 		  firstST.setWrite(true);
    	 		 // firstST.writeToPLC();
    	 		  update标志(firstST,1);
    	 		 //如果不为空时真加不了的，每当收到放行的信号后，会自动移动，这时会把这个设为NULL的。
    	     	 Carry carr=new Carry(firstST.get工单号(), firstST.get分解号(), firstST.get载具序号(),firstST.get模组序ID());
    	     	 carr.set假电芯1(row.get(12).toString().equals("0")?0:(int)row.get(12));
    	     	 carr.set假电芯2(row.get(13).toString().equals("0")?0:(int)row.get(13));
    	     	 carr.set电芯位置1(row.get(14)==null?null:row.get(14).toString());
    	     	 carr.set电芯位置2(row.get(15)==null?null:row.get(15).toString());
    	     	 carr.set电芯位置3(row.get(16)==null?null:row.get(16).toString());
    	     	 carr.set电芯位置4(row.get(17)==null?null:row.get(17).toString());
    	     	 carr.set叠装否(row.get(18).equals("是")?true:false);
    	     	 carr.set工单ID(row.get(10)==null?0:Integer.parseInt(row.get(10).toString()));
    	     	 carr.set模组类型(row.get(19)==null?0:Integer.parseInt(row.get(19).toString()));
    	     	 carr.set电芯类型(row.get(20)==null?0:Integer.parseInt(row.get(20).toString()));
    	     	 carr.setPack类型(row.get(21)==null?0:Integer.parseInt(row.get(21).toString()));
    	     	 carr.set模组层数(row.get(22)==null?0:Integer.parseInt(row.get(22).toString()));
    	     	 carr.set模组序号(row.get(24)==null?0:Integer.parseInt(row.get(24).toString()));
    	     	 carr.set模组编码(row.get(5)==null?"0":row.get(5).toString());
    	     	 Vector<Vector> tost=SqlTool.findInVector("select 工位,物料 from 配方指令队列   where 工单ID='"+
    	     	 carr.get工单ID()+"' and 模组序ID='"+carr.get模组序ID()+"' and 分解号='"+carr.get分解号()+"' and 载具序号='"+carr.get载具序号()+"' order by 工位");
    	     	 String beifangs="";
    	     	 for(int m=0;m<tost.size();m++){
    	     		 Vector vv=(Vector)tost.get(m);
    	     	     carr.set工位(carr.get工位()+""+vv.get(0)); 
    	     	     beifangs= (vv.get(1)==null?"":vv.get(1))+"!_!"+beifangs;
    	         }
    	     	 if(!beifangs.equals("")){
    	     		beifangs=beifangs.substring(0,  beifangs.length()-3) ;
    	     	    }
    	     	 carr.set配方(beifangs);
    	     	 if(装配区==1){
    	  		 PLC.getIntance().line.addFist(carr);
    	  		 ((_FST)firstST).set允许工位动作标志(true);
    	  		 }
    	     	 else{
    	     	 PLC.getIntance().line2.addFist(carr); 
    	     	 ((_FST)firstST).set允许工位动作标志(true);
    	     	 System.out.println("line2---------");
    	  		 }
    	 		 
    	 	 ((_FST)secondST).clear();
   	 		 ((_FST)secondST).setId(row2.get(0)==null?0:(int)row2.get(0));
   	 		 ((_FST)secondST).set工单号(row2.get(1)==null?0:(int)row2.get(1));
   	 		 ((_FST)secondST).set分解号(row2.get(2)==null?0:(int)row2.get(2));
   	 		 ((_FST)secondST).set载具序号(row2.get(3)==null?0:(int)row2.get(3));
   	 		 ((_FST)secondST).set翻B面(row2.get(8).equals("是")?true:false);
   	 		 ((_FST)secondST).set允许工位动作标志(false);
   	 		 ((_FST)secondST).set立库RDY(false);
   	 	     secondST.set工单ID(row2.get(10)==null?0:(int)row2.get(10));
   	         secondST.set模组序ID(row2.get(11)==null?0:(int)row2.get(11));
   	 		 secondST.setWrite(true);
   	 		 update标志(secondST,1);
   	 		 
   	 	 Carry carr2=new Carry( secondST.get工单号(),  secondST.get分解号(),  secondST.get载具序号(), secondST.get模组序ID());
   	 	 carr2.set假电芯1(row2.get(12).toString().equals("0")?0:(int)row2.get(12));
    	 carr2.set假电芯2(row2.get(13).toString().equals("0")?0:(int)row2.get(13));
     	 carr2.set电芯位置1(row2.get(14)==null?null:row2.get(14).toString());
     	 carr2.set电芯位置2(row2.get(15)==null?null:row2.get(15).toString());
     	 carr2.set电芯位置3(row2.get(16)==null?null:row2.get(16).toString());
     	 carr2.set电芯位置4(row2.get(17)==null?null:row2.get(17).toString());
     	 carr2.set叠装否(row2.get(18).equals("是")?true:false);
     	 carr2.set模组类型(row2.get(19)==null?0:(int)row2.get(19));
     	 carr2.set电芯类型(row2.get(20)==null?0:(int)row2.get(20));
     	 carr2.set工单ID(row2.get(10)==null?0:(int)row2.get(10));
     	 carr2.setPack类型(row2.get(21)==null?0:Integer.parseInt(row2.get(21).toString()));
     	 carr2.set模组层数(row2.get(22)==null?0:Integer.parseInt(row2.get(22).toString()));
     	 carr2.set模组序号(row2.get(24)==null?0:Integer.parseInt(row2.get(24).toString()));
     	 carr2.set模组编码(row2.get(5)==null?"0":row2.get(5).toString());
     	 String beifangs2="";	 
     	 Vector<Vector> tost2=SqlTool.findInVector("select 工位,物料 from 配方指令队列   where 工单ID='"+
    	     	 carr2.get工单ID()+"' and 模组序ID='"+carr.get模组序ID()+"' and 分解号='"+carr2.get分解号()+"' and 载具序号='"+carr2.get载具序号()+"' order by 工位");
    	     	 for(int m=0;m<tost2.size();m++){
    	     		 Vector vv=(Vector)tost2.get(m);
    	     		 carr2.set工位(carr2.get工位()+""+vv.get(0));
    	     		 beifangs2= (vv.get(1)==null?"":vv.get(1))+"!_!"+beifangs2;
    	         }
    	     	 
    	     	 if(!beifangs2.equals("")){
     	     		beifangs2=beifangs2.substring(0,  beifangs2.length()-3) ;
     	     	    }
     	     	 carr2.set配方(beifangs2);
    	  
    	    if(装配区==1) 	 {
  		          PLC.getIntance().line.setBuffer(carr2);
  		        ((_FST)secondST).set允许工位动作标志(true);}
    	          
    	    else  {PLC.getIntance().line2.setBuffer(carr2);
    	         ((_FST)secondST).set允许工位动作标志(true);}
    	 		 
    	 	}else{
    	 		if(tem.size()==1){
    	 		 Vector row=(Vector)tem.get(0);
    	 		 ((_FST)firstST).clear();
    	 		 ((_FST)firstST).setId(row.get(0)==null?0:(int)row.get(0));
    	 		 ((_FST)firstST).set工单号(row.get(1)==null?0:(int)row.get(1));
    	 		 ((_FST)firstST).set分解号(row.get(2)==null?0:(int)row.get(2));
    	 		 ((_FST)firstST).set载具序号(row.get(3)==null?0:(int)row.get(3));
       	 		 ((_FST)firstST).set翻B面(row.get(8).equals("是")?true:false);
       	 		 ((_FST)firstST).set允许工位动作标志(false);
       	 		 ((_FST)firstST).set立库RDY(false);
       	 	    firstST.set工单ID(row.get(10)==null?0:(int)row.get(10));
	 		    firstST.set模组序ID(row.get(11)==null?0:(int)row.get(11));
       	 		firstST.setWrite(true);
       	 	    update标志(firstST,1);
       	 	    
       	 	 Carry carr=new Carry(firstST.get工单号(), firstST.get分解号(), firstST.get载具序号(),firstST.get模组序ID());
       	 	 carr.set假电芯1(row.get(12).toString().equals("0")?0:(int)row.get(12));
	     	 carr.set假电芯2(row.get(13).toString().equals("0")?0:(int)row.get(13));
	     	 carr.set电芯位置1(row.get(14)==null?null:row.get(14).toString());
	     	 carr.set电芯位置2(row.get(15)==null?null:row.get(15).toString());
	     	 carr.set电芯位置3(row.get(16)==null?null:row.get(16).toString());
	     	 carr.set电芯位置4(row.get(17)==null?null:row.get(17).toString());
	     	 carr.set叠装否(row.get(18).equals("是")?true:false);
	     	 carr.set工单ID(row.get(10)==null?0:(int)row.get(10));
	     	 carr.set模组类型(row.get(19)==null?0:(int)row.get(19));
	     	 carr.set电芯类型(row.get(20)==null?0:(int)row.get(20));
	     	 carr.setPack类型(row.get(21)==null?0:Integer.parseInt(row.get(21).toString()));
	     	 carr.set模组层数(row.get(22)==null?0:Integer.parseInt(row.get(22).toString()));
	     	 carr.set模组序号(row.get(24)==null?0:Integer.parseInt(row.get(24).toString()));
	     	 carr.set模组编码(row.get(5)==null?"0":row.get(5).toString());
	     	Vector<Vector> tost=SqlTool.findInVector("select 工位,物料 from 配方指令队列   where 工单ID='"+
	    	     	 carr.get工单ID()+"' and 模组序ID='"+carr.get模组序ID()+"' and 分解号='"+carr.get分解号()+"' and 载具序号='"+carr.get载具序号()+"' order by 工位");
	    	     	
	    	     	 String beifangs="";
	    	     	 for(int m=0;m<tost.size();m++){
	    	     		 Vector vv=(Vector)tost.get(m);
	    	     	     carr.set工位(carr.get工位()+""+vv.get(0)); 
	    	     	     beifangs= (vv.get(1)==null?"":vv.get(1))+"!_!"+beifangs;
	    	         }
	    	     	 if(!beifangs.equals("")){
	    	     		beifangs=beifangs.substring(0,  beifangs.length()-3) ;
	    	     	    }
	    	     	 carr.set配方(beifangs);     	 
	    	     	 if(装配区==1) {	 
	  		             PLC.getIntance().line.addFist(carr);
	  		           ((_FST)firstST).set允许工位动作标志(true);}
	    	     	 else  {PLC.getIntance().line2.addFist(carr);
	    	     	      ((_FST)firstST).set允许工位动作标志(true);
	    	     	 }
    	 		}
    	 		
    	 	}
                 }
    	
    	 if(firstST.isWrite()&&!secondST.isWrite()){
    		  //如果第一个指令有队列，第二个没有，那么就写入第二条指令，读取下一条数据库的动作，前升降机读取标志=0的第一条记录。
    		 Vector<Vector> tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位,工单ID,模组序ID,IFNULL(假电芯1,0),IFNULL(假电芯2,0),电芯位置1,电芯位置2,电芯位置3,电芯位置4,IFNULL(叠装否,'否') ,模组类型,电芯类型 ,pack类型,模组层数,载具位置,模组序号,COUNT(DISTINCT 工单序号,模组序ID,分解号,载具序号 )  from 配方指令队列  where  装配区="+装配区+"      and IFNULL(前升读标志,0)<>1 GROUP BY 工单序号,模组序号,分解号,载具序号   ORDER BY 工单序号,模组序号,分解号,载具序号 LIMIT 10");
    		 if(tem.size()>0){
    	 		 Vector row=(Vector)tem.get(0);
    	 		 ((_FST)secondST).clear();
    	 		 ((_FST)secondST).setId(row.get(0)==null?0:(int)row.get(0));
       	 		 ((_FST)secondST).set工单号(row.get(1)==null?0:(int)row.get(1));
       	 		 ((_FST)secondST).set分解号(row.get(2)==null?0:(int)row.get(2));
       	 		 ((_FST)secondST).set载具序号(row.get(3)==null?0:(int)row.get(3));
       	 		 ((_FST)secondST).set翻B面(row.get(8).equals("是")?true:false);
       	 		 ((_FST)secondST).set允许工位动作标志(true);
       	 		 ((_FST)secondST).set立库RDY(false);
       	 		secondST.set工单ID(row.get(10)==null?0:(int)row.get(10));
       	 	    secondST.set模组序ID(row.get(11)==null?0:(int)row.get(11));
       	 		secondST.setWrite(true);
       	 	     update标志(secondST,1);
       	 	 Carry carr2=new Carry( secondST.get工单号(),  secondST.get分解号(),  secondST.get载具序号(), secondST.get模组序ID());
       	 	 carr2.set假电芯1(row.get(12).toString().equals("0")?0:(int)row.get(12));
	     	 carr2.set假电芯2(row.get(13).toString().equals("0")?0:(int)row.get(13));
         	 carr2.set电芯位置1(row.get(14)==null?null:row.get(14).toString());
         	 carr2.set电芯位置2(row.get(15)==null?null:row.get(15).toString());
         	 carr2.set电芯位置3(row.get(16)==null?null:row.get(16).toString());
         	 carr2.set电芯位置4(row.get(17)==null?null:row.get(17).toString());
         	 carr2.set叠装否(row.get(18).equals("是")?true:false);
         	 carr2.set模组类型(row.get(19)==null?0:(int)row.get(19));
        	 carr2.set电芯类型(row.get(20)==null?0:(int)row.get(20));
        	 carr2.set工单ID(row.get(10)==null?0:(int)row.get(10));
        	 carr2.setPack类型(row.get(21)==null?0:Integer.parseInt(row.get(21).toString()));
        	 carr2.set模组层数(row.get(22)==null?0:Integer.parseInt(row.get(22).toString()));
        	 carr2.set模组序号(row.get(24)==null?0:Integer.parseInt(row.get(24).toString()));
        	 carr2.set模组编码(row.get(5)==null?"0":row.get(5).toString());
        	 Vector<Vector> tost=SqlTool.findInVector("select 工位,物料 from 配方指令队列   where 工单ID='"+
        	     	 carr2.get工单ID()+"' and 模组序ID='"+carr2.get模组序ID()+"' and 分解号='"+carr2.get分解号()+"' and 载具序号='"+carr2.get载具序号()+"' order by 工位");
        	     	
        	     	 String beifangs="";
        	     	 for(int m=0;m<tost.size();m++){
        	     		 Vector vv=(Vector)tost.get(m);
        	     	     carr2.set工位(carr2.get工位()+""+vv.get(0)); 
        	     	     beifangs= (vv.get(1)==null?"":vv.get(1))+"!_!"+beifangs;
        	         }
        	     	 if(!beifangs.equals("")){
        	     		beifangs=beifangs.substring(0,  beifangs.length()-3) ;
        	     	    }
        	     	 carr2.set配方(beifangs);
        	     	 
        	     	if(装配区==1) {
      		               PLC.getIntance().line.setBuffer(carr2);
      		               ((_FST)secondST).set允许工位动作标志(true);   
        	     	    }
        	     	else { PLC.getIntance().line2.setBuffer(carr2);
        	     	        ((_FST)secondST).set允许工位动作标志(true);   
        	          	}
        	 		 
    	 		}
    	 		
    	  }
    	 
    	 if(!firstST.isWrite()&&secondST.isWrite()){ 
    		  //如果第一个指令有队列，第二个没有，那么把第二条指令移到第一条动作指令，然后在写入第二条动作指令
    		 ((_FST)firstST).set允许工位动作标志(false);   
    		 if(装配区==1) {
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
    	 if(装配区==1) {
    		 if(PLC.getIntance().line.getCarry(0)==null){
    			 ((_FST)firstST).set允许工位动作标志(false); 
    			 
    		 }
    		
    		 
    	 }
    		 else{
    			 
    			 if(PLC.getIntance().line2.getCarry(0)==null){
        			 ((_FST)firstST).set允许工位动作标志(false); 
        			 
        		 }
    		 }
    	 
    	}
    	
    	
    	///////////////////////////////////////////////////////
    	
    	if(stNum>=2&&stNum<=7){//动作容许标志默认false，需要判断
    		
    		if(!firstST.isWrite()&&!secondST.isWrite()){
    			
    			 firstST.set数据处理中(false);secondST.set数据处理中(false);
    			 
    			Vector<Vector> tem=new Vector<Vector>();
    			Carry cc=null;
    			if(装配区==1){cc=PLC.getIntance().line.getCarry(0);
    			}else{
    				       cc=PLC.getIntance().line2.getCarry(0);
    			}
    			if(false/*cc!=null*/){
          tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型 ,工单ID,模组序ID,模组层数,载具位置 ,第二编码  from 配方指令队列,通用物料 "+
    "  where  工单ID="+cc.get工单ID()+" and IFNULL(ST读取标志,0)<>1  and 装配区='"+装配区+"' and 模组序ID='"+cc.get模组序ID()+"' and 分解号='"+cc.get分解号()+"' "
    		+ " and 工位='"+(stNum-1)+"ST'  and 物料=物料编码 ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");
          
    			}else{
        tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型 ,工单ID,模组序ID,模组层数,载具位置,第二编码 from 配方指令队列,通用物料 "+
    			 "  where   工位='"+(stNum-1)+"ST'"+" and IFNULL(ST读取标志,0)<>1  and 装配区='"+装配区+"' "
    				+ " and 物料=物料编码  ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");				
    				
    			}
    	    	 	if(tem.size()>1){
    	    	 		 Vector row=(Vector)tem.get(0);
    	    	 		 Vector row2=(Vector)tem.get(1);
    	    	 		 ((_1_6ST)firstST).clear();
    	    	 		 ((_1_6ST)firstST).setId((int)row.get(0));
    	    	 		 ((_1_6ST)firstST).set工单号((int)row.get(1));
    	    	 		 ((_1_6ST)firstST).set分解号((int)row.get(2));
    	    	 		 ((_1_6ST)firstST).set载具序号((int)row.get(3));
    	    	 		 ((_1_6ST)firstST).set允许工位动作标志(false);
    	    	 		 ((_1_6ST)firstST).set投放型腔标志((row.get(15)==null?0:Integer.parseInt(row.get(15).toString()))==2);//根据
    	    	 		// System.out.println(row.get(10).getClass());
    	    	 		 ((_1_6ST)firstST).set模组类型标志((int)row.get(10));
    	    	 		 
    	    	 		 ((_1_6ST)firstST).set电芯类型标志((int)row.get(11));
    	    	 		 ((_1_6ST)firstST).set需求数量((int)row.get(7));
    	    	 		 ((_1_6ST)firstST).set立库RDY(false);
    	    	 		  ((_1_6ST)firstST).set配方特征(row.get(16)==null?0:(row.get(16).equals("")?0:Integer.parseInt(row.get(16)+"")));
    	    	 		 firstST.set工单ID((int)row.get(12));
    	    	 		 firstST.set模组序ID((int)row.get(13));
    	    	 		 firstST.set物料编码((String)row.get(6));
    	    	 		 firstST.setWrite(true);
    	    	 	     update标志(firstST,2);
    	    	 		
    	    	 		 
    	    	 		 ((_1_6ST)secondST).clear();
    	    	 		 ((_1_6ST)secondST).setId((int)row2.get(0));
    	    	 		 ((_1_6ST)secondST).set工单号((int)row2.get(1));
    	    	 		 ((_1_6ST)secondST).set分解号((int)row2.get(2));
    	    	 		 ((_1_6ST)secondST).set载具序号((int)row2.get(3));
    	    	 		 ((_1_6ST)secondST).set允许工位动作标志(false);
    	    	 		 ((_1_6ST)secondST).set投放型腔标志((row2.get(15)==null?0:Integer.parseInt(row2.get(15).toString()))==2);//根据
    	    	 		 ((_1_6ST)secondST).set模组类型标志((int)row2.get(10));
    	    	 		 ((_1_6ST)secondST).set电芯类型标志((int)row2.get(11));
    	    	 		 ((_1_6ST)secondST).set需求数量((int)row2.get(7));
    	    	 		 ((_1_6ST)secondST).set立库RDY(false);
    	    	 		 secondST.set工单ID((int)row2.get(12));
    	    	 		 secondST.set模组序ID((int)row2.get(13));
    	    	 		 secondST.set物料编码((String)row2.get(6));
    	    	 		 ((_1_6ST)secondST).set配方特征(row2.get(16)==null?0:(row2.get(16).equals("")?0:Integer.parseInt(row2.get(16)+"")));
    	    	 		 secondST.setWrite(true);
    	    	 		
    	    	 		 update标志(secondST,2);
    	    	 		 
    	    	 	}else{
    	    	 		if(tem.size()==1){
    	    	 		 Vector row=(Vector)tem.get(0);
    	    	 		 ((_1_6ST)firstST).clear();
    	    	 		 ((_1_6ST)firstST).setId((int)row.get(0));
    	    	 		 ((_1_6ST)firstST).set工单号((int)row.get(1));
    	    	 		 ((_1_6ST)firstST).set分解号((int)row.get(2));
    	    	 		 ((_1_6ST)firstST).set载具序号((int)row.get(3));
    	    	 		 ((_1_6ST)firstST).set允许工位动作标志(false);
    	    	 		 ((_1_6ST)firstST).set投放型腔标志((row.get(15)==null?0:Integer.parseInt(row.get(15).toString()))==2);//根据
    	    	 		 ((_1_6ST)firstST).set模组类型标志((int)row.get(10));
    	    	 		 ((_1_6ST)firstST).set电芯类型标志((int)row.get(11));
    	    	 		 ((_1_6ST)firstST).set需求数量((int)row.get(7));
    	    	 		 ((_1_6ST)firstST).set立库RDY(false);
    	    	 		firstST.set工单ID((int)row.get(12));
    	    	 		firstST.set模组序ID((int)row.get(13));
    	    	 		firstST.set物料编码((String)row.get(6));
    	    	 		firstST.setWrite(true);
    	    	 		 ((_1_6ST)firstST).set配方特征(row.get(16)==null?0:(row.get(16).equals("")?0:Integer.parseInt(row.get(16)+"")));
    	    	 		update标志(firstST,2);
    	    	 		}
    	    	 		
    	    	 	}
    			
    		 }
    		
    		if(firstST.isWrite()&&!secondST.isWrite()){
    			Vector<Vector> tem=new Vector<Vector>();
    			Carry cc=null;
    			if(装配区==1){cc=PLC.getIntance().line.getCarry(0);
    			}else{
    				cc=PLC.getIntance().line2.getCarry(0);
    			}
    			if(false/*cc!=null*/){
          tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型 ,工单ID,模组序ID,模组层数,载具位置 ,第二编码  from 配方指令队列 ,通用物料"+
    "  where  工单ID="+cc.get工单ID()+" and IFNULL(ST读取标志,0)<>1  and 装配区='"+装配区+"' and 模组序ID='"+cc.get模组序ID()+"' and 分解号='"+cc.get分解号()+"' "
    		+ " and 工位='"+(stNum-1)+"ST' and 物料=物料编码  ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");
          
    			}else{
    	  tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型 ,工单ID,模组序ID,模组层数,载具位置 ,第二编码  from 配方指令队列,通用物料 "+
    			  "  where   工位='"+(stNum-1)+"ST'"+" and IFNULL(ST读取标志,0)<>1  and 装配区='"+装配区+"'  and 物料=物料编码  "
  				+ " ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");	
    				
    			}
		// Vector<Vector> tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型 ,工单ID,模组序ID from 配方指令队列  where  装配区="+装配区+" and IFNULL(ST读取标志,0)<>1  and 工位='"+(stNum-1)+"ST' ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");		
				 if(tem.size()>0){
				    Vector row=(Vector)tem.get(0); 
				   ((_1_6ST)secondST).clear();
				   ((_1_6ST)secondST).setId((int)row.get(0));
				   ((_1_6ST)secondST).set工单号((int)row.get(1));
				   ((_1_6ST)secondST).set分解号((int)row.get(2));
				   ((_1_6ST)secondST).set载具序号((int)row.get(3));
				   ((_1_6ST)secondST).set允许工位动作标志(false);
				   ((_1_6ST)secondST).set投放型腔标志((row.get(15)==null?0:Integer.parseInt(row.get(15).toString()))==2);//根据
				   ((_1_6ST)secondST).set模组类型标志((int)row.get(10));
				   ((_1_6ST)secondST).set电芯类型标志((int)row.get(11));
				   ((_1_6ST)secondST).set需求数量((int)row.get(7));
				   ((_1_6ST)secondST).set立库RDY(false);
				   secondST.set工单ID((int)row.get(12));
	    	 	   secondST.set模组序ID((int)row.get(13));
	    	 	   secondST.set物料编码((String)row.get(6));
				   secondST.setWrite(true);	
				   ((_1_6ST)secondST).set配方特征(row.get(16)==null?0:(row.get(16).equals("")?0:Integer.parseInt(row.get(16)+"")));
				   update标志(secondST,2);
				 
				 }
    		}
    		
    		if(!firstST.isWrite()&&secondST.isWrite()){
    			//那么secondST移动到first,然后secondST重读
    			
       		 firstST.intFromST(secondST);
       		 secondST.setWrite(false);
       		 secondST.clear();
    		}
    		
    		updata动作();
    		
    	}
    
    	////////////////////////////////////
    	 if(stNum==8){//动作容许标志默认false，需要判断
   		    //假电芯工位
           if(!firstST.isWrite()&&!secondST.isWrite()){
        	   firstST.set数据处理中(false);secondST.set数据处理中(false);
        	   Vector<Vector> tem=new Vector<Vector>();
   			Carry cc=null;
   			if(装配区==1){cc=PLC.getIntance().line.getCarry(0);
   			}else{
   				cc=PLC.getIntance().line2.getCarry(0);
   			}
   			if(false/*cc!=null*/){
         tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型 ,假电芯1,假电芯2,工单ID,模组序ID,模组层数,载具位置,第二编码  from 配方指令队列,通用物料 "+
   "  where 工单ID="+cc.get工单ID()+" and IFNULL(ST读取标志,0)<>1  and 装配区='"+装配区+"' and 模组序ID='"+cc.get模组序ID()+"' and 分解号='"+cc.get分解号()+"' "
   		+ " and 工位='"+(stNum-1)+"ST'  and 物料=物料编码  ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");
         
   			}else{
   	tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型 ,假电芯1,假电芯2,工单ID,模组序ID,模组层数,载具位置,第二编码  from 配方指令队列,通用物料  "+
   		 "  where   工位='"+(stNum-1)+"ST'"+" and IFNULL(ST读取标志,0)<>1  and 装配区='"+装配区+"'  and 物料=物料编码  "
			+ " ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");					
   				
   			}    	   
    // Vector<Vector> tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型,假电芯1,假电芯2,工单ID,模组序ID "
     	//	+"  from 配方指令队列  where  装配区="+装配区+" and IFNULL(ST读取标志,0)<>3  and 工位='7ST' ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");
     	    	 	if(tem.size()>1){
     	    	 		 Vector row=(Vector)tem.get(0);
     	    	 		 Vector row2=(Vector)tem.get(1);
     	    	 
     	    	 		 
     	    	 		 ((_7ST)firstST).clear();
     	    	 		 ((_7ST)firstST).setId((int)row.get(0));
     	    	 		 ((_7ST)firstST).set工单号((int)row.get(1));
     	    	 		 ((_7ST)firstST).set分解号((int)row.get(2));
     	    	 		 ((_7ST)firstST).set载具序号((int)row.get(3));
     	    	 		 ((_7ST)firstST).set允许工位动作标志(false);
     	    	 		((_7ST)firstST).set第1个假电芯位置(row.get(12)==null?0:(int)row.get(12));
     	    	 	    ((_7ST)firstST).set第2个假电芯位置(row.get(13)==null?0:(int)row.get(13));
     	    	 		 ((_7ST)firstST).set模组类型标志((int)row.get(10));
     	    	 		 ((_7ST)firstST).set电芯类型标志((int)row.get(11));
     	    	 		 ((_7ST)firstST).set需求数量((int)row.get(7));
     	    	 		 ((_7ST)firstST).set立库RDY(false);
     	    	 		 firstST.set工单ID((int)row.get(14));
    	    	 		 firstST.set模组序ID((int)row.get(15));
    	    	 		firstST.set物料编码((String)row.get(6));
     	    	 		 firstST.setWrite(true);
     	    	 		 update标志(firstST,2);
     	    	 		
     	    	 	
     	    	 		 ((_7ST)secondST).clear();
     	    	 		 ((_7ST)secondST).setId((int)row2.get(0));
     	    	 		 ((_7ST)secondST).set工单号((int)row2.get(1));
     	    	 		 ((_7ST)secondST).set分解号((int)row2.get(2));
     	    	 		 ((_7ST)secondST).set载具序号((int)row2.get(3));
     	    	 		 ((_7ST)secondST).set允许工位动作标志(false);
     	    	 		((_7ST)secondST).set第1个假电芯位置(row2.get(12)==null?0:(int)row2.get(12));
     	    	 	    ((_7ST)secondST).set第2个假电芯位置(row2.get(13)==null?0:(int)row2.get(13));
     	    	 		 ((_7ST)secondST).set模组类型标志((int)row2.get(10));
     	    	 		 ((_7ST)secondST).set电芯类型标志((int)row2.get(11));
     	    	 		 ((_7ST)secondST).set需求数量((int)row2.get(7));
     	    	 		 ((_7ST)secondST).set立库RDY(false);
     	    	 		 secondST.set工单ID((int)row2.get(14));
     	    	 		 secondST.set模组序ID((int)row2.get(15));
     	    	 		 secondST.set物料编码((String)row2.get(6));
     	    	 		 secondST.setWrite(true);
     	    	 		 update标志(secondST,2);
     	    	 		 
     	    	 	}else{
     	    	 		if(tem.size()==1){
     	    	 		 Vector row=(Vector)tem.get(0);
     	    	 		
     	    	 		 ((_7ST)firstST).clear();
     	    	 		 ((_7ST)firstST).setId((int)row.get(0));
     	    	 		 ((_7ST)firstST).set工单号((int)row.get(1));
     	    	 		 ((_7ST)firstST).set分解号((int)row.get(2));
     	    	 		 ((_7ST)firstST).set载具序号((int)row.get(3));
     	    	 		 ((_7ST)firstST).set允许工位动作标志(false);
     	    	 		((_7ST)firstST).set第1个假电芯位置(row.get(12)==null?0:(int)row.get(12));
     	    	 	    ((_7ST)firstST).set第2个假电芯位置(row.get(13)==null?0:(int)row.get(13));
     	    	 		 ((_7ST)firstST).set模组类型标志((int)row.get(10));
     	    	 		 ((_7ST)firstST).set电芯类型标志((int)row.get(11));
     	    	 		 ((_7ST)firstST).set需求数量((int)row.get(7));
     	    	 		 ((_7ST)firstST).set立库RDY(false);
     	    	 		 firstST.set工单ID((int)row.get(14));
    	    	 		 firstST.set模组序ID((int)row.get(15));
    	    	 		 firstST.set物料编码((String)row.get(6));
     	    	 		 firstST.setWrite(true);
     	    	 		 update标志(firstST,2);
     	    	 		}
     	    	 		
     	    	 	}
     			
     		 }
     		
     		if(firstST.isWrite()&&!secondST.isWrite()){
     			  Vector<Vector> tem=new Vector<Vector>();
     	   			Carry cc=null;
     	   			if(装配区==1){cc=PLC.getIntance().line.getCarry(0);
     	   			}else{
     	   				cc=PLC.getIntance().line2.getCarry(0);
     	   			}
     	   		if(false/*cc!=null*/){
     	         tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型 ,假电芯1,假电芯2,工单ID,模组序ID,模组层数,载具位置 ,第二编码 from 配方指令队列 ,通用物料"+
     	   "  where  工单ID="+cc.get工单ID()+" and IFNULL(ST读取标志,0)<>1  and 装配区='"+装配区+"' and 模组序ID='"+cc.get模组序ID()+"' and 分解号='"+cc.get分解号()+"' "
     	   		+ " and 工位='"+(stNum-1)+"ST' and 物料=通用物料 ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");
     	         
     	   			} 
     	   		else{
     	   		tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型 ,假电芯1,假电芯2,工单ID,模组序ID,模组层数,载具位置 ,第二编码 from 配方指令队列 ,通用物料"+
     	   			 "  where   工位='"+(stNum-1)+"ST'"+" and IFNULL(ST读取标志,0)<>1  and 装配区='"+装配区+"' and 物料=通用物料  "
     				+ " ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");	 				
     	   			} 			
     			// Vector<Vector> tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位 ,模组类型,电芯类型,假电芯1,假电芯2,工单ID,模组序ID "
     		  //   		+"  from 配方指令队列  where  装配区="+装配区+" and IFNULL(ST读取标志,0)<>1  and 工位='7ST' ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 3");
 				 if(tem.size()>0){
 				    Vector row=(Vector)tem.get(0); 
 				    
 				   ((_7ST)secondST).clear();
 				   ((_7ST)secondST).setId((int)row.get(0));
 				   ((_7ST)secondST).set工单号((int)row.get(1));
 				   ((_7ST)secondST).set分解号((int)row.get(2));
 				   ((_7ST)secondST).set载具序号((int)row.get(3));
 				   ((_7ST)secondST).set允许工位动作标志(false);
 				   ((_7ST)secondST).set第1个假电芯位置(row.get(12)==null?0:(int)row.get(12));
	    	 	   ((_7ST)secondST).set第2个假电芯位置(row.get(13)==null?0:(int)row.get(13));
 				   ((_7ST)secondST).set模组类型标志((int)row.get(10));
 				   ((_7ST)secondST).set电芯类型标志((int)row.get(11));
 				   ((_7ST)secondST).set需求数量((int)row.get(7));
 				   ((_7ST)secondST).set立库RDY(false);
 				   secondST.set工单ID((int)row.get(14));
 				   secondST.set模组序ID((int)row.get(15));
 				   secondST.set物料编码((String)row.get(6));
 				   secondST.setWrite(true);	
 				   update标志(secondST,2);
 				 
 				 }
     		}
     		if(!firstST.isWrite()&&secondST.isWrite()){
     			//那么secondST移动到first,然后secondST重读
     			
        		 firstST.intFromST(secondST);
        		 secondST.setWrite(false);
        		 secondST.clear();
     		}
     		
     		updata动作();
   	     }
    	 
    	 
    	 if(stNum==9||stNum==11||stNum==12){//动作容许标志默认false，托盘到这儿直接放行
    		 boolean 数据更新完成 =((_1_6ST)firstST).is数据更新完成();
    		// boolean  数据处理中=((_1_6ST)firstST).is数据处理中();
    		 ((_1_6ST)firstST).clear();
    		 ((_1_6ST)firstST).set允许工位动作标志(false);
    		 ((_1_6ST)firstST).set数据更新完成(数据更新完成);
    		// ((_1_6ST)firstST).set数据处理中(数据处理中);
    		 ((_1_6ST)firstST).set立库RDY(true);
    		 Carry ca= plc.line.getCarry(stNum-1);//本工位,只要当载具到了本工位才进行处理
    		 if(ca!=null){
    			
    	 		 ((_1_6ST)firstST).setId(ca.调度ID);
    	 		 ((_1_6ST)firstST).set工单号(ca.工单号);
    	 		 ((_1_6ST)firstST).set分解号(ca.分解号);
    	 		 ((_1_6ST)firstST).set工单ID(ca.get工单ID());
    	 		 ((_1_6ST)firstST).set模组序ID(ca.get模组序ID());
    	 		 ((_1_6ST)firstST).set分解号(ca.get分解号());
    	 		 ((_1_6ST)firstST).set载具序号(ca.载具序号); 
    	 		 
    	 		 if(stNum==9){//给8ST的工位单盖板载具赋值
    	 			String pei=ca.get配方(); 
    	 			if(pei!=null){
    	 				 String sm[]=pei.split("!_!")	;
    	 				 if(sm.length==1){
    	 					String back= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[0]+"'");
    	 					if(back!=null&&back.equals("1000")) {
    	 					    ((_1_6ST)firstST).set单盖板载具(true);}
    	 					else{
    	 						((_1_6ST)firstST).set单盖板载具(false);  
    	 					  }
    	 					
    	 					 
    	 				   }else{
    	 					  ((_1_6ST)firstST).set单盖板载具(false);  
    	 					   
    	 				   }
    	 				
    	 			  }
    	 			 
    	 		   }
    			 
    		 }
    		 
    	 }
    	
    	 if(stNum==10){//预装工位，9ST
    		  //动作容许标志默认false，托盘到这儿判断设置值
    		   //同时把异步输送线的队列也处理了
    		 int next=stNum-1;
    		 if(!firstST.isWrite()&&!secondST.isWrite()){
    			 firstST.set数据处理中(false);secondST.set数据处理中(false);
    			 for(int i=stNum-1;i>=checkNum_预装;i--){//从9向前判断
    				 Carry car=null; 
    				 if(装配区==1)
    			  car=plc.line.getCarry(i);
    				 else  car=plc.line2.getCarry(i);
    			 if(car==null){continue;}
    			 
    			 String ss1= car.get电芯位置1(); String ss2= car.get电芯位置2();
    			 String ss3= car.get电芯位置3(); String ss4= car.get电芯位置4();
    			// System.out.println(car.载具序号+"=====================");
    		if(ss1!=null||ss2!=null||ss3!=null||ss4!=null){
    			int d1=0;int d2=0;int d3=0;int d4=0;
    			if(ss1!=null){d1=Integer.parseInt(ss1.split("=")[0]);}
    			if(ss2!=null){d2=Integer.parseInt(ss2.split("=")[0]);}
    			if(ss3!=null){d3=Integer.parseInt(ss3.split("=")[0]);}
    			if(ss4!=null){d4=Integer.parseInt(ss4.split("=")[0]);}
    		 					 
    	    ((_9ST)firstST).clear();
			((_9ST)firstST).setId(car.get调度ID());
			((_9ST)firstST).set工单号(car.get工单号());
			((_9ST)firstST).set工单ID(car.get工单ID());
			((_9ST)firstST).set模组序ID(car.get模组序ID());
			((_9ST)firstST).set分解号(car.get分解号());
			((_9ST)firstST).set载具序号(car.载具序号);
			((_9ST)firstST).set允许工位动作标志(false);
			((_9ST)firstST).setPACK号(car.get工单号());
			((_9ST)firstST).setPACK类型标志(car.getPack类型());
			((_9ST)firstST).set模组号(car.get分解号());
			 int xuhao=car.get模组序号();int fenjie=car.get分解号();
  			 try{
  				 if(fenjie<9){
  					((_9ST)firstST).set模组号(Integer.parseInt((xuhao+"0"+fenjie))) ;
  					 
  				 }else{
  					((_9ST)firstST).set模组号(Integer.parseInt((xuhao+""+fenjie))) ;	 
  					 
  				 }
  				 
  			   }catch(Exception e){}
			
			((_9ST)firstST).set模组类型标志(car.get模组类型());
			((_9ST)firstST).set电芯类型标志(car.get电芯类型());
			((_9ST)firstST).set需求数量(d1+d2+d3+d4);
			((_9ST)firstST).set第1个电芯位置(d1);
			((_9ST)firstST).set第2个电芯位置(d2);
			((_9ST)firstST).set第3个电芯位置(d3);
			((_9ST)firstST).set第4个电芯位置(d4);
			((_9ST)firstST).set立库RDY(true);
			((_9ST)firstST).setWrite(true);
		///////////////////////////////////////////////////////////////////////////////////////////////////	
			String pei=car.get配方(); 
	 			if(pei!=null){
	 				 String sm[]=pei.split("!_!")	;
	 				 if(sm.length==2){
	 					 boolean is有盖板=false;
	 					String back= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[0]+"'");
	 					String back2= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[1]+"'");
	 					if(back!=null&&back.equals("1000")) {
	 						   is有盖板=true;  }
	 					if(back2!=null&&back2.equals("1000")) {
	 						   is有盖板=true;  }
	 					
	 					if(!is有盖板)
	 					  {
	 						
	 					
	 					if(back!=null&&!back.equals("0")) {
	 						((_9ST)firstST).set配方特征(Integer.parseInt(back.equals("")?"0":back)); 
	 						   
	 					}
	 					if(back2!=null&&!back2.equals("0")) {
	 						((_9ST)firstST).set配方特征(Integer.parseInt(back2.equals("")?"0":back2)); 
	 						}
	 					
	 					  }
	 					 
	 				   }else{
	 					  if(sm.length==1){
	 						 String back= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[0]+"'");
	 						if(back!=null&&!back.equals("1000")){
	 							((_9ST)firstST).set配方特征(Integer.parseInt(back.equals("")?"0":back)); 
	 							
	 						 }
	 						  
	 					  }
	 					   
	 				   }
	 				
	 			  }
		////////////////////////////////////////////////////////////////////////////////////////	
			
			  next=i;
			    break;
    			 }
    		
    	}//////////////////////////////////////end for
    	 for(int i=next-1;i>=checkNum_预装;i--){
    		 Carry car=null; 
			 if(装配区==1)
		           car=plc.line.getCarry(i);
			 else  car=plc.line2.getCarry(i);
        			 if(car==null){continue;}
        			 String ss1= car.get电芯位置1(); String ss2= car.get电芯位置2();
        			 String ss3= car.get电芯位置3(); String ss4= car.get电芯位置4();
        			// System.out.println(car.载具序号+"=2====================");
        		if(ss1!=null||ss2!=null||ss3!=null||ss4!=null){
        			int d1=0;int d2=0;int d3=0;int d4=0;
        			if(ss1!=null){d1=Integer.parseInt(ss1.split("=")[0]);}
        			if(ss2!=null){d2=Integer.parseInt(ss2.split("=")[0]);}
        			if(ss3!=null){d3=Integer.parseInt(ss3.split("=")[0]);}
        			if(ss4!=null){d4=Integer.parseInt(ss4.split("=")[0]);}
        		 					 
        			    ((_9ST)secondST).clear();
        				((_9ST)secondST).setId(car.get调度ID());
        				((_9ST)secondST).set工单号(car.get工单号());
        				((_9ST)secondST).set工单ID(car.get工单ID());
        				((_9ST)secondST).set模组序ID(car.get模组序ID());
        				((_9ST)secondST).set分解号(car.get分解号());
        				((_9ST)secondST).set载具序号(car.载具序号);
        				((_9ST)secondST).set允许工位动作标志(false);
        				((_9ST)secondST).setPACK号(car.get工单号());
        				((_9ST)secondST).setPACK类型标志(car.getPack类型());
        				((_9ST)secondST).set模组号(car.get分解号());
        				 int xuhao=car.get模组序号();int fenjie=car.get分解号();
        	  			 try{
        	  				 if(fenjie<9){
        	  					((_9ST)secondST).set模组号(Integer.parseInt((xuhao+"0"+fenjie))) ;
        	  					 
        	  				 }else{
        	  					((_9ST)secondST).set模组号(Integer.parseInt((xuhao+""+fenjie))) ;	 
        	  					 
        	  				 }
        	  				 
        	  			   }catch(Exception e){}
        				
        				((_9ST)secondST).set模组类型标志(car.get模组类型());
        				((_9ST)secondST).set电芯类型标志(car.get电芯类型());
        				((_9ST)secondST).set需求数量(d1+d2+d3+d4);
        				((_9ST)secondST).set第1个电芯位置(d1);
        				((_9ST)secondST).set第2个电芯位置(d2);
        				((_9ST)secondST).set第3个电芯位置(d3);
        				((_9ST)secondST).set第4个电芯位置(d4);
        				((_9ST)secondST).set立库RDY(true);
        				((_9ST)secondST).setWrite(true);
        				
        				///////////////////////////////////////////////////////////////////////////////////////////////////	
        				String pei=car.get配方(); 
        		 			if(pei!=null){
        		 				 String sm[]=pei.split("!_!")	;
        		 				 if(sm.length==2){
        		 					 boolean is有盖板=false;
        		 					String back= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[0]+"'");
        		 					String back2= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[1]+"'");
        		 					if(back!=null&&back.equals("1000")) {
        		 						   is有盖板=true;  }
        		 					if(back2!=null&&back2.equals("1000")) {
        		 						   is有盖板=true;  }
        		 					
        		 					if(!is有盖板)
        		 					  {
        		 						
        		 					
        		 					if(back!=null&&!back.equals("0")) {
        		 						((_9ST)secondST).set配方特征(Integer.parseInt(back.equals("")?"0":back)); 
        		 						   
        		 					}
        		 					if(back2!=null&&!back2.equals("0")) {
        		 						((_9ST)secondST).set配方特征(Integer.parseInt(back2.equals("")?"0":back2)); 
        		 						}
        		 					
        		 					  }
        		 					 
        		 				   }else{
        		 					  if(sm.length==1){
        		 						 String back= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[0]+"'");
        		 						if(back!=null&&!back.equals("1000")){
        		 							((_9ST)secondST).set配方特征(Integer.parseInt(back.equals("")?"0":back)); 
        		 							
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
    			 for(int i=stNum-1;i>=checkNum_预装;i--){
    				 Carry car=null; 
    				 if(装配区==1)
    			           car=plc.line.getCarry(i);
    				 else  car=plc.line2.getCarry(i);
        			 if(car==null){continue;}
        			 String s=firstST.get工单ID()+""+firstST.get模组序ID()+""+firstST.get分解号()+""+firstST.get载具序号();
        			 String s2=car.get工单ID()+""+car.get模组序ID()+""+car.get分解号()+""+car.get载具序号();
        			 if(s.equals(s2)){continue;}
        			 String ss1= car.get电芯位置1(); String ss2= car.get电芯位置2();
        			 String ss3= car.get电芯位置3(); String ss4= car.get电芯位置4();
        			 //System.out.println(s+"="+s2+"=3====================");
        		if(ss1!=null||ss2!=null||ss3!=null||ss4!=null){
        			int d1=0;int d2=0;int d3=0;int d4=0;
        			if(ss1!=null){d1=Integer.parseInt(ss1.split("=")[0]);}
        			if(ss2!=null){d2=Integer.parseInt(ss2.split("=")[0]);}
        			if(ss3!=null){d3=Integer.parseInt(ss3.split("=")[0]);}
        			if(ss4!=null){d4=Integer.parseInt(ss4.split("=")[0]);}
        	 	 					 
        		((_9ST)secondST).clear();
 				((_9ST)secondST).setId(car.get调度ID());
 				((_9ST)secondST).set工单号(car.get工单号());
 				((_9ST)secondST).set工单ID(car.get工单ID());
 				((_9ST)secondST).set模组序ID(car.get模组序ID());
 				((_9ST)secondST).set分解号(car.get分解号());
 				((_9ST)secondST).set载具序号(car.载具序号);
 				((_9ST)secondST).set允许工位动作标志(false);
 				((_9ST)secondST).setPACK号(car.get工单号());
 				((_9ST)secondST).setPACK类型标志(car.getPack类型());
 				((_9ST)secondST).set模组号(car.get分解号());
 				 int xuhao=car.get模组序号();int fenjie=car.get分解号();
	  			 try{
	  				 if(fenjie<9){
	  					((_9ST)secondST).set模组号(Integer.parseInt((xuhao+"0"+fenjie))) ;
	  					 
	  				 }else{
	  					((_9ST)secondST).set模组号(Integer.parseInt((xuhao+""+fenjie))) ;	 
	  					 
	  				 }
	  				 
	  			   }catch(Exception e){}
 				((_9ST)secondST).set模组类型标志(car.get模组类型());
 				((_9ST)secondST).set电芯类型标志(car.get电芯类型());
 				((_9ST)secondST).set需求数量(d1+d2+d3+d4);
 				((_9ST)secondST).set第1个电芯位置(d1);
 				((_9ST)secondST).set第2个电芯位置(d2);
 				((_9ST)secondST).set第3个电芯位置(d3);
 				((_9ST)secondST).set第4个电芯位置(d4);
 				((_9ST)secondST).set立库RDY(true);
 				((_9ST)secondST).setWrite(true);
 				///////////////////////////////////////////////////////////////////////////////////////////////////	
				String pei=car.get配方(); 
		 			if(pei!=null){
		 				 String sm[]=pei.split("!_!")	;
		 				 if(sm.length==2){
		 					 boolean is有盖板=false;
		 					String back= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[0]+"'");
		 					String back2= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[1]+"'");
		 					if(back!=null&&back.equals("1000")) {
		 						   is有盖板=true;  }
		 					if(back2!=null&&back2.equals("1000")) {
		 						   is有盖板=true;  }
		 					
		 					if(!is有盖板)
		 					  {
		 						
		 					
		 					if(back!=null&&!back.equals("0")) {
		 						((_9ST)secondST).set配方特征(Integer.parseInt(back.equals("")?"0":back)); 
		 						   
		 					}
		 					if(back2!=null&&!back2.equals("0")) {
		 						((_9ST)secondST).set配方特征(Integer.parseInt(back2.equals("")?"0":back2)); 
		 						}
		 					
		 					  }
		 					 
		 				   }else{
		 					  if(sm.length==1){
		 						 String back= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[0]+"'");
		 						if(back!=null&&!back.equals("1000")){
		 							((_9ST)secondST).set配方特征(Integer.parseInt(back.equals("")?"0":back)); 
		 							
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
    		
    		 updata动作();
    	 }
    	 
    	 if(stNum==13){//叠装工位
   		    //叠装工位,当载具到达时发
    		
    		 Carry car=null; 
			 if(装配区==1)
		           car=plc.line.getCarry(12);
			 else  car=plc.line2.getCarry(12);
    			 if(car!=null){
    				 if(!firstST.isWrite()){
    				 ((_12ST)firstST).clear();
	    	 		 ((_12ST)firstST).setId(car.get调度ID());
	    	 		 ((_12ST)firstST).set工单号(car.get工单号());
	    	 		 ((_12ST)firstST).set分解号(car.get分解号());
	    	 		 ((_12ST)firstST).set载具序号(car.get载具序号());
	    	 		 if(car.is叠装否()){
	    	 		 ((_12ST)firstST).set允许工位动作标志(true);
	    	 		 
	    	 		   }
	    	 		 else{
	    	 	     ((_12ST)firstST).set允许工位动作标志(false); 
	    	 	     
	    	 		 }
	    	 		 //((_12ST)firstST).set投放型腔标志(false);
	    	 		 ((_12ST)firstST).set模组类型标志(car.get模组类型());
	    	 		 ((_12ST)firstST).set电芯类型标志(car.get电芯类型());
                    // ((_12ST)firstST).set需求数量(0);
	    	 		 ((_12ST)firstST).set立库RDY(true);
	    	 		 firstST.set工单ID(car.get工单ID());
	    	 		 firstST.set模组序ID(car.get模组序ID());
	    	 		 
	    	 		
	     	 			String pei=car.get配方(); 
	     	 			if(pei!=null){
	     	 				 String sm[]=pei.split("!_!")	;
	     	 				 if(sm.length==2){
	     	 					 boolean is有盖板=false;
	     	 					String back= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[0]+"'");
	     	 					String back2= SqlTool.findOneRecord("Select 第二编码 from 通用物料  where 物料编码='"+sm[1]+"'");
	     	 					if(back!=null&&back.equals("1000")) {
	     	 						   is有盖板=true;  }
	     	 					if(back2!=null&&back2.equals("1000")) {
	     	 						   is有盖板=true;  }
	     	 					
	     	 					if(is有盖板){ ((_12ST)firstST).set有盖板叠装(true ); }
	     	 					else{
	     	 						
	     	 					
	     	 					if(back!=null&&!back.equals("0")) {
	     	 						((_12ST)firstST).set配方特征(Integer.parseInt(back.equals("")?"0":back)); 
	     	 						   
	     	 					}
	     	 					if(back2!=null&&!back2.equals("0")) {
	     	 						((_12ST)firstST).set配方特征(Integer.parseInt(back2.equals("")?"0":back2));
	     	 						}
	     	 					
	     	 					  }
	     	 					 
	     	 				   }else{
	     	 					 ((_12ST)firstST).set有盖板叠装(false );  
	     	 					 
	     	 					  if(sm.length==1){
	     	 						 String back= SqlTool.findOneRecord("Select 第二编码  from 通用物料  where 物料编码='"+sm[0]+"'");
	     	 						if(back!=null&&!back.equals("1000")){
	     	 							((_12ST)firstST).set配方特征(Integer.parseInt(back.equals("")?"0":back)); 
	     	 							
	     	 						 }
	     	 						  
	     	 					  }
	     	 					   
	     	 				   }
	     	 				
	     	 			  }
	     	 			 
	     	 		  
	    	 		 
	    	 		 
	    	 		 
	    	 		 firstST.setWrite(true);	 
    				 }
    			 }else{
    				 boolean f=((_12ST)firstST).is数据更新完成();
    				 firstST.setWrite(false);	
    				 ((_12ST)firstST).clear();
    				 ((_12ST)firstST).set数据更新完成(f);
    				 
    			 }
    		 
   	      }
    	 
    	 if(stNum==14){//到达14的时候在判断_13ST
    		    //取出工位
    		 Carry car=null; 
			 if(装配区==1)
		           car=plc.line.getCarry(13);
			 else  car=plc.line2.getCarry(13);
    		   if(car!=null){
    			   

    	    		  if(!firstST.isWrite()){
    	    		((_13ST)firstST).clear();
    	            ((_13ST)firstST).setId(car.get调度ID());
    	  			((_13ST)firstST).set工单号(car.get工单号());
    	  			((_13ST)firstST).set工单ID(car.get工单ID());
    	  			((_13ST)firstST).set模组序ID(car.get模组序ID());
    	  			((_13ST)firstST).set分解号(car.get分解号());
    	  			((_13ST)firstST).set载具序号(car.载具序号);
    	  			((_13ST)firstST).set允许工位动作标志(true);
    	  			((_13ST)firstST).setPACK号(car.get工单号());
    	  			((_13ST)firstST).setPACK类型标志(car.getPack类型());
    	  			((_13ST)firstST).set模组号(car.get分解号());
    	  			((_13ST)firstST).set模组类型标志(car.get模组类型());
    	  			((_13ST)firstST).set电芯类型标志(car.get电芯类型());
    	  			((_13ST)firstST).set有效型腔数(car.get有效型腔数());
    	  			((_13ST)firstST).set立库RDY(true);
    	  			((_13ST)firstST).set模组层数(car.get模组层数());  
    	  			 int xuhao=car.get模组序号();int fenjie=car.get分解号();
    	  			 try{
    	  				 if(fenjie<9){
    	  					((_13ST)firstST).set模组号(Integer.parseInt((xuhao+"0"+fenjie))) ;
    	  					 
    	  				 }else{
    	  					((_13ST)firstST).set模组号(Integer.parseInt((xuhao+""+fenjie))) ;	 
    	  					 
    	  				 }
    	  				 
    	  			   }catch(Exception e){}
    	  			
    	  			((_13ST)firstST).setWrite(true);
    	  			
    	    		      }  
    		   }else{
    			   boolean f=((_13ST)firstST).is数据更新完成();
    			   ((_13ST)firstST).setWrite(false);  
     			   ((_13ST)firstST).clear();
     			   ((_13ST)firstST).set数据更新完成(f);
     			   
    			   
    		   }
    		 //  ((_13ST)firstST).set允许工位动作标志(true);
    		   
    	      }
    	 
    	 if(stNum==15){//到达工位时发出信号
 		    //后升降机_BST
    		 Carry car=null; 
			 if(装配区==1)
		           car=plc.line.getCarry(14);
			 else  car=plc.line2.getCarry(14);
    		  if(car!=null){
    		
    		  if(!firstST.isWrite()){
    		((_BST)firstST).clear();
            ((_BST)firstST).setId(car.get调度ID());
  			((_BST)firstST).set工单号(car.get工单号());
  			((_BST)firstST).set工单ID(car.get工单ID());
  			((_BST)firstST).set模组序ID(car.get模组序ID());
  			((_BST)firstST).set分解号(car.get分解号());
  			((_BST)firstST).set载具序号(car.载具序号);
  			((_BST)firstST).set允许工位动作标志(true);
  			((_BST)firstST).setPACK号(car.get工单号());
  			((_BST)firstST).setPACK类型标志(car.getPack类型());
  			((_BST)firstST).set模组号(car.get分解号());
  			((_BST)firstST).set模组类型标志(car.get模组类型());
  			((_BST)firstST).set电芯类型标志(car.get电芯类型());
  			((_BST)firstST).set有效型腔数(car.get有效型腔数());
  			((_BST)firstST).set模组层数(car.get模组层数());
  			((_BST)firstST).set立库RDY(true);
  			((_BST)firstST).setWrite(true);
  			
    		      }
    	    }else{
    			 // ((_BST)firstST).setWrite(false);  
    			 // ((_BST)firstST).clear();
    			  
    		  }
    		  
 	      }
    	 
    	 if(stNum==16){ 
  		    //同步输送线
    		//从叠装工位向前判断
    		 if(!firstST.isWrite()&&!secondST.isWrite()){
    			 firstST.set数据处理中(false);secondST.set数据处理中(false);
    			 Vector tem=new Vector();
    			 for(int i=10;i>=checkNum_同步;i--){
    				 Carry car=null; 
    				 if(装配区==1)
    			           car=plc.line.getCarry(i);
    				 else  car=plc.line2.getCarry(i);
    			
    			 if(car==null){continue;}
    			 String ss1= car.get电芯位置1(); String ss2= car.get电芯位置2();
    			 String ss3= car.get电芯位置3(); String ss4= car.get电芯位置4();
    			 if(ss1!=null){
    				 String sm[]=ss1.split("=");
    				 if(sm.length==2){ 
    					 Vector row=new Vector();
    					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
    					 tem.add(row);
    					 System.out.println(tem);
    					 car.set电芯位置1(ss1+"=已上传");
    					 if(tem.size()==2){break;}
    				 }
    			 }
    			 if(ss2!=null){
    				 String sm[]=ss2.split("=");
    				 if(sm.length==2){
    					 Vector row=new Vector();
    					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
    					 tem.add(row);
    					 car.set电芯位置2(ss2+"=已上传");
    					 if(tem.size()==2){break;}
    				 }
    			 }
    			 if(ss3!=null){
    				 String sm[]=ss3.split("=");
    				 if(sm.length==2){
    					 Vector row=new Vector();
    					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
    					 tem.add(row);
    					 car.set电芯位置2(ss3+"=已上传");
    					 if(tem.size()==2){break;}
    				 }
    			 }
    			 
    			 if(ss4!=null){
    				 String sm[]=ss4.split("=");
    				 if(sm.length==2){
    					 Vector row=new Vector();
    					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
    					 tem.add(row);
    					 car.set电芯位置2(ss4+"=已上传");
    					 if(tem.size()==2){break;}
    				 }
    			 }
    			 }
    		if(tem.size()>0){
    			Vector row=(Vector)tem.get(0);
    			Carry car=(Carry)row.get(0);
    			boolean fan=(boolean)row.get(1);
    	    ((_DST)firstST).clear();
			((_DST)firstST).setId(car.get调度ID());
			((_DST)firstST).set工单号(car.get工单号());
			((_DST)firstST).set工单ID(car.get工单ID());
			((_DST)firstST).set模组序ID(car.get模组序ID());
			((_DST)firstST).set分解号(car.get分解号());
			((_DST)firstST).set载具序号(car.载具序号);
			((_DST)firstST).set允许工位动作标志(true);
		    ((_DST)firstST).set电芯类型标志(car.get电芯类型());
			((_DST)firstST).set翻B面(fan);
			
			((_DST)firstST).set立库RDY(true);
			((_DST)firstST).setWrite(true);
			  
    			 }
    		
    		if(tem.size()>1){
    			Vector row=(Vector)tem.get(1);
    			Carry car=(Carry)row.get(0);
    			boolean fan=(boolean)row.get(1);
        	    ((_DST)secondST).clear();
    			((_DST)secondST).setId(car.get调度ID());
    			((_DST)secondST).set工单号(car.get工单号());
    			((_DST)secondST).set工单ID(car.get工单ID());
    			((_DST)secondST).set模组序ID(car.get模组序ID());
    			((_DST)secondST).set分解号(car.get分解号());
    			((_DST)secondST).set载具序号(car.载具序号);
    			((_DST)secondST).set允许工位动作标志(true);
    		    ((_DST)secondST).set电芯类型标志(car.get电芯类型());
    			((_DST)secondST).set翻B面(fan);
    			
    			((_DST)secondST).set立库RDY(true);
    			((_DST)secondST).setWrite(true);
    			  
        			 }
        		
    		
    			
    
        		
    			
    		 }
    		 
    		 if(firstST.isWrite()&&!secondST.isWrite()){
    			 Vector tem=new Vector();
    			 for(int i=10;i>=checkNum_同步;i--){
    				 Carry car=null; 
    				 if(装配区==1)
    			           car=plc.line.getCarry(i);
    				 else  car=plc.line2.getCarry(i);
        			
        			 if(car==null){continue;}
        			 String ss1= car.get电芯位置1(); String ss2= car.get电芯位置2();
        			 String ss3= car.get电芯位置3(); String ss4= car.get电芯位置4();
        			 if(ss1!=null){
        				 String sm[]=ss1.split("=");
        				 if(sm.length==2){//要是等于3的话说明已经上传了
        					 Vector row=new Vector();
        					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
        					 tem.add(row);
        					 car.set电芯位置1(ss1+"=已上传");
        					 if(tem.size()==1){break;}
        				 }
        			 }
        			 if(ss2!=null){
        				 String sm[]=ss2.split("=");
        				 if(sm.length==2){
        					 Vector row=new Vector();
        					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
        					 tem.add(row);
        					 car.set电芯位置2(ss2+"=已上传");
        					 if(tem.size()==1){break;}
        				 }
        			 }
        			 if(ss3!=null){
        				 String sm[]=ss3.split("=");
        				 if(sm.length==2){
        					 Vector row=new Vector();
        					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
        					 tem.add(row);
        					 car.set电芯位置2(ss3+"=已上传");
        					 if(tem.size()==1){break;}
        				 }
        			 }
        			 
        			 if(ss4!=null){
        				 String sm[]=ss4.split("=");
        				 if(sm.length==2){ 
        					 Vector row=new Vector();
        					 row.addElement(car); row.addElement(sm[1].equals("A")?false:true);
        					 tem.add(row);
        					 car.set电芯位置2(ss4+"=已上传");
        					 if(tem.size()==1){break;} 
        				 }
        			 }
    			 }
    			 
        		if(tem.size()==1){
        			Vector row=(Vector)tem.get(0);
        			Carry car=(Carry)row.get(0);
        			boolean fan=(boolean)row.get(1);
        	    ((_DST)secondST).clear();
    			((_DST)secondST).setId(car.get调度ID());
    			((_DST)secondST).set工单号(car.get工单号());
    			((_DST)secondST).set模组序ID(car.get模组序ID());
    			((_DST)secondST).set分解号(car.get分解号());
    			((_DST)secondST).set载具序号(car.载具序号);
    			((_DST)secondST).set允许工位动作标志(true);
    		    ((_DST)secondST).set电芯类型标志(car.get电芯类型());
    			((_DST)secondST).set翻B面(fan);
    			((_DST)secondST).set工单ID(car.get工单ID());
    			
    			((_DST)secondST).set立库RDY(true);
    			((_DST)secondST).setWrite(true);
    			  
        			 }
        	
        		
        		
    			 
    		 }
    		 
    		 if(!firstST.isWrite()&&secondST.isWrite()){
    			 firstST.intFromST(secondST);
        		 secondST.setWrite(false);
        		 secondST.clear();
    			 
    		 }
    		// updata动作();  
  	      }
    	 
    	 
    	 firstST.writeifChangeToPLC();
    	 secondST.writeifChangeToPLC();
    	 
    	
    }
    
    public void updata动作(){//更新动作允许标志 
    	
    	int next=stNum-1;
    	
		//更新第一个工位动作容许标志
		if(firstST.isWrite()){
			
			Carry car=this.装配区==1?plc.line.getCarry(stNum-1):plc.line2.getCarry(stNum-1);
			if(car!=null){
			String id1=firstST.get工单ID()+""+firstST.get模组序ID()+""+firstST.get分解号()+firstST.get载具序号();
			String id2=car.get工单ID()+""+car.get模组序ID()+""+car.get分解号()+car.get载具序号();
			next=stNum-1;
			 if(id1.equals(id2)){
				//System.out.println("first="+id1);
    				 if(firstST instanceof _1_6ST )
    				( (_1_6ST)firstST).set允许工位动作标志(true);
    				 if(firstST instanceof _7ST )
		    		 ( (_7ST)firstST).set允许工位动作标志(true);
    				 if(firstST instanceof _9ST ){
			    	 ( (_9ST)firstST).set允许工位动作标志(true);
			    	 
    				 }
    			    if(firstST instanceof _DST ){
    			    	// ( (_DST)firstST).set允许工位动作标志(true);
    			    	
        				 }
    			   
			    }
			  }else{
				//  if(!firstST.is允许工位动作标志()){
				  Carry car2=this.装配区==1?plc.line.getCarry(stNum-2):plc.line2.getCarry(stNum-2);
				  if(car2!=null){
					  String id1=firstST.get工单ID()+""+firstST.get模组序ID()+""+firstST.get分解号()+firstST.get载具序号();
		    		  String id2=car2.get工单ID()+""+car2.get模组序ID()+""+car2.get分解号()+car2.get载具序号();
		    		  next=stNum-2;
		    			 if(id1.equals(id2)){
		    				// System.out.println("second="+id1);
		    				 if(firstST instanceof _1_6ST )
		    				( (_1_6ST)firstST).set允许工位动作标志(true);
		    				 if(firstST instanceof _7ST )
				    		 ( (_7ST)firstST).set允许工位动作标志(true);
		    				 if(firstST instanceof _9ST ){
					    	 ( (_9ST)firstST).set允许工位动作标志(true);
					    		 
		    				   }
		    				 if(firstST instanceof _DST ){
		    			    	// ( (_DST)firstST).set允许工位动作标志(true);
		    			    	
		        				 }
		    			    }
					  
				  }
				  //}
				  
			  }
			firstST.writeifChangeToPLC();
		}else{
		   // firstST.set允许工位动作标志(false);
			//firstST.writeifChangeToPLC();
			
		}
		
		//更新第二个工位动作容许标志
		if(secondST.isWrite()){
			
			if(next==0) return;
			Carry car=this.装配区==1?plc.line.getCarry(next-1):plc.line2.getCarry(next-1);
			
			if(car!=null){
				 //System.out.println("firstST==="+firstST.getName());
				 //System.out.println("secondST==="+secondST.getName());
				//System.out.println("==="+car.getName());
			String id1=secondST.get工单ID()+""+secondST.get模组序ID()+""+secondST.get分解号()+secondST.get载具序号();
			String id2=car.get工单ID()+""+car.get模组序ID()+""+car.get分解号()+car.get载具序号();
			
			 if(id1.equals(id2)){
				
				 if(secondST instanceof _1_6ST )
				( (_1_6ST)secondST).set允许工位动作标志(true);
				 if(secondST instanceof _7ST )
				( (_7ST)secondST).set允许工位动作标志(true);
				 if(secondST instanceof _9ST ){
				( (_9ST)secondST).set允许工位动作标志(true);
				//System.out.println("____+++++++___");
				 if(firstST instanceof _DST ){
			    // ( (_DST)secondST).set允许工位动作标志(true);
			    	
    				 }
				
				 }
			    }
			  }
			 secondST.writeifChangeToPLC();
		}else{
			
			//secondST.set允许工位动作标志(false);
			//secondST.writeifChangeToPLC();
		}
    }
    

    private synchronized boolean updataSTDB(int 货位, ST_Father st){
    	if(stNum<2||stNum>8){return false;}
    	if(stNum>=2&&stNum<=7){
    		//System.out.println("id="+st.getId());
  	  String tem=SqlTool.findOneRecord("select  物料,数量  from 配方指令队列   where  ID='"+st.getId()+"'");
  	  if(tem!=null){
  		String wuliao=tem.split("!_!")[0];
  	   //System.out.println("id2="+wuliao);
  		int shul=Integer.parseInt(tem.split("!_!")[1]);
  		String tem2=SqlTool.findOneRecord("select  托盘编号,物料,数量  from 库存托盘   where  货位号='"+货位+"'");
  		
  		if(tem2!=null){ 
  			String tp=tem2.split("!_!")[0];
  			String wuliao2=tem2.split("!_!")[1];	
  			int shul2=Integer.parseInt(tem2.split("!_!")[2]);
  			if(shul2==0){return false;}
  			if(wuliao.equals(wuliao2)){
  			 //更新托盘数量	
  			   int 需求数量=((_1_6ST)st).get需求数量();
  			   int 完成数量=((_1_6ST)st).get完成数量();
  			   int fshul=需求数量-完成数量;
  			   
  			   if(shul2>=fshul){
  				   String sql="update 库存托盘 set 数量="+(shul2-fshul)+" where 托盘编号="+"'"+tp+"'";
  				   String sql2="update 配方指令队列  set 完成数量="+(完成数量+fshul)+" where ID="+"'"+st.getId()+"'";
  				   SqlTool.insert(new String[]{sql});
  				   SqlTool.insert(new String[]{sql2});
  				   ((_1_6ST)st).set完成数量(完成数量+fshul);
  				  // ((_1_6ST)st).set立库RDY(false);
  				 /*******
  		  	  	 在这里要重新货物在托盘的位置，从PLC更新
  		  		 * 
  		  		 * *********/	
  				   if(stNum==2){//读取PLC，然后保存到数据库里面
  					   
  				   }
  				   if(stNum==3){}
  				   if(stNum==4){}
  				   if(stNum==5){}
  				   if(stNum==6){}
  				   if(stNum==7){}
  				  //查找类型
  				   String leixing=SqlTool.findOneRecord("select 类型  from 通用物料  where 物料编码="+"'"+wuliao2+"'");
  				   SqlTool.readAddresInPaletFromPLC(leixing, tp, 货位+"", 装配区);
  				   
  				    return true;
  			   }else{
  				 /* String sql="update 库存托盘 set 数量="+0+" where 托盘编号="+"'"+tp+"'";
  				  String sql2="update 配方指令队列  set 完成数量="+(完成数量+shul2)+" where ID="+"'"+st.getId()+"'";
  				  SqlTool.insert(new String[]{sql});
  				  SqlTool.insert(new String[]{sql2});
  				   ((_1_6ST)st).set完成数量(完成数量+shul2);*/
  				   //当需求大于这个托盘的现有量时，不更新数量，托盘应该走
  				    return false;  
  				   
  			   }
  				
  			 }
  		
  			
  		}
  		  
  	  }
  	  }
  	  
    	
    	
    	if(stNum==8){//假电芯的数量一次只能取一个
    		 String tem=SqlTool.findOneRecord("select  物料,数量  from 配方指令队列   where  ID='"+st.getId()+"'");
    	  	  if(tem!=null){
    	  		String wuliao=tem.split("!_!")[0];
    	  		int shul=Integer.parseInt(tem.split("!_!")[1]);
    	  		String tem2=SqlTool.findOneRecord("select  托盘编号,物料,数量  from 库存托盘   where  货位号='"+货位+"'");
    	  		
    	  		if(tem2!=null){
    	  			String tp=tem2.split("!_!")[0];
    	  			String wuliao2=tem2.split("!_!")[1];	
    	  			int shul2=Integer.parseInt(tem2.split("!_!")[2]);
    	  			if(shul2==0){return false;}
    	  			if(wuliao.equals(wuliao2)){
    	  			 //更新托盘数量	
    	  			   int 需求数量=((_7ST)st).get需求数量();
    	  			   int 完成数量=((_7ST)st).get完成数量();
    	  			   int fshul=需求数量-完成数量;
    	  			   if(shul2>=1){
    	  				   String sql="update 库存托盘 set 数量="+(shul2-1)+" where 托盘编号="+"'"+tp+"'";
    	  				   String sql2="update 配方指令队列  set 完成数量="+(完成数量+1)+" where ID="+"'"+st.getId()+"'";
    	  				   SqlTool.insert(new String[]{sql});
    	  				   SqlTool.insert(new String[]{sql2});
    	  				   ((_7ST)st).set完成数量(完成数量+1);
    	  				 /*******
    	    		  	  	 在这里要重新货物在托盘的位置，从PLC更新
    	    		  		 * 
    	    		  		 * *********/	
    	  				  String leixing=SqlTool.findOneRecord("select 类型  from 通用物料  where 物料编码="+"'"+wuliao2+"'");
    	  				  SqlTool.readAddresInPaletFromPLC(leixing, tp, 货位+"", 装配区);
    	  				  
    	  				    return true;
    	  			   }else{
    	  				  /*String sql="update 库存托盘 set 数量="+0+" where 托盘编号="+"'"+tp+"'";
    	  				  String sql2="update 配方指令队列  set 完成数量="+(完成数量+shul2)+" where ID="+"'"+st.getId()+"'";
    	  				  SqlTool.insert(new String[]{sql});
    	  				  SqlTool.insert(new String[]{sql2});
    	  				  ((_7ST)st).set完成数量(完成数量+shul2);*/
    	  				//当需求大于这个托盘的现有量时，不更新数量，托盘应该走
    	  				    return false;  
    	  				   
    	  			   }
    	  				
    	  			 }
    	  		}
    	  		  
    	  	  }		
    		
    	}
    	return false;
    }
    
    public boolean  updataDB(ST_Father st){
    	if(装配区==1){
    		System.out.println(stNum);
    		//更新物料在托盘的数量
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
    	
    	if(装配区==2){
    		//更新物料在托盘的数量
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
