package alai.znyk.plc;

public class _DST extends ST_Father implements STInterface {
	
	private int boolContent;//保存几个布尔值
    private boolean 允许工位动作标志;//D10001.1
	private boolean 翻B面;//D10001.2
	private boolean 立库RDY;//D10001.3
	private boolean 数据更新完成;//D10001.4
	private boolean 数据处理中=false;//D10006.5
	
	private int 电芯类型标志;//D10001.4
    private int 配方特征;

	
	
	public int get配方特征() {
		return 配方特征;
	}
	public void set配方特征(int 配方特征) {
		this.配方特征 = 配方特征;
	}

	
	protected  _DST(PLC plc,int machineID,String startAddress){
		super(plc,machineID,startAddress,3);
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
	public boolean is翻B面() {
		return 翻B面;
	}
	public void set翻B面(boolean 翻b面) {
		翻B面 = 翻b面;
		if(翻b面)
		boolContent=boolContent|0b10;else boolContent=boolContent&0b1111111111111101;
	}
	public boolean is立库RDY() {
		return 立库RDY;
	}
	public void set立库RDY(boolean 立库rdy) {
		立库RDY = 立库rdy;
		if(立库rdy)
		boolContent=boolContent|0b100;else boolContent=boolContent&0b1111111111111011;
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
		boolContent=boolContent|0b1000;else boolContent=boolContent&0b1111111111110111;
	}
	
	public boolean is数据处理中() {
		return 数据处理中;
	}
	public void set数据处理中(boolean 数据处理中) {
		this.数据处理中 = 数据处理中;
		if(数据处理中)
		boolContent=boolContent|0b10000;else boolContent=boolContent&0b1111111111101111;
	}

	
    public int get电芯类型标志() {
		return 电芯类型标志;
	}

	public void set电芯类型标志(int 电芯类型标志) {
		this.电芯类型标志 = 电芯类型标志;
	}

	
	public int getLength(){return length;}
    public String getStartAddress(){return startAddress;}
    public synchronized String writeToPLC(){
    	
    	String back=plc.writeBlockToBLC(startAddress, length, new int[]{boolContent,电芯类型标志,配方特征},machineID);
    	 if(back.equals("成功")){
    		    isChange();//初始话OLD
				old.intFromST(this);
			 }
    	 return back;
    	
    }
    public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_DST)st).getBoolContent();
	     允许工位动作标志=((_DST)st).is允许工位动作标志();
	     数据处理中=((_DST)st).is数据处理中();
		 立库RDY=((_DST)st).is立库RDY();
		 翻B面=((_DST)st).is翻B面();
		 数据更新完成=((_DST)st).is数据更新完成();
		 电芯类型标志=((_DST)st).get电芯类型标志();
		 配方特征=((_DST)st).get配方特征();
	 
}
    @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _DST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			 if(this.boolContent!=old.getBoolContent()||!this.getName().equals(old.getName())
					 || 配方特征!=((_DST)old).get配方特征()){return true;} 
			 
		 }
		 return false;}

    public void clear(){
    	boolContent=0;
    	允许工位动作标志=false;
	     翻B面=false;
	     立库RDY=false;
		 数据更新完成=false;
		 数据处理中=false;
    	电芯类型标志=0;
    	 write=false; 
    	 配方特征=0;
    	
    }
   
    public String updataFromPLC(){
    	 int back[]= plc.readBlockFromBLC(startAddress, length, machineID);
    	 if(back!=null){
    		 boolContent=back[0]; 
    		 int tem= boolContent;
    		     允许工位动作标志=((tem&0b01)==1);
    		     翻B面=((tem&0b10)==2);
    		     立库RDY=((tem&0b100)==4);
    			 数据更新完成=((tem&0b1000)==8);
    			 数据处理中=((tem&0b10000)==16);
    			 电芯类型标志=back[1];
    			 配方特征=back[2];
    	 
    	   return "FST 读取成功";
    	 }
    	 else{
    	  return "FST 读取失败";}
    	
    }
    
  
    
  
}
