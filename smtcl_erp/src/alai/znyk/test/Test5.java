package alai.znyk.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread t1=new Thread(){public void run(){
			while(true){
				
				System.out.println(1);
				try{
				Thread.sleep(1000);
				}catch(Exception e){}
			}
			
			
		}};
		
		Thread t2=new Thread(){public void run(){
			while(true){
				
				System.out.println(2);
				try{
					Thread.sleep(1000);
					}catch(Exception e){}
			}
			
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
