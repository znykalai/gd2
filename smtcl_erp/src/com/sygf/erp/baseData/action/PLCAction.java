package com.sygf.erp.baseData.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
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
			}else if(operType.equals("updateAll")){
				return updateAll(mapping, form, request, response);
			}else if(operType.equals("closePLC")){
				return closePLC(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/***
	 * 清除PLC
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward closePLC(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
		ApplicationContext context=GetApplicationContext.getContext(request);
		OrderOperactionDAO gdDao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");context=null;
		map.put("sql", "SELECT a.* FROM `配方指令队列` AS a WHERE a.`装配区`='"+map.get("line")+"' and (a.`前升读标志` is not NULL or a.`ST读取标志` is not null);");
		List list=gdDao.getZlList(map);
		String error="true";
		if(list!=null&&list.size()>0){
			error="false";
		}else{
			PLC.getIntance().reLoad(Integer.parseInt(map.get("line").toString()));//清除PLC
		};
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(error);
		response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 一键还原数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private synchronized ActionForward updateAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			JSONObject result=new JSONObject();
			//判断当前调度是否开启
			boolean plcDd=false;
			boolean yesNo=false;
			boolean update=true;
			CarryLine line=null;
			if(map.get("line").equals("1")){
				plcDd=PLC.getIntance().isStartDiaodu1();
				line=PLC.getIntance().line;
			}else{
				plcDd=PLC.getIntance().isStartDiaodu2();
				line=PLC.getIntance().line2;
			};
			String err="失败!";
			if(plcDd==true){//调度没有开启时执行此方法
				if(!session.getAttribute("juese").equals("1")&&
					!session.getAttribute("juese").equals("4")){
					for(int i=0;i<15;i++){
						try{
							if(line.getCarry(i)!=null){
								update=false;break;
							};
						}catch(Exception e){}
					};
				};
				if(update==true){//输送线上没有载具时可以清除
					ApplicationContext context=GetApplicationContext.getContext(request);
					PLCDAO dao=(PLCDAO)context.getBean("pLCDAO");
					OrderOperactionDAO gdDao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");context=null;
					if(map.get("xh").equals("0")){//多个订单时第一次清除PLC，不允许多次清除
						PLC.getIntance().reLoad(Integer.parseInt(map.get("line").toString()));//清除PLC
					};
					String GdId=map.get("GdId").toString();
					map.put("sql", "SELECT a.* FROM `配方指令队列` AS a WHERE a.`工单ID`='"+GdId+"' AND a.`装配区`='"+map.get("line")+"'");
					List list=gdDao.getZlList(map);
					//异步输送线处理
					for(int i=0;i<list.size();i++){
						String MzId=((HashMap)list.get(i)).get("模组序ID").toString();
						String fenJh=((HashMap)list.get(i)).get("分解号").toString();
						String zaiJh=((HashMap)list.get(i)).get("载具序号").toString();
						err=line.setCarryAt(-2,GdId,MzId,fenJh,zaiJh);//A区&B区
					};list=null;
					//数据库配方表处理
					map.put("sql", "SELECT MAX(A.`工单序号`) AS '序号' FROM `工单表` AS A");
					List maxList=gdDao.getMaxGdxh(map);gdDao=null;
					int maxGdxh=(((HashMap)maxList.get(0)).get("序号").equals("")?0:Integer.parseInt(((HashMap)maxList.get(0)).get("序号").toString()))+1;maxList=null;
					map.put("sql", "UPDATE 工单表 SET 工单序号="+maxGdxh+" where ID='"+GdId+"'");
					yesNo=dao.gwGzUpdate(map);
					map.put("sql", "UPDATE 配方指令队列 SET 前升读标志=null,完成数量=0,ST读取标志=null,工单序号="+maxGdxh+" WHERE 工单ID='"+GdId+"' AND 装配区='"+map.get("line")+"'");
					yesNo=dao.gwGzUpdate(map);dao=null;GdId=null;
				};
			};map=null;
			result.put("plcDd", plcDd);
			result.put("update", update);
			result.put("result", yesNo);
			result.put("setCarryAt", err);err=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);result=null;
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		};
		return null;
	};

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
			HttpSession session = request.getSession();
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			JSONObject result=new JSONObject();
			boolean plcDd=false;
			CarryLine line=null;
			boolean yesNo=true;
			if(map.get("line").equals("1")){
				plcDd=PLC.getIntance().isStartDiaodu1();
				line=PLC.getIntance().line;
			}else{
				plcDd=PLC.getIntance().isStartDiaodu2();
				line=PLC.getIntance().line2;
			};
			if(!session.getAttribute("juese").equals("1")&&
				!session.getAttribute("juese").equals("4")){
				for(int i=0;i<15;i++){
					try{
						if(line.getCarry(i)!=null){
							yesNo=false;break;
						};
					}catch(Exception e){}
				};
			};
			if(plcDd&&yesNo==true){//当调度没有开启时执行此方法
				PLC.getIntance().reLoad(Integer.parseInt(map.get("line").toString()));
			};map=null;
			result.put("plcDd", plcDd);
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
			HttpSession session = request.getSession();
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			JSONObject result=new JSONObject();
			boolean plcDd=false;
			boolean yesNo=true;
			boolean update=true;
			CarryLine line=null;
			if(map.get("line").equals("1")){
				plcDd=PLC.getIntance().isStartDiaodu1();
				line=PLC.getIntance().line;
			}else{
				plcDd=PLC.getIntance().isStartDiaodu2();
				line=PLC.getIntance().line2;
			};
			result.put("plcDd", plcDd);
			if(plcDd==false){//false时是开启调度状态，返回状态
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print(result);
				response.getWriter().close();
				return null;
			}else if(!session.getAttribute("juese").equals("1")&&
					!session.getAttribute("juese").equals("4")){
				for(int i=0;i<15;i++){
					try{
						if(line.getCarry(i)!=null){
							update=false;break;
						};
					}catch(Exception e){}
				};
			};
			if(update==true){
				ApplicationContext context=GetApplicationContext.getContext(request);
				PLCDAO dao=(PLCDAO)context.getBean("pLCDAO");
				JSONArray array1=new JSONArray(map.get("update1").toString());
				String err="成功";
				if(array1.length()>0){
					for(int i=0;i<array1.length();i++){
						int yiBssx=Integer.parseInt(array1.getJSONObject(i).getString("yiBssx"));
						String GdId=array1.getJSONObject(i).getString("GdId");
						String MzId=array1.getJSONObject(i).getString("MzId");
						String fenJh=array1.getJSONObject(i).getString("fenJh");
						String zaiJh=array1.getJSONObject(i).getString("zaiJh");
						String zaiJydbz=array1.getJSONObject(i).getString("zaiJydbz");
						//异步输送线处理//A区//B区
						err=line.setCarryAt(yiBssx,GdId,MzId,fenJh,zaiJh);
						//数据库配方表处理
						String sql="update 配方指令队列 set 前升读标志=" +(zaiJydbz.equals("")?null:zaiJydbz)+" where 工单ID='"+GdId+"' and 模组序ID='"+MzId+"' and 分解号='"+fenJh+"' and 载具序号='"+zaiJh+"'";
						map.put("sql", sql);GdId=null;zaiJydbz=null;MzId=null;fenJh=null;zaiJh=null;sql=null;
						yesNo=dao.gwGzUpdate(map);
					};
				};array1=null;
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
						String sql="update 配方指令队列 set 完成数量=" +wanCsl+",ST读取标志=" +(stGwydbz.equals("")?null:stGwydbz)+" where 工单ID='"+GdId+"' and 模组序ID='"+MzId+"' and 分解号='"+fenJh+"' and 工位='"+gw+"' and 载具序号='"+zaiJh+"'";
						map.put("sql", sql);sql=null;GdId=null;MzId=null;fenJh=null;wanCsl=null;stGwydbz=null;zaiJh=null;
						yesNo=dao.gwGzUpdate(map);
					};
				};
				result.put("setCarryAt", err);err=null;
				array2=null;map=null;dao=null;context=null;
			};
			result.put("result", yesNo);
			result.put("update", update);
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
		}finally{}
		return null;
	}
}