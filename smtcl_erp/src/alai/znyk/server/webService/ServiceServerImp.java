package alai.znyk.server.webService;


import java.util.Vector;

import javax.jws.WebService;

import alai.znyk.server.SqlTool;

@WebService(endpointInterface="alai.znyk.server.webService.ServiceServer",serviceName="ServiceServer")
public class ServiceServerImp implements ServiceServer {

	@Override
	public void setStateForEventID(int idEvent, int state, String ext) {
		// TODO Auto-generated method stub
		SqlTool.setStateForEventID(idEvent, state, ext);

	}

	@Override
	public String getLastComment(int machineID,int type) {
		// TODO Auto-generated method stub
		if(machineID==1){
			if(type==1){
				Vector 堆1上=SqlTool.findInVector("select idEvent,来源,任务类别,动作,托盘编号,来源货位号,放回货位号,请求区,状态,状态2 from 立库动作指令  where 状态='执行中' and 动作='上货' and 请求区= '1' order by idEvent");		
				
			}
			if(type==2){
				
			}
			
		}
		return "test";
	}

	@Override
	public String exeComment(String comment,int type) {
		// TODO Auto-generated method stub
		return "test";
	}
}
