package alai.znyk.plc;

public class _DST extends ST_Father implements STInterface {
	
	private int boolContent;//���漸������ֵ
    private boolean ����λ������־;//D10001.1
	private boolean ��B��;//D10001.2
	private boolean ����RDY;//D10001.3
	private boolean ���ݸ������;//D10001.4
	private boolean ���ݴ�����=false;//D10006.5
	
	private int ��о���ͱ�־;//D10001.4
    private int �䷽����;

	
	
	public int get�䷽����() {
		return �䷽����;
	}
	public void set�䷽����(int �䷽����) {
		this.�䷽���� = �䷽����;
	}

	
	protected  _DST(PLC plc,int machineID,String startAddress){
		super(plc,machineID,startAddress,3);
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
	@Override
	public boolean is���ݸ������() {
		return ���ݸ������;
	}
	@Override
	public void set���ݸ������(boolean ���ݸ������) {
		super.set���ݸ������(���ݸ������);
		this.���ݸ������ = ���ݸ������;
		if(���ݸ������)
		boolContent=boolContent|0b1000;else boolContent=boolContent&0b1111111111110111;
	}
	
	public boolean is���ݴ�����() {
		return ���ݴ�����;
	}
	public void set���ݴ�����(boolean ���ݴ�����) {
		this.���ݴ����� = ���ݴ�����;
		if(���ݴ�����)
		boolContent=boolContent|0b10000;else boolContent=boolContent&0b1111111111101111;
	}

	
    public int get��о���ͱ�־() {
		return ��о���ͱ�־;
	}

	public void set��о���ͱ�־(int ��о���ͱ�־) {
		this.��о���ͱ�־ = ��о���ͱ�־;
	}

	
	public int getLength(){return length;}
    public String getStartAddress(){return startAddress;}
    public synchronized String writeToPLC(){
    	
    	String back=plc.writeBlockToBLC(startAddress, length, new int[]{boolContent,��о���ͱ�־,�䷽����},machineID);
    	 if(back.equals("�ɹ�")){
    		    isChange();//��ʼ��OLD
				old.intFromST(this);
			 }
    	 return back;
    	
    }
    public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_DST)st).getBoolContent();
	     ����λ������־=((_DST)st).is����λ������־();
	     ���ݴ�����=((_DST)st).is���ݴ�����();
		 ����RDY=((_DST)st).is����RDY();
		 ��B��=((_DST)st).is��B��();
		 ���ݸ������=((_DST)st).is���ݸ������();
		 ��о���ͱ�־=((_DST)st).get��о���ͱ�־();
		 �䷽����=((_DST)st).get�䷽����();
	 
}
    @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _DST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			 if(this.boolContent!=old.getBoolContent()||!this.getName().equals(old.getName())
					 || �䷽����!=((_DST)old).get�䷽����()){return true;} 
			 
		 }
		 return false;}

    public void clear(){
    	boolContent=0;
    	����λ������־=false;
	     ��B��=false;
	     ����RDY=false;
		 ���ݸ������=false;
		 ���ݴ�����=false;
    	��о���ͱ�־=0;
    	 write=false; 
    	 �䷽����=0;
    	
    }
   
    public String updataFromPLC(){
    	 int back[]= plc.readBlockFromBLC(startAddress, length, machineID);
    	 if(back!=null){
    		 boolContent=back[0]; 
    		 int tem= boolContent;
    		     ����λ������־=((tem&0b01)==1);
    		     ��B��=((tem&0b10)==2);
    		     ����RDY=((tem&0b100)==4);
    			 ���ݸ������=((tem&0b1000)==8);
    			 ���ݴ�����=((tem&0b10000)==16);
    			 ��о���ͱ�־=back[1];
    			 �䷽����=back[2];
    	 
    	   return "FST ��ȡ�ɹ�";
    	 }
    	 else{
    	  return "FST ��ȡʧ��";}
    	
    }
    
  
    
  
}
