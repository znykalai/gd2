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
		col.addElement("");col.addElement("�������");col.addElement("����ID");
		col.addElement("������");col.addElement("pack����");
		col.addElement("��������");col.addElement("װ����");
		col.addElement("�ֽ�����");col.addElement("���������");
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
		Vector v=SqlTool.findInVector("select �������,ID,������,pack����,��������,װ����,�ֽ����� ,���������.�������/���������.����  from ������ ,��������� where ID=����ID order by �������");
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
				String sql1="delete from �䷽ָ����� where ����ID='"+mode.getValueAt(i, 2)+"'";
				String sql2="delete from ������  where ID='"+mode.getValueAt(i, 2)+"'";
				v.add(sql1);v.add(sql2);
				
			}
			
		   }
		
		if(v.size()>0){
			String[] s=new String[v.size()];
			for(int i=0;i<s.length;i++){
				s[i]=v.get(i);
			}
			 String back=SqlTool.insert(s);
			 if(back.contains("�ɹ�")){
				 getData();
				 
			 }
		}
		
	}

}
