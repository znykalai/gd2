package alai.znyk.plc;

import java.io.Serializable;
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
		System.out.println(���ϱ���+"-------------");
		this.���ϱ��� = ���ϱ���;
	}

	public Hashtable table=new Hashtable();
	protected ST_Father old;
	public String getName(){
		
		return ����ID+""+ģ����ID+""+�ֽ��+""+�ؾ����+""+id;
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
		this.write = write;
	}
	 public void clear(){
		 write=false; 
		 
	 };
	 
	 public void intFromST(ST_Father st){
		
		 this.id=st.getId();
		 this.write=st.write;
		 this.set������(st.get������());
		 this.set�ֽ��(st.get�ֽ��());
		 this.set�ؾ����(st.get�ؾ����());
		 this.setģ����ID(st.getģ����ID());
		 this.set����ID(st.get����ID());
		
		 
	 }
	 
	
	 public boolean isChange(){return false;}

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
		return "�ɹ�";
	}
	
	public String writeifChangeToPLC(){
		if(this.isChange()){
		String back=writeToPLC();
		if(back!=null){
			if(back.equals("�ɹ�")){
				System.out.println("00000");
				old.intFromST(this);
			}
		}
		
		}
		
		return null;
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

}
