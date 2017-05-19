package alai.znyk.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import alai.znyk.server.SqlTool;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class DeletOrderFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Vector col=new Vector();
	
	  DefaultTableModel mode=new DefaultTableModel(){
			 public void setValueAt(Object aValue, int row, int column) {
				 super.setValueAt(aValue, row, column);
									}
			 
			 public Class<?> getColumnClass(int columnIndex) {
				    if(columnIndex==0){return Boolean.class;}
			        return Object.class;
			    }
			 
			
	 };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeletOrderFrame frame = new DeletOrderFrame();
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
	public DeletOrderFrame() {
		col.addElement("");col.addElement("工单序号");col.addElement("工单ID");
		col.addElement("工单号");col.addElement("pack编码");
		col.addElement("工单数量");col.addElement("装配区");
		col.addElement("分解日期");col.addElement("工单完成率");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 813, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setRowHeight(20);
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton button = new JButton("\u5220\u9664");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		panel.add(button);
		table.setModel(mode);
		getData();
	}
	
	public void setWidth2(){
		
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(4).setPreferredWidth(300);
	}
	public void getData(){
		Vector v=SqlTool.findInVector("select 工单序号,ID,工单号,pack编码,工单数量,装配区,分解日期 ,工单完成率.完成数量/工单完成率.数量  from 工单表 ,工单完成率 where ID=工单ID order by 工单序号");
		for(int r=0;r<v.size();r++){
			Vector row=(Vector)v.get(r);
			row.insertElementAt(false, 0);
		}
		mode.setDataVector(v, col);
		setWidth2();
	}
	
	public void delete(){
		Vector<String> v=new Vector<String>();
		for(int i=0;i<mode.getRowCount();i++){
			if((Boolean)mode.getValueAt(i, 0)){
			   //	SqlTool.insert(sql)	
				String sql1="delete from 配方指令队列 where 工单ID='"+mode.getValueAt(i, 2)+"'";
				String sql2="delete from 工单表  where ID='"+mode.getValueAt(i, 2)+"'";
				v.add(sql1);v.add(sql2);
				
			}
			
		   }
		
		if(v.size()>0){
			String[] s=new String[v.size()];
			for(int i=0;i<s.length;i++){
				s[i]=v.get(i);
			}
			 String back=SqlTool.insert(s);
			 if(back.contains("成功")){
				 getData();
				 
			 }
		}
		
	}

}
