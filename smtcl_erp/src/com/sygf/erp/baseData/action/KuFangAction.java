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

import com.sygf.erp.baseData.dao.HomeActionDAO;
import com.sygf.erp.baseData.dao.KuFangActionDAO;
import com.sygf.erp.util.GetApplicationContext;

public class KuFangAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String operType = request.getParameter("operType");
			if (operType.equals("getHckzddl")){
				return getHckzddl(mapping, form, request, response);
			}else if (operType.equals("getKuCun")){
				return getKuCun(mapping, form, request, response);
			}else if (operType.equals("getHw")){
				return getHw(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取緩存库 指定队列
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getHckzddl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			KuFangActionDAO dao = (KuFangActionDAO)context.getBean("kuFangActionDAO");
			JSONObject result = new JSONObject();
			//立体库动作指令
			List list = dao.getActionCommand();
			ArrayList actionCommandList = new ArrayList();
			if(list!=null&&list.size()>0){
				int i=0;
				while(i<list.size()){
					HashMap map = new HashMap();
					map.put("idEvent", ((HashMap)list.get(i)).get("idEvent"));
					map.put("dongzuo", ((HashMap)list.get(i)).get("动作"));
					map.put("tp_code", ((HashMap)list.get(i)).get("托盘编号"));
					map.put("zhuangtai", ((HashMap)list.get(i)).get("状态"));
					map.put("laiyuanhuoweihao", ((HashMap)list.get(i)).get("来源货位号"));
					map.put("fanghuihuoweihao", ((HashMap)list.get(i)).get("放回货位号"));
					map.put("shifouhuidaku", ((HashMap)list.get(i)).get("是否回大库"));
					map.put("fasongshijian", ((HashMap)list.get(i)).get("发送时间"));
					map.put("wanchengshijian", ((HashMap)list.get(i)).get("完成时间"));
					actionCommandList.add(map);
					i++;
				}
			}
			result.put("data", actionCommandList);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{}
		return null;
	}
	
	/**
	 * 库存
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getKuCun(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			KuFangActionDAO dao = (KuFangActionDAO)context.getBean("kuFangActionDAO");
			JSONObject result = new JSONObject();
			//立体库动作指令
			List list = dao.getKuCunList();
			ArrayList kuCunList = new ArrayList();
			if(list!=null&&list.size()>0){
				int i=0;
				while(i<list.size()){
					HashMap map = new HashMap();
					map.put("tp_code", ((HashMap)list.get(i)).get("托盘编号"));
					map.put("wl_code", ((HashMap)list.get(i)).get("物料"));
					map.put("number", ((HashMap)list.get(i)).get("数量"));
					map.put("huoweihao", ((HashMap)list.get(i)).get("货位号"));
					map.put("fangxiang", ((HashMap)list.get(i)).get("方向"));
					kuCunList.add(map);
					i++;
				}
			}
			result.put("data", kuCunList);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取货位
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getHw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			ApplicationContext context = GetApplicationContext.getContext(request);
			HomeActionDAO dao = (HomeActionDAO)context.getBean("homeActionDAO");
			JSONObject result = new JSONObject();
			//获取缓存库 输送线状态
			List list = dao.getHckState();
			ArrayList resultList = new ArrayList();
			if(list!=null&&list.size()>0){
				int i=0;
				while(i<list.size()){
					HashMap map = new HashMap();
					map.put(""+i+"", ((HashMap)list.get(i)).get("货位序号"));
					resultList.add(map);
					i++;
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