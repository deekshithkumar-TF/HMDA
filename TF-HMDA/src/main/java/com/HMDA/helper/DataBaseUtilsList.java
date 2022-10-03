package com.HMDA.helper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.HMDA.baseclass.BaseTest;
import com.HMDA.reports.Logs;

public class DataBaseUtilsList extends BaseTest {

	public static List<String> getResultFromDatabaseAcc(String sql) {

		//Properties prop = BasePage.prop;
		Connection connection;
		Map<String, String> headerValuePairs = new LinkedHashMap<>();
		List<String> columnV=new ArrayList<String>();
		try {
			connection = DriverManager.getConnection(
					"jdbc:sqlserver://sqlserver-cpss.database.windows.net;database=CreditServicesMasterDBQA;integratedSecurity=false;",
					"cpssdev","Sa123456!");
			Thread.sleep(4000);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery( sql );
			ResultSetMetaData rsm = resultSet.getMetaData();

			int columnsNumber = rsm.getColumnCount();

			int rowCount = 0;
			while (resultSet.next()) {
				++rowCount;
				for (int i = 1; i <= columnsNumber; i++) {
					headerValuePairs.put(rsm.getColumnName(i), resultSet.getString(i));
					columnV.add(resultSet.getString(i).trim());
					
//					Logs.info( rsm.getColumnName( i ) + " = " + resultSet.getString( i ) );
				}
			}
			connection.close();

			if (rowCount == 0)
				Logs.info( "No records found for query = " + sql);
			//return headerValuePairs;
			return columnV;

		} catch(Exception ex) {
			throw new AutomationException( "DB error " + ex.getMessage() );
		}
	}
	/*
	 * static Map<String, String> fetchRecord(String table, String orderId) {
	 * 
	 * String sql = "SELECT * FROM " + table + " WHERE OrderId = " + orderId;
	 * System.out.println("Select query = " + sql); return getResultFromDatabaseAcc(
	 * sql ); }
	 * 
	 * static Map<String, String> fetchTopRecordBasedOnJoin(String table, String
	 * joinTable) {
	 * 
	 * String sql = "SELECT TOP 1 " + joinTable + ".* FROM " + table +
	 * " INNER JOIN " + joinTable + " ON " + table + ".TradeLineId = " + joinTable +
	 * ".TradeLineId" + " order by OrderId desc";
	 * System.out.println("Select query = " + sql); return getResultFromDatabaseAcc(
	 * sql ); }
	 */
}
