package com.sygf.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ibatis.sqlmap.client.SqlMapClient;

public interface IDBManager {
	
	/**
	 * 获取个数据库连接
	 * NetPatrol系统自身使用
	 * 
	 * @return Connection
	 */
	public Connection getConnection();
	
	/**
	 * 获取个数据库连接
	 * NetPatrol使用其他系统的数据库使用
	 * @return
	 */
	public Connection getConnection(String source);
	
	/**
	 * 获取iBATIS的MapClient对象
	 * NetPatrol系统自身使用。为单例模式
	 * 
	 * @return SqlMapClient
	 */
	public SqlMapClient getMapClient();
	
	/**
	 * 根据数据源的不同取不同的MapClient
	 * 为多例模式?
	 * 
	 * @param source
	 * @return
	 */
	public SqlMapClient getMapClient(String source);
	
	/**
	 * 对数据库资源的释放?
	 * 
	 * @param rs
	 * @param ps
	 * @param conn
	 */
	public void freeConnection(ResultSet rs, PreparedStatement ps, Connection conn);

}
