package com.HMDA.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import com.HMDA.baseclass.BaseTest;
import com.HMDA.reports.Logs;

public class DataBaseUtils extends BaseTest {

	public static Map<String, String> getResultFromDatabase(String sql) {

		//Properties prop = BasePage.prop;
		Connection connection;
		Map<String, String> headerValuePairs = new LinkedHashMap<>();
		try {
			connection = DriverManager.getConnection(dbUrl,"cpssdev","Sa123456!");
			Thread.sleep(6000);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery( sql );
			ResultSetMetaData rsm = resultSet.getMetaData();

			int columnsNumber = rsm.getColumnCount();

			int rowCount = 0;
			while (resultSet.next()) {
				++rowCount;
				for (int i = 1; i <= columnsNumber; i++) {
					headerValuePairs.put(rsm.getColumnName(i), resultSet.getString(i));
//					Logs.info( rsm.getColumnName( i ) + " = " + resultSet.getString( i ) );
				}
			}
			connection.close();

			if (rowCount == 0)
				Logs.info( "No records found for query = " + sql);
			return headerValuePairs;

		} catch(Exception ex) {
			throw new AutomationException( "DB error " + ex.getMessage() );
		}
	}

	static Map<String, String> fetchRecord(String table, String orderId) {

		String sql = "SELECT * FROM " + table + " WHERE OrderId = " + orderId;
		System.out.println("Select query = " + sql);
		return getResultFromDatabase( sql );
	}

	static Map<String, String> fetchTopRecordBasedOnJoin(String table, String joinTable) {

		String sql = "SELECT TOP 1 " + joinTable + ".* FROM " + table +
				" INNER JOIN " + joinTable + " ON " + table + ".TradeLineId = " + joinTable + ".TradeLineId"
				+ " order by OrderId desc";
		System.out.println("Select query = " + sql);
		return getResultFromDatabase( sql );
	}

}
