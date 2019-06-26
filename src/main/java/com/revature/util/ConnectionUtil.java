package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.util.ConnectionUtil;

public class ConnectionUtil {

	private static final Logger logger = Logger.getLogger(ConnectionUtil.class);
	
	/**
	 * This should be used when deploying in a Tomcat server
	 */
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			logger.warn("Exception thrown adding oracle driver.", e);
		}
	}	
		private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class);
		
		public static Connection getConnection() throws SQLException {

			String url = "jdbc:oracle:thin:@bankdbinstance.cd85qhigqy8i.us-east-2.rds.amazonaws.com:1521:ORCL";
			String username = "admin";
			String password = "p4ssw0rd";
			
			return DriverManager.getConnection(url, username, password);
			
		}
		
		public static void main(String[] args) {
			try {
				getConnection();
				logger.info("Connection successful.");
			} catch (SQLException e) {
				logger.error("Could not connect.", e);
			}
		}
		
}
