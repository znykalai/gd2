package alai.znyk.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import alai.znyk.common.ClientSer;
import alai.znyk.server.SqlTool;

public class Test5 {

	public static void main(String[] args) {
		try{
			//ClientSer.getIntance();//.getState(2);
			//String ss="成功";
			System.out.println(3001%3000);
//			System.out.println(Integer.toBinaryString(12));
//			String ss=Integer.toBinaryString(12);
//			
//			char index0=ss.charAt(0);
//			
//			if(index0=='1'){
//				System.out.println(index0);
//			}
//			
//			if((index0+"").equals("1")){
//				System.out.println(index0);
//				
//			}
//			
//			if(true)
//			return;
			//String s=SqlTool.findOneRecord("Select ID,物料编码  from 托盘物料map where 托盘编号='"+2001+"'");
		
			//System.out.println(s);
			//String tp=ClientSer.getIntance().ReadFromRffid("", 1);
		// TODO Auto-generated method stub
		//System.out.println("tp="+tp+"p"+(tp.equals("")));
		
		}catch(Exception e){e.printStackTrace();}
		
		
		Thread t1=new Thread(){public void run(){
			  // c.print(1);
			
		}};
		
		Thread t2=new Thread(){public void run(){
			// c.print(20);
			
		}};
		t1.start();
		t2.start();
		
		JFrame f=new JFrame();
		JButton b=new JButton("22");
		f.getContentPane().add(b);
		
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				t1.stop();
				
				System.out.println(t1.isDaemon());
				
				
			}});
		   f.pack();
		   f.setVisible(true);
		

	}

}
