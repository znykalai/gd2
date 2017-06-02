package alai.znyk.plc;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

public class ST_Father implements STInterface, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected PLC plc;
	protected int machineID;
	protected int length=0;//连续数字的长度
	protected String startAddress="D10000";
	protected String startGD="D11001";//光大写入的地址
	protected int id;
	protected boolean write;
	//工单号ID+模组序ID+分解号+载具序号,这三个也决定了唯一的载具
	protected int 工单号;
	protected int 工单ID;
	protected int 模组序ID;
	protected int 分解号;//模组号
	protected int 载具序号;
	protected  String 物料编码="";
	
	public String get物料编码() {
		return 物料编码;
	}
	public void set物料编码(String 物料编码) {
		//System.out.println(物料编码+"-------------");
		this.物料编码 = 物料编码;
	}

	public Hashtable table=new Hashtable();
	protected transient ST_Father old;
	public String getName(){
		//确定一个载具，前3位确定一个模组	
		return get工单ID()+"="+get模组序ID()+"="+get分解号()+"="+get载具序号();
	}
	public int get模组序ID() {
		return 模组序ID;
	}

	public void set模组序ID(int 模组序id) {
		模组序ID = 模组序id;
	}

	public int get工单ID() {
		return 工单ID;
	}

	public void set工单ID(int 工单id) {
		工单ID = 工单id;
	}

	public int getId() {
		return id;
	}
	

	public void setId(int id) {
		this.id = id;
	}

	public int get工单号() {
		return 工单号;
	}

	public void set工单号(int 工单号) {
		this.工单号 = 工单号;
	}

	public int get分解号() {
		return 分解号;
	}

	public void set分解号(int 分解号) {
		this.分解号 = 分解号;
	}

	public int get载具序号() {
		return 载具序号;
	}

	public void set载具序号(int 载具序号) {
		this.载具序号 = 载具序号;
	}
	
	protected ST_Father(PLC plc,int machineID,String stAddress,int len){
		this.plc=plc;
		this.machineID=machineID;
		this.startAddress=stAddress;
		this.length=len;
	}
	
	public boolean isWrite() {
		return write;
	}
	public void setWrite(boolean write) {
		// if(!write)
		//clear();
		this.write = write;
	}
	 public void clear(){
		 write=false; 
		 
	 };
	 public boolean is允许工位动作标志() {
		
			return false;
		}
	 
	 public void intFromST(ST_Father st){
		
		 this.id=st.getId();
		 this.write=st.write;
		 this.set工单号(st.get工单号());
		 this.set分解号(st.get分解号());
		 this.set载具序号(st.get载具序号());
		 this.set模组序ID(st.get模组序ID());
		 this.set工单ID(st.get工单ID());
		
		 
	 }
	 //子类必须覆盖
	 public void set允许工位动作标志(boolean 允许工位动作标志) {
		 
		 
	 }
	 
	 public void set数据更新完成(boolean 数据更新完成){//子类覆盖
		// System.out.println(startAddress+"的数据更新完成="+数据更新完成);
	 }
	 //子类必须覆盖
	 public boolean is数据处理中() {
			return false;
		}
	 //子类必须覆盖
	 public void set数据处理中(boolean 数据处理中) {
		 //System.out.println("父類=set数据更新完成");
		}
	 //子類必須覆蓋
	 public boolean is数据更新完成() {
		 System.out.println("父類=is数据更新完成");
			return false;
		}
	 public boolean isChange(){
		 //子类覆盖
		 return false;}
	 public void set立库RDY(boolean 立库rdy) {
			//子类覆盖
		}
	
	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStartAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String writeToPLC() {
		// TODO Auto-generated method stub
		System.out.println("======");
		return "成功";
	}
	
	public String writeifChangeToPLC(){
		if(this.isChange()){
		String back=writeToPLC();
		if(back!=null){
			if(back.equals("成功")){
				//System.out.println("00000");
				old.intFromST(this);
				return "成功!";
			}else{
				return back;
			}
		}
		
		}
		
		return "成功!";
	}
	public int get剩余数量(){
		return 0;
	}

	@Override
	public String updataFromPLC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBoolContent() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public  Hashtable  getMap() {
		synchronized(this){
		Field f[]=this.getClass().getDeclaredFields();
		 table.clear();
		for(int i=0;i<f.length;i++){
		 String name=f[i].getName();
		 Object ty=f[i].getType();
		
		 try{
			 
		 if(ty.toString().equals("boolean")){
			 String name2=name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
			 Method m2=	this.getClass().getMethod("is"+name2, null) ;
			 table.put(name,  m2.invoke(this, null));
			 }else{
			 String name2=name.substring(0, 1).toUpperCase()+name.substring(1, name.length());		 
				Method m2=	this.getClass().getMethod("get"+name2, null) ; 
			    table.put(name,  m2.invoke(this, null));
				 }
			  
		 }catch(Exception ex){ex.printStackTrace();}   
		 
		}
		table.put("startAddress-", startAddress);
		table.put("工单ID-", 工单ID);
		table.put("模组序ID-", 模组序ID);
		table.put("分解号-", 分解号);
		table.put("载具序号-", 载具序号);
		table.put("ID", id);
		return table;
		
		}
	}
	
	public String setValueByName(String name,String value,String oldValue){
		if(oldValue==null)oldValue="";
		synchronized(this){
		 try{
			 Field f= this.getClass().getDeclaredField(name);
			 String name2=name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
			 
			 if(f.getType().toString().equals("boolean")){
				 
			   Method m=	this.getClass().getMethod("is"+name2, null) ;
			   Object b= m.invoke(this, null)==null?"": m.invoke(this, null);
			   if(!oldValue.equals(b+"")){
				    return "数据不同步，请同步后在更新数据！";  
				   
			   }
			   ////////////////////////////////////////////////
			   Method m2=	this.getClass().getMethod("set"+name2, boolean.class) ; 
			   if(value!=null&&value.equals("false"))
			    m2.invoke(this, false);
			   if(value!=null&&value.equals("true"))
				   m2.invoke(this, true);
			        
			   return "成功";
			 }
			 if(f.getType().toString().equals("int")){
				 
				 Method m=	this.getClass().getMethod("get"+name2, null) ;
				   Object b= m.invoke(this, null)==null?"": m.invoke(this, null);
				   if(!oldValue.equals(b+"")){
					    return "数据不同步，请同步后在更新数据！";  
					   
				   }
				/////////////////////////////////////////////////////////////	
				   Method m2=	this.getClass().getMethod("set"+name2, int.class) ; 
				   if(value!=null)
				   m2.invoke(this, Integer.parseInt(value));
				  
				   return "成功";
				 }
			 
			 if(f.getType().toString().equals("class java.lang.String")){
				 
				 Method m=	this.getClass().getMethod("get"+name2, null) ;
				 Object b= m.invoke(this, null)==null?"": m.invoke(this, null);
				   if(!oldValue.equals(b+"")){
					    return "数据不同步，请同步后在更新数据！";  
					   
				   }
				/////////////////////////////////////////////////////////////	
					
				   Method m2=	this.getClass().getMethod("set"+name2, String.class) ; 
				   if(value!=null)
				   m2.invoke(this, value);
				 
				   return "成功";
				 }
		 
		 }catch(Exception ex){ex.printStackTrace();
		   return ex.getMessage();
		 }
		
		return "失败";
		
		}
	}

}
