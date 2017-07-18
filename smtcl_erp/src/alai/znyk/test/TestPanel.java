package alai.znyk.test;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import alai.GDT.Resint;
import alai.znyk.common.ClientSer;
import alai.znyk.common.SqlPro;
import alai.znyk.plc.Carry;
import alai.znyk.plc.CarryLine;
import alai.znyk.plc.PLC;
import alai.znyk.plc.ReST;
import alai.znyk.server.SqlTool;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class TestPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	JTextArea textArea = new JTextArea();
	Hashtable<Thread,String> th=new Hashtable<Thread,String>() ;
	public TestPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		JButton button = new JButton("\u6062\u590D\u8BA2\u5355");
		panel.add(button);
		
		JButton button_2 = new JButton("\u542F\u52A8\u8C03\u5EA6");
		panel.add(button_2);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(button_2.getText().equals("启动调度")){
				  PLC.getIntance().setDiaodu1(false);
				  button_2.setText("停止调度");
				    }else{
				  PLC.getIntance().setDiaodu1(true);
				  button_2.setText("启动调度");   	
				    	
				    }
			}
		});
		
		JButton button_1 = new JButton("\u542F\u52A8\u6D4B\u8BD5");
		panel.add(button_1);
		
		JButton button_3 = new JButton("\u6E05\u9664\u5149\u5927\u5730\u5740");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int bac=ClientSer.getIntance().writeSirIntToCTR("D11001", 64, new int[64],  1)  ;
				if(bac>-1){
					System.out.println("====="+bac);
				}
			}
		});
		panel.add(button_3);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(textArea);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(button_1.getText().equals("启动测试")){
				   start();
				  button_1.setText("停止测试");
				  }else{
					  isStart=false; 
					  button_1.setText("启动测试");
				  }
				
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql="update 配方指令队列 set 前升读标志=NULL, ST读取标志=NULL, 完成数量=0";
				SqlTool.insert(new String[]{sql});
				CarryLine l=PLC.getIntance().line;
				for(int i=0;i<l.getLength();i++){
					l.setCarryAt(null, i);
				}
				    l.setBuffer(null);
				    PLC.getIntance().reLoad(1);
			}
		});

	}
	
	public void setText(String s){
		textArea.append(s+"\n");
		
	}
	boolean isStart=false;
	public void start(){
		     if(isStart) return;
		       isStart=true;
			 if(isStart){
				 
				 new Thread(){
					 public void run(){
						 while(true&&isStart){
							 exeCarry();
							
							 try {
								
								Thread.sleep(50);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							 
						 }	 
					 }
				 }.start();
			 }
			 
		
		
	   }
	
	int ss=0;
	Hashtable<String,Long> table=new Hashtable<String,Long>();
	public void exeCarry(){
		/*
		try{
			PLC plc=PLC.getIntance();
		      CarryLine lin= plc.line;
		      for(int i=lin.getLength()-1;i>-1;i--){
		    	 Carry car= lin.getCarry(i);
		    	 if(car!=null){
		    		// System.out.println(i+"工位="+car.get模组层数());
		    		  if(car.get模组层数()!=100){
		    			 
		    			  if(lin.getCarry(i+1)==null){
		    				
		    				  final int tem=i;
		    				  car.set模组层数(100);
		    				
		 //    new Thread(){
		    	      final Carry car2=car;
		    	      final int a=tem;
		    //	 public void run(){
		    	             
		    	           //final Carry car2=lin.getCarry(a);
		    	          
		    			
		    				Resint r[]=	ClientSer.getIntance().getReturnPlc("D11001", 64, 16, 1);
		    				//System.out.println(r.length+"---------------------");
		    				Resint bint=r[a*2];
		        			ReST RS=new ReST(new Resint(bint.resInt));
		        			RS.setMachineID(1);
		        			RS.startAddres=("D"+(11001+a*2));
		        			if(car2.getName().equals(PLC.getIntance().STC1.get(a).firstST.getName())){
		        				RS.set动作完成(true);
			        			RS.writeToPLC();
			    				try {
									//Thread.sleep(1000);
								} catch (Exception e) {
									
									e.printStackTrace();
								}
			    				
		    				  	
		    					
		    			 	 }
		        			
		        			//ClientSer.STC1.get(a).firstST.setPLC(plc);
		        		   // ClientSer.STC1.get(a).firstST.updataFromPLC();
		    			   	
		    			if(!ClientSer.STC1.get(a).firstST.is数据更新完成())
		    				{
		    				    
		    			  	    RS.set载具放行(true);
		    			  	    String sb= RS.writeToPLC();
		    			  	   //   Thread.sleep(100);
		    			  	   if(!sb.contains("成功"))
		    			  	    SqlPro.getLog().error("PLC写入数据="+sb);
		    			  	    
		    			  	   table.put(a+"", System.currentTimeMillis());
		    			  	  
		    			  	  if(sb.contains("成功"))
		    	new Thread(){
		    		    final int a3=a;
		    		    final Carry car3=car;
		    		    final ReST R=RS;
		    		    long time1=System.currentTimeMillis();
		    			  	public void run(){
		    			  		int j=0;
		    			  		int t=0;
		    			  		//setText(car3.getName());
		    			  		ss=1;
		    			  		while(true){
		    			  		 // ClientSer.STC1.get(a3).firstST.updataFromPLC();
		    			  		//	System.out.println(PLC.STC1.get(a3).firstST);
		    			  			if(ClientSer.STC1.get(a3).firstST.is数据更新完成())
		    			  			  {   R.set动作完成(false);
		    			  			 //  table.remove(a3+"");
		    			  			    if(a3==5){
		    			  			    	
		    			  			    	if(car3.getName().equals(PLC.STC1.get(a3).firstST.getName()))
		    			  			    	{
		    			  			    	 R.set动作完成(false);
				    			  			 R.set载具放行(false);
		    			  			    	 R.set放料完成(true);
		    			  			    	 R.writeToPLC();
		    			  			    	 
		    			  			    	}
		    			  			    	else{
		    			  			    		 R.set动作完成(false);
					    			  			 R.set载具放行(false);
			    			  			    	 R.set放料完成(true);
			    			  			    	 R.writeToPLC(); 
		    			  			    	 }
		    			  			    	car3.set模组层数(1);
		    			  			    	break; 
		    			  			         }
		    			  			    else{
		    			  			    	
		    			  			    	 R.set动作完成(false);
				    			  			 R.set载具放行(false);	
				    			  			 String sb2=R.writeToPLC();
				    			  			 car3.set模组层数(1);
				    			  			  if(!sb2.contains("成功"))
				   		    			  	   SqlPro.getLog().error("PLC写入数据="+sb2);
				    			  			break;   
		    			  			    }
		    			  			   
		    			  				
		    			  				}
		    			  			long tt1=System.currentTimeMillis()-time1;
		    			  			//JOptionPane.showMessageDialog(null,a3+"ST 第"+j+" 次数据更新完成等待True超时 "+car3.get模组层数()+"||"+tt1+"||"+(System.currentTimeMillis()-time1)+"||"+PLC.getIntance().STC1.get(a3).firstST.toString()+PLC.getIntance().th);
		    			  			if(tt1>3000) {
		    			  			//数据更新完成等待True超时	
		    			  				j++;
		    			  			//	JOptionPane.showMessageDialog(null,a3+"ST 第"+j+" 次数据更新完成等待True超时 "+car3.get模组层数()+"||"+tt1+"||"+(System.currentTimeMillis()-time1)+"||"+PLC.getIntance().STC1.get(a3).firstST.toString()+PLC.getIntance().th);
		    			  				setText(a3+"ST 第"+j+" 次数据更新完成等待True超时 "+car3.get模组层数()+"||"+tt1+"||"+(System.currentTimeMillis()-time1)+"||"+ClientSer.STC1.get(a3).firstST.toString()+plc.th);
		    			  				
		    			  				try{Thread.sleep(1000);}catch(Exception e){}
		    			  				
		    			  				if(j>1){
		    			  				 //isStart=false;
		    			  				 setText(Thread.currentThread()+"等待数据处理完成信号处理线程被停止");
		    			  		    	// break;
		    			  				}
		    			  				
		    			  			}
		    			  //	if(t%5==0)	{System.out.println(Thread.currentThread()+"-等待数据更新完成变为TRUE");}
		    			    	t++;
		    			  			try {Thread.sleep(200);} catch (InterruptedException e) { }
						
		    			  		  }
		    			  	  }	
		    			  		
		    	    }.start();
		    	    
		    	  //  try {Thread.sleep(1000);} catch (InterruptedException e) {}
		    	
		    				  }
		    				else{
		    				long tt=table.get(a+"")==null?System.currentTimeMillis():table.get(a+"");
		    				//	if(ClientSer.STC1.get(a).firstST.is允许工位动作标志())
		    			             setText(a+"ST数据更新完成等待OFF超时   模组层数="+car2.get模组层数()+"延时="+
		    				(System.currentTimeMillis()-tt));	
		    			             car2.set模组层数(1);
		    					    // isStart=false;
		    			  	}
		    			  	
		    				
		    				  
		    				  
		    //   } }.start();
		    				
		   
		    				
		    				 
		    			    }
		    			  
		    		  }else{
		    			//  System.out.println(i+"ST 层数="+car.get模组层数());  
		    		  }
		    		  
		    		 
		    	 }//end IF
		    	 
		    	 Thread.sleep(1);
		    	// if( i==0){ i=lin.getLength()-1;}
		    	  
		      }
		   }catch(Exception ex){ex.printStackTrace();}
		   */
	    }
}
