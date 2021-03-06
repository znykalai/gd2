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

import com.sygf.erp.baseData.dao.BaseDataDAO;
import com.sygf.erp.baseData.dao.OrderOperactionDAO;
import com.sygf.erp.util.GetApplicationContext;
import com.sygf.erp.util.GetParam;

import alai.znyk.plc.Carry;
import alai.znyk.plc.CarryLine;
import alai.znyk.plc.PLC;

public class OrderOperAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String operType=request.getParameter("operType");
			if (operType.equals("getDdList")){
				return getDdList(mapping, form, request, response);
			}else if (operType.equals("getZlmzList")){
				return getZlmzList(mapping, form, request, response);
			}else if (operType.equals("getZlpfList")){
				return getZlpfList(mapping, form, request, response);
			}else if (operType.equals("fenjieRadioBtn")){
				return fenjieRadioBtn(mapping, form, request, response);
			}else if (operType.equals("fenjieAllBtn")){
				return fenjieAllBtn(mapping, form, request, response);
			}else if (operType.equals("downloadBtn")){
				return downloadBtn(mapping, form, request, response);
			}else if (operType.equals("delBtn")){
				return delBtn(mapping, form, request, response);
			}else if (operType.equals("upGdBtn")){
				return upGdBtn(mapping, form, request, response);
			}else if (operType.equals("bomGdBtn")){
				return bomGdBtn(mapping, form, request, response);
			}else if(operType.equals("getPacklist")){
				return getPacklist(mapping, form, request, response);
			}else if(operType.equals("orderSave")){
				return orderSave(mapping, form, request, response);
			}else if(operType.equals("getGdDownload")){
				return getGdDownload(mapping, form, request, response);
			}else if(operType.equals("orderdelete")){
				return orderdelete(mapping, form, request, response);
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 订单维护-新增&修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward orderSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "utf-8", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			OrderOperactionDAO dao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");
			JSONObject head=new JSONObject(map.get("head").toString());
			JSONObject result=new JSONObject();
			if(head.get("id").equals("")){//新增
				map.put("sql", "insert into `工单下载`(`工单号`,`pack编码`,`工单数量`,`装配区`,`释放否`)" +
						"values(" +
						"'"+head.get("订单号").toString()+"'," +
						"'"+head.get("pack编码").toString()+"'," +
						"'"+head.get("订单数量").toString()+"'," +
						"'"+head.get("装配区").toString()+"'," +
						"'"+head.get("释放否").toString()+"')");
				result.put("result", dao.saveAll(map)?"保存成功！":"保存失败！");//保存	
				map.put("sql","SELECT MAX(a.ID)AS ID FROM `工单下载` a");//获取工单ID的 SQL
				result.put("id", ((HashMap)dao.getGdxzId(map).get(0)).get("ID"));//获取工单下载表ID
			}else{//修改
				map.put("sql", "update 工单下载 set "
						+ "工单号='"+head.get("订单号").toString()+"',"
						+ "pack编码='"+head.get("pack编码").toString()+"',"
						+ "工单数量='"+head.get("订单数量").toString()+"',"
						+ "装配区='"+head.get("装配区").toString()+"',"
						+ "释放否='"+head.get("释放否").toString()+"' where ID="+head.get("id"));
				result.put("result", dao.saveAll(map)?"保存成功！":"保存失败！");//保存
				result.put("id", head.get("id"));
			};
			head=null;context=null;dao=null;map=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
        };
		return null;
	};
	/**
	 * 订单维护-删除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward orderdelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "utf-8", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			OrderOperactionDAO dao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");
			JSONObject result=new JSONObject();
			if(!map.get("id").equals("")){
				String sql="delete from `工单下载` where ID=" +map.get("id");
				map.put("sql", sql);sql=null;
				result.put("result", dao.saveAll(map));//删除
			};
			map=null;context=null;dao=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
        };
		return null;
	};
						
	/**
	 * pack列表获取
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getPacklist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "utf-8", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			BaseDataDAO dao=(BaseDataDAO)context.getBean("baseDataDAO");
			ArrayList result=new ArrayList();
			String sql="select * from `pack题头`";
			map.put("sql", sql);sql=null;
			List list=dao.getHeadList(map);
			for(int i=0;i<list.size();i++){
				HashMap mapPara=new HashMap();
				mapPara.put("'pack_bianma'", "'"+((HashMap)list.get(i)).get("pack编码")+"'");
				mapPara.put("'pack_leixing'", "'"+((HashMap)list.get(i)).get("pack类型")+"'");
				result.add(mapPara);mapPara=null;
			};list=null;map=null;context=null;dao=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		};
		return null;
	}
	/**
	 * 工单下载表获取
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getGdDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "utf-8", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			OrderOperactionDAO dao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");
			ArrayList result=new ArrayList();
			String sql="select a.* from `工单下载` as a";
			map.put("sql", sql);sql=null;
			List list=dao.getGdDownload(map);
			for(int i=0;i<list.size();i++){
				HashMap mapPara=new HashMap();
			    mapPara.put("'order_id'", "'"+((HashMap)list.get(i)).get("ID")+"'");
				mapPara.put("'order_code'", "'"+((HashMap)list.get(i)).get("工单号")+"'");
				mapPara.put("'pack_code'", "'"+((HashMap)list.get(i)).get("pack编码")+"'");
				mapPara.put("'order_number'", "'"+((HashMap)list.get(i)).get("工单数量")+"'");
				mapPara.put("'assemble_area_id'", "'"+((HashMap)list.get(i)).get("装配区")+"'");
				mapPara.put("'chuansong_id'", "'"+((HashMap)list.get(i)).get("传送否")+"'");
			    mapPara.put("'shifang_id'", "'"+((HashMap)list.get(i)).get("释放否")+"'");
				result.add(mapPara);mapPara=null;
			};list=null;map=null;context=null;dao=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		};
		return null;
	}
	/**
	 * 获取订单list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getDdList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			OrderOperactionDAO dao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");
			ArrayList result=new ArrayList();
			String sql=" WHERE `工单表`.`状态` IS NULL AND ";
			if(!map.get("getGdId").equals("")){
				sql+=" `工单表`.`工单号`='"+map.get("getGdId")+"' AND ";
			};
			if(!map.get("getGdfenjieriqi").equals("")){
				sql+=" `工单表`.`分解日期` like '%"+map.get("getGdfenjieriqi")+"%' AND ";
			};
			if(!map.get("getPackeCode").equals("")){
				sql+=" `工单表`.`pack编码`='"+map.get("getPackeCode")+"' AND ";
			};
			//如果是系统管理员则不判断AB区
			if(session.getAttribute("username")!=null&&
				session.getAttribute("fangxiang")!=null&&
				!session.getAttribute("username").equals("admin")){
					sql+=" `工单表`.`装配区`='"+session.getAttribute("fangxiang")+"' AND ";
			};
			if(sql.endsWith("AND ")){sql=sql.substring(0,sql.length()-4);};
			map.put("sql", sql);sql=null;
			List list=dao.getDdList(map);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap mapPara=new HashMap();
					String gdwcl=((HashMap)list.get(i)).get("工单完成率").toString();
					String start_type="初始化";
					if(gdwcl.equals("100.0000")){
						start_type="已完成";
					}else{
						map.put("sql", "SELECT `配方指令队列`.ID FROM `配方指令队列` WHERE `配方指令队列`.`工单ID`='"+((HashMap)list.get(i)).get("ID")+"' AND " +
								"((`配方指令队列`.`前升读标志` IS NOT NULL OR `配方指令队列`.`前升读标志`<>'') OR " +
								"`配方指令队列`.`ST读取标志` IS NOT NULL OR `配方指令队列`.`ST读取标志`<>'')");
						List zlList=dao.getGdtype(map);
						if(zlList!=null&&zlList.size()>0){
							start_type="正在处理";
						}else{
							map.put("sql", "SELECT `配方指令队列`.ID FROM `配方指令队列` WHERE `配方指令队列`.`工单ID`='"+((HashMap)list.get(i)).get("ID")+"' AND " +
									"((`配方指令队列`.`前升读标志` IS NULL OR `配方指令队列`.`前升读标志`='') OR " +
									"`配方指令队列`.`ST读取标志` IS NULL OR `配方指令队列`.`ST读取标志`='')");
							zlList=dao.getGdtype(map);
							if(zlList!=null&&zlList.size()>0){
								start_type="已分解";
							}
						};zlList=null;
					};
					mapPara.put("'id'", "'"+((HashMap)list.get(i)).get("ID")+"'");
					mapPara.put("'dd_zhuangtai'", "'"+start_type+"'");start_type=null;
					mapPara.put("'dd_code'", "'"+((HashMap)list.get(i)).get("工单号")+"'");
					mapPara.put("'dd_xuhao'", "'"+((HashMap)list.get(i)).get("工单序号")+"'");
					mapPara.put("'pack_code'", "'"+((HashMap)list.get(i)).get("pack编码")+"'");
					mapPara.put("'pack_leixing'", "'"+((HashMap)list.get(i)).get("pack类型")+"'");
					mapPara.put("'dd_zhuangpeiqu'", "'"+((HashMap)list.get(i)).get("装配区")+"'");
					mapPara.put("'dd_jihuashuliang'", "'"+((HashMap)list.get(i)).get("工单数量")+"'");
					mapPara.put("'dd_fenjieriqi'", "'"+((HashMap)list.get(i)).get("分解日期")+"'");
					mapPara.put("'dd_jindu'", "'"+gdwcl+"'");gdwcl=null;
					result.add(mapPara);mapPara=null;
				}
			};
			map=null;list=null;dao=null;context=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
			response.getWriter().close();result=null;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 指令表-模组显示
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getZlmzList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			OrderOperactionDAO dao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");
			ArrayList result=new ArrayList();
			List list=dao.getZlmzList(map);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap mapPara=new HashMap();
					mapPara.put("'dd_id'", "'"+((HashMap)list.get(i)).get("工单ID")+"'");
					mapPara.put("'mz_code'", "'"+((HashMap)list.get(i)).get("模组编码")+"'");
					mapPara.put("'mz_xuId'", "'"+((HashMap)list.get(i)).get("模组序ID")+"'");
					mapPara.put("'mz_leixing'", "'"+((HashMap)list.get(i)).get("模组类型")+"'");
					mapPara.put("'mz_shuliang'", "'"+((HashMap)list.get(i)).get("数量")+"'");
					mapPara.put("'mz_jindu'", "'"+((HashMap)list.get(i)).get("模组完成率")+"'");
					result.add(mapPara);
					mapPara=null;
				}
			};
			list=null;context=null;dao=null;map=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 订单-模组-配方显示
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getZlpfList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			OrderOperactionDAO dao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");
			ArrayList result=new ArrayList();
			JSONObject obj=new JSONObject();
			String sql="SELECT a.* FROM `配方指令队列` a WHERE " +
					"a.`工单ID`='"+map.get("dd_id")+"' AND a.`模组序ID`='"+map.get("mz_xuId")+"' ORDER BY a.`分解号`,a.`载具序号`,a.`载具位置`";
			map.put("sql", sql);sql=null;
			List list=dao.getZlpfList(map);
			if(list!=null&&list.size()>0){
				//故障恢复
				if(map.get("type")!=null&&map.get("type").equals("GuzhangHuifu")){
					ArrayList A=new ArrayList();
					int row=24;//行高
					for(int i=0;i<list.size();i++){
						HashMap mapPara1=new HashMap();
						String dd_zaijuxuhao=((HashMap)list.get(i)).get("载具序号").toString();
						//计算行高
						if(i+1!=list.size()&&dd_zaijuxuhao.equals(((HashMap)list.get(i+1)).get("载具序号"))){
							row=row+row;
						}else{
							CarryLine line;
							if(map.get("line").equals("1")){
								line=PLC.getIntance().line;
							}else{
								line=PLC.getIntance().line2;
							};
							String k="";
							for(int j=-1;j<15;j++){
								try{
								Carry car=line.getCarry(j);
								if(car!=null){
									if(car.get工单ID()==Integer.parseInt(map.get("dd_id").toString())&&
										car.get模组序ID()==Integer.parseInt(map.get("mz_xuId").toString())&&
										car.get分解号()==Integer.parseInt(((HashMap)list.get(i)).get("分解号").toString())&&
										car.get载具序号()==Integer.parseInt(dd_zaijuxuhao)){
										k=j+"";
										break;
									};
								};
								}catch(Exception e){
									e.printStackTrace();
								}
							};
							mapPara1.put("dd_zaijuxuhao",dd_zaijuxuhao);
							mapPara1.put("fenJh", ((HashMap)list.get(i)).get("分解号"));
							mapPara1.put("row", row);
							mapPara1.put("dd_qianshengdubiaozhi", ((HashMap)list.get(i)).get("前升读标志").equals("1")?"已读":"");
							mapPara1.put("shusongxian", k);
							A.add(mapPara1);
							row=24;
						};
						mapPara1=null;
						HashMap mapPara2=new HashMap();
						mapPara2.put("dd_gdId", map.get("dd_id"));
						mapPara2.put("dd_mzxId", map.get("mz_xuId"));
						mapPara2.put("dd_zaijuxuhao",dd_zaijuxuhao);
						mapPara2.put("dd_fenjiehao", ((HashMap)list.get(i)).get("分解号"));
						mapPara2.put("dd_wuliao", ((HashMap)list.get(i)).get("物料"));
						mapPara2.put("dd_xuqiushuliang", ((HashMap)list.get(i)).get("数量"));
						mapPara2.put("dd_wanchengshuliang", ((HashMap)list.get(i)).get("完成数量"));
						mapPara2.put("dd_gongwei", ((HashMap)list.get(i)).get("工位"));
						mapPara2.put("dd_stduqubiaozhi", ((HashMap)list.get(i)).get("ST读取标志").equals("1")?"已读":"");
						result.add(mapPara2);
						mapPara2=null;
						dd_zaijuxuhao=null;
					};
					obj.put("A", A);A=null;
					obj.put("B", result);
				//配方显示
				}else{
					for(int i=0;i<list.size();i++){
						HashMap mapPara=new HashMap();
						mapPara.put("'dd_gdxuhao'", "'"+((HashMap)list.get(i)).get("工单序号")+"'");
						mapPara.put("'dd_zaijuxuhao'", "'"+((HashMap)list.get(i)).get("载具序号")+"'");
						mapPara.put("'dd_fenjiehao'", "'"+((HashMap)list.get(i)).get("分解号")+"'");
						mapPara.put("'dd_wuliao'", "'"+((HashMap)list.get(i)).get("物料")+"'");
						mapPara.put("'dd_wuliaomiaoshu'", "'"+((HashMap)list.get(i)).get("物料描述")+"'");
						mapPara.put("'dd_xuqiushuliang'", "'"+((HashMap)list.get(i)).get("数量")+"'");
						mapPara.put("'dd_wanchengshuliang'", "'"+((HashMap)list.get(i)).get("完成数量")+"'");
						mapPara.put("'dd_dianxin1'", "'"+((HashMap)list.get(i)).get("电芯位置1")+"'");
						mapPara.put("'dd_dianxin2'", "'"+((HashMap)list.get(i)).get("电芯位置2")+"'");
						mapPara.put("'dd_dianxin3'", "'"+((HashMap)list.get(i)).get("电芯位置3")+"'");
						mapPara.put("'dd_dianxin4'", "'"+((HashMap)list.get(i)).get("电芯位置4")+"'");
						mapPara.put("'dd_gongwei'", "'"+((HashMap)list.get(i)).get("工位")+"'");
						result.add(mapPara);
						mapPara=null;
					};
				};
			};
			list=null;context=null;dao=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(map.get("type")!=null?obj:result.toString().replaceAll("'='", "':'"));
			map=null;obj=null;
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 选择分解
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward fenjieRadioBtn(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			OrderOperactionDAO dao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");
			map.put("sql", "SELECT a.ID,a.ID AS 工单ID,a.`工单序号`,b.`pack编码`,b.`pack类型`,a.`装配区`,c.`模组序ID` AS 模组序ID,c.`序号` AS `模组序号`,c.`模组编码`,e.`序号` AS 载具序号,d.`模组类型`,d.`电芯类型`,d.`层数` AS 模组层数,f.`物料`,f.`物料描述`,f.`数量`,f.`工位`,f.`载具位置`,e.`电芯数` AS 电芯数量,e.`翻面否`,'' AS 前升读标志,'' AS 叠装读标志,'' AS 预装读标志,'' AS ST读取标志,e.`电芯1` AS 电芯位置1,e.`电芯2` AS 电芯位置2,e.`电芯3` AS 电芯位置3,e.`电芯4` AS 电芯位置4,e.`假电芯1` ,e.`假电芯2`,e.`叠装否`,e.`有效型腔`,'' AS 完成数量,c.`数量` AS 分解号 FROM `工单表` a LEFT JOIN `pack题头` b ON a.`pack编码`=b.`pack编码` LEFT JOIN `pack行` c ON b.`pack编码`=c.`pack编码` LEFT JOIN `模组题头` d ON c.`模组编码`=d.`模组编码` LEFT JOIN `模组载具` e ON d.`模组ID`= e.`模组ID` LEFT JOIN `模组指令行` f ON d.`模组ID`=f.`模组ID` AND e.`载具ID`=f.`载具ID` WHERE a.`pack编码`='"+map.get("pack_code")+"' AND a.ID='"+map.get("dd_id")+"' AND (f.`物料` <> '' or f.`物料`=NULL) ORDER BY a.`工单序号`,c.`序号`");
			List list=dao.getZlList(map);
			if(list!=null&&list.size()>0){
				int fenjiehao=(int)Double.parseDouble(((HashMap)list.get(0)).get("分解号").toString());
				String mz_xuId=((HashMap)list.get(0)).get("模组序ID").toString();int i=1;
				while(i<=fenjiehao){//分解次数
					for(int j=0;j<list.size();j++){
						//所需要分解的模组全部完成之后 进行下一条数据处理
						if(mz_xuId.equals(((HashMap)list.get(j)).get("模组序ID"))	){
							map.put("工单ID", ((HashMap)list.get(j)).get("工单ID"));
							map.put("工单序号", ((HashMap)list.get(j)).get("工单序号"));
							map.put("pack编码", ((HashMap)list.get(j)).get("pack编码"));
							map.put("pack类型", ((HashMap)list.get(j)).get("pack类型"));
							map.put("装配区", ((HashMap)list.get(j)).get("装配区"));
							map.put("分解号", i);
							map.put("模组序号", ((HashMap)list.get(j)).get("模组序号"));
							map.put("模组序ID", ((HashMap)list.get(j)).get("模组序ID"));
							map.put("模组编码", ((HashMap)list.get(j)).get("模组编码"));
							map.put("载具序号", ((HashMap)list.get(j)).get("载具序号"));
							map.put("模组类型", ((HashMap)list.get(j)).get("模组类型"));
							map.put("电芯类型", ((HashMap)list.get(j)).get("电芯类型"));
							map.put("物料", ((HashMap)list.get(j)).get("物料"));
							map.put("物料描述", ((HashMap)list.get(j)).get("物料描述"));
							map.put("数量", ((HashMap)list.get(j)).get("数量"));
							map.put("工位", ((HashMap)list.get(j)).get("工位"));
							map.put("电芯数量", ((HashMap)list.get(j)).get("电芯数量"));
							map.put("翻面否", ((HashMap)list.get(j)).get("翻面否"));
							map.put("电芯位置1", ((HashMap)list.get(j)).get("电芯位置1"));
							map.put("电芯位置2", ((HashMap)list.get(j)).get("电芯位置2"));
							map.put("电芯位置3", ((HashMap)list.get(j)).get("电芯位置3"));
							map.put("电芯位置4", ((HashMap)list.get(j)).get("电芯位置4"));
							map.put("假电芯1", ((HashMap)list.get(j)).get("假电芯1"));
							map.put("假电芯2", ((HashMap)list.get(j)).get("假电芯2"));
							map.put("叠装否", ((HashMap)list.get(j)).get("叠装否"));
							map.put("有效型腔", ((HashMap)list.get(j)).get("有效型腔"));
							map.put("完成数量", 0);
							map.put("模组层数", ((HashMap)list.get(j)).get("模组层数"));
							map.put("载具位置", ((HashMap)list.get(j)).get("载具位置"));
							dao.savePfzldl(map);
						};
					};
					if(i>=fenjiehao){
						for(int k=0;k<list.size();k++){
							if(mz_xuId.equals(((HashMap)list.get(k)).get("模组序ID"))&&list.size()>0){
								list.remove(k--);
							};
						};
						if(list.size()>0){
							i=0;
							fenjiehao=(int)Double.parseDouble(((HashMap)list.get(0)).get("分解号").toString());
							mz_xuId=((HashMap)list.get(0)).get("模组序ID").toString();
						}else{
							//更新分解日期
							map.put("sql", "UPDATE `工单表` SET `分解日期`=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') WHERE `ID`='"+map.get("dd_id")+"'");
							dao.updateGdState(map);
							return null;
						};
					};
					i++;
				};
				mz_xuId=null;fenjiehao=0;i=0;
			};
			list=null;map=null;dao=null;context=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("true");
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 分解全部初始化数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward fenjieAllBtn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			OrderOperactionDAO dao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");
			map.put("sql", "SELECT a.ID,a.ID AS 工单ID,a.`工单序号`, b.`pack编码`,b.`pack类型`,a.`装配区`,c.`模组序ID`,c.`序号` AS `模组序号`,c.`模组编码`,e.`序号` AS 载具序号,d.`模组类型`,d.`电芯类型`,d.`层数` AS 模组层数,f.`物料`,f.`物料描述`,f.`数量`,f.`工位`,f.`载具位置`,e.`电芯数` AS 电芯数量,e.`翻面否`,'' AS 前升读标志,'' AS 叠装读标志,'' AS 预装读标志,'' AS ST读取标志,e.`电芯1` AS 电芯位置1,e.`电芯2` AS 电芯位置2,e.`电芯3` AS 电芯位置3,e.`电芯4` AS 电芯位置4,e.`假电芯1` ,e.`假电芯2`,e.`叠装否`,e.`有效型腔`,'' AS 完成数量,c.`数量` AS 分解号 FROM `工单表` a LEFT JOIN `pack题头` b ON a.`pack编码`=b.`pack编码` LEFT JOIN `pack行` c ON b.`pack编码`=c.`pack编码` LEFT JOIN `模组题头` d ON c.`模组编码`=d.`模组编码` LEFT JOIN `模组载具` e ON d.`模组ID`= e.`模组ID` LEFT JOIN `模组指令行` f ON d.`模组ID`=f.`模组ID` AND e.`载具ID`=f.`载具ID` LEFT JOIN `配方指令队列` g ON a.ID=g.`工单ID` WHERE (f.`物料` <> '' or f.`物料`=NULL) AND (g.`工单ID` is NULL OR g.`工单ID` ='') AND a.`状态` IS NULL ORDER BY a.`工单序号`,c.`序号`");
			List list=dao.getZlList(map);
			boolean fj=false;
			if(list!=null&&list.size()>0){
				//更新分解日期
				map.put("sql", "UPDATE `工单表` a LEFT JOIN `配方指令队列` b ON a.ID=b.`工单ID` SET a.`分解日期`=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') WHERE b.`工单ID` IS NULL OR b.`工单ID`=''");
				dao.updateGdState(map);
				//处理配方表
				int fenjiehao=(int)Double.parseDouble(((HashMap)list.get(0)).get("分解号").toString());
				String gd_id=((HashMap)list.get(0)).get("工单ID").toString();
				String mz_xuId=((HashMap)list.get(0)).get("模组序ID").toString();
				int i=1;
				while(i<=fenjiehao){//分解次数
					for(int j=0;j<list.size();j++){
						//所需要分解的模组全部完成之后 进行下一条数据处理
						if(gd_id.equals(((HashMap)list.get(j)).get("工单ID")) && mz_xuId.equals(((HashMap)list.get(j)).get("模组序ID"))){
							map.put("工单ID", ((HashMap)list.get(j)).get("工单ID"));
							map.put("工单序号", ((HashMap)list.get(j)).get("工单序号"));
							map.put("pack编码", ((HashMap)list.get(j)).get("pack编码"));
							map.put("pack类型", ((HashMap)list.get(j)).get("pack类型"));
							map.put("装配区", ((HashMap)list.get(j)).get("装配区"));
							map.put("分解号", i);
							map.put("模组序号", ((HashMap)list.get(j)).get("模组序号"));
							map.put("模组序ID", ((HashMap)list.get(j)).get("模组序ID"));
							map.put("模组编码", ((HashMap)list.get(j)).get("模组编码"));
							map.put("载具序号", ((HashMap)list.get(j)).get("载具序号"));
							map.put("模组类型", ((HashMap)list.get(j)).get("模组类型"));
							map.put("电芯类型", ((HashMap)list.get(j)).get("电芯类型"));
							map.put("物料", ((HashMap)list.get(j)).get("物料"));
							map.put("物料描述", ((HashMap)list.get(j)).get("物料描述"));
							map.put("数量", ((HashMap)list.get(j)).get("数量"));
							map.put("工位", ((HashMap)list.get(j)).get("工位"));
							map.put("电芯数量", ((HashMap)list.get(j)).get("电芯数量"));
							map.put("翻面否", ((HashMap)list.get(j)).get("翻面否"));
							map.put("电芯位置1", ((HashMap)list.get(j)).get("电芯位置1"));
							map.put("电芯位置2", ((HashMap)list.get(j)).get("电芯位置2"));
							map.put("电芯位置3", ((HashMap)list.get(j)).get("电芯位置3"));
							map.put("电芯位置4", ((HashMap)list.get(j)).get("电芯位置4"));
							map.put("假电芯1", ((HashMap)list.get(j)).get("假电芯1"));
							map.put("假电芯2", ((HashMap)list.get(j)).get("假电芯2"));
							map.put("叠装否", ((HashMap)list.get(j)).get("叠装否"));
							map.put("有效型腔", ((HashMap)list.get(j)).get("有效型腔"));
							map.put("完成数量", 0);
							map.put("模组层数", ((HashMap)list.get(j)).get("模组层数"));
							map.put("载具位置", ((HashMap)list.get(j)).get("载具位置"));
							dao.savePfzldl(map);
						};
					};
					if(i>=fenjiehao){
						for(int k=0;k<list.size();k++){
							if(gd_id.equals(((HashMap)list.get(k)).get("工单ID"))&&
							mz_xuId.equals(((HashMap)list.get(k)).get("模组序ID")) && list.size()>0){
								list.remove(k--);
							};
						};
						if(list.size()>0){
							i=0;
							fenjiehao=(int)Double.parseDouble(((HashMap)list.get(0)).get("分解号").toString());
							gd_id=((HashMap)list.get(0)).get("工单ID").toString();
							mz_xuId=((HashMap)list.get(0)).get("模组序ID").toString();
						}else{
							break;
						};
					};
					i++;
				};
				fenjiehao=0;gd_id=null;mz_xuId=null;i=0;fj=true;
			};
			context=null;dao=null;map=null;list=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(fj);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 下载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private synchronized ActionForward downloadBtn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			OrderOperactionDAO dao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");
			map.put("sql", "SELECT a.* FROM `工单下载` a WHERE a.`传送否`='否'");
			List list=dao.getGdDownload(map);boolean deleteType=false;
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					double gdNumber=Double.parseDouble(((HashMap)list.get(i)).get("工单数量").toString());
					String id=((HashMap)list.get(i)).get("ID").toString();
					for(int k=0;k<(int)gdNumber;k++){
						//获取最大工单序号
						map.put("sql", "SELECT MAX(A.`工单序号`) AS '序号' FROM `工单表` AS A");
						List maxList=dao.getMaxGdxh(map);
						int maxGdxh=(((HashMap)maxList.get(0)).get("序号").equals("")?0:Integer.parseInt(((HashMap)maxList.get(0)).get("序号").toString()))+1;maxList=null;
						map.put("sql","insert into `工单表`(`工单序号`,`工单号`,`pack编码`,`工单数量`,`装配区`,`传送否`,`释放否`,`完成数量`)"+
								"values("+maxGdxh+","+
								""+((HashMap)list.get(i)).get("工单号")+"," +
								"'"+((HashMap)list.get(i)).get("pack编码")+"'," +
								"1," +
								"'"+((HashMap)list.get(i)).get("装配区")+"'," +
								"'是'," +
								"'"+((HashMap)list.get(i)).get("释放否")+"'," +
								"0)");
						deleteType=dao.saveAll(map);//将工单下载到工单表中
					};
					if(deleteType){
						//删除已下载的工单信息，工单下载表
						map.put("sql", "DELETE FROM `工单下载` WHERE ID='"+id+"'");
						dao.removeAll(map);id=null;
					};
				};
			};list=null;map=null;dao=null;context=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(deleteType);
			response.getWriter().close();
		}catch (Exception e) {
			System.out.println(e);e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除工单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward delBtn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			OrderOperactionDAO dao=(OrderOperactionDAO)context.getBean("orderOperactionDAO");
			JSONObject result=new JSONObject();
			//查询当前工单是否处理中，非处理状态可删除
			map.put("sql", "SELECT `配方指令队列`.* FROM `配方指令队列` WHERE `配方指令队列`.`工单ID`='"+map.get("gd_id")+"' AND " +
					"((`配方指令队列`.`前升读标志` IS NOT NULL OR `配方指令队列`.`前升读标志`<>'') OR " +
					"`配方指令队列`.`ST读取标志` IS NOT NULL OR `配方指令队列`.`ST读取标志`<>'')");
			List list=dao.getGdtype(map);
			if(list!=null&&list.size()>0){
				result.put("body","此工单正在处理，无法删除！");
				result.put("success",false);
			}else{
				map.put("sql", "UPDATE 工单表 SET 状态='已删除' where ID='"+map.get("gd_id")+"'");
				dao.updateGdState(map);
				map.put("sql", "DELETE FROM `配方指令队列` WHERE 工单ID='"+map.get("gd_id")+"'");
				dao.removeAll(map);
				result.put("body","删除成功！");
				result.put("success",true);
			};
			list=null;map=null;dao=null;context=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 工单调序-上调
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward upGdBtn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8"); 
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			BaseDataDAO dao=(BaseDataDAO)context.getBean("baseDataDAO");
			JSONObject result=new JSONObject();
			String gd_id=map.get("gd_id").toString();
			String up_gd_id=map.get("up_gd_id").toString();
			int gd_xuhao=Integer.parseInt(map.get("gd_xuhao").toString());
			int up_gd_xuhao=Integer.parseInt(map.get("up_gd_xuhao").toString());
			boolean yesNo=false;
			String sql="update `工单表` set " +
					"工单序号='-1' " +	 //需要先将调小的序号变为-1；然后根据-1改变成调小后的序号
					"where ID='"+up_gd_id+"'";
			map.put("sql", sql);
			yesNo=dao.updateXuhaoUp(map);
			if(yesNo){
				sql="update `工单表` set " +
					"工单序号='"+up_gd_xuhao+"' " +
					"where ID='"+gd_id+"'";
				map.put("sql", sql);
				yesNo=dao.updateXuhaoUp(map);
			}
			if(yesNo){
				sql="update `工单表` set " +
					"工单序号='"+gd_xuhao+"' " +
					"where ID='"+up_gd_id+"'";
				map.put("sql", sql);
				yesNo=dao.updateXuhaoUp(map);
			}
			if(yesNo){
				result.put("body","上调成功！");
				result.put("success",true);
			}else{
				result.put("body","上调失败！");
				result.put("success",false);
			}
			gd_id=null;
			up_gd_id=null;
			gd_xuhao=0;
			up_gd_xuhao=0;
			context=null;dao=null;map=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/***
	 * 工单调序-下调
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward bomGdBtn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8"); 
			response.setCharacterEncoding("utf-8");
			HashMap map=GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context=GetApplicationContext.getContext(request);
			BaseDataDAO dao=(BaseDataDAO)context.getBean("baseDataDAO");
			JSONObject result=new JSONObject();
			String gd_id=map.get("gd_id").toString();
			String up_gd_id=map.get("up_gd_id").toString();
			int gd_xuhao=Integer.parseInt(map.get("gd_xuhao").toString());
			int up_gd_xuhao=Integer.parseInt(map.get("up_gd_xuhao").toString());
			boolean yesNo=false;
			String sql="update `工单表` set " +
					"工单序号='-1' " +	 //需要先将调小的序号变为-1；然后根据-1改变成调小后的序号
					"where ID='"+gd_id+"'";
			map.put("sql", sql);
			yesNo=dao.updateXuhaoUp(map);
			if(yesNo){
				sql="update `工单表` set " +
					"工单序号='"+gd_xuhao+"' " +
					"where ID='"+up_gd_id+"'";
				map.put("sql", sql);
				yesNo=dao.updateXuhaoUp(map);
			}
			if(yesNo){
				sql="update `工单表` set " +
					"工单序号='"+up_gd_xuhao+"' " +
					"where ID='"+gd_id+"'";
				map.put("sql", sql);
				yesNo=dao.updateXuhaoUp(map);
			}
			if(yesNo){
				result.put("body","下调成功！");
				result.put("success",true);
			}else{
				result.put("body","下调失败！");
				result.put("success",false);
			}
			gd_id=null;
			up_gd_id=null;
			gd_xuhao=0;
			up_gd_xuhao=0;
			context=null;dao=null;map=null;
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}