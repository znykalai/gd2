package com.sygf.erp.baseData.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sygf.erp.baseData.dao.HomeActionDAO;

import alai.znyk.plc.PLC;

public class HomeAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String operType = request.getParameter("operType");
			if (operType.equals("getHckState")){
				return getHckState(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取换成库 状态
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getHckState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			//HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			HomeActionDAO dao = (HomeActionDAO)context.getBean("homeActionDAO");
			JSONObject result = new JSONObject();
			//获取换成库 输送线状态
			List list = dao.getHckState();
			ArrayList resultList = new ArrayList();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap map = new HashMap();
					map.put(""+i+"", ((HashMap)list.get(i)).get("货位序号"));
					resultList.add(map);
				}
			}
			//异步输送线上层
			ArrayList topArratList = new ArrayList();
			for(int i=0;i<15;i++){
				try{
					HashMap map = new HashMap();
					if(PLC.getIntance().line.getCarry(i)!=null){
						map.put(""+i+"","TOP-"+i+"ST");
						topArratList.add(map);
					}
				}catch(Exception e){}
			}
			//异步输送线下层
			ArrayList bottomArratList = new ArrayList();
			for(int i=0;i<15;i++){
				try{
					HashMap map = new HashMap();
					if(PLC.getIntance().line2.getCarry(i)!=null){
						map.put(""+i+"","BOTTOM-"+i+"ST");
						bottomArratList.add(map);
					}
				}catch(Exception e){}
			}
			list = dao.getGdWanChengLv();
			result.put("hckTop", topArratList);
			result.put("hckTb", resultList);
			result.put("hckBottom", bottomArratList);
			result.put("gdWcl", list!=null&&list.size()>0?((HashMap)list.get(0)).get("工单完成率"):"");
			
			System.out.println("result="+result);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}