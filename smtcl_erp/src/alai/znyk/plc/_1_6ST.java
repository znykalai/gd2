package alai.znyk.plc;

public class _1_6ST extends ST_Father implements STInterface {
//1��6�Ĺ�λ��Ҫ��ÿ�����ֵĵ�ַ��������
	private int boolContent;//���漸������ֵ
    private boolean ����λ������־;//D10006.1
	private boolean Ͷ����ǻ��־;//D10006.2
	private boolean ����RDY;//D10006.3
	private boolean ���ݸ������;//D10006.4
	private int ��о���ͱ�־;//D10007
	private int ģ�����ͱ�־;//D10008
	private int ��������;//D100033
	private int �������;
    protected  String ���ϱ���="";
	
	public String get���ϱ���() {
		return ���ϱ���;
	}
	public void set���ϱ���(String ���ϱ���) {
		System.out.println(���ϱ���+"2-------------");
		this.���ϱ��� = ���ϱ���;
	}
	
	public int get�������() {
		return �������;
	}
	public void set�������(int �������) {
		this.������� = �������;
	}
	protected _1_6ST(PLC plc, int machineID,String startAddress) {
		super(plc, machineID,startAddress,5);
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
	public boolean isͶ����ǻ��־() {
		return Ͷ����ǻ��־;
	}
	public void setͶ����ǻ��־(boolean Ͷ����ǻ��־) {
		this.Ͷ����ǻ��־ = Ͷ����ǻ��־;
		if(Ͷ����ǻ��־)
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
	public boolean is���ݸ������() {
		return ���ݸ������;
	}
	public void set���ݸ������(boolean ���ݸ������) {
		this.���ݸ������ = ���ݸ������;
		if(���ݸ������)
			boolContent=boolContent|0b1000;else boolContent=boolContent&0b1111111111110111;
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
	 public void clear(){
	    	 boolContent=0;
	         ��о���ͱ�־=0;//D10007
  		     ģ�����ͱ�־=0;//D10008
  		     ��������=0;//D10009
  		     ����λ������־=false;
 		     Ͷ����ǻ��־=false;
 		     ����RDY=false;
 		     ���ݸ������=false;
 		     �������=0;
 		     ���ϱ���="";
	    	
	    }
	 
	 public void intFromST(ST_Father st){
		     super.intFromST(st);
		     boolContent=((_1_6ST)st).getBoolContent();
		     ��о���ͱ�־=((_1_6ST)st).get��о���ͱ�־();
		     ģ�����ͱ�־=((_1_6ST)st).getģ�����ͱ�־();
		     ��������=((_1_6ST)st).get��������();
		     ����λ������־=((_1_6ST)st).is����λ������־();
		     Ͷ����ǻ��־=((_1_6ST)st).isͶ����ǻ��־();
		     ����RDY=((_1_6ST)st).is����RDY();
		     ���ݸ������=((_1_6ST)st).is���ݸ������();
		     �������=((_1_6ST)st).get�������();
		     ���ϱ���=((_1_6ST)st).get���ϱ���();
	 }
	 
	 @Override
	 public boolean isChange(){
		
		 if(old==null){
			 
			 old=new _1_6ST(plc, machineID,startAddress);
			 return true;
			 
		 }else{
			if(this.boolContent!=old.getBoolContent()||!this.getName().equals(old.getName())){
				
				return true;} 
			 
		 }
		 return false;}
	 
	 @Override 
	 public int getʣ������(){
			return ��������-�������;
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
		return plc.writeBlockToBLC(startAddress, length, new int[]{boolContent,��о���ͱ�־,ģ�����ͱ�־,��������,�������},machineID);
	}
	@Override
	public String updataFromPLC() {
		int back[]= plc.readBlockFromBLC(startAddress, length, machineID);
   	 if(back!=null){
   		 boolContent=back[0]; 
   		 int tem= boolContent;
   		     ����λ������־=((tem&0b01)==1);
   		     Ͷ����ǻ��־=((tem&0b10)==1);
   		     ����RDY=((tem&0b100)==1);
   		     ���ݸ������=((tem&0b1000)==1);
   		     
   		     ��о���ͱ�־=back[1];//D10007
   		     ģ�����ͱ�־=back[2];//D10008
   		     ��������=back[3];//D10009
   		     �������=back[4];
   	 
   	   return "ST ��ȡ�ɹ�";
   	 }
   	 else{
   	  return "ST ��ȡʧ��";}
   	
	}
	
   public void println(){System.out.println(Integer.toBinaryString(boolContent));}

}
