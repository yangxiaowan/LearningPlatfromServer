package com.learningplatfrom.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatabaseUtils {
	// jdbc:mysql://172.19.55.5:3306/student
	// jdbc:mysql://192.168.56.1:3306/student
	// private static final String databasePath =
	// "jdbc:mysql://localhost:3306/learningplatfrom";
	private static final String databasePath = "jdbc:mysql://121.42.202.208:3306/learningplatfrom";
	private static final String driverPath = "com.mysql.jdbc.Driver";
	private String userName = "root";
	private String password = "beyonddream";
	Connection connection = null;

	public BaseDatabaseUtils() {
		try {
			Class.forName(driverPath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("加载数据库驱动失败");
		}
		System.out.println("加载数据库驱动成功");
	}

	/**
	 * @return 返回数据库的连接对象Connection
	 */
	public Connection getDatabaseConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(databasePath,
						userName, password);
				System.out.println("连接到数据库成功");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("连接到数据库出现异常");
			}
		}
		return connection;
	}

	/**
	 * @param sqlStatement
	 *            字符串，数据库更新语句
	 * @return 返回一个布尔类型，判断更新是否成功，成功返回true，失败返回false
	 */
	public boolean UpDateDatabase(String sqlStatement) {
		if (connection != null) {
			try {
				Statement statement = connection.createStatement();
				statement.executeUpdate(sqlStatement);
				statement.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("更新数据库出现异常UpDateDatabase()");
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param tableName
	 *            数据库中表的名字
	 * @param firstItem
	 *            第一个项目
	 * @param secondItem
	 *            第二个项目
	 * @param firstValue
	 *            第一个值
	 * @param secondValue
	 *            第二个值
	 * @return
	 * @throws SQLException 
	 */
	public boolean isRightNote(String tableName, String firstItem,
			String secondItem, String firstValue, String secondValue) throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;  //结果集合
		String queryString = "SELECT "+firstItem+" FROM "+tableName+" WHERE "+secondItem+" = '"+secondValue+"'";
		try {
			statement = connection.createStatement();
			statement.execute(queryString);
			resultSet = statement.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("isRightNode() abnormal@");
		}
		resultSet.next();
		String queryedPassword = resultSet.getString("mPassword");
		if(queryedPassword.equals(firstValue)){
			return true;
		}
		return false;
	}

	/**
	 * @param queryStr
	 *            字符串，查询语句
	 * @return ResultSet 返回在数据库中查询的结果集合
	 */
	public ResultSet getQueryResult(String queryStr) {
		ResultSet result = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeQuery(queryStr);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据库查询出现异常getQueryResult()");
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @param tableName
	 *            字符串 数据库中的表名
	 * @param rowName
	 *            某一记录的某字段中的值
	 * @return 该值是否在数据库中对应有记录，如果有，则返回true,否则返回false
	 */
	public boolean isExsitNote(String tableName, String rowName, String valueStr) {
		// 根据数据库中某一字段查询该字段在数据库中是否存在记录，该方法用于查找主键或者唯一标识记录的字段
		String selectStr = "SELECT " + rowName + " FROM " + tableName
				+ " WHERE " + rowName + "='" + valueStr + "'";
		System.out.println(selectStr);
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute(selectStr);
			ResultSet set = statement.getResultSet();
			if (set.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print("isExsitNode()方法出现异常");
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
