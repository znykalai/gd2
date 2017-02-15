package com.sygf.erp.baseData.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sygf.erp.baseData.dao.BaseDataDAO;
import com.sygf.erp.util.GetParam;

public class BaseDataAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String operType = request.getParameter("operType");
			if (operType.equals("saveWl")){
				return saveWl(mapping, form, request, response);
			}else if (operType.equals("deleteWl")){
				return deleteWl(mapping, form, request, response);
			}else if (operType.equals("selectWlList")){
				return selectWlList(mapping, form, request, response);
			}else if (operType.equals("saveMz")){
				return saveMz(mapping, form, request, response);
			}else if (operType.equals("getMzList")){
				return getMzList(mapping, form, request, response);
			}else if (operType.equals("saveZlh")){
				return saveZlh(mapping, form, request, response);
			}else if (operType.equals("mz_delete")){
				return mz_delete(mapping, form, request, response);
			}else if (operType.equals("mz_zj_up")){
				return mz_zj_up(mapping, form, request, response);
			}else if (operType.equals("mz_zlh_up")){
				return mz_zlh_up(mapping, form, request, response);
			}else if (operType.equals("mz_zlh_bottom")){
				return mz_zlh_bottom(mapping, form, request, response);
			}else if (operType.equals("savePack")){
				return savePack(mapping, form, request, response);
			}else if (operType.equals("showPackRow")){
				return showPackRow(mapping, form, request, response);
			}else if (operType.equals("getPackHead")){
				return getPackHead(mapping, form, request, response);
			}else if (operType.equals("deletePack")){
				return deletePack(mapping, form, request, response);
			}else if (operType.equals("mz_zj_bottom")){
				return mz_zj_bottom(mapping, form, request, response);
			}else if (operType.equals("pack_up")){
				return pack_up(mapping, form, request, response);
			}else if (operType.equals("pack_bottom")){
				return pack_bottom(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 基础数据-物料查询
	 * @name  guofemg
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward selectWlList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			String sql = "";
			if(map.get("id")!=null&&!map.get("id").equals("")){
				sql = " where a.物料编码='"+map.get("id")+"'";
			}
			map.put("sql", sql);
			List list = dao.selectWlList(map);
			ArrayList result = new ArrayList();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap mapPara = new HashMap();
					mapPara.put("'wuliao_code'", "'"+((HashMap)list.get(i)).get("物料编码")+"'");
					mapPara.put("'wuliao_miaoshu'", "'"+((HashMap)list.get(i)).get("物料描述")+"'");
					mapPara.put("'leibie_id'", "'"+((HashMap)list.get(i)).get("类别")+"'");
					mapPara.put("'leixing_id'", "'"+((HashMap)list.get(i)).get("类型")+"'");
					mapPara.put("'danwei_id'", "'"+((HashMap)list.get(i)).get("单位")+"'");
					mapPara.put("'wl_newDate'", "'"+((HashMap)list.get(i)).get("新建时间")+"'");
					mapPara.put("'shixiao_id'", "'"+((HashMap)list.get(i)).get("失效")+"'");
					mapPara.put("'tuopanleibie_id'", "'"+((HashMap)list.get(i)).get("托盘类别")+"'");
					mapPara.put("'zhuangzaicanshu_id'", "'"+((HashMap)list.get(i)).get("装载系数")+"'");
					mapPara.put("'huiliufazhi_id'", "'"+((HashMap)list.get(i)).get("回流阀值")+"'");
					mapPara.put("'dier_code'", "'"+((HashMap)list.get(i)).get("第二编码")+"'");
					mapPara.put("'plc_code'", "'"+((HashMap)list.get(i)).get("PLC编码")+"'");
					mapPara.put("'shanghuoqu_id'", "'"+((HashMap)list.get(i)).get("默认上货区")+"'");
					mapPara.put("'xiahuoqu_id'", "'"+((HashMap)list.get(i)).get("默认下货区")+"'");
					result.add(mapPara);
				}
			}
			//System.out.println(result.toString().replaceAll("'='", "':'"));
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 基础数据-物料维护
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @name  guoFeng
	 * @return
	 */
	private ActionForward saveWl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			int wlSize = dao.getWlCode(map).size();
			String sql = "insert into 通用物料(物料编码,物料描述,类别,类型,单位,新建者,新建时间,更新时间,失效,托盘类别,装载系数,回流阀值,第二编码,PLC编码,默认上货区,默认下货区)" +
						"values(" +
						"'"+map.get("wuliao_code")+"'," +
						"'"+map.get("wuliao_miaoshu")+"'," +
						"'"+map.get("leibie_id")+"'," +
						"'"+map.get("leixing_id")+"'," +
						"'"+map.get("danwei_id")+"'," +
						"'"+session.getAttribute("username")+"'," +
						"'"+map.get("wl_newDate")+"'," +
						"'"+map.get("wl_newDate")+"'," +
						"'"+map.get("shixiao_id")+"'," +
						"'"+map.get("tuopanleibie_id")+"'," +
						"'"+map.get("zhuangzaicanshu_id")+"'," +
						"'"+map.get("huiliufazhi_id")+"'," +
						"'"+map.get("dier_code")+"'," +
						"'"+map.get("plc_code")+"'," +
						"'"+map.get("shanghuoqu_id")+"'," +
						"'"+map.get("xiahuoqu_id")+"')";
			//修改
			if(wlSize > 0){
				sql = "update 通用物料 set " +
						"物料描述 = '"+map.get("wuliao_miaoshu")+"', " +
						"类别 = '" + map.get("leibie_id")+"'," +
						"类型 = '" + map.get("leixing_id")+"'," +
						"单位 = '" + map.get("danwei_id")+"'," +
						"更新时间 = DATE_FORMAT(NOW(),'%Y-%m-%d')," +
						"失效 = '" + map.get("shixiao_id")+"'," +
						"托盘类别 = '" + map.get("tuopanleibie_id")+"'," +
						"装载系数 = '" + map.get("zhuangzaicanshu_id")+"'," +
						"回流阀值 = '" + map.get("huiliufazhi_id")+"'," +
						"第二编码 = '" + map.get("dier_code")+"'," +
						"PLC编码 = '" + map.get("plc_code")+"'," +
						"默认上货区 = '" + map.get("shanghuoqu_id")+"'," +
						"默认下货区 = '" + map.get("xiahuoqu_id")+"' " +
						"where 物料编码 = '"+map.get("wuliao_code")+"'";
			}
			map.put("sql", sql);
			boolean yesNo = dao.saveWlBaseData(map);
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 基础数据-物料删除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @name  guoFeng
	 * @return
	 */
	private ActionForward deleteWl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			boolean yesNo = dao.deleteWl(map);
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 基础数据-模组保存
	 * @name  guoFeng
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward saveMz(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
//			System.out.println("map="+map);
			//模组题头
			JSONObject head = new JSONObject(map.get("head").toString());
			//模组编码
			String mozu_code = head.getString("mozu_code");
			//模组类型
			String mozu_leixing = head.getString("mozu_leixing");
			//电芯类型
			String mozu_dianxinleixing = head.getString("mozu_dianxinleixing");
			//工位类别
			String mozu_gongweileibie = head.getString("mozu_gongweileibie");
			//模组型腔数
			String mozu_xingqiangshu = head.getString("mozu_xingqiangshu");
			//新建者
			String userName = session.getAttribute("username")==null?"":session.getAttribute("username").toString();
			//新建时间
			String mozu_newDate = head.getString("mozu_newDate");
			map.put("mozu_code", mozu_code);
			int mzYesNo = dao.getMzYesNo(map).size();
			String sql = "insert into 模组题头(模组编码,模组类型,电芯类型,工位类别,型腔数,新建者,新建时间)" +
						"values(" +
						"'"+mozu_code+"'," +
						"'"+mozu_leixing+"'," +
						"'"+mozu_dianxinleixing+"'," +
						"'"+mozu_gongweileibie+"'," +
						"'"+mozu_xingqiangshu+"'," +
						"'"+userName+"'," +
						"'"+mozu_newDate+"')";
			if(mzYesNo > 0 ){
				sql = "update 模组题头 set " +
					"模组类型 = '" +mozu_leixing+"'," +
					"电芯类型 = '" +mozu_dianxinleixing+"'," +
					"工位类别 = '" +mozu_gongweileibie+"'," +
					"型腔数 = '" +mozu_xingqiangshu+"'," +
					"更新时间 = DATE_FORMAT(NOW(),'%Y-%m-%d')," +
					"更新者 = '" + userName+"' " +
					"where 模组编码 = '"+mozu_code+"'";
			}
			map.put("sql", sql);
			boolean yesNo = dao.saveMzHead(map);//模组题头保存
			//模组ID
			String mz_id = "";
			if(yesNo){
				List maList = dao.getMzYesNo(map);
				if(maList.size() > 0){
					mz_id = ((HashMap)maList.get(0)).get("模组ID").toString();
				}
				//新增模组行
				JSONArray add = new JSONArray(map.get("add").toString());
				//修改模组行
				JSONArray update = new JSONArray(map.get("update").toString());
				//add
				if(add.length() > 0 && !mz_id.equals("")){
					for(int i=0;i < add.length();i++){
						sql = "select a.* from 模组载具 a where a.模组ID = '"+mz_id+"' order by a.序号 DESC";
						map.put("sql", sql);
//						System.out.println(sql);
						List xhList = dao.getMzzjList(map);
						int xh = 0;
						if(xhList.size() > 0){
							xh = Integer.parseInt(((HashMap)xhList.get(0)).get("序号").toString());
						}
						sql = "insert into 模组载具(模组ID,序号,翻面否,叠装否,电芯数,电芯1,电芯2,电芯3,电芯4,有效型腔,假电芯1,假电芯2)" +
							"values(" +
							"'"+mz_id+"'," +
							"'"+(xh+1)+"'," +	//序号+1
							"'"+add.getJSONObject(i).getString("mozu_fanmianfou")+"'," +
							"'"+add.getJSONObject(i).getString("mozu_diezhuangfou")+"'," +
							"'"+add.getJSONObject(i).getString("mozu_dianxinshu")+"'," +
							"'"+add.getJSONObject(i).getString("mozu_dianxin1")+"'," +
							"'"+add.getJSONObject(i).getString("mozu_dianxin2")+"'," +
							"'"+add.getJSONObject(i).getString("mozu_dianxin3")+"'," +
							"'"+add.getJSONObject(i).getString("mozu_dianxin4")+"'," +
							"'"+add.getJSONObject(i).getString("mozu_youxiaoxingqiang")+"'," +
							"'"+add.getJSONObject(i).getString("mozu_jiadianxin1")+"'," +
							"'"+add.getJSONObject(i).getString("mozu_jiadianxin2")+"')";
//						System.out.println("模组行="+sql);
						map.put("sql", sql);
						yesNo = dao.saveMzRow(map);
					}
				}
				//update
				if(update.length() > 0 && !mz_id.equals("")){
					for(int i=0;i < update.length();i++){
						sql = "update 模组载具 set " +
							"翻面否 = '" +update.getJSONObject(i).getString("mozu_fanmianfou")+"'," +
							"叠装否 = '" +update.getJSONObject(i).getString("mozu_diezhuangfou")+"'," +
							"电芯数 = '" +update.getJSONObject(i).getString("mozu_dianxinshu")+"'," +
							"电芯1 = '" +update.getJSONObject(i).getString("mozu_dianxin1")+"'," +
							"电芯2 = '" +update.getJSONObject(i).getString("mozu_dianxin2")+"'," +
							"电芯3 = '" +update.getJSONObject(i).getString("mozu_dianxin3")+"'," +
							"电芯4 = '" +update.getJSONObject(i).getString("mozu_dianxin4")+"'," +
							"有效型腔 = '" +update.getJSONObject(i).getString("mozu_youxiaoxingqiang")+"'," +
							"假电芯1 = '" +update.getJSONObject(i).getString("mozu_jiadianxin1")+"'," +
							"假电芯2 = '" + update.getJSONObject(i).getString("mozu_jiadianxin2")+"' " +
							"where 模组ID = '"+mz_id+"' and 载具ID='"+update.getJSONObject(i).getString("zj_id")+"'";
//						System.out.println("模组指令行修改="+sql);
						map.put("sql", sql);
						yesNo = dao.saveMzRow(map);
					}
				}
			}
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			result.put("mz_id", mz_id);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取模组信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getMzList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "iso-8859-1", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
//			System.out.println(map);
			ArrayList result = new ArrayList();
			if(map.get("type").equals("head")){
				String where = "";
				if(map.get("where")!=null&&!map.get("where").equals("")){
					where = map.get("where").toString();
				}
				String sql = "select a.* from `模组题头` a" + where;
				map.put("sql", sql);
				List list = dao.getMzHead(map);
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						HashMap mapPara = new HashMap();
						mapPara.put("'mozu_id'", "'"+((HashMap)list.get(i)).get("模组ID")+"'");
						mapPara.put("'mozu_code'", "'"+((HashMap)list.get(i)).get("模组编码")+"'");
						mapPara.put("'mozu_leixing'", "'"+((HashMap)list.get(i)).get("模组类型")+"'");
						mapPara.put("'mozu_dianxinleixing'", "'"+((HashMap)list.get(i)).get("电芯类型")+"'");
						mapPara.put("'mozu_gongweileibie'", "'"+((HashMap)list.get(i)).get("工位类别")+"'");
						mapPara.put("'mozu_xingqiangshu'", "'"+((HashMap)list.get(i)).get("型腔数")+"'");
						mapPara.put("'mozu_newDate'", "'"+((HashMap)list.get(i)).get("新建时间")+"'");
						result.add(mapPara);
					}
				}
			}else if(map.get("type").equals("zjRow")){
				String sql = "select a.* from `模组载具` a where a.模组ID='"+map.get("mozu_id")+"' order by a.序号";
				map.put("sql", sql);
				List list = dao.getMzzjList(map);
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						HashMap mapPara = new HashMap();
						mapPara.put("'mozu_id'", "'"+((HashMap)list.get(i)).get("模组ID")+"'");
						mapPara.put("'zj_id'", "'"+((HashMap)list.get(i)).get("载具ID")+"'");
						mapPara.put("'zj_xuhao'", "'"+((HashMap)list.get(i)).get("序号")+"'");
						mapPara.put("'zj_fanmianfou'", "'"+((HashMap)list.get(i)).get("翻面否")+"'");
						mapPara.put("'zj_diezhuangfou'", "'"+((HashMap)list.get(i)).get("叠装否")+"'");
						mapPara.put("'zj_dianxinshu'", "'"+((HashMap)list.get(i)).get("电芯数")+"'");
						mapPara.put("'zj_dianxin1'", "'"+((HashMap)list.get(i)).get("电芯1")+"'");
						mapPara.put("'zj_dianxin2'", "'"+((HashMap)list.get(i)).get("电芯2")+"'");
						mapPara.put("'zj_dianxin3'", "'"+((HashMap)list.get(i)).get("电芯3")+"'");
						mapPara.put("'zj_dianxin4'", "'"+((HashMap)list.get(i)).get("电芯4")+"'");
						mapPara.put("'zj_youxiaoxingqiang'", "'"+((HashMap)list.get(i)).get("有效型腔")+"'");
						mapPara.put("'zj_jiadianxin1'", "'"+((HashMap)list.get(i)).get("假电芯1")+"'");
						mapPara.put("'zj_jiadianxin2'", "'"+((HashMap)list.get(i)).get("假电芯2")+"'");
						result.add(mapPara);
					}
				}
			}else if(map.get("type").equals("zlhRow")){
				String sql = "select a.* from 模组指令行 a where a.模组ID='"+map.get("mozu_id")+"' and a.载具ID='"+map.get("zj_id")+"' order by a.序号";
				map.put("sql", sql);
				List zlhList = dao.getMzzlhList(map);
				if(zlhList!=null&&zlhList.size()>0){
					for(int i=0;i<zlhList.size();i++){
						HashMap mapPara = new HashMap();
						mapPara.put("'mozu_id'", "'"+((HashMap)zlhList.get(i)).get("模组ID")+"'");
						mapPara.put("'zj_id'", "'"+((HashMap)zlhList.get(i)).get("载具ID")+"'");
						mapPara.put("'zlh_xuhao'", "'"+((HashMap)zlhList.get(i)).get("序号")+"'");
						mapPara.put("'zlh_wuliao'", "'"+((HashMap)zlhList.get(i)).get("物料")+"'");
						mapPara.put("'zlh_wuliaomiaoshu'", "'"+((HashMap)zlhList.get(i)).get("物料描述")+"'");
						mapPara.put("'zlh_shuliang'", "'"+((HashMap)zlhList.get(i)).get("数量")+"'");
						mapPara.put("'zlh_gongwei'", "'"+((HashMap)zlhList.get(i)).get("工位")+"'");
						result.add(mapPara);
					}
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
	 * 保存指令行
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward saveZlh(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			//新增模组行
			JSONArray add = new JSONArray(map.get("add").toString());
			//修改模组行
			JSONArray update = new JSONArray(map.get("update").toString());
//			System.out.println("map="+map);
			boolean yesNo = false;
			if(add.length() > 0){//add
				for(int i=0;i<add.length();i++){
					String sql = "select a.* from `模组指令行` a where a.模组ID = '"+add.getJSONObject(i).getString("mozu_id")+"' " +
							"and a.载具ID='"+add.getJSONObject(i).getString("zj_id")+"' order by a.序号 DESC";
					map.put("sql", sql);
					List xhList = dao.getMzzlhList(map);
					int xh = 0;
					if(xhList.size() > 0){
						xh = Integer.parseInt(((HashMap)xhList.get(0)).get("序号").toString());
					}
					sql = "insert into `模组指令行`(`模组ID`,`载具ID`,`序号`,`物料`,`物料描述`,`数量`,`工位`)" +
							"values(" +
							"'"+add.getJSONObject(i).getString("mozu_id")+"'," +
							"'"+add.getJSONObject(i).getString("zj_id")+"'," +
							"'"+(xh+1)+"'," +	//序号+1
							"'"+add.getJSONObject(i).getString("zlh_wuliao")+"'," +
							"'"+add.getJSONObject(i).getString("zlh_wuliaomiaoshu")+"'," +
							"'"+add.getJSONObject(i).getString("zlh_shuliang")+"'," +
							"'"+add.getJSONObject(i).getString("zlh_gongwei")+"')";
					map.put("sql", sql);
					yesNo = dao.saveZlhRow(map);
				}
			}
			if(update.length()>0){
				for(int i=0;i < update.length();i++){
					String sql = "update 模组指令行 set " +
						"物料 = '" +update.getJSONObject(i).getString("zlh_wuliao")+"'," +
						"物料描述 = '" +update.getJSONObject(i).getString("zlh_wuliaomiaoshu")+"'," +
						"数量 = '" +update.getJSONObject(i).getString("zlh_shuliang")+"'," +
						"工位 = '" + update.getJSONObject(i).getString("zlh_gongwei")+"' " +
						"where 模组ID = '"+update.getJSONObject(i).getString("mozu_id")+"' " +
								"and 载具ID='"+update.getJSONObject(i).getString("zj_id")+"' "+
								"and 序号='"+update.getJSONObject(i).getString("zlh_xuhao")+"'";
					map.put("sql", sql);
					yesNo = dao.saveZlhRow(map);
				}
			}
			response.setContentType("text/html;charset=utf-8");
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除模组
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward mz_delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			boolean yesNo = true;
			//删除模组题头
			if(map.get("deleteType").equals("模组")&&
				!map.get("mozu_id").equals("")){
				String sql = "DELETE a,b,c FROM `模组题头` AS a "+
							 "LEFT JOIN `模组载具` AS b ON a.模组ID = b.模组ID "+
							 "LEFT JOIN `模组指令行` AS c ON a.模组ID = c.模组ID and b.载具ID=c.载具ID WHERE a.模组ID='"+map.get("mozu_id")+"'";
				map.put("sql", sql);
				yesNo = dao.mz_delete(map);
			}
			//删除模组载具
			if(map.get("deleteType").equals("载具行")&&
				!map.get("mozu_id").equals("")&&
				!map.get("zj_id").equals("")){
				String sql = "DELETE a,b FROM `模组载具` AS a "+
							 "LEFT JOIN `模组指令行` AS b ON a.`载具ID` = b.`载具ID` AND a.`模组ID`=b.`模组ID` "+
							 "WHERE a.模组ID='"+map.get("mozu_id")+"' AND a.`载具ID`='"+map.get("zj_id")+"'";
				map.put("sql", sql);
				yesNo = dao.mz_delete(map);
			}
			//删除指令行
			if(map.get("deleteType").equals("指令行")&&
				!map.get("mozu_id").equals("")&&
				!map.get("zj_id").equals("")&&
				!map.get("zlh_xuhao").equals("")){
				String sql = "DELETE a FROM `模组指令行` AS a WHERE a.`模组ID`='"+map.get("mozu_id")+"' AND a.`载具ID`='"+map.get("zj_id")+"' AND a.`序号`='"+map.get("zlh_xuhao")+"'";
				map.put("sql", sql);
				yesNo = dao.mz_delete(map);
			}
			response.setContentType("text/html;charset=utf-8");
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 模组载具上调
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward mz_zj_up(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
//			System.out.println("map="+map);
			int xuhao = Integer.parseInt(map.get("zj_xuhao")+"");
			String mozu_id = map.get("mozu_id").toString();
			String zj_id = map.get("zj_id").toString();
			String zj_id_up = map.get("zj_id_up").toString();
			boolean yesNo = true;
			if(!mozu_id.equals("")&&!zj_id.equals("")){
				String sql = "update 模组载具 set " +
					"序号 = '-1' " +	//需要先将调小的序号变为-1；然后根据-1改变成调小后的序号
					"where 模组ID = '"+mozu_id+"' " +
							"and 载具ID='"+zj_id_up+"' "+
							"and 序号='"+(xuhao - 1)+"'";
				map.put("sql", sql);
//				System.out.println("sql="+sql);
				yesNo = dao.updateXuhaoUp(map);
				if(yesNo){
					sql = "update 模组载具 set " +
						"序号 = '"+(xuhao-1)+"' " +
						"where 模组ID = '"+mozu_id+"' " +
								"and 载具ID='"+zj_id+"' "+
								"and 序号='"+xuhao+"'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
				if(yesNo){
					sql = "update 模组载具 set " +
						"序号 = '"+xuhao+"' " +
						"where 模组ID = '"+mozu_id+"' " +
								"and 载具ID='"+zj_id_up+"' "+
								"and 序号='-1'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
			}
			response.setContentType("text/html;charset=utf-8");
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 模组载具下调
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward mz_zj_bottom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
//			System.out.println("map="+map);
			int xuhao = Integer.parseInt(map.get("zj_xuhao")+"");
			String mozu_id = map.get("mozu_id").toString();
			String zj_id = map.get("zj_id").toString();
			String zj_id_up = map.get("zj_id_up").toString();
			boolean yesNo = true;
			if(!mozu_id.equals("")&&!zj_id.equals("")){
				String sql = "update 模组载具 set " +
					"序号 = '-1' " +	//需要先将调小的序号变为-1；然后根据-1改变成调小后的序号
					"where 模组ID = '"+mozu_id+"' " +
							"and 载具ID='"+zj_id_up+"' "+
							"and 序号='"+(xuhao + 1)+"'";
				map.put("sql", sql);
//				System.out.println("sql="+sql);
				yesNo = dao.updateXuhaoUp(map);
				if(yesNo){
					sql = "update 模组载具 set " +
						"序号 = '"+(xuhao+1)+"' " +
						"where 模组ID = '"+mozu_id+"' " +
								"and 载具ID='"+zj_id+"' "+
								"and 序号='"+xuhao+"'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
				if(yesNo){
					sql = "update 模组载具 set " +
						"序号 = '"+xuhao+"' " +
						"where 模组ID = '"+mozu_id+"' " +
								"and 载具ID='"+zj_id_up+"' "+
								"and 序号='-1'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
			}
			response.setContentType("text/html;charset=utf-8");
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 指令行上调
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward mz_zlh_up(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
//			System.out.println("map="+map);
			int xuhao = Integer.parseInt(map.get("zlh_xuhao")+"");
			String mozu_id = map.get("mozu_id").toString();
			String zj_id = map.get("zj_id").toString();
			boolean yesNo = true;
			if(!mozu_id.equals("")&&!zj_id.equals("")){
				String sql = "update 模组指令行 set " +
					"序号 = '-1' " +	//需要先将调小的序号变为-1；然后根据-1改变成调小后的序号
					"where 模组ID = '"+mozu_id+"' " +
							"and 载具ID='"+zj_id+"' "+
							"and 序号='"+(xuhao - 1)+"'";
				map.put("sql", sql);
//				System.out.println("sql="+sql);
				yesNo = dao.updateXuhaoUp(map);
				if(yesNo){
					sql = "update 模组指令行 set " +
						"序号 = '"+(xuhao-1)+"' " +
						"where 模组ID = '"+mozu_id+"' " +
								"and 载具ID='"+zj_id+"' "+
								"and 序号='"+xuhao+"'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
				if(yesNo){
					sql = "update 模组指令行 set " +
						"序号 = '"+xuhao+"' " +
						"where 模组ID = '"+mozu_id+"' " +
								"and 载具ID='"+zj_id+"' "+
								"and 序号='-1'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
			}
			response.setContentType("text/html;charset=utf-8");
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 指令行下调
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward mz_zlh_bottom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{

			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
//			System.out.println("map="+map);
			int xuhao = Integer.parseInt(map.get("zlh_xuhao")+"");
			String mozu_id = map.get("mozu_id").toString();
			String zj_id = map.get("zj_id").toString();
			boolean yesNo = true;
			if(!mozu_id.equals("")&&!zj_id.equals("")){
				String sql = "update 模组指令行 set " +
					"序号 = '-1' " +	//需要先将调小的序号变为-1；然后根据-1改变成调小后的序号
					"where 模组ID = '"+mozu_id+"' " +
							"and 载具ID='"+zj_id+"' "+
							"and 序号='"+(xuhao + 1)+"'";
				map.put("sql", sql);
//				System.out.println("sql="+sql);
				yesNo = dao.updateXuhaoUp(map);
				if(yesNo){
					sql = "update 模组指令行 set " +
						"序号 = '"+(xuhao+1)+"' " +
						"where 模组ID = '"+mozu_id+"' " +
								"and 载具ID='"+zj_id+"' "+
								"and 序号='"+xuhao+"'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
				if(yesNo){
					sql = "update 模组指令行 set " +
						"序号 = '"+xuhao+"' " +
						"where 模组ID = '"+mozu_id+"' " +
								"and 载具ID='"+zj_id+"' "+
								"and 序号='-1'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
			}
			response.setContentType("text/html;charset=utf-8");
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.getWriter().print(result);
			response.getWriter().close();
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * pack保存
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward savePack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
//			System.out.println("map="+map);
			JSONObject head = new JSONObject(map.get("head").toString());
			String pack_id = head.get("pack_id").toString();	//pack——ID
			String pack_code = head.get("pack_code").toString();//pack编码
			String userName = session.getAttribute("username")==null?"":session.getAttribute("username").toString();//登录人员
			List headList = null;
			boolean yesNo = false;
			String sql = "insert into `pack题头`(`pack编码`,`pack类型`,`默认生产线`,`电芯类型`,`新建者`,`新建时间`)" +
						"values(" +
						"'"+pack_code+"'," +
						"'"+head.get("pack_leixing").toString()+"'," +
						"'"+head.get("pack_morenshengchanxian").toString()+"'," +
						"'"+head.get("pack_dianxinleixing").toString()+"'," +
						"'"+userName+"'," +
						"'"+head.get("pack_newDate").toString()+"')";
			if(!pack_id.equals("")){
				sql = "update `pack题头` set " +
					"pack类型 = '" +head.get("pack_leixing").toString()+"'," +
					"默认生产线 = '" +head.get("pack_morenshengchanxian").toString()+"'," +
					"电芯类型 = '" +head.get("pack_dianxinleixing").toString()+"'," +
					"更新时间 = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%S')," +
					"更新者 = '" + userName +"' " +
					"where ID = '"+pack_id+"' " +
							"and pack编码='"+pack_code+"'";
			}
			map.put("sql", sql);
			yesNo = dao.savePack(map);
			sql = "select a.* from `pack题头` a where a.`pack编码` = '"+pack_code+"'";
			map.put("sql", sql);
			headList = dao.getHeadList(map);
			if(headList!=null&&headList.size()>0){
				pack_id = ((HashMap)headList.get(0)).get("ID").toString();
			}
			if(yesNo&&!pack_id.equals("")){
				//新增模组行
				JSONArray add = new JSONArray(map.get("add").toString());
				if(add.length() > 0){//add
					for(int i=0;i<add.length();i++){
						sql = "select a.*,b.`模组类型` from `pack行` a " +
							"LEFT JOIN `模组题头` b ON a.`模组编码` = b.`模组编码` " +
							"where a.`pack编码` = '"+pack_code+"' and a.`ID`='"+pack_id+"' " +
							"order by a.序号 DESC";;
						map.put("sql", sql);
						List xhList = dao.getPackRowList(map);
						int xh = 0;
						if(xhList.size() > 0){
							xh = Integer.parseInt(((HashMap)xhList.get(0)).get("序号").toString());
						}
						sql = "insert into `pack行`(`ID`,`pack编码`,`序号`,`模组编码`,`数量`,`更新者`,`更新时间`)" +
								"values(" +
								"'"+pack_id+"'," +
								"'"+pack_code+"'," +
								"'"+(xh+1)+"'," +	//序号+1
								"'"+add.getJSONObject(i).getString("pack_mozu_code")+"'," +
								"'"+add.getJSONObject(i).getString("pack_shuliang")+"'," +
								"'"+userName+"'," +
								"DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%S'))";
						map.put("sql", sql);
						yesNo = dao.savePack(map);
					}
				}
				//修改模组行
				JSONArray update = new JSONArray(map.get("update").toString());
				if(update.length() > 0){
					for(int i=0;i < update.length();i++){
						sql = "update `pack行` set " +
						"模组编码 = '" +update.getJSONObject(i).getString("pack_mozu_code")+"'," +
						"数量 = '" +update.getJSONObject(i).getString("pack_shuliang")+"'," +
						"更新时间 = DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%S')," +
						"更新者 = '" + userName +"' " +
						"where ID = '"+pack_id+"' " +
								"and pack编码='"+pack_code+"' and 序号='"+update.getJSONObject(i).getString("pack_xuhao")+"'";
					}
					map.put("sql", sql);
					yesNo = dao.savePack(map);
				}
			}
			response.setContentType("text/html;charset=utf-8");
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			result.put("pack_id", pack_id);
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * pack行显示
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward showPackRow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			ArrayList result = new ArrayList();
			if(!map.get("pack_id").equals("")&&!map.get("pack_code").equals("")){
				String sql = "select a.*,b.`模组类型` from `pack行` a " +
						"LEFT JOIN `模组题头` b ON a.`模组编码` = b.`模组编码` " +
						"where a.`pack编码` = '"+map.get("pack_code")+"' and a.`ID`='"+map.get("pack_id")+"' " +
						"order by a.序号";
				map.put("sql", sql);
				List list = dao.getPackRowList(map);
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						HashMap mapPara = new HashMap();
						mapPara.put("'pack_xuhao'", "'"+((HashMap)list.get(i)).get("序号")+"'");
						mapPara.put("'pack_mozu_code'", "'"+((HashMap)list.get(i)).get("模组编码")+"'");
						mapPara.put("'pack_mozuleixing'", "'"+((HashMap)list.get(i)).get("模组类型")+"'");
						mapPara.put("'pack_shuliang'", "'"+((HashMap)list.get(i)).get("数量")+"'");
						result.add(mapPara);
					}
				}
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
	/**
	 * 显示pack题头
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward getPackHead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
			ArrayList result = new ArrayList();
			String sql = "select a.* from `pack题头` a";
			map.put("sql", sql);
			List list = dao.getHeadList(map);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap mapPara = new HashMap();
					mapPara.put("'pack_id'", "'"+((HashMap)list.get(i)).get("ID")+"'");
					mapPara.put("'pack_code'", "'"+((HashMap)list.get(i)).get("pack编码")+"'");
					mapPara.put("'pack_leixing'", "'"+((HashMap)list.get(i)).get("pack类型")+"'");
					mapPara.put("'pack_morenshengchanxian'", "'"+((HashMap)list.get(i)).get("默认生产线")+"'");
					mapPara.put("'pack_dianxinleixing'", "'"+((HashMap)list.get(i)).get("电芯类型")+"'");
					mapPara.put("'pack_newDate'", "'"+((HashMap)list.get(i)).get("新建时间")+"'");
					result.add(mapPara);
				}
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result.toString().replaceAll("'='", "':'"));
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
	/**
	 * pack删除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward deletePack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
//			System.out.println("map="+map);
			boolean yesNo = false;
			if(map.get("deleteType").equals("pack题头")){
				String sql = "DELETE a FROM `pack行` AS a WHERE a.`ID`='"+map.get("pack_id")+"' AND a.`pack编码`='"+map.get("pack_code")+"'";
				map.put("sql", sql);
				yesNo =dao.deletePack(map);
				if(yesNo){
					sql = "DELETE a FROM `pack题头` AS a WHERE a.ID='"+map.get("pack_id")+"' and a.`pack编码`='"+map.get("pack_code")+"'";
					map.put("sql", sql);
					yesNo =dao.deletePack(map);
				}
			}else if(map.get("deleteType").equals("pack行")){
				String sql = "DELETE a FROM `pack行` AS a WHERE a.`ID`='"+map.get("pack_id")+"' AND a.`pack编码`='"+map.get("pack_code")+"' AND a.`序号`='"+map.get("pack_xuhao")+"'";
				map.put("sql", sql);
				yesNo =dao.deletePack(map);
			}
			response.setContentType("text/html;charset=utf-8");
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
	/**
	 * pack行上调
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward pack_up(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
//			System.out.println("map="+map);
			int xuhao = Integer.parseInt(map.get("pack_xuhao")+"");
			String pack_id = map.get("pack_id").toString();
			String pack_code = map.get("pack_code").toString();
			
			boolean yesNo = false;
			if(!pack_id.equals("")&&!pack_code.equals("")){
				String sql = "update pack行 set " +
					"序号 = '-1' " +	//需要先将调小的序号变为-1；然后根据-1改变成调小后的序号
					"where ID = '"+pack_id+"' " +
							"and pack编码='"+pack_code+"' "+
							"and 序号='"+(xuhao - 1)+"'";
				map.put("sql", sql);
//				System.out.println("sql="+sql);
				yesNo = dao.updateXuhaoUp(map);
				if(yesNo){
					sql = "update pack行 set " +
						"序号 = '"+(xuhao-1)+"' " +
						"where ID = '"+pack_id+"' " +
						"and pack编码='"+pack_code+"' "+
								"and 序号='"+xuhao+"'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
				if(yesNo){
					sql = "update pack行 set " +
						"序号 = '"+xuhao+"' " +
						"where ID = '"+pack_id+"' " +
						"and pack编码='"+pack_code+"' "+
								"and 序号='-1'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
			}
			response.setContentType("text/html;charset=utf-8");
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * pack行下调
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward pack_bottom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			HashMap map = GetParam.GetParamValue(request, "utf-8", "utf-8");
			HttpSession session = request.getSession();
			ApplicationContext context = new ClassPathXmlApplicationContext("file:"+request.getRealPath("/")+"\\WEB-INF\\applicationContext.xml");
			BaseDataDAO dao = (BaseDataDAO)context.getBean("baseDataDAO");
//			System.out.println("map="+map);
			int xuhao = Integer.parseInt(map.get("pack_xuhao")+"");
			String pack_id = map.get("pack_id").toString();
			String pack_code = map.get("pack_code").toString();
			
			boolean yesNo = true;
			if(!pack_id.equals("")&&!pack_code.equals("")){
				String sql = "update pack行 set " +
					"序号 = '-1' " +	//需要先将调小的序号变为-1；然后根据-1改变成调小后的序号
					"where ID = '"+pack_id+"' " +
							"and pack编码='"+pack_code+"' "+
							"and 序号='"+(xuhao + 1)+"'";
				map.put("sql", sql);
//				System.out.println("sql="+sql);
				yesNo = dao.updateXuhaoUp(map);
				if(yesNo){
					sql = "update pack行 set " +
						"序号 = '"+(xuhao+1)+"' " +
						"where ID = '"+pack_id+"' " +
						"and pack编码='"+pack_code+"' "+
								"and 序号='"+xuhao+"'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
				if(yesNo){
					sql = "update pack行 set " +
						"序号 = '"+xuhao+"' " +
						"where ID = '"+pack_id+"' " +
						"and pack编码='"+pack_code+"' "+
								"and 序号='-1'";
					map.put("sql", sql);
//					System.out.println("sql="+sql);
					yesNo = dao.updateXuhaoUp(map);
				}
			}
			response.setContentType("text/html;charset=utf-8");
			JSONObject result = new JSONObject();
			result.put("success", yesNo);
			response.getWriter().print(result);
			response.getWriter().close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}