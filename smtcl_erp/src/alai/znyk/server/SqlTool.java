package alai.znyk.server;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import alai.znyk.common.SqlPro;
import alai.znyk.plc.PLC;

public class SqlTool {
	
	public static Vector findInVector(String sql) {
        Vector has=new Vector();


        ConnactionPool p=ConnactionPool.getPool();
		Conn conn= p.getCon2("") ;
		Connection con=conn.getCon(); 
       
          Statement  st=null;
          ResultSet set=null;
     try{
     //    System.out.println(sql+ this.getClientHost());

   
         st=con.createStatement();
        set=st.executeQuery(sql);

     ResultSetMetaData rs=set.getMetaData();
     int num=rs.getColumnCount();


    while(set.next()){
        Vector v=new Vector();

    for(int i=1;i<num+1;i++){
       v.addElement(set.getObject(i));
      }
       has.addElement(v);
    }
    set.close();
    st.close();
    conn.realseCon();
    return has;
     }
   catch(Exception e){
        e.printStackTrace() ;
       try {
    	   conn.realseCon();
           set.close();
           st.close();

       } catch (SQLException ex) {

           ex.printStackTrace();
           conn.realseCon();
       }
          conn.realseCon();
          e.printStackTrace();
       return null;

   }

     //   return has;
 }
	
    public static String findOneRecord(String sql) {

         String val=null;
         ConnactionPool p=ConnactionPool.getPool();
         Conn fac=p.getCon2("");
         Statement  st=null;
     try{


     Connection con=fac.getCon();
     st=con.createStatement();
     ResultSet set=st.executeQuery(sql);

     ResultSetMetaData rs=set.getMetaData();
     int num=rs.getColumnCount();
     while(set.next()){
    for(int i=1;i<num+1;i++){
        if(i==1)
        val=(set.getObject(i)==null||set.getObject(i).equals("")?0:set.getObject(i))+"!_!";
    else val=val+(set.getObject(i)==null||set.getObject(i).equals("")?0:set.getObject(i))+"!_!";

      }
       val=val.substring(0,val.length()-3);
       break;
    }
    st.close();
    fac.realseCon();
    return val;
     }
   catch(Exception e){
       try {
           st.close();
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
        fac.realseCon();
        e.printStackTrace();
       return null;

   }


      }	
    
    public static String insert(String[] sql)  {
    	       ConnactionPool p=ConnactionPool.getPool();
               Conn conn=p.getCon2("");

                 Statement st=null;
                 Connection con=conn.getCon();
            try{
                   st=con.createStatement();
                   con.setAutoCommit(false);
             for(int i=0;i<sql.length;i++)
                   st.executeUpdate(sql[i]);



                  con.commit();
                  con.setAutoCommit(true);
                  st.close();
                  conn.realseCon();
             }catch(Exception ex){ ex.printStackTrace();
                try {
                    con.rollback();
                    con.setAutoCommit(true);
                    st.close();
                    conn.realseCon();
                     return ex.getMessage();
                  } catch (SQLException ex1) {
                      ex1.printStackTrace() ;
                      conn.realseCon();
                     return ex1.getMessage();
                  }

               }
                  conn.realseCon();


                  return "��¼���³ɹ���";

    }  
    
    public static String update7Line(String tp,String fromID,String toID)  {
	       ConnactionPool p=ConnactionPool.getPool();
           Conn conn=p.getCon2("");

          Statement st=null;
          Connection con=conn.getCon();
     try{
            st=con.createStatement();
            con.setAutoCommit(false);
            String sql1="update ��λ��     set ���̱��=NULL where ��λ���="+"'"+fromID+"'";
            String sql2="update ��λ��     set ���̱��='"+tp+"' where ��λ���="+"'"+toID+"'";
            String sql3="update �������  set ��λ��="+toID+" where ���̱��="+"'"+tp+"'";
           
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);
            st.executeUpdate(sql3);


           con.commit();
           con.setAutoCommit(true);
           st.close();
           conn.realseCon();
      }catch(Exception ex){ ex.printStackTrace();
         try {
             con.rollback();
             con.setAutoCommit(true);
             st.close();
             conn.realseCon();
              return ex.getMessage();
           } catch (SQLException ex1) {
               ex1.printStackTrace() ;
               conn.realseCon();
              return ex1.getMessage();
           }

        }
           conn.realseCon();


           return "��¼���³ɹ���";

}  
    
   public static String  add����ָ��(String tp,String fromID,String toID,String type/*�ϻ����»��������߻���*/, 
		  int todaku/*1=�ش�⣬��1=����*/, String machineID){
	   ConnactionPool p=ConnactionPool.getPool();
       Conn conn=p.getCon2("");

      Statement st=null;
      ResultSet set=null;
      Connection con=conn.getCon();
      boolean have=false;
      String back="δ����";
 try{
        st=con.createStatement();
        con.setAutoCommit(false);
        int fomI=Integer.parseInt(fromID);
        int toI=Integer.parseInt(toID);
        if(type.equals("�ϻ�")){
              if(fomI==60001||fomI==60002){
        		  if(fomI==60001){
        			  if((toI>0&&toI<29)||toI>500&&toI<615){}else{
        				  have=true;
        				  back="�������"+fomI+"��λ�ѻ��ϵ�"+toI+"��λ1";    
        			  }
        			  
        		  }
        		  if(fomI==60002){
        			  if(toI>0&&toI<29){}else{
        				  have=true;
        				  back="�������"+fomI+"��λ�ѻ��ϵ�"+toI+"��λ2";    
        			  }
        			  
        		  }
        		  
        	  }else{
        		  have=true;
        		  back="�������"+fomI+"��λ�ѻ��ϵ�"+toI+"��λ2";  
        		  
        	  }
        	 
        }
        
        if(type.equals("�»�")){
        	if(!((fomI>0&&fomI<23)&&(toI==60002||(toI>500&&toI<615)))){
      		  have=true;
            	  back="�������"+fomI+"��λ�ѻ��µ�"+toI+"��λ";
      	  }
      }
      
        if(type.equals("�����߻���")){
        	  if(!(fomI>500&&fomI<615)){
        		  have=true;
              	  back="���Ͳ������"+fomI+"������"+toI+"��λ";
        	  }
        }
        
        if(!have){
        String sqll="select idEvent,״̬,״̬2 from ���⶯��ָ��  where ״̬<>'���' and ���̱��='"+tp+"' order by idEvent";
        set=st.executeQuery(sqll);
        if(set.next()){
        	have=true;
        	back="����������̵�ָ����δִ�����ָ��";
         }
        }
        
       if(type.equals("�����߻���")){
    	   //��ȡ�Ļ�����û���������
  		 if(!have){
   	        String sql2="select ��λ���   from ��λ��   where  ���̱��='"+tp+"' and ��λ���='"+fromID+"'";	
   	        set=st.executeQuery(sql2);
   	         if(!set.next()){
   	        	have=true;
   	        	 back="��ָ��λ��û�������";
   	           }	
   	        }
  		 
  		 if(!have){
    	   String  ql="insert into ���⶯��ָ�� (��Դ,�������,����,"+
                   "���̱��,״̬,��Դ��λ��,�Żػ�λ��,״̬2,������,�Ƿ�ش��,�½�ʱ��) values("+
                   "'����',"+
                   "'PACKװ��',"+
                   "'�����߻���',"+
                   "'"+tp+"',"+
                    "'�Ŷ�',"+
                     "'"+fromID+"',"+
                     "'"+toID+"',"+
                      "'"+(todaku==1?0:1)+"',"+
                      "'"+machineID+"',"+
                      "'"+todaku+"',"+
                      SqlPro.getDate()[1]+
                   ")";
			   st.executeUpdate(ql);
			   back="ָ��ɹ�����";
  		       }
			   
         }else{
        	
        	 if(type.equals("�ϻ�")){
        		 
        		 //�ж�ȥ���Ļ�λ��û������
        		
        		 if(!have){
        			  
         	        String sql2="select ���̱��  from ��λ��   where  ��λ���='"+toID+"'";	
         	        set=st.executeQuery(sql2);
         	         if(set.next()){
         	        	Object t=set.getObject(1)==null?"":set.getObject(1);
         	        	if(!t.equals("")){
         	        		have=true;
         	        	         back="ȥ���Ļ�λ�Ѿ�������";
         	        	          }
         	           }	
         	        }
        		 //�ж�ȥ���Ļ�λ��ָ�����������û�б�ָ���������
        		 if(!have){
        		String sql2="select idEvent,�Żػ�λ��,״̬  from ���⶯��ָ��  where ״̬<>'���' and �Żػ�λ��='"+toID+"' order by idEvent";
          	     set=st.executeQuery(sql2);
          	         if(set.next()){
          	        	Object t=set.getObject(1)==null?"":set.getObject(1);
          	        	if(!t.equals("")){
          	        		have=true;
          	        	    back="��ָ�������ȥ���Ļ�λ����������";
          	        	          }
          	           }	
          	        }
        		 //��������ж϶�û�У���ô��ָ����������������
        		  if(!have){
        			  String  ql="insert into ���⶯��ָ�� (��Դ,�������,����,"+
                              "���̱��,״̬,��Դ��λ��,�Żػ�λ��,״̬2,������,�Ƿ�ش��,�½�ʱ��) values("+
                              "'����',"+
                              "'PACKװ��',"+
                              "'�ϻ�',"+
                              "'"+tp+"',"+
                               "'�Ŷ�',"+
                                "'"+fromID+"',"+
                                "'"+toID+"',"+
                                 "'"+1+"',"+
                                 "'"+machineID+"',"+
                                 "'"+0+"',"+
                                 SqlPro.getDate()[1]+
                              ")";
        			    st.executeUpdate(ql);
        			    back="ָ��ɹ�����";
        		  }
        		 
        	 }//end�ϻ�

        	 if(type.equals("�»�")){
        		 //��ȡ�Ļ�����û���������
        		 if(!have){
         	        String sql2="select ��λ���   from ��λ��   where  ���̱��='"+tp+"' and ��λ���='"+fromID+"'";	
         	        set=st.executeQuery(sql2);
         	         if(!set.next()){
         	        	have=true;
         	        	 back="��ָ��λ��û�������";
         	           }	
         	        }
        		 
        		 if(toID.equals("60002")){
        		 //�ж�ȡ������̨��û������
        		 if(!have){//��ʱ�Ȳ���
        		
          	        }
        		 
        		   }else{
        		  //�ж�7����������û�л�	   
        			   if(!have){
                	        String sql2="select ���̱��  from ��λ��   where  ��λ���='"+toID+"'";	
                	        set=st.executeQuery(sql2);
                	         if(set.next()){
                	        	Object t=set.getObject(1)==null?"":set.getObject(1);
                	        	if(!t.equals("")){
                	        		have=true;
                	        	    back="ȥ���Ļ�λ�Ѿ�������";
                	        	          }
                	           }	
                	         }    
        		   }
        		 
        		 //��������ж϶�û�У���ô��ָ����������������
        		  if(!have){
        			  int t=Integer.parseInt(toID);
        			   if(t>500&&t<615)todaku=0;
        			  String  ql="insert into ���⶯��ָ�� (��Դ,�������,����,"+
                              "���̱��,״̬,��Դ��λ��,�Żػ�λ��,״̬2,������,�Ƿ�ش��,�½�ʱ��) values("+
                              "'����',"+
                              "'PACKװ��',"+
                              "'�»�',"+
                              "'"+tp+"',"+
                               "'�Ŷ�',"+
                                "'"+fromID+"',"+
                                "'"+toID+"',"+
                                 "'"+(todaku==1?0:1)+"',"+
                                 "'"+machineID+"',"+
                                 "'"+todaku+"',"+
                                 SqlPro.getDate()[1]+
                              ")";
        			    st.executeUpdate(ql);
        			    back="ָ��ɹ�����";
        		  }
        		 
        	 }//end�»�
        		 	 
        	 
         }

      
       con.commit();
       con.setAutoCommit(true);
       if(set!=null)
       set.close();
       st.close();
       conn.realseCon();
  }catch(Exception ex){ ex.printStackTrace();
     try {
         con.rollback();
         con.setAutoCommit(true);
         if(set!=null)
         set.close();
         st.close();
         conn.realseCon();
          return ex.getMessage();
       } catch (SQLException ex1) {
           ex1.printStackTrace() ;
           conn.realseCon();
          return ex1.getMessage();
       }

    }
       conn.realseCon();


       return back;
	 
    	
    }
   
//��ҪӦ������������̨   
   public static String autoUpPallet(String tp,String wuliao,String fromID,String machineID)  {
	   Integer id=Integer.parseInt(fromID);
	   if(id>60002&&id<60001)
	   {
		  
		     return "���ϵ㳬����Χ!_!-1";
	   }
	   if(wuliao.equals("")||wuliao==null){wuliao="-1";}
	   
       ConnactionPool p=ConnactionPool.getPool();
       Conn conn=p.getCon2("");
       String back="δ����!_!-1";
       Statement st=null;
       ResultSet set=null;
      Connection con=conn.getCon();
 try{
        st=con.createStatement();
        con.setAutoCommit(false);
        boolean isToLine=false;
       if(fromID.equals("60001")){
    	 //�ж��������Ӧ��ȥ�ĸ���λ������ȥ7����λ,���ȼ��7����λ��û��ָ�Ȼ���ڼ�⹤λ����û������
    	   String sql="SELECT DISTINCT ��λ,����-IFNULL(�������,0) from �䷽ָ�����   where ����='"
    	   +wuliao+"' AND װ����='"+machineID+"' and ����-IFNULL(�������,0)>0 ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 2 ";
    	  //1.�����жϵ�ǰָ�������û����Ҫ������ϵ�
    	   for(int i=1;i<8;i++){
    	   String wul1= (machineID.equals(SqlPro.�Ѷ��1+"")?PLC.getIntance().STC1.get(i).firstST.get���ϱ���():
    		   PLC.getIntance().STC2.get(i).firstST.get���ϱ���());
    	   String wul2= (machineID.equals(SqlPro.�Ѷ��1+"")?PLC.getIntance().STC1.get(i).secondST.get���ϱ���():
    		   PLC.getIntance().STC2.get(i).secondST.get���ϱ���());
    	   int sl1=  (machineID.equals(SqlPro.�Ѷ��1+"")?PLC.getIntance().STC1.get(i).firstST.getʣ������():
    		   PLC.getIntance().STC2.get(i).firstST.getʣ������());
    	   int sl2= (machineID.equals(SqlPro.�Ѷ��1+"")?PLC.getIntance().STC1.get(i).secondST.getʣ������():
    		   PLC.getIntance().STC2.get(i).secondST.getʣ������());
    	   System.out.println(wuliao+"/"+wul1+"/"+wul2+"/"+sl1+"/"+sl2);
    	   if((wuliao.equals(wul1)&&sl1>0)||(wuliao.equals(wul2)&&sl2>0)){
    		   //���һ�λ�ϵ�
    		   System.out.println("==========");
    		int huowei= SqlPro.getMap().get(machineID+""+i+"ST");
    	    String sql2= "select  ���̱��   from ��λ��  where  ��λ��� ='"+huowei+"'";
    	    set=st.executeQuery(sql2);
    	    if(set.next()){
    	    	Object t=set.getObject(1);
    	    	if(t==null){//������湤λû��TP��ô�ͷ��������λ
    	    		//�ڼ��һ��ָ�����������û��ȥ�������λ��ָ��
    	    	    set=st.executeQuery("select idEvent,�Żػ�λ��,״̬  from ���⶯��ָ��  where ״̬<>'���' and �Żػ�λ��='"+huowei+"' order by idEvent");
    	    		if(!set.next()){
    	    	    String iss=add����ָ��( tp, fromID,huowei+"","�ϻ�"/*�ϻ����»��������߻���*/, 
    	    				  0/*1=�ش�⣬��1=����*/,  machineID);
    	    		if(iss.contains("�ɹ�")){
    	    			back=iss+"!_!"+huowei;
    	    			isToLine=true;
       	    		    break;
    	    		  }
    	    		
    	    		 
    	    	   }
    	    		 
    	                    }
    		  
    	        }
    	   }
    	   
       }
    	   
       }
       
       if(!isToLine){
    	  
    	    //60002,��ֻ��ȥ����   ,���60001ûȥLine,��ô����һ����ȥ���ܵ����
    	    //2.�����ж��������Ĭ��ȥ�ĸ�λ�ã���Ĭ��ȥ��λ���У�������һ���յ���ʲô
    	   String quyu="";
    	   String s1=" SELECT Ĭ���ϻ���  from ͨ������  where ���ϱ���='"+wuliao+"'";
    	   boolean isDefault=false;
    	   set=st.executeQuery(s1);
    	   if(set.next()){quyu=set.getString(1)==null?"":(String)set.getString(1);}
    	   if(!quyu.equals("")){
    	   String s2=" SELECT ��λ��� from ��λ��   where ��λ��� IN("+quyu+") or `���̱��` is  null and `���̱��`='' order by ����";
    	   set=st.executeQuery(s2);
    	   while(set.next()){
    		   String iss=add����ָ��( tp, fromID,set.getObject(1)+"","�ϻ�"/*�ϻ����»��������߻���*/, 
	    				  0/*1=�ش�⣬��1=����*/,  machineID);
    		      if(iss.contains("�ɹ�")){
    		    	  isDefault=true;
    		    	  back=iss+"!_!"+set.getObject(1);
    		    	  break;}
    		   }
    	    
    	   }
 
    	   //3. �жϻ��������ϻ�λ����ľ���
    	   if(!isDefault){
    		  
    		   String s3=" SELECT ��λ���  from ��λ��   where ���̱��  is null or `���̱��`='' order by ����";
    		   set=st.executeQuery(s3);
    		   while(set.next()){
    			   String iss=add����ָ��( tp, fromID,set.getObject(1)+"","�ϻ�"/*�ϻ����»��������߻���*/, 
 	    				  0/*1=�ش�⣬��1=����*/,  machineID);
    			  
     		      if(iss.contains("�ɹ�")){
     		    	  isDefault=true;
     		    	  back=iss+"!_!"+set.getObject(1);
     		    	  System.out.println("------------"+iss);
     		    	  break;} 
    			   
    		   }
    	   }
    	   
    	   
       }
        

       con.commit();
       con.setAutoCommit(true);
       if(set!=null)set.close();
       st.close();
       conn.realseCon();
  }catch(Exception ex){ ex.printStackTrace();
     try {
         con.rollback();
         con.setAutoCommit(true);
         if(set!=null)set.close();
         st.close();
         conn.realseCon();
          return ex.getMessage()+"!_!-1";
       } catch (SQLException ex1) {
           ex1.printStackTrace() ;
           conn.realseCon();
          return ex1.getMessage()+"!_!-1";
       }

    }
       conn.realseCon();


       return back;

}  
   
   public static void setStateForEventID(int idEvent, int state, String ext) {
		// TODO Auto-generated method stub
	
	  ConnactionPool p=ConnactionPool.getPool();
      Conn conn=p.getCon2("");
      ResultSet set=null;
     Statement st=null;
     Connection con=conn.getCon();
try{
       st=con.createStatement();
       con.setAutoCommit(false);
       String sql="select ����,״̬,״̬2,�Ƿ�ش��,��Դ��λ��,�Żػ�λ��,���̱��,������  from ���⶯��ָ��  where idEvent='"+idEvent+"' and ״̬<>'���'";
       set=st.executeQuery(sql);
       if(set.next()){
    	   String zong=set.getObject(1)+"";String zt1=set.getObject(2)+"";
    	   String zt2=set.getObject(3)+"";String isToback=set.getObject(4)+"";
           String fromID=set.getObject(5)+"";String toID=set.getObject(6)+"";
           String tp=set.getObject(7)+"";String qu=set.getObject(8)+"";
       if(state==SqlPro.���){
    	   if(zong.equals("�ϻ�")){
    	   //1.��һ�����¿�����̱�
    	  
    		   set=st.executeQuery("select ���̱�� from �������  where ���̱��="+"'"+tp+"'");   
    		   if(set.next()){
    			   st.executeUpdate("update �������  set ��λ��='"+toID+"',����='"+qu+"' where ���̱��='"+tp+"'");  
    		   }else{
    			   String sba[]=getWuliaoFromLK(tp).split("!_!");
    			   
    			   String  ql="insert into ������� (���̱��,����,����,"+
    	                   "����,��λ��) values("+
    	                     "'"+tp+"',"+
    	                     "'"+sba[0]+"',"+
    	                      "'"+sba[1]+"',"+
    	                      "'"+qu+"',"+
    	                      "'"+toID+"'"+
    	                    ")";
    			   st.executeUpdate(ql);
    		  
    		   }
    		   //2.�ڶ������»�λ��
    		   st.executeUpdate("update ��λ��     set ���̱��='"+tp+"',�Ѷ��='"+qu+"' where ��λ���="+"'"+toID+"'");
    		   //3.�����������䷽ָ����б�
    		   st.executeUpdate("update ���⶯��ָ��  set ״̬='���',���ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    		
    		  
    		   }
    	   
    	   if(zong.equals("�»�")){
        	   //1.��һ�����¿�����̱�
    		   if(toID.equals("60002")){//���Ӧ�ðѻ�λ
    		   st.executeUpdate("update �������  set ��λ��='"+toID+"' ,����='"+qu+"' where ���̱��='"+tp+"'");
    		   }else{
    		   st.executeUpdate("update �������  set ��λ��='"+toID+"' ,����='"+qu+"' where ���̱��='"+tp+"'");   
    		   }
    		   //2.�ڶ������»�λ��
    		   st.executeUpdate("update ��λ��     set ���̱��=NULL,�Ѷ��=NULL where ��λ���="+"'"+fromID+"'");
    		   if(!toID.equals("60002"))
    		   st.executeUpdate("update ��λ��     set ���̱��='"+tp+"',�Ѷ��='"+qu+"'  where ��λ���="+"'"+toID+"'");
    		  //3.�����������䷽ָ����б�
    		   st.executeUpdate("update ���⶯��ָ��  set ״̬='���',���ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    		
        		   }
    	   
    	   if(zong.equals("�����߻���")){
    		 //1.��һ�����¿�����̱�
         	  
    		  // st.executeUpdate("update �������  set ��λ��='"+toID+"' ,����='"+qu+"' where ���̱��='"+tp+"'");
    		   st.executeUpdate("update �������  set ��λ��=NULL  where ���̱��='"+tp+"'");
    		   //2.�ڶ������»�λ��
    		   st.executeUpdate("update ��λ��     set ���̱��=NULL,�Ѷ��=NULL where ��λ���="+"'"+fromID+"'");
    		   if(!toID.equals("60002"))
    		   st.executeUpdate("update ��λ��     set ���̱��='"+tp+"',�Ѷ��='"+qu+"'  where ��λ���="+"'"+toID+"'");
    		  //3.�����������䷽ָ����б�
    		   st.executeUpdate("update ���⶯��ָ��  set ״̬='���',���ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    		   
        		   }
      
          }
       }

      con.commit();
      con.setAutoCommit(true);
      if(set!=null)set.close();
      st.close();
      conn.realseCon();
 }catch(Exception ex){ ex.printStackTrace();
    try {
        con.rollback();
        con.setAutoCommit(true);
        if(set!=null)set.close();
        st.close();
        conn.realseCon();
        
      } catch (SQLException ex1) {
          ex1.printStackTrace() ;
          conn.realseCon();
         
      }

   }
      conn.realseCon();


     

	}
   
   //�Ӵ��������̱����ȡ���Ϻ������ķ���
   public static String fromDKisTP="wuliao!_!1";
   public static String getWuliaoFromLK(String tp){
	   
	   return fromDKisTP;
   }
   
   //֪ͨ����̨���������������ͻ�
 
   public static String informToDK(String tp,String wuliao,int shuliang){
	   
	   return "";
   }
   
   //��ȥ������̨���ź�ʱ�����������
   //��Ϣ=��λ�ţ�����λ��=-1��ִ�в��ɹ���=100000��������ȥ���ɹ�
   public static String exeRffid2(String tp){
	   ConnactionPool p=ConnactionPool.getPool();
       Conn conn=p.getCon2("");
       ResultSet set=null;
      Statement st=null;
      Connection con=conn.getCon();
      String back="δ����!_!-1";
 try{
        st=con.createStatement();
        con.setAutoCommit(false);
        String sql="select idEvent,����,״̬,״̬2,�Ƿ�ش��,��Դ��λ��,�Żػ�λ��,���̱��,"
        		+"������  from ���⶯��ָ��   where �Ƿ�ش��='1' and ״̬2<>'1' and ״̬='���'"
        		+" and ���̱��='"+tp+"' order by idEvent";
        set=st.executeQuery(sql);
        if(set.next()){
        	//�����������ǻش���
        	//1.����ɾ��������̱�����ļ�¼
        	Object eventID=set.getObject(1);
        	 set=st.executeQuery("select ����,����  from �������  where ���̱��="+"'"+tp+"'"); 
        	 String wuliao="";
        	 int shul=0;
        	 if(set.next()){
        		 wuliao=set.getString(1);
        		 shul=set.getInt(2);
        	 }
        	 st.executeUpdate("DELETE from `�������` where ���̱��='"+tp+"'");
        	 //2.��������ָ�����
        	 st.executeUpdate("update ���⶯��ָ��  set ״̬2='1' where idEvent="+"'"+eventID+"'");
            //3.֪ͨȥ������̨������ȥ����
        	 informToDK(tp,wuliao,shul);
        	//4.���ȥ�������������ͻ�ʧ������
        	
        	 back="�ɹ�����!_!100000";
        }
         else{
        	//�ϻ���
        	 set=st.executeQuery("select ����,����  from �������  where ���̱��="+"'"+tp+"'"); 	
        	 if(set.next()){
        		 Object wul=set.getObject(1);
        		 back =autoUpPallet( tp,wul+"","60002",set.getObject(2)+"");
        		 
        	 }
        	 
         }
      
       con.commit();
       con.setAutoCommit(true);
       st.close();
       conn.realseCon();
  }catch(Exception ex){ ex.printStackTrace();
     try {
         con.rollback();
         con.setAutoCommit(true);
         st.close();
         conn.realseCon();
          return ex.getMessage()+"!_!-1";
       } catch (SQLException ex1) {
           ex1.printStackTrace() ;
           conn.realseCon();
          return ex1.getMessage()+"!_!-1";
       }

    }
       conn.realseCon();


       return back;
  
	   
   }
    
}
