package alai.znyk.plc;

public class _9ST extends ST_Father implements STInterface {
//1��6�Ĺ�λ��Ҫ��ÿ�����ֵĵ�ַ��������
	private int boolContent;//���漸������ֵ
    private boolean ����λ������־;//D10006.1
	private boolean ����RDY;//D10006.3
	private boolean ���ݸ������;//D10006.4
	private int ��о���ͱ�־;//D10007
	private int ģ�����ͱ�־;//D10008
	private int ��������;//D10009
	private int PACK���ͱ�־;
	private int PACK��;
	private int ģ���;

	private int ��1����оλ��;
	
	private int ��2����оλ��;
    private int ��3����оλ��;
	private int ��4����оλ��;
	
	
	protected _9ST(PLC plc, int machineID,String startAddress) {
		super(plc, machineID,startAddress,11);
		// TODO Auto-generated constructor stub
	}
	public int getBoolContent(){return boolContent;}
	public boolean is����λ������־() {
		return ����λ������־;
	}
	public void set����λ������־(boolean ����λ������־) {
		this.����λ������־ = ����λ������־;
		if(����λ������־)
			boolContent=boolContent|0b1;else boolContent=boolContent&0b11111111111111110;
	}
	
	public boolean is����RDY() {
		return ����RDY;
	}
	public void set����RDY(boolean ����rdy) {
		����RDY = ����rdy;
		if(����rdy)
			boolContent=boolContent|0b10;else boolContent=boolContent&0b1111111111111101;
	}
	public boolean is���ݸ������() {
		return ���ݸ������;
	}
	public void set���ݸ������(boolean ���ݸ������) {
		this.���ݸ������ = ���ݸ������;
		if(���ݸ������)
			boolContent=boolContent|0b100;else boolContent=boolContent&0b1111111111111011;
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
	public int get��������() {
		return ��������;
	}
	public void set��������(int ��������) {
		this.�������� = ��������;
	}
	
	public int getPACK���ͱ�־() {
		return PACK���ͱ�־;
	}

	public void setPACK���ͱ�־(int pACK���ͱ�־) {
		PACK���ͱ�־ = pACK���ͱ�־;
	}

	public int getPACK��() {
		return PACK��;
	}

	public void setPACK��(int pACK��) {
		PACK�� = pACK��;
	}

	public int getģ���() {
		return ģ���;
	}

	public void setģ���(int ģ���) {
		this.ģ��� = ģ���;
	}

	public int get��3����оλ��() {
		return ��3����оλ��;
	}

	public void set��3����оλ��(int ��3����оλ��) {
		this.��3����оλ�� = ��3����оλ��;
	}

	public int get��4����оλ��() {
		return ��4����оλ��;
	}

	public void set��4����оλ��(int ��4����оλ��) {
		this.��4����оλ�� = ��4����оλ��;
	}

	public int get��1����оλ��() {
		return ��1����оλ��;
	}

	public void set��1����оλ��(int ��1����оλ��) {
		this.��1����оλ�� = ��1����оλ��;
	}

	public int get��2����оλ��() {
		return ��2����оλ��;
	}

	public void set��2����оλ��(int ��2����оλ��) {
		this.��2����оλ�� = ��2����оλ��;
	}
	
	 public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_9ST)st).getBoolContent();
	     ����λ������־=((_9ST)st).is����λ������־();
		 ����RDY=((_9ST)st).is����RDY();
	     ���ݸ������=((_9ST)st).is���ݸ������();
	     
	     ��о���ͱ�־=((_9ST)st).get��о���ͱ�־();
	     ģ�����ͱ�־=((_9ST)st).getģ�����ͱ�־();
	     ��������=((_9ST)st).get��������();
	     PACK���ͱ�־=((_9ST)st).getPACK���ͱ�־();
	     PACK��=((_9ST)st).getPACK��();
  		 ģ���=((_9ST)st).getģ���();
	     ��1����оλ��=((_9ST)st).get��1����оλ��();
		 ��2����оλ��=((_9ST)st).get��2����оλ��();
		 ��3����оλ��=((_9ST)st).get��3����оλ��();
		 ��4����оλ��=((_9ST)st).get��4����оλ��();
	     
   	
	 
 }
	 @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _9ST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			 if(this.boolContent!=old.getBoolContent()||!this.getName().equals(old.getName())){return true;} 
			 
		 }
		 return false;}

	 public void clear(){
	    	boolContent=0;
	    	 ����λ������־=false;
			 ����RDY=false;
		     ���ݸ������=false;
	         ��о���ͱ�־=0;//D10007
  		     ģ�����ͱ�־=0;//D10008
  		     ��������=0;//D10009
  		     PACK���ͱ�־=0;
 		     PACK��=0;
 		     ģ���=0;
 		     ��1����оλ��=0;
 		     ��2����оλ��=0;
 		     ��3����оλ��=0;
		     ��4����оλ��=0;
	    	
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
				new int[]{boolContent,��о���ͱ�־,ģ�����ͱ�־,��������,
						PACK���ͱ�־,PACK��,ģ���,��1����оλ��,��1����оλ��,��3����оλ��,��4����оλ��},
				machineID);
	}
	@Override
	public String updataFromPLC() {
		int back[]= plc.readBlockFromBLC(startAddress, length, machineID);
   	 if(back!=null){
   		 boolContent=back[0]; 
   		 int tem= boolContent;
   		     ����λ������־=((tem&0b01)==1);
   		    
   		     ����RDY=((tem&0b10)==1);
   		     ���ݸ������=((tem&0b100)==1);
   		     
   		     ��о���ͱ�־=back[1];//D10007
   		     ģ�����ͱ�־=back[2];//D10008
   		     ��������=back[3];//D10009
   		     PACK���ͱ�־=back[4];
   		     PACK��=back[5];
   		     ģ���=back[6];
   		     ��1����оλ��=back[7];
   		     ��2����оλ��=back[8];
   		     ��3����оλ��=back[9];
		     ��4����оλ��=back[10];
   	 
   	   return "ST ��ȡ�ɹ�";
   	 }
   	 else{
   	  return "ST ��ȡʧ��";}
   	
	}
	


}
