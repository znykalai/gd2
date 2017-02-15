package alai.znyk.server;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

import alai.znyk.common.SqlPro;

import java.sql.*;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Conn{
    public boolean busy=false;
    Connection con;
    int i=-1;
     //  public static int 借用编号=1000;
      //public static int 事务处理标识=1000;
       //public static int 订单号=1000;
      // public static int 申请号=1000;
      // public static int 发料编号=1;
      // public static int 杂发杂出编号=1;
       int date[]=new int[]{1000,1000,1000,1,10000,1000};
    String url="";
    public Conn(int index,String drv,String url,int[] da){
        i=index;
        this.url=url;
        date=da;
      try{//select table_name from user_tables
             Class.forName(drv).newInstance();
             con=DriverManager.getConnection(url,"a3info","a3infosql");
             //con=DriverManager.getConnection(url,"hr","210210");


        }
catch(Exception e){
    e.printStackTrace();
}

}
public void initDate(){

try{

                 /*Statement st=con.createStatement();
                 ResultSet set=st.executeQuery("select max(to_number(借用标号)) from "+SqlPro.借用);
                 if( set.next())
                 date[0]=set.getInt(1);
                 if(date[0]==0)date[0]=1000;
                 System.out.println(url+"借用编号"+date[0]);
                 set=st.executeQuery("select max(订单号) from 订单接受题头");
                if( set.next())
                  date[1]=set.getInt(1);
                  if(date[1]==0)date[1]=1000;
                 System.out.println(url+"订单号"+date[1]);

                 set=st.executeQuery("select max(发料号) from 发料号");

                 if(set.next()){

                    date[3]=set.getInt(1);
              System.out.println(url+"发料编号="+date[3]);
                    }
                 set=st.executeQuery("select max(申请号) from 申请题头");
                 if(set.next()){

                   date[2]=set.getInt(1);
                   if(date[2]==0)date[2]=1000;
                   System.out.println(url+"申请号="+date[2]);
                   }
                 set=st.executeQuery("select max(合同ID) from 销售订单");
             if(set.next()){
                                      date[4]=set.getInt(1);
                                      if(date[4]==0)date[4]=10000;

                   }

                   set=st.executeQuery("select max(nvl(单据号,0)) from 杂发杂收题头");
      if(set.next()){
                               date[5]=set.getInt(1);
                               if(date[5]==0)date[5]=10000;

            }

                   System.out.println(url+"销售订单号="+date[4]);
                set.close();*/
}catch(Exception ex){ex.printStackTrace();}

 }
 public int get杂项编号(){
    return date[5];
 }
 public void set杂项编号(int sum){
    date[5]=sum;
}



public int get借用编号(){
return date[0];
 }
 public int get申请号(){
return date[2];
 }
 public int get订单号(){
 return date[1];
 }
 public int get销售订单号(){
       return date[4];
 }
 public int get发料编号(){
 return date[3];
 }
 public void set借用编号(int i){
    date[0]=i;
  }
  public void set申请号(int i){
    date[2]=i;
  }
  public void set订单号(int i){
        date[1]=i;
  }
  public void set发料编号(int i){
        date[3]=i;
 }
 public void set销售订单号(int i){
        date[4]=i;
 }

public static void main(String ss[]){
  // new Conn(1);
}
public Connection getCon(){
    busy=true;
    return con;
    }
    public boolean isBusy(){
    return busy;
    }
 public void realseCon(){
     busy=false;
    // System.out.println("连接释放！");
 }
  }
