package alai.znyk.test;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import alai.znyk.plc.PLC;
import alai.znyk.plc.ReST;
import alai.znyk.plc.STContent;
import alai.znyk.plc.STInterface;
import alai.znyk.plc._1_6ST;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.EventObject;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;

public class STPanel extends JPanel {
	private JTable table;
	DefaultTableModel mode=new DefaultTableModel(){
		 public void setValueAt(Object aValue, int row, int column) {
			 super.setValueAt(aValue, row, column);
			 if(column==0)return;
			  
			 if(column==1){
			  Object name=mode.getValueAt(row, 0);
			  try{
				  if(row<mode.getRowCount()-4){
				  if(aValue instanceof Boolean){
			Method m=	ST.firstST.getClass().getMethod("set"+name, boolean.class) ;
			m.invoke(ST.firstST, aValue);}
				  if(aValue instanceof Integer){
					 
					Method m=	ST.firstST.getClass().getMethod("set"+name, int.class) ;
					m.invoke(ST.firstST, aValue);
				  }	
				  if(aValue instanceof String){
						 
						Method m=	ST.firstST.getClass().getMethod("set"+name, String.class) ;
						m.invoke(ST.firstST, aValue);
					  }	
				
			}else{
				
				 if(aValue instanceof Boolean){
						Method m=	RS.getClass().getMethod("set"+name, boolean.class) ;
						m.invoke(RS, aValue);}
							  else{
								Method m=	RS.getClass().getMethod("set"+name, int.class) ;
								m.invoke(RS, aValue);
							  }	
				 System.out.println(RS.getBoolCont().getResInt());
			}
			
				  textField.setText(ST.firstST.getBoolContent()+"");	  
				  }catch(Exception ex){ex.printStackTrace();}
			  }
			
			 
			 if(column==2){
				 if(row<mode.getRowCount()-4){
				  Object name=mode.getValueAt(row, 0);
				  try{
					  if(aValue instanceof Boolean){
				Method m=	ST.secondST.getClass().getMethod("set"+name, boolean.class) ;
				m.invoke(ST.secondST, aValue);}
					  if(aValue instanceof Integer){
						Method m=	ST.secondST.getClass().getMethod("set"+name, int.class) ;
						m.invoke(ST.secondST, aValue);
						
					
				}
			       if(aValue instanceof String){
							Method m=	ST.secondST.getClass().getMethod("set"+name, String.class) ;
							m.invoke(ST.secondST, aValue);
							
						
					}
				
					  }catch(Exception ex){ex.printStackTrace();}
			}
				  }
		    }
   };
	MyEditer edit=new MyEditer(new JTextField());
	/**
	 * Create the panel.
	 */
	//STInterface ST=PLC.getIntance()._6ST_1_1;
	STContent ST=PLC.getIntance().ST0_1;
	private JTextField textField;
	private JTextField textField_1;
    ReST RS=PLC.getIntance().RST[0];
	public STPanel() {
		System.out.println("---------------------");
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setRowHeight(20);
		scrollPane.setViewportView(table);
        Vector col=new Vector();
        col.addElement("项目"); col.addElement("队列1值");col.addElement("队列2值");
        mode.setDataVector(new Vector(), col);
        table.setModel(mode);
        
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        
        JLabel lblBool = new JLabel("BOOL\u5408\u5E76");
        panel.add(lblBool);
        
        textField = new JTextField();
        panel.add(textField);
        textField.setColumns(10);
        textField.setText(ST.firstST.getBoolContent()+"");
        
        JLabel label = new JLabel("\u9996\u5730\u5740");
        panel.add(label);
        
        textField_1 = new JTextField();
        panel.add(textField_1);
        textField_1.setColumns(10);
        textField_1.setText(ST.firstST.getStartAddress());
        
        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);
        
        JLabel label_1 = new JLabel(":");
        panel_1.add(label_1);
        
        JButton btnNewButton = new JButton("\u66F4\u65B0\u5230PLC");
        panel_1.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ST.firstST.writeToPLC();
        		ST.secondST.writeToPLC();
        	}
        });
        table.getColumnModel().getColumn(1).setCellEditor(edit);
        initPanel();
       
	}
	public STPanel(STContent ST){
	
		this.ST=ST;
		RS=PLC.getIntance().RST[ST.stNum-1];
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setRowHeight(16);
		scrollPane.setViewportView(table);
        Vector col=new Vector();
        col.addElement("项目"); col.addElement("队列1值");col.addElement("队列2值");
        mode.setDataVector(new Vector(), col);
        table.setModel(mode);
        
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        
        JLabel lblBool = new JLabel("BOOL\u5408\u5E76");
        panel.add(lblBool);
        
        textField = new JTextField();
        panel.add(textField);
        textField.setColumns(10);
        textField.setText(ST.firstST.getBoolContent()+"");
        
        JLabel label = new JLabel("\u9996\u5730\u5740");
        panel.add(label);
        
        textField_1 = new JTextField();
        panel.add(textField_1);
        textField_1.setColumns(10);
        textField_1.setText(ST.firstST.getStartAddress());
        
        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);
        
        JLabel label_1 = new JLabel((ST.stNum-1)+"工位:");
        panel_1.add(label_1);
        
        JButton btnNewButton = new JButton("\u66F4\u65B0\u5230PLC");
        panel_1.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ST.firstST.writeToPLC();
        		ST.secondST.writeToPLC();
        	}
        });
        table.getColumnModel().getColumn(1).setCellEditor(edit);
        table.getColumnModel().getColumn(2).setCellEditor(edit);
        initPanel();
        new Thread(){
        	public void run(){
        		while(true){
        		//initPanel();
        		try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		}
        		
        	}
        	
        }.start();
	}
	public void initPanel(){
		
		if(ST==null) return;
		mode.getDataVector().removeAllElements();
		Field f[]=ST.firstST.getClass().getDeclaredFields();
		
		if(0==0){
		for(int i=0;i<f.length;i++){
			try{  
				Vector row=new Vector();
				String name=f[i].getName();
				
				if(name.contains("bool")){
					String boolcont=ST.firstST.getBoolContent()+"";
					textField.setText(boolcont);
					continue;}
				Object ty=f[i].getType();
				row.addElement(name);
			    if(ty.toString().equals("boolean")){
				
				Method m2=	ST.firstST.getClass().getMethod("is"+name, null) ;
				row.addElement(m2.invoke(ST.firstST, null));
				Method m3=	ST.secondST.getClass().getMethod("is"+name, null) ;
				row.addElement(m3.invoke(ST.secondST, null));
			    //b.println();
				 }
				 else{
				Method m2=	ST.firstST.getClass().getMethod("get"+name, null) ;
				row.addElement(m2.invoke(ST.firstST, null));
				Method m3=	ST.secondST.getClass().getMethod("get"+name, null) ;
				row.addElement(m3.invoke(ST.secondST, null));
					 
				 }
              mode.addRow(row);
            	  
            	  
              
             }
			
			catch(Exception e){e.printStackTrace();}
		      }
		}else{
//			try{
//			for(int i=0;i<f.length;i++){
//				String name=f[i].getName();
//				if(name.contains("bool"))continue;
//				 Object ty=f[i].getType();
//				 if(ty.toString().equals("boolean")){
//					    Method m2=	ST.firstST.getClass().getMethod("is"+name, null) ;
//					   Method m3=	ST.secondST.getClass().getMethod("is"+name, null) ;
//						
//						for(int r=0;r<mode.getRowCount();r++){
//						 if(mode.getValueAt(r, 0).equals(name)){
//							mode.setValueAt(m2.invoke(ST.firstST, null), r, 1);
//							mode.setValueAt(m3.invoke(ST.secondST, null), r, 2);
//						 }
//						}
//						 }
//						 else{
//						Method m2=	ST.firstST.getClass().getMethod("get"+name, null) ;
//						//m2.invoke(ST.firstST, null);
//						Method m3=	ST.secondST.getClass().getMethod("get"+name, null) ;
//						//m3.invoke(ST.secondST, null);
//						for(int r=0;r<mode.getRowCount();r++){
//							 if(mode.getValueAt(r, 0).equals(name)){
//								 mode.setValueAt(m2.invoke(ST.firstST, null), r, 1);
//								 mode.setValueAt(m3.invoke(ST.secondST, null), r, 2);
//							 }
//							}	 
//						 }
//				 
//			}
//			
//			}catch(Exception ex){}
//			 
		 }
		try{
		 Field f2[]=RS.getClass().getDeclaredFields();
		 for(int i=0;i<f2.length;i++){
			
			   
				String name=f2[i].getName();
				
				if(name.equals("serialVersionUID"))continue;
				 Vector row=new Vector();
				if(name.contains("bool"))continue;
				Object ty=f2[i].getType();
				row.addElement(name);
			    if(ty.toString().equals("boolean")){
				
				Method m2=	RS.getClass().getMethod("is"+name, null) ;
				row.addElement(m2.invoke(RS, null));
				row.addElement(null);
			    //b.println();
				 }
				 else{
				Method m2=	RS.getClass().getMethod("get"+name, null) ;
				row.addElement(m2.invoke(RS, null));
				row.addElement(null);
					 
				 }
           mode.addRow(row);
         	  
		 }
			 }catch(Exception ee){ee.printStackTrace();}
	}

}
