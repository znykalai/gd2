package alai.znyk.plc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import alai.GDT.Inint;
import alai.GDT.Resint;
import alai.localhost.GD_wsdl.GDLocator;
import alai.znyk.common.ClientSer;
import alai.znyk.common.SqlPro;
import alai.znyk.server.SqlTool;

public class PLC implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean �����ȡ������=true;
	public boolean is�����ȡ������() {
		return �����ȡ������;
	}
	public void set�����ȡ������(boolean �����ȡ������) {
		this.�����ȡ������ = �����ȡ������;
	}
	public boolean is����⶯�����() {
		return ����⶯�����;
	}
	public void set����⶯�����(boolean ����⶯�����) {
		this.����⶯����� = ����⶯�����;
	}

	public boolean ����⶯�����=false;
	public boolean stop1=false;//ֹͣ����ϵͳ�����ݿ������ȡ����
	public boolean stop2=true;//ֹͣ����ϵͳ�����ݿ������ȡ����
	public void setDiaodu1(boolean state){
		stop1=state;
	}
	public void setDiaodu2(boolean state){
		stop2=state;
	}
	public boolean isStartDiaodu1(){return stop1;}
	public boolean isStartDiaodu2(){return stop2;}
	
	public Vector<STContent> STC1=new Vector<STContent>();
	public Vector<STContent> STC2=new Vector<STContent>();
	public CarryLine line=new CarryLine(this);
	public CarryLine line2=new CarryLine(this);
	String STATE1="502=0|504=0|506=0|508=0|510=0|512=0|514=0";
	

	String STATE2="602=0|604=0|606=0|608=0|610=0|612=0|614=0";
	
	private _FST _FST_1_1=new _FST(this, 1, "D10001");
	private _FST _FST_2_1=new _FST(this, 1, "D10138");
	private _FST _FST_1_2=new _FST(this, 2, "D10001");
	private _FST _FST_2_2=new _FST(this, 2, "D10138");
	public STContent ST0_1=new STContent(this,_FST_1_1, _FST_2_1, 1, 1);
	public STContent ST0_2=new STContent(this,_FST_1_2, _FST_2_2, 1, 2);
	
	private _1_6ST _1ST_1_1=new _1_6ST(this, 1, "D10006");
	private _1_6ST _1ST_2_1=new _1_6ST(this, 1, "D10043");
	private _1_6ST _1ST_1_2=new _1_6ST(this, 2, "D10006");
	private _1_6ST _1ST_2_2=new _1_6ST(this, 2, "D10043");
	public STContent ST1_1=new STContent(this,_1ST_1_1, _1ST_2_1, 2, 1);
	public STContent ST1_2=new STContent(this,_1ST_1_2, _1ST_2_2, 2, 2);
	
	private _1_6ST _2ST_1_1=new _1_6ST(this, 1, "D10014");
	private _1_6ST _2ST_2_1=new _1_6ST(this, 1, "D10151");
	private _1_6ST _2ST_1_2=new _1_6ST(this, 2, "D10014");
	private _1_6ST _2ST_2_2=new _1_6ST(this, 2, "D10151");
	public STContent ST2_1=new STContent(this,_2ST_1_1, _2ST_2_1, 3, 1);
	public STContent ST2_2=new STContent(this,_2ST_1_2, _2ST_2_2, 3, 2);
	
	private _1_6ST _3ST_1_1=new _1_6ST(this, 1, "D10022");
	private _1_6ST _3ST_2_1=new _1_6ST(this, 1, "D10159");
	private _1_6ST _3ST_1_2=new _1_6ST(this, 2, "D10022");
	private _1_6ST _3ST_2_2=new _1_6ST(this, 2, "D10159");
	public STContent ST3_1=new STContent(this,_3ST_1_1, _3ST_2_1, 4, 1);
	public STContent ST3_2=new STContent(this,_3ST_1_2, _3ST_2_2, 4, 2);
	
	private _1_6ST _4ST_1_1=new _1_6ST(this, 1, "D10030");
	private _1_6ST _4ST_2_1=new _1_6ST(this, 1, "D10167");
	private _1_6ST _4ST_1_2=new _1_6ST(this, 2, "D10030");
	private _1_6ST _4ST_2_2=new _1_6ST(this, 2, "D10167");
	public STContent ST4_1=new STContent(this,_4ST_1_1, _4ST_2_1, 5, 1);
	public STContent ST4_2=new STContent(this,_4ST_1_2, _4ST_2_2, 5, 2);
	
	private _1_6ST _5ST_1_1=new _1_6ST(this, 1, "D10038");
	private _1_6ST _5ST_2_1=new _1_6ST(this, 1, "D10175");
	private _1_6ST _5ST_1_2=new _1_6ST(this, 2, "D10038");
	private _1_6ST _5ST_2_2=new _1_6ST(this, 2, "D10175");
	public STContent ST5_1=new STContent(this,_5ST_1_1, _5ST_2_1, 6, 1);
	public STContent ST5_2=new STContent(this,_5ST_1_2, _5ST_2_2, 6, 2);
	
	private _1_6ST _6ST_1_1=new _1_6ST(this, 1, "D10046");
	private _1_6ST _6ST_2_1=new _1_6ST(this, 1, "D10183");
	private _1_6ST _6ST_1_2=new _1_6ST(this, 2, "D10046");
	private _1_6ST _6ST_2_2=new _1_6ST(this, 2, "D10183");
	public STContent ST6_1=new STContent(this,_6ST_1_1, _6ST_2_1, 7, 1);
	public STContent ST6_2=new STContent(this,_6ST_1_2, _6ST_2_2, 7, 2);
	
	private _7ST _7ST_1_1=new _7ST(this, 1, "D10054");
	private _7ST _7ST_2_1=new _7ST(this, 1, "D10191");
	private _7ST _7ST_1_2=new _7ST(this, 2, "D10054");
	private _7ST _7ST_2_2=new _7ST(this, 2, "D10191");
	public STContent ST7_1=new STContent(this,_7ST_1_1, _7ST_2_1, 8, 1);
	public STContent ST7_2=new STContent(this,_7ST_1_2, _7ST_2_2, 8, 2);
	
	private _1_6ST _8ST_1_1=new _1_6ST(this, 1, "D10064");
	private _1_6ST _8ST_2_1=new _1_6ST(this, 1, "D10201");
	private _1_6ST _8ST_1_2=new _1_6ST(this, 2, "D10064");
	private _1_6ST _8ST_2_2=new _1_6ST(this, 2, "D10201");
	public STContent ST8_1=new STContent(this,_8ST_1_1, _8ST_2_1, 9, 1);
	public STContent ST8_2=new STContent(this,_8ST_1_2, _8ST_2_2, 9, 2);
	
	private _9ST _9ST_1_1=new _9ST(this, 1, "D10072");
	private _9ST _9ST_2_1=new _9ST(this, 1, "D10209");
	private _9ST _9ST_1_2=new _9ST(this, 2, "D10072");
	private _9ST _9ST_2_2=new _9ST(this, 2, "D10209");
	public STContent ST9_1=new STContent(this,_9ST_1_1, _9ST_2_1, 10, 1);
	public STContent ST9_2=new STContent(this,_9ST_1_2, _9ST_2_2, 10, 2);
	
	private _1_6ST _10ST_1_1=new _1_6ST(this, 1, "D10087");
	private _1_6ST _10ST_2_1=new _1_6ST(this, 1, "D10224");
	private _1_6ST _10ST_1_2=new _1_6ST(this, 2, "D10087");
	private _1_6ST _10ST_2_2=new _1_6ST(this, 2, "D10224");
	public STContent ST10_1=new STContent(this,_10ST_1_1, _10ST_2_1, 11, 1);
	public STContent ST10_2=new STContent(this,_10ST_1_2, _10ST_2_2, 11, 2);
	
	private _1_6ST _11ST_1_1=new _1_6ST(this, 1, "D10095");
	private _1_6ST _11ST_2_1=new _1_6ST(this, 1, "D10232");
	private _1_6ST _11ST_1_2=new _1_6ST(this, 2, "D10095");
	private _1_6ST _11ST_2_2=new _1_6ST(this, 2, "D10232");
	public STContent ST11_1=new STContent(this,_11ST_1_1, _11ST_2_1, 12, 1);
	public STContent ST11_2=new STContent(this,_11ST_1_2, _11ST_2_2, 12, 2);
	
	private _12ST _12ST_1_1=new _12ST(this, 1, "D10103");
	private _12ST _12ST_2_1=new _12ST(this, 1, "D10240");
	private _12ST _12ST_1_2=new _12ST(this, 2, "D10103");
	private _12ST _12ST_2_2=new _12ST(this, 2, "D10240");
	public STContent ST12_1=new STContent(this,_12ST_1_1, _12ST_2_1, 13, 1);
	public STContent ST12_2=new STContent(this,_12ST_1_2, _12ST_2_2, 13, 2);
	
	private _13ST _13ST_1_1=new _13ST(this, 1, "D10110");
	private _13ST _13ST_2_1=new _13ST(this, 1, "D10247");
	private _13ST _13ST_1_2=new _13ST(this, 2, "D10110");
	private _13ST _13ST_2_2=new _13ST(this, 2, "D10247");
	public STContent ST13_1=new STContent(this,_13ST_1_1, _13ST_2_1, 14, 1);
	public STContent ST13_2=new STContent(this,_13ST_1_2, _13ST_2_2, 14, 2);
	
	private _BST _BST_1_1=new _BST(this, 1, "D10121");
	private _BST _BST_2_1=new _BST(this, 1, "D10258");
	private _BST _BST_1_2=new _BST(this, 2, "D10121");
	private _BST _BST_2_2=new _BST(this, 2, "D10258");
	public STContent ST14_1=new STContent(this,_BST_1_1, _BST_2_1, 15, 1);
	public STContent ST14_2=new STContent(this,_BST_1_2, _BST_2_2, 15, 2);
	
	private _DST _DST_1_1=new _DST(this, 1, "D10132");
	private _DST _DST_2_1=new _DST(this, 1, "D10269");
	private _DST _DST_1_2=new _DST(this, 2, "D10132");
	private _DST _DST_2_2=new _DST(this, 2, "D10269");
	public STContent ST15_1=new STContent(this,_DST_1_1, _DST_2_1, 16, 1);
	public STContent ST15_2=new STContent(this,_DST_1_2, _DST_2_2, 16, 2);
	
	private static PLC INSTANCE;
	public ReST RST[]=new ReST[]{
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),new ReST(new Resint()),
			//��2���������
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),new ReST(new Resint())
	};
	
	public ReST RST2[]=new ReST[]{
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),new ReST(new Resint()),
			//��2���������
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),
			new ReST(new Resint()), new ReST(new Resint()),new ReST(new Resint()),new ReST(new Resint())
	};
	
	public int[]gw1=new int[]{502,504,506,508,510,512,514};
	public int[]gw2=new int[]{602,604,606,608,610,612,614};
	
	private  PLC(){
		  PLC pp=readO();
		  if(pp!=null){
			  INSTANCE=pp;
			// String ss= INSTANCE.STC1.get(3).firstST.get���ϱ���();
			// System.out.println(ss);
		      STC1=INSTANCE.STC1;
			  line=INSTANCE.line;
			  STC2=INSTANCE.STC2;
			  line2=INSTANCE.line2;
			
			_FST_1_1=INSTANCE._FST_1_1;
			_FST_2_1=INSTANCE._FST_2_1;
			_FST_1_2=INSTANCE._FST_1_2;
			_FST_2_2=INSTANCE._FST_2_2;
		    
			ST0_1=INSTANCE.ST0_1;
			ST0_2=INSTANCE.ST0_2;
			
			_1ST_1_1=INSTANCE._1ST_1_1;
			_1ST_2_1=INSTANCE._1ST_2_1;
			_1ST_1_2=INSTANCE._1ST_1_2;
			_1ST_2_2=INSTANCE._1ST_2_2;
			ST1_1=INSTANCE.ST1_1;
			ST1_2=INSTANCE.ST1_2;
			
			_2ST_1_1=INSTANCE._2ST_1_1;
			_2ST_2_1=INSTANCE._2ST_2_1;
			_2ST_1_2=INSTANCE._2ST_1_2;
			_2ST_2_2=INSTANCE._2ST_2_2;
			ST2_1=INSTANCE.ST2_1;
			ST2_2=INSTANCE.ST2_2;
			_3ST_1_1=INSTANCE._3ST_1_1;
			_3ST_2_1=INSTANCE._3ST_2_1;
			_3ST_1_2=INSTANCE._3ST_1_2;
			_3ST_2_2=INSTANCE._3ST_2_2;
					ST3_1=INSTANCE.ST3_1;
					ST3_2=INSTANCE.ST3_2;
					_4ST_1_1=INSTANCE._4ST_1_1;
					_4ST_2_1=INSTANCE._4ST_2_1;
					_4ST_1_2=INSTANCE._4ST_1_2;
					_4ST_2_2=INSTANCE._4ST_2_2;
					ST4_1=INSTANCE.ST4_1;
					ST4_2=INSTANCE.ST4_2;
					_5ST_1_1=INSTANCE._5ST_1_1;
					_5ST_2_1=INSTANCE._5ST_2_1;
					_5ST_1_2=INSTANCE._5ST_1_2;
					_5ST_2_2=INSTANCE._5ST_2_2;
					ST5_1=INSTANCE.ST5_1;
					ST5_2=INSTANCE.ST5_2;
					_6ST_1_1=INSTANCE._6ST_1_1;
					_6ST_2_1=INSTANCE._6ST_2_1;
					_6ST_1_2=INSTANCE._6ST_1_2;
					_6ST_2_2=INSTANCE._6ST_2_2;
					ST6_1=INSTANCE.ST6_1;
					ST6_2=INSTANCE.ST6_2;
					_7ST_1_1=INSTANCE._7ST_1_1;
					_7ST_2_1=INSTANCE._7ST_2_1;
					_7ST_1_2=INSTANCE._7ST_1_2;
					_7ST_2_2=INSTANCE._7ST_2_2;
					ST7_1=INSTANCE.ST7_1;
					ST7_2=INSTANCE.ST7_2;
					_8ST_1_1=INSTANCE._8ST_1_1;
					_8ST_2_1=INSTANCE._8ST_2_1;
					_8ST_1_2=INSTANCE._8ST_1_2;
					_8ST_2_2=INSTANCE._8ST_2_2;
					ST8_1=INSTANCE.ST8_1;
					ST8_2=INSTANCE.ST8_2;
					_9ST_1_1=INSTANCE._9ST_1_1;
					_9ST_2_1=INSTANCE._9ST_2_1;
					_9ST_1_2=INSTANCE._9ST_1_2;
					_9ST_2_2=INSTANCE._9ST_2_2;
					ST9_1=INSTANCE.ST9_1;
					ST9_2=INSTANCE.ST9_2;
					_10ST_1_1=INSTANCE._10ST_1_1;
					_10ST_2_1=INSTANCE._10ST_2_1;
					_10ST_1_2=INSTANCE._10ST_1_2;
					_10ST_2_2=INSTANCE._10ST_2_2;
					ST10_1=INSTANCE.ST10_1;
					ST10_2=INSTANCE.ST10_2;
					_11ST_1_1=INSTANCE._11ST_1_1;
					_11ST_2_1=INSTANCE._11ST_2_1;
					_11ST_1_2=INSTANCE._11ST_1_2;
					_11ST_2_2=INSTANCE._11ST_2_2;
					ST11_1=INSTANCE.ST11_1;
					ST11_2=INSTANCE.ST11_2;
					_12ST_1_1=INSTANCE._12ST_1_1;
					_12ST_2_1=INSTANCE._12ST_2_1;
					_12ST_1_2=INSTANCE._12ST_1_2;
					_12ST_2_2=INSTANCE._12ST_2_2;
					ST12_1=INSTANCE.ST12_1;
					ST12_2=INSTANCE.ST12_2;
					_13ST_1_1=INSTANCE._13ST_1_1;
					_13ST_2_1=INSTANCE._13ST_2_1;
					_13ST_1_2=INSTANCE._13ST_1_2;
					_13ST_2_2=INSTANCE._13ST_2_2;
					ST13_1=INSTANCE.ST13_1;
					ST13_2=INSTANCE.ST13_2;
					_BST_1_1=INSTANCE._BST_1_1;
					_BST_2_1=INSTANCE._BST_2_1;
					_BST_1_2=INSTANCE._BST_1_2;
					_BST_2_2=INSTANCE._BST_2_2;
					ST14_1=INSTANCE.ST14_1;
					ST14_2=INSTANCE.ST14_2;
					_DST_1_1=INSTANCE._DST_1_1;
					_DST_2_1=INSTANCE._DST_2_1;
					_DST_1_2=INSTANCE._DST_1_2;
					_DST_2_2=INSTANCE._DST_2_2;
					ST15_1=INSTANCE.ST15_1;
					ST15_2=INSTANCE.ST15_2;

		  
		  }
		
		STC1.clear();  
		STC1.add(ST0_1);STC1.add(ST1_1);STC1.add(ST2_1);STC1.add(ST3_1);STC1.add(ST4_1);STC1.add(ST5_1);
		STC1.add(ST6_1);STC1.add(ST7_1);STC1.add(ST8_1);STC1.add(ST9_1);STC1.add(ST10_1);STC1.add(ST11_1);
		STC1.add(ST12_1);STC1.add(ST13_1);STC1.add(ST14_1);STC1.add(ST15_1);
		
		STC2.clear(); 
		
		STC2.add(ST0_2);STC2.add(ST1_2);STC2.add(ST2_2);STC2.add(ST3_2);STC2.add(ST4_2);STC2.add(ST5_2);
		STC2.add(ST6_2);STC2.add(ST7_2);STC2.add(ST8_2);STC2.add(ST9_2);STC2.add(ST10_2);STC2.add(ST11_2);
		STC2.add(ST12_2);STC2.add(ST13_2);STC2.add(ST14_2);STC2.add(ST15_2);
	 }
	
	public static synchronized PLC getIntance(){
		if(INSTANCE!=null){return INSTANCE;}else{
			INSTANCE=new PLC();
			System.out.println("---------------");
			INSTANCE.startTiaodu();
			return INSTANCE;
		}
		
	}
	public void setAutoRfidUp(boolean state){
		SqlPro.autoRFIDup=state;
	}
	public boolean getAutoRfidUp(){
		  return SqlPro.autoRFIDup;
	}
	
	public void writeO(){
		try{
			long time1=System.currentTimeMillis();
		    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\PLC.obj"));
			out.writeObject(this);
			out.flush();
			//System.out.println(System.currentTimeMillis()-time1);
			out.close();
		  }catch(Exception ex){ex.printStackTrace();}
		
	}
	
	public PLC readO() {
		ObjectInputStream in=null;
         try{
		 in = new ObjectInputStream(new FileInputStream("D:\\PLC.obj"));
		  PLC plc=  (PLC)in.readObject();
		  in.close();
		  return plc;
	     }catch(Exception e){
	    	 try{
	    	 if(in!=null)in.close();}catch(Exception exx){}
	    	 e.printStackTrace();
	    	 return null;}
	}
	Object LOCK1="";
	Object LOCK2="";
	public  String getSTATE1() {
		synchronized(LOCK1){
		return STATE1;
		}
	}
	public  void setSTATE1() {
		synchronized(LOCK1){
			try{
			STATE1=ClientSer.getIntance().getState(10);}catch(Exception ex){ex.printStackTrace();}
		}
	}
	public  String getSTATE2() {
		synchronized(LOCK2){
		return STATE2;}
	}
	public synchronized void setSTATE2() {
		synchronized(LOCK2){
			try{
			STATE2=ClientSer.getIntance().getState(11);}catch(Exception ex){ex.printStackTrace();}
		}
	}
	
	public void startTiaodu(){
		new Thread(){
			public void run(){
			while(true){
				try{
					setSTATE1();
					setSTATE2();
				}catch(Exception e){e.printStackTrace();}
				ST0_1.initFromSql();
				ST1_1.initFromSql();
				ST2_1.initFromSql();
				ST3_1.initFromSql();
				ST4_1.initFromSql();
				ST5_1.initFromSql();
				ST6_1.initFromSql();
				ST7_1.initFromSql();
				ST8_1.initFromSql();
				ST9_1.initFromSql();
				ST10_1.initFromSql();
				ST11_1.initFromSql();
				ST12_1.initFromSql();
				ST13_1.initFromSql();
				ST14_1.initFromSql();
				ST15_1.initFromSql();
				
				getSTRdy(1,2);
				getSTRdy(1,3);
				getSTRdy(1,4);
				getSTRdy(1,5);
				getSTRdy(1,6);
				getSTRdy(1,7);
				getSTRdy(1,8);
				getFromPLC(1);//����д�����
				writeO();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			}
			
		}.start();
		
	}
	
	public ReST[] getRePLC(int machine){
		if(machine==1)return RST;else return RST2;
		
	}
	public  Vector<STContent> getWrPLC(int machine){
		if(machine==1)return STC1;else return STC2;
		
	}
	public  CarryLine getCarryLine(int machine){
		if(machine==1)return line;else return line2;
		
	}
	
	//��ȡ����״̬����������������
    public ReST[] getFromPLC(int װ����){
    	try{
    		Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 63, 16, װ����);
    		
    		for(int i=0;i<16;i++){
    			
    			Resint bint=r[i*2];
    			int tem1=bint.getResInt();
    			int tem2=getRePLC(װ����)[i].boolCont.getResInt();
    			
    			int �ؾ߷���1=(tem1&0b100)==4?1:0;
    			int �ؾ߷���2=(tem2&0b100)==4?1:0;
    			int ȡ�����1=(tem1&0b10)==2?1:0;
    			int ȡ�����2=(tem2&0b10)==2?1:0;
    			//RST[i]=new ReST(bint);
    			getRePLC(װ����)[i].set�ؾߵ�λ((tem1&0b1)==1?true:false);
    			getRePLC(װ����)[i].set�˹���װ��ģʽ((tem1&0b1000)==8?true:false);
    			getRePLC(װ����)[i].set�ؾ߷���((tem1&0b100)==4?true:false);
    			getRePLC(װ����)[i].set�������((tem1&0b10)==2?true:false);
    			if(ȡ�����1!=ȡ�����2){//ģ��һ���źŸı�
    				//�������̵���������
    				if(ȡ�����1==1){//ģ��һ��������
    					//��Ȿ��λ��û������
    					if(getCarryLine(װ����).getCarry(i)!=null){
    					getWrPLC(װ����).get(i).updataDB(getWrPLC(װ����).get(i).firstST);//�������̵�����
    				 
    				    System.out.println("ȡ�����1");
    				    
    					}
    				
    				}
    				
    			}
    			
    			if(�ؾ߷���1!=�ؾ߷���2){
    				
    				
    				//��������λ�ã�ͬʱ��write�ó�false,
    				if(�ؾ߷���1==1){
    				if(i<15){
    					Carry car=getCarryLine(װ����).getCarry(i);
    					if(car!=null){
    					if(car.getName().equals(getWrPLC(װ����).get(i).firstST.getName())){
    						//�����������Ǳ���λ��Ҫ������
    						 if( getWrPLC(װ����).get(i).firstST.getʣ������()==0||�����ȡ������){
    							 if(ȡ�����1==1||����⶯�����){
    							 System.out.println("�ؾ߷���1");
    						   getWrPLC(װ����).get(i).firstST.set���ݸ������(true);
    						   String back=STC1.get(i).firstST.writeifChangeToPLC();
    						   System.out.println("�ؾ߷���1"+back);
    						  if(back.contains("�ɹ�")){
    							  //д��PLC�ɹ���
    	    				 if( getCarryLine(װ����).removeToNext(i))
    	    					 getWrPLC(װ����).get(i).firstST.setWrite(false);
    	    				  }
    							  } //end �ж�ȡ�����
    						 }
    					}
    					else{//�������ڱ���λû���κ���Ҫ�Ķ��������ж϶�����ɱ�־
    						 getCarryLine(װ����).removeToNext(i);
    	    				  
    					}
    					
    					}
    				 //���µ�PLC,��initFromSql()�Զ����
    			       }
    				
    				
    				
    				
    				}
    			}
    			
    		}
    		
    		//���µڸ���������
    		for(int i=16;i<32;i++){
    			Resint bint=r[i*2];
    			int tem1=bint.getResInt();
    			int tem2=getRePLC(װ����)[i].boolCont.getResInt();
    			int �ؾ߷���1=(tem1&0b100)==4?1:0;
    			int �ؾ߷���2=(tem2&0b100)==4?1:0;
    			int ȡ�����1=(tem1&0b10)==2?1:0;
    			int ȡ�����2=(tem2&0b10)==2?1:0;
    			getRePLC(װ����)[i].set�ؾߵ�λ((tem1&0b1)==1?true:false);
    			getRePLC(װ����)[i].set�˹���װ��ģʽ((tem1&0b1000)==8?true:false);
    			getRePLC(װ����)[i].set�ؾ߷���((tem1&0b100)==4?true:false);
    			getRePLC(װ����)[i].set�������((tem1&0b10)==2?true:false);
    			//RST[i]=new ReST(bint);
    			if(ȡ�����1!=ȡ�����2){
    				//�������̵���������
    				if(ȡ�����1==1){
    					if(getCarryLine(װ����).getCarry(i)!=null)
    					getWrPLC(װ����).get(i).updataDB(getWrPLC(װ����).get(i).secondST);//�������̵�����
    					
    				}
    				
    			}
    			
    			
    		}
    	
    	
    	}catch(Exception ex){ex.printStackTrace();}
    	return null;
    }
    

    
	/*public void set����(int s){
	    //  ReST rs=RST[s];
		
	  }*/
	
	public  static void main(String ss[]){
		
		getIntance();
	
	}
	
   public String writeBlockToBLC(String startAddress,int len,int[]val, int machineID){
       int i= ClientSer.getIntance().writeSirIntToCTR(startAddress, len, val, machineID);
	   if(i!=-1){
	   return "�ɹ�";
	   }else{
		 return "ʧ��";
	   }
   }
   
   public String writeRandomToBLC(String[] startAddress,int[]val, int machineID){
	   
	     return null;
   }
   
   public int[] readBlockFromBLC(String startAddress,int nums, int machineID){
	   try{
		  Resint[] back= ClientSer.getIntance().getSirIntValuesFromCTR(startAddress, nums, 16, machineID);
	  if(back!=null){
			  int tem[]=new int[back.length];
			   for(int i=0;i<tem.length;i++){
				   tem[i]=back[i].getResInt();
				   
			   }
			  return tem;
		  }
		   
	   }catch(Exception ex){}
	   
	   return null;
   }
   
  
   //�������û׼���ã���ôҪ�������£�1�������λ�������̣���ô����������̵����ϲ���ָ��Ҫȡ�����ϣ�����������������ʱ��Ҫ�������ߣ�����һ���µ����̣�Ҫ��û��������ô�͵��µ�������
   public boolean getSTRdy(int line,int st){
	   //�ⲽ�жϿ��Բ���Ҫ����Ϊֻ�е�λ�źŲŸ�������
	   //STATE1,STATE2�����߳������Զ�����
	   boolean ��λ1=false; boolean ��λ2=false;
	   try{
		  if(st==1){
			   if(line==1){
				   STC1.get(st-1).firstST.set����RDY(true);
				   STC1.get(st-1).secondST.set����RDY(true);
				   return true;
			   }else{
				   STC2.get(st-1).firstST.set����RDY(true);
				   STC2.get(st-1).secondST.set����RDY(true);
				   return true;
			   }
		   }
		  
	  if(st>=2&&st<=8){
	   if(line==1){
		   
		   String smm[]=getSTATE1().split("\\|");
		   
		   if(smm[st-2].split("=")[1].equals("0")){
			  
			   STC1.get(st-1).firstST.set����RDY(false);
			   STC1.get(st-1).secondST.set����RDY(false);
			     ��λ1=false;
			   //return false;  
		   }else{
			     ��λ1=true;
		   }
		   
	   }else{
		   String smm[]=getSTATE2().split("\\|");
		   
		   if(smm[st-2].split("=")[1].equals("0")){
			   STC2.get(st-1).firstST.set����RDY(false);
			   STC2.get(st-1).secondST.set����RDY(false);
			   ��λ2=false;
			  // return false;  
		   }else{
			   ��λ2=true;
		    }
		   
	   }
	   }
	   
	 
	   if(st>=2&&st<=7){
		  String tem2=null;
		  STContent cot= STC1.get(st-1);
		  if(line==1){
			  tem2=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ��λ��='"+gw1[st-2]+"'"); 
			  cot= STC1.get(st-1);
		  }else{
			  tem2=SqlTool.findOneRecord("select  ���̱��,����,���� ,��λ��  from �������   where  ��λ��='"+gw2[st-2]+"'"); 
			  cot= STC2.get(st-1);  
			  
		  }
		  
		  if(tem2==null){//û�����̵����
			  cot.firstST.set����RDY(false);
			  cot.secondST.set����RDY(false);
			  
			  String wuliao=cot.firstST.get���ϱ���()==null?"":cot.firstST.get���ϱ���();
			  if(!wuliao.equals("")){
				  if(line==1){	  
		         String sql1=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ����='"+wuliao+"' and ����>0 and ����='"+line+"'"); 
				  if(sql1!=null){
					 String sm6[]=sql1.split("!_!") ;
					 SqlTool.add����ָ��(sm6[0], sm6[3],  (gw1[st-2]-1)+"", "�»�", 0, line+"");
					   
				    }
				  }else{
					  String sql1=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ����='"+wuliao+"' and ����>0 and ����='"+2+"'"); 
					  if(sql1!=null){
						 String sm6[]=sql1.split("!_!") ;
						 SqlTool.add����ָ��(sm6[0], sm6[3],  (gw2[st-2]-1)+"", "�»�", 0, line+"");
						   
					    }  
					  
				  }
			  }
			  //�������Ķ���������
			  
			  return false;}
		 
		  else{//�����̵����
			  String sm[]=tem2.split("!_!");
			  int tpshul=Integer.parseInt(sm[2]);
		      
		       int id=((_1_6ST)cot.firstST).id;
		       int id2=((_1_6ST)cot.secondST).id;
            //   boolean re=((_1_6ST)cot.firstST).is����RDY();
		    //   boolean re2=((_1_6ST)cot.secondST).is����RDY();
		       int ��������=((_1_6ST)cot.firstST).get��������();
			   int �������=((_1_6ST)cot.firstST).get�������();
			   if(��������!=�������){
				   //�����һ�����л�ûȡ�꣬�Ǿ��жϵ�һ������
		       
		       String tem=SqlTool.findOneRecord("select  ����,����  from �䷽ָ�����   where  ID='"+id+"'");
		          if(tem==null){//û����˼����λ��û��ʼ��
		        	  cot.firstST.set����RDY(false);
		        	  tem="-1!_!-1";
		    	     return false;
		    	     }
		       if(sm[1].equals(tem.split("!_!")[0])){//�ж϶�����Ҫ�����ϣ�����ǰ��λ���̵������ǲ���һ��
		    	   
		    	   if(��������-�������>tpshul){
					   //�����̻���
		    		   cot.firstST.set����RDY(false);
		    		   
		    		   SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 1, line+"");
		    		   System.out.println("Ҫȡ�Ĳ�����------������="+sm[1]+"=="+tem.split("!_!")[0]);
		    		   
					    return false;
					   }else{
						if(line==1){   
					    cot.firstST.set����RDY(true&&��λ1);  
					    return true&&��λ1;}
						else{
							 cot.firstST.set����RDY(true&&��λ2);  
							 return true&&��λ2;
					        }
					   
					   }
		    	   
		       }else{ //�������ϸ�����λ�Ĳ�һ��
		    	      //�����̻���  
		    	   if(tpshul>2){//�ػ���
		    	     SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 0, line+"");
		    	     System.out.println("��Ϊ�������ϲ��Ǳ���λҪ������------���ص�����"+sm[1]+"=="+tem.split("!_!")[0]);
		    	     }else{//�ش��
		    	     SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 1, line+"");	 
		    	     System.out.println("��Ϊ��������<3�Ҳ��Ǳ���λҪ������------���ص����"+sm[1]+"=="+tem.split("!_!")[0]);
		    	     }
		    	   
		    	   cot.firstST.set����RDY(false);
		    	   return false;  
		       }
		    }
			   else{//�����һ������ȡ���ˣ����жϵڶ�������	   
				   String tem=SqlTool.findOneRecord("select  ����,����  from �䷽ָ�����   where  ID='"+id2+"'");
				 
				   if(tem==null){//û����˼����λ��û��ʼ��
			        	  cot.secondST.set����RDY(false);
			        	  tem="-1!_!-1";
			    	     return false;
			    	     }
				   if(sm[1].equals(tem.split("!_!")[0])){
					   int ��������2=((_1_6ST)cot.secondST).get��������();
					   int �������2=((_1_6ST)cot.secondST).get�������();
					   if(��������2-�������2>tpshul){
						   //�����̻���
						   cot.secondST.set����RDY(false);
						   SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 1, line+"");  
						   return false;
						   }else{
							   if(line==1){   
								    cot.secondST.set����RDY(true&&��λ1);  
								    return true&&��λ1;}
									else{
										 cot.secondST.set����RDY(true&&��λ2);  
										 return true&&��λ2;
								        }
							   }
					   
				   }
				        else{
			    	      //�����̻���  
				        cot.secondST.set����RDY(false);
				        if(tpshul>2){//�ػ���
				    	     SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 0, line+"");
				    	     System.out.println("2��Ϊ�������ϲ��Ǳ���λҪ������------���ص�����"+sm[1]+"=="+tem.split("!_!")[0]);
				    	     }else{//�ش��
				    	     SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 1, line+"");	 
				    	     System.out.println("2��Ϊ��������<3�Ҳ��Ǳ���λҪ������------���ص����"+sm[1]+"=="+tem.split("!_!")[0]);
				    	     }
				    	 return false;  
				       }
				   
				   /////////////////////////////
				   
			   }
		       
		       
		  } 
		   
	   }
	   
	   
	   if(st==8){//ֻҪ�����������������൱���ܷ���TRUE
			 
			  String tem2=null;
			  STContent cot= STC1.get(st-1);
			  if(line==1){
				  tem2=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ��λ��='"+gw1[st-2]+"'"); 
				  cot= STC1.get(st-1);
			  }else{
				  tem2=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ��λ��='"+gw2[st-2]+"'"); 
				  cot= STC2.get(st-1);  
				  
			  }
			  if(tem2==null){//û�����̵����
				  cot.firstST.set����RDY(false);
				  cot.secondST.set����RDY(false);
				  
				 String wuliao=cot.firstST.get���ϱ���()==null?"":cot.firstST.get���ϱ���();
				  if(!wuliao.equals("")){
					  if(line==1){	  
			         String sql1=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ����='"+wuliao+"' and ����>0 and ����='"+line+"'"); 
					  if(sql1!=null){
						 String sm6[]=sql1.split("!_!") ;
						  SqlTool.add����ָ��(sm6[0], sm6[3],  (gw1[st-2]-1)+"", "�»�", 0, line+"");
						   
					    }
					  }else{
						  String sql1=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ����='"+wuliao+"' and ����>0 and ����='"+2+"'"); 
						  if(sql1!=null){
							 String sm6[]=sql1.split("!_!") ;
							 SqlTool.add����ָ��(sm6[0], sm6[3],  (gw2[st-2]-1)+"", "�»�", 0, line+"");
							   
						    }  
						  
					  }
				  }
				  //�������Ķ���������
				  
				  return false;}
			 
			  else{//�����̵����
				  String sm[]=tem2.split("!_!");
				   int tpshul=Integer.parseInt(sm[2]);
			       int id=((_7ST)cot.firstST).id;
			       int id2=((_7ST)cot.secondST).id;
	               boolean re=((_7ST)cot.firstST).is����RDY();
			       boolean re2=((_7ST)cot.secondST).is����RDY();
			       int ��������=((_7ST)cot.firstST).get��������();
				   int �������=((_7ST)cot.firstST).get�������();
				   if(��������!=�������){
					   //�����һ�����л�ûȡ�꣬�Ǿ��жϵ�һ������
			       
			       String tem=SqlTool.findOneRecord("select  ����,����  from �䷽ָ�����   where  ID='"+id+"'");

				   if(tem==null){//û����˼����λ��û��ʼ��
			        	  cot.firstST.set����RDY(false);
			        	  tem="-1!_!-1";
			    	     return false;
			    	     }
			       if(sm[1].equals(tem.split("!_!")[0])){
			    	   if(tpshul<1){//��Ϊ��оÿ��ֻȡһ��������ֻ�жϴ���0�����
						   //�����̻���
			    		   cot.firstST.set����RDY(false);
			    		   SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 1, line+"");  
						   return false;
						   }else{
							   if(line==1){   
								    cot.firstST.set����RDY(true&&��λ1);  
								    return true&&��λ1;}
									else{
										 cot.firstST.set����RDY(true&&��λ2);  
										 return true&&��λ2;
								        }
							   
						   }
			    	   
			       }else{//������ϲ����
			    	      //�����̻���  
			    	   cot.firstST.set����RDY(false);
			    	   
			    	   if(tpshul>2){//�ػ���
				    	     SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 0, line+"");
				    	     }else{//�ش��
				    	     SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 1, line+"");	 
				    	     }
			    	   return false;  
			       }
			    }
				   else{
					     //�����һ������ȡ���ˣ����жϵڶ�������	   
					   String tem=SqlTool.findOneRecord("select  ����,����  from �䷽ָ�����   where  ID='"+id2+"'");

					   if(tem==null){//û����˼����λ��û��ʼ��
				        	  cot.secondST.set����RDY(false);
				        	  tem="-1!_!-1";
				    	     return false;
				    	     }
					   if(sm[1].equals(tem.split("!_!")[0])){
						   int ��������2=((_7ST)cot.secondST).get��������();
						   int �������2=((_7ST)cot.secondST).get�������();
						   if(tpshul<1){
							   //�����̻���
							   cot.secondST.set����RDY(false);
							   SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 1, line+"");  
							   return false;
							   }else{
								   if(line==1){   
									    cot.secondST.set����RDY(true&&��λ1);  
									    return true&&��λ1;}
										else{
										cot.secondST.set����RDY(true&&��λ2);  
										return true&&��λ2;
									        }
								   }
						   
					   }
					        else{
				    	      //�����̻���  
					        	 cot.secondST.set����RDY(false);
					        	 if(tpshul>2){//�ػ���
						    	     SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 0, line+"");
						    	     }else{//�ش��
						    	     SqlTool.add����ָ��(sm[0], sm[3], "60002", "�����߻���", 1, line+"");	 
						    	     }
					    	 return false;  
					       }
					   
				   }
			       
			       
			  } 
			      
		   
	   }
	   
	   
	      return true;
	   
	   }catch(Exception ex){ex.printStackTrace();}
	   
	 
	   
	   
	    
	     return false;
	   
   }
   
    ReST first=new ReST(new Resint());
	ReST second=new ReST(new Resint());
	Hashtable table=new Hashtable();
	String LOCK3="";
	public Hashtable  getDataFromGD(int ST,int line){
		synchronized(LOCK3){
		Resint[] rs=ClientSer.getIntance().getReturnPlc("D11001",63,16,line);
		
		Resint r1=rs[ST*2];
		Resint r2=rs[(ST+16)*2];
		first=new ReST(r1);
		first.startAddres="D"+(11001+ST*2);
	    second=new ReST(r2);
	    second.startAddres="D"+(11033+ST*2);
		try{
		Field f[]=first.getClass().getDeclaredFields();
		table.clear();
		for(int i=0;i<f.length;i++){
		    String name=f[i].getName();
		    if(name.equals("serialVersionUID"))continue;
		    Object ty=f[i].getType();

			 try{
				 
			 if(ty.toString().equals("boolean")){
				 String name2=name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
				 Method m2=	first.getClass().getMethod("is"+name2, null) ;
				 Method m3=	second.getClass().getMethod("is"+name2, null) ;
				 table.put(name,  m2.invoke(first, null));
				 table.put(name+"2",  m3.invoke(second, null));
				 }else{
					 if(name.equals("boolCont")){
						 String name2=name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
						 Method m2=	first.getClass().getMethod("get"+name2, null) ;
						 Method m3=	second.getClass().getMethod("get"+name2, null) ;
						 table.put(name,  ((Resint)m2.invoke(first, null)).getResInt());
						 table.put(name+"2",((Resint)m3.invoke(second, null)).getResInt());
					 }else{
					 String name2=name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
					 Method m2=	first.getClass().getMethod("get"+name2, null) ;
					 Method m3=	second.getClass().getMethod("get"+name2, null) ;
					 table.put(name,  m2.invoke(first, null));
					 table.put(name+"2",  m3.invoke(second, null));
					 }
					 
					 }
				  
			 }catch(Exception ex){ex.printStackTrace();}   
			 
		
			
		}
		
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		
		return table;
		}
     }
	
	public String setValueByName(String name,String value,String oldValue,int ST,int line){
		if(oldValue==null)oldValue="";
		synchronized(LOCK3){
		 try{
			 getDataFromGD( ST,line);
			 Field f= first.getClass().getDeclaredField(name);
			 String name2=name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
			 
			 if(f.getType().toString().equals("boolean")){
				 
			   Method m=	first.getClass().getMethod("is"+name2, null) ;
			   Object b= m.invoke(first, null)==null?"": m.invoke(first, null);
			   if(!oldValue.equals(b+"")){
				    return "���ݲ�ͬ������ͬ�����ڸ������ݣ�";  
				   
			   }
			   ////////////////////////////////////////////////
			   Method m2=	first.getClass().getMethod("set"+name2, boolean.class) ; 
			   if(value!=null&&value.equals("false"))
			    m2.invoke(first, false);
			   if(value!=null&&value.equals("true"))
				   m2.invoke(first, true);
			       first.writeToPLC(); 
			   return "�ɹ�";
			 }
			 if(f.getType().toString().equals("int")){
				 
				 Method m=	first.getClass().getMethod("get"+name2, null) ;
				   Object b= m.invoke(first, null)==null?"": m.invoke(first, null);
				   if(!oldValue.equals(b+"")){
					    return "���ݲ�ͬ������ͬ�����ڸ������ݣ�";  
					   
				   }
				/////////////////////////////////////////////////////////////	
				   Method m2=	first.getClass().getMethod("set"+name2, int.class) ; 
				   if(value!=null)
				   m2.invoke(first, Integer.parseInt(value));
				   first.writeToPLC(); 
				   return "�ɹ�";
				 }
			 
			 if(f.getType().toString().equals("class java.lang.String")){
				 
				 Method m=	first.getClass().getMethod("get"+name2, null) ;
				 Object b= m.invoke(first, null)==null?"": m.invoke(first, null);
				   if(!oldValue.equals(b+"")){
					    return "���ݲ�ͬ������ͬ�����ڸ������ݣ�";  
					   
				   }
				/////////////////////////////////////////////////////////////	
					
				   Method m2=	first.getClass().getMethod("set"+name2, String.class) ; 
				   if(value!=null)
				   m2.invoke(first, value);
				   first.writeToPLC(); 
				   return "�ɹ�";
				 }
			 if(f.getType().toString().equals("class alai.GDT.Resint")){
				   return "�ɹ�";
				 
			    }
		 
		 }catch(Exception ex){ex.printStackTrace();
		   return ex.getMessage();
		 }
		
		   return "ʧ��";
		
		 }
	}

	
}
