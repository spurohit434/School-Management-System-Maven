package com.wg.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static Connection connection;

	public static Connection getConnection() throws ClassNotFoundException {
		if (connection == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "mysql");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}