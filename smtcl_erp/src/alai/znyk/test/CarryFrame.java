package alai.znyk.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import alai.znyk.plc.CarryLine;
import alai.znyk.plc.PLC;
import alai.znyk.plc.STContent;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CarryFrame extends JFrame {

	private JPanel contentPane;
	Vector<JButton> buts=new Vector<JButton>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CarryFrame frame = new CarryFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CarryFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1203, 124);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 15, 0, 0));
		
		JButton button = new JButton("1");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button);
			}
		});
		contentPane.add(button);
		buts.addElement(button);
		
		JButton button_1 = new JButton("2");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_1);
			}
		});
		contentPane.add(button_1);
		buts.addElement(button_1);
		
		JButton button_2 = new JButton("3");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_2);
			}
		});
		contentPane.add(button_2);
		buts.addElement(button_2);
		
		JButton button_3 = new JButton("4");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_3);
			}
		});
		contentPane.add(button_3);
		buts.addElement(button_3);
		
		JButton button_4 = new JButton("5");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_4);
			}
		});
		contentPane.add(button_4);
		buts.addElement(button_4);
		
		JButton button_5 = new JButton("6");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_5);
			}
		});
		contentPane.add(button_5);
		buts.addElement(button_5);
		
		JButton button_6 = new JButton("7");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_6);
			}
		});
		contentPane.add(button_6);
		buts.addElement(button_6);
		
		JButton button_7 = new JButton("8");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_7);
			}
		});
		contentPane.add(button_7);
		buts.addElement(button_7);
		
		JButton button_8 = new JButton("9");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_8);
			}
		});
		contentPane.add(button_8);
		buts.addElement(button_8);
		
		JButton button_9 = new JButton("10");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_9);
			}
		});
		contentPane.add(button_9);
		buts.addElement(button_9);
		
		JButton button_10 = new JButton("11");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_10);
			}
		});
		contentPane.add(button_10);
		buts.addElement(button_10);
		
		JButton button_11 = new JButton("12");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_11);
			}
		});
		contentPane.add(button_11);
		buts.addElement(button_11);
		
		JButton button_12 = new JButton("13");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_12);
			}
		});
		contentPane.add(button_12);
		buts.addElement(button_12);
		
		JButton button_13 = new JButton("14");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_13);
			}
		});
		contentPane.add(button_13);
		buts.addElement(button_13);
		
		JButton button_14 = new JButton("15");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click(button_14);
			}
		});
		contentPane.add(button_14);
		buts.addElement(button_14);
		new Thread(){public void run(){
			
			while(true){
				init();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}}.start();
		
		
	}

	public void click(JButton but){
		CarryLine li=PLC.getIntance().line;
		
		int col=buts.lastIndexOf(but);
		li.removeToNext(col);
		STContent st=PLC.getIntance().STC1.get(col);
		st.firstST.setWrite(false);
		init();
		
	}
	public void init(){
		CarryLine li=PLC.getIntance().line;
		for(int i=0;i<buts.size();i++){
			if(li.getCarry(i)==null){
				buts.get(i).setText("");
				buts.get(i).setEnabled(false);
			}else{
				buts.get(i).setText(li.getCarry(i).getÔØ¾ßÐòºÅ()+"");
				buts.get(i).setEnabled(true);
				
			}
			
		}
		
	}
}
