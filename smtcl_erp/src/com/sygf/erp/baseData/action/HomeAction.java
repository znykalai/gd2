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
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sygf.erp.baseData.dao.HomeActionDAO;
import com.sygf.erp.util.GDFrame;
import com.sygf.erp.util.GetApplicationContext;
import com.sygf.erp.util.GetParam;

import alai.znyk.common.ClientSer;
import alai.znyk.plc.PLC;

public class HomeAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String operType = request.getParameter("operType");
			if (operType.equals("getHckState")){
				return getHckState(mapping, form, request, response);
			}else if (operType.equals("getGDFrame")){
				return getGDFrame(mapping, form, request, response);
			}else if(operType.equals("getHckButton")){
				return getHckButton(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 显示GDFrame 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getGDFrame(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			//show
			GDFrame.showFrame();
			return null;
		}catch (Exception e) {
			// TODO: handle exception
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
			//异步输送线上层
			ArrayList topArratList = new ArrayList();
			for(int i=0,j=0;i<15;i++){
				try{
					HashMap map = new HashMap();
					if(PLC.getIntance().line.getCarry(i)!=null){
						map.put(""+j+"","TOP-"+i+"ST");
						topArratList.add(map);
						j++;
					}
				}catch(Exception e){}
			}
			//异步输送线下层
			ArrayList bottomArratList = new ArrayList();
			for(int i=0,j=0;i<15;i++){
				try{
					HashMap map = new HashMap();
					if(PLC.getIntance().line2.getCarry(i)!=null){
						map.put(""+j+"","BOTTOM-"+i+"ST");
						bottomArratList.add(map);
						j++;
					}
				}catch(Exception e){}
			}
			//订单完成率
			list = dao.getGdWanChengLv();
			String gdWcl = list!=null&&list.size()>0?((HashMap)list.get(0)).get("工单完成率").toString():"0";
			//货位使用率
			list = dao.getHWshiYongLv();
			String hwSyl = list!=null&&list.size()>0?((HashMap)list.get(0)).get("货位使用率").toString():"0";
			result.put("hckTop", topArratList);
			result.put("hckTb", resultList);
			result.put("hckBottom", bottomArratList);
			result.put("gdWcl",gdWcl);
			result.put("hwSyl",hwSyl);
			System.err.println("home---定时刷新");
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
	 * 获取主页八个按钮状态以及启动 关闭
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getHckButton(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			JSONObject result = new JSONObject();
			if(map.get("type").equals("get")){
				result.put("A", PLC.getIntance().isStartDiaodu1());
				result.put("B", PLC.getIntance().isStartDiaodu2());
				System.out.println("result="+result);
			//A区,启动调度
			}else if(map.get("type").equals("top")){
				System.out.println("当前按钮是否开启中="+map.get("A"));
				if(map.get("A").equals("true")){
					PLC.getIntance().setDiaodu1(false);
					System.out.println("A区，关闭调度！");
				}else{
					PLC.getIntance().setDiaodu1(true);
					System.out.println("A区，开启调度!");
				}
				result.put("type",PLC.getIntance().isStartDiaodu1());
			//B区,启动调度
			}else if(map.get("type").equals("bottom")){
				System.out.println("当前按钮是否开启中="+map.get("B"));
				if(map.get("B").equals("true")){
					PLC.getIntance().setDiaodu2(false);
					System.out.println("B区，关闭调度！");
				}else{
					PLC.getIntance().setDiaodu2(true);
					System.out.println("B区，开启调度!");
				}
				result.put("type", PLC.getIntance().isStartDiaodu2());
			//A区,复位
			}else if(map.get("type").equals("fuwei_top")){
				result.put("type", ClientSer.getIntance().c_exeComment("",2,1));
			//A区,归零启动
			}else if(map.get("type").equals("guilingqidong_top")){
				result.put("type", ClientSer.getIntance().c_exeComment("",5,1));
			//A区,断点启动
			}else if(map.get("type").equals("duandianqidong_top")){
				result.put("type", ClientSer.getIntance().c_exeComment("",4,1));
			//B区,复位
			}else if(map.get("type").equals("fuwei_bottom")){
				result.put("type", ClientSer.getIntance().c_exeComment("",3,2));
			//B区,归零启动
			}else if(map.get("type").equals("guilingqidong_bottom")){
				result.put("type", ClientSer.getIntance().c_exeComment("",5,2));
			//B区,断点启动
			}else if(map.get("type").equals("duandianqidong_bottom")){
				result.put("type", ClientSer.getIntance().c_exeComment("",4,2));
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}