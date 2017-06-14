package com.sygf.erp.baseData.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;

import com.sygf.erp.baseData.dao.OrderOperactionDAO;
import com.sygf.erp.baseData.dao.PLCDAO;
import com.sygf.erp.util.GetApplicationContext;
import com.sygf.erp.util.GetParam;

import alai.znyk.plc.CarryLine;
import alai.znyk.plc.PLC;
import alai.znyk.plc.STContent;
import alai.znyk.plc.ST_Father;

public class PLCAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String operType=request.getParameter("operType");
			if (operType.equals("getGw")){
				return getGw(mapping, form, request, response);
			}else if (operType.equals("updateGw")){
				return updateGw(mapping, form, request, response);
			}else if(operType.equals("gwGzUpdate")){
				return gwGzUpdate(mapping, form, request, response);
			}else if(operType.equals("gwGzDelete")){
				return gwGzDelete(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 清除PLC指令
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward gwGzDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			JSONObject result=new JSONObject();
			boolean plcDd;
			if(map.get("line").equals("1")){
				plcDd=PLC.getIntance().isStartDiaodu1();
			}else{
				plcDd=PLC.getIntance().isStartDiaodu2();
			};
			result.put("plcDd", plcDd);
			if(plcDd){//当调度没有开启时执行此方法
				PLC.getIntance().reLoad(Integer.parseInt(map.get("line").toString()));
			};map=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 故障恢复
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward gwGzUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			JSONObject result=new JSONObject();
			boolean plcDd;
			if(map.get("line").equals("1")){
				plcDd=PLC.getIntance().isStartDiaodu1();
			}else{
				plcDd=PLC.getIntance().isStartDiaodu2();
			};
			result.put("plcDd", plcDd);
			if(plcDd==false){//false时是开启调度状态，返回状态
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print(result);
				response.getWriter().close();
				return null;
			};
			ApplicationContext context=GetApplicationContext.getContext(request);
			PLCDAO dao=(PLCDAO)context.getBean("pLCDAO");
			JSONArray array1=new JSONArray(map.get("update1").toString());
			boolean yesNo=false;
			String err="成功";
			if(array1.length()>0){
				for(int i=0;i<array1.length();i++){
					int yiBssx=Integer.parseInt(array1.getJSONObject(i).getString("yiBssx"));
					String GdId=array1.getJSONObject(i).getString("GdId");
					String MzId=array1.getJSONObject(i).getString("MzId");
					String fenJh=array1.getJSONObject(i).getString("fenJh");
					String zaiJh=array1.getJSONObject(i).getString("zaiJh");
					String zaiJydbz=array1.getJSONObject(i).getString("zaiJydbz");
					//异步输送线处理
					if(map.get("line").equals("1")){//A区
						err=PLC.getIntance().line.setCarryAt(yiBssx,GdId,MzId,fenJh,zaiJh);
					}else{//B区
						err=PLC.getIntance().line2.setCarryAt(yiBssx,GdId,MzId,fenJh,zaiJh);
					};
					//数据库配方表处理
					String sql="update 配方指令队列 set 前升读标志=" +(zaiJydbz.equals("")?null:zaiJydbz)+" where 工单ID='"+GdId+"' and 模组序ID='"+MzId+"' and 分解号='"+fenJh+"' and 载具序号='"+zaiJh+"'";
					map.put("sql", sql);GdId=null;zaiJydbz=null;MzId=null;fenJh=null;zaiJh=null;sql=null;
					yesNo=dao.gwGzUpdate(map);
				};
			};array1=null;
			result.put("setCarryAt", err);err=null;
			JSONArray array2=new JSONArray(map.get("update2").toString());
			if(array2.length()>0){
				for(int i=0;i<array2.length();i++){
					String GdId=array2.getJSONObject(i).getString("GdId");
					String MzId=array2.getJSONObject(i).getString("MzId");
					String fenJh=array2.getJSONObject(i).getString("fenJh");
					String zaiJh=array2.getJSONObject(i).getString("zaiJh");
					String wanCsl=array2.getJSONObject(i).getString("wanCsl");
					String stGwydbz=array2.getJSONObject(i).getString("stGwydbz");
					String gw=array2.getJSONObject(i).getString("gw");
					//数据库配方表处理
					String sql="update 配方指令队列 set  完成数量=" +wanCsl+",ST读取标志=" +(stGwydbz.equals("")?null:stGwydbz)+" where 工单ID='"+GdId+"' and 模组序ID='"+MzId+"' and 分解号='"+fenJh+"' and 工位='"+gw+"' and 载具序号='"+zaiJh+"'";
					map.put("sql", sql);sql=null;GdId=null;MzId=null;fenJh=null;wanCsl=null;stGwydbz=null;zaiJh=null;
					yesNo=dao.gwGzUpdate(map);
				};
			};array2=null;map=null;dao=null;context=null;
			result.put("result", yesNo);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取PLC数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getGw(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap typeMap=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
//			System.out.println("typeMap="+typeMap);
			JSONObject result=new JSONObject();
			ArrayList mapList=new ArrayList();//返回table数据
			ArrayList sszList=new ArrayList();//返回到输送线
			//getDataFromGD
			//A区工位
			Vector<STContent> A=PLC.getIntance().STC1;
			//A区输送线
			CarryLine ss=PLC.getIntance().line;
			if(typeMap.get("type").equals("B")||typeMap.get("type").equals("D")){
				A=PLC.getIntance().STC2;		//B区工位
				ss=PLC.getIntance().line2;	//B区输送线
			};
			//装配区A，装配区B
			if(typeMap.get("type").equals("A")||typeMap.get("type").equals("B")){
				int j=0;
				while(j < 16){
					try{
						ArrayList list=new ArrayList();
						HashMap mapA=new HashMap();
						ST_Father duilie1=A.get(j).firstST;
						ST_Father duilie2=A.get(j).secondST;
						Vector<HashMap> v=new Vector<HashMap>();
						Hashtable temp=duilie1.getMap();
						Hashtable temp2=duilie2.getMap();
						Enumeration keys=temp.keys();
						while(keys.hasMoreElements()){
							HashMap map=new HashMap();
							Object k=keys.nextElement();
							Object val=temp.get(k);
							Object val2=temp2.get(k);
							if(val instanceof Boolean){
								map.put("A", k);
								map.put("B",val );
								map.put("C",val2 );
								v.insertElementAt(map, 0);
							}else{
								map.put("A", k);
								map.put("B",val );
								map.put("C",val2 );
								v.add(map);	
							}
						}
						for(int i=0;i<v.size();i++){
							list.add(v.get(i));
						}
						temp=null; temp2=null;keys=null;
						mapA.put("A_LIST", list);
						mapA.put("A_BOLL", duilie1.getBoolContent());
						mapA.put("A_SDZ", duilie1.getStartAddress());
						mapList.add(mapA);
						j++;
					}catch(Exception e){}
				};
			//选择PLC返回工位
			}else{
				int line=typeMap.get("type").equals("C")?1:2;
				int ST=Integer.parseInt(typeMap.get("gw").toString());
				Hashtable temp=PLC.getIntance().getDataFromGD(ST,line);
				ArrayList list=new ArrayList();
				HashMap mapA=new HashMap();
				Enumeration keys=temp.keys();
				while(keys.hasMoreElements()){
					Object k=keys.nextElement();
					if(k.toString().indexOf("2")==-1&&!(k=="boolCont"||k=="startAddres")){
						HashMap map=new HashMap();
						map.put("A", k);
						map.put("B", temp.get(k));
						map.put("C", temp.get(k+"2"));
						list.add(map);
					};
				};
				mapA.put("A_LIST", list);
				mapA.put("A_BOLL", temp.get("boolCont"));
				mapA.put("A_SDZ", temp.get("startAddres"));
				mapList.add(mapA);mapA=null;temp=null;keys=null;
			};
			for(int i=0;i<15;i++){
				HashMap map=new HashMap();
				try{
					map.put(""+i+"", ss.getCarry(i).get载具序号()+"-"+ss.getCarry(i).get工位());
					sszList.add(map);
				}catch(Exception e){
					map.put(""+i+"", "");
					sszList.add(map);
				};
				map=null;
			};
			result.put("data", mapList);
			result.put("A_SSX", sszList);
			A=null;sszList=null;mapList=null;ss=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return null;
	}
	/**
	 * 更改PLC原数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward updateGw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			String result=null;
			Vector<STContent> A=null;
			//A区
			if(map.get("gwType").equals("A")){
				A=PLC.getIntance().STC1;
			//B区
			}else if(map.get("gwType").equals("B")){
				A=PLC.getIntance().STC2;
			};
			//i= 当前第几个工位
			int i=Integer.parseInt(map.get("gw").toString());
			if(map.get("gwType").equals("A")||map.get("gwType").equals("B")){
				//map.get("cm")=当前工位下的第几个队列  firstST=队列1,secondST=队列2,
				if(map.get("cm").equals("firstST")){
					result=A.get(i).firstST.setValueByName(map.get("name").toString(), 
							map.get("value").toString(), map.get("oldValue").toString());
				}else{
					result=A.get(i).secondST.setValueByName(map.get("name").toString(), 
							map.get("value").toString(), map.get("oldValue").toString());
				}
			//update 选择PLC返回工位
			}else{
				//setValueByName
				result=PLC.getIntance().setValueByName(map.get("name").toString(), 
						map.get("value").toString(), map.get("oldValue").toString(), i, 
						map.get("gwType").equals("C")?1:2);
			};
			A=null;i=0;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return null;
	}
}