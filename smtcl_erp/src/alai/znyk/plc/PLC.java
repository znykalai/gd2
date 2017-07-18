package alai.znyk.plc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
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
	
	public boolean A���������Զ������=false;
	public boolean B���������Զ������=false;
	public boolean A�������ߵ�λ����=false;
	public boolean B�������ߵ�λ����=false;
	public boolean �����ȡ������=true;
	protected transient long lastConcnetA=0;
	protected transient long lastConcnetB=0;
	protected transient Hashtable th=new Hashtable();
	protected transient Hashtable<Integer,String> ����startTime_A=new Hashtable<Integer,String>();
	protected transient Hashtable<String,Long> tongji_A=new Hashtable<String,Long>();
	
	protected transient Hashtable<Integer,String> ����startTime_B=new Hashtable<Integer,String>();
	protected transient Hashtable<String,Long> tongji_B=new Hashtable<String,Long>();
	/*tongji.put("�շ��д���");tongji.put("�շ���ʱ��");tongji.put("�ϴοշ�����ʱ");
	 *tongji.put("�ж������д���");tongji.put("�ж�������ʱ��");tongji.put("�ϴ��ж���������ʱ");
	 *tongji.put("���ģ������");tongji.put("���ģPACK����");
	 *
	 * 
	 * */
	public int ������ֵ=2;
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
	public Hashtable getTH(){return th;}
    
	public boolean ����⶯�����=true;
	public boolean stop1=true;//ֹͣ����ϵͳ�����ݿ������ȡ����
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
	public CarryLine line=new CarryLine(1);
	public CarryLine line2=new CarryLine(2);
	String STATE1="502=0|504=0|506=0|508=0|510=0|512=0|514=0";
	

	String STATE2="602=0|604=0|606=0|608=0|610=0|612=0|614=0";
	
	private _FST _FST_1_1=new _FST(this, 1, "D10001");
	private _FST _FST_2_1=new _FST(this, 1, "D10138");
	private _FST _FST_1_2=new _FST(this, 2, "D10001");
	private _FST _FST_2_2=new _FST(this, 2, "D10138");
	public STContent ST0_1=new STContent(this,_FST_1_1, _FST_2_1, 1, 1);
	public STContent ST0_2=new STContent(this,_FST_1_2, _FST_2_2, 1, 2);
	
	private _1_6ST _1ST_1_1=new _1_6ST(this, 1, "D10006");
	private _1_6ST _1ST_2_1=new _1_6ST(this, 1, "D10143");
	private _1_6ST _1ST_1_2=new _1_6ST(this, 2, "D10006");
	private _1_6ST _1ST_2_2=new _1_6ST(this, 2, "D10143");
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
	
	/*public int[]gw1=new int[]{502,504,506,508,510,512,514};
	public int[]gw2=new int[]{602,604,606,608,610,612,614};
	*/
	public int[]gw1=new int[]{514,512,510,508,506,504,502};
	public int[]gw2=new int[]{614,612,610,608,606,604,602};
	
	private  PLC(){
		  PLC pp=readO();
		 
		  if(pp!=null){
			  pp.setDiaodu1(true);
			  pp.setDiaodu2(true);
			  INSTANCE=pp;
			// String ss= INSTANCE.STC1.get(3).firstST.get���ϱ���();
			// System.out.println(ss);
			  stop1=INSTANCE.stop1;
			  stop2=INSTANCE.stop2;
			  �����ȡ������=INSTANCE.�����ȡ������;
			  ����⶯�����=INSTANCE.����⶯�����;
			 
			  A���������Զ������=INSTANCE.A���������Զ������;
			  B���������Զ������=INSTANCE.B���������Զ������;
			  A�������ߵ�λ����=INSTANCE.A�������ߵ�λ����;
			  B�������ߵ�λ����=INSTANCE.B�������ߵ�λ����; 
			  System.out.println("A���������Զ������="+A���������Զ������);
			  
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
			System.out.println("PLC����---------------");
			INSTANCE.startTiaodu();
			INSTANCE.startTiaodu2();
			INSTANCE.sendHert(1);
			INSTANCE.sendHert(2);//��2����
			INSTANCE.autoGetPaletToST(1);
			INSTANCE.autoGetPaletToST(2);
			
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
			
			SqlTool.writePLC(this);
			
		  }catch(Exception ex){ex.printStackTrace();}
		
	}
	
	public PLC readO() {
		
         try{
        	 Object ob=SqlTool.readPLC();
        	 if(ob!=null)
             return (PLC)ob;
        	 else return null;
	     }catch(Exception e){
	    	
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
			STATE1=ClientSer.getIntance().getState(10);
			}catch(Exception ex){
				SqlPro.getLog().error(ex.getMessage());
				ex.printStackTrace();}
		}
	}
	public  String getSTATE2() {
		synchronized(LOCK2){
		return STATE2;}
	}
	public synchronized void setSTATE2() {
		synchronized(LOCK2){
			try{
			STATE2=ClientSer.getIntance().getState(11);}catch(Exception ex){
				SqlPro.getLog().error(ex.getMessage());
				ex.printStackTrace();}
		}
	}
	String LockCon="con";
	
	private long getLastConcent(int machineID){
		synchronized(LockCon){
		    if(machineID==1) return lastConcnetA;
		    else return lastConcnetB;
		    
		     }
		  }
	private void setLastConcent(long num,int machineID){
		synchronized(LockCon){
		    if(machineID==1)  lastConcnetA=num;
		                else  lastConcnetB=num;
		    
		     }
		
	}
	
	public void sendHert(int machineID){
		    new Thread(){
		    	public void run(){
		    	while(true){
		    		try{
		    			int bak=ClientSer.getIntance().writeSirIntToCTR("D11999", 1, new int[]{1}, machineID);
		    			if(bak>-1){
		    				setLastConcent(System.currentTimeMillis(), machineID);
		    				
		    			}
		    			Thread.sleep(500);
		    			ClientSer.getIntance().writeSirIntToCTR("D11999", 1, new int[]{0}, machineID);
		    			if(bak>-1){
		    				setLastConcent(System.currentTimeMillis(), machineID);
		    			}
		    			Thread.sleep(500);
		    		}catch(Exception ex){ex.printStackTrace();}
		    		
		    	}
		    	}
		    }.start();
	}
	
	public boolean getConcent(int machineID){
		if(System.currentTimeMillis()-getLastConcent(machineID)<15) return true;
		  else
		  return false;
	}
	
	public void startTiaodu(){
		
		System.out.println("����16����λ���ȡ�������");
		new Thread(){
			public void run(){ 
			while(true){
				
				long time1=System.currentTimeMillis();
				try{
					setSTATE1();
					setSTATE2();
				}catch(Exception e){e.printStackTrace();}
				//System.out.println("����ͨ��ʱ��="+(System.currentTimeMillis()-time1)+"MS");
				if(!getConcent(1)){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
					
				}
				
				ST0_1.initFromSql(1);
				ST1_1.initFromSql(1);
				ST2_1.initFromSql(1);
				ST3_1.initFromSql(1);
				ST4_1.initFromSql(1);
				ST5_1.initFromSql(1);
				ST6_1.initFromSql(1);
				ST7_1.initFromSql(1);
				ST8_1.initFromSql(1);
				ST9_1.initFromSql(1);
				ST10_1.initFromSql(1);
				ST11_1.initFromSql(1);
				ST12_1.initFromSql(1);
				ST13_1.initFromSql(1);
				ST14_1.initFromSql(1);
				ST15_1.initFromSql(1);
				//System.out.println("14����λ���ݿ⴦��ʱ��="+(System.currentTimeMillis()-time1)+"MS");
				getSTRdy(1,2);
				getSTRdy(1,3);
				getSTRdy(1,4);
				getSTRdy(1,5);
				getSTRdy(1,6);
				getSTRdy(1,7);
				getSTRdy(1,8);
				if(!stop1)
				getFromPLC(1);//����д�����
				long en=System.currentTimeMillis()-time1;
				if(en>1000){
		    	SqlPro.getLog().error("A��15��λPLCͨ���봦��ʱ��="+en+"MS");
				System.out.println("A��PLCͨ���봦��ʱ��="+en+"MS");
				}
				writeO();
			//	System.out.println("A������15����λһ��������ʱ="+(System.currentTimeMillis()-time1)+"MS");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			}
			
		}.start();
		
	}
	
	public void startTiaodu2(){
		new Thread(){
			public void run(){
			while(true){
				/*try{
					setSTATE1();
					setSTATE2();
				}catch(Exception e){e.printStackTrace();}*/
				
				if(!getConcent(2)){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
					
				}
				long time1=System.currentTimeMillis();
				ST0_2.initFromSql(1);
				ST1_2.initFromSql(1);
				ST2_2.initFromSql(1);
				ST3_2.initFromSql(1);
				ST4_2.initFromSql(1);
				ST5_2.initFromSql(1);
				ST6_2.initFromSql(1);
				ST7_2.initFromSql(1);
				ST8_2.initFromSql(1);
				ST9_2.initFromSql(1);
				ST10_2.initFromSql(1);
				ST11_2.initFromSql(1);
				ST12_2.initFromSql(1);
				ST13_2.initFromSql(1);
				ST14_2.initFromSql(1);
				ST15_2.initFromSql(1);
				
				getSTRdy(2,2);
				getSTRdy(2,3);
				getSTRdy(2,4);
				getSTRdy(2,5);
				getSTRdy(2,6);
				getSTRdy(2,7);
				getSTRdy(2,8);
				if(!stop2)
				getFromPLC(2);//����д�����
				long en=System.currentTimeMillis()-time1;
				if(en>1000){
				
				SqlPro.getLog().error("B��15��λPLCͨ���봦��ʱ��="+en+"MS");
				}
				//System.out.println("B������15����λһ��������ʱ="+(System.currentTimeMillis()-time1)+"MS");
				//writeO();
				try {
					Thread.sleep(100); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			}
			
		}.start();
		
	}
	   //�Զ���������λ����	
	public void autoGetPaletToST(int machineID){
		new Thread(){public void run(){
		while(true){
			 try{
			      Thread.sleep(1000);
			 }catch(Exception ex){}
			if(machineID==1&&!A���������Զ������)continue;
			if(machineID==2&&!B���������Զ������)continue;
			
			try{
			/*	
			  String ss= ClientSer.getIntance().getState(machineID==1?SqlPro.A��������:SqlPro.B��������);
			  if(ss==null||ss.equals("-2")){
			  System.out.println("autoGetPaletToST �Ͽ���ȡ��ʱ,״̬��="+ss);
			  return;}
			  if(ss==null||ss.equals("-1")){
				  System.out.println("autoGetPaletToST ����ͨ�ŶϿ�,״̬��="+ss);
				  return;}
			  
		String sm[]=ss.split("\\|");
			*/
			
		for(int i=1;i<8;i++){
			STContent cot= getWrPLC(machineID).get(i);
			if(cot.firstST.isWrite()){
				  String wuliao=cot.firstST.get���ϱ���()==null?"":cot.firstST.get���ϱ���();
				  if(!wuliao.equals("")){
					  if(machineID==1){	  
			         String sql1=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ����='"+wuliao+"' and ����>0 and ��λ��  between 1 and 28 and ����='"+machineID+"'"); 
					  if(sql1!=null){
						 String sm6[]=sql1.split("!_!") ;
						 if(A���������Զ������){//����⵽ż��λΪ��ʱ�ͷ����»�����
						 SqlTool.add����ָ��(sm6[0], sm6[3],  (gw1[i-1]-1)+"", "�»�", 0, machineID+"");
						 
						 }
						   
					    }
					  }else{
						  String sql1=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ����='"+wuliao+"' and ����>0 and ��λ��  between 1 and 28 and ����='"+2+"'"); 
						  if(sql1!=null){
							 String sm6[]=sql1.split("!_!") ;
							 if(B���������Զ������){
								 //����⵽ż��λΪ��ʱ�ͷ����»�����
							   SqlTool.add����ָ��(sm6[0], sm6[3],  (gw2[i-1]-1)+"", "�»�", 0, 2+"");
							 
							 }
							   
						    }  
						  
					  }
				  }
				
			 }
			
		}
		}catch(Exception ex){ex.printStackTrace();}
			
		
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
	
	public Hashtable<Integer,String> getH����(int װ����){
		 if(װ����==1){
			
			 return ����startTime_A;
		 }else{
			 return ����startTime_B;
			
		 }
		
	}
	
	public Hashtable<String,Long> getTj(int װ����){
		 if(װ����==1){
			
			 return tongji_A;
		 }else{
			 return tongji_B;
			
		 }
		
	}
	
	int modeTh=0;
	//��ȡ����״̬����������������
    public ReST[] getFromPLC(int װ����){
    	try{
    		if(modeTh%30==0){
    			 System.out.println(th.size()+"="+th);
    			//System.out.println("--"+this.getͳ��(װ����));
    		 }
    		
    		
    		modeTh++;
    		Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, װ����);
    	
    		if(r[0].getResInt()==-1)return null;
    		if(r[0].getResInt()==-2)return null;
    		for(int i=15;i>-1;i--){
    			
    			Resint bint=r[i*2];
    			int tem1=bint.getResInt();
    			int tem2=getRePLC(װ����)[i].boolCont.getResInt();
    			
    			int �ؾ߷���1=(tem1&0b100)==4?1:0;
    			int �ؾ߷���2=(tem2&0b100)==4?1:0;
    			int ȡ�����1=(tem1&0b10)==2?1:0;
    			int ȡ�����2=(tem2&0b10)==2?1:0;
    			
    			if(�ؾ߷���1==1){
     			   getH����(װ����).put(i+100, i+"��λ�ؾ߷���1="+�ؾ߷���1+",�ϴ��ؾ߷���="+�ؾ߷���2+" "+new Date().toLocaleString());
     			  }else{
     			   getH����(װ����).remove(i+100); 
     			   }
     			
    			
    			getRePLC(װ����)[i].set�ؾߵ�λ((tem1&0b1)==1?true:false);
    			getRePLC(װ����)[i].set�˹���װ��ģʽ((tem1&0b1000)==8?true:false);
    			getRePLC(װ����)[i].set�ؾ߷���((tem1&0b100)==4?true:false);
    			getRePLC(װ����)[i].set�������((tem1&0b10)==2?true:false);
    			
    			if(ȡ�����1!=ȡ�����2){//ģ��һ���źŸı�
    				//�������̵���������,��ʹ�ؾ�û��λ
    				if(ȡ�����1==1){//ģ��һ��������
    					//��Ȿ��λ��û������
    					
    					
    					if(i<15){//=15û���ؾ��ˣ���ͬ�������ߵĵ�о��������������
    					    
    					  //ȡ�������Ҫ���ܹ���ͬ������
    					   getWrPLC(װ����).get(i).updataDB(getWrPLC(װ����).get(i).firstST);//�������̵�����
    					
    					}
    					
    				    System.out.println("ȡ�����1 firstST in getFromPlc-���ؾ�"+getWrPLC(װ����).get(i).firstST.getName()+"->"+i+"ST");
    				   
    				}
    				
    			}
    			
    			
    			
    			if(�ؾ߷���1!=�ؾ߷���2){

    				//��������λ�ã�ͬʱ��write�ó�false,
    				if(�ؾ߷���1==1){
    					long fTime=System.currentTimeMillis();
    					 getH����(װ����).put(i,"1��:���ܵ�����"+new Date().toLocaleString());
    				if(i<15){
    					if(getWrPLC(װ����).get(i).firstST.is���ݸ������()){
    					  SqlPro.getLog().error("�����붯������ͬʱ���ڣ��������쳣->"+i+"ST");
    					}
    					Carry car=getCarryLine(װ����).getCarry(i);
    					
    					if(car!=null){
    						
    				
    						//�����ƶ��ؾߵ���һ����λ��ͬʱ������һ����λ����Ϣ
    						if(ȡ�����1==1||����⶯�����){
    							
    						 if( getCarryLine(װ����).removeToNext(i)){
    							 
    							 if(i<14){
    								 if(i==0){
    									
    									//pack
    									 if(getTj(װ����).get(car.get������()+"start")==null){
    										getTj(װ����).put(car.get������()+"start", System.currentTimeMillis());
      		    					    
      									   }
    									 
    		    					    }
    								 
    								 
    								 if(i==13){//ģ��
    									
    									////////////////////////////////////////////// 
    									//pack
    									 if(getTj(װ����).get(car.get������()+"start")!=null){
    										 if(getTj(װ����).get(car.get������()+"end")==null){
    											 getTj(װ����).put(car.get������()+"end", System.currentTimeMillis());
    											 getTj(װ����).put(car.get������()+"PACK��ʱ=", getTj(װ����).get(car.get������()+"end")-getTj(װ����).get(car.get������()+"start"));
    											 getTj(װ����).put("���ģPACK����",getTj(װ����).get("���ģPACK����")==null?1:getTj(װ����).get("���ģPACK����")+1);
     		    					        
    										   }
     		    					      }
     		    					    }	
    		    						
    								getWrPLC(װ����).get(i+1).initFromSql(1); 
    								//��������Դ���һ��ģ�鿪ʼ��ʱ�䣬�������ʱ�䡣
    							
    							 }
    						}else{
    							
    							getRePLC(װ����)[i].set�ؾ߷���(false);
    							if(modeTh%15==0){
    								SqlPro.getLog().error("�ؾ��ƶ�ʧ����->"+i+"ST������ؾ��ƶ�ʧ�ܣ��Ͳ������ؾ߷���=true���źţ�ָ��Ҳ�����ƶ���������һ���ؾ��ж�");
    				    		 }
    							
    							continue;
    							
    							
    							
    						}
    							 
    						 }
    						//�����ؾ��ƶ��ж�
    						
    						
    					if(car.getName().equals(getWrPLC(װ����).get(i).firstST.getName())){
    						
    						 if( getWrPLC(װ����).get(i).firstST.getʣ������()!=0){
    							
    							  SqlPro.getLog().error("ץȡ������ȡ�쳣,û����ȡ����ɾ��ƶ��ؾ�->"+i+"ST");
    							
    						  }
    						//�����������Ǳ���λ��Ҫ������
    						 if( getWrPLC(װ����).get(i).firstST.getʣ������()==0||�����ȡ������){
    							 if(ȡ�����1==1||����⶯�����){
    							 System.out.println("�ؾ߷���1");
    							 final long timeS=System.currentTimeMillis();
    							 if(i==5){
    								 
    							   getWrPLC(װ����).get(i).firstST.set���ݸ������(true);
    							   getWrPLC(װ����).get(i).firstST.set���ݴ�����(true);
    							   System.out.println("set���ݴ�����"+getWrPLC(װ����).get(i).firstST.is���ݴ�����());
    								 }else{
    							     getWrPLC(װ����).get(i).firstST.set����λ������־(false);
    							     getWrPLC(װ����).get(i).firstST.setWrite(false);
    							     //���Ȱ�ָ��ŵ���������
    							     getWrPLC(װ����).get(i).initFromSql(0); 
    							     getWrPLC(װ����).get(i).updata����(0);
    							    //����PLC�����ݸ��������
    							     getWrPLC(װ����).get(i).firstST.set���ݸ������(true);
    							    }
    						   // String back=getWrPLC(װ����).get(i).firstST.writeifChangeToPLC();
    							 String back=getWrPLC(װ����).get(i).firstST.writeToPLC();
    							 long endF=System.currentTimeMillis()-fTime;
    							 if(endF>=3000){
    								 SqlPro.getLog().error("��"+i+"��λ�ڽ��ܵ��ؾ߷��к�д�� ���ݸ������ ��ʱ="+endF);  
    								 
    							 }
    						  if(back.contains("�ɹ�")){
    							  //д��PLC�ɹ���,������ٴμ���ؾ߷��б�ΪOFF
    							  System.out.println("1��"+i+"��λ���ݸ�����ɣ����ͳɹ�");
    							  final int curr2=i;
    							 
    		 new Thread(){
    			     final int curr=curr2;
    			 
    								  public void run(){
    									  getH����(װ����).put(curr,getH����(װ����).get(curr)+",2��:���ݸ�����ɱ�ON"+new Date().toLocaleString());
    									  th.put(Thread.currentThread(), װ����+"-װ����"+curr+"��λ�����ݸ������="+getWrPLC(װ����).get(curr).firstST.is���ݸ������());
    									  final long timeS2=timeS;
    									
    									 // System.out.println(Thread.currentThread()+"����1-------------------------------------------------------------------------------------");
    									  while(true){
    									//System.out.println("---====");	  
    						    Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, װ����);	  
    						    Resint bint2=r2[curr*2];
    			    			int tem11=bint2.getResInt();
    			    		    int �ؾ߷���new=(tem11&0b100)==4?1:0;
    			    			
    			    				if(�ؾ߷���new==0){
			    						getRePLC(װ����)[curr].�ؾ߷���=false;
    			    				 
			    					 getH����(װ����).put(curr,getH����(װ����).get(curr)+",3��:���ܵ��ؾ߷���OF"+new Date().toLocaleString());
    			    					  
    			    		if(curr==5){
    			    						//�����5ST���Ȳ�Ҫ��ָ������ƶ����Ƚ��ܵ�������ɺ����ƶ�����
    			    					
    			    				new Thread(){
        	    	    					public void run(){ 
        	    	    							 th.put(Thread.currentThread(),  װ����+"-װ����"+curr2+"��λ�����ݸ������="+getWrPLC(װ����).get(curr2).firstST.is���ݸ������());
        	    	    							 int dd=0;
        	    	    						    while(true)	{
        	    	    						    	try{
        	    	    						    if(dd%5==0)	{ System.out.println("�ȴ���������ź�=================");}
        	    	    						    dd++;
        	    	    						   Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, װ����);	  
        	    	    		    			   Resint bint2=r2[curr*2];
        	    	    		    			   int tem11=bint2.getResInt();
        	    	    		    			   int �������=(tem11&0b100000)==32?1:0; 		
        	    				    if(�������==1){
        	    				    	
        	    				    	/***************************/
        	    				    	getWrPLC(װ����).get(curr).lock();
        	    	    				    getWrPLC(װ����).get(curr).firstST.setWrite(false);	 
        	    	    	    	        getWrPLC(װ����).get(curr).firstST.clear();
        	    	    	    	        getWrPLC(װ����).get(curr).firstST.set���ݸ������(false);
        	    	    			        getWrPLC(װ����).get(curr).initFromSql(0);
        	    	    			        getWrPLC(װ����).get(curr).updata����(0);
        	    	    			    getWrPLC(װ����).get(curr).unLock();
        	    	    			    String back=  getWrPLC(װ����).get(curr).firstST.writeToPLC();
        	    	    	    	  /***************************/
        	    	    	    	  
        	    	    	    	  if(getWrPLC(װ����).get(curr).firstST.is���ݸ������()){
 			    						 SqlPro.getLog().error("��"+curr+"��λд��ʧ�� ���ݱ��false�������̱߳�Ϊtrue"); 
 			    					 }
        	    	    	    	  if(!back.contains("�ɹ�")){
        	    	    	    		  SqlPro.getLog().error("��"+curr+"��λ�ؾ߷���1"+back+"------------------->:д��ʧ��");
        	    	    	    		  System.out.println("��"+curr+"��λ�ؾ߷���1"+back+"------------------->:д��ʧ��");  
        	    	    	    	  }else{ 
        	    	    	    		  getH����(װ����).put(curr,getH����(װ����).get(curr)+",4��:���ݸ�����ɱ�OF"+new Date().toLocaleString());
        	    	    	    		  }
        	    	    	    	  
        	    	    	    	    break;	
                                        }
        	    	    			Thread.sleep(200);	
        	    	    						    		
        	    	    				}catch(Exception eee){ SqlPro.getLog().error("��"+curr+"��λ�ؾ߷���1"+back+"------------------->:д��ʧ��",eee);}
        	    	    						    	
        	    	    					} 
        	    	    				th.remove(Thread.currentThread());			 
        	    	    					}
        	    	    				}.start();
        	    	    					 break;	
    			    						
    			    		}else{
    			    						//��5��λ��
    			    					
    			    		          	/***************************/
    			    		       	getWrPLC(װ����).get(curr).lock();
    			    			           getWrPLC(װ����).get(curr).updata����(0);
    	    	    					   getWrPLC(װ����).get(curr).firstST.set���ݸ������(false);
    	    	    					   String back2=getWrPLC(װ����).get(curr).firstST.writeToPLC();
    	    	    			    getWrPLC(װ����).get(curr).unLock();
    	    	    					 /***************************/		   
    	    	    					   if(getWrPLC(װ����).get(curr).firstST.is���ݸ������()){
    				    						 SqlPro.getLog().error("��"+curr+"��λд��ʧ�� ���ݱ��false�������̱߳�Ϊtrue"); 
    				    					 }
    	    	    					  if(!back2.contains("�ɹ�")){
    	    	    						  SqlPro.getLog().error("��"+curr+"��λ�ؾ߷���1"+back2+"------------------->:д��ʧ��,���ݸ������û��FALSE");
            	    	    	    		  System.out.println("��"+curr+"��λ�ؾ߷���1"+back2+"------------------->:д��ʧ��");  
            	    	    	    	  }else{
            	    	    	    		  getH����(װ����).put(curr,getH����(װ����).get(curr)+",4��:���ݸ�����ɱ�OF"+new Date().toLocaleString());
            	    	    	    	  }
    	    	    					// System.out.println("��"+curr+"��λ�ؾ߷���1"+back+"------------------->:"+(System.currentTimeMillis()-timeS2)+"ms");
    	    	    					 break;
    			    			 }
    			    				
    			    				 }
    			    				
    			    			//}
    			    			try{Thread.sleep(200);}catch(Exception ee){}
    			    			
    			    			}//end while
    									  th.remove(Thread.currentThread());		
    									//  System.out.println(Thread.currentThread()+"����1-------------------------------------------------------------------------------------");
    								  }
    		}.start();
    							  
    	    				
    	    				 
    	    				 
    	    				 
    	    				  }else{
    	    					  SqlPro.getLog().error("1��"+i+"��λ���ݸ�����ɣ�����ʧ�ܣ��������ؾ߷��д���ʧ��,û��������FALSE�߳�");
    	    					  System.out.println("1��"+i+"��λ���ݸ�����ɣ�����ʧ�ܣ��������ؾ߷��д���ʧ��");  
    	    				      }
    						  
    							  } //end �ж�ȡ�����
    						 }
    					}
    			else{//�������ڱ���λû���κ���Ҫ�Ķ��������ж϶�����ɱ�־
    						 
    						System.out.println("�������ڱ���λû���κ���Ҫ�Ķ��������ж϶�����ɱ�־-----------------------------");
    					//����һ��//////////////////////////////////////////////
    					//	getH����(װ����).put(i, System.currentTimeMillis());
    					/*
    						if( getCarryLine(װ����).removeToNext(i)){ 
    							 if(i<14){
    							  getWrPLC(װ����).get(i+1).initFromSql(1); 
    							 
    							 }
    						}
    						*/
    					//////////////////////////////////////////////////	 
    					   final long timeS=System.currentTimeMillis();
    					   getWrPLC(װ����).get(i).firstST.set���ݸ������(true);
    					   getWrPLC(װ����).get(i).firstST.set���ݴ�����(true);
    					   getWrPLC(װ����).get(i).firstST.set����λ������־(false);
   						 
   						   String back=getWrPLC(װ����).get(i).firstST.writeToPLC();
   						   long endF=System.currentTimeMillis()-fTime;
   						   
						 if(endF>=3000){
							  SqlPro.getLog().error("��"+i+"��λ�ڽ��ܵ��ؾ߷��к�д�� ���ݸ������ ��ʱ="+endF);  
							 
						   }
   						    if(back.contains("�ɹ�")){
   							  //д��PLC�ɹ���,������ٴμ���ؾ߷��б�ΪOFF
							 // System.out.println("2��"+i+"��λ���ݸ�����ɣ����ͳɹ�");
							  final int curr2=i;
							//  getWrPLC(װ����).get(i).firstST.updataFromPLC();
							//  System.out.println(i+"��λ�����ݸ������="+getWrPLC(װ����).get(i).firstST.is���ݸ������());
							 // try{Thread.sleep(500);}catch(Exception ee){}
					new Thread(){
						 final int curr=curr2;
								  public void run(){
									  getH����(װ����).put(curr,getH����(װ����).get(curr)+",2��:���ݸ�����ɱ�ON"+new Date().toLocaleString());
									  th.put(Thread.currentThread(),  װ����+"-װ����"+curr+"��λ�����ݸ������="+getWrPLC(װ����).get(curr).firstST.is���ݸ������());
									
									  final long timeS2=timeS;
									 // System.out.println(Thread.currentThread()+"����2-------------------------------------------------------------------------------------");
									  while(true){
										  
						    Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, װ����);	  
						    Resint bint2=r2[curr*2];
			    			int tem11=bint2.getResInt();
			    			//int tem22=getRePLC(װ����)[curr].boolCont.getResInt();
			    			
			    			int �ؾ߷���new=(tem11&0b100)==4?1:0; 
			    			//int �ؾ߷���old=(tem22&0b100)==4?1:0;
			    			//if(�ؾ߷���!=�ؾ߷���old){
			    				if(�ؾ߷���new==0){
			    					getRePLC(װ����)[curr].�ؾ߷���=false;
			    					getH����(װ����).put(curr,getH����(װ����).get(curr)+",3��:���ܵ��ؾ߷���OF"+new Date().toLocaleString());
			    					
			    					 
			    				// if( getCarryLine(װ����).removeToNext(curr)){
			    					  //����������
			    					/***************************/
			    					getWrPLC(װ����).get(curr).lock();
			    					getWrPLC(װ����).get(curr).firstST.set���ݴ�����(false);
			    					getWrPLC(װ����).get(curr).firstST.set���ݸ������(false);
			    					getWrPLC(װ����).get(curr).unLock();
			    					 String back3= getWrPLC(װ����).get(curr).firstST.writeToPLC();
			    					/***************************/
			    					 if(getWrPLC(װ����).get(curr).firstST.is���ݸ������()){
			    						 SqlPro.getLog().error("��"+curr+"��λд��ʧ�� ���ݱ��false�������̱߳�Ϊtrue"); 
			    					 }
			    					if(!back3.contains("�ɹ�")){
			    						 SqlPro.getLog().error("��"+curr+"��λ�ؾ߷���1"+back3+"------------------->:д��ʧ�� ���ݸ������û��FALSE");
        	    	    	    		 System.out.println("��"+curr+"��λ�ؾ߷���1"+back3+"------------------->:д��ʧ��");  
        	    	    	    	  }else{
        	    	    	    		 getH����(װ����).put(curr,getH����(װ����).get(curr)+",4��:���ݸ�����ɱ�OF"+new Date().toLocaleString());	  
        	    	    	    		  
        	    	    	    	  }
			    				
			    				 
			    				 break;
			    				 }
			    				
			    			//}
			    			try{Thread.sleep(200);}catch(Exception ee){}
			    			 
			    			}//end while
									  th.remove(Thread.currentThread());
									//  System.out.println(Thread.currentThread()+"����2-------------------------------------------------------------------------------------");
								  }
				}.start(); 
   	    				  }
   						    else{
   						     SqlPro.getLog().error("2��"+i+"��λ���ݸ�����ɣ�����ʧ�ܣ���������û��������FALSE�߳�");
   	    					 System.out.println("2��"+i+"��λ���ݸ�����ɣ�����ʧ�ܣ���������"); 
   	    					  
   	    				      }
    						
    	    				  
    					}
    					
    					}
    				
    			       }
    				/////////////////////
    				if(i==15){
    					System.out.println("ͬ���������ؾ߷���-------------��");
    					final long timeS=System.currentTimeMillis();
    					 //getWrPLC(װ����).get(i).lock();////////////////////
    					 getWrPLC(װ����).get(i).firstST.set����λ������־(false);
					     getWrPLC(װ����).get(i).firstST.setWrite(false);
					     //���Ȱ�ָ��ŵ���������
					     getWrPLC(װ����).get(i).initFromSql(0); 
					     //getWrPLC(װ����).get(i).updata����(0);
					    //����PLC�����ݸ��������
					     getWrPLC(װ����).get(i).firstST.set���ݸ������(true);
					     //getWrPLC(װ����).get(i).unLock();///////////////
    					/*getWrPLC(װ����).get(i).lock();
 					    getWrPLC(װ����).get(i).firstST.set���ݸ������(true);
 					    getWrPLC(װ����).get(i).firstST.set����λ������־(false);
 					    getWrPLC(װ����).get(i).unLock();*/
					    String back=getWrPLC(װ����).get(i).firstST.writeToPLC();
						  // System.out.println("ͬ��������--��λ�ؾ߷���"+back+":"+(System.currentTimeMillis()-timeS)+"ms");
						    if(back.contains("�ɹ�")){
							 
						    	 final int curr2=i;
							
							  new Thread(){
								  final int curr=curr2; 
								  public void run(){
									  th.put(Thread.currentThread(),  װ����+"-װ����"+curr+"��λ�����ݸ������="+getWrPLC(װ����).get(curr).firstST.is���ݸ������());
									  getH����(װ����).put(curr,getH����(װ����).get(curr)+",2��:���ݸ�����ɱ�ON"+new Date().toLocaleString());
									  
									  final long timeS2=timeS;
									  //System.out.println(Thread.currentThread()+"����15-------------------------------------------------------------------------------------");
									  while(true){
										  
						    Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, װ����);	  
						    Resint bint2=r2[curr*2];
			    			int tem11=bint2.getResInt();
			    			int tem22=getRePLC(װ����)[curr].boolCont.getResInt();
			    			
			    			int �ؾ߷���new=(tem11&0b100)==4?1:0; 
			    			int �ؾ߷���old=(tem22&0b100)==4?1:0;
			    			//if(�ؾ߷���!=�ؾ߷���old){
			    				if(�ؾ߷���new==0){
			    					/***************************/
			    					getH����(װ����).put(curr,getH����(װ����).get(curr)+",3��:���ܵ��ؾ߷���OF"+new Date().toLocaleString());
			    					 getRePLC(װ����)[curr].�ؾ߷���=false;
			    					 getWrPLC(װ����).get(curr).lock();
			    					 /*getWrPLC(װ����).get(curr).firstST.set���ݸ������(false);
			    					 getWrPLC(װ����).get(curr).firstST.clear();
			    					 getWrPLC(װ����).get(curr).initFromSql(0);
			    					 */
			    					 getWrPLC(װ����).get(curr).firstST.set���ݴ�����(false);
				    				 getWrPLC(װ����).get(curr).firstST.set���ݸ������(false);
				    				 getWrPLC(װ����).get(curr).unLock();
			    					 String back5= getWrPLC(װ����).get(curr).firstST.writeToPLC();
			    					 /***************************/ 
	    	    					if(!back5.equals("�ɹ�")){
	    	    						
	    	    						SqlPro.getLog().error("ͬ��������"+curr+"��λ�ؾ߷��к����ݸ�����ɱ��false"+back5+"------>:ʧ��");
	    	    					}else{
	    	    						getH����(װ����).put(curr,getH����(װ����).get(curr)+",4��:���ݸ�����ɱ�OF"+new Date().toLocaleString());	 	
	    	    						
	    	    					 }
	    	    			            break;
			    				 }
			    				
			    			//}
			    			try{Thread.sleep(200);}catch(Exception ee){}
			    			 
			    			}//end while
									  th.remove(Thread.currentThread());
									 // System.out.println(Thread.currentThread()+"����15-------------------------------------------------------------------------------------");
								  }
							  }.start();
	    				  }else{
	    					  SqlPro.getLog().error("2��"+i+"��λ���ݸ�����ɣ�����ʧ�ܣ���������û��������FALSE�߳�");  
	    					  
	    				  }	
    					
    				   }
    				////////////////////////
    				
    				
    				
    				}//end �ؾ߷���=1
    				
    				
    			}
    	
    			
    		}
    		
    		//���µڸ���������
    		for(int i=31;i>15;i--){ 
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
    				System.out.println("ȡ�����2---secendST in getFromPlc");
    				//SqlPro.getLog().error("ȡ�����2 secendST in getFromPlc-���ؾ�"+getWrPLC(װ����).get(i).firstST.getName()+"->"+(i-16)+"ST");
    				if(ȡ�����1==1){
    					 int ʣ������=getWrPLC(װ����).get(i-16).firstST.getʣ������();
    					// if(getCarryLine(װ����).getCarry(i-16)!=null)
    					if(ʣ������!=0){//˵���ڶ��������Ѿ��Ƶ���һ��������,��Ϊ����ִ�����һ�����к����ִ�еڶ�������
    					   getWrPLC(װ����).get(i-16).firstST.set���ݴ�����(true);
    					   getWrPLC(װ����).get(i-16).updataDB(getWrPLC(װ����).get(i-16).firstST);//�������̵�����
    					  
    					   }else{
    						   getWrPLC(װ����).get(i-16).secondST.set���ݴ�����(true);  
    					       getWrPLC(װ����).get(i-16).updataDB(getWrPLC(װ����).get(i-16).secondST);//�������̵�����
    					    
    					   }
    					
    				}
    				
    			}
    			
    			
    		}
    		
    	
    	
    	}catch(Exception ex){
    		SqlPro.getLog().error(ex.getMessage(),ex);
    		ex.printStackTrace();}
    	
    	
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
	   if(i>-1){
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
	   String �˹���="60001";
	   if(line==2)�˹���="60002";
	   String AGV��="60003";
	   if(line==2)AGV��="60004";
	   boolean ��λ1=false; boolean ��λ2=false;
	   try{
		 
		  
	  if(st>=2&&st<=8){
	   if(line==1){
		   
		   String smm[]=getSTATE1().split("\\|"); 
		   if(smm.length<2){
			   getH����(1).put(100088, "��ȡ����1״̬�쳣,ʱ��="+new Date().toLocaleString()+"����="+getSTATE1());
			   SqlPro.getLog().error("��ȡ����״̬�쳣����1250��!"+getSTATE1());  
			    return false;
		   }
		   getH����(1).remove(100088);
		   if(smm[7-(st-2)-1].split("=")[1].equals("0")){
			  
			   STC1.get(st-1).firstST.set����RDY(false);
			   STC1.get(st-1).secondST.set����RDY(false);
			   ��λ1=false;
			   //return false;  
		   }else{
			  
			   Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, line);
			   Resint bint=r[(st-1)*2];
			   Resint bint2=r[(st-1)*2+32];
   			   int tem1=bint.getResInt();
   			   int ���̻ش��=(tem1&0b10000)==16?1:0;//PLC�������̻ؿ���Ϣ
   			   int tem2=bint2.getResInt();
			   int ���̻ش��2=(tem2&0b10000)==16?1:0;//�ڶ�����PLC�������̻ؿ���Ϣ
   			   //���жϵ�һ����
			  // System.out.println("���̻ش��/���̻ش��2------------------"+���̻ش��+"/"+���̻ش��2);
   			   if(���̻ش��==1||���̻ش��2==1){//�����̳���
   				String sb=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ��λ��='"+gw1[st-2]+"'");
   				if(sb!=null){
   					String sm[]=sb.split("!_!");
   					if(A���������Զ������){
   				    String bb=SqlTool.add����ָ��(sm[0], sm[3], �˹���, "�����߻���", 1, line+"");
   				    String bb2=SqlTool.insert(new String[]{"UPDATE ������� set ����=0 where ���̱��='"+sm[0]+"'"});
   				    }
   				   
   				    return false; 
   				    }
   			      }else{
   			    	  
			        ��λ1=true;
			      }
			  
   			   
   			 
			     
		   }
		   
		   
		   
	   }else{
		   String smm[]=getSTATE2().split("\\|");
		   if(smm.length<2){
			   getH����(2).put(100088, "��ȡ����2״̬�쳣,ʱ��="+new Date().toLocaleString()+"����="+getSTATE2());
			   SqlPro.getLog().error("��ȡ����״̬�쳣����1288��!"+getSTATE2());  
			    return false;
		   }
		   getH����(2).remove(100088);
		   if(smm[7-(st-2)-1].split("=")[1].equals("0")){
			   STC2.get(st-1).firstST.set����RDY(false);
			   STC2.get(st-1).secondST.set����RDY(false);
			   ��λ2=false;
			  // return false;  
		   }else{
			   Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, line);
			   Resint bint=r[(st-1)*2];
			   Resint bint2=r[(st-1)*2+32];
 			   int tem1=bint.getResInt();
 			   int ���̻ش��=(tem1&0b10000)==16?1:0;//PLC�������̻ؿ���Ϣ
 			   int tem2=bint2.getResInt();
			   int ���̻ش��2=(tem2&0b10000)==16?1:0;//�ڶ�����PLC�������̻ؿ���Ϣ
   			   
   			   if(���̻ش��==1||���̻ش��2==1){//�����̳���
   				String sb=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ��λ��='"+gw2[st-2]+"'");
   				if(sb!=null){
   					String sm[]=sb.split("!_!");
   					if(B���������Զ������){
   				    String bb=SqlTool.add����ָ��(sm[0], sm[3], �˹���, "�����߻���", 1, line+"");
   				    String bb2=SqlTool.insert(new String[]{"UPDATE ������� set ����=0 where ���̱��='"+sm[0]+"'"});
   				    }
   				  
   				    // STC2.get(st-1).firstST.set����RDY(false);
				    // STC2.get(st-1).secondST.set����RDY(false); 
   				    return false; 
   				    
   				    }
   			      }else{
   			    	  
			        ��λ2=true;
			      }
			   
			   
		    }
		   
	   }
	   }
	 //����ʱ��ס�˴��ͺ� 
	  if(A�������ߵ�λ����)
	    ��λ1=true;  
	  if(B�������ߵ�λ����)
	    ��λ2=true;
	   
	 
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
		  
		  if(tem2==null){//û�����̵����,����������
			  cot.firstST.set����RDY(false);
			  cot.secondST.set����RDY(false);
			  
			  String wuliao=cot.firstST.get���ϱ���()==null?"":cot.firstST.get���ϱ���();
			  if(!wuliao.equals("")){
				  if(line==1){	  
		         String sql1=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ����='"+wuliao+"' and ����>0 and ��λ��  between 1 and 28 and ����='"+line+"'"); 
				  if(sql1!=null){
					 String sm6[]=sql1.split("!_!") ;
					 if(A���������Զ������){//����⵽ż��λΪ��ʱ�ͷ����»�����
					 SqlTool.add����ָ��(sm6[0], sm6[3],  (gw1[st-2]-1)+"", "�»�", 0, line+"");
					 
					 }
					   
				    }
				  }else{
					  String sql1=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ����='"+wuliao+"' and ����>0 and ��λ��  between 1 and 28 and ����='"+2+"'"); 
					  if(sql1!=null){
						 String sm6[]=sql1.split("!_!") ;
						 if(B���������Զ������){
							 //����⵽ż��λΪ��ʱ�ͷ����»�����
						 SqlTool.add����ָ��(sm6[0], sm6[3],  (gw2[st-2]-1)+"", "�»�", 0, line+"");
						 
						 }
						   
					    }  
					  
				  }
			  }
			  //�����������������
			  
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
		    		   if((line==1?A���������Զ������:B���������Զ������)){
		    		   SqlTool.add����ָ��(sm[0], sm[3], �˹���, "�����߻���", 1, line+"");
		    		   System.out.println("Ҫȡ�Ĳ�����------������="+sm[1]+"=="+tem.split("!_!")[0]);
		    		   }
		    		   
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
		    	   if(tpshul>������ֵ){//�ػ���
		    		   if((line==1?A���������Զ������:B���������Զ������)){
		    	     String b=SqlTool.add����ָ��(sm[0], sm[3], AGV��, "�����߻���", 0, line+"");
		    	     System.out.println("��Ϊ�������ϲ��Ǳ���λҪ������------���ص�����"+sm[1]+"=="+tem.split("!_!")[0]+"->"+b);
		    		   }
		    	     }else{//�ش��
		    	    	 if((line==1?A���������Զ������:B���������Զ������)){
		    	     String b=SqlTool.add����ָ��(sm[0], sm[3], �˹���, "�����߻���", 1, line+"");	 
		    	     System.out.println("��Ϊ��������<3�Ҳ��Ǳ���λҪ������------���ص����"+sm[1]+"=="+tem.split("!_!")[0]+"->"+b);
		    	    	 }
		    	     }
		    	   
		    	   cot.firstST.set����RDY(false);
		    	   return false;  
		       }
		    }
			   else{//�����һ������ȡ���ˣ����жϵڶ�������	 
				   if(��������==�������&&��������!=0)
				    cot.firstST.set����RDY(false);//��һ������ȡ���˰�����READY=false;
				   
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
						   if((line==1?A���������Զ������:B���������Զ������)){
						   SqlTool.add����ָ��(sm[0], sm[3], �˹���, "�����߻���", 1, line+"");  
						   }
						   return false;
						   }else{
							   
							   if(line==1){  
								   if(��������2==�������2&&��������2!=0){
									   cot.secondST.set����RDY(false);  
									   return false;
								   }else{
								    cot.secondST.set����RDY(true&&��λ1);  
								    return true&&��λ1;}
								    
							   }
									else{
										 if(��������2==�������2&&��������2!=0){
											 cot.secondST.set����RDY(false);
											 return false;
										   }else{
										    cot.secondST.set����RDY(true&&��λ2);  
										    return true&&��λ2;}
								        }
							   }
					   
				   }
				        else{
			    	      //�����̻���  
				        cot.secondST.set����RDY(false);
				        if(tpshul>������ֵ){//�ػ���
				        	 if((line==1?A���������Զ������:B���������Զ������))
				    	     SqlTool.add����ָ��(sm[0], sm[3], AGV��, "�����߻���", 0, line+"");
				    	     //System.out.println("2��Ϊ�������ϲ��Ǳ���λҪ������------���ص�����"+sm[1]+"=="+tem.split("!_!")[0]);
				    	     }else{//�ش��
				    	    	 if((line==1?A���������Զ������:B���������Զ������))
				    	     SqlTool.add����ָ��(sm[0], sm[3], �˹���, "�����߻���", 1, line+"");	 
				    	     //System.out.println("2��Ϊ��������<3�Ҳ��Ǳ���λҪ������------���ص����"+sm[1]+"=="+tem.split("!_!")[0]);
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
			         String sql1=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ����='"+wuliao+"' and ����>0 and ��λ��  between 1 and 28 and ����='"+line+"'"); 
					  if(sql1!=null){
						 String sm6[]=sql1.split("!_!") ;
						 if((line==1?A���������Զ������:B���������Զ������)){
						  SqlTool.add����ָ��(sm6[0], sm6[3],  (gw1[st-2]-1)+"", "�»�", 0, line+"");
						  }
						   
					    }
					  }else{
						  String sql1=SqlTool.findOneRecord("select  ���̱��,����,����,��λ��  from �������   where  ����='"+wuliao+"' and ����>0 and ��λ��  between 1 and 28 and ����='"+2+"'"); 
						  if(sql1!=null){
							 String sm6[]=sql1.split("!_!") ;
							 if((line==1?A���������Զ������:B���������Զ������)){
							 SqlTool.add����ָ��(sm6[0], sm6[3],  (gw2[st-2]-1)+"", "�»�", 0, line+"");
							 }
							   
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
			    		   if((line==1?A���������Զ������:B���������Զ������))
			    		   SqlTool.add����ָ��(sm[0], sm[3], �˹���, "�����߻���", 1, line+"");  
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
			    	   
			    	   if(tpshul>������ֵ){//�ػ���
			    		     if((line==1?A���������Զ������:B���������Զ������))
				    	     SqlTool.add����ָ��(sm[0], sm[3], AGV��, "�����߻���", 0, line+"");
				    	     }else{//�ش��
				    	    	 if((line==1?A���������Զ������:B���������Զ������))
				    	     SqlTool.add����ָ��(sm[0], sm[3], �˹���, "�����߻���", 1, line+"");	 
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
							   if((line==1?A���������Զ������:B���������Զ������))
							   SqlTool.add����ָ��(sm[0], sm[3], �˹���, "�����߻���", 1, line+"");  
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
					        	 if(tpshul>������ֵ){//�ػ���
					        		 if((line==1?A���������Զ������:B���������Զ������))
						    	     SqlTool.add����ָ��(sm[0], sm[3], AGV��, "�����߻���", 0, line+"");
						    	     }else{//�ش��
						    	    	if((line==1?A���������Զ������:B���������Զ������))
						    	     SqlTool.add����ָ��(sm[0], sm[3], �˹���, "�����߻���", 1, line+"");	 
						    	     }
					    	 return false;  
					       }
					   
				   }
			       
			       
			  } 
			      
		   
	   }
	   
	   if(st>=2&&st<=8){//��ʼ�ж�����λ�����������ʼ�հ�����λ����
		   
		   
	   }  
	   
	  //����̨��8��λ��ʼ,����RDY��Զ��TRUE 
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
	   
	   
	      return true;
	   
	   }catch(Exception ex){
		  // SqlPro.getLog().error(ex.getMessage());
		  SqlPro.getLog().error("error",ex);
		   ex.printStackTrace();}
	   
	 
	   
	   
	    
	     return false;
	   
   }
   
   
    ReST first=new ReST(new Resint());
	ReST second=new ReST(new Resint());
	Hashtable table=new Hashtable();
	String LOCK3="";
	public Hashtable  getDataFromGD(int ST,int line){
		if(!this.getConcent(line)){return table;}
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
					// table.put("ID", first.GET)
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
		if(!this.getConcent(line)){return "�����쳣";}
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

	
	public void reLoad(int machineID){
		System.out.println("=+clear plc  ========================================");
		if(machineID==1){
			for(int i=0;i<STC1.size();i++)
			{   STC1.get(i).unLock();
				STC1.get(i).firstST.clear();
				STC1.get(i).secondST.clear();
				STC1.get(i).firstST.writeToPLC();
				STC1.get(i).secondST.writeToPLC();
				
				
			}

			/*tongji.put("�շ��д���");tongji.put("�շ���ʱ��");tongji.put("�ϴοշ�����ʱ");
			 *tongji.put("�ж������д���");tongji.put("�ж�������ʱ��");tongji.put("�ϴ��ж���������ʱ");
			 *tongji.put("���ģ������");tongji.put("���ģPACK����");
			 *
			 * 
			 * */
			//tongji.get("")
			 Vector t=new Vector();
			 Enumeration<String> en1=th.keys();
			 while(en1.hasMoreElements()){
				 Object key=en1.nextElement();
				 Object val=(th.get(key)==null?"":th.get(key));
				 if(val.toString().contains("1-װ����")){
					 ((Thread)key).stop();
					  t.addElement(key);
				 }
			 }
			 
			 try{
			 for(int i=0;i<t.size();i++){
				  th.remove(t.get(i));
			     }
			 }catch(Exception ee){}
			 
		     }else{
		    	 
		    	 for(int i=0;i<STC2.size();i++)
					{   STC2.get(i).unLock();
						STC2.get(i).firstST.clear();
						STC2.get(i).secondST.clear();
						STC2.get(i).firstST.writeToPLC();
						STC2.get(i).secondST.writeToPLC();
					}	
		    	 Vector t=new Vector();
		    	 Enumeration<String> en2=th.keys();
				 while(en2.hasMoreElements()){
					 Object key=en2.nextElement();
					 Object val=(th.get(key)==null?"":th.get(key));
					 if(val.toString().contains("2-װ����")){
						 ((Thread)key).stop();
						  t.addElement(key);
						 
					 }
				 }
				 try{
				 for(int i=0;i<t.size();i++){
					  th.remove(t.get(i));
					 
				 }
				 }catch(Exception ee){}
		     }
		
		getH����(machineID).clear();
		
		Hashtable<String ,Long> m= getTj(machineID);
		Vector<String> t=new Vector<String>();
	    Enumeration<String> en=m.keys();
	    while(en.hasMoreElements()){
	    	String o=en.nextElement();
	    	if(o.contains("start")||o.contains("end")){
	    		t.addElement(o);
	    	}
	    }
		for(int i=0;i<t.size();i++){m.remove(t.get(i));}
		t=null;
		}
	
	public List<String> getͳ��(int machineID){
		 List<String> list = new ArrayList<String>();
		
		/*   Hashtable<String,Long> h=(Hashtable<String,Long>)getTj(machineID).clone();
		
		  
		   Enumeration<String> en=h.keys();
		   
		   while(en.hasMoreElements()){
		    	String o=en.nextElement(); 
		    	long time=h.get(o);
		    	if(time>1000000000){
		    		list.add(o+"="+new Date(time).toLocaleString());
		    	}else{
		    	list.add(o+"="+(h.get(o)==null?"�ȴ�ͳ���С�����":h.get(o)+""));}
		    	
		       }
		
		   h=null;*/
		 
		 Hashtable<Integer,String> h2=(Hashtable<Integer,String>)getH����(machineID);
			
		  for(int i=0;i<16;i++){
		    
		    	String s=h2.get(i);
		    	String s2=h2.get(i+100);
		    	if(s2!=null){
		    		list.add(0,"<font color=red>"+i+"ST->"+s2+"</font>");
		    	}
		    	if(s!=null){
		    		if(s.contains("4��")){
		    			list.add(i+"ST->"+s);
		    		}else{
		    			list.add(0,"<font color=red>"+i+"ST->"+s+"</font>");	
		    		}
		    	}
		       }
		
		  if(h2.get(100088)!=null){
			     list.add(0,"<font color=red>"+h2.get(100088)+"</font>");	  
		  }
		   Hashtable<String,Long>   h=getTj(machineID);
		 // Collections.sort(list);
		   
		   
		  Hashtable<String ,String> thh= ClientSer.th;
		 
			  if(thh.get(machineID+"�������")!=null){
				  if(thh.get(machineID+"�������").contains("�쳣"))
				  list.add(0,"<font color=red>"+thh.get(machineID+"�������")+"</font>");  
				  else {
				   if(thh.get(machineID+"�������").contains("���ʿ�ʼ"))	
				   {  
				   list.add(0,"<font color=blue>"+thh.get(machineID+"�������")+"</font>"); 
				  }
				   else{
				   list.add(0,thh.get(machineID+"�������"));   
				        }
				  
				       } 	  
				  }
			  
			  if(thh.get(machineID+"PLC��")!=null){
				  if(thh.get(machineID+"PLC��").contains("�쳣")) 
				  list.add(0,"<font color=red>"+thh.get(machineID+"PLC��")+"</font>");  
				  else{ 
					  if(thh.get(machineID+"PLC��").contains("���ʿ�ʼ")){
				           list.add(0,"<font color=blue>"+thh.get(machineID+"PLC��")+"</font>"); 
				           }
					  else{
						   list.add(0,thh.get(machineID+"PLC��"));
				        }
				   
				     }	  
				  }
			  
			  if(thh.get(machineID+"PLCд")!=null){
				  if(thh.get(machineID+"PLCд").contains("�쳣")) 
				  list.add(0,"<font color=red>"+thh.get(machineID+"PLCд")+"</font>"); 
				  else{
					  if(thh.get(machineID+"PLCд").contains("���ʿ�ʼ")) {  
				         list.add(0,"<font color=blue>"+thh.get(machineID+"PLCд")+"</font>"); 
				      
					     }else{
					     list.add(0,thh.get(machineID+"PLCд"));  
				    	  
				              }
				  
				   }  
				  }
			 
			 list.add(0,"<font color=blue>PLC1���ʿ�ʼ,д��ȴ����أ���ǰ��ʼ��ַ=D11999,�ӿڵȴ���=1</font>"); 
		  
		 list.add(0, "���PACK����="+(h.get("���ģPACK����")==null?"�ȴ�ͳ���С�����":h.get("���ģPACK����")+""));
		  
		   return list; 
	}
	
	
	public String stop(){
		 Vector v=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ״̬<>'�Ŷ�'  order by idEvent");
		   if(v.size()>0){
			   return "�Ѷ������δִ����������ȴ��Ѷ��ִ��������ڹر�ϵͳ";
		   }
		   else{
			  SqlPro.is������=false; 
			  SqlPro.isָ�����=false;
			  stop1=true;
			  stop2=true;
			   
		   }
		return "�ɹ�";
	}
}
