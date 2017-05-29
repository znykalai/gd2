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
	
	public boolean A区输送线自动请求打开=true;
	public boolean B区输送线自动请求打开=true;
	public boolean A区输送线到位常有=false;
	public boolean B区输送线到位常有=false;
	public boolean 不检测取料数量=true;
	protected transient Hashtable th=new Hashtable();
	public int 回流阀值=2;
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
			//INSTANCE.startTiaodu2();
			INSTANCE.sendHert(1);
			//INSTANCE.sendHert(2);//第2区域
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
			STATE1=ClientSer.getIntance().getState(10);}catch(Exception ex){
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
	
	public void sendHert(int machineID){
		    new Thread(){
		    	public void run(){
		    	while(true){
		    		try{
		    			ClientSer.getIntance().writeSirIntToCTR("D11999", 1, new int[]{1}, machineID)	;
		    			Thread.sleep(500);
		    			ClientSer.getIntance().writeSirIntToCTR("D11999", 1, new int[]{0}, machineID)	;
		    		}catch(Exception ex){ex.printStackTrace();}
		    		
		    	}
		    	}
		    }.start();
	}
	public void startTiaodu(){
		
		System.out.println("启动16个工位调度。。。。");
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
				if(!stop1)
				getFromPLC(1);//必须写在最后
				
				writeO();
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
				
				ST0_2.initFromSql();
				ST1_2.initFromSql();
				ST2_2.initFromSql();
				ST3_2.initFromSql();
				ST4_2.initFromSql();
				ST5_2.initFromSql();
				ST6_2.initFromSql();
				ST7_2.initFromSql();
				ST8_2.initFromSql();
				ST9_2.initFromSql();
				ST10_2.initFromSql();
				ST11_2.initFromSql();
				ST12_2.initFromSql();
				ST13_2.initFromSql();
				ST14_2.initFromSql();
				ST15_2.initFromSql();
				
				getSTRdy(2,2);
				getSTRdy(2,3);
				getSTRdy(2,4);
				getSTRdy(2,5);
				getSTRdy(2,6);
				getSTRdy(2,7);
				getSTRdy(2,8);
				if(!stop2)
				getFromPLC(2);//必须写在最后
				//writeO();
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
	int modeTh=0;
	//读取光大的状态，并更新托盘数量
    public ReST[] getFromPLC(int 装配区){
    	try{
    		if(modeTh%10==0){
    			System.out.println(th.size()+"="+th);
    		 }
    		modeTh++;
    		Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 63, 16, 装配区);
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
    			//RST[i]=new ReST(bint);
    			getRePLC(装配区)[i].set载具到位((tem1&0b1)==1?true:false);
    			getRePLC(装配区)[i].set人工组装线模式((tem1&0b1000)==8?true:false);
    			getRePLC(装配区)[i].set载具放行((tem1&0b100)==4?true:false);
    			getRePLC(装配区)[i].set动作完成((tem1&0b10)==2?true:false);
    			
    			if(取料完成1!=取料完成2){//模拟一个信号改变
    				//更新托盘的物料数量,即使载具没到位
    				if(取料完成1==1){//模拟一个上升沿
    					//检测本工位有没有托盘
    					if(i<15){//=15没有载具了，是同步输送线的电芯，不处理这个情况
    					//if(getCarryLine(装配区).getCarry(i)!=null){
    					  
    					   getWrPLC(装配区).get(i).updataDB(getWrPLC(装配区).get(i).firstST);//更新托盘的数量
    					 // getWrPLC(装配区).get(i).firstST.SET
    					   
    				         //  }
    					}
    					
    				    System.out.println("取料完成1 firstST in getFromPlc-在载具"+getWrPLC(装配区).get(i).firstST.getName()+"->"+i+"ST");
    				    SqlPro.getLog().error("取料完成1 firstST in getFromPlc-在载具"+getWrPLC(装配区).get(i).firstST.getName()+"->"+i+"ST");
    				}
    				
    			}
    			
    		
    			
    			if(载具放行1!=载具放行2){
    				
    			
    				
    				//更新托盘位置，同时把write置成false,
    				if(载具放行1==1){
    				if(i<15){
    					Carry car=getCarryLine(装配区).getCarry(i);
    					if(car!=null){
    						//首先移动载具到下一个工位，同时更新下一个工位的信息
    						if(取料完成1==1||不检测动作完成){
    						 if( getCarryLine(装配区).removeToNext(i)){
    							 if(i<14){
    								getWrPLC(装配区).get(i+1).initFromSql(); 
    							
    							 }
    						}else{
    							 SqlPro.getLog().error("载具移动失败在->"+i+"ST");	
    							
    						}
    							 
    						 }
    						
    						
    					if(car.getName().equals(getWrPLC(装配区).get(i).firstST.getName())){
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
    							     getWrPLC(装配区).get(i).initFromSql(); 
    							     getWrPLC(装配区).get(i).updata动作();
    							    //告诉PLC我数据更新完成了
    							     getWrPLC(装配区).get(i).firstST.set数据更新完成(true);
    							    }
    							 
    						    String back=getWrPLC(装配区).get(i).firstST.writeifChangeToPLC();
    						    // System.out.println("第"+i+"工位载具放行1"+back+":"+(System.currentTimeMillis()-timeS)+"ms");
    						  if(back.contains("成功")){
    							  //写入PLC成功后,在这儿再次检测载具放行变为OFF
    							  System.out.println("1第"+i+"工位数据更新完成，发送成功");
    							  final int curr2=i;
    							 
    							  new Thread(){
    								  public void run(){
    									  th.put(Thread.currentThread(), curr2+"工位，数据更新完成="+getWrPLC(装配区).get(curr2).firstST.is数据更新完成());
    									  final long timeS2=timeS;
    									  final int curr=curr2;
    									 // System.out.println(Thread.currentThread()+"启动1-------------------------------------------------------------------------------------");
    									  while(true){
    						    Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 63, 16, 装配区);	  
    						    Resint bint2=r2[curr*2];
    			    			int tem11=bint2.getResInt();
    			    		
    			    			int 载具放行new=(tem11&0b100)==4?1:0;
    			    			//int 载具放行old=(tem22&0b100)==4?1:0;
    			    			//if(载具放行!=载具放行old){
    			    				if(载具放行new==0){
    			    					if(curr==5){
    			    						//如果是5ST，先不要让指令队列移动。等接受到放料完成后，在移动队列
    			    						
    			    						 new Thread(){
        	    	    						 
        	    	    						 public void run(){
        	    	    							 th.put(Thread.currentThread(), curr2+"工位，数据更新完成="+getWrPLC(装配区).get(curr2).firstST.is数据更新完成());
        	    	    							 int dd=0;
        	    	    						    while(true)	{
        	    	    						    	try{
        	    	    						    if(dd%5==0)	{ System.out.println("等待放料完成信号=================");}
        	    	    						    dd++;
        	    	    						   Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 63, 16, 装配区);	  
        	    	    		    			   Resint bint2=r2[curr*2];
        	    	    		    			   int tem11=bint2.getResInt();
        	    	    		    			   int 放料完成=(tem11&0b100000)==32?1:0; 		
        	    				    if(放料完成==1){		
        	    	    				    getWrPLC(装配区).get(curr).firstST.setWrite(false);	 
        	    	    	    	        getWrPLC(装配区).get(curr).firstST.clear();
        	    	    	    	        getWrPLC(装配区).get(curr).firstST.set数据更新完成(false);
        	    	    			        getWrPLC(装配区).get(curr).initFromSql();
        	    	    			        getWrPLC(装配区).get(curr).updata动作();
        	    	    	    	  String back=  getWrPLC(装配区).get(curr).firstST.writeifChangeToPLC();
        	    	    	    	  if(!back.contains("成功")){
        	    	    	    		  SqlPro.getLog().error("第"+curr+"工位载具放行1"+back+"------------------->:写入失败");
        	    	    	    		  System.out.println("第"+curr+"工位载具放行1"+back+"------------------->:写入失败");  
        	    	    	    	  }
        	    	    	    	  
        	    	    	    	    break;	
                                        }
        	    	    			Thread.sleep(200);	
        	    	    						    		
        	    	    				}catch(Exception eee){}
        	    	    						    	
        	    	    					} 
        	    	    				th.remove(Thread.currentThread());			 
        	    	    					}
        	    	    				}.start();
        	    	    					 break;	
    			    						
    			    				}else{
    			    						//非5工位的
    			    					/* getWrPLC(装配区).get(curr).firstST.setWrite(false);	
    	    	    					 getWrPLC(装配区).get(curr).firstST.clear();
    	    	    					 getWrPLC(装配区).get(curr).firstST.set数据处理中(true);
    			    					 getWrPLC(装配区).get(curr).initFromSql();*/
    			    					 
    	    	    					 getWrPLC(装配区).get(curr).firstST.set数据更新完成(false);
    	    	    					 String back2=getWrPLC(装配区).get(curr).firstST.writeifChangeToPLC();
    	    	    					  if(!back2.contains("成功")&&!back2.contains("沒變化")){
    	    	    						  SqlPro.getLog().error("第"+curr+"工位载具放行1"+back2+"------------------->:写入失败");
            	    	    	    		  System.out.println("第"+curr+"工位载具放行1"+back2+"------------------->:写入失败");  
            	    	    	    	  }
    	    	    					// System.out.println("第"+curr+"工位载具放行1"+back+"------------------->:"+(System.currentTimeMillis()-timeS2)+"ms");
    	    	    					 break;
    			    					}
    			    				
    			    				 }
    			    				
    			    			//}
    			    			try{Thread.sleep(200);}catch(Exception ee){}
    			    			
    			    			}//end while
    									  th.remove(Thread.currentThread());		
    									//  System.out.println(Thread.currentThread()+"结束1-------------------------------------------------------------------------------------");
    								  }
    							  }.start();
    							  
    	    				
    	    				 
    	    				 
    	    				 
    	    				  }else{
    	    					  SqlPro.getLog().error("1第"+i+"工位数据更新完成，发送失败！！！！载具放行处理失败");
    	    					  System.out.println("1第"+i+"工位数据更新完成，发送失败！！！！载具放行处理失败");  
    	    				      }
    						  
    							  } //end 判断取料完成
    						 }
    					}
    					else{//如托盘在本工位没有任何需要的动作，不判断动作完成标志
    						 
    						System.out.println("如托盘在本工位没有任何需要的动作，不判断动作完成标志-----------------------------");
    						 if( getCarryLine(装配区).removeToNext(i)){ 
    							 if(i<14){
    							  getWrPLC(装配区).get(i+1).initFromSql(); 
    							 
    							 }
    						}
    						 
    						final long timeS=System.currentTimeMillis();
    					   getWrPLC(装配区).get(i).firstST.set数据更新完成(true);
    					   
    					   getWrPLC(装配区).get(i).firstST.set数据处理中(true);
    					  // System.out.println("2set数据处理中"+getWrPLC(装配区).get(i).firstST.is数据处理中());
    					   getWrPLC(装配区).get(i).firstST.set允许工位动作标志(false);
   						   String back=getWrPLC(装配区).get(i).firstST.writeifChangeToPLC();
   						  // System.out.println("第"+i+"工位载具放行2"+back+":"+(System.currentTimeMillis()-timeS)+"ms");
   						    if(back.contains("成功")){
   							  //写入PLC成功后,在这儿再次检测载具放行变为OFF
							  System.out.println("2第"+i+"工位数据更新完成，发送成功");
							  final int curr2=i;
							//  getWrPLC(装配区).get(i).firstST.updataFromPLC();
							  System.out.println(i+"工位，数据更新完成="+getWrPLC(装配区).get(i).firstST.is数据更新完成());
							 // try{Thread.sleep(500);}catch(Exception ee){}
							  new Thread(){
								  public void run(){
									  th.put(Thread.currentThread(), curr2+"工位，数据更新完成="+getWrPLC(装配区).get(curr2).firstST.is数据更新完成());
									  final int curr=curr2;
									  final long timeS2=timeS;
									 // System.out.println(Thread.currentThread()+"启动2-------------------------------------------------------------------------------------");
									  while(true){
										  
						    Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 63, 16, 装配区);	  
						    Resint bint2=r2[curr*2];
			    			int tem11=bint2.getResInt();
			    			//int tem22=getRePLC(装配区)[curr].boolCont.getResInt();
			    			
			    			int 载具放行new=(tem11&0b100)==4?1:0; 
			    			//int 载具放行old=(tem22&0b100)==4?1:0;
			    			//if(载具放行!=载具放行old){
			    				if(载具放行new==0){
			    					 
			    				// if( getCarryLine(装配区).removeToNext(curr)){
			    					  //不更新命令
			    					 getWrPLC(装配区).get(curr).firstST.set数据处理中(false);
			    					 getWrPLC(装配区).get(curr).firstST.set数据更新完成(false);
			    					String back3= getWrPLC(装配区).get(curr).firstST.writeifChangeToPLC();
			    					if(!back3.contains("成功")){
			    						 SqlPro.getLog().error("第"+curr+"工位载具放行1"+back+"------------------->:写入失败");
        	    	    	    		 System.out.println("第"+curr+"工位载具放行1"+back3+"------------------->:写入失败");  
        	    	    	    	  }
			    				
			    				 
			    				 break;
			    				 }
			    				
			    			//}
			    			try{Thread.sleep(200);}catch(Exception ee){}
			    			 
			    			}//end while
									  th.remove(Thread.currentThread());
									//  System.out.println(Thread.currentThread()+"结束2-------------------------------------------------------------------------------------");
								  }
							  }.start(); 
   	    				  }
   						    else{
   						     SqlPro.getLog().error("2第"+i+"工位数据更新完成，发送失败！！！！！");
   	    					  System.out.println("2第"+i+"工位数据更新完成，发送失败！！！！！"); 
   	    					  
   	    				      }
    						
    	    				  
    					}
    					
    					}
    				 //更新到PLC,由initFromSql()自动完成
    			       }
    				/////////////////////
    				if(i==15){
    					System.out.println("同步输送线载具放行-------------》");
    					final long timeS=System.currentTimeMillis();
 					   getWrPLC(装配区).get(i).firstST.set数据更新完成(true);
 					   getWrPLC(装配区).get(i).firstST.set允许工位动作标志(false);
					   String back=getWrPLC(装配区).get(i).firstST.writeifChangeToPLC();
						  // System.out.println("同步输送线--工位载具放行"+back+":"+(System.currentTimeMillis()-timeS)+"ms");
						    if(back.contains("成功")){
							  //写入PLC成功后,在这儿再次检测载具放行变为OFF
							 // System.out.println("2同步输送线--工位数据更新完成，发送成功");
							  final int curr2=i;
							//  getWrPLC(装配区).get(i).firstST.updataFromPLC();
							//  System.out.println("同步输送线工位，数据更新完成="+getWrPLC(装配区).get(i).firstST.is数据更新完成());
							 // try{Thread.sleep(500);}catch(Exception ee){}
							  new Thread(){
								  public void run(){
									  th.put(Thread.currentThread(), curr2+"工位，数据更新完成="+getWrPLC(装配区).get(curr2).firstST.is数据更新完成());
									  final int curr=curr2;
									  final long timeS2=timeS;
									  //System.out.println(Thread.currentThread()+"启动15-------------------------------------------------------------------------------------");
									  while(true){
										  
						    Resint r2[]=	ClientSer.getIntance().getReturnPlc("D11001", 63, 16, 装配区);	  
						    Resint bint2=r2[curr*2];
			    			int tem11=bint2.getResInt();
			    			int tem22=getRePLC(装配区)[curr].boolCont.getResInt();
			    			
			    			int 载具放行new=(tem11&0b100)==4?1:0; 
			    			int 载具放行old=(tem22&0b100)==4?1:0;
			    			//if(载具放行!=载具放行old){
			    				if(载具放行new==0){
			    			
			    					 getWrPLC(装配区).get(curr).firstST.set数据更新完成(false);
			    					 //getWrPLC(装配区).get(curr).firstST.writeifChangeToPLC();
			    					 getWrPLC(装配区).get(curr).firstST.setWrite(false);	
	    	    					 getWrPLC(装配区).get(curr).firstST.clear();
	    	    					 getWrPLC(装配区).get(curr).initFromSql();
	    	    					String back5= getWrPLC(装配区).get(curr).firstST.writeifChangeToPLC();
	    	    					if(!back5.equals("成功")){
	    	    						
	    	    						 System.out.println("同步输送线"+curr+"工位载具放行2"+back5+"------>:失败");
	    	    					}
	    	    			 //  System.out.println("同步输送线"+curr+"工位载具放行2"+back+"------>:"+(System.currentTimeMillis()-timeS2)+"ms");
			    				 
			    				 break;
			    				 }
			    				
			    			//}
			    			try{Thread.sleep(200);}catch(Exception ee){}
			    			 
			    			}//end while
									  th.remove(Thread.currentThread());
									 // System.out.println(Thread.currentThread()+"结束15-------------------------------------------------------------------------------------");
								  }
							  }.start();
	    				  }	
    					
    				   }
    				////////////////////////
    				
    				
    				
    				}//end 载具放行=1
    				
    				
    			}
    	
    			
    		}
    		
    		//更新第个二个队列
    		for(int i=16;i<32;i++){ 
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
    				SqlPro.getLog().error("取料完成2 secendST in getFromPlc-在载具"+getWrPLC(装配区).get(i).firstST.getName()+"->"+(i-16)+"ST");
    				if(取料完成1==1){
    					 int 剩余数量=getWrPLC(装配区).get(i-16).firstST.get剩余数量();
    					// if(getCarryLine(装配区).getCarry(i-16)!=null)
    					if(剩余数量!=0){//说明第二个队列已经移到第一个队列了,因为必须执行完第一个队列后才能执行第二个队列
    					   getWrPLC(装配区).get(i).firstST.set数据处理中(true);
    					   getWrPLC(装配区).get(i-16).updataDB(getWrPLC(装配区).get(i-16).firstST);//更新托盘的数量
    					  
    					   }else{
    						   getWrPLC(装配区).get(i).secondST.set数据处理中(true);  
    					       getWrPLC(装配区).get(i-16).updataDB(getWrPLC(装配区).get(i-16).secondST);//更新托盘的数量
    					    
    					   }
    					
    				}
    				
    			}
    			
    			
    		}
    	
    	
    	}catch(Exception ex){
    		SqlPro.getLog().error(ex.getMessage());
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
		   
		   if(smm[7-(st-2)-1].split("=")[1].equals("0")){
			  
			   STC1.get(st-1).firstST.set立库RDY(false);
			   STC1.get(st-1).secondST.set立库RDY(false);
			   到位1=false;
			   //return false;  
		   }else{
			   Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 63, 16, line);
			   Resint bint=r[(st-1)*2];
   			   int tem1=bint.getResInt();
   			   int 托盘回大库=(tem1&0b10000)==16?1:0;//PLC请求托盘回库信息
   			   if(托盘回大库==1){//把托盘出库
   				String sb=SqlTool.findOneRecord("select  托盘编号,物料,数量,货位号  from 库存托盘   where  货位号='"+gw1[st-2]+"'");
   				if(sb!=null){
   					String sm[]=sb.split("!_!");
   					if(A区输送线自动请求打开){
   				    String bb=SqlTool.add动作指令(sm[0], sm[3], 人工点, "输送线回流", 1, line+"");
   				    String bb2=SqlTool.insert(new String[]{"UPDATE 库存托盘 set 数量=0 where 托盘编号='"+sm[0]+"'"});
   				    }
   				   
   				    // STC1.get(st-1).firstST.set立库RDY(false);
				   //  STC1.get(st-1).secondST.set立库RDY(false); 
   				    return false; 
   				    }
   			      }else{
   			    	  
			        到位1=true;
			      }
			     
			     
		   }
		   
	   }else{
		   String smm[]=getSTATE2().split("\\|");
		   
		   if(smm[7-(st-2)-1].split("=")[1].equals("0")){
			   STC2.get(st-1).firstST.set立库RDY(false);
			   STC2.get(st-1).secondST.set立库RDY(false);
			   到位2=false;
			  // return false;  
		   }else{
			   Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 63, 16, line);
			   Resint bint=r[(st-1)*2];
   			   int tem1=bint.getResInt();
   			   int 托盘回大库=(tem1&0b10000)==16?1:0;
   			   if(托盘回大库==1){//把托盘出库
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
		   SqlPro.getLog().error(ex.getMessage());
		   ex.printStackTrace();}
	   
	 
	   
	   
	    
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
			{
				STC1.get(i).firstST.clear();
				STC1.get(i).secondST.clear();
				STC1.get(i).firstST.writeifChangeToPLC();
				STC1.get(i).secondST.writeifChangeToPLC();
				
			}
		     }else{
		    	 
		    	 for(int i=0;i<STC2.size();i++)
					{
						STC2.get(i).firstST.clear();
						STC2.get(i).secondST.clear();
						STC1.get(2).firstST.writeifChangeToPLC();
						STC1.get(2).secondST.writeifChangeToPLC();
					}	 
		     }
		
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
}
