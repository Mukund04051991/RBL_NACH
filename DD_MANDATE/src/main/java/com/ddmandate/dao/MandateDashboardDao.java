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

	Connection con = null;
	PreparedStatement pst;
	String query;
	ResultSet rs = null;

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

	
	public JSONArray getMandateDetails(String utility_name, String utility_code,
			 String to_date) { ResultSet rstemp=null;
			  System.out.println("<<< JSONArray getMandateDetails >>>");
			  System.out.println("utility_name:" + utility_name);
			  System.out.println("utility_code:" + utility_code);
			  System.out.println("to_date:" + to_date);
			 
			  int count = 0;
			 
			  JSONObject obj = null; JSONArray array = null; try {
			  
			  con = MandateDashboardDao.getConnection();
			  
			  // Getting
			  
			  String query ="select BATCH_ID,CDTR_ACCT_NO ,CDTR_AGNT_NAME,MMS_TYPE from mms_out_info_tmp where auth_date =to_date('2022-07-27','YYYY-MM-DD') and CDTR_AGNT_NAME ='IDBI BANK LTD' and CDTR_ACCT_NO='IBKL00686000013568' ";
			  System.out.println(query); 
			  PreparedStatement pst = con.prepareStatement(query);
			  
			  //pst.setString(1, to_date);
			 // pst.setString(2, utility_name);
			 // pst.setString(3, utility_code);
			  
			  rstemp = pst.executeQuery();
			  
			  // MANDATE COUNT System.out.println("MANDATE COUNT >>>>"); 
			  String query1 ="SELECT COUNT(CDTR_ACCT_NO) as MANDATE_COUNT FROM mms_out_info_tmp where CDTR_AGNT_NAME =? and CDTR_ACCT_NO=? GROUP BY CDTR_ACCT_NO ORDER BY COUNT(CDTR_ACCT_NO) DESC"; 
			  System.out.println("MANDATE COUNT" + query1); 
			  PreparedStatement pst1 = con.prepareStatement(query1); 
			  
			  // pst1.setString(1, utility_name); 
			  // pst1.setString(2, utility_code);
			  pst1.setString(1, "IDBI BANK LTD"); 
			  pst1.setString(2, "IBKL00686000013568");
			  ResultSet rs1 = pst1.executeQuery();
			  
			  // DATA ENTRY PENDING System.out.println("DATA ENTRY PENDING>>>>>>"); 
			  String
			  query2 =
			  "SELECT COUNT(STATUS) as DATA_ENTRY_PENDING_STATUS FROM mms_out_info_tmp where STATUS ='N' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC"; 
			  System.out.println(query2); 
			  PreparedStatement pst2 =con.prepareStatement(query2);
			  
			    // pst2.setString(1, utility_name); 
			  // pst2.setString(2, utility_code);
			  
			  pst2.setString(1, "IDBI BANK LTD"); 
			  pst2.setString(2, "IBKL00686000013568");
			  ResultSet rs2 = pst2.executeQuery();
			  
			  // PENDING VERIFICATION QUEUE
			  System.out.println("PENDING VERIFICATION QUEUE >>>>>>"); String query3 =
			  "SELECT COUNT(STATUS) AS PENDING_VERIFICATION_QUEUE FROM mms_out_info_tmp where STATUS ='MODA' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC"; 
			  System.out.println(query3); 
			  PreparedStatement pst3 = con.prepareStatement(query3); 
			 
			  //pst3.setString(1, utility_name); 
			  // pst3.setString(2, utility_code);
			  pst3.setString(1, "IDBI BANK LTD"); 
			  pst3.setString(2, "IBKL00686000013568");
			  ResultSet rs3 = pst3.executeQuery();
			  
			  // SENDBACK DATA ENTRY PENDING
			  System.out.println("SENDBACK DATA ENTRY PENDING >>>>>>>>>>"); String query4 =
			  "SELECT COUNT(STATUS) AS SENDBACK_DATA_ENTRY_PENDING FROM mms_out_info_tmp where STATUS ='MOD' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC"; 
			  System.out.println(query4); 
			  PreparedStatement pst4 = con.prepareStatement(query4); 
			  //pst4.setString(1, utility_name); // 
			  //pst4.setString(2, utility_code);
			  
			  pst4.setString(1, "IDBI BANK LTD"); 
			  pst4.setString(2, "IBKL00686000013568");
			  ResultSet rs4 = pst4.executeQuery();
			  
			  // PENDING VERIFICATION pending
			  System.out.println("PENDING VERIFICATION >>>>>>"); String query5 =
			  "SELECT COUNT(STATUS) AS PENDING_VERIFICATION FROM mms_out_info_tmp where STATUS ='MODA' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC"; 
			  System.out.println(query5); 
			  PreparedStatement pst5 = con.prepareStatement(query5); 
			  // pst.setString(1, to_date); 
			  
			  //pst5.setString(1, utility_name); 
			  //pst5.setString(2, utility_code);
			  
			  pst5.setString(1, "IDBI BANK LTD"); 
			  pst5.setString(2, "IBKL00686000013568");
			  ResultSet rs5 = pst5.executeQuery();
			  
			  // SENBACK BACK FOR VERIFICATION pending 
			  String query6 =
			  "SELECT COUNT(STATUS) AS SENBACK_BACK_FOR_VERIFICATION FROM mms_out_info_tmp where STATUS ='MODA' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC"; 
			  System.out.println(query6); 
			  PreparedStatement pst6 = con.prepareStatement(query6);
			  
			  //pst.setString(1, utility_name); 
			  //pst.setString(2, utility_code);
			  pst6.setString(1, "IDBI BANK LTD"); 
			  pst6.setString(2, "IBKL00686000013568");
			  ResultSet rs6 = pst6.executeQuery();
			  
			  // verified 
			  String query7 =
			  "SELECT COUNT(STATUS) AS VERIFIED FROM mms_out_info_tmp where STATUS ='CRV' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			  System.out.println(query7);
			  PreparedStatement pst7 = con.prepareStatement(query7); 
			  
			 // pst.setString(1, utility_name); 
			  // pst.setString(2, utility_code);
			  
			  pst7.setString(1, "IDBI BANK LTD"); 
			  pst7.setString(2, "IBKL00686000013568");
			  ResultSet rs7 = pst7.executeQuery();
			  
			  // Rejected 
			  String query8 =
			  "SELECT COUNT(STATUS) AS REJECTED FROM mms_out_info_tmp where STATUS ='CRVR' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC"; 
			  System.out.println(query8); 
			  PreparedStatement pst8 = con.prepareStatement(query8); 
			  
			  //pst8.setString(1, utility_name); 
			  //pst8.setString(2, utility_code);
			  
			  pst8.setString(1, "IDBI BANK LTD"); 
			  pst8.setString(2, "IBKL00686000013568");
			  ResultSet rs8 = pst8.executeQuery();
			  
			  // ACTION PENDING 
			  String query9 =
			  "SELECT COUNT(STATUS) AS ACTION_PENDING FROM mms_out_info_tmp where STATUS ='MODA' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC"; 
			  System.out.println(query9); 
			  PreparedStatement pst9 = con.prepareStatement(query9);
			
			  // pst.setString(1, utility_name); 
			  // pst.setString(2, utility_code);
			  
			  pst9.setString(1, "IDBI BANK LTD"); 
			  pst9.setString(2, "IBKL00686000013568");
			  ResultSet rs9 = pst9.executeQuery();
			  
			  // ACK RECEVIED 
			  String query10 =
			  "SELECT COUNT(STATUS) AS ACK_RECEVIED FROM mms_out_info_tmp where STATUS ='ACKN' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC"; 
			  System.out.println(query10); 
			  PreparedStatement pst10 = con.prepareStatement(query10);
			
			  //pst.setString(1, utility_name);
			  // pst.setString(2, utility_code);
			  
			  pst10.setString(1, "IDBI BANK LTD"); 
			  pst10.setString(2,"IBKL00686000013568"); 
			  ResultSet rs10 = pst10.executeQuery();
			  
			  // NACK RECEVIED 
			  String query11 =
			  "SELECT COUNT(STATUS) AS NACK_RECEVIED FROM mms_out_info_tmp where STATUS ='ACKN' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC"; 
			  System.out.println(query11); 
			  PreparedStatement pst11 = con.prepareStatement(query11); 
			  // pst.setString(1, to_date); //
			 // pst.setString(1, utility_name); 
			  //pst.setString(2, utility_code);
			  
			  pst11.setString(1, "IDBI BANK LTD"); 
			  pst11.setString(2,"IBKL00686000013568"); 
			  ResultSet rs11 = pst11.executeQuery();
			  
			  array = new JSONArray();
			  
			  System.out.println("<<<< JSONArray >>>>");
			  
			  while (rstemp.next() && rstemp != null) {
			  
			  System.out.println("<<<< rstemp.next() >>>>"); 
			  obj = new JSONObject();
			  obj.put("FILE_NAME", rstemp.getString("BATCH_ID")); 
			  obj.put("UTILITY_NAME", rstemp.getString("CDTR_ACCT_NO")); 
			  obj.put("UTILITY_CODE", rstemp.getString("CDTR_AGNT_NAME")); 
			  obj.put("MMS_TYPE", rstemp.getString("MMS_TYPE"));
			  
			  }
			  
			  while (rs1.next() && rs1 != null) {
				  obj = new JSONObject();
			  
			  obj.put("MANDATE_TYPE", rs1.getString("MANDATE_COUNT"));
			  
			  }
			  
			  while (rs2.next() && rs2 != null) { 
				  obj = new JSONObject();
			  
			  obj.put("DATA_ENTRY_PENDING_STATUS",rs2.getString("DATA_ENTRY_PENDING_STATUS"));
			  
			  }
			  
			  while (rs3.next() && rs3 != null) { 
				  obj = new JSONObject();
			  
			  obj.put("PENDING_VERIFICATION_QUEUE", rs3.getString("PENDING_VERIFICATION_QUEUE"));
			  
			  }
			  
			  while (rs4.next() && rs4 != null) { obj = new JSONObject();
			  
			  obj.put("SENDBACK_DATA_ENTRY_PENDING",
			  rs4.getString("SENDBACK_DATA_ENTRY_PENDING"));
			  
			  }
			  
			  while (rs5.next() && rs5 != null) { obj = new JSONObject();
			  
			  obj.put("PENDING_VERIFICATION", rs5.getString("PENDING_VERIFICATION"));
			  
			  }
			  
			  while (rs6.next() && rs6 != null) { obj = new JSONObject();
			  
			  obj.put("SENBACK_BACK_FOR_VERIFICATION",
			  rs6.getString("SENBACK_BACK_FOR_VERIFICATION"));
			  
			  }
			  
			  while (rs7.next() && rs7 != null) { obj = new JSONObject();
			  
			  obj.put("VERIFIED", rs7.getString("VERIFIED"));
			  
			  }
			  
			  while (rs8.next() && rs8 != null) { obj = new JSONObject();
			  
			  obj.put("REJECTED", rs8.getString("REJECTED"));
			  
			  }
			  
			  while (rs9.next() && rs9 != null) { obj = new JSONObject();
			  
			  obj.put("ACTION_PENDING", rs9.getString("ACTION_PENDING"));
			  
			  }
			  
			  while (rs10.next() && rs10 != null) { obj = new JSONObject();
			  
			  obj.put("ACK_RECEIVED", rs10.getString("ACK_RECEVIED"));
			  
			  }
			  
			  while (rs11.next() && rs11 != null) { obj = new JSONObject();
			  
			  obj.put("NACK_RECEIVED", rs11.getString("NACK_RECEIVED"));
			  
			  }
			  
			  array.put(obj.toString());
			  
			  } catch (Exception ex) { ex.printStackTrace(System.out); } finally {
			  
			  
			  if (pst != null) {
			  
			  } if (con != null) {
			  
			  } } return array;
			  
			  }
			 
	// added on 12012023

	public List<UtilityDetails> getUmrnDetails(String To_date, String utility_name, String utility_code1) {
		
		List<UtilityDetails> MonthlyList = new ArrayList<UtilityDetails>();
		UtilityDetails bean = new UtilityDetails();
		PreparedStatement ps = null;
        Connection con = null;

        System.out.println("<<< getUmrnDetails >>>");
       System.out.println("To_date"+To_date);
       System.out.println("utility_name"+utility_name);
       System.out.println("utility_code1"+utility_code1);
        
        
		String BATCH_ID;
		String CDTR_AGNT_NAME;
		String CDTR_ACCT_NO;
		String MMS_TYPE;
		String MANDATE_COUNT;
		String DATA_ENTRY_PENDING_STATUS;
		String PENDING_VERIFICATION_QUEUE;
		String SENDBACK_DATA_ENTRY_PENDING;
		String PENDING_VERIFICATION;
		String SENBACK_BACK_FOR_VERIFICATION;
		String VERIFIED;
		String REJECTED;
		String ACTION_PENDING;
		String ACK_RECEVIED;
		String NACK_RECEIVED;

		System.out.println("OKKKAYYYY");
		try {

			con = MandateDashboardDao.getConnection();
			// conn1 = this.getConnection();
			System.out.println(con);

			// String query = "select auth_date,BATCH_ID,CDTR_ACCT_NO
			// ,CDTR_AGNT_NAME,MMS_TYPE from mms_out_info_tmp where auth_date
			// =to_date('2022-07-27','YYYY-MM-DD') and CDTR_AGNT_NAME ='IDBI BANK LTD' and
			// CDTR_ACCT_NO='IBKL00686000013568' ";
			String query = "select auth_date,BATCH_ID,CDTR_ACCT_NO ,CDTR_AGNT_NAME,MMS_TYPE from mms_out_info_tmp where auth_date =to_date(?,'YYYY-MM-DD') and CDTR_AGNT_NAME =? and CDTR_ACCT_NO= ? ";
			System.out.println(query);
			PreparedStatement pst = con.prepareStatement(query);
			/*
			 * pst.setString(1, to_date); pst.setString(2, utility_name); pst.setString(3,
			 * utility_code);
			 */

			pst.setString(1, To_date);
			pst.setString(2, utility_name);
			pst.setString(3, utility_code1);

			ResultSet rstemp = pst.executeQuery();

			// MANDATE COUNT
			System.out.println("MANDATE COUNT >>>>");
			String query1 = "SELECT COUNT(CDTR_ACCT_NO) as MANDATE_COUNT FROM mms_out_info_tmp where CDTR_AGNT_NAME =? and CDTR_ACCT_NO=? GROUP BY CDTR_ACCT_NO ORDER BY COUNT(CDTR_ACCT_NO) DESC";
			System.out.println("MANDATE COUNT" + query1);
			PreparedStatement pst1 = con.prepareStatement(query1);

			// pst.setString(1, To_date);
			pst1.setString(1, utility_name);
			pst1.setString(2, utility_code1);

			// pst1.setString(1, "IDBI BANK LTD");
			// pst1.setString(2, "IBKL00686000013568");
			ResultSet rs1 = pst1.executeQuery();

			// DATA ENTRY PENDING
			System.out.println("DATA ENTRY PENDING>>>>>>");
			String query2 = "SELECT COUNT(STATUS) as DATA_ENTRY_PENDING_STATUS FROM mms_out_info_tmp where STATUS ='N' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query2);
			PreparedStatement pst2 = con.prepareStatement(query2);
			// pst2.setString(1, To_date);
			pst2.setString(1, utility_name);
			pst2.setString(2, utility_code1);

			// pst2.setString(1, "IDBI BANK LTD");
			// pst2.setString(2, "IBKL00686000013568");
			ResultSet rs2 = pst2.executeQuery();

			// PENDING VERIFICATION QUEUE
			System.out.println("PENDING VERIFICATION QUEUE >>>>>>");
			String query3 = "SELECT COUNT(STATUS) AS PENDING_VERIFICATION_QUEUE FROM mms_out_info_tmp where STATUS ='MODA' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query3);
			PreparedStatement pst3 = con.prepareStatement(query3);

			// pst3.setString(1, To_date);
			pst3.setString(1, utility_name);
			pst3.setString(2, utility_code1);

			// pst3.setString(1, "IDBI BANK LTD");
			// pst3.setString(2, "IBKL00686000013568");
			ResultSet rs3 = pst3.executeQuery();

			// SENDBACK DATA ENTRY PENDING
			System.out.println("SENDBACK DATA ENTRY PENDING >>>>>>>>>>");
			String query4 = "SELECT COUNT(STATUS) AS SENDBACK_DATA_ENTRY_PENDING FROM mms_out_info_tmp where STATUS ='MOD' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query4);
			PreparedStatement pst4 = con.prepareStatement(query4);
			// pst4.setString(1, To_date);
			pst4.setString(1, utility_name);
			pst4.setString(2, utility_code1);

			// pst4.setString(1, "IDBI BANK LTD");
			// pst4.setString(2, "IBKL00686000013568");
			ResultSet rs4 = pst4.executeQuery();

			// PENDING VERIFICATION pending
			System.out.println("PENDING VERIFICATION >>>>>");
			String query5 = "SELECT COUNT(STATUS) AS PENDING_VERIFICATION FROM mms_out_info_tmp where STATUS ='MODA' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query5);
			PreparedStatement pst5 = con.prepareStatement(query5);
			// pst.setString(1, To_date);
			pst5.setString(1, utility_name);
			pst5.setString(2, utility_code1);

			// pst5.setString(1, "IDBI BANK LTD");
			// pst5.setString(2, "IBKL00686000013568");
			ResultSet rs5 = pst5.executeQuery();

			// SENBACK BACK FOR VERIFICATION pending
			String query6 = "SELECT COUNT(STATUS) AS SENBACK_BACK_FOR_VERIFICATION FROM mms_out_info_tmp where STATUS ='MODA' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query6);
			PreparedStatement pst6 = con.prepareStatement(query6);
			// pst.setString(1, To_date);
			pst6.setString(1, utility_name);
			pst6.setString(2, utility_code1);

			// pst6.setString(1, "IDBI BANK LTD");
			// pst6.setString(2, "IBKL00686000013568");
			ResultSet rs6 = pst6.executeQuery();

			// verified
			String query7 = "SELECT COUNT(STATUS) AS VERIFIED FROM mms_out_info_tmp where STATUS ='CRV' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query7);
			PreparedStatement pst7 = con.prepareStatement(query7);

			// pst.setString(1, To_date);
			pst7.setString(1, utility_name);
			pst7.setString(2, utility_code1);

			// pst7.setString(1, "IDBI BANK LTD");
			// pst7.setString(2, "IBKL00686000013568");
			ResultSet rs7 = pst7.executeQuery();

			// Rejected
			String query8 = "SELECT COUNT(STATUS) AS REJECTED FROM mms_out_info_tmp where STATUS ='CRVR' AND SENDBACK='Y' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query8);
			PreparedStatement pst8 = con.prepareStatement(query8);
			// pst.setString(1, To_date);
			pst8.setString(1, utility_name);
			pst8.setString(2, utility_code1);

			// pst8.setString(1, "IDBI BANK LTD");
			// pst8.setString(2, "IBKL00686000013568");
			ResultSet rs8 = pst8.executeQuery();

			// ACTION PENDING
			String query9 = "SELECT COUNT(STATUS) AS ACTION_PENDING FROM mms_out_info_tmp where STATUS ='MODA' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query9);
			PreparedStatement pst9 = con.prepareStatement(query9);
			// pst.setString(1, To_date);
			pst9.setString(1, utility_name);
			pst9.setString(2, utility_code1);

			// pst9.setString(1, "IDBI BANK LTD");
			// pst9.setString(2, "IBKL00686000013568");
			ResultSet rs9 = pst9.executeQuery();

			// ACK RECEVIED
			String query10 = "SELECT COUNT(STATUS) AS ACK_RECEVIED FROM mms_out_info_tmp where STATUS ='ACKN' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query10);
			PreparedStatement pst10 = con.prepareStatement(query10);
			// pst.setString(1, To_date);
			pst10.setString(1, utility_name);
			pst10.setString(2, utility_code1);

			// pst10.setString(1, "IDBI BANK LTD");
			// pst10.setString(2, "IBKL00686000013568");
			ResultSet rs10 = pst10.executeQuery();

			// NACK RECEVIED
			String query11 = "SELECT COUNT(STATUS) AS NACK_RECEVIED FROM mms_out_info_tmp where STATUS ='ACKN' AND CDTR_AGNT_NAME =? AND CDTR_ACCT_NO=? GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
			System.out.println(query11);
			PreparedStatement pst11 = con.prepareStatement(query11);

			// pst11.setString(1, To_date);
			pst11.setString(1, utility_name);
			pst11.setString(2, utility_code1);

			// pst11.setString(1, "IDBI BANK LTD");
			// pst11.setString(2, "IBKL00686000013568");
			ResultSet rs11 = pst11.executeQuery();

			System.out.println("<<<< JSONArray >>>>");

			while (rstemp.next() && rstemp != null) {

				System.out.println("<<<< rstemp.next() >>>>");

				bean = new UtilityDetails();

				BATCH_ID = bean.setBATCH_ID(rstemp.getString(1));
				CDTR_AGNT_NAME = bean.setCDTR_AGNT_NAME(rstemp.getString(2));
				CDTR_ACCT_NO = bean.setCDTR_ACCT_NO(rstemp.getString(3));
				MMS_TYPE = bean.setMMS_TYPE(rstemp.getString(4));

				/*
				 * obj = new JSONObject(); obj.put("FILE_NAME", rstemp.getString("BATCH_ID"));
				 * obj.put("UTILITY_NAME", rstemp.getString("CDTR_AGNT_NAME"));
				 * obj.put("UTILITY_CODE", rstemp.getString("CDTR_ACCT_NO"));
				 * obj.put("MMS_TYPE", rstemp.getString("MMS_TYPE"));
				 */

			}

			while (rs1.next() && rs1 != null) {
				/*
				 * obj = new JSONObject(); obj.put("MANDATE_TYPE",
				 * rs1.getString("MANDATE_COUNT"));
				 */
				MANDATE_COUNT = bean.setMANDATE_COUNT(rs1.getString(1));

			}

			while (rs2.next() && rs2 != null) {
				/*
				 * obj = new JSONObject(); obj.put("DATA_ENTRY_PENDING_STATUS",
				 * rs2.getString("DATA_ENTRY_PENDING_STATUS"));
				 */

				DATA_ENTRY_PENDING_STATUS = bean.setDATA_ENTRY_PENDING_STATUS(rs2.getString(1));

			}

			while (rs3.next() && rs3 != null) {
				/*
				 * obj = new JSONObject();
				 * 
				 * obj.put("PENDING_VERIFICATION_QUEUE",
				 * rs3.getString("PENDING_VERIFICATION_QUEUE"));
				 */
				PENDING_VERIFICATION_QUEUE = bean.setPENDING_VERIFICATION_QUEUE(rs3.getString(1));

			}

			while (rs4.next() && rs4 != null) {
				// obj = new JSONObject();

				// obj.put("SENDBACK_DATA_ENTRY_PENDING",
				// rs4.getString("SENDBACK_DATA_ENTRY_PENDING"));
				SENDBACK_DATA_ENTRY_PENDING = bean.setSENDBACK_DATA_ENTRY_PENDING(rs4.getString(1));
			}

			while (rs5.next() && rs5 != null) {
				// obj = new JSONObject();

				// obj.put("PENDING_VERIFICATION", rs5.getString("PENDING_VERIFICATION"));
				PENDING_VERIFICATION = bean.setPENDING_VERIFICATION(rs5.getString(1));
			}

			while (rs6.next() && rs6 != null) {
				// obj = new JSONObject();

				// obj.put("SENBACK_BACK_FOR_VERIFICATION",
				// rs6.getString("SENBACK_BACK_FOR_VERIFICATION"));
				SENBACK_BACK_FOR_VERIFICATION = bean.setSENBACK_BACK_FOR_VERIFICATION(rs6.getString(1));
			}

			while (rs7.next() && rs7 != null) {
				// obj = new JSONObject();

				// obj.put("VERIFIED", rs7.getString("VERIFIED"));
				VERIFIED = bean.setVERIFIED(rs7.getString(1));
			}

			while (rs8.next() && rs8 != null) {
				// obj = new JSONObject();

				// obj.put("REJECTED", rs8.getString("REJECTED"));
				REJECTED = bean.setREJECTED(rs8.getString(1));

			}

			while (rs9.next() && rs9 != null) {
				// obj = new JSONObject();

				// obj.put("ACTION_PENDING", rs9.getString("ACTION_PENDING"));
				ACTION_PENDING = bean.setACTION_PENDING(rs9.getString(1));

			}

			while (rs10.next() && rs10 != null) {
				// obj = new JSONObject();

				// obj.put("ACK_RECEIVED", rs10.getString("ACK_RECEVIED"));
				ACK_RECEVIED = bean.setACK_RECEVIED(rs10.getString(1));
			}

			while (rs11.next() && rs11 != null) {
				// obj = new JSONObject();

				// obj.put("NACK_RECEIVED", rs11.getString("NACK_RECEIVED"));
				NACK_RECEIVED = bean.setNACK_RECEIVED(rs11.getString(1));
			}

			MonthlyList.add(bean);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (rs != null) {
					rs.close();
					rs = null;

				}
			} catch (Exception e)

			{

				e.printStackTrace();
			}
		}

		return MonthlyList;

	}

}
