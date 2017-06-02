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
		   //	SqlPro.getLog().error("载具移动失败在->本工位为空"+fromSt+"ST");	

			return false;}
		if(fromSt>cont.length-1){
			SqlPro.getLog().error("载具移动失败在->超出边界"+fromSt+"ST");	
			return false;}
		
		 if(cont[fromSt+1]==null){
			cont[fromSt+1]=cont[fromSt];
			cont[fromSt]=null;
			return true;
		}
		 SqlPro.getLog().error("载具移动失败在->前面有载具"+fromSt+"ST");	
		return false;
	}
	public void removeLast(){
		cont[cont.length-1]=null;
		
	}
	
	public void setCarryAt(Carry ca,int index){
		cont[index]=ca;
	}
	
	public String setCarryAt(int index,String 工单ID,String 模组ID,String 分解号,String 载具号){
		//清空托盘
		if(index==-2){
			//从载具上清空托盘,如果这个托盘在0号工位，还要做相应处理
			for(int i=-1;i<15;i++){
				Carry car=this.getCarry(i);
				if(car!=null){
					if(car.getName().equals(工单ID+"="+模组ID+"="+分解号+"="+载具号)){
						if(i==-1){
							this.buffer=null;
							System.out.println(i+":"+machineID);
							if(machineID==1){
								PLC.getIntance().ST0_1.secondST.write=false;
								// String sql="update 配方指令队列  set 前升读标志=NULL where 工单ID="+"'"+工单ID+"' and 分解号="+分解号+" and 模组序ID="+模组ID+" and 载具序号="+载具号+" and 装配区="+machineID;
						    	 // System.out.println(i+":"+sql);
								// SqlTool.insert(new String[]{sql});						
							   }else{
								PLC.getIntance().ST0_2.secondST.write=false; 
								// String sql="update 配方指令队列  set 前升读标志=NULL where 工单ID="+"'"+工单ID+"' and 分解号="+分解号+" and 模组序ID="+模组ID+" and 载具序号="+载具号+" and 装配区="+2;
						    	// SqlTool.insert(new String[]{sql});	
							   }
						    }
						else{
							cont[i]=null;
							if(i==0){
								if(machineID==1){
									PLC.getIntance().ST0_1.firstST.write=false;
									// String sql="update 配方指令队列  set 前升读标志=NULL where 工单ID="+"'"+工单ID+"' and 分解号="+分解号+" and 模组序ID="+模组ID+" and 载具序号="+载具号+" and 装配区="+machineID;
									// System.out.println(i+":"+SqlTool.insert(new String[]{sql}));
									 
								   }else{
									PLC.getIntance().ST0_2.firstST.write=false;  
									 //String sql="update 配方指令队列  set 前升读标志=NULL where 工单ID="+"'"+工单ID+"' and 分解号="+分解号+" and 模组序ID="+模组ID+" and 载具序号="+载具号+" and 装配区="+2;
							    	 //SqlTool.insert(new String[]{sql});	
								   }
								
							}
						}
						
					}
					
				   }
				
			}
			
			return "成功";
		}
		
		else{
			//首先清除输送线上已有的这个载具
			for(int i=-1;i<15;i++){
				Carry car=this.getCarry(i);
				if(car!=null){
					if(car.getName().equals(工单ID+"="+模组ID+"="+分解号+"="+载具号)){
						if(i==-1){
						  this.buffer=null;
							
						}
						else{
							cont[i]=null;
						}
						
					}
					
				   } }
				
			
			//然后把这个工位设成本载具
	 Vector<Vector> tem=SqlTool.findInVector("select  ID,工单序号,分解号,载具序号,pack编码,模组编码,物料,数量,翻面否,工位,工单ID,模组序ID,IFNULL(假电芯1,0),IFNULL(假电芯2,0),电芯位置1,电芯位置2,电芯位置3,电芯位置4,IFNULL(叠装否,'否'),模组类型,电芯类型  ,pack类型,COUNT(DISTINCT 工单序号,模组序ID,分解号,载具序号 )"
	 		+ "  from 配方指令队列   where  工单ID='"+工单ID+"' and 模组序ID='"+模组ID+
	 		"'"+" and 分解号='"+分解号+"' and 载具序号 ='"+载具号+"' GROUP BY 工单序号,分解号,载具序号   ORDER BY 工单序号,模组序号,分解号,载具序号 LIMIT 10");		
	  
	 if(tem.size()>0){    
	       Vector row=(Vector)tem.get(0);
	       Carry carr=new Carry(row.get(1)==null?0:(int)row.get(1), Integer.parseInt(分解号),Integer.parseInt(载具号),Integer.parseInt(模组ID));
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
	     	 Vector<Vector> tost=SqlTool.findInVector("select 工位,物料 from 配方指令队列   where 工单ID='"+
	     	 carr.get工单ID()+"' and 分解号='"+carr.get分解号()+"' and 载具序号='"+carr.get载具序号()+"' order by 工位");
	     	 for(int m=0;m<tost.size();m++){
	     		 Vector vv=(Vector)tost.get(m);
	     	     carr.set工位(carr.get工位()+""+vv.get(0));
	     	 
	         }
	     	if(index==-1) {
	     		this.buffer=carr;
	     	}else{
	     		 cont[index]=carr;
	     	}
	     	return "成功"; 
	     }
	
			
		}//end else
		
		 return "未加入";
		
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
		     System.out.println(carr2.get载具序号());
			
		   }catch(Exception ex){ex.printStackTrace();}
		
	}
}
