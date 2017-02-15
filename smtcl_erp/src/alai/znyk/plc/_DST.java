package alai.znyk.plc;

public class _DST extends ST_Father implements STInterface {
	
	private int boolContent;//���漸������ֵ
    private boolean ����λ������־;//D10001.1
	private boolean ��B��;//D10001.2
	private boolean ����RDY;//D10001.3
	private boolean ���ݸ�����;//D10001.4
	private int ��о���ͱ�־;//D10001.4
	

	
	protected  _DST(PLC plc,int machineID,String startAddress){
		super(plc,machineID,startAddress,2);
		}
	public int getBoolContent(){return boolContent;}
	public boolean is����λ������־() {
		return ����λ������־;
	}
	public void set����λ������־(boolean ����λ������־) {
		this.����λ������־ = ����λ������־;
		if(����λ������־)
	boolContent=boolContent|0b1;else boolContent=boolContent&0b1111111111111110;
	}
	public boolean is��B��() {
		return ��B��;
	}
	public void set��B��(boolean ��b��) {
		��B�� = ��b��;
		if(��b��)
		boolContent=boolContent|0b10;else boolContent=boolContent&0b1111111111111101;
	}
	public boolean is����RDY() {
		return ����RDY;
	}
	public void set����RDY(boolean ����rdy) {
		����RDY = ����rdy;
		if(����rdy)
		boolContent=boolContent|0b100;else boolContent=boolContent&0b1111111111111011;
	}
	public boolean is���ݸ�����() {
		return ���ݸ�����;
	}
	
	public void set���ݸ�����(boolean ���ݸ�����) {
		this.���ݸ����� = ���ݸ�����;
		if(���ݸ�����)
		boolContent=boolContent|0b1000;else boolContent=boolContent&0b1111111111110111;
	}
	
	
	
    public int get��о���ͱ�־() {
		return ��о���ͱ�־;
	}

	public void set��о���ͱ�־(int ��о���ͱ�־) {
		this.��о���ͱ�־ = ��о���ͱ�־;
	}

	
	public int getLength(){return length;}
    public String getStartAddress(){return startAddress;}
    public String writeToPLC(){
    	
    	return plc.writeBlockToBLC(startAddress, length, new int[]{boolContent,��о���ͱ�־},machineID);
    	
    }
    public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_DST)st).getBoolContent();
	     ����λ������־=((_DST)st).is����λ������־();
		 ����RDY=((_DST)st).is����RDY();
		 ��B��=((_DST)st).is��B��();
		 ���ݸ�����=((_DST)st).is���ݸ�����();
		 ��о���ͱ�־=((_DST)st).get��о���ͱ�־();
	 
}
    @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _DST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			 if(this.boolContent!=old.getBoolContent()||!this.getName().equals(old.getName())){return true;} 
			 
		 }
		 return false;}

    public void clear(){
    	boolContent=0;
    	����λ������־=false;
	     ��B��=false;
	     ����RDY=false;
		 ���ݸ�����=false;
    	��о���ͱ�־=0;
    	
    }
   
    public String updataFromPLC(){
    	 int back[]= plc.readBlockFromBLC(startAddress, length, machineID);
    	 if(back!=null){
    		 boolContent=back[0]; 
    		 int tem= boolContent;
    		     ����λ������־=((tem&0b01)==1);
    		     ��B��=((tem&0b10)==1);
    		     ����RDY=((tem&0b100)==1);
    			 ���ݸ�����=((tem&0b1000)==1);
    			 ��о���ͱ�־=back[1];
    	 
    	   return "FST ��ȡ�ɹ�";
    	 }
    	 else{
    	  return "FST ��ȡʧ��";}
    	
    }
    
  
    
  
}
