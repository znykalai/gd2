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

import com.sygf.erp.baseData.dao.BaseDataDAO;
import com.sygf.erp.baseData.dao.HomeActionDAO;
import com.sygf.erp.baseData.dao.KuFangActionDAO;
import com.sygf.erp.util.GetApplicationContext;
import com.sygf.erp.util.GetParam;

import alai.znyk.common.ClientSer;
import alai.znyk.server.SqlTool;

public class KuFangAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String operType = request.getParameter("operType");
			if (operType.equals("getHckzddl")){
				return getHckzddl(mapping, form, request, response);
			}else if (operType.equals("getKuCun")){
				return getKuCun(mapping, form, request, response);
//			}else if (operType.equals("getHw")){
//				return getHw(mapping, form, request, response);
			}else if (operType.equals("getTp")){
				return getTp(mapping, form, request, response);
			}else if (operType.equals("fsMingLing")){
				return fsMingLing(mapping, form, request, response);
			}else if (operType.equals("delEvent")){
				return delEvent(mapping, form, request, response);
			}else if (operType.equals("getFx")){
				return getFx(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取当前用户库房操作方向
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getFx(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			HashMap map=new HashMap();
			map.put("sql", "SELECT DISTINCT(a.`账户`) AS `账户`,a.`密码`,a.`角色`,a.`方向`,b.`角色名` FROM `账户` AS a LEFT JOIN `角色` AS b ON a.`角色`=b.ID WHERE a.`账户`='"+session.getAttribute("username").toString()+"' ORDER BY a.`角色`");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(((HashMap)dao.selectYongHJues(map).get(0)).get("方向"));
			map=null;dao=null;context=null;session=null;
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除事件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward delEvent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context = GetApplicationContext.getContext(request);
			KuFangActionDAO dao = (KuFangActionDAO)context.getBean("kuFangActionDAO");
			map.put("sql", "DELETE FROM `立库动作指令` WHERE idEvent="+map.get("id"));
			String result=dao.removeAll(map);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
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
//			System.err.println("库房操作---定时刷新");
//			System.out.println(request.getRemoteAddr()+"===");
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
//	/**
//	 * 获取货位
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	private ActionForward getHw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) {
//		try{
//			request.setCharacterEncoding("utf-8");
//			response.setCharacterEncoding("utf-8");
//			ApplicationContext context = GetApplicationContext.getContext(request);
//			HomeActionDAO dao = (HomeActionDAO)context.getBean("homeActionDAO");
//			JSONObject result = new JSONObject();
//			//获取缓存库 输送线状态
//			List list = dao.getHckState();
//			ArrayList resultList = new ArrayList();
//			if(list!=null&&list.size()>0){
//				int i=0;
//				while(i<list.size()){
//					HashMap map = new HashMap();
//					map.put(""+i+"", ((HashMap)list.get(i)).get("货位序号"));
//					resultList.add(map);
//					i++;
//				}
//			}
//			result.put("hckTb", resultList);
//			response.setContentType("text/html;charset=utf-8");
//			response.getWriter().print(result);
//			response.getWriter().close();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	/**
	 * 获取托盘编号
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getTp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			JSONObject mes=new JSONObject();
			//方向
			int fx=Integer.parseInt(request.getParameter("fx").toString());
			//获取托盘编码
			String tp_code = ClientSer.getIntance().ReadFromRffid("",fx);
			mes.put("tp_code", tp_code);tp_code=null;
			mes.put("wl_code", "");
			if(!mes.getString("tp_code").equals("")){
				HashMap map=new HashMap();
				map.put("sql", " WHERE a.托盘编号='"+mes.getString("tp_code")+"'");
				ApplicationContext context=GetApplicationContext.getContext(request);
				KuFangActionDAO dao=(KuFangActionDAO)context.getBean("kuFangActionDAO");context=null;
				List list=dao.getRfidWl(map);map=null;dao=null;
				if(list!=null&&list.size()>0){
					mes.put("wl_code", ((HashMap)list.get(0)).get("物料编码"));
				};list=null;
			};
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(mes);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 发送命令
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward fsMingLing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context = GetApplicationContext.getContext(request);
			KuFangActionDAO dao = (KuFangActionDAO)context.getBean("kuFangActionDAO");
//			System.out.println("map="+map);
			String result = null;
			if(map.get("title").equals("上货")){
				result = SqlTool.manUpPallet(map.get("tp").toString(),
						map.get("wuliao").toString(),Integer.parseInt(map.get("num").toString()),
						map.get("toID").toString(),map.get("machineID").toString());
			}else{
				List hwList = dao.getTpMachineID(map);
//				System.out.println("hwList="+hwList);
				if(hwList!=null&&hwList.size()>0){
					String tp = ((HashMap)hwList.get(0)).get("托盘编号").toString();
					String machineID = ((HashMap)hwList.get(0)).get("方向").toString();
					result = SqlTool.add动作指令(tp,map.get("fromID").toString(),
							map.get("toID").toString(),map.get("type").toString(),
							Integer.parseInt(map.get("todaku").toString()),machineID);
				}else{
					result = "此货位没有托盘！";
				}
				hwList=null;
			}
			dao=null;context=null;map=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}