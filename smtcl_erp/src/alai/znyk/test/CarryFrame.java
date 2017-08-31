package alai.znyk.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CarryFrame extends JPanel {

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
		
		setBounds(100, 100, 1203, 124);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout());
		this.add(contentPane);
		contentPane.setLayout(new GridLayout(0, 15, 0, 0));
		
		JButton button = new JButton("1");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button,e);
			}
		});
		
	
		contentPane.add(button);
		buts.addElement(button);
		
		JButton button_1 = new JButton("2");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_1,e);
			}
		});
		contentPane.add(button_1);
		buts.addElement(button_1);
		
		JButton button_2 = new JButton("3");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_2,e);
			}
		});
		contentPane.add(button_2);
		buts.addElement(button_2);
		
		JButton button_3 = new JButton("4");
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_3,e);
			}
		});
		contentPane.add(button_3);
		buts.addElement(button_3);
		
		JButton button_4 = new JButton("5");
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_4,e);
			}
		});
		contentPane.add(button_4);
		buts.addElement(button_4);
		
		JButton button_5 = new JButton("6");
		button_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_5,e);
			}
		});
		contentPane.add(button_5);
		buts.addElement(button_5);
		
		JButton button_6 = new JButton("7");
		button_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_6,e);
			}
		});
		contentPane.add(button_6);
		buts.addElement(button_6);
		
		JButton button_7 = new JButton("8");
		button_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_7,e);
			}
		});
		contentPane.add(button_7);
		buts.addElement(button_7);
		
		JButton button_8 = new JButton("9");
		button_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_8,e);
			}
		});
		contentPane.add(button_8);
		buts.addElement(button_8);
		
		JButton button_9 = new JButton("10");
		button_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_9,e);
			}
		});
		contentPane.add(button_9);
		buts.addElement(button_9);
		
		JButton button_10 = new JButton("11");
		button_10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_10,e);
			}
		});
		contentPane.add(button_10);
		buts.addElement(button_10);
		
		JButton button_11 = new JButton("12");
		button_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_11,e);
			}
		});
		contentPane.add(button_11);
		buts.addElement(button_11);
		
		JButton button_12 = new JButton("13");
		button_12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_12,e);
			}
		});
		contentPane.add(button_12);
		buts.addElement(button_12);
		
		JButton button_13 = new JButton("14");
		button_13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_13,e);
			}
		});
		contentPane.add(button_13);
		buts.addElement(button_13);
		
		JButton button_14 = new JButton("15");
		button_14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click(button_14,e);
			}
		});
		contentPane.add(button_14);
		buts.addElement(button_14);
		new Thread(){public void run(){
			
			while(true){
				init();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}}.start();
		
		
	}

	public void click(JButton but,MouseEvent e){
		if(1==1)
		return;
		CarryLine li=PLC.getIntance().line;
		int col=buts.lastIndexOf(but);
		if(li.getCarry(col)==null)return;
		if(li.removeToNext(col)){
			 STContent st=PLC.getIntance().STC1.get(col);
			 if(st.stNum==1){
				 st.firstST.setWrite(false); 
			 }else{
		if(e.getButton()==MouseEvent.BUTTON3){
		    System.out.println("=====MOVE"); 
		   
		    st.firstST.setWrite(false);
		    }
		
		   }
			 }
		    init();
		
	}
	public void init(){
		CarryLine li=PLC.getIntance().line;
		for(int i=0;i<buts.size();i++){
			if(li.getCarry(i)==null){
				buts.get(i).setFont(new Font("宋体", Font.PLAIN, 11));
				buts.get(i).setText((i)+"工位");
				buts.get(i).setEnabled(false);
			}else{
				buts.get(i).setFont(new Font("宋体", Font.PLAIN, 10));
				buts.get(i).setToolTipText(li.getCarry(i).get载具序号()+"-"+li.getCarry(i).get工位());
				buts.get(i).setText(li.getCarry(i).get载具序号()+"-"+li.getCarry(i).getName().replace("=", "|"));
				buts.get(i).setEnabled(true);
				
			}
			
		}
		
	}
}
