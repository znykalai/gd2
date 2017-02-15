package alai.znyk.plc;

public class _12ST extends ST_Father implements STInterface {
//1到6的工位，要求每个数字的地址必须连续
	private int boolContent;//保存几个布尔值
    private boolean 允许工位动作标志;//D10006.1
	private boolean 立库RDY;//D10006.3
	private boolean 数据更新完成;//D10006.4
	private int 电芯类型标志;//D10007
	private int 模组类型标志;//D10008

	
	protected _12ST(PLC plc, int machineID,String startAddress) {
		super(plc, machineID,startAddress,3);
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
	public boolean is数据更新完成() {
		return 数据更新完成;
	}
	public void set数据更新完成(boolean 数据更新完成) {
		this.数据更新完成 = 数据更新完成;
		if(数据更新完成)
			boolContent=boolContent|0b100;else boolContent=boolContent&0b1111111111111011;
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
	 public void clear(){
	    	boolContent=0;
	         电芯类型标志=0;
		     模组类型标志=0;
		     允许工位动作标志=false;
   		     立库RDY=false;
   		     数据更新完成=false;
		   
	    	
	    }
	 public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_12ST)st).getBoolContent();
	     电芯类型标志=((_12ST)st).get电芯类型标志();
	     模组类型标志=((_12ST)st).get模组类型标志();
	   
	     允许工位动作标志=((_12ST)st).is允许工位动作标志();
	    
	     立库RDY=((_12ST)st).is立库RDY();
	     数据更新完成=((_12ST)st).is数据更新完成();
	 
 }
	 @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _12ST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			 if(this.boolContent!=old.getBoolContent()||!this.getName().equals(old.getName())){return true;} 
			 
		 }
		 return false;}

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
	public String writeToPLC() {
		return plc.writeBlockToBLC(startAddress, length, new int[]{boolContent,电芯类型标志,模组类型标志},machineID);
	}
	@Override
	public String updataFromPLC() {
		int back[]= plc.readBlockFromBLC(startAddress, length, machineID);
   	 if(back!=null){
   		 boolContent=back[0]; 
   		 int tem= boolContent;
   		     允许工位动作标志=((tem&0b01)==1);
   		     立库RDY=((tem&0b10)==1);
   		     数据更新完成=((tem&0b100)==1);
   		     电芯类型标志=back[1];//D10007
   		     模组类型标志=back[2];//D10008
   		    
   	 
   	   return "ST 读取成功";
   	 }
   	 else{
   	  return "ST 读取失败";}
   	
	}
	


}
