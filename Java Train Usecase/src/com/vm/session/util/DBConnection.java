package com.vm.session.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public static Connection getConnection() throws Exception
	{
		return DriverManager.getConnection(JDBCProperties.URL,JDBCProperties.USERNAME,JDBCProperties.PASSWORD);
	}
}
