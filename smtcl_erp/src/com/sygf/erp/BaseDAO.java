package com.sygf.erp;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sygf.util.db.DBManager;
import com.sygf.util.db.IDBManager;

public class BaseDAO {

	public IDBManager dbm = null;

	public SqlMapClient dao = null;

	public BaseDAO(){
		dbm = DBManager.getDBManager();
		dao = dbm.getMapClient();
	}

}
