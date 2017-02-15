package com.sygf.util;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{ 
	static Logger log = Logger.getLogger("DynamicDataSource");
	
	public Object unwrap(Class iface) throws SQLException{
		return null;
	}
	public boolean isWrapperFor(Class iface) throws SQLException{
		return false;
	}
	
	protected Object determineCurrentLookupKey() {
		return DbContextHolder.getDbType();
	}
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return log;
	}

}