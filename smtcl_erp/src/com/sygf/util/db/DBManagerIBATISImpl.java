package com.sygf.util.db;

import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.sygf.util.Log;
import com.sygf.util.ResourceReader;
import com.sygf.util.db.DBManager;
import com.sygf.util.db.IDBManager;

public class DBManagerIBATISImpl implements IDBManager {

	private Object serialObject = new Object();
	
	private SqlMapClient sqlMap = null;
	
	private SqlMapClient otherMap = null;
	
	/* （非 Javadoc�?
	 * @see com.huahong.util.db.IDBManager#freeConnection(java.sql.ResultSet, java.sql.PreparedStatement, java.sql.Connection)
	 */
	public void freeConnection(ResultSet rs, PreparedStatement ps,
			Connection conn) {
		try{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
			if(conn != null){
				conn.close();
			}
		}catch(SQLException se){
			Log.error("释放数据库错误！");
		}
	}

	/* （非 Javadoc�?
	 * @see com.huahong.util.db.IDBManager#getConnection()
	 */
	public Connection getConnection() {
		if(sqlMap == null){
			sqlMap = getMapClient();
		}
		try{
			return sqlMap.getDataSource().getConnection();
		} catch(SQLException se){
			Log.error("数据库连接管理类获取连接出错" + se.getMessage());
			return null;
		} catch(Exception e){
			Log.error("数据库连接管理类获取连接出错" + e.getMessage());
			return null;
		}
	}

	/* （非 Javadoc
	 * @see com.huahong.util.db.IDBManager#getConnection()
	 */
	public Connection getConnection(String source) {
		if(otherMap == null){
			otherMap = getMapClient();
		}
		try{
			return otherMap.getDataSource().getConnection();
		} catch(SQLException se){
			Log.error("数据库连接管理类获取连接出错" + se.getMessage());
			return null;
		} catch(Exception e){
			Log.error("数据库连接管理类获取连接出错" + e.getMessage());
			return null;
		}
	}
	
	/* （非 Javadoc
	 * @see com.huahong.util.db.IDBManager#getMapClient()
	 */
	public SqlMapClient getMapClient() {
		if (sqlMap == null) {
			Reader reader = null;
			try {
				reader = Resources.getResourceAsReader(DBManager.props.getProperty("config.xml.path"));
				Properties loProp = ResourceReader.getResourcesFromPackage(DBManager.props.getProperty("config.properties.path"), getClass());
				loProp = ResourceReader.getDecodedResources(loProp);
				sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader, loProp); // 获取SqlMapClient对象
				
			} catch (Exception e) {
				e.printStackTrace();
				Log.error("获取DAO出错" + e.getMessage());
				
			} finally {
				try {
					reader.close();
				} catch (Exception fe) {
					Log.error("资源释放出现异常" + fe.getMessage());
				}
			}
		}
		return sqlMap;
	}

	/**
	 * 多例模式
	 */
	public SqlMapClient getMapClient(String source) {
		synchronized (serialObject){
			Reader reader = null;
			try {
				reader = Resources.getResourceAsReader(DBManager.props.getProperty(source));
				Properties loProp = ResourceReader.getResourcesFromPackage(DBManager.props.getProperty(source + ".config"), getClass());
				loProp = ResourceReader.getDecodedResources(loProp);
				otherMap = SqlMapClientBuilder.buildSqlMapClient(reader, loProp); // 获取SqlMapClient对象
				
			} catch (Exception e) {
				e.printStackTrace();
				Log.error("获取DAO出错" + e.getMessage());
				
			} finally {
				try {
					reader.close();
				} catch (Exception fe) {
					Log.error("资源释放出现异常" + fe.getMessage());
				}
			}
			return otherMap;
		}
	}

}
