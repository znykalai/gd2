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
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
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
			String operType = request.getParameter("operType");
			if (operType.equals("getGw")){
				return getGw(mapping, form, request, response);
			}else if (operType.equals("updateGw")){
				return updateGw(mapping, form, request, response);
			}
		} catch (Exception e) {
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
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			JSONObject result = new JSONObject();
			ArrayList mapList = new ArrayList();
			//A区工位
			int j = 0;
			Vector<STContent> A = PLC.getIntance().STC1;
			while(j < 16){
				ArrayList list = new ArrayList();
				HashMap mapA=new HashMap();
				ST_Father duilie1 = A.get(j).firstST;
				ST_Father duilie2 = A.get(j).secondST;
				Vector<HashMap> v=new Vector<HashMap>();
				Hashtable temp=duilie1.getMap();
				Hashtable temp2=duilie2.getMap();
				Enumeration keys=temp.keys();
				while(keys.hasMoreElements()){
					HashMap map = new HashMap();
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
			}
			//A区输送线
			CarryLine ss = PLC.getIntance().line;
			ArrayList sszList = new ArrayList();
			for(int i=0;i<15;i++){
				try{
					HashMap map = new HashMap();
					map.put(""+i+"", ss.getCarry(i).get载具序号()+"-"+ss.getCarry(i).get工位());
					sszList.add(map);
					map=null;
				}catch(Exception e){}
			}
			result.put("data", mapList);
			result.put("A_SSX", sszList);
			ss=null;sszList=null;mapList=null;
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
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context = GetApplicationContext.getContext(request);
			String result=null;
			System.out.println("map="+map);
			//A区
			if(map.get("gwType").equals("A")){
				Vector<STContent> A = PLC.getIntance().STC1;
				//i= 当前第几个工位
				int i = Integer.parseInt(map.get("gw").toString());
				//map.get("cm") = 当前工位下的第几个队列  firstST=1,secondST=2,
				if(map.get("cm").equals("firstST")){
					result = A.get(i).firstST.setValueByName(map.get("name").toString(), 
							map.get("value").toString(), map.get("oldValue").toString());
				}
				A=null;i=0;
			}
			//B区
			else{
				
			}
			System.out.println("result="+result);
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