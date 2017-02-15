package alai.znyk.server;

import java.sql.*;
import java.util.Hashtable;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

import java.rmi.Naming;


public  class  ConnactionPool {
	private static ConnactionPool pool;
    public static Hashtable ipCont=new Hashtable();
   //中捷//
 static Conn cont[]=new Conn[10];
   int date[]=new int[]{1000,1000,1000,1,10000,1000};
  public Hashtable quest=new Hashtable();
  private String drv="com.mysql.jdbc.Driver";
  private String url="jdbc:mysql://localhost:3306/kufang";
//  private String drv="oracle.jdbc.OracleDriver";
//  private String url="jdbc:oracle:thin:@127.0.0.1:1521:orc";



  private ConnactionPool(){
       System.out.println("****ConnactonPool is start!****");

   try{

       for(int i=0;i<cont.length;i++){
       cont[i]=new Conn(i,drv,url,date);


       }
        cont[0].initDate();
        System.out.println("立体库is start!");
    }catch(Exception ex){
          System.out.println("*****立体库启动失败****");
        }
   

    }
  
  public static synchronized ConnactionPool getPool(){
	  if(pool==null){
		  
		  pool=new ConnactionPool();
		  return pool;
	  }else{
		  
		  return pool;
	  }
	  
  }
    public void addQuest(long questId,Thread thread){
     quest.put(questId,thread);
     }
     public void removeQuest(long questId){
      quest.remove(questId);
      }
  
    public static void setIpCont(String ip,String yongh){
     if(yongh.startsWith("zj")){
      ConnactionPool.ipCont.put(ip,"B2");
          }
          if(yongh.startsWith("f3")){
     ConnactionPool.ipCont.put(ip,"F3");
         }
         if(yongh.startsWith("e3")){
            ConnactionPool.ipCont.put(ip,"E3");
         }
     if(yongh.startsWith("f1")){
                 ConnactionPool.ipCont.put(ip,"F1");
         }
      if(yongh.startsWith("e4")){
                ConnactionPool.ipCont.put(ip,"E4");
        }
        if(yongh.startsWith("f4")){
                      ConnactionPool.ipCont.put(ip,"F4");
        }

       }
    

     public static synchronized Conn getCon2(String yongh){
      String name=yongh==null?"":yongh;
    /////////////////////////多组织应该把这个删掉
      if(true){
             for(int i=0;i<cont.length;i++){
               if(!cont[i].isBusy()){
            	   cont[i].busy=true;//新增加的
                  return cont[i] ;
                      }
              }

        }
   

       return null;
     }

    public static Conn getCon(UnicastRemoteObject rom){
         Conn co=null;
         String zhu=null;
        try{
        String name=rom.getClientHost();
      //  System.out.println("========"+name);
        zhu=(String)ipCont.get(name);
        if(zhu==null){
           //这里在立体库中先禁用，那是因为服务器中用到了RMI调用，但是没向客户端一样，生成了监听程序
            /*
        ReccptRmote  rm = (ReccptRmote ) Naming.lookup("//"+name+":22222" +
                                  "/client");
        String yong=rm.get_yonghu();
        setIpCont(name,yong);
          */
    }
        zhu=(String)ipCont.get(name);

            }catch(Exception ex){
                   ex.printStackTrace();
                  // StartServer.appendText(ex.getMessage());
        }

        //增加的默认组织 多组织应该把这个删掉
  for(int i=0;i<cont.length;i++){
      if(!cont[i].isBusy()){
        co=cont[i] ;
        return co;
        }
    }

///////////////////



        if(zhu.equals("B2")){
    for(int i=0;i<cont.length;i++){
     if(!cont[i].isBusy()){
        co=cont[i] ;

     break;
     }
    }
        }

       


    return co;
    }

    public static int getNotUseNum(){
     int num=0;
    for(int i=0;i<cont.length;i++){
     if(!cont[i].isBusy()){
       num++;
        }

    }
    return num;
   }
  public static void realse(int i){
  cont[i].realseCon();
  }

  public static void main(String ss[]){
	  ConnactionPool p=ConnactionPool.getPool();
	  try{
		Conn conn= p.getCon2("") ;
		Connection con=conn.getCon(); 
		Statement st=con.createStatement();
		
		long time1=System.currentTimeMillis();
        ResultSet set=st.executeQuery("select * from t_userequworkevents where id=866");
        int num= set.getMetaData().getColumnCount();
        int r=1;
        while(set.next()){
        	System.out.print((r++)+":");
        	 for(int i=1;i<num;i++){
        	
        	System.out.print(set.getObject(i)+" ");}
        	 System.out.println();
        	
        }
       
		  System.out.println(System.currentTimeMillis()-time1);
		  Conn conn2= p.getCon2("") ;
			Connection con2=conn2.getCon(); 
			Statement st2=con2.createStatement();
		  long time2=System.currentTimeMillis();
	        ResultSet set2=st2.executeQuery("select * from t_userequworkevents where id=891");
	        int num2= set2.getMetaData().getColumnCount();
	        int r2=1;
	        while(set2.next()){
	        	System.out.print((r2++)+":");
	        	 for(int i=1;i<num;i++){
	        	
	        	System.out.print(set2.getObject(i)+" ");}
	        	 System.out.println();
	        	
	        }
	       
			  System.out.println(System.currentTimeMillis()-time2);
	  }catch(Exception ex){ex.printStackTrace();}
  }

}
