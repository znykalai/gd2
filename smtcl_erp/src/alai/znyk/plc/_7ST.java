package alai.znyk.plc;

public class _7ST extends ST_Father implements STInterface {
//1到6的工位，要求每个数字的地址必须连续
	private int boolContent;//保存几个布尔值
    private boolean 允许工位动作标志;//D10006.1
	private boolean 立库RDY;//D10006.2
	private boolean 数据更新完成;//D10006.3
	private boolean 数据处理中=false;//D10006.4
	
	private int 电芯类型标志;//D10007
	private int 模组类型标志;//D10008
	private int 需求数量;//D10009
	private int 第1个假电芯位置;
	private int 第2个假电芯位置;
    private int 完成数量;
    protected  String 物料编码="";
    private int 配方特征;

	
	
	public int get配方特征() {
		return 配方特征;
	}
	public void set配方特征(int 配方特征) {
		this.配方特征 = 配方特征;
	}
	
	public String get物料编码() {
		return 物料编码;
	}
	public void set物料编码(String 物料编码) {
		this.物料编码 = 物料编码;
	}
	
	public int get完成数量() {
		return 完成数量;
	}
	public void set完成数量(int 完成数量) {
		this.完成数量 = 完成数量;
	}
	
	protected _7ST(PLC plc, int machineID,String startAddress) {
		super(plc, machineID,startAddress,8);
		// TODO Auto-generated constructor stub
	}
	public int getBoolContent(){return boolContent;}
	public boolean is允许工位动作标志() {
		return 允许工位动作标志;
	}
	public void set允许工位动作标志(boolean 允许工位动作标志) {
		this.允许工位动作标志 = 允许工位动作标志;
		if(允许工位动作标志)
			boolContent=boolContent|0b1;else boolContent=boolContent&0b1111111111111110;
	}
	
	public boolean is立库RDY() {
		return 立库RDY;
	}
	public void set立库RDY(boolean 立库rdy) {
		立库RDY = 立库rdy;
		if(立库rdy)
			boolContent=boolContent|0b10;else boolContent=boolContent&0b1111111111111101;
	}
	@Override
	public boolean is数据更新完成() {
		return 数据更新完成;
	}
	@Override
	public void set数据更新完成(boolean 数据更新完成) {
		super.set数据更新完成(数据更新完成);
		this.数据更新完成 = 数据更新完成;
		if(数据更新完成)
			boolContent=boolContent|0b100;else boolContent=boolContent&0b1111111111111011;
	}
	
	public boolean is数据处理中() {
		return 数据处理中;
	}
	public void set数据处理中(boolean 数据处理中) {
		this.数据处理中 = 数据处理中;
		if(数据处理中)
			boolContent=boolContent|0b1000;else boolContent=boolContent&0b1111111111110111;
	}
	
	public int get电芯类型标志() {
		return 电芯类型标志;
	}
	public void set电芯类型标志(int 电芯类型标志) {
		this.电芯类型标志 = 电芯类型标志;
	}
	public int get模组类型标志() {
		return 模组类型标志;
	}
	public void set模组类型标志(int 模组类型标志) {
		this.模组类型标志 = 模组类型标志;
	}
	public int get需求数量() {
		return 需求数量;
	}
	public void set需求数量(int 需求数量) {
		this.需求数量 = 需求数量;
	}
	public int get第1个假电芯位置() {
		return 第1个假电芯位置;
	}

	public void set第1个假电芯位置(int 第1个假电芯位置) {
		this.第1个假电芯位置 = 第1个假电芯位置;
	}

	public int get第2个假电芯位置() {
		return 第2个假电芯位置;
	}

	public void set第2个假电芯位置(int 第2个假电芯位置) {
		this.第2个假电芯位置 = 第2个假电芯位置;
	}
	 public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_7ST)st).getBoolContent();
	     允许工位动作标志=((_7ST)st).is允许工位动作标志();
		 立库RDY=((_7ST)st).is立库RDY();
	     数据更新完成=((_7ST)st).is数据更新完成();
	     
	     电芯类型标志=((_7ST)st).get电芯类型标志();
	     模组类型标志=((_7ST)st).get模组类型标志();
	     需求数量=((_7ST)st).get需求数量();
	     第1个假电芯位置=((_7ST)st).get第1个假电芯位置();
		 第2个假电芯位置=((_7ST)st).get第2个假电芯位置();
	     
		  完成数量=((_7ST)st).get完成数量();
		  物料编码=((_7ST)st).get物料编码();
		  配方特征=((_7ST)st).get配方特征();
		  数据处理中=((_7ST)st).is数据处理中();
	 
 }
	 @Override 
	 public int get剩余数量(){
			return 需求数量-完成数量;
		}
	 @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _7ST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			 if(this.boolContent!=old.getBoolContent()||!this.getName().equals(old.getName())||
					 配方特征!=((_7ST)old).get配方特征()){return true;} 
			 
		 }
		 return false;}


	 public void clear(){
	    	boolContent=0;
	    	允许工位动作标志=false;
			 立库RDY=false;
		     数据更新完成=false;
		     数据处理中=false;
	         电芯类型标志=0;//D10007
  		     模组类型标志=0;//D10008
  		     需求数量=0;//D10009
  		     第1个假电芯位置=0;
 		     第2个假电芯位置=0;
 		    完成数量=0;
 		    物料编码="";
 		   write=false; 
 		  配方特征=0;
 		     
	    	
	    }
	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return length;
	}
	@Override
	public String getStartAddress() {
		// TODO Auto-generated method stub
		return startAddress;
	}
	@Override
	public synchronized String writeToPLC() {
		return plc.writeBlockToBLC(startAddress, length, new int[]{boolContent,电芯类型标志,模组类型标志,需求数量,第1个假电芯位置,第1个假电芯位置,完成数量,配方特征},machineID);
	}
	@Override
	public String updataFromPLC() {
		int back[]= plc.readBlockFromBLC(startAddress, length, machineID);
   	 if(back!=null){
   		 boolContent=back[0]; 
   		 int tem= boolContent;
   		     允许工位动作标志=((tem&0b01)==1);
   		     立库RDY=((tem&0b10)==2);
   		     数据更新完成=((tem&0b100)==4);
   		     数据处理中=((tem&0b1000)==8);
   		     电芯类型标志=back[1];//D10007
   		     模组类型标志=back[2];//D10008
   		     需求数量=back[3];//D10009
   		     第1个假电芯位置=back[4];
   		     第2个假电芯位置=back[5];
   		     完成数量=back[6];
   		     配方特征=back[7];
   	 
   	   return "ST 读取成功";
   	 }
   	 else{
   	  return "ST 读取失败";}
   	
	}
	


}
