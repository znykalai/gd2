package alai.znyk.plc;

import java.io.Serializable;

public class Carry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8716340516267092805L;

	public  String id;//工单号+托盘序号+分解号
	
	public int 调度ID;
    public int 有效型腔数;//
    public int 已装数量; //
    public int 工单号;//PACK号
    public int 工单ID;//
    public int get工单ID() {
		return 工单ID;
	}
	public void set工单ID(int 工单id) {
		工单ID = 工单id;
	}
	public int 分解号;//模组号
    public int 载具序号;
    public int pack编码;//
    public int 模组编码;//
    public int 电芯类型;//
    public int 模组类型;//
    public int 模组序ID;//
    
    public int 假电芯1;
    public int 假电芯2;
    public String 电芯位置1=null;
    public String 电芯位置2=null;
    public String 电芯位置3=null;
    public String 电芯位置4=null;
    public boolean 叠装否;
    
   
  
   
	public Carry(int 工单号,int 分解号,int 载具序号,int 模组序ID){
    	this.工单号=工单号;
    	this.分解号=分解号;
    	this.载具序号=载具序号;
    	this.模组序ID=模组序ID;
    } 
	 public boolean is叠装否() {
			return 叠装否;
		}

		public void set叠装否(boolean 叠装否) {
			this.叠装否 = 叠装否;
		}

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int get调度ID() {
		return 调度ID;
	}
	public void set调度ID(int 调度id) {
		调度ID = 调度id;
	}
	public int get有效型腔数() {
		return 有效型腔数;
	}
	public void set有效型腔数(int 有效型腔数) {
		this.有效型腔数 = 有效型腔数;
	}
	public int get已装数量() {
		return 已装数量;
	}
	public void set已装数量(int 已装数量) {
		this.已装数量 = 已装数量;
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
	public int getPack编码() {
		return pack编码;
	}
	public void setPack编码(int pack编码) {
		this.pack编码 = pack编码;
	}
	public int get模组编码() {
		return 模组编码;
	}
	public void set模组编码(int 模组编码) {
		this.模组编码 = 模组编码;
	}
	public int get电芯类型() {
		return 电芯类型;
	}
	public void set电芯类型(int 电芯类型) {
		this.电芯类型 = 电芯类型;
	}
	public int get模组类型() {
		return 模组类型;
	}
	public void set模组类型(int 模组类型) {
		this.模组类型 = 模组类型;
	}
	public int get模组序ID() {
		return 模组序ID;
	}
	public void set模组序ID(int 模组序id) {
		模组序ID = 模组序id;
	}
	public int get假电芯1() {
		return 假电芯1;
	}
	public void set假电芯1(int 假电芯1) {
		this.假电芯1 = 假电芯1;
	}
	public int get假电芯2() {
		return 假电芯2;
	}
	public void set假电芯2(int 假电芯2) {
		this.假电芯2 = 假电芯2;
	}
	public String get电芯位置1() {
		if(电芯位置1!=null){
			return	电芯位置1.equals("")?null:电芯位置1;
		}
		return 电芯位置1;
	}
	public void set电芯位置1(String 电芯位置1) {
		this.电芯位置1 = 电芯位置1;
	}
	public String get电芯位置2() {
		if(电芯位置2!=null){
			return	电芯位置2.equals("")?null:电芯位置2;
		}
		return 电芯位置2;
	}
	public void set电芯位置2(String 电芯位置2) {
		this.电芯位置2 = 电芯位置2;
	}
	public String get电芯位置3() {
		if(电芯位置3!=null){
			return	电芯位置3.equals("")?null:电芯位置3;
		}
		return 电芯位置3;
	}
	public void set电芯位置3(String 电芯位置3) {
		this.电芯位置3 = 电芯位置3;
	}
	public String get电芯位置4() {
		if(电芯位置4!=null){
			return	电芯位置4.equals("")?null:电芯位置4;
		}
		return 电芯位置4;
	}
	public void set电芯位置4(String 电芯位置4) {
		this.电芯位置4 = 电芯位置4;
	}
	 
		
}
