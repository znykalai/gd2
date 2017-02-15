package alai.znyk.plc;

public class _7ST extends ST_Father implements STInterface {
//1��6�Ĺ�λ��Ҫ��ÿ�����ֵĵ�ַ��������
	private int boolContent;//���漸������ֵ
    private boolean ����λ������־;//D10006.1
	private boolean ����RDY;//D10006.3
	private boolean ���ݸ������;//D10006.4
	private int ��о���ͱ�־;//D10007
	private int ģ�����ͱ�־;//D10008
	private int ��������;//D10009
	private int ��1���ٵ�оλ��;
	private int ��2���ٵ�оλ��;
    private int �������;
    protected  String ���ϱ���="";
	
	public String get���ϱ���() {
		return ���ϱ���;
	}
	public void set���ϱ���(String ���ϱ���) {
		this.���ϱ��� = ���ϱ���;
	}
	
	public int get�������() {
		return �������;
	}
	public void set�������(int �������) {
		this.������� = �������;
	}
	
	protected _7ST(PLC plc, int machineID,String startAddress) {
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
			boolContent=boolContent|0b1;else boolContent=boolContent&0b1111111111111110;
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
	public int get��1���ٵ�оλ��() {
		return ��1���ٵ�оλ��;
	}

	public void set��1���ٵ�оλ��(int ��1���ٵ�оλ��) {
		this.��1���ٵ�оλ�� = ��1���ٵ�оλ��;
	}

	public int get��2���ٵ�оλ��() {
		return ��2���ٵ�оλ��;
	}

	public void set��2���ٵ�оλ��(int ��2���ٵ�оλ��) {
		this.��2���ٵ�оλ�� = ��2���ٵ�оλ��;
	}
	 public void intFromST(ST_Father st){
	     super.intFromST(st);
	     boolContent=((_7ST)st).getBoolContent();
	     ����λ������־=((_7ST)st).is����λ������־();
		 ����RDY=((_7ST)st).is����RDY();
	     ���ݸ������=((_7ST)st).is���ݸ������();
	     
	     ��о���ͱ�־=((_7ST)st).get��о���ͱ�־();
	     ģ�����ͱ�־=((_7ST)st).getģ�����ͱ�־();
	     ��������=((_7ST)st).get��������();
	     ��1���ٵ�оλ��=((_7ST)st).get��1���ٵ�оλ��();
		 ��2���ٵ�оλ��=((_7ST)st).get��2���ٵ�оλ��();
	     
		  �������=((_7ST)st).get�������();
		  ���ϱ���=((_7ST)st).get���ϱ���();
	 
 }
	 @Override 
	 public int getʣ������(){
			return ��������-�������;
		}
	 @Override
	 public boolean isChange(){
		 if(old==null){
			 old=new _7ST(plc, machineID,startAddress);
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
  		     ��1���ٵ�оλ��=0;
 		     ��2���ٵ�оλ��=0;
 		    �������=0;
 		    ���ϱ���="";
 		     
	    	
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
		return plc.writeBlockToBLC(startAddress, length, new int[]{boolContent,��о���ͱ�־,ģ�����ͱ�־,��������,��1���ٵ�оλ��,��1���ٵ�оλ��,�������},machineID);
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
   		     ��1���ٵ�оλ��=back[4];
   		     ��2���ٵ�оλ��=back[5];
   		     �������=back[6];
   	 
   	   return "ST ��ȡ�ɹ�";
   	 }
   	 else{
   	  return "ST ��ȡʧ��";}
   	
	}
	


}
