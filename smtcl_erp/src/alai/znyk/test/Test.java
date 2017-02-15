package alai.znyk.test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;

import alai.znyk.plc.PLC;
import alai.znyk.plc._FST;
import alai.znyk.server.Conn;
import alai.znyk.server.ConnactionPool;

import javax.swing.JCheckBox;

public class Test {
   static{
	   try{
	   UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());   
	   }catch(Exception ex){}
   }
	private JFrame frame;
	private JTextField textField;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1008, 428);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnGet = new JButton("get");
		
		btnGet.setBounds(330, 358, 93, 23);
		frame.getContentPane().add(btnGet);
		
		textField = new JTextField();
		textField.setText("866");
		textField.setBounds(433, 359, 100, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		STPanel panel = new STPanel();
		panel.setBounds(10, 10, 240, 279);
		frame.getContentPane().add(panel);
		
		STPanel panel_1 = new STPanel();
		panel_1.setBounds(251, 10, 240, 279);
		frame.getContentPane().add(panel_1);
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.initPanel();
			  }
		});
	}
	
	public void getSql(){
		try{
		ConnactionPool p=ConnactionPool.getPool();
		Conn conn= p.getCon2("") ;
		Connection con=conn.getCon(); 
		Statement st=con.createStatement();
		
		long time1=System.currentTimeMillis();
       ResultSet set=st.executeQuery("select * from t_userequworkevents where id='"+textField.getText()+"'");
		// ResultSet set=st.executeQuery("select * from hr.通用物料  where 物料编码='"+textField.getText()+"'");
        int num= set.getMetaData().getColumnCount();
        int r=1;
        while(set.next()){
        	System.out.print((r++)+":");
        	 for(int i=1;i<num;i++){
        	
        	 System.out.print(set.getObject(i)+" ");
        	 }
        	 System.out.println();
        	
        }
       
		  System.out.println(System.currentTimeMillis()-time1);
		  set.close();
		  st.close();
		  conn.realseCon();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	 }
}
