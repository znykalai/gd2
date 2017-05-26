package alai.znyk.server;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Properties;
import java.util.Vector;

import alai.znyk.common.ClientSer;
import alai.znyk.common.SqlPro;
import alai.znyk.plc.PLC;

public class SqlTool {
	
	static Properties pro;
	
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
    	if(set.getObject(i)!=null){
    		if(set.getObject(i) instanceof Long){
              v.addElement(Integer.parseInt(set.getObject(i).toString()));
          }else{
        	  v.addElement(set.getObject(i));	  
          }
       }
    	else{
    		v.addElement(null);	
    	}
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
         Connection con=fac.getCon();
         Statement  st=null;
         ResultSet set=null;
     try{


     
     st=con.createStatement();
      set=st.executeQuery(sql);

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
     set.close();
    st.close();
    fac.realseCon();
    return val;
     }
   catch(Exception e){
       try {
    	   if(set!=null)
    	   set.close();
           st.close();
           fac.realseCon();
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
    
   public synchronized static String  add����ָ��(String tp,String fromID,String toID,String type/*�ϻ����»��������߻���*/, 
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
              if(fomI==60001||fomI==60002||fomI==60003||fomI==60004){
        		  if(fomI==60001){
        			  if((toI>0&&toI<29)||toI>500&&toI<615){}
        			  else{
        				  have=true;
        				  back="�������"+fomI+"��λ�ѻ��ϵ�"+toI+"��λ1";    
        			  }
        			  
        		  }
        		  if(fomI==60002){
        			  if(toI>0&&toI<29||toI>500&&toI<615){}
        			  else{
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
        	if(!((fomI>0&&fomI<29)&&(/*toI==60002||*/(toI>500&&toI<615)))){
      		      have=true;
            	  back="�������"+fomI+"��λ�ѻ��µ�"+toI+"��λ3";
      	  }
        	
        	
          if(machineID.equals("1")){//A��
        	  if((toI>600&&toI<615)){
        		  have=true;
            	  back="�������"+fomI+"��λ�ѻ��µ�"+toI+"��λ,���ܰ�A�������̷ŵ�B��������";
        		  
        	  }
        	  if((toI>500&&toI<515)){
        		  if(toI%2==0){
        			  have=true;
                	  back="�������"+fomI+"��λ�ѻ��µ�"+toI+"��λ,ֻ�ܷ�������λ"; 
        			  
        		  } 
        		  
        	  }
        	  
          }else{////B��
        	  if((toI>500&&toI<515)){
        		  have=true;
            	  back="�������"+fomI+"��λ�ѻ��µ�"+toI+"��λ,���ܰ�B�������̷ŵ�A��������";
        		  
        	  }
        	  
        	  if((toI>600&&toI<615)){
        		  if(toI%2==0){ 
        			  have=true;
                	  back="�������"+fomI+"��λ�ѻ��µ�"+toI+"��λ,ֻ�ܷ�������λ"; 
        			  
        		  } 
        		  
        	  }
        	  
          }
      }
      
        if(type.equals("�����߻���")){
        	  if(!(fomI>500&&fomI<615)){
        		 
        		  have=true;
              	  back="���Ͳ������"+fomI+"������"+toI+"��λ";
        	  }
        	  
        	  if(toI>500&&toI<615){//toIֻ����60001��60002����ܺ�
        		  have=true;
              	  back="���Ͳ������"+fomI+"������"+toI+"��λ";  
        		  
        	  }
        	
        	  
        	//��ʱ������ӣ�ֻҪ��Ԥװ������û��ɣ��Ͳ��ܵ���������
        	  String sqll="select idEvent,״̬,״̬2 from ���⶯��ָ��  where ����='Ԥ�ϻ�' and ״̬2<>'1' and ������='"+machineID+"' order by idEvent";
              set=st.executeQuery(sqll);
              
              if(set.next()){
               	have=true;
               	back="AGVС���Ѿ����˹��ϻ�ָ��ռ�ã����˹��ϻ���ɺ��ڷ��ʹ�����";
                }
        	 
        }
        
        if(type.equals("Ԥ�ϻ�")){
        	
        	if(machineID.equals("1")){
       		 if((toI>600&&toI<615)){
          		  have=true;
              	  back="�������"+fomI+"��λ�ѻ��µ�"+toI+"��λ,���ܰ�A�������̷ŵ�B��������";
          		  
          	  }
       		
       	}else{
       	   if((toI>500&&toI<515)){
     		     have=true;
         	     back="�������"+fomI+"��λ�ѻ��µ�"+toI+"��λ,���ܰ�A�������̷ŵ�B��������";
     		  
     	     }
       		
       	}  
        	
        	 if((toI>500&&toI<515)){
       		  if(toI%2==0){
       			  have=true;
               	  back="�������"+fomI+"��λ�ѻ��µ�"+toI+"��λ,ֻ�ܷ�������λ"; 
       			  
       		  } 
       		  
       	  }
        	 
         if((toI>600&&toI<615)){
       		  if(toI%2==0){ 
       			  have=true;
               	  back="�������"+fomI+"��λ�ѻ��µ�"+toI+"��λ,ֻ�ܷ�������λ"; 
       			  
       		  } 
       		  
       	  }
        	
        if(!have)	{
        	// String sqll="select idEvent,״̬,״̬2 from ���⶯��ָ��  where ����='Ԥ�ϻ�' and ״̬2<>'1' and ���̱��='"+tp+"' order by idEvent";
        	/*����ʱ�������棬����Y���ϻ���ûRFID������һ��ֻ����һ���������ֹ���̴���*/
        	String sqll="select idEvent,״̬,״̬2 from ���⶯��ָ��  where ����='Ԥ�ϻ�' and ״̬2<>'1' and ������='"+machineID+"'order by idEvent";
        	set=st.executeQuery(sqll);
             if(set.next()){
             	have=true;
             	back="���������������Ԥ�ϻ�ָ��";
              }
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
  			 int toID2=toI;
  			 if(toI==60002){
  				 if(fomI>500&&fomI<515){toID2=60001;}
  				 if(fomI>600&&fomI<615){toID2=60002;}
  			 }
    	   String  ql="insert into ���⶯��ָ�� (��Դ,�������,����,"+
                   "���̱��,״̬,��Դ��λ��,�Żػ�λ��,״̬2,������,�Ƿ�ش��,�½�ʱ��) values("+
                   "'����',"+
                   "'PACKװ��',"+
                   "'�����߻���',"+
                   "'"+tp+"',"+
                    "'�Ŷ�',"+
                     "'"+fromID+"',"+
                     "'"+toID2+"',"+
                      "'"+(0)+"',"+
                      "'"+machineID+"',"+
                      "'"+todaku+"',"+
                      SqlPro.getDate()[1]+
                   ")";
			   st.executeUpdate(ql);
			   back="ָ��ɹ�����-"+toID;
  		       }
			   
         }else{
        	 
        	 if(type.equals("Ԥ�ϻ�")){
            	if(!have){
            		  String  ql="insert into ���⶯��ָ�� (��Դ,�������,����,"+
                              "���̱��,״̬,��Դ��λ��,�Żػ�λ��,״̬2,������,�Ƿ�ش��,�½�ʱ��) values("+
                              "'����',"+
                              "'PACKװ��',"+
                              "'Ԥ�ϻ�',"+
                              "'"+tp+"',"+
                               "'���',"+
                                "'"+fromID+"',"+
                                "'"+toID+"',"+
                                 "'"+0+"',"+
                                 "'"+machineID+"',"+
                                 "'"+0+"',"+
                                 SqlPro.getDate()[1]+
                              ")";
        			    st.executeUpdate(ql);
        			    back="ָ��ɹ�����-"+toID;
            		
            	}
          }
        	
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
        			    back="ָ��ɹ�����-"+toID;
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
        		 
        		 if(toID.equals("60002")||toID.equals("60001")){
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
        		        back="ָ��ɹ�����-"+toID;
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
   
  //�����ֶ������̣��ɹ��󷵻ذ������ɹ��������� 
   public static String manUpPallet(String tp,String wuliao,int num,String toID,String machineID){
	   String back="";
	   ConnactionPool p=ConnactionPool.getPool();
       Conn conn=p.getCon2("");
       ResultSet set=null;
      Statement st=null;
      Connection con=conn.getCon();
 try{
        st=con.createStatement();
        con.setAutoCommit(false);
        //��һ���Ȱ����̵�����������
        set=st.executeQuery("select ���̱�� from �������  where ���̱��="+"'"+tp+"'");   
		   if(set.next()){
			   st.executeUpdate("update �������  set ����='"+num+"',����='"+machineID+"',����='"+wuliao+"' where ���̱��='"+tp+"'");  
		   }else{
			   String  ql="insert into ������� (���̱��,����,����,"+
	                   "����) values("+
	                     "'"+tp+"',"+
	                     "'"+wuliao+"',"+
	                      "'"+num+"',"+
	                      "'"+machineID+"'"+
	                      
	                    ")";
			   st.executeUpdate(ql);	   
			   
		   }
		   
		   if(machineID.equals("1")){
       
		   back = add����ָ��( tp,"60001", toID,"Ԥ�ϻ�"/*�ϻ����»��������߻���,Ԥ�ϻ�*/, 
					  0,  machineID);
		   }
		   if(machineID.equals("2")){
		       
		  back = add����ָ��( tp,"60002", toID,"Ԥ�ϻ�"/*�ϻ����»��������߻���,Ԥ�ϻ�*/, 
						  0,  machineID);
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
	   if(id>60004&&id<60001)
	   {//60002,60003
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
       if(fromID.equals("60001")||fromID.equals("60002")/*||fromID.equals("60003")||fromID.equals("60004")*/){//�����ϻ���
    	 //�ж��������Ӧ��ȥ�ĸ���λ������ȥ7����λ,���ȼ��7����λ��û��ָ�Ȼ���ڼ�⹤λ����û������
    	  // String sql="SELECT DISTINCT ��λ,����-IFNULL(�������,0) from �䷽ָ�����   where ����='"
    	  // +wuliao+"' AND װ����='"+machineID+"' and ����-IFNULL(�������,0)>0 ORDER BY �������,ģ�����,�ֽ��,�ؾ����  LIMIT 2 ";
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
    	   String s2=" SELECT ��λ��� from ��λ��   where ��λ��� IN("+quyu+") and (`���̱��`='' or `���̱��` is  null)  order by ����";
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
   

   static String  lock="";
   public static String setStateForEventID(int idEvent, int state, String ext) {
		// TODO Auto-generated method stub
	   System.out.println("����"+idEvent+"/"+state);
	  synchronized(lock){
		   System.out.println("�M��LOCL"+idEvent+"/"+state);
	  String back="";
	  ConnactionPool p=ConnactionPool.getPool();
      Conn conn=p.getCon2("");
      ResultSet set=null;
     Statement st=null;
     Connection con=conn.getCon();
try{  boolean isEvent=false;
       st=con.createStatement();
       con.setAutoCommit(false);
       String sql="select ����,״̬,״̬2,�Ƿ�ش��,��Դ��λ��,�Żػ�λ��,���̱��,������  from ���⶯��ָ��  where idEvent='"+idEvent+"' and ״̬<>'���'";
       set=st.executeQuery(sql);
       if(set.next()){
    	  
    	   String zong=set.getObject(1)+"";String zt1=set.getObject(2)+"";
    	   String zt2=set.getObject(3)+"";String isToback=set.getObject(4)+"";
           String fromID=set.getObject(5)+"";String toID=set.getObject(6)+"";
           String tp=set.getObject(7)+"";
           String qu=set.getObject(8)+"";
       if(state==SqlPro.���){
    	   isEvent=true;
    	   if(zong.equals("�ϻ�")){
    	   //1.��һ�����¿�����̱�
    	  
    		   set=st.executeQuery("select ���̱��,���� from �������  where ���̱��="+"'"+tp+"'");   
    		   if(set.next()){
    			    String wuliao=set.getString(2);
    			   st.executeUpdate("update �������  set ��λ��='"+toID+"',����='"+qu+"' where ���̱��='"+tp+"'");  
    			   if(fromID.equals("60001")||fromID.equals("60002")){
    				 //���������ڵ��䷽λ�� 
    				   set=st.executeQuery("select ����  from ͨ������  where ���ϱ���="+"'"+wuliao+"'"); 
    				   String lei="";
    				   if(set.next()){
    					   lei=set.getString(1);
    					   SqlTool.initAddresInPalet(lei, tp,st); 
    				   }
    				  
	    			  
    				}
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
    			   
    			   if(fromID.equals("60001")||fromID.equals("60002")){
      				/*****���������ڵ��䷽λ�ã�������  
      				   */
    				   set=st.executeQuery("select ����  from ͨ������  where ���ϱ���="+"'"+sba[0]+"'"); 
    				   if(set.next()){
    					   SqlTool.initAddresInPalet(set.getString(1), tp,st);
    				   }
      			   }
    		  
    		   }
    		   //2.�ڶ������»�λ��
    		   st.executeUpdate("update ��λ��     set ���̱��='"+tp+"',�Ѷ��='"+qu+"' where ��λ���="+"'"+toID+"'");
    		   //3.�����������䷽ָ����б�
    		   st.executeUpdate("update ���⶯��ָ��  set ״̬='���',���ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    		
    		  
    		   }
    	   
    	   if(zong.equals("�»�")){
    		  
        	   //1.��һ�����¿�����̱�
    		   if(toID.equals("60002")||toID.equals("60001")){
    			   //������µ��˹�̨�ģ�ֱ�Ӱ����̵Ļ�λ���
    		     st.executeUpdate("update �������  set ��λ��=NULL ,����='"+qu+"' where ���̱��='"+tp+"'");
    		   }else{
    		   st.executeUpdate("update �������  set ��λ��='"+toID+"' ,����='"+qu+"' where ���̱��='"+tp+"'");   
    		   }
    		   //2.�ڶ������»�λ��
    		   st.executeUpdate("update ��λ��     set ���̱��=NULL,�Ѷ��=NULL where ��λ���="+"'"+fromID+"'");
    		   if(!toID.equals("60002")&&!toID.equals("60001"))
    		   st.executeUpdate("update ��λ��     set ���̱��='"+tp+"',�Ѷ��='"+qu+"'  where ��λ���="+"'"+toID+"'");
    		  //3.�����������䷽ָ����б�
    		   st.executeUpdate("update ���⶯��ָ��  set ״̬='���',���ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    		
        		   }
    	   
    	   if(zong.equals("�����߻���")){
    		   //����ʱ�汾���棬�����߻���Ҫ�����ַ�ʽ������һ�ֻػ��ܣ�һ�ֻ��˹�̨
    		  
    		 //1.��һ�����¿�����̱�
         	  
    		   // st.executeUpdate("update �������  set ��λ��='"+toID+"' ,����='"+qu+"' where ���̱��='"+tp+"'");
    		    st.executeUpdate("update �������  set ��λ��=NULL  where ���̱��='"+tp+"'");
    		   //2.�ڶ������»�λ��
    		   st.executeUpdate("update ��λ��     set ���̱��=NULL,�Ѷ��=NULL where ��λ���="+"'"+fromID+"'");
    		   if(toID.equals("60002")||toID.equals("60001")){
    			   //����ǻ��˹�̨��
    			   
    		     //  st.executeUpdate("update ��λ��     set ���̱��='"+tp+"',�Ѷ��='"+qu+"'  where ��λ���="+"'"+toID+"'");
    			     st.executeUpdate("update ���⶯��ָ��  set ״̬2='1' where idEvent="+"'"+idEvent+"'");
    			     st.executeUpdate("DELETE from `�������` where ���̱��='"+tp+"'");
    			     
    	        	
    		   }
    		  //3.�����������䷽ָ����б�
    		   st.executeUpdate("update ���⶯��ָ��  set ״̬='���',���ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    		   
        		   }
      
          }
       
       if(state==SqlPro.ִ����){
    	   isEvent=true;
    	   //��û��
    	   st.executeUpdate("update ���⶯��ָ��  set ״̬='ִ����',����ʱ��="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    	   
    	   
          }
       }
       if(isEvent){
         back="�ɹ�";}else{back="û���eventID";}
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

     return back;
     
	   }
	   
	}
   
   //�Ӵ��������̱����ȡ���Ϻ������ķ���
   public static String fromDKisTP="wuliao!_!1";
   public static String getWuliaoFromLK(String tp){
	   
	   return findOneRecord("select ����,����,max(ID)  FROM �������   where ���̱���='"+tp+"'");
   }
   
   //֪ͨ����̨���������������ͻ�
 
   public static String informToDK(String tp,String wuliao,int shuliang){
	   
	   return "�ɹ�";
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
        		+"������  from ���⶯��ָ��   where ����='Ԥ�ϻ�' and ״̬2<>'1' and ״̬='���'"
        		+" and ���̱��='"+tp+"' order by idEvent";
        set=st.executeQuery(sql);
        if(set.next()){
        	 boolean isHuowei=false;
        	 System.out.println("RFID2-------------3="+tp);
        	 Object eventID=set.getObject(1);
        	 Object toID=set.getObject(7)==null?"":set.getObject(7);
        	 String machineID=set.getObject(9)==null?"":set.getObject(9).toString();
        	 
        	 if(!toID.equals("")){
        		if(machineID.equals("1")) {
        	 String iss=add����ָ��( tp, "60001",toID+"","�ϻ�"/*�ϻ����»��������߻���*/, 
    				  0/*1=�ش�⣬��1=����*/,  machineID);
        	 
        	 if(iss.contains("�ɹ�")){
        		 System.out.println("RFID2-------------4="+tp);
        		 //����״̬��
        		 st.executeUpdate("update ���⶯��ָ��  set ״̬2='1' where idEvent="+"'"+eventID+"'");
        		
        		 isHuowei=true;
        		 back=iss+"!_!"+toID;
        		 
        	   }}
        		
        		if(machineID.equals("2")) {
               	 String iss=add����ָ��( tp, "60002",toID+"","�ϻ�"/*�ϻ����»��������߻���*/, 
   	    				  0/*1=�ش�⣬��1=����*/,  machineID);
               	 
               	 if(iss.contains("�ɹ�")){
               		 System.out.println("RFID2-------------4="+tp);
               		 //����״̬��
               		 st.executeUpdate("update ���⶯��ָ��  set ״̬2='1' where idEvent="+"'"+eventID+"'");
               		
               		 isHuowei=true; 
               		 back=iss+"!_!"+toID;
               		 
               	   }}
               	 
        	 
        	 }
        	 
        	 if(!isHuowei){
            	 set=st.executeQuery("select ����,����  from �������  where ���̱��="+"'"+tp+"'"); 	
            	 
            	 if(set.next()){
            		 Object wul=set.getObject(1);
            		 Object machineID2=set.getObject(2);
            		 if(machineID.equals("1")){
            		 back =autoUpPallet( tp,wul+"","60001",machineID2+"");
            		 }
            		 else{
            	     back =autoUpPallet( tp,wul+"","60002",machineID2+""); 
            			 
            		 }
            		 if(back.contains("�ɹ�"))
            		 st.executeUpdate("update ���⶯��ָ��  set ״̬2='1' where ���̱��="+"'"+tp+"'");
            	      }
            	 
                 } 
        	
        	 back="�ɹ�����!_!100000";
        }
         else{
        	//��������������Դ��7�������ߵ��ϻ���
        	 boolean isHuowei=false;
        	 //�����ж����������û��ָ����λ
        	 String sql3="select max(idEvent),����,״̬,״̬2,�Ƿ�ش��,��Դ��λ��,�Żػ�λ��,���̱��,"
             		+"������  from ���⶯��ָ��   where   ����='�����߻���' and ״̬='���' and ״̬2<>'1'"
             		+" and ���̱��='"+tp+"'";
        	 System.out.println("RFID2-------------2="+tp);
             set=st.executeQuery(sql3);
             if(set.next()){
            	 System.out.println("RFID2-------------3="+tp);
            	 Object eventID=set.getObject(1);
            	 Object toID=set.getObject(7)==null?"":set.getObject(7);
            	 String machineID=set.getObject(9)==null?"":set.getObject(9).toString();
            	 if(!toID.equals("60001")&&!toID.equals("60002")&&!toID.equals("")){
            		if(machineID.equals("1")) {
            	 String iss=add����ָ��( tp, "60003",toID+"","�ϻ�"/*�ϻ����»��������߻���*/, 
	    				  0/*1=�ش�⣬��1=����*/,  machineID);
            	 
            	 if(iss.contains("�ɹ�")){
            		 System.out.println("RFID2-------------4="+tp);
            		 //����״̬��
            		 st.executeUpdate("update ���⶯��ָ��  set ״̬2='1' where idEvent="+"'"+eventID+"'");
            		
            		 isHuowei=true;
            		 back=iss+"!_!"+toID;
            		 
            	   }}
            		
            		if(machineID.equals("2")) {
                   	 String iss=add����ָ��( tp, "60004",toID+"","�ϻ�"/*�ϻ����»��������߻���*/, 
       	    				  0/*1=�ش�⣬��1=����*/,  machineID);
                   	 
                   	 if(iss.contains("�ɹ�")){
                   		 System.out.println("RFID2-------------4="+tp);
                   		 //����״̬��
                   		 st.executeUpdate("update ���⶯��ָ��  set ״̬2='1' where idEvent="+"'"+eventID+"'");
                   		
                   		 isHuowei=true;
                   		 back=iss+"!_!"+toID;
                   		 
                   	   }}
                   	 
            	 
            	 
            	 }
            	 
             }
             
             if(!isHuowei){
        	 set=st.executeQuery("select ����,����  from �������  where ���̱��="+"'"+tp+"'"); 	
        	 
        	 if(set.next()){
        		 Object wul=set.getObject(1);
        		 Object machineID=set.getObject(2);
        		 if(machineID.equals("1")){
        		 back =autoUpPallet( tp,wul+"","60003",machineID+"");
        		 }
        		 else{
        	     back =autoUpPallet( tp,wul+"","60004",machineID+""); 
        			 
        		 }
        		 if(back.contains("�ɹ�"))
        		 st.executeUpdate("update ���⶯��ָ��  set ״̬2='1' where ���̱��="+"'"+tp+"'");
        	      }
        	 
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
   
   
   public static byte[] write(Object ob){
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
	    ObjectOutputStream out = null; 
	    try { 
	      out = new ObjectOutputStream(baos); 
	      out.writeObject(ob);    
	    } catch (IOException e) { 
	    	e.printStackTrace();
	    
	    }finally{ 
	      try { 
	          out.close(); 
	      } catch (IOException e) { 
	    	  e.printStackTrace();
	      } 
	    } 
	      
	    return baos.toByteArray(); 

	}
	
	public static Object readFromByte(byte[] b) throws IOException, ClassNotFoundException{
		
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
	
	 public static String writePLC(Object plc)  {
	       ConnactionPool p=ConnactionPool.getPool();
           Conn conn=p.getCon2("");

           PreparedStatement st=null;
          Connection con=conn.getCon();
   try{   
	      String sql = "UPDATE plc SET PLC=?,����ʱ��="+SqlPro.getDate()[1]+" where ID='1'";
          st=con.prepareStatement(sql);
          con.setAutoCommit(false);
          st.setBytes(1, write(plc));
         // st.setDate(2,new Date(System.currentTimeMillis()));
        //   st.setTime(2, new Time(System.currentTimeMillis()));
          st.executeUpdate();

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
	 
	 public static Object readPLC()  {
	       ConnactionPool p=ConnactionPool.getPool();
         Conn conn=p.getCon2("");
         ResultSet set=null;
         Statement st=null;
        
        Connection con=conn.getCon();
 try{   
	      String sql = "select plc from PLC where ID=1";
          st=con.createStatement();
          set=st.executeQuery(sql);
          if(set.next()){
        	  Blob blob = set.getBlob("plc");
        	  byte[] datas = blob.getBytes(1, (int)blob.length());
        	  System.out.println("S:"+blob.length());
        	  return readFromByte(datas);
          }
       
      
       st.close();
       conn.realseCon();
  }catch(Exception ex){ ex.printStackTrace();
     try {
         
         st.close();
         conn.realseCon();
          return null;
       } catch (SQLException ex1) {
           ex1.printStackTrace() ;
           conn.realseCon();
          return null;
       }

    }
       conn.realseCon();


       return null;

} 

public static void clearValueForPalet( String ��λ��,int machineID){
	System.out.println("clear pallet address-------------");
	if(pro==null){pro=SqlPro.loadProperties(SqlPro.class.getResource("conf.pro").getFile());}
	if(pro!=null){
		 int to[]=new int[41];
		 ClientSer.getIntance().writeSirIntToCTR(pro.getProperty(��λ��), 41, to,  machineID)  ;	
		
	  }
	
	
 }	

//д����������Ļ���λ��,�տ�ʼ������ʱ��˵�л���ȫ�����ġ�	 
public static	void initAddresInPalet(String lei/*1=10��X2��X2��*/,String palet, Statement st){
	if(lei==null||lei.equals(""))return;
	//�����ݿ������������ݸ�ʽ����
	//��ַ1=1,��ַ2=0��������������1�����л���0����û��
		if(lei.equals("1")){//���ȷ����д��ĳ��ȣ�10��X2��X2��
			//1-40
			//��λ����ʼ��ַ����ע��û�г�ʼ���������λ����ʼ��ַ��D200����ôD200�ͱ�עΪ��û�г�ʼ����
			String val="1=1,2=1,3=1,4=1,5=1,6=1,7=1,8=1,"+
					    "9=1,10=1,11=1,12=1,13=1,14=1,15=1,16=1,17=1,"+
					    "18=1,19=1,20=1,21=1,22=1,23=1,24=1,25=1,26=1,"+
					    "27=1,28=1,29=1,30=1,31=1,32=1,33=1,34=1,35=1,"+
					    "36=1,37=1,38=1,39=1,40=1";
			String sql= "update �������  set address='"+val+"' where ���̱��="+"'"+palet+"'";
			System.out.println(sql);
			try{
				if(st==null){
				SqlTool.insert(new String[]{sql});}
				else{
					st.executeUpdate(sql);
					
				}
				
			}catch(Exception ex){}
			
			
		}
       if(lei.equals("2")){//���ȷ����д��ĳ��ȣ�7��X2��X2��
			//1-28
    	    //��λ����ʼ��ַ����ע��û�г�ʼ��
    	   String val="1=1,2=1,3=1,4=1,5=1,6=1,7=1,8=1,"+
				    "9=1,10=1,11=1,12=1,13=1,14=1,15=1,16=1,17=1,"+
				    "18=1,19=1,20=1,21=1,22=1,23=1,24=1,25=1,26=1,"+
				    "27=1,28=1";
    	   String sql= "update �������  set address='"+val+"' where ���̱��="+"'"+palet+"'";
    		try{
				if(st==null){
				SqlTool.insert(new String[]{sql});}
				else{
					st.executeUpdate(sql);
					
				}
				
			}catch(Exception ex){}
			
		}
       
       if(lei.equals("3")){//���ȷ����д��ĳ��ȣ�7��X1��X1��,�װ�
			//1-7
    	   String val="1=1,2=0,3=1,4=0,5=1,6=0,7=1,8=0,"+
				    "9=1,10=0,11=1,12=0,13=1,14=0,15=1,16=0,17=1,"+
				    "18=0,19=1,20=0,21=1,22=0,23=1,24=0,25=1,26=0,"+
				    "27=1,28=0";
    	   String sql= "update �������  set address='"+val+"' where ���̱��="+"'"+palet+"'";
    		try{
				if(st==null){
				SqlTool.insert(new String[]{sql});}
				else{
					st.executeUpdate(sql);
					
				}
				
			}catch(Exception ex){}
			
			
		}
       if(lei.equals("4")){//���ȷ����д��ĳ��ȣ�4��X2��X1��
			//1-8
    	   String val="1=1,2=1,3=1,4=1,5=1,6=1,7=1,8=1";
    	   String sql= "update �������  set address='"+val+"' where ���̱��="+"'"+palet+"'";
    		try{
				if(st==null){
				SqlTool.insert(new String[]{sql});}
				else{ 
					st.executeUpdate(sql);
					
				}
				
			}catch(Exception ex){}
			
		}
} 

//��PLC����д�������ϵĻ���λ���ͺ� ,ֻ�е����̵��������ߵ��ն�ʱ��д��,��startLine�����
public static	void WriteAddresInPaletToPLC(String lei/*1=10��X2��X2��*/,String palet,String huowei,int machineID){
	System.out.println("write pallet address-------------");
	if(lei==null||lei.equals(""))return;
	
	if(pro==null){pro=SqlPro.loadProperties(SqlPro.class.getResource("conf.pro").getFile());}
	if(pro!=null){
		
	
			//��λ����ʼ��ַ����ע��û�г�ʼ���������λ����ʼ��ַ��D200����ôD200�ͱ�עΪ��û�г�ʼ����
	   String val=SqlTool.findOneRecord("select address from �������  where  ���̱��='"+palet+"'");
	   if(val!=null){
		   String sm[]=val.split(",");
		   int length=sm.length+1;
		   int to[]=new int[length];
		   to[0]=1;
		   for(int i=1;i<to.length;i++){
			     to[i]=Integer.parseInt(sm[i-1].split("=")[1]);
			   
		     }
		   
		   ClientSer.getIntance().writeSirIntToCTR(pro.getProperty(huowei), length, to,  machineID)  ;
		   
	   }
			
		}
    
	
} 

//��PLC�����ȡ���ݺ�Ȼ�󱣴浽���ݿ����棬���ܵ����ݸ������ʱ�����ô˷���
public static	void readAddresInPaletFromPLC(String lei/*1=10��X2��X2��*/,String palet,String huowei,int machineID){
	System.out.println("read pallet address from plc -------------huowei="+huowei+"/tp="+palet+"machineID="+machineID);
	if(lei==null||lei.equals(""))return;
	
	if(pro==null){pro=SqlPro.loadProperties(SqlPro.class.getResource("conf.pro").getFile());}
	if(pro!=null){
		
		if(lei.equals("1")){//���ȷ����д��ĳ��ȣ�10��X2��X2��
			//1-40
			//��λ����ʼ��ַ����ע��û�г�ʼ���������λ����ʼ��ַ��D200����ôD200�ͱ�עΪ��û�г�ʼ����
			String val="";
			alai.GDT.Resint[]back=ClientSer.getIntance().getSirIntValuesFromCTR(pro.getProperty(huowei), 41, 16, machineID);
			for(int i=1;i<41;i++){
				 val=val+","+i+"="+back[i].resInt;
				
			}
			val=val.substring(1, val.length());
			String sql= "update �������  set address='"+val+"' where ���̱��="+"'"+palet+"'";
			SqlTool.insert(new String[]{sql});
			
		}
       if(lei.equals("2")){//���ȷ����д��ĳ��ȣ�7��X2��X2��
			//1-28
    	    //��λ����ʼ��ַ����ע��û�г�ʼ��
    	   String val="";
			alai.GDT.Resint[]back=ClientSer.getIntance().getSirIntValuesFromCTR(pro.getProperty(huowei), 29, 16, machineID);
			for(int i=1;i<29;i++){
				 val=val+","+i+"="+back[i].resInt;
				
			}
			val=val.substring(1, val.length());
    	   String sql= "update �������  set address='"+val+"' where ���̱��="+"'"+palet+"'";
			SqlTool.insert(new String[]{sql});
			
		}
       
       if(lei.equals("3")){//���ȷ����д��ĳ��ȣ�7��X1��X1��
			//1-7
    	   String val="";
			alai.GDT.Resint[]back=ClientSer.getIntance().getSirIntValuesFromCTR(pro.getProperty(huowei), 8, 16, machineID);
			for(int i=1;i<8;i++){
				 val=val+","+i+"="+back[i].resInt;
				
			}
			val=val.substring(1, val.length());
    	   String sql= "update �������  set address='"+val+"' where ���̱��="+"'"+palet+"'";
			SqlTool.insert(new String[]{sql});
			
			
		}
       if(lei.equals("4")){//���ȷ����д��ĳ��ȣ�4��X2��X1��
			//1-8
    	   String val="";
			alai.GDT.Resint[]back=ClientSer.getIntance().getSirIntValuesFromCTR(pro.getProperty(huowei), 9, 16, machineID);
			for(int i=1;i<9;i++){
				 val=val+","+i+"="+back[i].resInt;
				
			}
			val=val.substring(1, val.length());
    	   String sql= "update �������  set address='"+val+"' where ���̱��="+"'"+palet+"'";
			SqlTool.insert(new String[]{sql});
			
		}
			
		}
  
	
} 


	 
public static void main(String ss[]){
	String s="11111112";
	System.out.println( writePLC(s));
	System.out.println( readPLC());
	
}	 
    
}
