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
				Vector ��1��=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬='ִ����' and ����='�ϻ�' and ������= '1' order by idEvent");		
				
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
