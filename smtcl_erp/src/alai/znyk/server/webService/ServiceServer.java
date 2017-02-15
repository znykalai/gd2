package alai.znyk.server.webService;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ServiceServer {
	public void setStateForEventID(@WebParam(name="idEvent")int idEvent,
			@WebParam(name="state")int state,
			@WebParam(name="ext")String ext);
	
	//得到缓存库最后一条指令。machineID=1，第一个堆垛机，machineID=2时第2个堆垛机；3代表第一个堆垛机输送线指令；4代表第2个堆垛机输送线指令
	//返回指令字符串
	public String getLastComment(@WebParam(name="machineID")int machineID,@WebParam(name="type")int type);
	
	
	public String exeComment(@WebParam(name="comment")String comment,@WebParam(name="type")int type) ;
}
