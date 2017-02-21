package com.sygf.erp.systemSecurity.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sygf.util.Log;

public class LoginDAO extends SqlMapClientDaoSupport {
	
	public List getUserPassword(HashMap map){
		try{
			return getSqlMapClientTemplate().queryForList("LoginDAO.getUserPassword",map);
		}catch (Exception e) {
			Log.error("LoginDAO.getUserPassword方法出现异常！" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
