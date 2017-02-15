package alai.znyk.plc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CarryLine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2995468924150571066L;

	/**
	 * 
	 */


	private  Carry[]cont=new Carry[15];
	
	PLC plc;
	public Carry buffer;
	public CarryLine(PLC plc){
		 this.plc=plc;
	} 
	public boolean addFist(Carry carr){
		if(1==1){
			cont[0]=carr;
			return true;
		}else{return false;}
	}
	public int getLength(){return cont.length;}
	public void setBuffer(Carry carr){this.buffer=carr;}
	
	public Carry getCarry(int index){
		
		return cont[index];
	}
	public boolean removeToNext(int fromSt){
		if(fromSt==cont.length-1){removeLast();return true;}
		if(cont[fromSt]==null) {return false;}
		if(fromSt>cont.length-1){return false;}
		 if(cont[fromSt+1]==null){
			cont[fromSt+1]=cont[fromSt];
			cont[fromSt]=null;
			return true;
		}
		
		return false;
	}
	public void removeLast(){
		cont[cont.length-1]=null;
		
	}
	
	public void setCarryAt(Carry ca,int index){
		cont[index]=ca;
	}
	
	

	public  static void main(String ss[]){
		CarryLine l=new CarryLine(null);
		l.setBuffer( new Carry(1,1,1,1));
		l.addFist(l.buffer);
//		l.buffer=null;
		try{
			long time1=System.currentTimeMillis();
		    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\objectFile.obj"));
			out.writeObject( l);
			out.flush();
			System.out.println(System.currentTimeMillis()-time1);
			out.close();
			
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\objectFile.obj"));
			CarryLine carr=  (CarryLine)in.readObject();
			in.close();
			Carry carr2=l.buffer;
		     System.out.println(carr2.get‘ÿæﬂ–Ú∫≈());
			
		   }catch(Exception ex){ex.printStackTrace();}
		
	}
}
