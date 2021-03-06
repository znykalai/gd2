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
	public static String zl1="";
	
	
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
        SqlPro.getLog().error("sql", e);
       try {
    	   conn.realseCon();
    	   if(set!=null)
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


                  return "记录更新成功！";

    }  
    
    public static String update7Line(String tp,String fromID,String toID)  {
	       ConnactionPool p=ConnactionPool.getPool();
           Conn conn=p.getCon2("");

          Statement st=null;
          Connection con=conn.getCon();
     try{
            st=con.createStatement();
            con.setAutoCommit(false);
            String sql1="update 货位表     set 托盘编号=NULL where 货位序号="+"'"+fromID+"'";
            String sql2="update 货位表     set 托盘编号='"+tp+"' where 货位序号="+"'"+toID+"'";
            String sql3="update 库存托盘  set 货位号="+toID+" where 托盘编号="+"'"+tp+"'";
           
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


           return "记录更新成功！";

}  
    
   public synchronized static String  add动作指令(String tp,String fromID,String toID,String type/*上货，下货，输送线回流*/, 
		  int todaku/*1=回大库，非1=不回*/, String machineID){
	   ConnactionPool p=ConnactionPool.getPool();
       Conn conn=p.getCon2("");

      Statement st=null;
      ResultSet set=null;
      Connection con=conn.getCon();
      boolean have=false;
      String back="未处理";
 try{
	   if(toID==null)toID="";
        st=con.createStatement();
        con.setAutoCommit(false);
        int fomI=Integer.parseInt(fromID);
        int toI=Integer.parseInt(toID.equals("")?"0":toID);
        if(type.equals("上货")){
              if(fomI==60001||fomI==60002||fomI==60003||fomI==60004){
        		  if(fomI==60001){
        			  if((toI>0&&toI<29)||toI>500&&toI<615){}
        			  else{
        				  have=true;
        				  back="不允许从"+fomI+"货位把货上到"+toI+"货位1";    
        			  }
        			  
        		  }
        		  if(fomI==60002){
        			  if(toI>0&&toI<29||toI>500&&toI<615){}
        			  else{
        				  have=true;
        				  back="不允许从"+fomI+"货位把货上到"+toI+"货位2";    
        			  }
        			  
        		  }
        		  
        	  }else{
        		  have=true;
        		  back="不允许从"+fomI+"货位把货上到"+toI+"货位2";  
        		  
        	  }
        	 
        }
        
        if(type.equals("下货")){
        	if(!((fomI>0&&fomI<29)&&(/*toI==60002||*/(toI>500&&toI<615)))){
      		      have=true;
            	  back="不允许从"+fomI+"货位把货下到"+toI+"货位3";
      	  }
        	
        	
          if(machineID.equals("1")){//A区
        	  if((toI>600&&toI<615)){
        		  have=true;
            	  back="不允许从"+fomI+"货位把货下到"+toI+"货位,不能把A区的托盘放到B区输送线";
        		  
        	  }
        	  if((toI>500&&toI<515)){
        		  if(toI%2==0){
        			  have=true;
                	  back="不允许从"+fomI+"货位把货下到"+toI+"货位,只能发到奇数位"; 
        			  
        		  } 
        		  
        	  }
        	  
          }else{////B区
        	  if((toI>500&&toI<515)){
        		  have=true;
            	  back="不允许从"+fomI+"货位把货下到"+toI+"货位,不能把B区的托盘放到A区输送线";
        		  
        	  }
        	  
        	  if((toI>600&&toI<615)){
        		  if(toI%2==0){ 
        			  have=true;
                	  back="不允许从"+fomI+"货位把货下到"+toI+"货位,只能发到奇数位"; 
        			  
        		  } 
        		  
        	  }
        	  
          }
      }
      
        if(type.equals("输送线回流")){
        	  if(!(fomI>500&&fomI<615)){
        		 
        		  have=true;
              	  back="输送不允许从"+fomI+"回流到"+toI+"货位";
        	  }
        	  
        	  if(toI>500&&toI<615){//toI只能是60001或60002或货架号
        		  have=true;
              	  back="输送不允许从"+fomI+"回流到"+toI+"货位";  
        		  
        	  }
        	
        	  
        	//零时方案添加，只要有预装的命令没完成，就不能调动输送线
        	  String sqll="select idEvent,状态,状态2 from 立库动作指令  where 动作='预上货' and 状态2<>'1' and 请求区='"+machineID+"' order by idEvent";
              set=st.executeQuery(sqll);
              
              if(set.next()){
               	have=true;
               	back="AGV小车已经被人工上货指令占用，等人工上货完成后在发送此命令";
                }
        	 
        }
        
        if(type.equals("预上货")){
        	
        	if(machineID.equals("1")){
       		 if((toI>600&&toI<615)){
          		  have=true;
              	  back="不允许从"+fomI+"货位把货下到"+toI+"货位,不能把A区的托盘放到B区输送线";
          		  
          	  }
       		
       	}else{
       	   if((toI>500&&toI<515)){
     		     have=true;
         	     back="不允许从"+fomI+"货位把货下到"+toI+"货位,不能把A区的托盘放到B区输送线";
     		  
     	     }
       		
       	}  
        	
        	 if((toI>500&&toI<515)){
       		  if(toI%2==0){
       			  have=true;
               	  back="不允许从"+fomI+"货位把货下到"+toI+"货位,只能发到奇数位"; 
       			  
       		  } 
       		  
       	  }
        	 
         if((toI>600&&toI<615)){
       		  if(toI%2==0){ 
       			  have=true;
               	  back="不允许从"+fomI+"货位把货下到"+toI+"货位,只能发到奇数位"; 
       			  
       		  } 
       		  
       	  }
        	
        if(!have)	{
        	// String sqll="select idEvent,状态,状态2 from 立库动作指令  where 动作='预上货' and 状态2<>'1' and 托盘编号='"+tp+"' order by idEvent";
        	/*在临时方案里面，由于Y向上货点没RFID，所以一次只能上一个命令，来防止托盘串号*/
        	String sqll="select idEvent,状态,状态2 from 立库动作指令  where 动作='预上货' and 状态2<>'1' and 请求区='"+machineID+"'order by idEvent";
        	set=st.executeQuery(sqll);
             if(set.next()){
             	have=true;
             	back="已有预上货指令,只能有一个预上指令！";
              }
             }
             
      }
      
        
        if(!have){
        String sqll="select idEvent,状态,状态2 from 立库动作指令  where 状态<>'完成' and 托盘编号='"+tp+"' order by idEvent";
        set=st.executeQuery(sqll);
        if(set.next()){
        	have=true;
        	back="关于这个托盘的指令有未执行完的指令";
         }
        }
        
       if(type.equals("输送线回流")){
    	   //所取的货架有没有这个托盘
  		 if(!have){
   	        String sql2="select 货位序号   from 货位表   where  托盘编号='"+tp+"' and 货位序号='"+fromID+"'";	
   	        set=st.executeQuery(sql2);
   	         if(!set.next()){
   	        	have=true;
   	        	 back="在指定位置没这个托盘";
   	           }	
   	        }
  		 
  		 if(!have){
  			 int toID2=toI;
  			 if(toI==60002){
  				 if(fomI>500&&fomI<515){toID2=60001;}
  				 if(fomI>600&&fomI<615){toID2=60002;}
  			 }
    	   String  ql="insert into 立库动作指令 (来源,任务类别,动作,"+
                   "托盘编号,状态,来源货位号,放回货位号,状态2,请求区,是否回大库,新建时间) values("+
                   "'立库',"+
                   "'PACK装配',"+
                   "'输送线回流',"+
                   "'"+tp+"',"+
                    "'排队',"+
                     "'"+fromID+"',"+
                     "'"+toID2+"',"+
                      "'"+(0)+"',"+
                      "'"+machineID+"',"+
                      "'"+todaku+"',"+
                      SqlPro.getDate()[1]+
                   ")";
			   st.executeUpdate(ql);
			   back="指令成功加入-"+toID;
  		       }
			   
         }else{
        	 
        	 if(type.equals("预上货")){
            	if(!have){
            		  String  ql="insert into 立库动作指令 (来源,任务类别,动作,"+
                              "托盘编号,状态,来源货位号,放回货位号,状态2,请求区,是否回大库,新建时间) values("+
                              "'立库',"+
                              "'PACK装配',"+
                              "'预上货',"+
                              "'"+tp+"',"+
                               "'完成',"+
                                "'"+fromID+"',"+
                                "'"+toI+"',"+
                                 "'"+0+"',"+
                                 "'"+machineID+"',"+
                                 "'"+0+"',"+
                                 SqlPro.getDate()[1]+
                              ")";
        			    st.executeUpdate(ql);
        			    back="指令成功加入-"+toID;
            		
            	}
          }
        	
        	 if(type.equals("上货")){
        		 
        		 //判断去往的货位有没有托盘
        		
        		 if(!have){
        			  
         	        String sql2="select 托盘编号  from 货位表   where  货位序号='"+toID+"'";	
         	        set=st.executeQuery(sql2);
         	         if(set.next()){
         	        	Object t=set.getObject(1)==null?"":set.getObject(1);
         	        	if(!t.equals("")){
         	        		have=true;
         	        	   back="去往的货位已经有托盘";
         	        	          }
         	           }	
         	        }
        		 //判断去往的货位在指令队列里面有没有被指定别的托盘
        		 if(!have){
        		String sql2="select idEvent,放回货位号,状态  from 立库动作指令  where 状态<>'完成' and 放回货位号='"+toID+"' order by idEvent";
          	     set=st.executeQuery(sql2);
          	         if(set.next()){
          	        	Object t=set.getObject(1)==null?"":set.getObject(1);
          	        	if(!t.equals("")){
          	        		have=true;
          	        	    back="在指令队列里去往的货位被安排托盘";
          	        	          }
          	           }	
          	        }
        		 //如果上面判断多没有，那么想指令队列里面插入数据
        		  if(!have){
        			  String  ql="insert into 立库动作指令 (来源,任务类别,动作,"+
                              "托盘编号,状态,来源货位号,放回货位号,状态2,请求区,是否回大库,新建时间) values("+
                              "'立库',"+
                              "'PACK装配',"+
                              "'上货',"+
                              "'"+tp+"',"+
                               "'排队',"+
                                "'"+fromID+"',"+
                                "'"+toID+"',"+
                                 "'"+1+"',"+
                                 "'"+machineID+"',"+
                                 "'"+0+"',"+
                                 SqlPro.getDate()[1]+
                              ")";
        			    st.executeUpdate(ql);
        			    back="指令成功加入-"+toID;
        		  }
        		 
        	 }//end上货

        	 if(type.equals("下货")){
        		 //所取的货架有没有这个托盘
        		 if(!have){
         	        String sql2="select 货位序号   from 货位表   where  托盘编号='"+tp+"' and 货位序号='"+fromID+"'";	
         	        set=st.executeQuery(sql2);
         	         if(!set.next()){
         	        	have=true;
         	        	 back="在指定位置没这个托盘";
         	           }	
         	        }
        		 
        		 if(toID.equals("60002")||toID.equals("60001")){
        		 //判断取料升降台有没有托盘
        		 if(!have){//暂时先不做
        		
          	        }
        		 
        		   }else{
        		  //判断7条输送线有没有货	   
        			   if(!have){
                	        String sql2="select 托盘编号  from 货位表   where  货位序号='"+toID+"'";	
                	        set=st.executeQuery(sql2);
                	         if(set.next()){
                	        	Object t=set.getObject(1)==null?"":set.getObject(1);
                	        	if(!t.equals("")){
                	        		have=true;
                	        	    back="去往的货位已经有托盘";
                	        	          }
                	           }	
                	         }    
        		   }
        		 
        		
        		 //在判断有没有两个堆垛机去往同一个地方下货的。
        		 if(!have){
             		String sql2="select idEvent,来源货位号,状态  from 立库动作指令  where 状态<>'完成' and 来源货位号='"+fromID+"' order by idEvent";
               	     set=st.executeQuery(sql2);
               	         if(set.next()){
               	        	Object t=set.getObject(1)==null?"":set.getObject(1);
               	        	if(!t.equals("")){
               	        		have=true;
               	        	    back="在指令队列里已有指令安排到哪儿取货！";
               	        	          }
               	           }	
               	        }
        		 
        		 if(!have){
        			 String sql2="select idEvent,放回货位号,状态  from 立库动作指令  where 状态<>'完成' and 放回货位号='"+toID+"' order by idEvent";
              	       set=st.executeQuery(sql2);
                	         if(set.next()){
                	        	Object t=set.getObject(1)==null?"":set.getObject(1);
                	        	if(!t.equals("")){
                	        		have=true;
                	        	    back="在指令队列里已有指令安排下货到这个位置！";
                	        	          }
                	           }	
                	        }
        		 
        		 //如果上面判断多没有，那么想指令队列里面插入数据
        		  if(!have){
        			  int t=Integer.parseInt(toID);
        			   if(t>500&&t<615)todaku=0;
        			  String  ql="insert into 立库动作指令 (来源,任务类别,动作,"+
                              "托盘编号,状态,来源货位号,放回货位号,状态2,请求区,是否回大库,新建时间) values("+
                              "'立库',"+
                              "'PACK装配',"+
                              "'下货',"+
                              "'"+tp+"',"+
                               "'排队',"+
                                "'"+fromID+"',"+
                                "'"+toID+"',"+
                                 "'"+(todaku==1?0:1)+"',"+
                                 "'"+machineID+"',"+
                                 "'"+todaku+"',"+
                                 SqlPro.getDate()[1]+
                              ")";
        			    st.executeUpdate(ql);
        		        back="指令成功加入-"+toID;
        		  }
        		 
        	 }//end下货
        		 	 
        	 
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
   
  //用于手动上托盘，成功后返回包含“成功”的字样 
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
        
        if(machineID.equals("1")){
            
 		   back = add动作指令( tp,"60001", toID,"预上货"/*上货，下货，输送线回流,预上货*/, 
 					  0,  machineID);
 		   }
 		   if(machineID.equals("2")){
 		       
 		  back = add动作指令( tp,"60002", toID,"预上货"/*上货，下货，输送线回流,预上货*/, 
 						  0,  machineID);
 		    }
        //第一步先把托盘的数量给按上
 	if(back.contains("成功")){
        set=st.executeQuery("select 托盘编号 from 库存托盘  where 托盘编号="+"'"+tp+"'");   
		   if(set.next()){
			   st.executeUpdate("update 库存托盘  set 数量='"+num+"',方向='"+machineID+"',物料='"+wuliao+"' where 托盘编号='"+tp+"'");  
		   }else{
			   String  ql="insert into 库存托盘 (托盘编号,物料,数量,"+
	                   "方向) values("+
	                     "'"+tp+"',"+
	                     "'"+wuliao+"',"+
	                      "'"+num+"',"+
	                      "'"+machineID+"'"+
	                      
	                    ")";
			   st.executeUpdate(ql);	   
			   
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
//主要应用与来料升降台   
   public static String autoUpPallet(String tp,String wuliao,String fromID,String machineID)  {
	   Integer id=Integer.parseInt(fromID);
	   if(id>60004&&id<60001)
	   {//60002,60003
		  return "上料点超出范围!_!-1";
	   }
	   
	   if(wuliao.equals("")||wuliao==null){wuliao="-1";}
	   
       ConnactionPool p=ConnactionPool.getPool();
       Conn conn=p.getCon2("");
       String back="未处理!_!-1";
       Statement st=null;
       ResultSet set=null;
      Connection con=conn.getCon();
 try{
        st=con.createStatement();
        con.setAutoCommit(false);
        boolean isToLine=false;
       if(fromID.equals("60001")||fromID.equals("60002")/*||fromID.equals("60003")||fromID.equals("60004")*/){//两个上货点
    	 //判断这个货物应该去哪个工位，优先去7个工位,首先检测7个工位有没有指令，然后在检测工位上有没有托盘
    	  // String sql="SELECT DISTINCT 工位,数量-IFNULL(完成数量,0) from 配方指令队列   where 物料='"
    	  // +wuliao+"' AND 装配区='"+machineID+"' and 数量-IFNULL(完成数量,0)>0 ORDER BY 工单序号,模组序号,分解号,载具序号  LIMIT 2 ";
    	  //1.首先判断当前指令队列有没有需要这个物料的
    	   for(int i=1;i<8;i++){
    	   String wul1= (machineID.equals(SqlPro.堆垛机1+"")?PLC.getIntance().STC1.get(i).firstST.get物料编码():
    		   PLC.getIntance().STC2.get(i).firstST.get物料编码());
    	   String wul2= (machineID.equals(SqlPro.堆垛机1+"")?PLC.getIntance().STC1.get(i).secondST.get物料编码():
    		   PLC.getIntance().STC2.get(i).secondST.get物料编码());
    	   int sl1=  (machineID.equals(SqlPro.堆垛机1+"")?PLC.getIntance().STC1.get(i).firstST.get剩余数量():
    		   PLC.getIntance().STC2.get(i).firstST.get剩余数量());
    	   int sl2= (machineID.equals(SqlPro.堆垛机1+"")?PLC.getIntance().STC1.get(i).secondST.get剩余数量():
    		   PLC.getIntance().STC2.get(i).secondST.get剩余数量());
    	   System.out.println(wuliao+"/"+wul1+"/"+wul2+"/"+sl1+"/"+sl2);
    	   if((wuliao.equals(wul1)&&sl1>0)||(wuliao.equals(wul2)&&sl2>0)){
    		   //查找货位上的
    		   System.out.println("==========");
    		int huowei= SqlPro.getMap().get(machineID+""+i+"ST");
    	    String sql2= "select  托盘编号   from 货位表  where  货位序号 ='"+huowei+"'";
    	    set=st.executeQuery(sql2);
    	    if(set.next()){
    	    	Object t=set.getObject(1);
    	    	if(t==null){//如果缓存工位没有TP那么就发到这个工位
    	    		//在检查一下指令队列里面有没有去往这个货位的指令
    	    	    set=st.executeQuery("select idEvent,放回货位号,状态  from 立库动作指令  where 状态<>'完成' and 放回货位号='"+huowei+"' order by idEvent");
    	    		if(!set.next()){
    	    	    String iss=add动作指令( tp, fromID,huowei+"","上货"/*上货，下货，输送线回流*/, 
    	    				  0/*1=回大库，非1=不回*/,  machineID);
    	    		if(iss.contains("成功")){
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
    	  
    	    //60002,那只能去货架   ,如果60001没去Line,那么这里一起处理去货架的情况
    	    //2.首先判断这个托盘默认去哪个位置，在默认去的位置中，看看第一个空的是什么
    	   String quyu="";
    	   String s1=" SELECT 默认上货区  from 通用物料  where 物料编码='"+wuliao+"'";
    	   boolean isDefault=false;
    	   set=st.executeQuery(s1);
    	   if(set.next()){quyu=set.getString(1)==null?"":(String)set.getString(1);}
    	   if(!quyu.equals("")){
    	   String s2=" SELECT 货位序号 from 货位表   where 货位序号 IN("+quyu+") and (`托盘编号`='' or `托盘编号` is  null)  order by 距离";
    	   set=st.executeQuery(s2); 
    	   while(set.next()){
    		   String iss=add动作指令( tp, fromID,set.getObject(1)+"","上货"/*上货，下货，输送线回流*/, 
	    				  0/*1=回大库，非1=不回*/,  machineID);
    		      if(iss.contains("成功")){
    		    	  isDefault=true;
    		    	  back=iss+"!_!"+set.getObject(1);
    		    	  break;}
    		   }
    	    
    	   }
 
    	   //3. 判断货架上离上货位最近的距离
    	   if(!isDefault){
    		  
    		   String s3=" SELECT 货位序号  from 货位表   where 托盘编号  is null or `托盘编号`='' order by 距离";
    		   set=st.executeQuery(s3);
    		   while(set.next()){
    			   String iss=add动作指令( tp, fromID,set.getObject(1)+"","上货"/*上货，下货，输送线回流*/, 
 	    				  0/*1=回大库，非1=不回*/,  machineID);
    			  
     		      if(iss.contains("成功")){
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
   public static synchronized String setStateForEventID(int idEvent, int state, String ext) {
		// TODO Auto-generated method stub
	   System.out.println("调用"+idEvent+"/"+state);
	  synchronized(lock){
		   System.out.println("進入LOCL"+idEvent+"/"+state);
	  String back="";
	  ConnactionPool p=ConnactionPool.getPool();
      Conn conn=p.getCon2("");
      ResultSet set=null;
     Statement st=null;
     Connection con=conn.getCon();
try{  boolean isEvent=false;
       st=con.createStatement();
       con.setAutoCommit(false);
       String sql="select 动作,状态,状态2,是否回大库,来源货位号,放回货位号,托盘编号,请求区  from 立库动作指令  where idEvent='"+idEvent+"' and 状态<>'完成'";
       set=st.executeQuery(sql);
       if(set.next()){
    	  
    	   String zong=set.getObject(1)+"";String zt1=set.getObject(2)+"";
    	   String zt2=set.getObject(3)+"";String isToback=set.getObject(4)+"";
           String fromID=set.getObject(5)+"";String toID=set.getObject(6)+"";
           String tp=set.getObject(7)+"";
           String qu=set.getObject(8)+"";
       if(state==SqlPro.完成){
    	   isEvent=true;
    	   if(zong.equals("上货")){
    	   //1.第一步更新库存托盘表
    	  
    		   set=st.executeQuery("select 托盘编号,物料 from 库存托盘  where 托盘编号="+"'"+tp+"'");   
    		   if(set.next()){
    			    String wuliao=set.getString(2);
    			   st.executeUpdate("update 库存托盘  set 货位号='"+toID+"',方向='"+qu+"' where 托盘编号='"+tp+"'");  
    			   if(fromID.equals("60001")||fromID.equals("60002")){
    				 //更新托盘内的配方位置 
    				   set=st.executeQuery("select 类型  from 通用物料  where 物料编码="+"'"+wuliao+"'"); 
    				   String lei="";
    				   if(set.next()){
    					   lei=set.getString(1);
    					   SqlTool.initAddresInPalet(lei, tp,st); 
    				   }
    				  
	    			  
    				}
    		   }else{
    			   String sba[]=getWuliaoFromLK(tp).split("!_!"); 
    			   
    			   String  ql="insert into 库存托盘 (托盘编号,物料,数量,"+
    	                   "方向,货位号) values("+
    	                     "'"+tp+"',"+
    	                      "'"+sba[0]+"',"+
    	                      "'"+sba[1]+"',"+
    	                      "'"+qu+"',"+
    	                      "'"+toID+"'"+
    	                    ")";
    			   st.executeUpdate(ql);
    			   
    			   if(fromID.equals("60001")||fromID.equals("60002")){
      				/*****更新托盘内的配方位置，等着做  
      				   */
    				   set=st.executeQuery("select 类型  from 通用物料  where 物料编码="+"'"+sba[0]+"'"); 
    				   if(set.next()){
    					   SqlTool.initAddresInPalet(set.getString(1), tp,st);
    				   }
      			   }
    		  
    		   }
    		   //2.第二步更新货位表
    		   st.executeUpdate("update 货位表     set 托盘编号='"+tp+"',堆垛机='"+qu+"' where 货位序号="+"'"+toID+"'");
    		   //3.第三步更新配方指令队列表
    		   st.executeUpdate("update 立库动作指令  set 状态='完成',完成时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    		
    		  
    		   }
    	   
    	   if(zong.equals("下货")){
    		  
        	   //1.第一步更新库存托盘表
    		   if(toID.equals("60002")||toID.equals("60001")){
    			   //如果是下到人工台的，直接把托盘的货位清空
    		     st.executeUpdate("update 库存托盘  set 货位号=NULL ,方向='"+qu+"' where 托盘编号='"+tp+"'");
    		   }else{
    		   st.executeUpdate("update 库存托盘  set 货位号='"+toID+"' ,方向='"+qu+"' where 托盘编号='"+tp+"'");   
    		   }
    		   //2.第二步更新货位表
    		   st.executeUpdate("update 货位表     set 托盘编号=NULL,堆垛机=NULL where 货位序号="+"'"+fromID+"'");
    		   if(!toID.equals("60002")&&!toID.equals("60001"))
    		   st.executeUpdate("update 货位表     set 托盘编号='"+tp+"',堆垛机='"+qu+"'  where 货位序号="+"'"+toID+"'");
    		  //3.第三步更新配方指令队列表
    		   st.executeUpdate("update 立库动作指令  set 状态='完成',完成时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    		
        		   }
    	   
    	   if(zong.equals("输送线回流")){
    		   //在临时版本里面，输送线回流要分两种方式来处理，一种回货架，一种回人工台
    		  
    		 //1.第一步更新库存托盘表
         	  
    		   // st.executeUpdate("update 库存托盘  set 货位号='"+toID+"' ,方向='"+qu+"' where 托盘编号='"+tp+"'");
    		    st.executeUpdate("update 库存托盘  set 货位号=NULL  where 托盘编号='"+tp+"'");
    		   //2.第二步更新货位表
    		   st.executeUpdate("update 货位表     set 托盘编号=NULL,堆垛机=NULL where 货位序号="+"'"+fromID+"'");
    		   if(toID.equals("60002")||toID.equals("60001")){
    			   //如果是回人工台的
    			   
    		     //  st.executeUpdate("update 货位表     set 托盘编号='"+tp+"',堆垛机='"+qu+"'  where 货位序号="+"'"+toID+"'");
    			     st.executeUpdate("update 立库动作指令  set 状态2='1' where idEvent="+"'"+idEvent+"'");
    			     st.executeUpdate("DELETE from `库存托盘` where 托盘编号='"+tp+"'");
    			     
    	        	
    		   }
    		  //3.第三步更新配方指令队列表
    		   st.executeUpdate("update 立库动作指令  set 状态='完成',完成时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    		   
        		   }
      
          }
       
       if(state==SqlPro.执行中){
    	   isEvent=true;
    	   //还没做
    	   st.executeUpdate("update 立库动作指令  set 状态='执行中',发送时间="+SqlPro.getDate()[1]+" where idEvent="+"'"+idEvent+"'");
    	   
    	   
          }
       }
       if(isEvent){
         back="成功";
         zl1="";
         }else{
        	 back="没这个eventID";
             zl1="你删除了一个执行中的指令,eventID="+idEvent+"，请确保你的操作是正确的。";
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

     return back;
     
	   }
	   
	}
   
   //从大库根据托盘编码或取物料和数量的方法
   public static String fromDKisTP="wuliao!_!1";
   public static String getWuliaoFromLK(String tp){
	   
	   return findOneRecord("select 物料,数量,max(ID)  FROM 大库请求   where 托盘编码='"+tp+"'");
   }
   
   //通知升降台，可以升起向大库送货
 
   public static String informToDK(String tp,String wuliao,int shuliang){
	   
	   return "成功";
   }
   
   //当去料升降台有信号时调用这个方法
   //信息=货位号，当货位号=-1，执行不成功，=100000，升降机去大库成功
   public static synchronized String exeRffid2(String tp){
	   ConnactionPool p=ConnactionPool.getPool();
       Conn conn=p.getCon2("");
       ResultSet set=null;
      Statement st=null;
      Connection con=conn.getCon();
      String back="未处理!_!-1";
 try{
        st=con.createStatement(); 
        con.setAutoCommit(false);
        String sql="select idEvent,动作,状态,状态2,是否回大库,来源货位号,放回货位号,托盘编号,"
        		+"请求区  from 立库动作指令   where 动作='预上货' and 状态2<>'1' and 状态='完成'"
        		+" and 托盘编号='"+tp+"' order by idEvent";
        set=st.executeQuery(sql);
        if(set.next()){//有预上货指令
        	 boolean isHuowei=false;
        	 System.out.println("RFID2-------------3="+tp);
        	 Object eventID=set.getObject(1);
        	 Object toID=set.getObject(7)==null?"":set.getObject(7);
        	 String machineID=set.getObject(9)==null?"":set.getObject(9).toString();
        	 
        	 if(!toID.equals("")&&!toID.equals("0")){
        		if(machineID.equals("1")) {
        	 String iss=add动作指令( tp, "60001",toID+"","上货"/*上货，下货，输送线回流*/, 
    				  0/*1=回大库，非1=不回*/,  machineID);
        	 
        	 if(iss.contains("成功")){
        		 System.out.println("RFID2-------------4="+tp);
        		 //更新状态二
        		 st.executeUpdate("update 立库动作指令  set 状态2='1' where idEvent="+"'"+eventID+"'");
        		
        		 isHuowei=true;
        		 back=iss+"!_!"+toID;
        		 
        	   }}
        		
        		if(machineID.equals("2")) {
               	 String iss=add动作指令( tp, "60002",toID+"","上货"/*上货，下货，输送线回流*/, 
   	    				  0/*1=回大库，非1=不回*/,  machineID);
               	 
               	 if(iss.contains("成功")){
               		 System.out.println("RFID2-------------4="+tp);
               		 //更新状态二
               		 st.executeUpdate("update 立库动作指令  set 状态2='1' where idEvent="+"'"+eventID+"'");
               		
               		 isHuowei=true; 
               		 back=iss+"!_!"+toID;
               		 
               	   }}
               	 
        	 
        	 }
        	 
        	 if(!isHuowei){
        		 //自动判断
            	 set=st.executeQuery("select 物料,方向  from 库存托盘  where 托盘编号="+"'"+tp+"'"); 	
            	 
            	 if(set.next()){
            		 Object wul=set.getObject(1);
            		 Object machineID2=set.getObject(2);
            		 if(machineID.equals("1")){
            		 back =autoUpPallet( tp,wul+"","60001",machineID2+"");
            		 }
            		 else{
            	     back =autoUpPallet( tp,wul+"","60002",machineID2+""); 
            			 
            		 }
            		 if(back.contains("成功"))
            		 st.executeUpdate("update 立库动作指令  set 状态2='1' where 托盘编号="+"'"+tp+"'");
            	      }
            	 
                 } 
        	
        	 back="成功处理!_!100000";
        }
         else{
        	//如果这个托盘是来源与7条输送线的上货架
        	 boolean isHuowei=false;
        	 //首先判断这个托盘有没有指定货位
        	 String sql3="select max(idEvent),动作,状态,状态2,是否回大库,来源货位号,放回货位号,托盘编号,"
             		+"请求区  from 立库动作指令   where   动作='输送线回流' and 状态='完成' and 状态2<>'1'"
             		+" and 托盘编号='"+tp+"'";
        	 System.out.println("RFID2-------------2="+tp);
             set=st.executeQuery(sql3);
             if(set.next()){
            	 System.out.println("RFID2-------------3="+tp);
            	 Object eventID=set.getObject(1);
            	 Object toID=set.getObject(7)==null?"":set.getObject(7);
            	 String machineID=set.getObject(9)==null?"":set.getObject(9).toString();
            	 if(!toID.equals("60001")&&!toID.equals("60002")&&!toID.equals("")&&!toID.equals("0")){
            		if(machineID.equals("1")) {
            	 String iss=add动作指令( tp, "60003",toID+"","上货"/*上货，下货，输送线回流*/, 
	    				  0/*1=回大库，非1=不回*/,  machineID);
            	 
            	 if(iss.contains("成功")){
            		 System.out.println("RFID2-------------4="+tp);
            		 //更新状态二
            		 st.executeUpdate("update 立库动作指令  set 状态2='1' where idEvent="+"'"+eventID+"'");
            		
            		 isHuowei=true;
            		 back=iss+"!_!"+toID;
            		 
            	   }}
            		
            		if(machineID.equals("2")) {
                   	 String iss=add动作指令( tp, "60004",toID+"","上货"/*上货，下货，输送线回流*/, 
       	    				  0/*1=回大库，非1=不回*/,  machineID);
                   	 
                   	 if(iss.contains("成功")){
                   		 System.out.println("RFID2-------------4="+tp);
                   		 //更新状态二
                   		 st.executeUpdate("update 立库动作指令  set 状态2='1' where idEvent="+"'"+eventID+"'");
                   		
                   		 isHuowei=true;
                   		 back=iss+"!_!"+toID;
                   		 
                   	   }}
                   	 
            	 
            	 
            	 }
            	 
             }
             
             if(!isHuowei){
            	 //自动判断
        	 set=st.executeQuery("select 物料,方向  from 库存托盘  where 托盘编号="+"'"+tp+"'"); 	
        	 
        	 if(set.next()){
        		 Object wul=set.getObject(1);
        		 Object machineID=set.getObject(2);
        		 if(machineID.equals("1")){
        		 back =autoUpPallet( tp,wul+"","60003",machineID+"");
        		 }
        		 else{
        	     back =autoUpPallet( tp,wul+"","60004",machineID+""); 
        			 
        		 }
        		 if(back.contains("成功"))
        		 st.executeUpdate("update 立库动作指令  set 状态2='1' where 托盘编号="+"'"+tp+"'");
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

     System.out.println("处理Rffid"+back);
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
	      String sql = "UPDATE plc SET PLC=?,更新时间="+SqlPro.getDate()[1]+" where ID='1'";
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


         return "记录更新成功！";

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

public static void clearValueForPalet( String 货位号,int machineID){
	System.out.println("clear pallet address-------------");
	if(pro==null){pro=SqlPro.loadProperties(SqlPro.class.getResource("conf.pro").getFile());}
	if(pro!=null){
		 int to[]=new int[41];
		 ClientSer.getIntance().writeSirIntToCTR(pro.getProperty(货位号), 41, to,  machineID)  ;	
		
	  }
	
	
 }	

//写入托盘里面的货物位置,刚开始入托盘时，说有货物全是慢的。	 
public static	void initAddresInPalet(String lei/*1=10行X2列X2成*/,String palet, Statement st){
	if(lei==null||lei.equals(""))return;
	//向数据库里面插入的数据格式如下
	//地址1=1,地址2=0，。。。。。。1代表有货，0代表没货
		if(lei.equals("1")){//类别确定了写入的长度，10行X2列X2成
			//1-40
			//货位的起始地址，标注有没有初始化，比如货位的起始地址是D200，那么D200就标注为有没有初始化。
			String val="1=1,2=1,3=1,4=1,5=1,6=1,7=1,8=1,"+
					    "9=1,10=1,11=1,12=1,13=1,14=1,15=1,16=1,17=1,"+
					    "18=1,19=1,20=1,21=1,22=1,23=1,24=1,25=1,26=1,"+
					    "27=1,28=1,29=1,30=1,31=1,32=1,33=1,34=1,35=1,"+
					    "36=1,37=1,38=1,39=1,40=1";
			String sql= "update 库存托盘  set address='"+val+"' where 托盘编号="+"'"+palet+"'";
			System.out.println(sql);
			try{
				if(st==null){
				SqlTool.insert(new String[]{sql});}
				else{
					st.executeUpdate(sql);
					
				}
				
			}catch(Exception ex){}
			
			
		}
       if(lei.equals("2")){//类别确定了写入的长度，7行X2列X2成
			//1-28
    	    //货位的起始地址，标注有没有初始化
    	   String val="1=1,2=1,3=1,4=1,5=1,6=1,7=1,8=1,"+
				    "9=1,10=1,11=1,12=1,13=1,14=1,15=1,16=1,17=1,"+
				    "18=1,19=1,20=1,21=1,22=1,23=1,24=1,25=1,26=1,"+
				    "27=1,28=1";
    	   String sql= "update 库存托盘  set address='"+val+"' where 托盘编号="+"'"+palet+"'";
    		try{
				if(st==null){
				SqlTool.insert(new String[]{sql});}
				else{
					st.executeUpdate(sql);
					
				}
				
			}catch(Exception ex){}
			
		}
       
       if(lei.equals("3")){//类别确定了写入的长度，7行X1列X1成,底板
			//1-7
    	   String val="1=1,2=0,3=1,4=0,5=1,6=0,7=1,8=0,"+
				    "9=1,10=0,11=1,12=0,13=1,14=0,15=1,16=0,17=1,"+
				    "18=0,19=1,20=0,21=1,22=0,23=1,24=0,25=1,26=0,"+
				    "27=1,28=0";
    	   String sql= "update 库存托盘  set address='"+val+"' where 托盘编号="+"'"+palet+"'";
    		try{
				if(st==null){
				SqlTool.insert(new String[]{sql});}
				else{
					st.executeUpdate(sql);
					
				}
				
			}catch(Exception ex){}
			
			
		}
       if(lei.equals("4")){//类别确定了写入的长度，4行X2列X1成
			//1-8
    	   String val="1=1,2=1,3=1,4=1,5=1,6=1,7=1,8=1";
    	   String sql= "update 库存托盘  set address='"+val+"' where 托盘编号="+"'"+palet+"'";
    		try{
				if(st==null){
				SqlTool.insert(new String[]{sql});}
				else{ 
					st.executeUpdate(sql);
					
				}
				
			}catch(Exception ex){}
			
		}
} 

//向PLC里面写入托盘上的货物位置型号 ,只有当托盘到达输送线的终端时才写入,在startLine里调用
public static	void WriteAddresInPaletToPLC(String lei/*1=10行X2列X2成*/,String palet,String huowei,int machineID){
	System.out.println("write pallet address-------------");
	if(lei==null||lei.equals(""))return;
	
	if(pro==null){pro=SqlPro.loadProperties(SqlPro.class.getResource("conf.pro").getFile());}
	if(pro!=null){
		
	
			//货位的起始地址，标注有没有初始化，比如货位的起始地址是D200，那么D200就标注为有没有初始化。
	   String val=SqlTool.findOneRecord("select address from 库存托盘  where  托盘编号='"+palet+"'");
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

//从PLC里面读取数据后，然后保存到数据库里面，接受到数据更新完成时多会调用此方法
public static	void readAddresInPaletFromPLC(String lei/*1=10行X2列X2成*/,String palet,String huowei,int machineID){
	System.out.println("read pallet address from plc -------------huowei="+huowei+"/tp="+palet+"machineID="+machineID);
	if(lei==null||lei.equals(""))return;
	
	if(pro==null){pro=SqlPro.loadProperties(SqlPro.class.getResource("conf.pro").getFile());}
	if(pro!=null){
		
		if(lei.equals("1")){//类别确定了写入的长度，10行X2列X2成
			//1-40
			//货位的起始地址，标注有没有初始化，比如货位的起始地址是D200，那么D200就标注为有没有初始化。
			String val="";
			alai.GDT.Resint[]back=ClientSer.getIntance().getSirIntValuesFromCTR(pro.getProperty(huowei), 41, 16, machineID);
			for(int i=1;i<41;i++){
				 val=val+","+i+"="+back[i].resInt;
				
			}
			val=val.substring(1, val.length());
			String sql= "update 库存托盘  set address='"+val+"' where 托盘编号="+"'"+palet+"'";
			SqlTool.insert(new String[]{sql});
			
		}
       if(lei.equals("2")){//类别确定了写入的长度，7行X2列X2成
			//1-28
    	    //货位的起始地址，标注有没有初始化
    	   String val="";
			alai.GDT.Resint[]back=ClientSer.getIntance().getSirIntValuesFromCTR(pro.getProperty(huowei), 29, 16, machineID);
			for(int i=1;i<29;i++){
				 val=val+","+i+"="+back[i].resInt;
				
			}
			val=val.substring(1, val.length());
    	   String sql= "update 库存托盘  set address='"+val+"' where 托盘编号="+"'"+palet+"'";
			SqlTool.insert(new String[]{sql});
			
		}
       
       if(lei.equals("3")){//类别确定了写入的长度，7行X1列X1成
			//1-7
    	   String val="";
			alai.GDT.Resint[]back=ClientSer.getIntance().getSirIntValuesFromCTR(pro.getProperty(huowei), 8, 16, machineID);
			for(int i=1;i<8;i++){
				 val=val+","+i+"="+back[i].resInt;
				
			}
			val=val.substring(1, val.length());
    	   String sql= "update 库存托盘  set address='"+val+"' where 托盘编号="+"'"+palet+"'";
			SqlTool.insert(new String[]{sql});
			
			
		}
       if(lei.equals("4")){//类别确定了写入的长度，4行X2列X1成
			//1-8
    	   String val="";
			alai.GDT.Resint[]back=ClientSer.getIntance().getSirIntValuesFromCTR(pro.getProperty(huowei), 9, 16, machineID);
			for(int i=1;i<9;i++){
				 val=val+","+i+"="+back[i].resInt;
				
			}
			val=val.substring(1, val.length());
    	   String sql= "update 库存托盘  set address='"+val+"' where 托盘编号="+"'"+palet+"'";
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
