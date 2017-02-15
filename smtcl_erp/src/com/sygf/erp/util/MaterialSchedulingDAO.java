package com.sygf.erp.util;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sygf.util.Log;

public class MaterialSchedulingDAO extends SqlMapClientDaoSupport {

	
	
	
	public List getListDingDan(){
		try {
			return getSqlMapClientTemplate().queryForList("MaterialSchedulingDAO.getListDingDan", null);
		} catch (Exception e) {
			Log.error("MaterialSchedulingDAO.getListDingDan方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List getList(HashMap map){
		try {
			return getSqlMapClientTemplate().queryForList("MaterialSchedulingDAO.getList", map);
		} catch (Exception e) {
			Log.error("MaterialSchedulingDAO.getList方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public List getGoodsPos(){
		try {
			return getSqlMapClientTemplate().queryForList("MaterialSchedulingDAO.getGoodsPos", null);
		} catch (Exception e) {
			Log.error("MaterialSchedulingDAO.getGoodsPos方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
