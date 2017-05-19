package alai.znyk.plc;

public class _12ST extends ST_Father implements STInterface {
//1��6�Ĺ�λ��Ҫ��ÿ�����ֵĵ�ַ��������
	private int boolContent;//���漸������ֵ
    private boolean ����λ������־;//D10006.1
	private boolean ����RDY;//D10006.2
	private boolean ���ݸ������;//D10006.3
	private boolean �иǰ��װ;//D10006.4
	private boolean ���ݴ�����=false;//D10006.5
	
	private int ��о���ͱ�־;//D10007
	private int ģ�����ͱ�־;//D10008
    private int �䷽����;

	
	
	public int get�䷽����() {
		return �䷽����;
	}
	public void set�䷽����(int �䷽����) {
		this.�䷽���� = �䷽����;
	}

	
	protected _12ST(PLC plc, int machineID,String startAddress) {
		super(plc, machineID,startAddress,4);
		// TODO Auto-generated constructor stub
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
	
	public boolean is����RDY() {
		return ����RDY;
	}
	public void set����RDY(boolean ����rdy) {
		this.����RDY = ����rdy;
		if(����rdy)
			boolContent=boolContent|0b10;else boolContent=boolContent&0b1111111111111101;
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
			boolContent=boolContent|0b100;else boolContent=boolContent&0b1111111111111011;
	}
	
	public boolean is�иǰ��װ() {
		return �иǰ��װ;
	}
	public void set�иǰ��װ(boolean �иǰ��װ) {
		this.�иǰ��װ = �иǰ��װ;
		if(�иǰ��װ)
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
	public int getģ�����ͱ�־() {
		return ģ�����ͱ�־;
	}
	public void setģ�����ͱ�־(int ģ�����ͱ�־) {
		this.ģ�����ͱ�־ = ģ�����ͱ�־;
	}
	 public void clear(){
	    	boolContent=0;
	         ��о���ͱ�־=0;
		     ģ�����ͱ�־=0;
		     ����λ������־=false;
   		     ����RDY=false;
   		     ���ݸ������=false;
   		     �иǰ��װ=false;
   		     write=false; 
   		     ���ݴ����� =false;
   		     �䷽����=0;
		   
	    	
	    }
	 public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_12ST)st).getBoolContent();
	     ��о���ͱ�־=((_12ST)st).get��о���ͱ�־();
	     ģ�����ͱ�־=((_12ST)st).getģ�����ͱ�־();
	   
	     ����λ������־=((_12ST)st).is����λ������־();
	     �иǰ��װ=((_12ST)st).is�иǰ��װ();
	     ����RDY=((_12ST)st).is����RDY();
	     ���ݸ������=((_12ST)st).is���ݸ������();
	     ���ݴ�����=((_12ST)st).is���ݴ�����();
	     �䷽����=((_12ST)st).get�䷽����();
	 
 }
	 @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _12ST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			 if(this.boolContent!=old.getBoolContent()||
					 !this.getName().equals(old.getName())||
					 this.get�䷽����()!=((_12ST)old).get�䷽����())
			 {return true;} 
			 
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
		return plc.writeBlockToBLC(startAddress, length, new int[]{boolContent,��о���ͱ�־,ģ�����ͱ�־,�䷽����},machineID);
	}
	@Override
	public String updataFromPLC() {
		int back[]= plc.readBlockFromBLC(startAddress, length, machineID);
   	 if(back!=null){
   		 boolContent=back[0]; 
   		 int tem= boolContent;
   		     ����λ������־=((tem&0b01)==1);
   		     ����RDY=((tem&0b10)==2);
   		     ���ݸ������=((tem&0b100)==4);
   		     �иǰ��װ=((tem&0b1000)==8);
   		     ���ݴ�����=((tem&0b10000)==16);
   		     ��о���ͱ�־=back[1];//D10007
   		     ģ�����ͱ�־=back[2];//D10008
   		     �䷽����=back[3];
   		    
   	 
   	   return "ST ��ȡ�ɹ�";
   	 }
   	 else{
   	  return "ST ��ȡʧ��";}
   	
	}
	


}
