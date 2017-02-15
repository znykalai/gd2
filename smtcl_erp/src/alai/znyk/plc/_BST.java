package alai.znyk.plc;

public class _BST extends ST_Father implements STInterface {
//1��6�Ĺ�λ��Ҫ��ÿ�����ֵĵ�ַ��������
	private int boolContent;//���漸������ֵ
    private boolean ����λ������־;//D10006.1
	private boolean ����RDY;//D10006.3
	private boolean ���ݸ������;//D10006.4
	private int ��о���ͱ�־;//D10007
	private int ģ�����ͱ�־;//D10008
	private int ��Ч��ǻ��;//D10009
	private int PACK���ͱ�־;
	private int PACK��;
	private int ģ���;

	
	
	protected _BST(PLC plc, int machineID,String startAddress) {
		super(plc, machineID,startAddress,7);
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
	public int get��Ч��ǻ��() {
		return ��Ч��ǻ��;
	}
	public void set��Ч��ǻ��(int ��Ч��ǻ��) {
		this.��Ч��ǻ�� = ��Ч��ǻ��;
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

	 public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_BST)st).getBoolContent();
	     ����λ������־=((_BST)st).is����λ������־();
		 ����RDY=((_BST)st).is����RDY();
	     ���ݸ������=((_BST)st).is���ݸ������();
	     
	     ��о���ͱ�־=((_BST)st).get��о���ͱ�־();
	     ģ�����ͱ�־=((_BST)st).getģ�����ͱ�־();
	   
	     PACK���ͱ�־=((_BST)st).getPACK���ͱ�־();
	     PACK��=((_BST)st).getPACK��();
  		 ģ���=((_BST)st).getģ���();
	    
	     
   	
	 
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
	    	����λ������־=false;
   		    ����RDY=false;
  		    ���ݸ������=false;
	    	  ��о���ͱ�־=0;
	   		  ģ�����ͱ�־=0;
	   		  ��Ч��ǻ��=0;
	   		  PACK���ͱ�־=0;
	   		  PACK��=0;
	   		  ģ���=0;
	    	
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
				new int[]{boolContent,��о���ͱ�־,ģ�����ͱ�־,��Ч��ǻ��,
						PACK���ͱ�־,PACK��,ģ���},
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
   		     ��Ч��ǻ��=back[3];//D10009
   		     PACK���ͱ�־=back[4];
   		     PACK��=back[5];
   		     ģ���=back[6];
   		    
   	 
   	   return "ST ��ȡ�ɹ�";
   	 }
   	 else{
   	  return "ST ��ȡʧ��";}
   	
	}
	


}
