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
	protected int length=0;//�������ֵĳ���
	protected String startAddress="D10000";
	protected String startGD="D11001";//���д��ĵ�ַ
	protected int id;
	protected boolean write;
	//������ID+ģ����ID+�ֽ��+�ؾ����,������Ҳ������Ψһ���ؾ�
	protected int ������;
	protected int ����ID;
	protected int ģ����ID;
	protected int �ֽ��;//ģ���
	protected int �ؾ����;
	protected  String ���ϱ���="";
	
	public String get���ϱ���() {
		return ���ϱ���;
	}
	public void set���ϱ���(String ���ϱ���) {
		//System.out.println(���ϱ���+"-------------");
		this.���ϱ��� = ���ϱ���;
	}

	public Hashtable table=new Hashtable();
	protected transient ST_Father old;
	public String getName(){
		//ȷ��һ���ؾߣ�ǰ3λȷ��һ��ģ��	
		return get����ID()+"="+getģ����ID()+"="+get�ֽ��()+"="+get�ؾ����();
	}
	public int getģ����ID() {
		return ģ����ID;
	}

	public void setģ����ID(int ģ����id) {
		ģ����ID = ģ����id;
	}

	public int get����ID() {
		return ����ID;
	}

	public void set����ID(int ����id) {
		����ID = ����id;
	}

	public int getId() {
		return id;
	}
	

	public void setId(int id) {
		this.id = id;
	}

	public int get������() {
		return ������;
	}

	public void set������(int ������) {
		this.������ = ������;
	}

	public int get�ֽ��() {
		return �ֽ��;
	}

	public void set�ֽ��(int �ֽ��) {
		this.�ֽ�� = �ֽ��;
	}

	public int get�ؾ����() {
		return �ؾ����;
	}

	public void set�ؾ����(int �ؾ����) {
		this.�ؾ���� = �ؾ����;
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
	 public boolean is����λ������־() {
		
			return false;
		}
	 
	 public void intFromST(ST_Father st){
		
		 this.id=st.getId();
		 this.write=st.write;
		 this.set������(st.get������());
		 this.set�ֽ��(st.get�ֽ��());
		 this.set�ؾ����(st.get�ؾ����());
		 this.setģ����ID(st.getģ����ID());
		 this.set����ID(st.get����ID());
		
		 
	 }
	 //������븲��
	 public void set����λ������־(boolean ����λ������־) {
		 
		 
	 }
	 
	 public void set���ݸ������(boolean ���ݸ������){//���า��
		// System.out.println(startAddress+"�����ݸ������="+���ݸ������);
	 }
	 //������븲��
	 public boolean is���ݴ�����() {
			return false;
		}
	 //������븲��
	 public void set���ݴ�����(boolean ���ݴ�����) {
		 //System.out.println("���=set���ݸ������");
		}
	 //���횸��w
	 public boolean is���ݸ������() {
		 System.out.println("���=is���ݸ������");
			return false;
		}
	 public boolean isChange(){
		 //���า��
		 return false;}
	 public void set����RDY(boolean ����rdy) {
			//���า��
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
		return "�ɹ�";
	}
	
	public String writeifChangeToPLC(){
		if(this.isChange()){
		String back=writeToPLC();
		if(back!=null){
			if(back.equals("�ɹ�")){
				//System.out.println("00000");
				old.intFromST(this);
				return "�ɹ�!";
			}else{
				return back;
			}
		}
		
		}
		
		return "�ɹ�!";
	}
	public int getʣ������(){
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
		table.put("����ID-", ����ID);
		table.put("ģ����ID-", ģ����ID);
		table.put("�ֽ��-", �ֽ��);
		table.put("�ؾ����-", �ؾ����);
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
				    return "���ݲ�ͬ������ͬ�����ڸ������ݣ�";  
				   
			   }
			   ////////////////////////////////////////////////
			   Method m2=	this.getClass().getMethod("set"+name2, boolean.class) ; 
			   if(value!=null&&value.equals("false"))
			    m2.invoke(this, false);
			   if(value!=null&&value.equals("true"))
				   m2.invoke(this, true);
			        
			   return "�ɹ�";
			 }
			 if(f.getType().toString().equals("int")){
				 
				 Method m=	this.getClass().getMethod("get"+name2, null) ;
				   Object b= m.invoke(this, null)==null?"": m.invoke(this, null);
				   if(!oldValue.equals(b+"")){
					    return "���ݲ�ͬ������ͬ�����ڸ������ݣ�";  
					   
				   }
				/////////////////////////////////////////////////////////////	
				   Method m2=	this.getClass().getMethod("set"+name2, int.class) ; 
				   if(value!=null)
				   m2.invoke(this, Integer.parseInt(value));
				  
				   return "�ɹ�";
				 }
			 
			 if(f.getType().toString().equals("class java.lang.String")){
				 
				 Method m=	this.getClass().getMethod("get"+name2, null) ;
				 Object b= m.invoke(this, null)==null?"": m.invoke(this, null);
				   if(!oldValue.equals(b+"")){
					    return "���ݲ�ͬ������ͬ�����ڸ������ݣ�";  
					   
				   }
				/////////////////////////////////////////////////////////////	
					
				   Method m2=	this.getClass().getMethod("set"+name2, String.class) ; 
				   if(value!=null)
				   m2.invoke(this, value);
				 
				   return "�ɹ�";
				 }
		 
		 }catch(Exception ex){ex.printStackTrace();
		   return ex.getMessage();
		 }
		
		return "ʧ��";
		
		}
	}

}
