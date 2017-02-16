package com.sygf.erp.baseData.dao;

import java.util.HashMap;
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
}