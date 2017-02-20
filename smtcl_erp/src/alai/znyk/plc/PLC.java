package alai.znyk.plc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

import alai.GDT.Inint;
import alai.GDT.Resint;
import alai.localhost.GD_wsdl.GDLocator;
import alai.znyk.common.ClientSer;
import alai.znyk.server.SqlTool;

public class PLC implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean stop1=false;
	public boolean stop2=false;
	
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
	
	public int[]gw1=new int[]{502,504,506,508,510,512,514};
	public int[]gw2=new int[]{602,604,606,608,610,612,614};
	
	private  PLC(){
		  PLC pp=readO();
		  if(pp!=null){
			  INSTANCE=pp;
			// String ss= INSTANCE.STC1.get(3).firstST.get物料编码();
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
	
	public void startTiaodu(){
		new Thread(){
			public void run(){
			while(true){
				try{
				STATE1=ClientSer.getIntance().getState(10);
				STATE2=ClientSer.getIntance().getState(11);
				}catch(Exception e){e.printStackTrace();}
				ST0_1.initFromSql();
				getSTRdy(1,2);
				ST1_1.initFromSql();
				getSTRdy(1,3);
				ST2_1.initFromSql();
				getSTRdy(1,4);
				ST3_1.initFromSql();
				getSTRdy(1,5);
				ST4_1.initFromSql();
				getSTRdy(1,6);
				ST5_1.initFromSql();
				getSTRdy(1,7);
				ST6_1.initFromSql();
				getSTRdy(1,8);
				ST7_1.initFromSql();
				ST8_1.initFromSql();
				ST9_1.initFromSql();
				ST10_1.initFromSql();
				ST11_1.initFromSql();
				ST12_1.initFromSql();
				ST13_1.initFromSql();
				ST14_1.initFromSql();
				ST15_1.initFromSql();
				getFromPLC(1);
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
	
	//读取光大的状态，并更新托盘数量
    public ReST[] getFromPLC(int 装配区){
    	try{
    		Resint r[]=	ClientSer.getIntance().getSirIntValuesFromCTR("D11001", 63, 16, 装配区);
    		
    		for(int i=0;i<16;i++){
    			
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
    			if(取料完成1!=取料完成2){
    				//更新托盘的物料数量
    				if(取料完成1==1){
    					//检测本工位有没有托盘
    					if(getCarryLine(装配区).getCarry(i)!=null){
    					getWrPLC(装配区).get(i).updataDB(getWrPLC(装配区).get(i).firstST);//更新托盘的数量
    				 
    				    System.out.println("取料完成1");}
    				
    				}
    				
    			}
    			
    			if(载具放行1!=载具放行2){
    				
    				
    				//更新托盘位置，同时把write置成false,
    				if(载具放行1==1){
    				if(i<15){
    					Carry car=getCarryLine(装配区).getCarry(i);
    					if(car!=null){
    					if(car.getName().equals(getWrPLC(装配区).get(i).firstST.getName())){
    						//如果这个托盘是本工位需要的托盘
    						 if( getWrPLC(装配区).get(i).firstST.get剩余数量()==0){
    						  System.out.println("载具放行1");
    						  getWrPLC(装配区).get(i).firstST.set数据更新完成(true);
    						   String back=STC1.get(i).firstST.writeifChangeToPLC();
    						  if(back.contains("成功")){
    							  //写入PLC成功后
    	    				 if( getCarryLine(装配区).removeToNext(i))
    	    					 getWrPLC(装配区).get(i).firstST.setWrite(false);
    	    				  }
    						  
    						 }
    					}
    					else{
    						getCarryLine(装配区).removeToNext(i);
    	    				  
    					}
    					
    					}
    				 //更新到PLC,由initFromSql()自动完成
    			       }
    				
    				
    				
    				
    				}
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
    				if(取料完成1==1){
    					if(getCarryLine(装配区).getCarry(i)!=null)
    					getWrPLC(装配区).get(i).updataDB(getWrPLC(装配区).get(i).secondST);//更新托盘的数量
    					
    				}
    				
    			}
    			
    			
    		}
    	
    	
    	}catch(Exception ex){ex.printStackTrace();}
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
	   if(i!=-1){
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
	  if(st>=2&&st<=8){
	   if(line==1){
		   
		   String smm[]=STATE1.split("\\|");
		   if(st-2==0){
			 //  System.out.println("=="+STATE1);
		   }
		   if(smm[st-2].split("=")[1].equals("0")){
			  
			   STC1.get(st-1).firstST.set立库RDY(false);
			   STC1.get(st-1).secondST.set立库RDY(false);
			   return false;  
		   }
		   
	   }else{
		   String smm[]=STATE2.split("\\|");
		   if(smm[st-2].split("=")[1].equals("0")){
			   STC2.get(st-1).firstST.set立库RDY(false);
			   STC2.get(st-1).secondST.set立库RDY(false);
			   return false;  
		   }
		   
	   }
	   }
	   
	 
	   if(st>=2&&st<=7){
		  String tem2=null;
		  STContent cot= STC1.get(st-1);
		  if(line==1){
			  tem2=SqlTool.findOneRecord("select  托盘编号,物料,数量  from 库存托盘   where  货位号='"+gw1[st-2]+"'"); 
			  cot= STC1.get(st-1);
		  }else{
			  tem2=SqlTool.findOneRecord("select  托盘编号,物料,数量  from 库存托盘   where  货位号='"+gw2[st-2]+"'"); 
			  cot= STC2.get(st-1);  
			  
		  }
		  
		  if(tem2==null){//没有托盘的情况
			  cot.firstST.set立库RDY(false);
			  cot.secondST.set立库RDY(false);
			  return false;}
		 
		  else{
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
		       if(tem==null)tem="-1!_!-1";
		       if(sm[1].equals(tem.split("!_!")[0])){//判断队列需要的物料，跟当前工位托盘的物料是不是一样
		    	   
		    	   if(需求数量-完成数量>tpshul){
					   //叫托盘回流
		    		   cot.firstST.set立库RDY(false);
					    return false;
					   }else{
					   cot.firstST.set立库RDY(true);  
						   return true;
					   
					   }
		    	   
		       }else{
		    	      //叫托盘回流  
		    	   cot.firstST.set立库RDY(false);
		    	   return false;  
		       }
		    }
			   else{//如果第一个队列取完了，才判断第二个队列	   
				   String tem=SqlTool.findOneRecord("select  物料,数量  from 配方指令队列   where  ID='"+id2+"'");
				   if(tem==null)tem="-1!_!-1";
				   if(sm[1].equals(tem.split("!_!")[0])){
					   int 需求数量2=((_1_6ST)cot.secondST).get需求数量();
					   int 完成数量2=((_1_6ST)cot.secondST).get完成数量();
					   if(需求数量2-完成数量2>tpshul){
						   //叫托盘回流
						   cot.secondST.set立库RDY(false);
						   return false;
						   }else{
							cot.secondST.set立库RDY(true);
							   return true;}
					   
				   }
				        else{
			    	      //叫托盘回流  
				        cot.secondST.set立库RDY(false);
				    	 return false;  
				       }
				   
			   }
		       
		       
		  } 
		   
	   }
	   
	   
	   if(st==8){//只要托盘有数量且物料相当就能返回TRUE
			 
			  String tem2=null;
			  STContent cot= STC1.get(st-1);
			  if(line==1){
				  tem2=SqlTool.findOneRecord("select  托盘编号,物料,数量  from 库存托盘   where  货位号='"+gw1[st-2]+"'"); 
				  cot= STC1.get(st-1);
			  }else{
				  tem2=SqlTool.findOneRecord("select  托盘编号,物料,数量  from 库存托盘   where  货位号='"+gw2[st-2]+"'"); 
				  cot= STC2.get(st-1);  
				  
			  }
			  if(tem2==null){//没有托盘的情况
				  cot.firstST.set立库RDY(false);
				  cot.secondST.set立库RDY(false);
				  return false;}
			 
			  else{
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
			       if(tem==null)tem="-1!_!-1";
			       if(sm[1].equals(tem.split("!_!")[0])){
			    	   if(tpshul>0){
						   //叫托盘回流
			    		   cot.firstST.set立库RDY(false);
						   return false;
						   }else{
						   cot.firstST.set立库RDY(true);
							   return true;}
			    	   
			       }else{
			    	      //叫托盘回流  
			    	   cot.firstST.set立库RDY(false);
			    	   return false;  
			       }
			    }
				   else{
					     //如果第一个队列取完了，才判断第二个队列	   
					   String tem=SqlTool.findOneRecord("select  物料,数量  from 配方指令队列   where  ID='"+id2+"'");
					   if(tem==null)tem="-1!_!-1";
					   if(sm[1].equals(tem.split("!_!")[0])){
						   int 需求数量2=((_7ST)cot.secondST).get需求数量();
						   int 完成数量2=((_7ST)cot.secondST).get完成数量();
						   if(tpshul>0){
							   //叫托盘回流
							   cot.secondST.set立库RDY(false);
							   return false;
							   }else{
								   cot.secondST.set立库RDY(true);
								   return true;
								   }
						   
					   }
					        else{
				    	      //叫托盘回流  
					        	 cot.secondST.set立库RDY(false);
					    	 return false;  
					       }
					   
				   }
			       
			       
			  } 
			      
		   
	   }
	   
	 
	   
	   
	    
	     return true;
	   
   }
   
   
}
