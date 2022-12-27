package com.ddmandate.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReportDao {

	public static Connection getConnection() {

		ResourceBundle rbDB = ResourceBundle.getBundle("DB");
		String DRIVER = rbDB.getString("DRIVER");
		String CONN = rbDB.getString("CONN");
		String IP = rbDB.getString("IP");
		String PORT = rbDB.getString("PORT");
		String DBNAME = rbDB.getString("DBNAME");
		String USERNAME = rbDB.getString("USERNAME");
		String PASSWORD = rbDB.getString("PASSWORD");
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(CONN + IP + PORT + DBNAME, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return con;
	}

	public JSONArray getDatabyDate(String fromdate, String todate) {
		System.out.println("getDatabyDate " + "fromdate:" + fromdate + " " + "todate:" + todate);

		int count = 0;
		Connection con = null;
		PreparedStatement ps = null;

		JSONObject obj = null;
		JSONArray array = null;
		try {

			con = ReportDao.getConnection();

			ps = con.prepareStatement(
					"select * from transaction_data where txn_date between TO_DATE(?,'MM-DD-YYYY') and TO_DATE(?,'MM-DD-YYYY')");

			ps.setString(1, fromdate);
			ps.setString(2, todate);
			array = new JSONArray();
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				obj = new JSONObject();

				obj.put("TXN_ID", rs.getString(1));
				obj.put("TXN_DATE", rs.getString(2));
				obj.put("CUST_NAME", rs.getString(3));//
				obj.put("POST_FLAG", rs.getString(4));//
				obj.put("TRAN_REMARKS", rs.getString(5));
				array.put(obj.toString());
			}

		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
		return array;

	}

}
