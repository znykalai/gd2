package alai.znyk.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import alai.znyk.common.ClientSer;
import alai.znyk.plc.PLC;
import alai.znyk.server.SqlTool;

public class Test2 {
	String lock1="";
	public void print1(int i){
	                 
					int s=0;
					synchronized(lock1){
						System.out.println("����"+i+"_"+Thread.currentThread());
					 while(s<100){
					//System.out.println("1");
				      	s++;
				      	try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }
					 System.out.println("end"+i+"_"+Thread.currentThread());
					}
					
					
	
		
	}
		
	public void print2(){
		
		synchronized(lock1){
		     print1(2);
		     System.out.println("end------------");
		  }
	
}
	
	public byte[] write(Object ob){
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
	    ObjectOutputStream out = null; 
	    try { 
	      out = new ObjectOutputStream(baos); 
	      out.writeObject(ob);    
	    } catch (IOException e) { 
	    
	    }finally{ 
	      try { 
	          out.close(); 
	      } catch (IOException e) { 
	    	  e.printStackTrace();
	      } 
	    } 
	      
	    return baos.toByteArray(); 

	}
	
	public Object readFromByte(byte[] b) throws IOException, ClassNotFoundException{
		
		ByteArrayInputStream bais=null; 
	    ObjectInputStream in = null; 
	    try{ 
	      bais = new ByteArrayInputStream(b); 
	      in = new ObjectInputStream(bais); 
	      return in.readObject(); 
	    }finally{ 
	      if(in != null){ 
	        try { 
	          in.close(); 
	        } catch (IOException e) { 
	           e.printStackTrace();
	        } 
	      } 
	    } 

	}
	
	 private static void removeNull( Vector<Hashtable<String,Integer>> zl){
  	   if(zl==null)return;
  	   for(int i=0;i<zl.size();i++){
  		   Hashtable<String,Integer> h=zl.get(i);
  		   String key=h.keys().nextElement();
  		   if(h.get(key)==0){
  			   zl.remove(i);
  			   removeNull(zl);
  		   }
  		   
  		   
  	   }
  	
  }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String back=SqlTool.add����ָ��("1", "60001", "501", "�ϻ�", 0, "1");
		 // String back=SqlTool.add����ָ��("100", "25", "60002", "�»�", 1, "1");
		 // System.out.println(back);
		//String sql="update �������  set ��λ��=NULL,����=NULL where ���̱��="+"'"+1+"'";
		 
		//String back=SqlTool.insert(new String[]{sql});
		//System.out.println(back);
		
		//String s="501=1|502=1|503=1|504=0|505=1|506=0|507=1|508=0|509=1|510=0|511=1|512=0|513=1|514=0";
		//String sm[]=s.split("=");
		//for(int i=0;i<sm.length;i++)
		//System.out.println(sm[i]);
		
		//String st="1";
		//System.out.println(st.equals(1+""));
		//System.out.println(0b1000);
		//ClientSer.getIntance();
		//String nam="1";Integer i=1;
		//System.out.println(i.toString());
		 
		//System.out.println(nam.equals(i+""+""));
		 
			// System.out.println(PLC.getIntance().ST2_1.firstST.getMap());
		//System.out.println(PLC.getIntance().ST2_1.firstST.setValueByName("��������", "1","0"));
		
		/*try{
		//	String s=ClientSer.getIntance().ReadFromRffid("", 1);
		  // System.out.println(s);
			Test2 t=new Test2();
			new Thread(){public void run(){
				//t.print1(1);
				
			}}.start();
			
			new Thread(){public void run(){
				//t.print2();
				
			}}.start();
			
		}catch(Exception ex){}*/
			 
			//String s= ClientSer.getIntance().c_exeComment("11", 1, 1) ;
			//System.out.println(s);
		
		 Vector<Hashtable<String,Integer>> zl=new Vector<Hashtable<String,Integer>>();
		 Hashtable<String,Integer> h=new Hashtable<String,Integer>(); 
		 h.put("1", 1);
		 zl.addElement(h);
		 
		 Hashtable<String,Integer> h2=new Hashtable<String,Integer>(); 
		 h2.put("1", 0);
		 zl.addElement(h2);
		 
		 Hashtable<String,Integer> h3=new Hashtable<String,Integer>(); 
		 h3.put("1", 0);
		 zl.addElement(h3);
		 
		/* Hashtable<String,Integer> h4=new Hashtable<String,Integer>(); 
		 h4.put("1", 0);
		 zl.addElement(h4);
		 
		 Hashtable<String,Integer> h5=new Hashtable<String,Integer>(); 
		 h5.put("1", 0);
		 zl.addElement(h5);
		 
		 Hashtable<String,Integer> h6=new Hashtable<String,Integer>(); 
		 h6.put("1", 1);
		 zl.addElement(h6);*/
		// removeNull(zl);
		 
		 Vector<Hashtable<String,Integer>> z2=new Vector<Hashtable<String,Integer>>();
		 Hashtable<String,Integer> hh=new Hashtable<String,Integer>(); 
		 hh.put("2", 1);
		 z2.addElement(hh);
		 
		 Hashtable<String,Integer> hh2=new Hashtable<String,Integer>(); 
		 hh2.put("2", 0);
		 z2.addElement(hh2);
		 
		 Hashtable<String,Integer> hh3=new Hashtable<String,Integer>(); 
		 hh3.put("2", 0);
		 z2.addElement(hh3);
		 
		 Hashtable<String,Integer> hh4=new Hashtable<String,Integer>(); 
		 hh4.put("2", 0);
		 z2.addElement(hh4);
		 
		 Hashtable<String,Integer> hh5=new Hashtable<String,Integer>(); 
		 hh5.put("2", 0);
		 z2.addElement(hh5);
		 
		if(zl.size()>z2.size()){
			for(int i=0;i<z2.size();i++){
				
				zl.insertElementAt(z2.get(i), i*2);
			}
			
		}else{
			if(zl.size()<z2.size()){
				int tem=zl.size();
	         for(int i=0;i<tem;i++){
	        	 System.out.println(i);
		        zl.insertElementAt(z2.get(i), i*2);
		
	                   } 
	         
	          for(int i=tem;i<z2.size();i++){
		              zl.addElement(z2.get(i));
	              }
	          }
	     }
		 
		 System.out.println(zl);
		
	}

}
