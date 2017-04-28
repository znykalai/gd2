package com.sygf.erp.baseData.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sygf.util.Log;

public class HomeActionDAO extends SqlMapClientDaoSupport {
	/**
	 * 获取缓存库状态
	 * @param map
	 * @return
	 */
	public List getHckState(){
		try{
			return getSqlMapClientTemplate().queryForList("HomeActionDAO.getHckState",null);
		}catch (Exception e) {
			Log.error("HomeActionDAO.getHckState方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 工单完成率
	 * @return
	 */
	public List getGdWanChengLv(){
		try{
			return getSqlMapClientTemplate().queryForList("HomeActionDAO.getGdWanChengLv",null);
		}catch (Exception e) {
			Log.error("HomeActionDAO.getGdWanChengLv方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 货位使用率
	 * @return
	 */
	public List getHWshiYongLv() {
		try{
			return getSqlMapClientTemplate().queryForList("HomeActionDAO.getHWshiYongLv",null);
		}catch (Exception e) {
			Log.error("HomeActionDAO.getHWshiYongLv方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 物料请求，大立体库
	 * @return
	 */
	public List getDltk() {
		try{
			return getSqlMapClientTemplate().queryForList("HomeActionDAO.getDltk",null);
		}catch (Exception e) {
			Log.error("HomeActionDAO.getDltk方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}