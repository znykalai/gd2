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
import com.sygf.erp.util.GetParam;

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
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			HomeActionDAO dao = (HomeActionDAO)context.getBean("homeActionDAO");
			ArrayList resultList = new ArrayList();
			JSONObject result = new JSONObject();
			//获取换成库 输送线状态
			List list = dao.getHckState();
			System.out.println("list="+list);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					map.put("'"+i+"'", ((HashMap)list.get(i)).get("货位序号"));
					resultList.add(map);
				}
			}
			result.put("hckTb", resultList);
			
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}