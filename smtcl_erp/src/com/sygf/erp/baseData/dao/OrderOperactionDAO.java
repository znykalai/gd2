package com.sygf.erp.baseData.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sygf.util.Log;

public class OrderOperactionDAO extends SqlMapClientDaoSupport {
	//获取工单list
	public List getDdList(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("OrderOperactionDAO.getDdList",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.getDdList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	};
	//获取指令表信息list
	public List getZlList(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("OrderOperactionDAO.getZlList",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.getZlList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	};
	//获取指令表模组信息list
	public List getZlmzList(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("OrderOperactionDAO.getZlmzList",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.getZlmzList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	};
	//配方指令队列保存
	public void savePfzldl(HashMap map){
		try{
			getSqlMapClientTemplate().insert("OrderOperactionDAO.savePfzldl",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.savePfzldl方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
	};
	//修改工单分解日期
	public void updateGdState(HashMap map){
		try{
			getSqlMapClientTemplate().update("OrderOperactionDAO.updateGdState",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.updateGdState方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
	};
	/**
	 * 指令表 配方查询
	 * @param map
	 * @return
	 */
	public List getZlpfList(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("OrderOperactionDAO.getZlpfList",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.getZlpfList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	};
	/**
	 * 下载工单
	 * @param map
	 * @return
	 */
	public List getGdDownload(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("OrderOperactionDAO.getGdDownload",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.getGdDownload方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	};
	/**
	 * 获取最大的工单序号
	 * @param map
	 * @return
	 */
	public List getMaxGdxh(){
		try{
			return getSqlMapClientTemplate().queryForList("OrderOperactionDAO.getMaxGdxh",null);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.getMaxGdxh方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	};
	/**
	 * 公共保存方法
	 * @param map
	 * @return
	 */
	public boolean saveAll(HashMap map){
		boolean returnSaveAll = false;
		try{
			getSqlMapClientTemplate().insert("OrderOperactionDAO.saveAll",map);
			returnSaveAll = true;
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.saveAll方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return returnSaveAll;
	};
	/**
	 * 公共删除方法
	 * @param map
	 * @return
	 */
	public void removeAll(HashMap map){
		try{
			getSqlMapClientTemplate().delete("OrderOperactionDAO.removeAll",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.removeAll方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
	};
	/**
	 * 获取订单状态
	 * @param map
	 * @return
	 */
	public List getGdtype(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("OrderOperactionDAO.getGdtype",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.getGdtype方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	};
	/**
	 * 获取模组完成进度
	 * @param map
	 * @return
	 */
	public List getMzJinduList(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("OrderOperactionDAO.getMzJinduList",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.getMzJinduList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	};
	/**
	 * 获取保存后的工单ID
	 * @param map
	 * @return
	 */
	public List getGdxzId(HashMap map) {
		try{
			return getSqlMapClientTemplate().queryForList("OrderOperactionDAO.getGdxzId",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.getGdxzId方法出现异常！" + e.getMessage());
			e.printStackTrace();
		};
		return null;
	};
	/**
	 * 工单下载表获取	
	 * @param map
	 * @return
	 */
	public List getGdDownload1(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("OrderOperactionDAO.getGdDownload",map);
		}catch (Exception e) {
			Log.error("OrderOperactionDAO.getGdDownload方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	};
}