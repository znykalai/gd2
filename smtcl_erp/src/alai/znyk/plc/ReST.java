package alai.znyk.plc;

import java.io.Serializable;

import alai.GDT.Resint;

public class ReST implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean �ؾߵ�λ;
	public boolean �������;
	public boolean �ؾ߷���;
	public boolean �˹���װ��ģʽ;
	public Resint boolCont;
	public ReST(Resint boolCont){
		this.boolCont=boolCont;
		int con=boolCont.getResInt();
		 �ؾߵ�λ=((con&0b01)==1);
		 �������=((con&0b10)==1);
		 �ؾ߷���=((con&0b100)==1);
		 �˹���װ��ģʽ=((con&0b1000)==1);
	}
	public boolean is�ؾߵ�λ() {
		return �ؾߵ�λ;
	}
	public void set�ؾߵ�λ(boolean �ؾߵ�λ) {
		this.�ؾߵ�λ = �ؾߵ�λ;
		if(�ؾߵ�λ)
			boolCont.resInt=boolCont.resInt|0b1;else boolCont.resInt=boolCont.resInt&0b1111111111111110;
	}
	public boolean is�������() {
		return �������;
	}
	public void set�������(boolean �������) {
		this.������� = �������;
		if(�������)
			boolCont.resInt=boolCont.resInt|0b10;else boolCont.resInt=boolCont.resInt&0b1111111111111101;
	}
	public boolean is�ؾ߷���() {
		return �ؾ߷���;
	}
	public void set�ؾ߷���(boolean �ؾ߷���) {
		this.�ؾ߷��� = �ؾ߷���;
		if( �ؾ߷���)
			boolCont.resInt=boolCont.resInt|0b100;else boolCont.resInt=boolCont.resInt&0b1111111111111011;
	}
	public boolean is�˹���װ��ģʽ() {
		return �˹���װ��ģʽ;
	}
	public void set�˹���װ��ģʽ(boolean �˹���װ��ģʽ) {
		this.�˹���װ��ģʽ = �˹���װ��ģʽ;
	}
	public Resint getBoolCont() {
		return boolCont;
	}
	public void setBoolCont(Resint boolCont) {
		this.boolCont = boolCont;
	}

}
