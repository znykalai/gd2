package alai.znyk.server.webService;


import java.util.Vector;

import javax.jws.WebService;

import alai.znyk.common.ClientSer;
import alai.znyk.server.SqlTool;

@WebService(endpointInterface="alai.znyk.server.webService.ServiceServer",serviceName="ServiceServer")
public class ServiceServerImp implements ServiceServer {

	@Override
	public int setStateForEventID(int idEvent, int state, String ext) {
		// TODO Auto-generated method stub
		String back=SqlTool.setStateForEventID(idEvent, state, ext);
		System.out.println(back);
		if(back.contains("成功")){return 1;}else return -1;

	}

	@Override
	public String getLastComment(int machineID,int type) {
		// TODO Auto-generated method stub
		if(machineID==1){
			if(type==1){//指堆垛机的动作指令
				Vector 堆1=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态='执行中' and 动作<>'输送线回流' and 请求区= '1' order by idEvent");		
				if(堆1.size()>0){
					Vector row=(Vector)堆1.get(0);
					try{
					return row.get(0)+"|"+row.get(5)+"|"+row.get(6)+"|"+ClientSer.getIntance().getState(2)+"|"+(row.get(2).equals("上货")?1:2);
					}catch(Exception e){
						e.printStackTrace();
						return "-1";
					}
				}
			}
			if(type==2){
				Vector 堆1=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态='执行中' and 动作='输送线回流' and 请求区= '1' order by idEvent");		
				if(堆1.size()>0){
					Vector row=(Vector)堆1.get(0);
					try{
					return row.get(0)+"|"+row.get(5)+"|"+row.get(6)+"|"+ClientSer.getIntance().getState(2)+"|"+3;
					}catch(Exception e){
						e.printStackTrace();
						return "-1";
					}
				}
			}
			
		}
		
	///////////////////////////////////////////////////////////////////////////////////	
		if(machineID==2){
			if(type==1){//指堆垛机的动作指令
				Vector 堆1=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态='执行中' and 动作<>'输送线回流' and 请求区= '2' order by idEvent");		
				if(堆1.size()>0){
					Vector row=(Vector)堆1.get(0);
					try{
					return row.get(0)+"|"+row.get(5)+"|"+row.get(6)+"|"+ClientSer.getIntance().getState(2)+"|"+(row.get(2).equals("上货")?1:2);
					}catch(Exception e){
						e.printStackTrace();
						return "-1";
					}
				}
			}
			if(type==2){
				Vector 堆1=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态='执行中' and 动作='输送线回流' and 请求区= '2' order by idEvent");		
				if(堆1.size()>0){
					Vector row=(Vector)堆1.get(0);
					try{
					return row.get(0)+"|"+row.get(5)+"|"+row.get(6)+"|"+ClientSer.getIntance().getState(2)+"|"+3;
					}catch(Exception e){
						e.printStackTrace();
						return "-1";
					}
				}
			}
			
		}
		return "-1";
	}

	@Override
	public String exeComment(String comment,int type) {
		// TODO Auto-generated method stub
		return "test";
	}
}
