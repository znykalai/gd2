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
		if(back.contains("�ɹ�")){return 1;}else return -1;

	}

	@Override
	public String getLastComment(int machineID,int type) {
		// TODO Auto-generated method stub
		if(machineID==1){
			if(type==1){//ָ�Ѷ���Ķ���ָ��
				Vector ��1=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬='ִ����' and ����<>'�����߻���' and ������= '1' order by idEvent");		
				if(��1.size()>0){
					Vector row=(Vector)��1.get(0);
					try{
					return row.get(0)+"|"+row.get(5)+"|"+row.get(6)+"|"+ClientSer.getIntance().getState(2)+"|"+(row.get(2).equals("�ϻ�")?1:2);
					}catch(Exception e){
						e.printStackTrace();
						return "-1";
					}
				}
			}
			if(type==2){
				Vector ��1=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬='ִ����' and ����='�����߻���' and ������= '1' order by idEvent");		
				if(��1.size()>0){
					Vector row=(Vector)��1.get(0);
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
			if(type==1){//ָ�Ѷ���Ķ���ָ��
				Vector ��1=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬='ִ����' and ����<>'�����߻���' and ������= '2' order by idEvent");		
				if(��1.size()>0){
					Vector row=(Vector)��1.get(0);
					try{
					return row.get(0)+"|"+row.get(5)+"|"+row.get(6)+"|"+ClientSer.getIntance().getState(2)+"|"+(row.get(2).equals("�ϻ�")?1:2);
					}catch(Exception e){
						e.printStackTrace();
						return "-1";
					}
				}
			}
			if(type==2){
				Vector ��1=SqlTool.findInVector("select idEvent,��Դ,�������,����,���̱��,��Դ��λ��,�Żػ�λ��,������,״̬,״̬2 from ���⶯��ָ��  where ״̬='ִ����' and ����='�����߻���' and ������= '2' order by idEvent");		
				if(��1.size()>0){
					Vector row=(Vector)��1.get(0);
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
