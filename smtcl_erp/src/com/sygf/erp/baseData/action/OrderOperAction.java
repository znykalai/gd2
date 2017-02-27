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

public class OrderOperAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String operType = request.getParameter("operType");
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			OrderOperactionDAO dao = (OrderOperactionDAO)context.getBean("orderOperactionDAO");
			ArrayList result = new ArrayList();
			String sql = " WHERE";
			if(!map.get("getGdId").equals("")){
				sql += " `工单表`.`工单号` = '"+map.get("getGdId")+"' AND ";
			}
			if(!map.get("getGdfenjieriqi").equals("")){
				sql += " `工单表`.`分解日期` = '"+map.get("getGdfenjieriqi")+"' AND ";
			}
			if(!map.get("getPackeCode").equals("")){
				sql += " `工单表`.`pack编码` = '"+map.get("getPackeCode")+"' AND ";
			}
			if(sql.equals(" WHERE")){
				sql = "";
			}else if(sql.endsWith("AND ")){
				sql = sql.substring(0,sql.length()-4);
			}
			map.put("sql", sql);
			List list = dao.getDdList(map);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap mapPara = new HashMap();
					String start_type = "初始化";
					if(((HashMap)list.get(i)).get("完成数量").equals("1")){
						start_type = "已完成";
					}else{
						sql = "SELECT `配方指令队列`.ID FROM `配方指令队列` WHERE `配方指令队列`.`工单ID` = '"+((HashMap)list.get(i)).get("ID")+"' AND " +
								"((`配方指令队列`.`前升读标志` IS NOT NULL OR `配方指令队列`.`前升读标志`<>'') OR " +
								"`配方指令队列`.`ST读取标志` IS NOT NULL OR `配方指令队列`.`ST读取标志`<>'')";
						map.put("sql", sql);
						List zlList = dao.getGdtype(map);
						if(zlList!=null&&zlList.size()>0){
							start_type = "正在处理";
						}else{
							sql = "SELECT `配方指令队列`.ID FROM `配方指令队列` WHERE `配方指令队列`.`工单ID` = '"+((HashMap)list.get(i)).get("ID")+"' AND " +
							"((`配方指令队列`.`前升读标志` IS NULL OR `配方指令队列`.`前升读标志`='') OR " +
							"`配方指令队列`.`ST读取标志` IS NULL OR `配方指令队列`.`ST读取标志`='')";
							map.put("sql", sql);
							zlList = dao.getGdtype(map);
							if(zlList!=null&&zlList.size()>0){
								start_type = "已分解";
							}
						}
					}
					mapPara.put("'id'", "'"+((HashMap)list.get(i)).get("ID")+"'");
					mapPara.put("'dd_zhuangtai'", "'"+start_type+"'");
					mapPara.put("'dd_code'", "'"+((HashMap)list.get(i)).get("工单号")+"'");
					mapPara.put("'dd_xuhao'", "'"+((HashMap)list.get(i)).get("工单序号")+"'");
					mapPara.put("'pack_code'", "'"+((HashMap)list.get(i)).get("pack编码")+"'");
					mapPara.put("'pack_leixing'", "'"+((HashMap)list.get(i)).get("pack类型")+"'");
					mapPara.put("'dd_zhuangpeiqu'", "'"+((HashMap)list.get(i)).get("装配区")+"'");
					mapPara.put("'dd_jihuashuliang'", "'"+((HashMap)list.get(i)).get("工单数量")+"'");
					mapPara.put("'dd_fenjieriqi'", "'"+((HashMap)list.get(i)).get("分解日期")+"'");
					mapPara.put("'dd_jindu'", "'"+((HashMap)list.get(i)).get("工单完成率")+"'");
					result.add(mapPara);
				}
			}
//			System.err.println("订单调度---定时刷新");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
			response.getWriter().close();
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
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			OrderOperactionDAO dao = (OrderOperactionDAO)context.getBean("orderOperactionDAO");
			ArrayList result = new ArrayList();
			List list = dao.getZlmzList(map);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap mapPara = new HashMap();
					mapPara.put("'dd_id'", "'"+((HashMap)list.get(i)).get("工单ID")+"'");
					mapPara.put("'mz_code'", "'"+((HashMap)list.get(i)).get("模组编码")+"'");
					mapPara.put("'mz_xuId'", "'"+((HashMap)list.get(i)).get("模组序ID")+"'");
					mapPara.put("'mz_leixing'", "'"+((HashMap)list.get(i)).get("模组类型")+"'");
					mapPara.put("'mz_shuliang'", "'"+((HashMap)list.get(i)).get("数量")+"'");
					mapPara.put("'mz_jindu'", "'"+((HashMap)list.get(i)).get("模组完成率")+"'");
					result.add(mapPara);
				}
			}
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
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			OrderOperactionDAO dao = (OrderOperactionDAO)context.getBean("orderOperactionDAO");
			ArrayList result = new ArrayList();
			String sql = "SELECT a.* FROM `配方指令队列` a WHERE " +
					"a.`工单ID`='"+map.get("dd_id")+"' AND a.`模组序ID`='"+map.get("mz_xuId")+"' ORDER BY a.`分解号`,a.`载具序号`";
			map.put("sql", sql);
			List list = dao.getZlpfList(map);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap mapPara = new HashMap();
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
				}
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
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
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			OrderOperactionDAO dao = (OrderOperactionDAO)context.getBean("orderOperactionDAO");
			String sql = "SELECT " +
						 "a.ID,"+
						 "a.ID AS 工单ID," +
						 "a.`工单序号`, " +
						 "b.`pack编码`," +
						 "b.`默认生产线` AS 装配区," +
						 "c.`模组序ID` AS 模组序ID," +
						 "c.`序号` AS `模组序号`," +
						 "c.`模组编码`," +
						 "e.`序号` AS 载具序号," +
						 "d.`模组类型`," +
						 "d.`电芯类型`," +
						 "f.`物料`," +
						 "f.`物料描述`," +
						 "f.`数量`," +
						 "f.`工位`," +
						 "e.`电芯数` AS 电芯数量," +
						 "e.`翻面否`," +
						 "'' AS 前升读标志," +
						 "'' AS 叠装读标志," +
						 "'' AS 预装读标志," +
						 "'' AS ST读取标志," +
						 "e.`电芯1` AS 电芯位置1," +
						 "e.`电芯2` AS 电芯位置2," +
						 "e.`电芯3` AS 电芯位置3," +
						 "e.`电芯4` AS 电芯位置4," +
						 "e.`假电芯1` ," +
						 "e.`假电芯2`," +
						 "e.`叠装否`," +
						 "e.`有效型腔`," +
						 "'' AS 完成数量," +
						 "c.`数量` AS 分解号 " +
						 "FROM `工单表` a " +
						 "LEFT JOIN `pack题头` b ON a.`pack编码` = b.`pack编码` " +
						 "LEFT JOIN `pack行` c ON b.`pack编码`=c.`pack编码` " +
						 "LEFT JOIN `模组题头` d ON c.`模组编码` = d.`模组编码` " +
						 "LEFT JOIN `模组载具` e ON d.`模组ID`= e.`模组ID` " +
						 "LEFT JOIN `模组指令行` f ON d.`模组ID`=f.`模组ID` AND e.`载具ID` = f.`载具ID`" +
						 "WHERE a.`pack编码`='"+map.get("pack_code")+"' AND a.ID = '"+map.get("dd_id")+"' AND (f.`物料` <> '' or f.`物料`=NULL) ORDER BY a.`工单序号`,c.`序号`";
			map.put("sql", sql);
			List list = dao.getZlList(map);
			if(list!=null&&list.size()>0){
				int fenjiehao = (int)Double.parseDouble(((HashMap)list.get(0)).get("分解号").toString());
				String mz_xuId = ((HashMap)list.get(0)).get("模组序ID").toString();
				int i = 1;
				while(i <= fenjiehao){//分解次数
					for(int j=0;j<list.size();j++){
						//所需要分解的模组全部完成之后 进行下一条数据处理
						if(mz_xuId.equals(((HashMap)list.get(j)).get("模组序ID"))	){
							map.put("工单ID", ((HashMap)list.get(j)).get("工单ID"));
							map.put("工单序号", ((HashMap)list.get(j)).get("工单序号"));
							map.put("pack编码", ((HashMap)list.get(j)).get("pack编码"));
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
							dao.savePfzldl(map);
						}
					}
					if(i>=fenjiehao){
						for(int k=0;k<list.size();k++){
							if(mz_xuId.equals(((HashMap)list.get(k)).get("模组序ID"))&&list.size()>0){
								list.remove(k--);
							}
						}
						if(list.size()>0){
							i = 0;
							fenjiehao = (int)Double.parseDouble(((HashMap)list.get(0)).get("分解号").toString());
							mz_xuId = ((HashMap)list.get(0)).get("模组序ID").toString();
						}else{
							//更新分解日期
							sql = "UPDATE `工单表` SET `分解日期`=DATE_FORMAT(NOW(),'%Y-%m-%d') WHERE `ID`='"+map.get("dd_id")+"'";
							map.put("sql", sql);
							dao.updateGdState(map);
							return null;
						}
					}
					i++;
				}
			}
			ArrayList result = new ArrayList();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
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
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			OrderOperactionDAO dao = (OrderOperactionDAO)context.getBean("orderOperactionDAO");
			String sql = "SELECT " +
						 "a.ID,"+
						 "a.ID AS 工单ID," +
						 "a.`工单序号`, " +
						 "b.`pack编码`," +
						 "b.`默认生产线` AS 装配区," +
						 "c.`模组序ID`," +
						 "c.`序号` AS `模组序号`," +
						 "c.`模组编码`," +
						 "e.`序号` AS 载具序号," +
						 "d.`模组类型`," +
						 "d.`电芯类型`," +
						 "f.`物料`," +
						 "f.`物料描述`," +
						 "f.`数量`," +
						 "f.`工位`," +
						 "e.`电芯数` AS 电芯数量," +
						 "e.`翻面否`," +
						 "'' AS 前升读标志," +
						 "'' AS 叠装读标志," +
						 "'' AS 预装读标志," +
						 "'' AS ST读取标志," +
						 "e.`电芯1` AS 电芯位置1," +
						 "e.`电芯2` AS 电芯位置2," +
						 "e.`电芯3` AS 电芯位置3," +
						 "e.`电芯4` AS 电芯位置4," +
						 "e.`假电芯1` ," +
						 "e.`假电芯2`," +
						 "e.`叠装否`," +
						 "e.`有效型腔`," +
						 "'' AS 完成数量," +
						 "c.`数量` AS 分解号 " +
						 "FROM `工单表` a " +
						 "LEFT JOIN `pack题头` b ON a.`pack编码` = b.`pack编码` " +
						 "LEFT JOIN `pack行` c ON b.`pack编码`=c.`pack编码` " +
						 "LEFT JOIN `模组题头` d ON c.`模组编码` = d.`模组编码` " +
						 "LEFT JOIN `模组载具` e ON d.`模组ID`= e.`模组ID` " +
						 "LEFT JOIN `模组指令行` f ON d.`模组ID`=f.`模组ID` AND e.`载具ID` = f.`载具ID` " +
						 "LEFT JOIN `配方指令队列` g ON a.ID = g.`工单ID` " +
						 "WHERE (f.`物料` <> '' or f.`物料`=NULL) AND (g.`工单ID` is NULL OR g.`工单ID` ='') ORDER BY a.`工单序号`,c.`序号`";
			map.put("sql", sql);
			List list = dao.getZlList(map);
			if(list!=null&&list.size()>0){
				//更新分解日期
				sql = "UPDATE `工单表` a " +
						"LEFT JOIN `配方指令队列` b ON a.ID = b.`工单ID` " +
						"SET a.`分解日期`=DATE_FORMAT(NOW(),'%Y-%m-%d') WHERE b.`工单ID` IS NULL OR b.`工单ID` = ''";
				map.put("sql", sql);
				dao.updateGdState(map);
				//处理配方表
				int fenjiehao = (int)Double.parseDouble(((HashMap)list.get(0)).get("分解号").toString());
				String gd_id = ((HashMap)list.get(0)).get("工单ID").toString();
				String mz_xuId = ((HashMap)list.get(0)).get("模组序ID").toString();
				int i = 1;
				while(i <= fenjiehao){//分解次数
					for(int j=0;j<list.size();j++){
						//所需要分解的模组全部完成之后 进行下一条数据处理
						if(gd_id.equals(((HashMap)list.get(j)).get("工单ID")) && mz_xuId.equals(((HashMap)list.get(j)).get("模组序ID"))){
							map.put("工单ID", ((HashMap)list.get(j)).get("工单ID"));
							map.put("工单序号", ((HashMap)list.get(j)).get("工单序号"));
							map.put("pack编码", ((HashMap)list.get(j)).get("pack编码"));
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
							dao.savePfzldl(map);
						}
					}
					if(i>=fenjiehao){
						for(int k=0;k<list.size();k++){
							if(gd_id.equals(((HashMap)list.get(k)).get("工单ID"))&&
							mz_xuId.equals(((HashMap)list.get(k)).get("模组序ID")) && list.size()>0){
								list.remove(k--);
							}
						}
						if(list.size()>0){
							i = 0;
							fenjiehao = (int)Double.parseDouble(((HashMap)list.get(0)).get("分解号").toString());
							gd_id = ((HashMap)list.get(0)).get("工单ID").toString();
							mz_xuId = ((HashMap)list.get(0)).get("模组序ID").toString();
						}else{
							return null;
						}
					}
					i++;
				}
				
			}
			ArrayList result = new ArrayList();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
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
	private ActionForward downloadBtn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			OrderOperactionDAO dao = (OrderOperactionDAO)context.getBean("orderOperactionDAO");
			String sql = "SELECT a.`ID`,a.`工单号`,a.`pack编码`,a.`工单数量`,a.`装配区`,a.`传送否`,a.`释放否` FROM `工单下载` a";
			map.put("sql", sql);
			List list = dao.getGdDownload(map);
			if(list!=null&&list.size()>0){
				boolean deleteType = false;
				for(int i=0;i<list.size();i++){
					double gdNumber = Double.parseDouble(((HashMap)list.get(i)).get("工单数量").toString());
					for(int k=0;k<(int)gdNumber;k++){
						//获取最大工单序号
						List maxList = dao.getMaxGdxh();
						int maxGdxh = 0;
						if(maxList!=null&&maxList.size()>0){
							maxGdxh = Integer.parseInt(((HashMap)maxList.get(0)).get("工单序号").toString().equals("")?"0":((HashMap)maxList.get(0)).get("工单序号").toString());
						}
						sql = "insert into `工单表`(`工单序号`,`工单号`,`pack编码`,`工单数量`,`装配区`,`传送否`,`释放否`,`完成数量`)"+
								"values("+(maxGdxh+1)+","+
										""+((HashMap)list.get(i)).get("工单号")+"," +
										"'"+((HashMap)list.get(i)).get("pack编码")+"'," +
										"1," +
										"'"+((HashMap)list.get(i)).get("装配区")+"'," +
										"'"+((HashMap)list.get(i)).get("传送否")+"'," +
										"'"+((HashMap)list.get(i)).get("释放否")+"'," +
										"0)";
						map.put("sql",sql);
						deleteType = dao.saveAll(map);//将工单下载到工单表中
					}
				}
				if(deleteType){
					//删除已下载的工单信息，工单下载表
					sql = "DELETE FROM `工单下载`";
					map.put("sql", sql);
					dao.removeAll(map);
				}
			}
			ArrayList result = new ArrayList();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
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
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			OrderOperactionDAO dao = (OrderOperactionDAO)context.getBean("orderOperactionDAO");
			JSONObject result = new JSONObject();
			//查询当前工单是否处理中，非处理状态可删除
			String sql = "SELECT `配方指令队列`.* FROM `配方指令队列` WHERE `配方指令队列`.`工单ID` = '"+map.get("gd_id")+"' AND " +
			"((`配方指令队列`.`前升读标志` IS NOT NULL OR `配方指令队列`.`前升读标志`<>'') OR " +
			"`配方指令队列`.`ST读取标志` IS NOT NULL OR `配方指令队列`.`ST读取标志`<>'')";
			map.put("sql", sql);
			List list = dao.getGdtype(map);
			if(list!=null&&list.size()>0){
				result.put("body","此工单正在处理，无法删除！");
				result.put("success",false);
			}else{
				sql = "DELETE a,b FROM `工单表` AS a "+
					 "LEFT JOIN `配方指令队列` AS b ON a.ID = b.`工单ID` WHERE a.ID='"+map.get("gd_id")+"'";
				map.put("sql", sql);
				dao.removeAll(map);
				result.put("body","删除成功！");
				result.put("success",true);
			}
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
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			JSONObject result = new JSONObject();
//			System.out.println(map);
			String gd_id = map.get("gd_id").toString();
			String up_gd_id = map.get("up_gd_id").toString();
			int gd_xuhao = Integer.parseInt(map.get("gd_xuhao").toString());
			int up_gd_xuhao = Integer.parseInt(map.get("up_gd_xuhao").toString());
			boolean yesNo = false;
			String sql = "update `工单表` set " +
					"工单序号 = '-1' " +	 //需要先将调小的序号变为-1；然后根据-1改变成调小后的序号
					"where ID = '"+up_gd_id+"'";
//			System.out.println("sql="+sql);
			map.put("sql", sql);
			yesNo = dao.updateXuhaoUp(map);
			if(yesNo){
				sql = "update `工单表` set " +
					"工单序号 = '"+up_gd_xuhao+"' " +
					"where ID = '"+gd_id+"'";
				map.put("sql", sql);
//				System.out.println("sql="+sql);
				yesNo = dao.updateXuhaoUp(map);
			}
			if(yesNo){
				sql = "update `工单表` set " +
					"工单序号 = '"+gd_xuhao+"' " +
					"where ID = '"+up_gd_id+"'";
				map.put("sql", sql);
//				System.out.println("sql="+sql);
				yesNo = dao.updateXuhaoUp(map);
			}
			if(yesNo){
				result.put("body","上调成功！");
				result.put("success",true);
			}else{
				result.put("body","上调失败！");
				result.put("success",false);
			}
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
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = GetApplicationContext.getContext(request);
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			JSONObject result = new JSONObject();
//			System.out.println(map);
			String gd_id = map.get("gd_id").toString();
			String up_gd_id = map.get("up_gd_id").toString();
			int gd_xuhao = Integer.parseInt(map.get("gd_xuhao").toString());
			int up_gd_xuhao = Integer.parseInt(map.get("up_gd_xuhao").toString());
			boolean yesNo = false;
			String sql = "update `工单表` set " +
					"工单序号 = '-1' " +	 //需要先将调小的序号变为-1；然后根据-1改变成调小后的序号
					"where ID = '"+gd_id+"'";
//			System.out.println("sql="+sql);
			map.put("sql", sql);
			yesNo = dao.updateXuhaoUp(map);
			if(yesNo){
				sql = "update `工单表` set " +
					"工单序号 = '"+gd_xuhao+"' " +
					"where ID = '"+up_gd_id+"'";
				map.put("sql", sql);
//				System.out.println("sql="+sql);
				yesNo = dao.updateXuhaoUp(map);
			}
			if(yesNo){
				sql = "update `工单表` set " +
					"工单序号 = '"+up_gd_xuhao+"' " +
					"where ID = '"+gd_id+"'";
				map.put("sql", sql);
//				System.out.println("sql="+sql);
				yesNo = dao.updateXuhaoUp(map);
			}
			if(yesNo){
				result.put("body","下调成功！");
				result.put("success",true);
			}else{
				result.put("body","下调失败！");
				result.put("success",false);
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