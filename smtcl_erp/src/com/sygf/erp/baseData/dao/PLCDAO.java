package com.sygf.erp.baseData.dao;

import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sygf.util.Log;

public class PLCDAO extends SqlMapClientDaoSupport {
	
	/**
	 * 故障处理以及恢复
	 * @param map
	 */
	public boolean gwGzUpdate(HashMap map) {
		boolean yesNo=false;
		try{
			getSqlMapClientTemplate().insert("PLCDAO.gwGzUpdate",map);
			yesNo=true;
		}catch (Exception e) {
			Log.error("PLCDAO.gwGzUpdate方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return yesNo;
	}
}
