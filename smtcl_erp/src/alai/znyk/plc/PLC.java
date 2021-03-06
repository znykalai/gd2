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
import alai.znyk.kufang.KuFang;
import alai.znyk.server.SqlTool;

public class PLC implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean A区输送线自动请求打开=false;
	public boolean B区输送线自动请求打开=false;
	public boolean A区输送线到位常有=false;
	public boolean B区输送线到位常有=false;
	public boolean 不检测取料数量=true;
	public transient boolean 打开交换记录=false;
	protected transient long lastConcnetA=0;
	protected transient long lastConcnetB=0;
	protected transient Hashtable th=new Hashtable();
	protected transient Hashtable<Integer,String> 放行startTime_A=new Hashtable<Integer,String>();
	protected transient Hashtable<String,Long> tongji_A=new Hashtable<String,Long>();
	
	protected transient Hashtable<Integer,String> 放行startTime_B=new Hashtable<Integer,String>();
	protected transient Hashtable<String,Long> tongji_B=new Hashtable<String,Long>();
	/*tongji.put("空放行次数");tongji.put("空放行时间");tongji.put("上次空放行用时");
	 *tongji.put("有动作放行次数");tongji.put("有动作放行时间");tongji.put("上次有动作放行用时");
	 *tongji.put("完成模组数量");tongji.put("完成模PACK数量");
	 *
	 * 
	 * */
	public int 回流阀值=5;
	public boolean is不检测取料数量() {
		return 不检测取料数量;
	}
	public void set不检测取料数量(boolean 不检测取料数量) {
		this.不检测取料数量 = 不检测取料数量;
	}
	public boolean is不检测动作完成() { 
		return 不检测动作完成;
	}
	public void set不检测动作完成(boolean 不检测动作完成) {
		this.不检测动作完成 = 不检测动作完成;
	}
	public Hashtable getTH(){return th;}
    
	public boolean 不检测动作完成=true;
	public boolean stop1=true;//停止调度系统从数据库里面读取数据
	public boolean stop2=true;//停止调度系统从数据库里面读取数据
	
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
			//第2个缓存队列
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
			//第2个缓存队列
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
			// String ss= INSTANCE.STC1.get(3).firstST.get物料编码();
			// System.out.println(ss);
			  stop1=INSTANCE.stop1;
			  stop2=INSTANCE.stop2;
			  不检测取料数量=INSTANCE.不检测取料数量;
			  不检测动作完成=INSTANCE.不检测动作完成;
			 
			  A区输送线自动请求打开=INSTANCE.A区输送线自动请求打开;
			  B区输送线自动请求打开=INSTANCE.B区输送线自动请求打开;
			  A区输送线到位常有=INSTANCE.A区输送线到位常有;
			  B区输送线到位常有=INSTANCE.B区输送线到位常有; 
			  System.out.println("A区输送线自动请求打开="+A区输送线自动请求打开);
			  
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
			System.out.println("PLC启动---------------");
			INSTANCE.startTiaodu();
			INSTANCE.startTiaodu2();
			INSTANCE.sendHert(1);
			INSTANCE.sendHert(2);//第2区域
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
		if(System.currentTimeMillis()-getLastConcent(machineID)<1500) return true;
		  else
		  return false; 
	}
	
	public void startTiaodu(){ 
		
		System.out.println("启动16个工位调度。。。。");
		new Thread(){
			public void run(){ 
			while(true){
				
				long time1=System.currentTimeMillis();
				try{
					setSTATE1();
					setSTATE2();
				}catch(Exception e){e.printStackTrace();}
				//System.out.println("立库通信时间="+(System.currentTimeMillis()-time1)+"MS");
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
				//System.out.println("14个工位数据库处理时间="+(System.currentTimeMillis()-time1)+"MS");
				getSTRdy(1,2);
				getSTRdy(1,3);
				getSTRdy(1,4);
				getSTRdy(1,5);
				getSTRdy(1,6);
				getSTRdy(1,7);
				getSTRdy(1,8);
				if(!stop1){
				
				getFromPLC(1);//必须写在最后
				
				}
				long en=System.currentTimeMillis()-time1;
				if(en>1000){
		    	SqlPro.getLog().error("A区15工位PLC通信与处理时间="+en+"MS");
				System.out.println("A区PLC通信与处理时间="+en+"MS");
				}
				writeO();
			//	System.out.println("A区处理15个工位一个周期用时="+(System.currentTimeMillis()-time1)+"MS");
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
				getFromPLC(2);//必须写在最后
				long en=System.currentTimeMillis()-time1;
				if(en>1000){
				
				SqlPro.getLog().error("B区15工位PLC通信与处理时间="+en+"MS");
				}
				//System.out.println("B区处理15个工位一个周期用时="+(System.currentTimeMillis()-time1)+"MS");
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
	   //自动把奇数货位填满	
	public void autoGetPaletToST(int machineID){
		new Thread(){public void run(){
		while(true){
			 try{
			      Thread.sleep(1000);
			 }catch(Exception ex){}
			if(machineID==1&&!A区输送线自动请求打开)continue;
			if(machineID==2&&!B区输送线自动请求打开)continue;
			
			try{
			/*	
			  String ss= ClientSer.getIntance().getState(machineID==1?SqlPro.A区输送线:SqlPro.B区输送线);
			  if(ss==null||ss.equals("-2")){
			  System.out.println("autoGetPaletToST 断开读取超时,状态码="+ss);
			  return;}
			  if(ss==null||ss.equals("-1")){
				  System.out.println("autoGetPaletToST 立库通信断开,状态码="+ss);
				  return;}
			  
		String sm[]=ss.split("\\|");
			*/
			
		for(int i=1;i<8;i++){
			STContent cot= getWrPLC(machineID).get(i);
			if(cot.firstST.isWrite()){
				  String wuliao=cot.firstST.get物料编码()==null?"":cot.firstST.get物料编码();
				  if(!wuliao.equals("")){
					  if(machineID==1){	  
			         String sql1=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  物料='"+wuliao+"' and 数量>0 and 货位号  between 1 and 28 and 方向='"+machineID+"'"); 
					  if(sql1!=null){
						 String sm6[]=sql1.split("!_!") ;
						 if(A区输送线自动请求打开){//当检测到偶数位为空时就发出下货请求
						 SqlTool.add动作指令(sm6[0], sm6[3],  (gw1[i-1]-1)+"", "下货", 0, machineID+"");
						 
						 }
						   
					    }
					  }else{
						  String sql1=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  物料='"+wuliao+"' and 数量>0 and 货位号  between 1 and 28 and 方向='"+2+"'"); 
						  if(sql1!=null){
							 String sm6[]=sql1.split("!_!") ;
							 if(B区输送线自动请求打开){
								 //当检测到偶数位为空时就发出下货请求
							   SqlTool.add动作指令(sm6[0], sm6[3],  (gw2[i-1]-1)+"", "下货", 0, 2+"");
							 
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
	
	public Hashtable<Integer,String> getH放行(int 装配区){
		 if(装配区==1){
			
			 return 放行startTime_A;
		 }else{
			 return 放行startTime_B;
			
		 }
		
	}
	
	public Hashtable<String,Long> getTj(int 装配区){
		 if(装配区==1){
			
			 return tongji_A;
		 }else{
			 return tongji_B;
			
		 }
		
	}
	private boolean getStop(int machineID){
		if(machineID==1)return stop1;else return stop2;
	  }
	int modeTh=0;
	//读取光大的状态，并更新托盘数量
    public ReST[] getFromPLC(int 装配区){
    	try{
    		if(modeTh%30==0){
    			 System.out.println(th.size()+"="+th);
    			//System.out.println("--"+this.get统计(装配区));
    		 }
    		
    		
    		modeTh++;
    		Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, 装配区);
    	
    		if(r[0].getResInt()==-1)return null;
    		if(r[0].getResInt()==-2)return null;
    		for(int i=15;i>-1;i--){
    			
    			Resint bint=r[i*2];
    			int tem1=bint.getResInt();
    			int tem2=getRePLC(装配区)[i].boolCont.getResInt();
    			
    			int 载具放行1=(tem1&0b100)==4?1:0;
    			int 载具放行2=(tem2&0b100)==4?1:0;
    			int 取料完成1=(tem1&0b10)==2?1:0;
    			int 取料完成2=(tem2&0b10)==2?1:0;
    			
    			if(载具放行1==1){
     			   getH放行(装配区).put(i+100, i+"工位载具放行1="+载具放行1+",上次载具放行="+载具放行2+" "+new Date().toLocaleString());
     			  }else{
     			   getH放行(装配区).remove(i+100); 
     			   }
     			
    			
    			getRePLC(装配区)[i].set载具到位((tem1&0b1)==1?true:false);
    			getRePLC(装配区)[i].set人工组装线模式((tem1&0b1000)==8?true:false);
    			getRePLC(装配区)[i].set载具放行((tem1&0b100)==4?true:false);
    			getRePLC(装配区)[i].set动作完成((tem1&0b10)==2?true:false);
    			
    			if(取料完成1!=取料完成2){//模拟一个信号改变
    				//更新托盘的物料数量,即使载具没到位
    				if(取料完成1==1){//模拟一个上升沿
    					//检测本工位有没有托盘
    					
    					
    					if(i<15){//=15没有载具了，是同步输送线的电芯，不处理这个情况
    					    
    					  //取料完成需要跟周工做同步处理
    					   getWrPLC(装配区).get(i).updataDB(getWrPLC(装配区).get(i).firstST);//更新托盘的数量
    					
    					}
    					
    				    System.out.println("取料完成1 firstST in getFromPlc-在载具"+getWrPLC(装配区).get(i).firstST.getName()+"->"+i+"ST");
    				   
    				}
    				
    			}
    			
    			
    			
    			if(载具放行1!=载具放行2){

    				//更新托盘位置，同时把write置成false,
    				if(载具放行1==1){
    					long fTime=System.currentTimeMillis();
    					 getH放行(装配区).put(i,"1步:接收到放行"+new Date().toLocaleString());
    				if(i<15){
    					if(getWrPLC(装配区).get(i).firstST.is数据更新完成()){
    					  SqlPro.getLog().error("放行与动作更新同时存在，这是有异常->"+i+"ST");
    					}
    					Carry car=getCarryLine(装配区).getCarry(i);
    					
    					if(car!=null){
    						
    				
    						//首先移动载具到下一个工位，同时更新下一个工位的信息
    						if(取料完成1==1||不检测动作完成){
    							
    						 if( getCarryLine(装配区).removeToNext(i)){
    							 
    							 if(i<14){
    								 if(i==0){
    									
    									//pack
    									 if(getTj(装配区).get(car.get工单号()+"start")==null){
    										getTj(装配区).put(car.get工单号()+"start", System.currentTimeMillis());
      		    					    
      									   }
    									 
    		    					    }
    								 
    								 
    								 if(i==13){//模组
    									
    									////////////////////////////////////////////// 
    									//pack
    									 if(getTj(装配区).get(car.get工单号()+"start")!=null){
    										 if(getTj(装配区).get(car.get工单号()+"end")==null){
    											 getTj(装配区).put(car.get工单号()+"end", System.currentTimeMillis());
    											 getTj(装配区).put(car.get工单号()+"PACK用时=", getTj(装配区).get(car.get工单号()+"end")-getTj(装配区).get(car.get工单号()+"start"));
    											 getTj(装配区).put("完成模PACK数量",getTj(装配区).get("完成模PACK数量")==null?1:getTj(装配区).get("完成模PACK数量")+1);
     		    					        
    										   }
     		    					      }
     		    					    }	
    		    						
    								getWrPLC(装配区).get(i+1).initFromSql(1); 
    								//在这儿可以处理一个模组开始的时间，后结束的时间。
    							
    							 }
    						}else{
    							
    							getRePLC(装配区)[i].set载具放行(false);
    							if(modeTh%15==0){
    								SqlPro.getLog().error("载具移动失败在->"+i+"ST，如果载具移动失败，就不处理载具放行=true的信号，指令也不会移动，继续下一个载具判断");
    				    		 }
    							
    							continue;
    							
    							
    							
    						}
    							 
    						 }
    						//结束载具移动判断
    						
    						
    					if(car.getName().equals(getWrPLC(装配区).get(i).firstST.getName())){
    						
    						 if( getWrPLC(装配区).get(i).firstST.get剩余数量()!=0){
    							
    							  SqlPro.getLog().error("抓取数量读取异常,没处理取料完成就移动载具->"+i+"ST");
    							
    						  }
    						//如果这个托盘是本工位需要的托盘
    						 if( getWrPLC(装配区).get(i).firstST.get剩余数量()==0||不检测取料数量){
    							 if(取料完成1==1||不检测动作完成){
    							 System.out.println("载具放行1");
    							 final long timeS=System.currentTimeMillis();
    							 if(i==5){
    								 
    							   getWrPLC(装配区).get(i).firstST.set数据更新完成(true);
    							   getWrPLC(装配区).get(i).firstST.set数据处理中(true);
    							   System.out.println("set数据处理中"+getWrPLC(装配区).get(i).firstST.is数据处理中());
    								 }else{
    							     getWrPLC(装配区).get(i).firstST.set允许工位动作标志(false);
    							     getWrPLC(装配区).get(i).firstST.setWrite(false);
    							     //优先把指令放到队列里面
    							     getWrPLC(装配区).get(i).initFromSql(0); 
    							     getWrPLC(装配区).get(i).updata动作(0);
    							    //告诉PLC我数据更新完成了
    							     getWrPLC(装配区).get(i).firstST.set数据更新完成(true);
    							    }
    						   // String back=getWrPLC(装配区).get(i).firstST.writeifChangeToPLC();
    							 String back=getWrPLC(装配区).get(i).firstST.writeToPLC();
    							 long endF=System.currentTimeMillis()-fTime;
    							 if(endF>=3000){
    								 SqlPro.getLog().error("第"+i+"工位在接收到载具放行后写入 数据更新完成 用时="+endF);  
    								 
    							 }
    						  if(back.contains("成功")){
    							  //写入PLC成功后,在这儿再次检测载具放行变为OFF
    							  System.out.println("1第"+i+"工位数据更新完成，发送成功");
    							  final int curr2=i;
    							 
    		 new Thread(){
    			     final int curr=curr2;
    			 
    								  public void run(){
    									  getH放行(装配区).put(curr,getH放行(装配区).get(curr)+",2步:数据更新完成变ON"+new Date().toLocaleString());
    									  th.put(Thread.currentThread(), 装配区+"-装配区"+curr+"工位，数据更新完成="+getWrPLC(装配区).get(curr).firstST.is数据更新完成());
    									  getTj(装配区).put(curr+"", 1l);
    									  final long timeS2=timeS;
    									
    									
    									  while(true){ 
    										  if(getStop(装配区)){ try{Thread.sleep(10);}catch(Exception e){};continue;}
    									 
    						    Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, 装配区);	  
    						    Resint bint2=r2[curr*2];
    			    			int tem11=bint2.getResInt();
    			    		    int 载具放行new=(tem11&0b100)==4?1:0;
    			    			
    			    				if(载具放行new==0){
			    						getRePLC(装配区)[curr].set载具放行(false);
    			    				 
			    					 getH放行(装配区).put(curr,getH放行(装配区).get(curr)+",3步:接收到载具放行OF"+new Date().toLocaleString());
    			    					  
    			    		if(curr==5){
    			    						//如果是5ST，先不要让指令队列移动。等接收到放料完成后，在移动队列
    			    					
    			    				new Thread(){
        	    	    					public void run(){ 
        	    	    							 th.put(Thread.currentThread(),  装配区+"-装配区"+curr2+"工位，数据更新完成="+getWrPLC(装配区).get(curr2).firstST.is数据更新完成());
        	    	    							 getTj(装配区).put(curr+"", 1l);
        	    	    							 int dd=0;
        	    	    						    while(true)	{
        	    	    						    	 if(getStop(装配区)){ try{Thread.sleep(10);}catch(Exception e){};continue;}
        	    	    						    	try{
        	    	    						     if(dd%5==0)	{ System.out.println("等待放料完成信号=================");}
        	    	    						      dd++;
        	    	    						   Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, 装配区);	  
        	    	    		    			   Resint bint2=r2[curr*2];
        	    	    		    			   int tem11=bint2.getResInt();
        	    	    		    			   int 放料完成=(tem11&0b100000)==32?1:0; 		
        	    				    if(放料完成==1){
        	    				    	
        	    				    	/***************************/
        	    				    	getWrPLC(装配区).get(curr).lock();
        	    	    				    getWrPLC(装配区).get(curr).firstST.setWrite(false);	 
        	    	    	    	        getWrPLC(装配区).get(curr).firstST.clear();
        	    	    	    	        getWrPLC(装配区).get(curr).firstST.set数据更新完成(false);
        	    	    			        getWrPLC(装配区).get(curr).initFromSql(0);
        	    	    			        getWrPLC(装配区).get(curr).updata动作(0);
        	    	    			    getWrPLC(装配区).get(curr).unLock();
        	    	    			    String back=  getWrPLC(装配区).get(curr).firstST.writeToPLC();
        	    	    	    	  /***************************/
        	    	    	    	  
        	    	    	    	  if(getWrPLC(装配区).get(curr).firstST.is数据更新完成()){
 			    						 SqlPro.getLog().error("第"+curr+"工位写入失败 数据变成false被其他线程变为true"); 
 			    					 }
        	    	    	    	  if(!back.contains("成功")){
        	    	    	    		  SqlPro.getLog().error("第"+curr+"工位载具放行1"+back+"------------------->:写入失败");
        	    	    	    		  System.out.println("第"+curr+"工位载具放行1"+back+"------------------->:写入失败");  
        	    	    	    	  }else{ 
        	    	    	    		  getH放行(装配区).put(curr,getH放行(装配区).get(curr)+",4步:数据更新完成变OF"+new Date().toLocaleString());
        	    	    	    		  }
        	    	    	    	  
        	    	    	    	    break;	
                                        }
        	    	    			Thread.sleep(200);	
        	    	    						    		
        	    	    				}catch(Exception eee){ SqlPro.getLog().error("第"+curr+"工位载具放行1"+back+"------------------->:写入失败",eee);}
        	    	    						    	
        	    	    					} 
        	    	    				th.remove(Thread.currentThread());	
        	    	    				getTj(装配区).remove(curr+"");
        	    	    					}
        	    	    				}.start();
        	    	    					 break;	
    			    						
    			    		}else{
    			    						//非5工位的
    			    					
    			    		          	/***************************/
    			    		       	getWrPLC(装配区).get(curr).lock();
    			    			           getWrPLC(装配区).get(curr).updata动作(0);
    	    	    					   getWrPLC(装配区).get(curr).firstST.set数据更新完成(false);
    	    	    					   String back2=getWrPLC(装配区).get(curr).firstST.writeToPLC();
    	    	    			    getWrPLC(装配区).get(curr).unLock();
    	    	    					 /***************************/		   
    	    	    					   if(getWrPLC(装配区).get(curr).firstST.is数据更新完成()){
    				    						 SqlPro.getLog().error("第"+curr+"工位写入失败 数据变成false被其他线程变为true"); 
    				    					 }
    	    	    					  if(!back2.contains("成功")){
    	    	    						  SqlPro.getLog().error("第"+curr+"工位载具放行1"+back2+"------------------->:写入失败,数据更新完成没成FALSE");
            	    	    	    		  System.out.println("第"+curr+"工位载具放行1"+back2+"------------------->:写入失败");  
            	    	    	    	  }else{
            	    	    	    		  getH放行(装配区).put(curr,getH放行(装配区).get(curr)+",4步:数据更新完成变OF"+new Date().toLocaleString());
            	    	    	    	  }
    	    	    					// System.out.println("第"+curr+"工位载具放行1"+back+"------------------->:"+(System.currentTimeMillis()-timeS2)+"ms");
    	    	    					 break;
    			    			 }
    			    				
    			    				 }
    			    				
    			    			//}
    			    			try{Thread.sleep(200);}catch(Exception ee){}
    			    			
    			    			}//end while
    									  th.remove(Thread.currentThread());
    									  getTj(装配区).remove(curr+"");
    									//  System.out.println(Thread.currentThread()+"结束1-------------------------------------------------------------------------------------");
    								  }
    		}.start();
    							  
    	    				
    	    				 
    	    				 
    	    				 
    	    				  }else{
    	    					  SqlPro.getLog().error("1第"+i+"工位数据更新完成，发送失败！！！！载具放行处理失败,没进到处理FALSE线程");
    	    					  System.out.println("1第"+i+"工位数据更新完成，发送失败！！！！载具放行处理失败");  
    	    				      }
    						  
    							  } //end 判断取料完成
    						 }
    					}
    			else{//如托盘在本工位没有任何需要的动作，不判断动作完成标志
    						 
    						System.out.println("如托盘在本工位没有任何需要的动作，不判断动作完成标志-----------------------------");
    					//再移一次//////////////////////////////////////////////
    					//	getH放行(装配区).put(i, System.currentTimeMillis());
    					/*
    						if( getCarryLine(装配区).removeToNext(i)){ 
    							 if(i<14){
    							  getWrPLC(装配区).get(i+1).initFromSql(1); 
    							 
    							 }
    						}
    						*/
    					//////////////////////////////////////////////////	 
    					   final long timeS=System.currentTimeMillis();
    					   getWrPLC(装配区).get(i).firstST.set数据更新完成(true);
    					   getWrPLC(装配区).get(i).firstST.set数据处理中(true);
    					   getWrPLC(装配区).get(i).firstST.set允许工位动作标志(false);
   						 
   						   String back=getWrPLC(装配区).get(i).firstST.writeToPLC();
   						   long endF=System.currentTimeMillis()-fTime;
   						   
						 if(endF>=3000){
							  SqlPro.getLog().error("第"+i+"工位在接收到载具放行后写入 数据更新完成 用时="+endF);  
							 
						   }
   						    if(back.contains("成功")){
   							  //写入PLC成功后,在这儿再次检测载具放行变为OFF
							 // System.out.println("2第"+i+"工位数据更新完成，发送成功");
							  final int curr2=i;
							//  getWrPLC(装配区).get(i).firstST.updataFromPLC();
							//  System.out.println(i+"工位，数据更新完成="+getWrPLC(装配区).get(i).firstST.is数据更新完成());
							 // try{Thread.sleep(500);}catch(Exception ee){}
					new Thread(){
						 final int curr=curr2;
								  public void run(){
									  getH放行(装配区).put(curr,getH放行(装配区).get(curr)+",2步:数据更新完成变ON"+new Date().toLocaleString());
									  th.put(Thread.currentThread(),  装配区+"-装配区"+curr+"工位，数据更新完成="+getWrPLC(装配区).get(curr).firstST.is数据更新完成());
									  getTj(装配区).put(curr+"", 1l);
									  final long timeS2=timeS;
									 // System.out.println(Thread.currentThread()+"启动2-------------------------------------------------------------------------------------");
									  while(true){
										  if(getStop(装配区)){ try{Thread.sleep(10);}catch(Exception e){};continue;}		  
						    Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, 装配区);	  
						    Resint bint2=r2[curr*2];
			    			int tem11=bint2.getResInt();
			    			//int tem22=getRePLC(装配区)[curr].boolCont.getResInt();
			    			
			    			int 载具放行new=(tem11&0b100)==4?1:0; 
			    			//int 载具放行old=(tem22&0b100)==4?1:0;
			    			//if(载具放行!=载具放行old){
			    				if(载具放行new==0){
			    					getRePLC(装配区)[curr].set载具放行(false);
			    					getH放行(装配区).put(curr,getH放行(装配区).get(curr)+",3步:接收到载具放行OF"+new Date().toLocaleString());
			    					
			    					 
			    				// if( getCarryLine(装配区).removeToNext(curr)){
			    					  //不更新命令
			    					/***************************/
			    					getWrPLC(装配区).get(curr).lock();
			    					getWrPLC(装配区).get(curr).firstST.set数据处理中(false);
			    					getWrPLC(装配区).get(curr).firstST.set数据更新完成(false);
			    					getWrPLC(装配区).get(curr).unLock();
			    					 String back3= getWrPLC(装配区).get(curr).firstST.writeToPLC();
			    					/***************************/
			    					 if(getWrPLC(装配区).get(curr).firstST.is数据更新完成()){
			    						 SqlPro.getLog().error("第"+curr+"工位写入失败 数据变成false被其他线程变为true"); 
			    					 }
			    					if(!back3.contains("成功")){
			    						 SqlPro.getLog().error("第"+curr+"工位载具放行1"+back3+"------------------->:写入失败 数据更新完成没能FALSE");
        	    	    	    		 System.out.println("第"+curr+"工位载具放行1"+back3+"------------------->:写入失败");  
        	    	    	    	  }else{
        	    	    	    		 getH放行(装配区).put(curr,getH放行(装配区).get(curr)+",4步:数据更新完成变OF"+new Date().toLocaleString());	  
        	    	    	    		  
        	    	    	    	  }
			    				
			    				 
			    				 break;
			    				 }
			    				
			    			//}
			    			try{Thread.sleep(200);}catch(Exception ee){}
			    			 
			    			}//end while
									  th.remove(Thread.currentThread());
									  getTj(装配区).remove(curr+"");
									//  System.out.println(Thread.currentThread()+"结束2-------------------------------------------------------------------------------------");
								  }
				}.start(); 
   	    				  }
   						    else{
   						     SqlPro.getLog().error("2第"+i+"工位数据更新完成，发送失败！！！！！没进到处理FALSE线程");
   	    					 System.out.println("2第"+i+"工位数据更新完成，发送失败！！！！！"); 
   	    					  
   	    				      }
    						
    	    				  
    					}
    					
    					}
    				
    			       }
    				/////////////////////
    				if(i==15){
    					System.out.println("同步输送线载具放行-------------》");
    					final long timeS=System.currentTimeMillis();
    					//getWrPLC(装配区).get(i).lock();////////////////////
    					 getWrPLC(装配区).get(i).firstST.set允许工位动作标志(false);
					     getWrPLC(装配区).get(i).firstST.setWrite(false);
					     //优先把指令放到队列里面
					     getWrPLC(装配区).get(i).initFromSql(0); 
					   
					    //告诉PLC我数据更新完成了 
					     getWrPLC(装配区).get(i).firstST.set数据更新完成(true);
					   //getWrPLC(装配区).get(i).unLock();///////////////
    					
					    String back=getWrPLC(装配区).get(i).firstST.writeToPLC();
						  // System.out.println("同步输送线--工位载具放行"+back+":"+(System.currentTimeMillis()-timeS)+"ms");
						    if(back.contains("成功")){
							 
						    	 final int curr2=i;
							
							  new Thread(){
								  final int curr=curr2; 
								  public void run(){
									  th.put(Thread.currentThread(),  装配区+"-装配区"+curr+"工位，数据更新完成="+getWrPLC(装配区).get(curr).firstST.is数据更新完成());
									  getH放行(装配区).put(curr,getH放行(装配区).get(curr)+",2步:数据更新完成变ON"+new Date().toLocaleString());
									  getTj(装配区).put(curr+"", 1l);
									  final long timeS2=timeS;
									  //System.out.println(Thread.currentThread()+"启动15-------------------------------------------------------------------------------------");
									  while(true){
								 if(getStop(装配区)){ try{Thread.sleep(10);}catch(Exception e){};continue;}			  
						    Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, 装配区);	  
						    Resint bint2=r2[curr*2];
			    			int tem11=bint2.getResInt();
			    			
			    			int 载具放行new=(tem11&0b100)==4?1:0; 
			    			
			    			//if(载具放行!=载具放行old){
			    				if(载具放行new==0){
			    					/***************************/
			    					getH放行(装配区).put(curr,getH放行(装配区).get(curr)+",3步:接收到载具放行OF"+new Date().toLocaleString());
			    					 getRePLC(装配区)[curr].set载具放行(false);
			    					 getWrPLC(装配区).get(curr).lock();
			    					
			    					 getWrPLC(装配区).get(curr).firstST.set数据处理中(false);
				    				 getWrPLC(装配区).get(curr).firstST.set数据更新完成(false);
				    				 getWrPLC(装配区).get(curr).unLock();
			    					 String back5= getWrPLC(装配区).get(curr).firstST.writeToPLC();
			    					 /***************************/ 
	    	    					if(!back5.equals("成功")){
	    	    						
	    	    						SqlPro.getLog().error("同步输送线"+curr+"工位载具放行后数据更新完成变成false"+back5+"------>:失败");
	    	    					}else{
	    	    						getH放行(装配区).put(curr,getH放行(装配区).get(curr)+",4步:数据更新完成变OF"+new Date().toLocaleString());	 	
	    	    						
	    	    					 }
	    	    			            break;
			    				 }
			    				
			    			//}
			    			try{Thread.sleep(200);}catch(Exception ee){}
			    			 
			    			}//end while
									  th.remove(Thread.currentThread());
									  getTj(装配区).remove(curr+"");
									 // System.out.println(Thread.currentThread()+"结束15-------------------------------------------------------------------------------------");
								  }
							  }.start();
	    				  }else{
	    					  SqlPro.getLog().error("2第"+i+"工位数据更新完成，发送失败！！！！！没进到处理FALSE线程");  
	    					  
	    				  }	
    					
    				   }
    				////////////////////////
    				
    				
    				
    				}//end 载具放行=1
    				
    				
    			}
    	
    			
    		}
    		
    		//更新第个二个队列
    		for(int i=31;i>15;i--){//31是寸动线的第2个队列，寸动线没有第2个队列，所以从30开始 
    			Resint bint=r[i*2];
    			int tem1=bint.getResInt();
    			int tem2=getRePLC(装配区)[i].boolCont.getResInt();
    			int 载具放行1=(tem1&0b100)==4?1:0;
    			int 载具放行2=(tem2&0b100)==4?1:0;
    			int 取料完成1=(tem1&0b10)==2?1:0;
    			int 取料完成2=(tem2&0b10)==2?1:0;
    			getRePLC(装配区)[i].set载具到位((tem1&0b1)==1?true:false);
    			getRePLC(装配区)[i].set人工组装线模式((tem1&0b1000)==8?true:false);
    			getRePLC(装配区)[i].set载具放行((tem1&0b100)==4?true:false);
    			getRePLC(装配区)[i].set动作完成((tem1&0b10)==2?true:false);
    			//RST[i]=new ReST(bint);
    			if(取料完成1!=取料完成2){
    				//更新托盘的物料数量
    				System.out.println("取料完成2---secendST in getFromPlc");
    				//SqlPro.getLog().error("取料完成2 secendST in getFromPlc-在载具"+getWrPLC(装配区).get(i).firstST.getName()+"->"+(i-16)+"ST");
    				if(取料完成1==1){
    					 int 剩余数量=getWrPLC(装配区).get(i-16).firstST.get剩余数量();
    					// if(getCarryLine(装配区).getCarry(i-16)!=null)
    					if(剩余数量!=0){//说明第二个队列已经移到第一个队列了,因为必须执行完第一个队列后才能执行第二个队列
    					   getWrPLC(装配区).get(i-16).firstST.set数据处理中(true);
    					   getWrPLC(装配区).get(i-16).updataDB(getWrPLC(装配区).get(i-16).firstST);//更新托盘的数量
    					  
    					   }else{
    						   getWrPLC(装配区).get(i-16).secondST.set数据处理中(true);  
    					       getWrPLC(装配区).get(i-16).updataDB(getWrPLC(装配区).get(i-16).secondST);//更新托盘的数量
    					    
    					   }
    					
    				}
    				
    			}
    			
    			
    		}
    		
    	
    	
    	}catch(Exception ex){
    		SqlPro.getLog().error(ex.getMessage(),ex);
    		ex.printStackTrace();}
    	
    	
    	return null;
    }
    

    
	/*public void set动作(int s){
	    //  ReST rs=RST[s];
		
	  }*/
	
	public  static void main(String ss[]){
		
		getIntance();
	
	}
	
   public String writeBlockToBLC(String startAddress,int len,int[]val, int machineID){
       int i= ClientSer.getIntance().writeSirIntToCTR(startAddress, len, val, machineID);
	   if(i>-1){
	     return "成功";
	   }else{
		 return "失败";
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
   
  
   //如果立库没准备好，那么要做两件事，1，如果工位上有托盘，那么就是这个托盘的物料不是指令要取的物料，或者数量不够，这时候要让托盘走，调用一个新的托盘；要是没有托盘那么就调新的托盘来
   public boolean getSTRdy(int line,int st){
	   //这步判断可以不需要，因为只有到位信号才更新托盘
	   //STATE1,STATE2在主线程里面自动更新
	   String 人工点="60001";
	   if(line==2)人工点="60002";
	   String AGV点="60003";
	   if(line==2)AGV点="60004";
	   boolean 到位1=false; boolean 到位2=false;
	   try{
		 
		  
	  if(st>=2&&st<=8){
	   if(line==1){
		   
		   String smm[]=getSTATE1().split("\\|"); 
		   if(smm.length<2){
			   getH放行(1).put(100088, "获取立库1状态异常,时间="+new Date().toLocaleString()+"返回="+getSTATE1());
			   SqlPro.getLog().error("获取立库状态异常，在1250行!"+getSTATE1());  
			    return false;
		   }
		   getH放行(1).remove(100088);
		   if(smm[7-(st-2)-1].split("=")[1].equals("0")){
			  
			   STC1.get(st-1).firstST.set立库RDY(false);
			   STC1.get(st-1).secondST.set立库RDY(false);
			   到位1=false;
			   //return false;  
		   }else{
			  
			   Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, line);
			   Resint bint=r[(st-1)*2];
			   Resint bint2=r[(st-1)*2+32];
   			   int tem1=bint.getResInt();
   			   int 托盘回大库=(tem1&0b10000)==16?1:0;//PLC请求托盘回库信息
   			   int tem2=bint2.getResInt();
			   int 托盘回大库2=(tem2&0b10000)==16?1:0;//第二队列PLC请求托盘回库信息
   			   //先判断第一队列
			  // System.out.println("托盘回大库/托盘回大库2------------------"+托盘回大库+"/"+托盘回大库2);
   			   if(托盘回大库==1||托盘回大库2==1){//把托盘出库
   				String sb=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  货位号='"+gw1[st-2]+"'");
   				if(sb!=null){
   					String sm[]=sb.split("!_!");
   					if(A区输送线自动请求打开){
   				    String bb=SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");
   				    String bb2=SqlTool.insert(new String[]{"UPDATE 库存托盘 set 数量=0 where 托盘编号='"+sm[0]+"'"});
   				    }
   				   
   				    return false; 
   				    }
   			      }else{
   			    	  
			        到位1=true;
			      }
			  
   			   
   			 
			     
		   }
		   
		   
		   
	   }else{
		   String smm[]=getSTATE2().split("\\|");
		   if(smm.length<2){
			   getH放行(2).put(100088, "获取立库2状态异常,时间="+new Date().toLocaleString()+"返回="+getSTATE2());
			   SqlPro.getLog().error("获取立库状态异常，在1288行!"+getSTATE2());  
			    return false;
		   }
		   getH放行(2).remove(100088);
		   if(smm[7-(st-2)-1].split("=")[1].equals("0")){
			   STC2.get(st-1).firstST.set立库RDY(false);
			   STC2.get(st-1).secondST.set立库RDY(false);
			   到位2=false;
			  // return false;  
		   }else{
			   Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, line);
			   Resint bint=r[(st-1)*2];
			   Resint bint2=r[(st-1)*2+32];
 			   int tem1=bint.getResInt();
 			   int 托盘回大库=(tem1&0b10000)==16?1:0;//PLC请求托盘回库信息
 			   int tem2=bint2.getResInt();
			   int 托盘回大库2=(tem2&0b10000)==16?1:0;//第二队列PLC请求托盘回库信息
   			   
   			   if(托盘回大库==1||托盘回大库2==1){//把托盘出库
   				String sb=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  货位号='"+gw2[st-2]+"'");
   				if(sb!=null){
   					String sm[]=sb.split("!_!");
   					if(B区输送线自动请求打开){
   				    String bb=SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");
   				    String bb2=SqlTool.insert(new String[]{"UPDATE 库存托盘 set 数量=0 where 托盘编号='"+sm[0]+"'"});
   				    }
   				  
   				    // STC2.get(st-1).firstST.set立库RDY(false);
				    // STC2.get(st-1).secondST.set立库RDY(false); 
   				    return false; 
   				    
   				    }
   			      }else{
   			    	  
			        到位2=true;
			      }
			   
			   
		    }
		   
	   }
	   }
	 //测试时封住了此型号 
	  if(A区输送线到位常有)
	    到位1=true;  
	  if(B区输送线到位常有)
	    到位2=true;
	   
	 
	   if(st>=2&&st<=7){ 
		  String tem2=null;
		  STContent cot= STC1.get(st-1);
		  if(line==1){
			  tem2=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  货位号='"+gw1[st-2]+"'"); 
			  cot= STC1.get(st-1);
		  }else{
			  tem2=SqlTool.findOneRecord("select  托盘编号,物料,数量 ,货位号  from 库存托盘   where  货位号='"+gw2[st-2]+"'"); 
			  cot= STC2.get(st-1);  
			  
		  }
		  
		  if(tem2==null){//没有托盘的情况,就请求托盘
			  cot.firstST.set立库RDY(false);
			  cot.secondST.set立库RDY(false);
			  
			  String wuliao=cot.firstST.get物料编码()==null?"":cot.firstST.get物料编码();
			  if(!wuliao.equals("")){
				  if(line==1){	  
		         String sql1=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  物料='"+wuliao+"' and 数量>0 and 货位号  between 1 and 28 and 方向='"+line+"'"); 
				  if(sql1!=null){
					 String sm6[]=sql1.split("!_!") ;
					 if(A区输送线自动请求打开){//当检测到偶数位为空时就发出下货请求
					 SqlTool.add动作指令(sm6[0], sm6[3],  (gw1[st-2]-1)+"", "下货", 0, line+"");
					 
					 }
					   
				    }
				  }else{
					  String sql1=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  物料='"+wuliao+"' and 数量>0 and 货位号  between 1 and 28 and 方向='"+2+"'"); 
					  if(sql1!=null){
						 String sm6[]=sql1.split("!_!") ;
						 if(B区输送线自动请求打开){
							 //当检测到偶数位为空时就发出下货请求
						 SqlTool.add动作指令(sm6[0], sm6[3],  (gw2[st-2]-1)+"", "下货", 0, line+"");
						 
						 }
						   
					    }  
					  
				  }
			  }
			  //可以在这儿请求托盘
			  
			  return false;}
		 
		  else{//有托盘的情况
			  String sm[]=tem2.split("!_!");
			  int tpshul=Integer.parseInt(sm[2]);
		      
		       int id=((_1_6ST)cot.firstST).id;
		       int id2=((_1_6ST)cot.secondST).id;
            //   boolean re=((_1_6ST)cot.firstST).is立库RDY();
		    //   boolean re2=((_1_6ST)cot.secondST).is立库RDY();
		       int 需求数量=((_1_6ST)cot.firstST).get需求数量();
			   int 完成数量=((_1_6ST)cot.firstST).get完成数量();
			   if(需求数量!=完成数量){
				   //如果第一个队列还没取完，那就判断第一个队列
		       
		       String tem=SqlTool.findOneRecord("select  物料,数量  from 配方指令队列   where  ID='"+id+"'");
		          if(tem==null){//没有意思，工位还没初始好
		        	  cot.firstST.set立库RDY(false);
		        	  tem="-1!_!-1";
		    	     return false;
		    	     }
		       if(sm[1].equals(tem.split("!_!")[0])){//判断队列需要的物料，跟当前工位托盘的物料是不是一样
		    	   
		    	   if(需求数量-完成数量>tpshul){
					   //叫托盘回流
		    		   cot.firstST.set立库RDY(false);
		    		   if((line==1?A区输送线自动请求打开:B区输送线自动请求打开)){
		    		   SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");
		    		   System.out.println("要取的不够了------而回流="+sm[1]+"=="+tem.split("!_!")[0]);
		    		   }
		    		   
					    return false;
					   }else{
						   
						if(line==1){   
					    cot.firstST.set立库RDY(true&&到位1);  
					    return true&&到位1;}
						else{
							 cot.firstST.set立库RDY(true&&到位2);  
							 return true&&到位2;
					        }
					   
					   }
		    	   
		       }else{ //托盘物料跟本工位的不一样
		    	      //叫托盘回流  
		    	   if(tpshul>回流阀值){//回货架
		    		   if((line==1?A区输送线自动请求打开:B区输送线自动请求打开)){
		    	     String b=SqlTool.add动作指令(sm[0], sm[3], AGV点, "输送线回流", 0, line+"");
		    	     System.out.println("因为托盘物料不是本工位要的物料------而回到货架"+sm[1]+"=="+tem.split("!_!")[0]+"->"+b);
		    		   }
		    	     }else{//回大库
		    	    	 if((line==1?A区输送线自动请求打开:B区输送线自动请求打开)){
		    	     String b=SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");	 
		    	     System.out.println("因为托盘数量<3且不是本工位要的物料------而回到大库"+sm[1]+"=="+tem.split("!_!")[0]+"->"+b);
		    	    	 }
		    	     }
		    	   
		    	   cot.firstST.set立库RDY(false);
		    	   return false;  
		       }
		    }
			   else{//如果第一个队列取完了，才判断第二个队列	 
				   if(需求数量==完成数量&&需求数量!=0)
				    cot.firstST.set立库RDY(false);//第一个队列取完了把立库READY=false;
				   
				   String tem=SqlTool.findOneRecord("select  物料,数量  from 配方指令队列   where  ID='"+id2+"'");
				 
				   if(tem==null){//没有意思，工位还没初始好
			        	  cot.secondST.set立库RDY(false);
			        	  tem="-1!_!-1";
			    	     return false;
			    	     }
				   if(sm[1].equals(tem.split("!_!")[0])){
					   int 需求数量2=((_1_6ST)cot.secondST).get需求数量();
					   int 完成数量2=((_1_6ST)cot.secondST).get完成数量();
					   if(需求数量2-完成数量2>tpshul){
						   //叫托盘回流
						   cot.secondST.set立库RDY(false);
						   if((line==1?A区输送线自动请求打开:B区输送线自动请求打开)){
						   SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");  
						   }
						   return false;
						   }else{
							   
							   if(line==1){  
								   if(需求数量2==完成数量2&&需求数量2!=0){
									   cot.secondST.set立库RDY(false);  
									   return false;
								   }else{
								    cot.secondST.set立库RDY(true&&到位1);  
								    return true&&到位1;}
								    
							   }
									else{
										 if(需求数量2==完成数量2&&需求数量2!=0){
											 cot.secondST.set立库RDY(false);
											 return false;
										   }else{
										    cot.secondST.set立库RDY(true&&到位2);  
										    return true&&到位2;}
								        }
							   }
					   
				   }
				        else{
			    	      //叫托盘回流  
				        cot.secondST.set立库RDY(false);
				        if(tpshul>回流阀值){//回货架
				        	 if((line==1?A区输送线自动请求打开:B区输送线自动请求打开))
				    	     SqlTool.add动作指令(sm[0], sm[3], AGV点, "输送线回流", 0, line+"");
				    	     //System.out.println("2因为托盘物料不是本工位要的物料------而回到货架"+sm[1]+"=="+tem.split("!_!")[0]);
				    	     }else{//回大库
				    	    	 if((line==1?A区输送线自动请求打开:B区输送线自动请求打开))
				    	     SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");	 
				    	     //System.out.println("2因为托盘数量<3且不是本工位要的物料------而回到大库"+sm[1]+"=="+tem.split("!_!")[0]);
				    	     }
				    	 return false;  
				       }
				   
				   /////////////////////////////
				   
			   }
		       
		       
		  } 
		   
	   }
	   
	   
	   if(st==8){//只要托盘有数量且物料相当就能返回TRUE
			 
			  String tem2=null;
			  STContent cot= STC1.get(st-1);
			  if(line==1){
				  tem2=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  货位号='"+gw1[st-2]+"'"); 
				  cot= STC1.get(st-1);
			  }else{
				  tem2=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  货位号='"+gw2[st-2]+"'"); 
				  cot= STC2.get(st-1);  
				  
			  }
			  if(tem2==null){//没有托盘的情况
				  cot.firstST.set立库RDY(false);
				  cot.secondST.set立库RDY(false);
				  
				 String wuliao=cot.firstST.get物料编码()==null?"":cot.firstST.get物料编码();
				  if(!wuliao.equals("")){
					  if(line==1){	  
			         String sql1=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  物料='"+wuliao+"' and 数量>0 and 货位号  between 1 and 28 and 方向='"+line+"'"); 
					  if(sql1!=null){
						 String sm6[]=sql1.split("!_!") ;
						 if((line==1?A区输送线自动请求打开:B区输送线自动请求打开)){
						  SqlTool.add动作指令(sm6[0], sm6[3],  (gw1[st-2]-1)+"", "下货", 0, line+"");
						  }
						   
					    }
					  }else{
						  String sql1=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  物料='"+wuliao+"' and 数量>0 and 货位号  between 1 and 28 and 方向='"+2+"'"); 
						  if(sql1!=null){
							 String sm6[]=sql1.split("!_!") ;
							 if((line==1?A区输送线自动请求打开:B区输送线自动请求打开)){
							 SqlTool.add动作指令(sm6[0], sm6[3],  (gw2[st-2]-1)+"", "下货", 0, line+"");
							 }
							   
						    }  
						  
					  }
				  }
				  //可以在哪儿请求托盘
				  
				  return false;}
			 
			  else{//有托盘的情况
				  String sm[]=tem2.split("!_!");
				   int tpshul=Integer.parseInt(sm[2]);
			       int id=((_7ST)cot.firstST).id;
			       int id2=((_7ST)cot.secondST).id;
	               boolean re=((_7ST)cot.firstST).is立库RDY();
			       boolean re2=((_7ST)cot.secondST).is立库RDY();
			       int 需求数量=((_7ST)cot.firstST).get需求数量();
				   int 完成数量=((_7ST)cot.firstST).get完成数量();
				   if(需求数量!=完成数量){
					   //如果第一个队列还没取完，那就判断第一个队列
			       
			       String tem=SqlTool.findOneRecord("select  物料,数量  from 配方指令队列   where  ID='"+id+"'");

				   if(tem==null){//没有意思，工位还没初始好
			        	  cot.firstST.set立库RDY(false);
			        	  tem="-1!_!-1";
			    	     return false;
			    	     }
			       if(sm[1].equals(tem.split("!_!")[0])){
			    	   if(tpshul<1){//因为电芯每次只取一个，所以只判断大于0的情况
						   //叫托盘回流
			    		   cot.firstST.set立库RDY(false);
			    		   if((line==1?A区输送线自动请求打开:B区输送线自动请求打开))
			    		   SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");  
						   return false;
						   }else{
							   if(line==1){   
								    cot.firstST.set立库RDY(true&&到位1);  
								    return true&&到位1;}
									else{
										 cot.firstST.set立库RDY(true&&到位2);  
										 return true&&到位2;
								        }
							   
						   }
			    	   
			       }else{//如果物料不相等
			    	      //叫托盘回流  
			    	   cot.firstST.set立库RDY(false);
			    	   
			    	   if(tpshul>回流阀值){//回货架
			    		     if((line==1?A区输送线自动请求打开:B区输送线自动请求打开))
				    	     SqlTool.add动作指令(sm[0], sm[3], AGV点, "输送线回流", 0, line+"");
				    	     }else{//回大库
				    	    	 if((line==1?A区输送线自动请求打开:B区输送线自动请求打开))
				    	     SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");	 
				    	     }
			    	   return false;  
			       }
			    }
				   else{
					     //如果第一个队列取完了，才判断第二个队列	   
					   String tem=SqlTool.findOneRecord("select  物料,数量  from 配方指令队列   where  ID='"+id2+"'");

					   if(tem==null){//没有意思，工位还没初始好
				        	  cot.secondST.set立库RDY(false);
				        	  tem="-1!_!-1";
				    	     return false;
				    	     }
					   if(sm[1].equals(tem.split("!_!")[0])){
						   int 需求数量2=((_7ST)cot.secondST).get需求数量();
						   int 完成数量2=((_7ST)cot.secondST).get完成数量();
						   if(tpshul<1){
							   //叫托盘回流
							   cot.secondST.set立库RDY(false);
							   if((line==1?A区输送线自动请求打开:B区输送线自动请求打开))
							   SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");  
							   return false;
							   }else{
								   if(line==1){   
									    cot.secondST.set立库RDY(true&&到位1);  
									    return true&&到位1;}
										else{
										cot.secondST.set立库RDY(true&&到位2);  
										return true&&到位2;
									        }
								   }
						   
					   }
					        else{
				    	      //叫托盘回流  
					        	 cot.secondST.set立库RDY(false);
					        	 if(tpshul>回流阀值){//回货架
					        		 if((line==1?A区输送线自动请求打开:B区输送线自动请求打开))
						    	     SqlTool.add动作指令(sm[0], sm[3], AGV点, "输送线回流", 0, line+"");
						    	     }else{//回大库
						    	    	if((line==1?A区输送线自动请求打开:B区输送线自动请求打开))
						    	     SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");	 
						    	     }
					    	 return false;  
					       }
					   
				   }
			       
			       
			  } 
			      
		   
	   }
	   
	   if(st>=2&&st<=8){//开始判断奇数位的托盘情况，始终把奇数位填满
		   
		   
	   }  
	   
	  //升降台和8工位开始,立库RDY永远是TRUE 
	   if(st==1){
		   if(line==1){
			   STC1.get(st-1).firstST.set立库RDY(true);
			   STC1.get(st-1).secondST.set立库RDY(true);
			   return true;
		   }else{
			   STC2.get(st-1).firstST.set立库RDY(true);
			   STC2.get(st-1).secondST.set立库RDY(true);
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
		if(!this.getConcent(line)){return "连接异常";}
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
				    return "数据不同步，请同步后在更新数据！";  
				   
			   }
			   ////////////////////////////////////////////////
			   Method m2=	first.getClass().getMethod("set"+name2, boolean.class) ; 
			   if(value!=null&&value.equals("false"))
			    m2.invoke(first, false);
			   if(value!=null&&value.equals("true"))
				   m2.invoke(first, true);
			       first.writeToPLC(); 
			   return "成功";
			 }
			 if(f.getType().toString().equals("int")){
				 
				 Method m=	first.getClass().getMethod("get"+name2, null) ;
				   Object b= m.invoke(first, null)==null?"": m.invoke(first, null);
				   if(!oldValue.equals(b+"")){
					    return "数据不同步，请同步后在更新数据！";  
					   
				   }
				/////////////////////////////////////////////////////////////	
				   Method m2=	first.getClass().getMethod("set"+name2, int.class) ; 
				   if(value!=null)
				   m2.invoke(first, Integer.parseInt(value));
				   first.writeToPLC(); 
				   return "成功";
				 }
			 
			 if(f.getType().toString().equals("class java.lang.String")){
				 
				 Method m=	first.getClass().getMethod("get"+name2, null) ;
				 Object b= m.invoke(first, null)==null?"": m.invoke(first, null);
				   if(!oldValue.equals(b+"")){
					    return "数据不同步，请同步后在更新数据！";  
					   
				   }
				/////////////////////////////////////////////////////////////	
					
				   Method m2=	first.getClass().getMethod("set"+name2, String.class) ; 
				   if(value!=null)
				   m2.invoke(first, value);
				   first.writeToPLC(); 
				   return "成功";
				 }
			 if(f.getType().toString().equals("class alai.GDT.Resint")){
				   return "成功";
				 
			    }
		 
		 }catch(Exception ex){ex.printStackTrace();
		   return ex.getMessage();
		 }
		
		   return "失败";
		
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

			/*tongji.put("空放行次数");tongji.put("空放行时间");tongji.put("上次空放行用时");
			 *tongji.put("有动作放行次数");tongji.put("有动作放行时间");tongji.put("上次有动作放行用时");
			 *tongji.put("完成模组数量");tongji.put("完成模PACK数量");
			 *
			 * 
			 * */
			//tongji.get("")
			 Vector t=new Vector();
			 Enumeration<String> en1=th.keys();
			 while(en1.hasMoreElements()){
				 Object key=en1.nextElement();
				 Object val=(th.get(key)==null?"":th.get(key));
				 if(val.toString().contains("1-装配区")){
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
					 if(val.toString().contains("2-装配区")){
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
		
		getH放行(machineID).clear();
		
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
		for(int i=0;i<line.getLength();i++){m.remove(i+"");}
		t=null;
		}
	
	public List<String> get统计(int machineID){
		 List<String> list = new ArrayList<String>();
		
		/*   Hashtable<String,Long> h=(Hashtable<String,Long>)getTj(machineID).clone();
		
		  
		   Enumeration<String> en=h.keys();
		   
		   while(en.hasMoreElements()){
		    	String o=en.nextElement(); 
		    	long time=h.get(o);
		    	if(time>1000000000){
		    		list.add(o+"="+new Date(time).toLocaleString());
		    	}else{
		    	list.add(o+"="+(h.get(o)==null?"等待统计中。。。":h.get(o)+""));}
		    	
		       }
		
		   h=null;*/
		
		
		 Hashtable<Integer,String> h2=(Hashtable<Integer,String>)getH放行(machineID);
			
		  for(int i=0;i<16;i++){
		    
		    	String s=h2.get(i);
		    	String s2=h2.get(i+100);
		    	if(s!=null){
		    		if(s.contains("4步")){
		    			if(打开交换记录)
		    			 list.add(i+"ST->"+s);
		    		}else{
		    			list.add(0,"<font color=red>"+i+"ST->"+s+"</font>");	
		    		}
		    	}
		    	
		    	if(s2!=null){
		    		list.add(0,"<font color=red>"+i+"ST->"+s2+"</font>");
		    	}
		    
		       }
		
		  if(h2.get(100088)!=null){
			     list.add(0,"<font color=red>"+h2.get(100088)+"</font>");//获取立库状态异常在getSTRdy()立库状态<2	  
		  }
		   Hashtable<String,Long>   h=getTj(machineID);
		 // Collections.sort(list);
		   
		   
		  Hashtable<String ,String> thh= ClientSer.th;
		 
			  if(thh.get(machineID+"立库访问")!=null){
				  if(thh.get(machineID+"立库访问").contains("异常"))
				  list.add(0,"<font color=red>"+thh.get(machineID+"立库访问")+"</font>");  
				  else {
				   if(thh.get(machineID+"立库访问").contains("访问开始"))	
				   {  
				   list.add(0,"<font color=blue>"+thh.get(machineID+"立库访问")+"</font>"); 
				  }
				   else{
				   list.add(0,thh.get(machineID+"立库访问"));   
				        }
				  
				       } 	  
				  }
			  
			  if(thh.get(machineID+"PLC读")!=null){
				  if(thh.get(machineID+"PLC读").contains("异常")) 
				  list.add(0,"<font color=red>"+thh.get(machineID+"PLC读")+"</font>");  
				  else{ 
					  if(thh.get(machineID+"PLC读").contains("访问开始")){
				           list.add(0,"<font color=blue>"+thh.get(machineID+"PLC读")+"</font>"); 
				           }
					  else{
						  
						   list.add(0,thh.get(machineID+"PLC读"));
				        }
				   
				     }	  
				  }
			  
			  if(thh.get(machineID+"PLC写")!=null){
				  if(thh.get(machineID+"PLC写").contains("异常")) 
				  list.add(0,"<font color=red>"+thh.get(machineID+"PLC写")+"</font>"); 
				  else{
					  if(thh.get(machineID+"PLC写").contains("访问开始")) {  
				         list.add(0,"<font color=blue>"+thh.get(machineID+"PLC写")+"</font>"); 
				      
					     }else{
					    	 
					     list.add(0,thh.get(machineID+"PLC写"));  
				    	  
				              }
				  
				   }  
				  }
			  
			  Hashtable<String ,String> th3= KuFang.th;
			  if(th3.get(machineID+"立库输送线")!=null){list.add(0,"<font color=blue>"+th3.get(machineID+"立库输送线")+"</font>"); }
			  if(th3.get(machineID+"堆垛机")!=null){ list.add(0,th3.get(machineID+"堆垛机")); } 
			 
			
		  
		     list.add(0, "完成PACK数量="+(h.get("完成模PACK数量")==null?"等待统计中。。。":h.get("完成模PACK数量")+""));
		     if(machineID==1){ 
		    	
		    	 if(canDelet(2).contains("成功")){
		    		//if(getTj(2).get("B区数量更新")==null){};
		    		 list.add(0,"<font color=green size=15>B区可以重新下单</font>");  
		    	 }
		    	 
		    	 if(canDelet(1).contains("成功")){
		    		  list.add(0,"<font color=green size=15>A区可以重新下单</font>");  
		    	 }
		    	 
		    	 if(SqlTool.zl1!=null&&!SqlTool.zl1.equals("")){ 
		    		 list.add(0,"<font color=black >"+SqlTool.zl1+"</font>");  
		    		 
		    	 }
		    	 
		       }
		     
		  
		   return list; 
	}
	
	
	public String stop(){
		 Vector v=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态<>'完成' and 状态<>'排队'  order by idEvent");
		   if(v.size()>0){
			   return "堆垛机还有未执行完的命令，请等待堆垛机执行完命令，在关闭系统";
		   }
		   else{
			  SqlPro.is大库调度=false; 
			  SqlPro.is指令调度=false;
			  stop1=true;
			  stop2=true;
			   
		   }
		return "成功";
	}
	
	public String  canDelet(int 装配区){
		  //一秒调用我的方法一次，当包含“成功”时，弹出“提示框表面一个PACK装配完成”
		
			 if(装配区==1){
			 for(int i=0;i<line.getLength();i++){
				 if(line.getCarry(i)!=null){
					 return "订单没装配完";
				 }
			   }}
			 if(装配区==2){ 
				 for(int i=0;i<line2.getLength();i++){
					 if(line2.getCarry(i)!=null){
						 return "订单没装配完"; 
					 }
				   }}
			 
			  Hashtable<String,Long>   h=getTj(装配区);
			  for(int i=1;i<line.getLength();i++){
				  if(h.get(i+"")!=null){
					  return "订单没装配完";  
					  
				  }
				  
			  }
			 
		
		  
		  return "成功";
		
	         }
	
	
}
