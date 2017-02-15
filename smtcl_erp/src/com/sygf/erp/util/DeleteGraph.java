//package com.sygf.erp.util;
//
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//
//public class DeleteGraph implements Job{
//	public void execute(JobExecutionContext context) throws JobExecutionException
//	 {
////	     String name = context.getJobDetail().getJobDataMap().getString("name");
////	     System.out.println("job executing..."+name);
////	     try{
////	     	DeleteGraph job = new DeleteGraph();
////	     	job.deleteGraph();
////	     }catch(Exception e){
////	     	System.out.println("exception file");
////	     	e.printStackTrace();
////	     }
//	 }
//	/**
//	 * 定时删除临时图片
//	 * 
//	 * @throws Exception
//	 */
//	private void deleteGraph() throws Exception{
//		
////		//String path = SysMaint.getProperty("TOMCAT_DIR");
////		String graphPath = GraphUtil.getGraphPath();
////		//System.out.println("job executing..."+graphPath);
////		File file = new File(graphPath);
////		Date date = new Date();
////		java.util.Calendar calendar = java.util.Calendar.getInstance();
////		calendar.setTime(date);
////		calendar.add(java.util.Calendar.DAY_OF_YEAR,1);
////		calendar.add(java.util.Calendar.MINUTE,-15);
////		date = calendar.getTime();
////		FileUtil.deleteFileDays(file,1,date);
////		
//	}
//	
//	public static void main(String[] args) {
////		DeleteGraph job = new DeleteGraph();
////		try{
////
////		job.deleteGraph();
////		}catch(Exception e){
////			e.printStackTrace();
////		}
//	}
//}
