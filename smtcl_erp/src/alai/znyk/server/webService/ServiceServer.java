package alai.znyk.server.webService;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ServiceServer {
	public void setStateForEventID(@WebParam(name="idEvent")int idEvent,
			@WebParam(name="state")int state,
			@WebParam(name="ext")String ext);
	
	//�õ���������һ��ָ�machineID=1����һ���Ѷ����machineID=2ʱ��2���Ѷ����3�����һ���Ѷ��������ָ�4�����2���Ѷ��������ָ��
	//����ָ���ַ���
	public String getLastComment(@WebParam(name="machineID")int machineID,@WebParam(name="type")int type);
	
	
	public String exeComment(@WebParam(name="comment")String comment,@WebParam(name="type")int type) ;
}
