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
     //  public static int ���ñ��=1000;
      //public static int �������ʶ=1000;
       //public static int ������=1000;
      // public static int �����=1000;
      // public static int ���ϱ��=1;
      // public static int �ӷ��ӳ����=1;
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
                 ResultSet set=st.executeQuery("select max(to_number(���ñ��)) from "+SqlPro.����);
                 if( set.next())
                 date[0]=set.getInt(1);
                 if(date[0]==0)date[0]=1000;
                 System.out.println(url+"���ñ��"+date[0]);
                 set=st.executeQuery("select max(������) from ����������ͷ");
                if( set.next())
                  date[1]=set.getInt(1);
                  if(date[1]==0)date[1]=1000;
                 System.out.println(url+"������"+date[1]);

                 set=st.executeQuery("select max(���Ϻ�) from ���Ϻ�");

                 if(set.next()){

                    date[3]=set.getInt(1);
              System.out.println(url+"���ϱ��="+date[3]);
                    }
                 set=st.executeQuery("select max(�����) from ������ͷ");
                 if(set.next()){

                   date[2]=set.getInt(1);
                   if(date[2]==0)date[2]=1000;
                   System.out.println(url+"�����="+date[2]);
                   }
                 set=st.executeQuery("select max(��ͬID) from ���۶���");
             if(set.next()){
                                      date[4]=set.getInt(1);
                                      if(date[4]==0)date[4]=10000;

                   }

                   set=st.executeQuery("select max(nvl(���ݺ�,0)) from �ӷ�������ͷ");
      if(set.next()){
                               date[5]=set.getInt(1);
                               if(date[5]==0)date[5]=10000;

            }

                   System.out.println(url+"���۶�����="+date[4]);
                set.close();*/
}catch(Exception ex){ex.printStackTrace();}

 }
 public int get������(){
    return date[5];
 }
 public void set������(int sum){
    date[5]=sum;
}



public int get���ñ��(){
return date[0];
 }
 public int get�����(){
return date[2];
 }
 public int get������(){
 return date[1];
 }
 public int get���۶�����(){
       return date[4];
 }
 public int get���ϱ��(){
 return date[3];
 }
 public void set���ñ��(int i){
    date[0]=i;
  }
  public void set�����(int i){
    date[2]=i;
  }
  public void set������(int i){
        date[1]=i;
  }
  public void set���ϱ��(int i){
        date[3]=i;
 }
 public void set���۶�����(int i){
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
    // System.out.println("�����ͷţ�");
 }
  }
