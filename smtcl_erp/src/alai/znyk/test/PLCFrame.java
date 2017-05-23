package alai.znyk.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import alai.znyk.common.ClientSer;
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
import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class PLCFrame extends JFrame {
	static{
		   try{
		   UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());   
		   }catch(Exception ex){}
	   }
	private JPanel contentPane;
	private DeletOrderFrame delet;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1452, 714);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_18 = new JPanel();
		contentPane.add(panel_18, BorderLayout.SOUTH);
		
		JButton button_1 = new JButton("\u5DE5\u5355\u5220\u9664");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(delet==null){delet=new DeletOrderFrame();
				delet.setVisible(true);
				}else{
					delet.setVisible(true);
					delet.getData();
					
				}
				
			}
		});
		panel_18.add(button_1);
		
		JButton btnNewButton = new JButton("\u5237\u65B0");
		panel_18.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u6E05\u9664\u5DE5\u4F4D");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PLC.getIntance().reLoad(1);
			}
		});
		panel_18.add(btnNewButton_1);
		
		JCheckBox chckbxA = new JCheckBox("A\u533A\u8F93\u9001\u7EBF\u5E38\u5F00");
		
		panel_18.add(chckbxA);
		
		JCheckBox chckbxB = new JCheckBox("B\u533A\u8F93\u9001\u7EBF\u5E38\u5F00");
		panel_18.add(chckbxB);
		chckbxA.setSelected(PLC.getIntance().A区输送线到位常有);
		chckbxB.setSelected(PLC.getIntance().B区输送线到位常有);
		
		JCheckBox chckbxA_1 = new JCheckBox("A\u533A\u8F93\u9001\u7EBF\u81EA\u52A8\u8BF7\u6C42\u6253\u5F00");
		chckbxA_1.setSelected(PLC.getIntance().A区输送线自动请求打开);
		chckbxA_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PLC.getIntance().A区输送线自动请求打开=chckbxA_1.isSelected();
			}
		});
		panel_18.add(chckbxA_1);
		
		JCheckBox chckbxB_1 = new JCheckBox("B\u533A\u8F93\u9001\u7EBF\u81EA\u52A8\u8BF7\u6C42\u6253\u5F00");
		chckbxB_1.setSelected(PLC.getIntance().B区输送线自动请求打开);
		chckbxB_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PLC.getIntance().B区输送线自动请求打开=chckbxB_1.isSelected();	
			}
		});
		panel_18.add(chckbxB_1);
		
		JLabel label = new JLabel("\u56DE\u6D41\u9600\u503C");
		panel_18.add(label);
		
		textField = new JTextField();
		panel_18.add(textField);
		textField.setColumns(10);
		textField.setText(PLC.getIntance().回流阀值+"");
		JButton button = new JButton("\u5199\u5165");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String in=textField.getText();
				try{
					PLC.getIntance().回流阀值=Integer.parseInt(in)	;
					
				}catch(Exception eex){}
			}
		});
		panel_18.add(button);
		
		JCheckBox chckbxrfid = new JCheckBox("\u6253\u5F00RFID");
		chckbxrfid.setSelected(ClientSer.isOpenRfid);
		chckbxrfid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxrfid.isSelected()){ 
					ClientSer.isOpenRfid=true;
				}else{
					ClientSer.isOpenRfid=false;
				}
			}
		});
		panel_18.add(chckbxrfid);
		
		chckbxA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PLC.getIntance().A区输送线到位常有=chckbxA.isSelected();
				System.out.println("A区输送线到位常有="+PLC.getIntance().A区输送线到位常有);
			}
		});
		chckbxB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PLC.getIntance().B区输送线到位常有=chckbxB.isSelected();
			}
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(1600,780));
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("装配区A_plc", null, panel, null);
		panel.setLayout(null);
		
		STPanel panel_3 = new STPanel(PLC.getIntance().ST0_1,this);
		panel_3.setBounds(0, 0, 255, 210);
		panel.add(panel_3);
		
		STPanel panel_4 = new STPanel(PLC.getIntance().ST1_1,this);
		panel_4.setBounds(265, 0, 255, 210);
		panel.add(panel_4);
		
		STPanel panel_5 = new STPanel(PLC.getIntance().ST2_1,this);
		panel_5.setBounds(530, 0, 255, 210);
		panel.add(panel_5);
		
		STPanel panel_6 = new STPanel(PLC.getIntance().ST3_1,this);
		panel_6.setBounds(795, 0, 255, 210);
		panel.add(panel_6);
		
		STPanel panel_7 = new STPanel(PLC.getIntance().ST4_1,this);
		panel_7.setBounds(1060, 0, 255, 210);
		panel.add(panel_7);
		
		STPanel panel_8 = new STPanel(PLC.getIntance().ST5_1,this);
		panel_8.setBounds(0, 208, 255, 210);
		panel.add(panel_8);
		
		STPanel panel_9 = new STPanel(PLC.getIntance().ST6_1,this);
		panel_9.setBounds(265, 208, 255, 210);
		panel.add(panel_9);
		
		STPanel panel_10 = new STPanel(PLC.getIntance().ST7_1,this);
		panel_10.setBounds(530, 208, 255, 210);
		panel.add(panel_10);
		
		STPanel panel_11 = new STPanel(PLC.getIntance().ST8_1,this);
		panel_11.setBounds(795, 208, 255, 210);
		panel.add(panel_11);
		
		STPanel panel_12 = new STPanel(PLC.getIntance().ST9_1,this);
		panel_12.setBounds(1060, 208, 255, 210);
		panel.add(panel_12);
		
		STPanel panel_13 = new STPanel(PLC.getIntance().ST10_1,this);
		panel_13.setBounds(0, 417, 255, 210);
		panel.add(panel_13);
		
		STPanel panel_14 = new STPanel(PLC.getIntance().ST11_1,this);
		panel_14.setBounds(265, 417, 255, 210);
		panel.add(panel_14);
		
		STPanel panel_15 = new STPanel(PLC.getIntance().ST12_1,this);
		panel_15.setBounds(530, 417, 255, 210);
		panel.add(panel_15);
		
		STPanel panel_16 = new STPanel(PLC.getIntance().ST13_1,this);
		panel_16.setBounds(795, 417, 255, 210);
		panel.add(panel_16);
		
		STPanel panel_17 = new STPanel(PLC.getIntance().ST15_1,this);
		panel_17.setBounds(1060, 417, 255, 210);
		panel.add(panel_17);
		//contentPane.add(tabbedPane, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("测试2", null, panel_2, null);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		CommentPanel pann=new CommentPanel();
		splitPane.setLeftComponent(pann);
		
		textField_1 = new JTextField();
		textField_1.setBounds(729, 448, 66, 21);
		pann.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(STContent.checkNum_预装+"");
		JButton button_2 = new JButton("\u91CD\u7F6E\u9884\u88C5\u9884\u8BFB\u4F4D\u7F6E");
		button_2.setBounds(805, 447, 129, 23);
		pann.add(button_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(729, 479, 66, 21);
		pann.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText(STContent.checkNum_同步+"");
		JButton button_3 = new JButton("\u91CD\u7F6E\u540C\u6B65\u7EBF\u9884\u8BFB\u4F4D\u7F6E");
		button_3.setBounds(805, 480, 141, 23);
		pann.add(button_3);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				STContent.checkNum_同步=Integer.parseInt(textField_2.getText());
			}
		});
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				STContent.checkNum_预装=Integer.parseInt(textField_1.getText());
			}
		});
	
		scrollPane.setViewportView(tabbedPane);
		splitPane.setDividerLocation(1000);
		//splitPane.setDividerLocation(100);
		//splitPane.setDividerLocation(0.0);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_3.initPanel();panel_4.initPanel();
				panel_5.initPanel();panel_6.initPanel();
				panel_7.initPanel();panel_8.initPanel();
				panel_9.initPanel();panel_10.initPanel();
				panel_11.initPanel();panel_12.initPanel();
				panel_13.initPanel();panel_14.initPanel();
				panel_15.initPanel();panel_16.initPanel();
				panel_17.initPanel();
				
				return;
				/*
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
				
				Thread.sleep(100);
				
					}
				}catch(Exception ex){}
				}
			}.start();*/
			}
			});
		CarryFrame cp=new CarryFrame();
		contentPane.add(cp, BorderLayout.NORTH);
	}
	public  int start =0;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
}
