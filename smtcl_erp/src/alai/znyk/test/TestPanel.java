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
				if(button_2.getText().equals("��������")){
				  PLC.getIntance().setDiaodu1(false);
				  button_2.setText("ֹͣ����");
				    }else{
				  PLC.getIntance().setDiaodu1(true);
				  button_2.setText("��������");   	
				    	
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
				if(button_1.getText().equals("��������")){
				   start();
				  button_1.setText("ֹͣ����");
				  }else{
					  isStart=false; 
					  button_1.setText("��������");
				  }
				
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql="update �䷽ָ����� set ǰ������־=NULL, ST��ȡ��־=NULL, �������=0";
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
		    		// System.out.println(i+"��λ="+car.getģ�����());
		    		  if(car.getģ�����()!=100){
		    			 
		    			  if(lin.getCarry(i+1)==null){
		    				
		    				  final int tem=i;
		    				  car.setģ�����(100);
		    				
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
		        				RS.set�������(true);
			        			RS.writeToPLC();
			    				try {
									//Thread.sleep(1000);
								} catch (Exception e) {
									
									e.printStackTrace();
								}
			    				
		    				  	
		    					
		    			 	 }
		        			
		        			//ClientSer.STC1.get(a).firstST.setPLC(plc);
		        		   // ClientSer.STC1.get(a).firstST.updataFromPLC();
		    			   	
		    			if(!ClientSer.STC1.get(a).firstST.is���ݸ������())
		    				{
		    				    
		    			  	    RS.set�ؾ߷���(true);
		    			  	    String sb= RS.writeToPLC();
		    			  	   //   Thread.sleep(100);
		    			  	   if(!sb.contains("�ɹ�"))
		    			  	    SqlPro.getLog().error("PLCд������="+sb);
		    			  	    
		    			  	   table.put(a+"", System.currentTimeMillis());
		    			  	  
		    			  	  if(sb.contains("�ɹ�"))
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
		    			  			if(ClientSer.STC1.get(a3).firstST.is���ݸ������())
		    			  			  {   R.set�������(false);
		    			  			 //  table.remove(a3+"");
		    			  			    if(a3==5){
		    			  			    	
		    			  			    	if(car3.getName().equals(PLC.STC1.get(a3).firstST.getName()))
		    			  			    	{
		    			  			    	 R.set�������(false);
				    			  			 R.set�ؾ߷���(false);
		    			  			    	 R.set�������(true);
		    			  			    	 R.writeToPLC();
		    			  			    	 
		    			  			    	}
		    			  			    	else{
		    			  			    		 R.set�������(false);
					    			  			 R.set�ؾ߷���(false);
			    			  			    	 R.set�������(true);
			    			  			    	 R.writeToPLC(); 
		    			  			    	 }
		    			  			    	car3.setģ�����(1);
		    			  			    	break; 
		    			  			         }
		    			  			    else{
		    			  			    	
		    			  			    	 R.set�������(false);
				    			  			 R.set�ؾ߷���(false);	
				    			  			 String sb2=R.writeToPLC();
				    			  			 car3.setģ�����(1);
				    			  			  if(!sb2.contains("�ɹ�"))
				   		    			  	   SqlPro.getLog().error("PLCд������="+sb2);
				    			  			break;   
		    			  			    }
		    			  			   
		    			  				
		    			  				}
		    			  			long tt1=System.currentTimeMillis()-time1;
		    			  			//JOptionPane.showMessageDialog(null,a3+"ST ��"+j+" �����ݸ�����ɵȴ�True��ʱ "+car3.getģ�����()+"||"+tt1+"||"+(System.currentTimeMillis()-time1)+"||"+PLC.getIntance().STC1.get(a3).firstST.toString()+PLC.getIntance().th);
		    			  			if(tt1>3000) {
		    			  			//���ݸ�����ɵȴ�True��ʱ	
		    			  				j++;
		    			  			//	JOptionPane.showMessageDialog(null,a3+"ST ��"+j+" �����ݸ�����ɵȴ�True��ʱ "+car3.getģ�����()+"||"+tt1+"||"+(System.currentTimeMillis()-time1)+"||"+PLC.getIntance().STC1.get(a3).firstST.toString()+PLC.getIntance().th);
		    			  				setText(a3+"ST ��"+j+" �����ݸ�����ɵȴ�True��ʱ "+car3.getģ�����()+"||"+tt1+"||"+(System.currentTimeMillis()-time1)+"||"+ClientSer.STC1.get(a3).firstST.toString()+plc.th);
		    			  				
		    			  				try{Thread.sleep(1000);}catch(Exception e){}
		    			  				
		    			  				if(j>1){
		    			  				 //isStart=false;
		    			  				 setText(Thread.currentThread()+"�ȴ����ݴ�������źŴ����̱߳�ֹͣ");
		    			  		    	// break;
		    			  				}
		    			  				
		    			  			}
		    			  //	if(t%5==0)	{System.out.println(Thread.currentThread()+"-�ȴ����ݸ�����ɱ�ΪTRUE");}
		    			    	t++;
		    			  			try {Thread.sleep(200);} catch (InterruptedException e) { }
						
		    			  		  }
		    			  	  }	
		    			  		
		    	    }.start();
		    	    
		    	  //  try {Thread.sleep(1000);} catch (InterruptedException e) {}
		    	
		    				  }
		    				else{
		    				long tt=table.get(a+"")==null?System.currentTimeMillis():table.get(a+"");
		    				//	if(ClientSer.STC1.get(a).firstST.is����λ������־())
		    			             setText(a+"ST���ݸ�����ɵȴ�OFF��ʱ   ģ�����="+car2.getģ�����()+"��ʱ="+
		    				(System.currentTimeMillis()-tt));	
		    			             car2.setģ�����(1);
		    					    // isStart=false;
		    			  	}
		    			  	
		    				
		    				  
		    				  
		    //   } }.start();
		    				
		   
		    				
		    				 
		    			    }
		    			  
		    		  }else{
		    			//  System.out.println(i+"ST ����="+car.getģ�����());  
		    		  }
		    		  
		    		 
		    	 }//end IF
		    	 
		    	 Thread.sleep(1);
		    	// if( i==0){ i=lin.getLength()-1;}
		    	  
		      }
		   }catch(Exception ex){ex.printStackTrace();}
		   */
	    }
}
