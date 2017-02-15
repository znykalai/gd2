package alai.znyk.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import alai.znyk.plc.PLC;
import alai.znyk.plc.STContent;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class PLCFrame extends JFrame {
	static{
		   try{
		   UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());   
		   }catch(Exception ex){}
	   }
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PLCFrame frame = new PLCFrame();
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
	public PLCFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1368, 714);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_18 = new JPanel();
		contentPane.add(panel_18, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("\u5237\u65B0");
		panel_18.add(btnNewButton);
		
		JButton btnCarry = new JButton("carry");
		btnCarry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CarryFrame().setVisible(true);
			}
		});
		panel_18.add(btnCarry);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(1600,780));
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("◊∞≈‰«¯A_plc", null, panel, null);
		panel.setLayout(null);
		
		STPanel panel_3 = new STPanel(PLC.getIntance().ST0_1);
		panel_3.setBounds(0, 0, 255, 210);
		panel.add(panel_3);
		
		STPanel panel_4 = new STPanel(PLC.getIntance().ST1_1);
		panel_4.setBounds(265, 0, 255, 210);
		panel.add(panel_4);
		
		STPanel panel_5 = new STPanel(PLC.getIntance().ST2_1);
		panel_5.setBounds(530, 0, 255, 210);
		panel.add(panel_5);
		
		STPanel panel_6 = new STPanel(PLC.getIntance().ST3_1);
		panel_6.setBounds(795, 0, 255, 210);
		panel.add(panel_6);
		
		STPanel panel_7 = new STPanel(PLC.getIntance().ST4_1);
		panel_7.setBounds(1060, 0, 255, 210);
		panel.add(panel_7);
		
		STPanel panel_8 = new STPanel(PLC.getIntance().ST5_1);
		panel_8.setBounds(0, 208, 255, 210);
		panel.add(panel_8);
		
		STPanel panel_9 = new STPanel(PLC.getIntance().ST6_1);
		panel_9.setBounds(265, 208, 255, 210);
		panel.add(panel_9);
		
		STPanel panel_10 = new STPanel(PLC.getIntance().ST7_1);
		panel_10.setBounds(530, 208, 255, 210);
		panel.add(panel_10);
		
		STPanel panel_11 = new STPanel(PLC.getIntance().ST8_1);
		panel_11.setBounds(795, 208, 255, 210);
		panel.add(panel_11);
		
		STPanel panel_12 = new STPanel(PLC.getIntance().ST9_1);
		panel_12.setBounds(1060, 208, 255, 210);
		panel.add(panel_12);
		
		STPanel panel_13 = new STPanel(PLC.getIntance().ST10_1);
		panel_13.setBounds(0, 417, 255, 210);
		panel.add(panel_13);
		
		STPanel panel_14 = new STPanel(PLC.getIntance().ST11_1);
		panel_14.setBounds(265, 417, 255, 210);
		panel.add(panel_14);
		
		STPanel panel_15 = new STPanel(PLC.getIntance().ST12_1);
		panel_15.setBounds(530, 417, 255, 210);
		panel.add(panel_15);
		
		STPanel panel_16 = new STPanel(PLC.getIntance().ST13_1);
		panel_16.setBounds(795, 417, 255, 210);
		panel.add(panel_16);
		
		STPanel panel_17 = new STPanel(PLC.getIntance().ST15_1);
		panel_17.setBounds(1060, 417, 255, 210);
		panel.add(panel_17);
		//contentPane.add(tabbedPane, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("≤‚ ‘2", null, panel_2, null);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		CommentPanel pann=new CommentPanel();
		splitPane.setLeftComponent(pann);
	
		scrollPane.setViewportView(tabbedPane);
		//splitPane.setDividerLocation(100);
		splitPane.setDividerLocation(0.8);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(start!=0){return;}
				start=1;
				new Thread(){
				public void run(){
				try{
					while(true){
				long time=System.currentTimeMillis();		
				panel_3.initPanel();panel_4.initPanel();
				panel_5.initPanel();panel_6.initPanel();
				panel_7.initPanel();panel_8.initPanel();
				panel_9.initPanel();panel_10.initPanel();
				panel_11.initPanel();panel_12.initPanel();
				panel_13.initPanel();panel_14.initPanel();
				panel_15.initPanel();panel_16.initPanel();
				panel_17.initPanel();
				//System.out.println(System.currentTimeMillis()-time);
				Thread.sleep(100);}
				}catch(Exception ex){}
				}
			}.start();
			}
			});
	}
	public  int start =0;
}
