package alai.znyk.plc;

import java.io.Serializable;

public class Carry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8716340516267092805L;

	public  String id;//������+�������+�ֽ��
	
	public int ����ID;
    public int ��Ч��ǻ��;//
    public int ��װ����; //
    public int ������;//PACK��
    public int ����ID;//
    public int get����ID() {
		return ����ID;
	}
	public void set����ID(int ����id) {
		����ID = ����id;
	}
	public int �ֽ��;//ģ���
    public int �ؾ����;
    public int pack����;//
    public int ģ�����;//
    public int ��о����;//
    public int ģ������;//
    public int ģ����ID;//
    
    public int �ٵ�о1;
    public int �ٵ�о2;
    public String ��оλ��1=null;
    public String ��оλ��2=null;
    public String ��оλ��3=null;
    public String ��оλ��4=null;
    public boolean ��װ��;
    
   
  
   
	public Carry(int ������,int �ֽ��,int �ؾ����,int ģ����ID){
    	this.������=������;
    	this.�ֽ��=�ֽ��;
    	this.�ؾ����=�ؾ����;
    	this.ģ����ID=ģ����ID;
    } 
	 public boolean is��װ��() {
			return ��װ��;
		}

		public void set��װ��(boolean ��װ��) {
			this.��װ�� = ��װ��;
		}

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int get����ID() {
		return ����ID;
	}
	public void set����ID(int ����id) {
		����ID = ����id;
	}
	public int get��Ч��ǻ��() {
		return ��Ч��ǻ��;
	}
	public void set��Ч��ǻ��(int ��Ч��ǻ��) {
		this.��Ч��ǻ�� = ��Ч��ǻ��;
	}
	public int get��װ����() {
		return ��װ����;
	}
	public void set��װ����(int ��װ����) {
		this.��װ���� = ��װ����;
	}
	public int get������() {
		return ������;
	}
	public void set������(int ������) {
		this.������ = ������;
	}
	public int get�ֽ��() {
		return �ֽ��;
	}
	public void set�ֽ��(int �ֽ��) {
		this.�ֽ�� = �ֽ��;
	}
	public int get�ؾ����() {
		return �ؾ����;
	}
	public void set�ؾ����(int �ؾ����) {
		this.�ؾ���� = �ؾ����;
	}
	public int getPack����() {
		return pack����;
	}
	public void setPack����(int pack����) {
		this.pack���� = pack����;
	}
	public int getģ�����() {
		return ģ�����;
	}
	public void setģ�����(int ģ�����) {
		this.ģ����� = ģ�����;
	}
	public int get��о����() {
		return ��о����;
	}
	public void set��о����(int ��о����) {
		this.��о���� = ��о����;
	}
	public int getģ������() {
		return ģ������;
	}
	public void setģ������(int ģ������) {
		this.ģ������ = ģ������;
	}
	public int getģ����ID() {
		return ģ����ID;
	}
	public void setģ����ID(int ģ����id) {
		ģ����ID = ģ����id;
	}
	public int get�ٵ�о1() {
		return �ٵ�о1;
	}
	public void set�ٵ�о1(int �ٵ�о1) {
		this.�ٵ�о1 = �ٵ�о1;
	}
	public int get�ٵ�о2() {
		return �ٵ�о2;
	}
	public void set�ٵ�о2(int �ٵ�о2) {
		this.�ٵ�о2 = �ٵ�о2;
	}
	public String get��оλ��1() {
		if(��оλ��1!=null){
			return	��оλ��1.equals("")?null:��оλ��1;
		}
		return ��оλ��1;
	}
	public void set��оλ��1(String ��оλ��1) {
		this.��оλ��1 = ��оλ��1;
	}
	public String get��оλ��2() {
		if(��оλ��2!=null){
			return	��оλ��2.equals("")?null:��оλ��2;
		}
		return ��оλ��2;
	}
	public void set��оλ��2(String ��оλ��2) {
		this.��оλ��2 = ��оλ��2;
	}
	public String get��оλ��3() {
		if(��оλ��3!=null){
			return	��оλ��3.equals("")?null:��оλ��3;
		}
		return ��оλ��3;
	}
	public void set��оλ��3(String ��оλ��3) {
		this.��оλ��3 = ��оλ��3;
	}
	public String get��оλ��4() {
		if(��оλ��4!=null){
			return	��оλ��4.equals("")?null:��оλ��4;
		}
		return ��оλ��4;
	}
	public void set��оλ��4(String ��оλ��4) {
		this.��оλ��4 = ��оλ��4;
	}
	 
		
}
