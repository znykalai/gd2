package alai.znyk.plc;

import java.io.Serializable;

import alai.GDT.Resint;

public class ReST implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean 载具到位;
	public boolean 动作完成;
	public boolean 载具放行;
	public boolean 人工组装线模式;
	public Resint boolCont;
	public String startAddres="D0";
	public String getStartAddres() {
		return startAddres;
	}
	public void setStartAddres(String startAddres) {
		//this.startAddres = startAddres;
	}
	
	private  int machineID=1;
	public int getmachineID() {
		return machineID;
	}
	public void setmachineID(int machineID) {
		this.machineID = machineID;
	}
	public ReST(Resint boolCont){
		
		this.boolCont=boolCont;
		int con=boolCont.getResInt();
		 载具到位=((con&0b01)==1);
		 动作完成=((con&0b10)==2);
		 载具放行=((con&0b100)==4);
		 人工组装线模式=((con&0b1000)==8);
		
	}
	public boolean is载具到位() {
		return 载具到位;
	}
	public void set载具到位(boolean 载具到位) {
		this.载具到位 = 载具到位;
		if(载具到位)
			boolCont.resInt=boolCont.resInt|0b1;else boolCont.resInt=boolCont.resInt&0b1111111111111110;
	}
	public boolean is动作完成() {
		return 动作完成;
	}
	public void set动作完成(boolean 动作完成) {
		this.动作完成 = 动作完成;
		if(动作完成)
			boolCont.resInt=boolCont.resInt|0b10;else boolCont.resInt=boolCont.resInt&0b1111111111111101;
	}
	public boolean is载具放行() {
		return 载具放行;
	}
	public void set载具放行(boolean 载具放行) {
		this.载具放行 = 载具放行;
		if( 载具放行)
			boolCont.resInt=boolCont.resInt|0b100;else boolCont.resInt=boolCont.resInt&0b1111111111111011;
	}
	public boolean is人工组装线模式() {
		return 人工组装线模式;
	}
	public void set人工组装线模式(boolean 人工组装线模式) {
		this.人工组装线模式 = 人工组装线模式;
		if( 人工组装线模式)
			boolCont.resInt=boolCont.resInt|0b1000;else boolCont.resInt=boolCont.resInt&0b1111111111110111;
	}
	public Resint getBoolCont() {
		return boolCont;
	}
	public void setBoolCont(Resint boolCont) {
		this.boolCont = boolCont;
	}
	public String writeToPLC() {
		return PLC.getIntance().writeBlockToBLC(startAddres, 1, new int[]{boolCont.resInt},machineID);
	}
}
