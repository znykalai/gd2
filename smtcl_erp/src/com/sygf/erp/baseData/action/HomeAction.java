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

import com.sygf.erp.baseData.dao.BaseDataDAO;
import com.sygf.erp.baseData.dao.HomeActionDAO;
import com.sygf.erp.util.GDFrame;
import com.sygf.erp.util.GetApplicationContext;
import com.sygf.erp.util.GetParam;

import alai.znyk.common.ClientSer;
import alai.znyk.common.SqlPro;
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
			}else if(operType.equals("getCKButton")){
				return getCKButton(mapping, form, request, response);
			}else if(operType.equals("getState")){
				return getState(mapping, form, request, response);
			}else if(operType.equals("getQX")){
				return getQX(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取权限
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getQX(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			HashMap map=new HashMap();
			String ID=session.getAttribute("juese").toString();
			map.put("sql", "SELECT a.角色名,a.角色功能,a.ID,a.序号,a.功能权限  FROM `角色` AS a WHERE a.ID='"+ID+"' ORDER BY a.`序号`");
			List list=dao.selectJues(map);
			String json="{物料:{新建:"+((HashMap)list.get(0)).get("功能权限")+","
						+ "保存:"+((HashMap)list.get(1)).get("功能权限")+","
						+ "删除:"+((HashMap)list.get(2)).get("功能权限")+"},"
						+"模组:{新建:"+((HashMap)list.get(3)).get("功能权限")+","
							+ "删除:"+((HashMap)list.get(4)).get("功能权限")+","
							+ "载具上调:"+((HashMap)list.get(5)).get("功能权限")+","
							+ "载具下调:"+((HashMap)list.get(6)).get("功能权限")+","
							+ "载具添加:"+((HashMap)list.get(7)).get("功能权限")+","
							+ "载具保存:"+((HashMap)list.get(8)).get("功能权限")+","
							+ "载具删除:"+((HashMap)list.get(9)).get("功能权限")+","
							+ "指令上调:"+((HashMap)list.get(10)).get("功能权限")+","
							+ "指令下调:"+((HashMap)list.get(11)).get("功能权限")+","
							+ "指令添加:"+((HashMap)list.get(12)).get("功能权限")+","
							+ "指令保存:"+((HashMap)list.get(13)).get("功能权限")+","
							+ "指令删除:"+((HashMap)list.get(14)).get("功能权限")+"},"
						+"pack:{新建:"+((HashMap)list.get(15)).get("功能权限")+","
							+ "删除:"+((HashMap)list.get(16)).get("功能权限")+","
							+ "保存:"+((HashMap)list.get(17)).get("功能权限")+","
							+ "pack行上调:"+((HashMap)list.get(18)).get("功能权限")+","
							+ "pack行下调:"+((HashMap)list.get(19)).get("功能权限")+","
							+ "pack行添加:"+((HashMap)list.get(20)).get("功能权限")+","
							+ "pack行删除:"+((HashMap)list.get(21)).get("功能权限")+"},"
						+"账户设置:{角色新建:"+((HashMap)list.get(22)).get("功能权限")+","
							+ "角色保存:"+((HashMap)list.get(23)).get("功能权限")+","
							+ "角色删除:"+((HashMap)list.get(24)).get("功能权限")+","
							+ "用户新建:"+((HashMap)list.get(25)).get("功能权限")+","
							+ "用户保存:"+((HashMap)list.get(26)).get("功能权限")+","
							+ "用户删除:"+((HashMap)list.get(27)).get("功能权限")+"},"
						+"发送命令:"+((HashMap)list.get(28)).get("功能权限")+","
						+"PLC:"+((HashMap)list.get(29)).get("功能权限")+","
						+"订单调度:{下载:"+((HashMap)list.get(30)).get("功能权限")+","
							+ "上调:"+((HashMap)list.get(31)).get("功能权限")+","
							+ "下调:"+((HashMap)list.get(32)).get("功能权限")+","
							+ "选择分解:"+((HashMap)list.get(33)).get("功能权限")+","
							+ "分解全部:"+((HashMap)list.get(34)).get("功能权限")+","
							+ "删除:"+((HashMap)list.get(35)).get("功能权限")+"},"
						+"启动调度:"+((HashMap)list.get(36)).get("功能权限")+","
						+"复位:"+((HashMap)list.get(37)).get("功能权限")+","
						+"归零启动:"+((HashMap)list.get(38)).get("功能权限")+","
						+"断点启动:"+((HashMap)list.get(39)).get("功能权限")+","
						+"不检测数量:"+((HashMap)list.get(40)).get("功能权限")+","
						+"不检测动作:"+((HashMap)list.get(41)).get("功能权限")+","
						+"RFD自动读取:"+((HashMap)list.get(42)).get("功能权限")+","
						+"启动库指令:"+((HashMap)list.get(43)).get("功能权限")+"}";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(json);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		};
		return null;
	};

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
			dao=null;context=null;list=null;topArratList=null;
			resultList=null;bottomArratList=null;gdWcl=null;hwSyl=null;
//			System.err.println("home---定时刷新");
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
//				System.out.println("result="+result);
			//A区,启动调度
			}else if(map.get("type").equals("top")){
//				System.out.println("当前按钮是否开启中="+map.get("A"));
				if(map.get("A").equals("true")){
					PLC.getIntance().setDiaodu1(false);
//					System.out.println("A区，关闭调度！");
				}else{
					PLC.getIntance().setDiaodu1(true);
//					System.out.println("A区，开启调度!");
				}
				result.put("type",PLC.getIntance().isStartDiaodu1());
			//B区,启动调度
			}else if(map.get("type").equals("bottom")){
//				System.out.println("当前按钮是否开启中="+map.get("B"));
				if(map.get("B").equals("true")){
					PLC.getIntance().setDiaodu2(false);
//					System.out.println("B区，关闭调度！");
				}else{
					PLC.getIntance().setDiaodu2(true);
//					System.out.println("B区，开启调度!");
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
	 * 获取操控按钮状态
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getCKButton(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			JSONObject result = new JSONObject();
			if(map.get("type").equals("get")){
				result.put("A", PLC.getIntance().is不检测取料数量());
				result.put("B", PLC.getIntance().is不检测动作完成());
				result.put("C", SqlPro.autoRFIDup);
				result.put("D", false);
			}else if(map.get("type").equals("A")){
				if(map.get("buttonA").equals("true")){
					PLC.getIntance().set不检测取料数量(false);
				}else{
					PLC.getIntance().set不检测取料数量(true);
				};
				result.put("type", PLC.getIntance().is不检测取料数量());
			}else if(map.get("type").equals("B")){
				if(map.get("buttonB").equals("true")){
					PLC.getIntance().set不检测动作完成(false);
				}else{
					PLC.getIntance().set不检测动作完成(true);
				};
				result.put("type", PLC.getIntance().is不检测动作完成());
			}else if(map.get("type").equals("C")){
				if(map.get("buttonC").equals("true")){
					SqlPro.autoRFIDup=false;
				}else{
					SqlPro.autoRFIDup=true;
				};
				result.put("type", SqlPro.autoRFIDup);
			}else{
				result.put("type", true);
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
	 * 获取急停状态
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			String result="0";
			if(map.get("type").equals("true")){
				System.out.println("急停");
				ClientSer.getIntance().c_exeComment("", 1,1);
			}else if(map.get("type").equals("false")){
				System.out.println("放行");
				ClientSer.getIntance().c_exeComment("", 6,1);
			};
			result=ClientSer.getIntance().getState(2);map=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}