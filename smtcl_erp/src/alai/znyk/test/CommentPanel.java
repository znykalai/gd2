package alai.znyk.test;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import alai.GDT.Resint;
import alai.znyk.common.ClientSer;
import alai.znyk.common.SqlPro;
import alai.znyk.kufang.KuFang;
import alai.znyk.plc.ReST;
import alai.znyk.server.SqlTool;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CommentPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	JComboBox comboBox = new JComboBox();
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	private JTable table_4;
    Vector colum3=new  Vector();
    Vector zl=new  Vector();
    Vector TP=new Vector();
	Vector KF=new Vector();
	MyEditer edit=new MyEditer(new JTextField());
	DefaultTableModel mode=new DefaultTableModel(){
		 public void setValueAt(Object aValue, int row, int column) {
			 super.setValueAt(aValue, row, column);
			 if(column==0)return;
			  
			 if(column==1){
			  String name=mode.getValueAt(row, 0).toString();
			  try{
				  String name2=name.substring(0, 1).toUpperCase()+name.substring(1, name.length()); 
				  if(aValue instanceof Boolean){
			Method m=	first.getClass().getMethod("set"+name2, boolean.class) ;
			m.invoke(first, aValue);}
				  if(aValue instanceof Integer){
					 
					Method m=	first.getClass().getMethod("set"+name2, int.class) ;
					m.invoke(first, aValue);
				  }	
				  if(aValue instanceof String){
						 
						Method m=	first.getClass().getMethod("set"+name2, String.class) ;
						m.invoke(first, aValue);
					  }	
				
			
			
				  
				  }catch(Exception ex){ex.printStackTrace();}
			  }
			
			 
			 if(column==2){
				
				  String name=mode.getValueAt(row, 0).toString();
				  String name2=name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
				  try{
					  if(aValue instanceof Boolean){
				Method m=	second.getClass().getMethod("set"+name2, boolean.class) ;
				m.invoke(second, aValue);}
					  if(aValue instanceof Integer){
						Method m=	second.getClass().getMethod("set"+name2, int.class) ;
						m.invoke(second, aValue);
						
					
				}
			       if(aValue instanceof String){
							Method m=	second.getClass().getMethod("set"+name2, String.class) ;
							m.invoke(second, aValue);
							
						
					}
				
					  }catch(Exception ex){ex.printStackTrace();}
		
				  }
		    }
  };
	
    DefaultTableModel modezl=new DefaultTableModel(){
		 public void setValueAt(Object aValue, int row, int column) {
			 if(column==0)return;
			 super.setValueAt(aValue, row, column);
			 
			  
			 if(column==modezl.getColumnCount()-1){
			  Object name=modezl.getValueAt(row, 0);
			  Object isWan=modezl.getValueAt(row, 5);
			  Object name2=modezl.getValueAt(row,modezl.getColumnCount()-1)==null?"0":modezl.getValueAt(row,modezl.getColumnCount()-1);
			  if(!name2.equals("0"))
			  // SqlTool.insert(new String[]{"update 立库动作指令 set 状态='完成' where idEvent='"+name+"'"});
				  if(!isWan.equals("完成")){
			    SqlTool.setStateForEventID(Integer.parseInt(name.toString()), SqlPro.完成, "");
				Vector v3=SqlTool.findInVector("select idEvent,动作,托盘编号,来源货位号,放回货位号,状态,状态2,是否回大库,空  from 立库动作指令  order by idEvent");
				modezl.setDataVector(v3, zl);
				Vector v4=SqlTool.findInVector("select 托盘编号,物料,数量,货位号,方向  from 库存托盘  order by 托盘编号");
				modeTP.setDataVector(v4, TP);
				
				
				Vector v5=SqlTool.findInVector("select 货位序号,托盘编号   from 货位表  order by 距离");
				modeKF.setDataVector(v5, KF);
				  }
			 }
			}
				  };
	DefaultTableModel mode3=new DefaultTableModel(){
		 public void setValueAt(Object aValue, int row, int column) {
			 if(column==0)return;
			 super.setValueAt(aValue, row, column);
			 
			  
			 if(column==1){
			  Object name=mode3.getValueAt(row, 0);
			  SqlTool.insert(new String[]{"update 有货信号 set 信号='"+aValue+"' where 工位='"+name+"'"});
			 }
			}
				  };
	DefaultTableModel mode4=new DefaultTableModel(){
						 public void setValueAt(Object aValue, int row, int column) {
							 if(column==0)return;
							 super.setValueAt(aValue, row, column);
							 
							  
							 if(column==1){
							  Object name=mode4.getValueAt(row, 0);
							  SqlTool.insert(new String[]{"update 到位信号 set 信号='"+aValue+"' where 工位='"+name+"'"});
							 }
							}
								  };
		   
	DefaultTableModel modeTP=new DefaultTableModel(){
				 public void setValueAt(Object aValue, int row, int column) {
										}
		 };
    DefaultTableModel modeKF=new DefaultTableModel(){
			 public void setValueAt(Object aValue, int row, int column) {
									}
	 };
	 
	 
	 
	  DefaultTableModel mode6=new DefaultTableModel(){
			 public void setValueAt(Object aValue, int row, int column) {
				 super.setValueAt(aValue, row, column);
									}
			 public boolean isCellEditable(int row, int column) {
				    if(column==0||column==2||column==4||column==6) return false;
			        return true;
			    }
	 };
	 
    private JTextField textField_12;
    private JTable table_5;
	 Vector colum6=new Vector();
	 Hashtable<String,String> aH=new Hashtable<String,String>();
	
	 
	 
	/**
	 * Create the panel.
	 */
	public CommentPanel() {
		colum6.addElement("序号");colum6.addElement("1层");
		colum6.addElement("序号");colum6.addElement("1层");
		colum6.addElement("序号");colum6.addElement("2层");
		colum6.addElement("序号");colum6.addElement("2层");
		aH.put("1ST", "D11299");aH.put("2ST", "D11349");
		aH.put("3ST", "D11399");aH.put("4ST", "D11449");
		aH.put("5ST", "D11499");aH.put("6ST", "D11549");
		aH.put("7ST", "D11599");
		KuFang f=KuFang.getIntance();
		colum3.addElement("工位");colum3.addElement("信号");
		Vector v=SqlTool.findInVector("select 工位,信号 from 有货信号");
		mode3.setDataVector(v, colum3);
		
		Vector v2=SqlTool.findInVector("select 工位,信号 from 到位信号");
		mode4.setDataVector(v2, colum3);
		
		zl.addElement("idEvent");zl.addElement("动作");
		zl.addElement("托盘");zl.addElement("从");zl.addElement("到");zl.addElement("状态");
		zl.addElement("已回大库");zl.addElement("大库");zl.addElement("完成");
		Vector v3=SqlTool.findInVector("select idEvent,动作,托盘编号,来源货位号,放回货位号,状态,状态2,是否回大库,空  from 立库动作指令  order by idEvent");
		modezl.setDataVector(v3, zl);
		
		TP.addElement("托盘编号");TP.addElement("物料");
		TP.addElement("数量");TP.addElement("货位号");TP.addElement("方向");
		Vector v4=SqlTool.findInVector("select 托盘编号,物料,数量,货位号,方向  from 库存托盘  order by 托盘编号");
		modeTP.setDataVector(v4, TP);
		
		KF.addElement("货位");KF.addElement("托盘");
		Vector v5=SqlTool.findInVector("select 货位序号,托盘编号   from 货位表  order by 距离");
		modeKF.setDataVector(v5, KF);
		
		setLayout(null);
		
		JLabel label = new JLabel("\u4E0A\u6599\u5347\u964D\u53F0");
		label.setBounds(0, 10, 75, 15);
		add(label);
		
		textField = new JTextField();
		textField.setBounds(96, 25, 75, 21);
		add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("\u6258\u76D8\u53F7");
		label_1.setBounds(96, 10, 54, 15);
		add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(177, 25, 75, 21);
		add(textField_1);
		
		JLabel label_2 = new JLabel("\u7269\u6599");
		label_2.setBounds(177, 10, 54, 15);
		add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(262, 25, 75, 21);
		add(textField_2);
		
		JLabel label_3 = new JLabel("\u4FE1\u53F7");
		label_3.setBounds(262, 10, 54, 15);
		add(label_3);
		
		JButton btnNewButton = new JButton("\u53D1\u9001");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().equals("")&&!textField_1.getText().equals("")){
				ClientSer.TP=textField.getText();	
				SqlTool.fromDKisTP=textField_1.getText()+"!_!"+10;
				  }
				ClientSer.rffid2=textField_2.getText().equals("")?"0":"1";
			}
		});
		btnNewButton.setBounds(347, 24, 93, 23);
		add(btnNewButton);
		
		JLabel label_4 = new JLabel("\u4E0B\u6599\u5347\u964D\u53F0");
		label_4.setBounds(0, 53, 75, 15);
		add(label_4);
		
		JLabel label_5 = new JLabel("\u6258\u76D8\u53F7");
		label_5.setBounds(96, 53, 54, 15);
		add(label_5);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(96, 68, 75, 21);
		add(textField_3);
		
		JLabel label_6 = new JLabel("\u4FE1\u53F7");
		label_6.setBounds(262, 52, 54, 15);
		add(label_6);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(262, 67, 75, 21);
		add(textField_4);
		
		JButton button = new JButton("\u53D1\u9001");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
					 ClientSer.TP=textField_3.getText();
					 ClientSer.rffid1=textField_4.getText().equals("")?"0":"1";
					
				
			}
		});
		button.setBounds(347, 66, 93, 23);
		add(button);
		
		JLabel label_7 = new JLabel("\u4E0A\u8D27");
		label_7.setBounds(0, 98, 75, 15);
		add(label_7);
		
		JLabel label_8 = new JLabel("\u4ECE");
		label_8.setBounds(96, 98, 54, 15);
		add(label_8);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(96, 113, 75, 21);
		add(textField_5);
		
		JLabel label_9 = new JLabel("\u5230");
		label_9.setBounds(262, 97, 54, 15);
		add(label_9);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(262, 112, 75, 21);
		add(textField_6);
		
		JButton button_1 = new JButton("\u53D1\u9001");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fID=textField_5.getText();
				String tID=textField_6.getText();
				if(!fID.equals("")&&!tID.equals("")){
					String tp=SqlTool.findOneRecord("select 托盘编号 from 货位表  where 货位序号='"+fID+"'");
					if(!tp.equals("0")){
						String back=SqlTool.add动作指令(tp,fID, tID, "上货", 0, "1")	;
						System.out.println(back);
						JOptionPane.showConfirmDialog(null, back);
					}
					
				}
				
				
			}
		});
		button_1.setBounds(347, 111, 93, 23);
		add(button_1);
		
		JLabel label_10 = new JLabel("\u4E0B\u8D27");
		label_10.setBounds(0, 153, 75, 15);
		add(label_10);
		
		JLabel label_11 = new JLabel("\u4ECE");
		label_11.setBounds(96, 135, 54, 15);
		add(label_11);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(96, 150, 75, 21);
		add(textField_7);
		
		JLabel label_12 = new JLabel("\u5230");
		label_12.setBounds(262, 134, 54, 15);
		add(label_12);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(262, 149, 75, 21);
		add(textField_8);
		
		JButton button_2 = new JButton("\u53D1\u9001");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fID=textField_7.getText();
				String tID=textField_8.getText();
				if(!fID.equals("")&&!tID.equals("")){
					String tp=SqlTool.findOneRecord("select 托盘编号 from 货位表  where 货位序号='"+fID+"'");
					if(!tp.equals("0")){
						String back=SqlTool.add动作指令(tp,fID, tID, "下货", 0, "1")	;
						System.out.println(back);
						JOptionPane.showConfirmDialog(null, back);
					}
					
				}
			}
		});
		button_2.setBounds(347, 148, 93, 23);
		add(button_2);
		
		JLabel label_13 = new JLabel("\u56DE\u6D41");
		label_13.setBounds(0, 183, 75, 15);
		add(label_13);
		
		JLabel label_14 = new JLabel("\u4ECE");
		label_14.setBounds(96, 172, 54, 15);
		add(label_14);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(96, 187, 75, 21);
		add(textField_9);
		
		JLabel label_15 = new JLabel("\u5230");
		label_15.setBounds(181, 172, 54, 15);
		add(label_15);
		
		textField_10 = new JTextField();
		textField_10.setEnabled(false);
		textField_10.setColumns(10);
		textField_10.setBounds(181, 187, 75, 21);
		add(textField_10);
		
		JButton button_3 = new JButton("\u53D1\u9001");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fID=textField_9.getText();
				String ishui=textField_11.getText().equals("")?"0":"1";
				if(!fID.equals("")){
					String tp=SqlTool.findOneRecord("select 托盘编号 from 货位表  where 货位序号='"+fID+"'");
					if(!tp.equals("0")){
						String back=SqlTool.add动作指令(tp,fID, "60002", "输送线回流", Integer.parseInt(ishui), "1")	;
						System.out.println(back);
						JOptionPane.showConfirmDialog(null, back);
					}
					
				}
			}
		});
		button_3.setBounds(347, 185, 93, 23);
		add(button_3);
		
		JLabel label_16 = new JLabel("\u662F\u5426\u56DE\u5927\u5E93");
		label_16.setBounds(262, 172, 75, 15);
		add(label_16);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(262, 187, 75, 21);
		add(textField_11);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null, "\u6307\u4EE4\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(0, 219, 440, 155);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6258\u76D8\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		scrollPane_1.setBounds(0, 415, 440, 124);
		add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					int row=table_1.getSelectedRow();
					Object tp=table_1.getValueAt(row, 0);
					textField_12.setText(tp.toString());
					
				}catch(Exception ex){}
			}
		});
		scrollPane_1.setViewportView(table_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(new TitledBorder(null, "\u8D27\u4F4D", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_2.setBounds(450, 10, 113, 362);
		add(scrollPane_2);
		
		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBorder(new TitledBorder(null, "\u6709\u8D27\u4FE1\u53F7", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_3.setBounds(573, 10, 93, 233);
		add(scrollPane_3);
		
		table_3 = new JTable();
		scrollPane_3.setViewportView(table_3);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u5230\u4F4D\u4FE1\u53F7", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		scrollPane_4.setBounds(573, 253, 93, 118);
		add(scrollPane_4);
		
		table_4 = new JTable();
		scrollPane_4.setViewportView(table_4);
		table.setModel(modezl);
		table_1.setModel(modeTP);
		table_2.setModel(modeKF);
		table_3.setModel(mode3);
		table_4.setModel(mode4);
		
		this.setPreferredSize(new Dimension(1012, 739));
		
		textField_12 = new JTextField();
		textField_12.setBounds(0, 549, 64, 21);
		add(textField_12);
		textField_12.setColumns(10);
		
		JButton button_4 = new JButton("\u5220\u9664");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tp=textField_12.getText();
				String huowei=SqlTool.findOneRecord("select 托盘编号 from 库存托盘  where 托盘编号='"+tp+"'");
				if(!huowei.equals("0")){
					String sql1="DELETE FROM 库存托盘  where 托盘编号='"+tp+"'";
					String sql2="UPDATE 货位表  SET 托盘编号=NULL   where 托盘编号='"+tp+"'";
					SqlTool.insert(new String[]{sql1,sql2});
					refsh();
					textField_12.setText("");
					
				}
			}
		});
		button_4.setBounds(78, 548, 93, 23);
		add(button_4);
		
		JButton button_5 = new JButton("\u5237\u65B0");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refsh();
			}
		});
		button_5.setBounds(347, 548, 93, 23);
		add(button_5);
		
		JLabel lblagvb = new JLabel("\u4E0A\u6599agvB");
		lblagvb.setBounds(0, 28, 75, 15);
		add(lblagvb);
		
		JLabel lblagva = new JLabel("\u56DE\u5E93/\u5927\u5E93agvA");
		lblagva.setBounds(0, 71, 93, 15);
		add(lblagva);
		
		JLabel label_19 = new JLabel("\u5DE6/\u53F3\u5347\u964D\u53F0");
		label_19.setBounds(0, 116, 93, 15);
		add(label_19);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//System.out.println(comboBox.getSelectedItem());
				setData();
			}
		});
		
		
		comboBox.addItem("0ST");comboBox.addItem("1ST");
		comboBox.addItem("2ST");comboBox.addItem("3ST");
		comboBox.addItem("4ST");comboBox.addItem("5ST");
		comboBox.addItem("6ST");comboBox.addItem("7ST");
		comboBox.addItem("8ST");comboBox.addItem("9ST");
		comboBox.addItem("10ST");comboBox.addItem("11ST");
		comboBox.addItem("12ST");comboBox.addItem("13ST");
		comboBox.addItem("14ST");comboBox.addItem("15ST");
		comboBox.setBounds(558, 384, 113, 21);
		add(comboBox);
		
		JLabel lblplc = new JLabel("\u9009\u62E9PLC\u8FD4\u56DE\u5DE5\u4F4D");
		lblplc.setBounds(440, 387, 108, 18);
		add(lblplc);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(440, 415, 231, 117);
		add(scrollPane_5);
		
		table_5 = new JTable();
		scrollPane_5.setViewportView(table_5);
		
		Vector col=new Vector();
	    col.addElement("光大返回"); col.addElement("队列1值");col.addElement("队列2值");
	    mode.setDataVector(new Vector(), col);
	    
	    table_5.setModel(mode);
	    
	    JButton button_6 = new JButton("\u5199\u5165");
	    button_6.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		first.writeToPLC();
	    		second.writeToPLC();
	    		try{
	    			Thread.sleep(200);
	    			
	    		}catch(Exception ex){}
	    		setData();
	    		
	    	}
	    });
	    button_6.setBounds(578, 548, 93, 23);
	    add(button_6);
	    
	    JLabel label_17 = new JLabel("\u9996\u5730\u5740\uFF1A");
	    label_17.setBounds(10, 602, 54, 15);
	    add(label_17);
	    
	    textField_13 = new JTextField();
	    textField_13.setBounds(59, 599, 91, 21);
	    add(textField_13);
	    textField_13.setColumns(10);
	    
	    JLabel label_18 = new JLabel("\u503C");
	    label_18.setBounds(159, 602, 54, 15);
	    add(label_18);
	    
	    textField_14 = new JTextField();
	    textField_14.setBounds(177, 599, 489, 21);
	    add(textField_14);
	    textField_14.setColumns(10);
	    
	    JButton button_7 = new JButton("\u53D1\u9001");
	    button_7.addActionListener(new ActionListener() { 
	    	public void actionPerformed(ActionEvent e) {
	    		String startA=textField_13.getText();
	    		String val=textField_14.getText();
	    		 if(val!=null&&!val.equals("")&&!startA.equals("")){
	    			   String sm[]=val.split(",");
	    			   int length=sm.length+1;
	    			   int to[]=new int[length];
	    			   to[0]=1;
	    			  for(int i=1;i<to.length;i++){
	    				     to[i]=Integer.parseInt(sm[i-1].split("=")[1]);
	    				}
	    		ClientSer.getIntance().writeSirIntToCTR(startA, length, to,  1)  ;
	    			   
	    		   }
	    				
	    	}
	    });
	    button_7.setBounds(676, 598, 93, 23);
	    add(button_7);
	    
	    JLabel label_20 = new JLabel("\u88C5\u914D\u533A");
	    label_20.setBounds(669, 10, 54, 15);
	    add(label_20);
	    
	    JComboBox comboBox_1 = new JComboBox();
	    comboBox_1.setBounds(714, 7, 93, 21);
	    add(comboBox_1);
	    comboBox_1.addItem(1); comboBox_1.addItem(2);
	    
	    JLabel label_21 = new JLabel("\u5DE5\u4F4D");
	    label_21.setBounds(817, 10, 54, 15);
	    add(label_21);
	    
	    JComboBox comboBox_2 = new JComboBox();
	    comboBox_2.addItem("1ST"); comboBox_2.addItem("2ST");
	    comboBox_2.addItem("3ST"); comboBox_2.addItem("4ST");
	    comboBox_2.addItem("5ST"); comboBox_2.addItem("6ST");
	    comboBox_2.addItem("7ST");
	    comboBox_2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		System.out.println(comboBox_2.getSelectedItem());
	    		String st=comboBox_2.getSelectedItem().toString();
	    		String machID=comboBox_1.getSelectedItem().toString();
	    		initTable6(aH.get(st),Integer.parseInt(machID));
	    	}
	    });
	    comboBox_2.setBounds(881, 7, 121, 21);
	    add(comboBox_2);
	    
	    JScrollPane scrollPane_6 = new JScrollPane();
	    scrollPane_6.setBounds(676, 47, 326,324);
	    add(scrollPane_6);
	    
	    table_6 = new JTable();
	    table_6.setRowHeight(25);
	    scrollPane_6.setViewportView(table_6);
	    mode6.setDataVector(new Vector(), colum6);
	    table_6.setModel(mode6);
	    
	    JButton button_8 = new JButton("\u5199\u5165\u6599\u7BB1\u4F4D\u7F6E");
	    button_8.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String st=comboBox_2.getSelectedItem().toString();
	    		String machID=comboBox_1.getSelectedItem().toString();
	    		writeTable6ToPLC(Integer.parseInt(machID),aH.get(st));
	    	}
	    });
	    button_8.setBounds(881, 384, 121, 23);
	    add(button_8);
	    
	    JButton button_9 = new JButton("\u5220\u9664\u6307\u4EE4");
	    button_9.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		int r=table.getSelectedRow();
	    		if(r!=-1){
	    			Object ID=table.getValueAt(r, 0);
	    			int i=JOptionPane.showConfirmDialog(null, "执行中的指令删除时要注意，排队中的可以安全删除", "提示", JOptionPane.YES_NO_OPTION);
	               
	                 if(i==JOptionPane.YES_OPTION){
	                	 String back=SqlTool.insert(new String[]{"delete from 立库动作指令  where idEvent='"+ID+"'"});
	                	 refsh();
	                 }
	    			
	    			
	    			
	    		   }
	    		
	    	}
	    });
	    button_9.setBounds(335, 384, 93, 23);
	    add(button_9);
	   // initTable6();
	    
	   
	    table_5.getColumnModel().getColumn(1).setCellEditor(edit);
	    table_5.getColumnModel().getColumn(2).setCellEditor(edit);
	    
		new Thread(){
			public void run(){
				while(true){
					try{
					//	refsh();
						Thread.sleep(5000);
						
					}catch(Exception ex){}
					
				}
				
			}
			
		}.start();

	}
	
	public void initTable6(String addr,int machineID){
		
		alai.GDT.Resint[]back=ClientSer.getIntance().getSirIntValuesFromCTR(addr, 29, 16, machineID);
		if(back==null)return;
		Vector data=new Vector();
		for(int r=0;r<7;r++){
			Vector row=new Vector();
			row.addElement(r*2+1);row.addElement(back[r*2+1].getResInt());
			row.addElement(r*2+2);row.addElement(back[r*2+2].getResInt());
			//第2成
			row.addElement(14+r*2+1);row.addElement(back[14+r*2+1].getResInt());
			row.addElement(14+r*2+2);row.addElement(back[14+r*2+2].getResInt());
			
			data.addElement(row);
			
		}
		 mode6.setDataVector(data, colum6);
		
		
	}
	
	public void writeTable6ToPLC(int machineID,String addr){
		int to[]=new int[29]; to[0]=1;
		for(int r=0;r<mode6.getRowCount();r++){
			to[r*2+1]=(int)mode6.getValueAt(r, 1);
			to[r*2+2]=(int)mode6.getValueAt(r, 3);
			to[14+r*2+1]=(int)mode6.getValueAt(r, 5);
			to[14+r*2+2]=(int)mode6.getValueAt(r, 7);
			
		   }
	int b= ClientSer.getIntance().writeSirIntToCTR(addr, to.length, to,  machineID)  ;
		if(b==1){
			JOptionPane.showMessageDialog(null,"写入成功","提示",JOptionPane.WARNING_MESSAGE,null);
		}
	}
	
	public void refsh(){
		Vector v3=SqlTool.findInVector("select idEvent,动作,托盘编号,来源货位号,放回货位号,状态,状态2,是否回大库,空  from 立库动作指令  order by idEvent");
		modezl.setDataVector(v3, zl);
		Vector v4=SqlTool.findInVector("select 托盘编号,物料,数量,货位号,方向  from 库存托盘  order by 托盘编号");
		modeTP.setDataVector(v4, TP);
		Vector v5=SqlTool.findInVector("select 货位序号,托盘编号   from 货位表  order by 距离");
		modeKF.setDataVector(v5, KF);
		
	}
	
	ReST first=new ReST(new Resint());
	ReST second=new ReST(new Resint());
	private JTextField textField_13;
	private JTextField textField_14;
	private JTable table_6;
	public void setData(){
		Resint[] rs=ClientSer.getIntance().getReturnPlc("D11001",63,16,1);
		String st=comboBox.getSelectedItem().toString();
		int s=Integer.parseInt(st.replace("ST", ""));
		Resint r1=rs[s*2];
		Resint r2=rs[(s+16)*2];
		first=new ReST(r1);
		first.startAddres="D"+(11001+s*2);
	    second=new ReST(r2);
	    second.startAddres="D"+(11033+s*2);
		try{
		Field f[]=first.getClass().getDeclaredFields();
		mode.getDataVector().removeAllElements();
		for(int i=0;i<f.length;i++){
			Vector row=new Vector();
			String name=f[i].getName();
			
		    Object ty=f[i].getType();
			row.addElement(name);
			 String name2=name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
		    if(ty.toString().equals("boolean")){
			
			Method m2=	first.getClass().getMethod("is"+name2, null) ;
			row.addElement(m2.invoke(first, null));
			Method m3=	second.getClass().getMethod("is"+name2, null) ;
			row.addElement(m3.invoke(second, null));
		    //b.println();
			mode.addRow(row);
			 }
		  
		    if(ty.toString().equals("class java.lang.String")){
		    	 // System.out.println(ty);
				Method m2=	first.getClass().getMethod("get"+name2, null) ;
				row.addElement(m2.invoke(first, null));
				Method m3=	second.getClass().getMethod("get"+name2, null) ;
				row.addElement(m3.invoke(second, null));
			    //b.println();
				mode.addRow(row);
				 }
        	  
			
		}
		
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
      }
}
