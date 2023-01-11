package com.ddmandate.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ddmandate.bean.UtilityDetails;

public class MandateDashboardDao {

	Connection con=null;
	PreparedStatement pst;
	String query;
	ResultSet rs=null;

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

	public List<UtilityDetails> getAllUtilityCodes() {

		Connection con = MandateDashboardDao.getConnection();
		List<UtilityDetails> utilitylist = new ArrayList<>();
		try {
			// query = "select * from utility_master mms_out_info_tmp";
			query = "select CDTR_AGNT_NAME,CDTR_ACCT_NO from mms_out_info_tmp";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				UtilityDetails utilityDetails = new UtilityDetails();

				utilityDetails.setCDTR_AGNT_NAME(rs.getString("CDTR_AGNT_NAME"));
				utilityDetails.setCDTR_ACCT_NO(rs.getString("CDTR_ACCT_NO"));

				utilitylist.add(utilityDetails);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
		return utilitylist;
	}

	public List<UtilityDetails> getUtilityCodeByUtilityName(String utility_name) {

		Connection con = MandateDashboardDao.getConnection();
		List<UtilityDetails> list = new ArrayList<>();
		try {
			// query = "select * from utility_master where utility_name=?";
			query = "select CDTR_ACCT_NO from mms_out_info_tmp where CDTR_AGNT_NAME=?";
			pst = con.prepareStatement(query);
			pst.setString(1, utility_name);
			rs = pst.executeQuery();
			while (rs.next()) {
				UtilityDetails utility_details = new UtilityDetails();

				// utility_details.setCDTR_AGNT_NAME(rs.getString("CDTR_AGNT_NAME"));
				utility_details.setCDTR_ACCT_NO(rs.getString("CDTR_ACCT_NO"));
				list.add(utility_details);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	
	// Added for MandateDashboardBean

	public JSONArray getMandateDetails(String utility_name, String utility_code, String to_date) {
		ResultSet rstemp=null;
		System.out.println("<<< JSONArray getMandateDetails >>>");
		System.out.println("utility_name:" + utility_name);
		System.out.println("utility_code:" + utility_code);
		System.out.println("to_date:" + to_date);

		int count = 0;

		JSONObject obj = null;
		JSONArray array = null;
		try {

			con = MandateDashboardDao.getConnection();

			// Getting

			String query = "select auth_date,BATCH_ID,CDTR_ACCT_NO ,CDTR_AGNT_NAME,MMS_TYPE from mms_out_info_tmp where auth_date =to_date('2022-07-27','YYYY-MM-DD') and CDTR_AGNT_NAME ='IDBI BANK LTD' and CDTR_ACCT_NO='IBKL00686000013568' ";
			System.out.println(query);
			PreparedStatement pst = con.prepareStatement(query);
			/*
			 * pst.setString(1, to_date); pst.setString(2, utility_name); pst.setString(3,
			 * utility_code);
			 */
			rstemp = pst.executeQuery();

			// MANDATE COUNT
			System.out.println("MANDATE COUNT >>>>");
			String query1 = "SELECT COUNT(CDTR_ACCT_NO) as MANDATE_COUNT FROM mms_out_info_tmp where CDTR_AGNT_NAME =? and CDTR_ACCT_NO=? GROUP BY CDTR_ACCT_NO ORDER BY COUNT(CDTR_ACCT_NO) DESC";
			System.out.println("MANDATE COUNT" + query1);
			PreparedStatement pst1 = con.prepareStatement(query1);
			// pst.setString(1, to_date);
			// pst1.setString(1, utility_name);
			// pst1.setString(2, utility_code);
			pst1.setString(1, "IDBI BANK LTD");
			pst1.setString(2, "IBKL00686000013568");
			ResultSet rs1 = pst1.executeQuery();

			// DATA ENTRY PENDING
			System.out.println("DATA ENTRY PENDING>>>>>>");
			String query2 = "SELECT COUNT(STATUS) as DATA_ENTRY_PENDING_STATUS FROM mms_out_info_tmp where STATUS ='N' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query2);
			PreparedStatement pst2 = con.prepareStatement(query2);
			// pst.setString(1, to_date);
			// pst2.setString(1, utility_name);
			// pst2.setString(2, utility_code);

			pst2.setString(1, "IDBI BANK LTD");
			pst2.setString(2, "IBKL00686000013568");
			ResultSet rs2 = pst2.executeQuery();

			// PENDING VERIFICATION QUEUE
			System.out.println("PENDING VERIFICATION QUEUE >>>>>>");
			String query3 = "SELECT COUNT(STATUS) AS PENDING_VERIFICATION_QUEUE FROM mms_out_info_tmp where STATUS ='MODA' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query3);
			PreparedStatement pst3 = con.prepareStatement(query3);
			// pst.setString(1, to_date)
			// pst.setString(1, utility_name);
			// pst.setString(2, utility_code);
			pst3.setString(1, "IDBI BANK LTD");
			pst3.setString(2, "IBKL00686000013568");
			ResultSet rs3 = pst3.executeQuery();

			// SENDBACK DATA ENTRY PENDING
			System.out.println("SENDBACK DATA ENTRY PENDING >>>>>>>>>>");
			String query4 = "SELECT COUNT(STATUS) AS SENDBACK_DATA_ENTRY_PENDING FROM mms_out_info_tmp where STATUS ='MOD' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query4);
			PreparedStatement pst4 = con.prepareStatement(query4);
			// pst.setString(1, to_date);
			// pst4.setString(1, utility_name);
			// pst4.setString(2, utility_code);

			pst4.setString(1, "IDBI BANK LTD");
			pst4.setString(2, "IBKL00686000013568");
			ResultSet rs4 = pst4.executeQuery();

			// PENDING VERIFICATION pending
			System.out.println("PENDING VERIFICATION >>>>>>");
			String query5 = "SELECT COUNT(STATUS) AS PENDING_VERIFICATION FROM mms_out_info_tmp where STATUS ='MODA' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query5);
			PreparedStatement pst5 = con.prepareStatement(query5);
			// pst.setString(1, to_date);
			// pst5.setString(1, utility_name);
			// pst5.setString(2, utility_code);

			pst5.setString(1, "IDBI BANK LTD");
			pst5.setString(2, "IBKL00686000013568");
			ResultSet rs5 = pst5.executeQuery();

			// SENBACK BACK FOR VERIFICATION pending
			String query6 = "SELECT COUNT(STATUS) AS SENBACK_BACK_FOR_VERIFICATION FROM mms_out_info_tmp where STATUS ='MODA' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query6);
			PreparedStatement pst6 = con.prepareStatement(query6);
			// pst.setString(1, to_date);
			// pst.setString(1, utility_name);
			// pst.setString(2, utility_code);
			pst6.setString(1, "IDBI BANK LTD");
			pst6.setString(2, "IBKL00686000013568");
			ResultSet rs6 = pst6.executeQuery();

			// verified
			String query7 = "SELECT COUNT(STATUS) AS VERIFIED FROM mms_out_info_tmp where STATUS ='CRV' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query7);
			PreparedStatement pst7 = con.prepareStatement(query7);
			// pst.setString(1, to_date);
			// pst.setString(1, utility_name);
			// pst.setString(2, utility_code);

			pst7.setString(1, "IDBI BANK LTD");
			pst7.setString(2, "IBKL00686000013568");
			ResultSet rs7 = pst7.executeQuery();

			// Rejected
			String query8 = "SELECT COUNT(STATUS) AS REJECTED FROM mms_out_info_tmp where STATUS ='CRVR' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query8);
			PreparedStatement pst8 = con.prepareStatement(query8);
			// pst.setString(1, to_date);
			// pst.setString(1, utility_name);
			// pst.setString(2, utility_code);

			pst8.setString(1, "IDBI BANK LTD");
			pst8.setString(2, "IBKL00686000013568");
			ResultSet rs8 = pst8.executeQuery();

			// ACTION PENDING
			String query9 = "SELECT COUNT(STATUS) AS ACTION_PENDING FROM mms_out_info_tmp where STATUS ='MODA' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query9);
			PreparedStatement pst9 = con.prepareStatement(query9);
			// pst.setString(1, to_date);
			// pst.setString(1, utility_name);
			// pst.setString(2, utility_code);

			pst9.setString(1, "IDBI BANK LTD");
			pst9.setString(2, "IBKL00686000013568");
			ResultSet rs9 = pst9.executeQuery();

			// ACK RECEVIED
			String query10 = "SELECT COUNT(STATUS) AS ACK_RECEVIED FROM mms_out_info_tmp where STATUS ='ACKN' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query10);
			PreparedStatement pst10 = con.prepareStatement(query10);
			// pst.setString(1, to_date);
			// pst.setString(1, utility_name);
			// pst.setString(2, utility_code);

			pst10.setString(1, "IDBI BANK LTD");
			pst10.setString(2, "IBKL00686000013568");
			ResultSet rs10 = pst10.executeQuery();

			// NACK RECEVIED
			String query11 = "SELECT COUNT(STATUS) AS NACK_RECEVIED FROM mms_out_info_tmp where STATUS ='ACKN' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query11);
			PreparedStatement pst11 = con.prepareStatement(query11);
			// pst.setString(1, to_date);
			// pst.setString(1, utility_name);
			// pst.setString(2, utility_code);

			pst11.setString(1, "IDBI BANK LTD");
			pst11.setString(2, "IBKL00686000013568");
			ResultSet rs11 = pst11.executeQuery();

			array = new JSONArray();

			System.out.println("<<<< JSONArray >>>>");
			
			if (rstemp.next() && rstemp != null) {

				System.out.println("<<<< rstemp.next() >>>>");
				obj = new JSONObject();
				obj.put("FILE_NAME", rstemp.getString("BATCH_ID"));
				obj.put("UTILITY_NAME", rstemp.getString("CDTR_AGNT_NAME"));
				obj.put("UTILITY_CODE", rstemp.getString("CDTR_ACCT_NO"));
				obj.put("MMS_TYPE", rstemp.getString("MMS_TYPE"));

			}

			while (rs1.next() && rs1 != null) {
				obj = new JSONObject();

				obj.put("MANDATE_TYPE", rs1.getString("MANDATE_COUNT"));

			}

			while (rs2.next() && rs2 != null) {
				obj = new JSONObject();

				obj.put("DATA_ENTRY_PENDING_STATUS", rs2.getString("DATA_ENTRY_PENDING_STATUS"));

			}

			while (rs3.next() && rs3 != null) {
				obj = new JSONObject();

				obj.put("PENDING_VERIFICATION_QUEUE", rs3.getString("PENDING_VERIFICATION_QUEUE"));

			}

			while (rs4.next() && rs4 != null) {
				obj = new JSONObject();

				obj.put("SENDBACK_DATA_ENTRY_PENDING", rs4.getString("SENDBACK_DATA_ENTRY_PENDING"));

			}

			while (rs5.next() && rs5 != null) {
				obj = new JSONObject();

				obj.put("PENDING_VERIFICATION", rs5.getString("PENDING_VERIFICATION"));

			}

			while (rs6.next() && rs6 != null) {
				obj = new JSONObject();

				obj.put("SENBACK_BACK_FOR_VERIFICATION", rs6.getString("SENBACK_BACK_FOR_VERIFICATION"));

			}

			while (rs7.next() && rs7 != null) {
				obj = new JSONObject();

				obj.put("VERIFIED", rs7.getString("VERIFIED"));

			}

			while (rs8.next() && rs8 != null) {
				obj = new JSONObject();

				obj.put("REJECTED", rs8.getString("REJECTED"));

			}

			while (rs9.next() && rs9 != null) {
				obj = new JSONObject();

				obj.put("ACTION_PENDING", rs9.getString("ACTION_PENDING"));

			}

			while (rs10.next() && rs10 != null) {
				obj = new JSONObject();

				obj.put("ACK_RECEIVED", rs10.getString("ACK_RECEVIED"));

			}

			while (rs11.next() && rs11 != null) {
				obj = new JSONObject();

				obj.put("NACK_RECEIVED", rs11.getString("NACK_RECEIVED"));

			}

			array.put(obj.toString());

		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		} finally {

			
			if (pst != null) {

			}
			if (con != null) {

			}
		}
		return array;

	}

}
