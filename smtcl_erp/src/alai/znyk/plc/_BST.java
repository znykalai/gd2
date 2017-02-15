package alai.znyk.plc;

public class _BST extends ST_Father implements STInterface {
//1到6的工位，要求每个数字的地址必须连续
	private int boolContent;//保存几个布尔值
    private boolean 允许工位动作标志;//D10006.1
	private boolean 立库RDY;//D10006.3
	private boolean 数据更新完成;//D10006.4
	private int 电芯类型标志;//D10007
	private int 模组类型标志;//D10008
	private int 有效型腔数;//D10009
	private int PACK类型标志;
	private int PACK号;
	private int 模组号;

	
	
	protected _BST(PLC plc, int machineID,String startAddress) {
		super(plc, machineID,startAddress,7);
		// TODO Auto-generated constructor stub
	}
	public int getBoolContent(){return boolContent;}
	public boolean is允许工位动作标志() {
		return 允许工位动作标志;
	}
	public void set允许工位动作标志(boolean 允许工位动作标志) {
		this.允许工位动作标志 = 允许工位动作标志;
		if(允许工位动作标志)
			boolContent=boolContent|0b1;else boolContent=boolContent&0b11111111111111110;
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
	public int get有效型腔数() {
		return 有效型腔数;
	}
	public void set有效型腔数(int 有效型腔数) {
		this.有效型腔数 = 有效型腔数;
	}
	
	public int getPACK类型标志() {
		return PACK类型标志;
	}

	public void setPACK类型标志(int pACK类型标志) {
		PACK类型标志 = pACK类型标志;
	}

	public int getPACK号() {
		return PACK号;
	}

	public void setPACK号(int pACK号) {
		PACK号 = pACK号;
	}

	public int get模组号() {
		return 模组号;
	}

	public void set模组号(int 模组号) {
		this.模组号 = 模组号;
	}

	 public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_BST)st).getBoolContent();
	     允许工位动作标志=((_BST)st).is允许工位动作标志();
		 立库RDY=((_BST)st).is立库RDY();
	     数据更新完成=((_BST)st).is数据更新完成();
	     
	     电芯类型标志=((_BST)st).get电芯类型标志();
	     模组类型标志=((_BST)st).get模组类型标志();
	   
	     PACK类型标志=((_BST)st).getPACK类型标志();
	     PACK号=((_BST)st).getPACK号();
  		 模组号=((_BST)st).get模组号();
	    
	     
   	
	 
 }
	 @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _BST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			 if(this.boolContent!=old.getBoolContent()||!this.getName().equals(old.getName())){return true;} 
			 
		 }
		 return false;}

	 public void clear(){
	    	boolContent=0;
	    	允许工位动作标志=false;
   		    立库RDY=false;
  		    数据更新完成=false;
	    	  电芯类型标志=0;
	   		  模组类型标志=0;
	   		  有效型腔数=0;
	   		  PACK类型标志=0;
	   		  PACK号=0;
	   		  模组号=0;
	    	
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
	public String writeToPLC() {
		return plc.writeBlockToBLC(startAddress, length, 
				new int[]{boolContent,电芯类型标志,模组类型标志,有效型腔数,
						PACK类型标志,PACK号,模组号},
				machineID);
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
   		     有效型腔数=back[3];//D10009
   		     PACK类型标志=back[4];
   		     PACK号=back[5];
   		     模组号=back[6];
   		    
   	 
   	   return "ST 读取成功";
   	 }
   	 else{
   	  return "ST 读取失败";}
   	
	}
	


}
