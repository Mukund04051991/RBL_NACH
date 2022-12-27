package com.ddmandate.dao;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

public class MandateFilesDao {

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

	public JSONArray getMandateFileDetails(String upload_date) {
		System.out.println("upload_date:" + upload_date);

		int count = 0;
		Connection con = null;
		PreparedStatement ps = null;

		JSONObject obj = null;
		JSONArray array = null;
		try {

			con = MandateFilesDao.getConnection();

			ps = con.prepareStatement(
					"select UTILITY_NAME,UTILITY_CODE,UPLOAD_FILE_NAME,UPLOAD_DATE,settlement_date,TOTAL_COUNT,TOTAL_AMOUNT,SUCCESS_COUNT,REJECTED_COUNT,MAKER_ID from nach_dd_in_file_info where  TO_DATE(UPLOAD_DATE,'DD-MM-YY')=TO_DATE(?,'DD-MM-YYYY')");

			ps.setString(1, upload_date);
			array = new JSONArray();
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				obj = new JSONObject();

				obj.put("UTILITY_NAME", rs.getString("UTILITY_NAME"));
				obj.put("UTILITY_CODE", rs.getString("UTILITY_CODE"));
				obj.put("UPLOAD_FILE_NAME", rs.getString("UPLOAD_FILE_NAME"));//
				obj.put("UPLOAD_DATE", rs.getString("UPLOAD_DATE"));//
				obj.put("settlement_date", rs.getString("settlement_date"));
				obj.put("TOTAL_COUNT", rs.getString("TOTAL_COUNT"));//
				obj.put("TOTAL_AMOUNT", rs.getString("TOTAL_AMOUNT"));//
				obj.put("SUCCESS_COUNT", rs.getString("SUCCESS_COUNT"));// SUCCESS_COUNT
				obj.put("REJECTED_COUNT", rs.getString("REJECTED_COUNT"));// REJECTED_COUNT
				obj.put("MAKER_ID", rs.getString("MAKER_ID"));// MAKER_ID
				array.put(obj.toString());
			}

		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
		return array;

	}

	// verify mandate files
	public boolean verifyMandateFile(String filename) {

		boolean status = false;
		PreparedStatement pstmtUpdate = null;
		Connection con = null;
		con = MandateFilesDao.getConnection();
		try {
			pstmtUpdate = con.prepareStatement("UPDATE USER_TEMP_FILE_INFO SET FILE_STATUS=? WHERE FILE_NAME =?");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// move file from path1 to path2
		ResourceBundle rbPath = ResourceBundle.getBundle("MMS_SPONSOR");
		String sourcePath = rbPath.getString("CLIENT_SOURCE_FILE_PATH");
		String targetPath = rbPath.getString("CLIENT_TARGET_FILE_PATH");

		String newfilename = filename.substring(0, filename.length());
		System.out.println("newfilename:" + newfilename);
		String[] filelist = newfilename.split(",");
		for (int i = 0; i <= filelist.length - 1; i++) {

			try {

				status = new File(sourcePath + File.separator + filelist[i])
						.renameTo(new File(targetPath + File.separator + filelist[i]));
				System.out.println("moved:" + filelist[i]);
				if (status) {
					pstmtUpdate.setString(1, "ACCEPTED");
				} else {
					pstmtUpdate.setString(1, "ERROR");
				}
				pstmtUpdate.setString(2, filelist[i]);
				int u = pstmtUpdate.executeUpdate();
				if (u > 0) {
					System.out.println("Verified successfully:" + filelist[i]);
				}

				// update file status as verified
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(System.out);

			}

		}
		return status;

	}

	// reject mandate files
	public boolean rejectMandateFile(String filename) {

		// update file status as rejected
		boolean status = false;
		Connection con = null;
		PreparedStatement pstmtUpdateRejected = null;

		String newfilename = filename.substring(0, filename.length());
		System.out.println("newfilename:" + newfilename);
		String[] filelist = newfilename.split(",");
		for (int i = 0; i <= filelist.length - 1; i++) {

			try {
				con = MandateFilesDao.getConnection();
				pstmtUpdateRejected = con
						.prepareStatement("UPDATE USER_TEMP_FILE_INFO SET FILE_STATUS=? WHERE FILE_NAME =?");
				pstmtUpdateRejected.setString(1, "REJECTED");
				pstmtUpdateRejected.setString(2, filelist[i]);
				int u = pstmtUpdateRejected.executeUpdate();
				if (u > 0) {
					System.out.println("Rejected successfully:" + filelist[i]);
					status = true;
				} else {
					status = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				status = false;
			}
		}
		return status;
	}

}
