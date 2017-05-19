package alai.znyk.plc;

public class _FST extends ST_Father implements STInterface {
	
	private int boolContent;//���漸������ֵ
    private boolean ����λ������־;//D10001.1
	private boolean ��B��;//D10001.2
	private boolean ����RDY;//D10001.3
	private boolean ���ݸ������;//D10001.4
	private boolean ���ݴ�����=false;//D10006.5
    private int �䷽����;

	public int get�䷽����() {
		return �䷽����;
	}
	public void set�䷽����(int �䷽����) {
		this.�䷽���� = �䷽����;
	}

	
	protected  _FST(PLC plc,int machineID,String startAddress){
		super(plc,machineID,startAddress,2);
		}
	public int getBoolContent(){return boolContent;}
	public boolean is����λ������־() {
		return ����λ������־;
	}
	public void set����λ������־(boolean ����λ������־) {
		this.����λ������־ = ����λ������־;
		if(����λ������־){
	    boolContent=boolContent|0b1;}else {boolContent=boolContent&0b1111111111111110;}
		//System.out.println("============"+ boolContent);
	   
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
		//System.out.println("boolContent1="+boolContent);
		this.���ݸ������ = ���ݸ������;
		if(���ݸ������)
		boolContent=boolContent|0b1000;else boolContent=boolContent&0b1111111111110111;
		System.out.println("boolContent2="+boolContent);
	}
	
	public boolean is���ݴ�����() {
		return ���ݴ�����;
	}
	public void set���ݴ�����(boolean ���ݴ�����) {
		this.���ݴ����� = ���ݴ�����;
		if(���ݴ�����)
		boolContent=boolContent|0b10000;else boolContent=boolContent&0b1111111111101111;
	}
	
	
    public int getLength(){return length;}
    public String getStartAddress(){return startAddress;}
    public String writeToPLC(){
    	
    	return plc.writeBlockToBLC(startAddress, length, new int[]{boolContent,�䷽����},machineID);
    	
    }
    public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_FST)st).getBoolContent();
	     ����λ������־=((_FST)st).is����λ������־();
	     ���ݴ�����=((_FST)st).is���ݴ�����();
		 ����RDY=((_FST)st).is����RDY();
		 ��B��=((_FST)st).is��B��();
		 ���ݸ������=((_FST)st).is���ݸ������();
		 �䷽����=((_FST)st).get�䷽����();
	 
}
    @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _FST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			 if(this.boolContent!=old.getBoolContent()||!this.getName().equals(old.getName())
					 || �䷽����!=((_FST)old).get�䷽����()){return true;} 
			 
		 }
		 return false;}

    public void clear(){
    	 boolContent=0;
    	 ����λ������־=false;
	     ��B��=false;
	     ����RDY=false;
		 ���ݸ������=false;
		 ���ݴ�����=false;
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
    			 �䷽����=back[1];
    		    
    	 
    	   return "FST ��ȡ�ɹ�";
    	 }
    	 else{
    	  return "FST ��ȡʧ��";}
    	
    }
    
    
    
    public static void main(String s[]){
    	
    	
     int x=0b1111;
     int b= x&0b10;
     System.out.println(Integer.toBinaryString(b));
 	 System.out.println(Integer.toBinaryString(x));
    	
    }
}
