package alai.znyk.common;
import java.util.Hashtable;
import java.net.URL;
import javax.swing.ImageIcon;
import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import javax.swing.JTable;
import java.awt.Desktop;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.Dimension;

import java.net.Socket;
import java.io.DataInputStream;

public class SqlPro {
    public static String tp=null;
      public static boolean ��ʾ=false;
     public static float Ĭ������=5;//������û�趨װ��ϵ��ʱ��ֵ
   static Hashtable<String,Integer> map=new Hashtable<String,Integer>();
    public static int �Ѷ��1=1;
    public static int �Ѷ��2=2;
    public static String ip_port="//127.0.0.1:5650";
    public static String ip_port2="//127.0.0.1:5000";
    public static int ����=1;
    public static int �Ѷ��1״̬=2;
    public static int �Ѷ��2״̬=3;
    public static int ������=4;
    public static int ȥ����=5;
    public static int AGV1=6;
    public static int AGV2=7;
    public static int A��������=8;
    public static int B��������=9;
    public static int �Ѷ������=3;
    public static int AGV������=3;
    public static int �Ŷ�=1;
    public static int ������=2;
    public static int ���=3;

    public static String kind[]
    =new String[]{"�ޱ�βĪ��Բ׶�׵���","����","��Ƭ","���׵���","�б�βĪ��Բ׶�׵���",
     "�Զ���������׶��","��ƽ�͹��ߵ���",
     "��ʽ��ϳ������","�ӳ���ʽ��ϳ����","Ī���꿨ͷ����","ǿ��ϳ��ͷ����","������ϳ������"};
    public static Hashtable canshu=new Hashtable();
    static{

   canshu.put("�ޱ�βĪ��Բ׶�׵���",new String[]{"����(L)","��Բֱ��(D)","�ڿ�ֱ��dm","׶��"});
   canshu.put("����",new String[]{"����ֱ��","�ӿڿ׾�","����","�ۿ�","����"});
   canshu.put("��Ƭ",new String[]{"��Ƭ����","�ӹ�����","��","��","��","�������",
                          "�������ٶ�","���۽�����","�ƴ�ת��","�ƴ�����ٶ�","��������",
      "�����"

      }
);
   canshu.put("���׵���",new String[]{"����ֱ��","��������","����","�ɻ��˳�ͷ�ͺ�"});
   canshu.put("�б�βĪ��Բ׶�׵���",new String[]{"����(L)","��Բֱ��(D)","�ڿ�ֱ��(dm)","׶��"});
   canshu.put("�Զ���������׶��",new String[]{"����(L)","�ڿ�ֱ��(dm)","׶��"});
   canshu.put("��ƽ�͹��ߵ���",new String[]{"����(L)","��Բֱ��(D)","�ڿ�ֱ��(dm)","׶��"});
   canshu.put("��ʽ��ϳ������",new String[]{"����(L)","ֱ��(D1)","ֱ��(D2)","ֱ��(dm)","�ۿ�(W)","׶��"});
   canshu.put("�ӳ���ʽ��ϳ����",new String[]{"����(L)","ֱ��(D1)","ֱ��(D2)","ֱ��(dm)","�ۿ�(W)","׶��"});
     canshu.put("Ī���꿨ͷ����",new String[]{"����(L)","ֱ��(dm)","׶��"});
     canshu.put("ǿ��ϳ��ͷ����",new String[]{"����(L)","ֱ��(D)","׶��"});
     canshu.put("������ϳ������",new String[]{"����(L)","����(L1)","ֱ��(D)","ֱ��(dm)","��(W)","׶��"});
   }

    public static Hashtable<String,Integer> getMap(){
    	//���湤λ
    	     if(map.size()==0){
                  map.put(�Ѷ��1+"1ST", 501); map.put(�Ѷ��1+"2ST", 503);
                  map.put(�Ѷ��1+"3ST", 505); map.put(�Ѷ��1+"4ST", 507);
                  map.put(�Ѷ��1+"5ST", 509); map.put(�Ѷ��1+"6ST", 511);map.put(�Ѷ��1+"7ST", 513);
                  map.put(�Ѷ��2+"1ST", 601); map.put(�Ѷ��2+"2ST", 603);
                  map.put(�Ѷ��2+"3ST", 605); map.put(�Ѷ��2+"4ST", 607);
                  map.put(�Ѷ��2+"5ST", 609); map.put(�Ѷ��2+"6ST", 611);map.put(�Ѷ��2+"7ST", 613);
    	     }
    	
    	    return map;
    }
    ///****//
    public static String ���init="����ʼ��";
    public static String[] getDate(){
          String td[]=new String[2];
          java.util.Date date=new  java.util.Date();
          String time=date.toLocaleString();
          String ss[]=time.split(" ");
          String t=ss[0];
          String t2=ss[1];
          String temp[]=t.split("-");
          String dd=temp[0]+"-"+temp[1]+"��-"+temp[2];
          String hms[]=t2.split(":");
          int hour=Integer.parseInt(hms[0]);
          int sec=Integer.parseInt(hms[2]);
          String hh="";
          if(hour>=12){
           hh=(hour-12==0?12:hour-12)+":"+hms[1]+":"+hms[2]+" ����";
          }else{
           hh=(hour==0?12:hour)+":"+hms[1]+":"+hms[2]+" ����";
       }

         dd=dd+" "+hh;

         td[0]="TO_DATE('"+dd+"','yyyy-Mon-dd HH:MI:SS AM')";

         String dd8=temp[0]+"-"+(temp[1].length()==1?0+temp[1]:temp[1])+"-"+(temp[2].length()==1?0+temp[2]:temp[2]);
         String dd9=(hms[0].length()==1?0+hms[0]:hms[0])+":"+(hms[1].length()==1?0+hms[1]:hms[1])+":"+(hms[2].length()==1?0+hms[2]:hms[2]);
         td[1]="'"+dd8+" "+dd9+"'";
      return td;
  }

  public static String getRq(){
      String td[]=new String[2];
      java.util.Date date=new  java.util.Date();
      String time=date.toLocaleString();
      String ss[]=time.split(" ");
      String t=ss[0];
      return t;
}

  public static int getMouths(String sd,String ed){
  String sm1[]=sd.split("-");
  String sm2[]=ed.split("-");
   int y1=Integer.parseInt(sm1[0]);
   int m1=Integer.parseInt(sm1[1]);
   int y2=Integer.parseInt(sm2[0]);
   int m2=Integer.parseInt(sm2[1]);
   int yy=y2-y1;
   int mm=m2-m1;

    return yy*12+mm;
  }
  public static String addMouth(String date,int mouths){
      String sm1[]=date.split("-");
      int y1=Integer.parseInt(sm1[0]);
      int m1=Integer.parseInt(sm1[1]);
      int m=m1+mouths;
      if(m<=12){return y1+"-"+m+"-"+1;}else{
         return y1+(m/12)+"-"+m%12+"-"+1;
      }
  }
  public static void main(String ss[]){
  System.out.println(addMouth("2012-7-1",18));
  }
  public static String getDate(Object date){

          if(date==null)return "''";else{if(date.toString().equals(""))return "''";}
           String time=date.toString();
           String ss[]=time.split(" ");
           if(ss.length>1){
           String t=ss[0];
           String t2=ss[1];
           t2=t2.replace(".0","");
           String temp[]=t.split("-");
           String dd=temp[0]+"-"+(temp[1].startsWith("0")?temp[1].substring(1):temp[1])+"��-"+temp[2];
           String hms[]=t2.split(":");
           int hour=Integer.parseInt(hms[0]);
           int sec=Integer.parseInt(hms[2]);
           String hh="";
           if(hour>=12){
            hh=(hour-12==0?12:hour-12)+":"+hms[1]+":"+hms[2]+" ����";
           }else{
            hh=(hour==0?12:hour)+":"+hms[1]+":"+hms[2]+" ����";
        }

          dd=dd+" "+hh;

         return "TO_DATE('"+dd+"','yyyy-Mon-dd HH:MI:SS AM')";
           }
      if(ss.length==1){
       String t=ss[0];
             return "TO_DATE('"+ss[0]+"','YYYY-MM-DD')";
          }
        return "''";
   }
   public static void  daoChu(JTable jTable1,String name) {



       DataOutputStream dout=null;
       OutputStreamWriter outr=null;
       BufferedWriter write=null;
       FileOutputStream out=null;
     try{
          File f=File.createTempFile(name,".xls");

          out=new FileOutputStream(f);


             dout=new DataOutputStream(out);
             outr=new OutputStreamWriter(out);
             write=new BufferedWriter(outr);
             String ss="";
            for(int c=0;c<jTable1.getColumnCount();c++){
                if(c!=jTable1.getColumnCount()-1)
               ss=ss+jTable1.getColumnName(c)+"\t";else
               ss=ss+jTable1.getColumnName(c)+"\n";
            }
             write.write(ss);
             for(int r=0;r<jTable1.getRowCount();r++){
                 ss="";
               for(int c=0;c<jTable1.getColumnCount();c++){
                    if(c!=jTable1.getColumnCount()-1)
                     ss=ss+(jTable1.getValueAt(r,c)==null?"":jTable1.getValueAt(r,c))+"\t";else
                     ss=ss+(jTable1.getValueAt(r,c)==null?"":jTable1.getValueAt(r,c))+"\n";
               }
                write.write(ss);
              }
              write.close();
              outr.close();
              dout.close();
              out.close();
         Desktop desktop = Desktop.getDesktop();
         desktop.open(f);


     }catch(Exception ex){
                  ex.printStackTrace();
                }
    }

    public static void showWinning(String s){
       JDialog da=new JDialog();
       da.setModal(true);
       da.setTitle("��ʾ");
       JLabel la=new JLabel(s);
       da.getContentPane().add(la);
       da.setSize(500,200);
       Dimension screen= Toolkit.getDefaultToolkit().getScreenSize();
       da.setLocation((screen.width-da.getWidth())/2,(screen.height-da.getHeight())/2);
       da.setVisible(true);

    }

    public   static String get_����(String meg,String typ){
               typ="\\\""+typ+"\\\"";

              if (meg==null||!meg.contains(typ)) return "";
      try{
              int start=meg.indexOf(typ);
              int len=typ.length();
              int end=-1;
               end=meg.indexOf(",", start);
              // System.out.println(typ+"||||"+"end="+end+", start="+start);
       if(end==-1) end=meg.indexOf("}", start);
          String temp=meg.substring(start,end);
          String sm[]=temp.split(":");
          return sm[1].replace("\\\"","");
        }catch(Exception e){e.printStackTrace();}

              return "";
      }

      public   static String get_iport_sys_Time(String meg){
                String  typ="\\\"sys_time\\\"";

                 if (meg==null||!meg.contains(typ)) return "";
         try{
                 int start=meg.indexOf(typ);
                 int len=typ.length();
                 int end=-1;
                  end=meg.indexOf(",", start);
                 // System.out.println(typ+"||||"+"end="+end+", start="+start);
          if(end==-1) end=meg.indexOf("}", start);
             String temp=meg.substring(start,end);
            // String sm[]=temp.split(":");
             return temp;
           }catch(Exception e){e.printStackTrace();}

                 return "";
         }

         public static String getMachinIDFormMeg(String meg){
                if(meg==null) return "";
               try{
                String s=meg.substring(0,meg.indexOf(","));
                String sm[]=s.split(":");
                String back=sm[1].replace("\"","");
                  return back;
                 }catch(Exception ex){
                   //  System.out.println("getMachinIDFormMeg-err"+meg);
                   //  ex.printStackTrace();

                 }
                  return "";
           }

           public static int getType(String meg){
                     String typ="\"messageType\"";
                     String shu="";
                     if (meg==null||!meg.contains(typ)) return -1;
             try{
                     int start=meg.indexOf(typ);
                     int len=typ.length();
                     int end=-1;
                      end=meg.indexOf(",", start);
              if(end==-1) end=meg.indexOf("}", start);
                 String temp=meg.substring(start,end);
                 String sm[]=temp.split(":");
                 shu=sm[1];
                 return Integer.parseInt(sm[1].replace("}",""));
               }catch(Exception e){
                   System.out.println("error_"+typ+",shu="+shu+",meg="+meg);
                 //e.printStackTrace();
               }

                     return -1;
    }

   /* public static String  writeValue_103(TestIport2  por,String tui,String type,String value){
   int mod=1;
   String value2=value;
   try{
       while(mod==1){
          por.getClient(tui).writeValue(type,value);
          Thread.sleep(200);
       String temp1=por.getMesg_����("103-"+tui);
       String TP=SqlPro.get_����(temp1,type);//�������ϵ�A
       type=type.toUpperCase();
       if(type.startsWith("PLB")){
         if(value.equals("0")){value2="OF";}else{value2="ON";}
        }
        if(TP.equals(value2)){
              break;}

         }
          return "�ɹ�";
      }catch(Exception ex){
           ex.printStackTrace();
           return "ʧ��";
      }
}
*/
  public static String getTiaoma(String ip){

      try{
                                Socket  so=new Socket(ip,23);
                                byte[] rec2=new byte[512];

                                DataOutputStream     out2=new DataOutputStream(so.getOutputStream());
                                out2.write("||>trigger on\r\n".getBytes("UTF-8"));
                                 System.out.println("SQLPROд��");
                                    Thread.sleep(500);
                                DataInputStream    in2=new DataInputStream(so.getInputStream());
                                System.out.println("SQLPRO��ʼ����-��������");
                                String name2="";
                                     int len2=-1;
                                    if(( len2=in2.read(rec2))!=-1){
                                     System.out.println("SQLPRO������");
                                     name2=new String(rec2,0,len2,"UTF-8");
                                     if(name2!=null){
                                      name2=name2.replace("\n","");
                                      name2=name2.replace("\r","");
                                       SqlPro.tp=name2;
                                     }
                                     System.out.println(name2);
                                     System.out.println(name2.length());

                                     in2.close();
                                     out2.close();
                                     so.close();
                                     return name2;
                                    }
                                 }catch(Exception ee){ee.printStackTrace();}
                                 return "fail!";

  }

 
}
