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
		System.out.println(物料编码+"-------------");
		this.物料编码 = 物料编码;
	}

	public Hashtable table=new Hashtable();
	protected ST_Father old;
	public String getName(){
		
		return 工单ID+""+模组序ID+""+分解号+""+载具序号+""+id;
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
		this.write = write;
	}
	 public void clear(){
		 write=false; 
		 
	 };
	 
	 public void intFromST(ST_Father st){
		
		 this.id=st.getId();
		 this.write=st.write;
		 this.set工单号(st.get工单号());
		 this.set分解号(st.get分解号());
		 this.set载具序号(st.get载具序号());
		 this.set模组序ID(st.get模组序ID());
		 this.set工单ID(st.get工单ID());
		
		 
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
		return "成功";
	}
	
	public String writeifChangeToPLC(){
		if(this.isChange()){
		String back=writeToPLC();
		if(back!=null){
			if(back.equals("成功")){
				System.out.println("00000");
				old.intFromST(this);
			}
		}
		
		}
		
		return null;
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

}
