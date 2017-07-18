package com.sygf.erp.baseData.action;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.sygf.erp.baseData.dao.OrderOperactionDAO;
import com.sygf.erp.util.GDFrame;
import com.sygf.erp.util.GetApplicationContext;
import com.sygf.erp.util.GetError;
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
			}else if(operType.equals("stop")){
				return stop(mapping, form, request, response);
			}else if(operType.equals("getDltk")){
				return getDltk(mapping, form, request, response);
			}else if(operType.equals("delDltk")){
				return delDltk(mapping, form, request, response);
			}else if(operType.equals("getError")){
				return getError(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取异常记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getError(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			int conA=Integer.parseInt(ClientSer.getIntance().getState(12).toString());
			int conB=Integer.parseInt(ClientSer.getIntance().getState(13).toString());
			String htmlA=GetError.getInstace().getArm(conA,"A");
			String htmlB=GetError.getInstace().getArm(conB,"B");
			JSONObject result = new JSONObject();
			if(htmlA==""&&htmlB==""){result.put("error", "");}else{
				result.put("error", "<span style='color:red;font-size:18px;'>"+htmlA+"\n\n"+htmlB+"</span>");
			};htmlA=null;htmlB=null;
			result.put("th",PLC.getIntance().getTH().size());
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);result=null;
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除大立体库请求
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	private ActionForward delDltk(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			ApplicationContext context = GetApplicationContext.getContext(request);
			OrderOperactionDAO dao = (OrderOperactionDAO)context.getBean("orderOperactionDAO");
			HashMap map=new HashMap();
			map.put("sql", "delete from `大库请求` where ID="+request.getParameter("eid").toString());
			dao.removeAll(map);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("删除成功!");
			response.getWriter().close();
		}catch (Exception e) {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("删除失败!");
			response.getWriter().close();
			e.printStackTrace();
		};
		return null;
	};
	/**
	 * 查询大立体库数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getDltk(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			ApplicationContext context = GetApplicationContext.getContext(request);
			HomeActionDAO dao = (HomeActionDAO)context.getBean("homeActionDAO");
			JSONObject result = new JSONObject();
			List list = dao.getDltk();
			ArrayList dltkList = new ArrayList();
			if(list!=null&&list.size()>0){
				int i=0;
				while(i<list.size()){
					HashMap map = new HashMap();
					map.put("id", ((HashMap)list.get(i)).get("id"));
					map.put("tpCode", ((HashMap)list.get(i)).get("托盘编码"));
					map.put("wlCode", ((HashMap)list.get(i)).get("物料"));
					map.put("number", ((HashMap)list.get(i)).get("数量"));
					map.put("fangXiang", ((HashMap)list.get(i)).get("方向"));
					dltkList.add(map);
					i++;
				};
			};
			result.put("data", dltkList);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		};
		return null;
	};
	/**
	 * 关机按钮操作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward stop(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			String result=PLC.getIntance().stop();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		};
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
			String json="{物料:"+((HashMap)list.get(0)).get("功能权限")+","
						+"模组:"+((HashMap)list.get(1)).get("功能权限")+","
						+"pack:"+((HashMap)list.get(2)).get("功能权限")+","
						+"账户设置:"+((HashMap)list.get(3)).get("功能权限")+","
						+"发送命令:"+((HashMap)list.get(4)).get("功能权限")+","
						+"PLC:"+((HashMap)list.get(5)).get("功能权限")+","
						+"订单维护:"+((HashMap)list.get(6)).get("功能权限")+","
						+"订单调度:"+((HashMap)list.get(7)).get("功能权限")+","
						+"启动调度:"+((HashMap)list.get(8)).get("功能权限")+","
						+"复位:"+((HashMap)list.get(9)).get("功能权限")+","
						+"归零启动:"+((HashMap)list.get(10)).get("功能权限")+","
						+"A区自动:"+((HashMap)list.get(11)).get("功能权限")+","
						+"B区自动:"+((HashMap)list.get(12)).get("功能权限")+","
						+"不检测数量:"+((HashMap)list.get(13)).get("功能权限")+","
						+"不检测动作:"+((HashMap)list.get(14)).get("功能权限")+","
						+"RFD自动读取:"+((HashMap)list.get(15)).get("功能权限")+","
						+"启动库指令:"+((HashMap)list.get(16)).get("功能权限")+","
						+"急停:"+((HashMap)list.get(17)).get("功能权限")+","
						+"RFID绑定:"+((HashMap)list.get(18)).get("功能权限")+"}";
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
			HomeActionDAO dao = (HomeActionDAO)context.getBean("homeActionDAO");context=null;
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
			};list=null;
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
			list = dao.getGdWanChengLv();dao=null;
			String gdWcl = list!=null&&list.size()>0?((HashMap)list.get(0)).get("工单完成率").toString():"0";list=null;
			//货位使用率
			//list = dao.getHWshiYongLv();
			//String hwSyl = list!=null&&list.size()>0?((HashMap)list.get(0)).get("货位使用率").toString():"0";
			//模组 PACK 完成数量统计；
			String packMzTongJi="<span style='color:rgb(75,176,249);'>装配区A</br>";
			List packMzTongJi1=PLC.getIntance().get统计(1);
			List packMzTongJi2=PLC.getIntance().get统计(2);
			for(int i=0;i<packMzTongJi1.size();i++){
				packMzTongJi+=packMzTongJi1.get(i)+"</br>";
			};packMzTongJi1=null;
			packMzTongJi+="</span></br><span style='color:rgb(146,208,80);'>装配区B</br>";
			for(int i=0;i<packMzTongJi2.size();i++){
				packMzTongJi+=packMzTongJi2.get(i)+"</br>";
			};packMzTongJi2=null;
			packMzTongJi+="</span>";
			//返回参数
			result.put("hckTop", topArratList);topArratList=null;
			result.put("hckTb", resultList);resultList=null;
			result.put("hckBottom", bottomArratList);bottomArratList=null;
			result.put("gdWcl",gdWcl);gdWcl=null;
			result.put("packMzTongJi", packMzTongJi);packMzTongJi=null;
			result.put("plcType1",PLC.getIntance().getConcent(1));/**获取PLC连接状态A**/
			result.put("plcType2",PLC.getIntance().getConcent(2));/**获取PLC连接状态B**/
			//result.put("hwSyl",hwSyl);//hwSyl=null;
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
			//A区,启动调度
			}else if(map.get("type").equals("top")){
				if(map.get("A").equals("true")){
					PLC.getIntance().setDiaodu1(false);
				}else{
					PLC.getIntance().setDiaodu1(true);
				}
				result.put("type",PLC.getIntance().isStartDiaodu1());
			//B区,启动调度
			}else if(map.get("type").equals("bottom")){
				if(map.get("B").equals("true")){
					PLC.getIntance().setDiaodu2(false);
				}else{
					PLC.getIntance().setDiaodu2(true);
				}
				result.put("type", PLC.getIntance().isStartDiaodu2());
			//A区,复位
			}else if(map.get("type").equals("fuwei_top")){
				result.put("type", ClientSer.getIntance().c_exeComment("",2,1));
			//A区,归零启动
			}else if(map.get("type").equals("guilingqidong_top")){
				result.put("type", ClientSer.getIntance().c_exeComment("",5,1));
//			//A区,断点启动
//			}else if(map.get("type").equals("duandianqidong_top")){
//				result.put("type", ClientSer.getIntance().c_exeComment("",4,1));
			//B区,复位
			}else if(map.get("type").equals("fuwei_bottom")){
				result.put("type", ClientSer.getIntance().c_exeComment("",3,2));
			//B区,归零启动
			}else if(map.get("type").equals("guilingqidong_bottom")){
				result.put("type", ClientSer.getIntance().c_exeComment("",5,2));
//			//B区,断点启动
//			}else if(map.get("type").equals("duandianqidong_bottom")){
//				result.put("type", ClientSer.getIntance().c_exeComment("",4,2));
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
				result.put("D", SqlPro.is大库调度);
				result.put("E", PLC.getIntance().A区输送线自动请求打开);
				result.put("F", PLC.getIntance().B区输送线自动请求打开);
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
			}else if(map.get("type").equals("D")){
				if(map.get("buttonD").equals("true")){
					SqlPro.is大库调度=false;
				}else{
					SqlPro.is大库调度=true;
				};
				result.put("type", SqlPro.is大库调度);
			}else if(map.get("type").equals("E")){
				if(map.get("buttonE").equals("true")){
					PLC.getIntance().A区输送线自动请求打开=false;
				}else{
					PLC.getIntance().A区输送线自动请求打开=true;
				};
				result.put("type", PLC.getIntance().A区输送线自动请求打开);
			}else if(map.get("type").equals("F")){
				if(map.get("buttonF").equals("true")){
					PLC.getIntance().B区输送线自动请求打开=false;
				}else{
					PLC.getIntance().B区输送线自动请求打开=true;
				};
				result.put("type", PLC.getIntance().B区输送线自动请求打开);
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