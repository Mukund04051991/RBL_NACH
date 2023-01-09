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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ddmandate.bean.UtilityDetails;

import com.ddmandate.bean.MandateDashboardBean;

public class ACHSponsorDao {

	Connection con;
	PreparedStatement pst;
	String query;
	ResultSet rs;

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

		Connection con = ACHSponsorDao.getConnection();
		List<UtilityDetails> utilitylist = new ArrayList<>();
		try {
			//query = "select * from utility_master";
			query ="select * from sponsor_cust_utility_details";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				UtilityDetails utilityDetails = new UtilityDetails();
				utilityDetails.setUtility_code(rs.getString("utility_code"));
				utilityDetails.setUtility_name(rs.getString("utility_name"));
				utilitylist.add(utilityDetails);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
		return utilitylist;
	}

	/*
	 * public boolean saveFileDataInDB(List<com.ach.sponsor.util.ExcelTemplateVO>
	 * txnList) { System.out.println("calling save file method"); String sql =
	 * "insert into nach_dd_txn_dr_inp (txn_seq_no, utility_code, settlement_date, account_holder_name,amount,beneficiary_account_no,umrn,upload_date) "
	 * + " VALUES (?,?,?,?,?,?,?,?)"; try {
	 * 
	 * for (com.ach.sponsor.util.ExcelTemplateVO vo : txnList) {
	 * 
	 * }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); return false; }
	 * 
	 * return true;
	 * 
	 * }
	 */

	public boolean validateUtilityCode(List<com.ddmandate.util.ExcelTemplateVO> txnList, String utility_code) {

		Connection con = ACHSponsorDao.getConnection();
		boolean status = false;
		try {

			for (com.ddmandate.util.ExcelTemplateVO vo : txnList) {
				System.out.println("vo.getUTILITYCODE():" + vo.getUTILITYCODE());
				int count = 0;
				if (!vo.getUTILITYCODE().equals(utility_code)) {
					count++;
				}
				System.out.println();
				if (count > 0) {
					status = false;
				} else {
					status = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
			status = false;

		}
		return status;

	}

	public List<UtilityDetails> getUtilityCodeByUtilityName(String utility_name) {

		Connection con = ACHSponsorDao.getConnection();
		List<UtilityDetails> list = new ArrayList<>();
		try {
			//query = "select * from utility_master where utility_name=?";
			query = "select * from sponsor_cust_utility_details where utility_name=?";
			pst = con.prepareStatement(query);
			pst.setString(1, utility_name);
			rs = pst.executeQuery();
			while (rs.next()) {
				UtilityDetails utility_details = new UtilityDetails();
				utility_details.setUtility_code(rs.getString("utility_code"));
				utility_details.setUtility_name(rs.getString("utility_name"));
				list.add(utility_details);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// get Email ID
	public String getEmailByUtilityCode(String utility_code) {

		Connection con = ACHSponsorDao.getConnection();

		List<UtilityDetails> list = new ArrayList<>();
		String email_id = "";
		try {
			//query = "select * from utility_master where utility_code=?";
			query = "select * from sponsor_cust_utility_details where utility_code=?";
			pst = con.prepareStatement(query);
			pst.setString(1, utility_code);
			rs = pst.executeQuery();
			while (rs.next()) {
				email_id = rs.getString("email_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return email_id;
	}

	public boolean uploadFileDatils(String filePath, String fileName, String utility_name, String utility_code,
			String email_id, List<com.ddmandate.util.ExcelTemplateVO> txnList) {

		boolean upload_filestatus = false;
		Connection con = ACHSponsorDao.getConnection();

		FileInputStream fin = null;
		/* String settlementDate = ""; */
		/*
		 * settlementDate = txnList.get(0).getSETTLEMENTDATE();
		 * System.out.println("settlement_date(valid file):" + settlementDate);
		 */

		try {
			fin = new FileInputStream(filePath + File.separator + fileName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			 con.setAutoCommit(false);
			//query = "insert into nach_dd_in_file_info(upload_file_name,file_structure,upload_date,utility_name,utility_code,email_id,file_id,SETTLEMENT_DATE) values (?,?,sysdate,?,?,?,LPAD(NACH_DD_TXN_FILE_ID.NEXTVAL,4,'0'),?)";
			query = "insert into nach_dd_mms_in_file_info(upload_file_name,file_structure,upload_date,utility_name,utility_code,email_id,file_id) values (?,?,sysdate,?,?,?,LPAD(NACH_DD_TXN_FILE_ID.NEXTVAL,4,'0'))";
			pst = con.prepareStatement(query);
			pst.setString(1, fileName);
			pst.setBinaryStream(2, fin, fin.available()); // write file
			pst.setString(3, utility_name);
			pst.setString(4, utility_code);
			pst.setString(5, email_id);
			/* pst.setString(6, settlementDate); */
			int i = pst.executeUpdate();
			if (i > 0) {
				con.commit();
				// insert record
				upload_filestatus = true;
			} else {
				upload_filestatus = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
			upload_filestatus = false;
		}

		return upload_filestatus;
	}

	public boolean checkFileExist(String filename) {

		Connection con = ACHSponsorDao.getConnection();
		boolean isExist = false;
		try {

			//query = "select count(1) from nach_dd_in_file_info where upload_file_name =?";
			query = "select count(1) from nach_dd_mms_in_file_info where upload_file_name =?";
			pst = con.prepareStatement(query);
			pst.setString(1, filename);
			rs = pst.executeQuery();
			int count = 0;
			while (rs.next()) {
				count = rs.getInt(1);
			}
			System.out.println("file exist count:" + count);
			if (count > 0) {
				isExist = true;
			} else {
				isExist = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return isExist;
	}

	// insert excel file record into database
	public boolean insertexcelFileRecords(List<com.ddmandate.util.ExcelTemplateVO> txnList, String filename,
			String headerLine) {

		System.out.println("headerLine:" + headerLine);
		boolean status = false;
		Connection con = ACHSponsorDao.getConnection();
		String file_id = getTransactionFileId(filename);
		int count = 0;
		int batchSize = 20;
		double total_amt = 0.00;
		DecimalFormat df = new DecimalFormat("#.00");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		String totalamt = "";
		// for bajaj file
		if (headerLine.trim().equals(
				"Image File Name|UTILITYCODE|ACTION|MANDATE_DATE|AC_TYPE|ACCNUMBER|IFSC CODE|MICR CODE|DEBIT TYPE|AMOUNT|REFNO1|REFNO2|FREQUENCY|START_DATE|END_DATE|UNTILCANCEL|CUST_NAME|MOBILE|MAIL|Additional_1|Additional_2|Additional_3|Additional_4|Additional_5")) {
			
			
			System.out.println("A");
			try {
				con.setAutoCommit(false);
				//query = "insert into nach_dd_txn_dr_inp(TXN_SEQ_NO,UTILITY_CODE,SETTLEMENT_DATE,ACCOUNT_HOLDER_NAME,settlement_amount,BENEFICIARY_ACCOUNT_NO,UMRN,UPLOAD_DATE,FILE_ID,dr_inw_status) values ('DD'|| TO_CHAR(SYSDATE,'DDMMYYYY') ||LPAD(NACH_DD_TXN_SEQ_NO.NEXTVAL,6,'0'),?,?,?,?,?,?,sysdate,?,?)";
				
				query ="insert into nach_dd_mms_reg_details(IMAGE_FILE_NAME,utility_code,ACTION,MANDATE_DATE,account_type,account_number,IFSC_CODE,\r\n"
						+ "MICR_CODE,DEBIT_TYPE,AMOUNT,ref_no_1,\r\n"
						+ "ref_no_2,FREQUENCY,START_DATE,END_DATE,until_cancel,customer_name,\r\n"
						+ "mobile_number,mail_id,additional_info_1,additional_info_2,additional_info_3,additional_info_4,additional_info_5) \r\n"
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				pst = con.prepareStatement(query);

				for (com.ddmandate.util.ExcelTemplateVO vo : txnList) {
					/*
					 * pst.setString(1, vo.getUTILITYCODE());// utility code Date sdate =
					 * formatter.parse(vo.getSETTLEMENTDATE());
					 * System.out.println("sdate.toString():"+sdate.toString()); pst.setString(2,
					 * formatter.format(sdate)); // pst.setString(2, vo.getSETTLEMENTDATE());//
					 * settlement date
					 * 
					 * pst.setString(3, vo.getBENEFICIARYACHOLDERNAME());// account holder name
					 * totalamt = df.format(Double.parseDouble(vo.getAMOUNT())); pst.setString(4,
					 * totalamt);// amount pst.setString(5, vo.getBENEFICIARYACNO());// account no
					 * pst.setString(6, vo.getUMRN());// umrn pst.setString(7, file_id);// file id
					 * pst.setString(8, "010");// file id
					 */			
					
					pst.setString(1, vo.getIMAGE_FILE_NAME());
					pst.setString(2, vo.getUTILITYCODE());
					pst.setString(3, vo.getACTION());
					pst.setString(4, vo.getMANDATE_DATE());
					pst.setString(5, vo.getAC_TYPE());
					pst.setString(6, vo.getACCNUMBER());
					pst.setString(7, vo.getIFSC_CODE());
					pst.setString(8, vo.getMICR_CODE());
					pst.setString(9, vo.getDEBIT_TYPE());
					pst.setString(10, vo.getAMOUNT());
					pst.setString(11, vo.getREFNO1());
					pst.setString(12, vo.getREFNO2());
					pst.setString(13, vo.getFREQUENCY());
					pst.setString(14, vo.getSTART_DATE());
					pst.setString(15, vo.getEND_DATE());
					pst.setString(16, vo.getUNTILCANCEL());
					pst.setString(17, vo.getCUST_NAME());
					pst.setString(18, vo.getMOBILE());
					pst.setString(19, vo.getMAIL());
					pst.setString(20, vo.getAdditional_1());
					pst.setString(21, vo.getAdditional_2());
					pst.setString(22, vo.getAdditional_3());
					pst.setString(23, vo.getAdditional_4());
					pst.setString(24, vo.getAdditional_5());
					
					total_amt = total_amt + Double.parseDouble(vo.getAMOUNT());
					count++;
					pst.addBatch();
				}

				if (count % batchSize == 0) {
					pst.executeBatch();
				}
				pst.executeBatch();
				con.commit();
				status = true;
				System.out.println("count:" + count);
				System.out.println("total_amt:" + total_amt);
				con.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(System.out);
				status = false;
			}
		} else {
			// for dhani file
			System.out.println("B");
			try {
				con.setAutoCommit(false);
				//query = "insert into nach_dd_txn_dr_inp(TXN_SEQ_NO,UTILITY_CODE,SETTLEMENT_DATE,settlement_amount,UMRN,UPLOAD_DATE,FILE_ID,dr_inw_status) values ('DD'|| TO_CHAR(SYSDATE,'DDMMYYYY') ||LPAD(NACH_DD_TXN_SEQ_NO.NEXTVAL,6,'0'),?,?,?,?,sysdate,?,?)";
				query = "insert into nach_dd_mms_reg_details(MANDATE_DATE,MANDATE_ID,UMRN,CUST_REF_NO,SCH_REF_NO,CUSTOMER_NAME,BANK,BRANCH,BANK_CODE,ACCOUNT_TYPE"
						+ ",ACCOUNT_NUMBER,AMOUNT,\r\n"
						+ "FREQUENCY,DEBIT_TYPE,START_DATE,END_DATE,UNTIL_CANCEL,TEL_NO,MOBILE_NUMBER,MAIL_ID,UTILITY_CODE,UTILITY_NAME,STATUS,"
						+ "STATUS_CODE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				pst = con.prepareStatement(query);

				for (com.ddmandate.util.ExcelTemplateVO vo : txnList) {

					//System.out.println("vo.getSETTLEMENTDATE():" + vo.getSETTLEMENTDATE());
					pst.setString(1, vo.getMANDATE_DATE());// utility code
					pst.setString(2, vo.getMANDATE_ID());// settlement date
					pst.setString(3, vo.getUMRN());// amount
					pst.setString(4, vo.getCUST_REFNO());// umrn
					pst.setString(5, vo.getSCH_REFNO());// 
					pst.setString(6, vo.getCUSTOMER_NAME());// file id
					pst.setString(7, vo.getBANK());// utility code
					pst.setString(8, vo.getBRANCH());// settlement date
					pst.setString(9, vo.getBANK_CODE());// amount
					pst.setString(10, vo.getACCOUNT_TYPE());// umrn
					pst.setString(11, vo.getACCNUMBER());//
					pst.setString(12, vo.getAMOUNT());
					pst.setString(13, vo.getFREQUENCY());// file id
					pst.setString(14, vo.getDEBIT_TYPE());// utility code
					pst.setString(15, vo.getSTART_DATE());// settlement date
					pst.setString(16, vo.getEND_DATE());// amount
					pst.setString(17, vo.getUNTIL_CANCEL());// umrn
					pst.setString(18, vo.getTEL_NO());// 
					pst.setString(19, vo.getMOBILE_NO());// file id
					pst.setString(20, vo.getMAIL_ID());// utility code
					System.out.println("UTILITYCODE >>>>"+vo.getUTILITYCODE());
					pst.setString(21, vo.getUTILITYCODE());// settlement date
					pst.setString(22, vo.getUTILITY_NAME());// amount
					pst.setString(23, vo.getSTATUS());// umrn
					pst.setString(24, vo.getSTATUS_CODE());// file id
					//spst.setString(25, vo.getSCH_REF_NO());// file id
					total_amt = total_amt + Double.parseDouble(vo.getAMOUNT());
					count++;
					pst.addBatch();
				}

				if (count % batchSize == 0) {
					pst.executeBatch();
				}
				pst.executeBatch();
				con.commit();
				status = true;
				System.out.println("count:" + count);
				System.out.println("total_amt:" + total_amt);
				con.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(System.out);
				status = false;
			}

		}
		// update header transaction count and amount
		System.out.println("in txn:" + status);
		if (status)
			updateHdrTxnCount(count, file_id, total_amt);

		return status;

	}

	// get transaction file id
	public String getTransactionFileId(String filename) {

		Connection con = ACHSponsorDao.getConnection();
		String file_id = "";

		//query = "select file_id from nach_dd_in_file_info where UPLOAD_FILE_NAME =?";
		query = "select file_id from nach_dd_mms_in_file_info where UPLOAD_FILE_NAME =?";
		try {
			pst = con.prepareStatement(query);
			pst.setString(1, filename);
			rs = pst.executeQuery();
			while (rs.next()) {
				file_id = rs.getString("file_id");
			}
			System.out.println("filename:" + filename + "\nfile_id" + file_id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
		}

		return file_id;
	}

	public int updateHdrTxnCount(int count, String file_id, double total_amt) {

		Connection con = ACHSponsorDao.getConnection();
		int i = 0;
		DecimalFormat df = new DecimalFormat("#.00");
		String totalamt = df.format(total_amt);
		try {
			con.setAutoCommit(false);
			//query = "update nach_dd_in_file_info set total_count=?,total_amount=? where file_id =?";
			query = "update nach_dd_mms_in_file_info set total_count=?,total_amount=? where file_id =?";
			pst = con.prepareStatement(query);
			pst.setString(1, String.valueOf(count));
			pst.setString(2, totalamt);
			pst.setString(3, file_id);
			i = pst.executeUpdate();

			if (i > 0) {
				con.commit();
				return i;
			} else {
				return i;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
			return i;
		}

	}

	// delete file from database
	public int deleteDDFile(String filename) {

		int t = 0;
		Connection con = ACHSponsorDao.getConnection();
		try {
			con.setAutoCommit(false);
			query = "delete from nach_dd_mms_in_file_info where upload_file_name =?";
			pst = con.prepareStatement(query);
			pst.setString(1, filename);
			t = pst.executeUpdate();

			if (t > 0) {
				con.commit();
				return t;
			} else {
				return t;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
			return t;
		}

	}

	
		//Added for MandateDashboardBean
		 		 
			public JSONArray getMandateDetails(String utility_name, String utility_code, String to_date) {
				System.out.println("utility_name:" + utility_name);
				System.out.println("utility_code:" + utility_code);
				System.out.println("to_date:" + to_date);

				int count = 0;

				JSONObject obj = null;
				JSONArray array = null;
				try {

					con = ACHSponsorDao.getConnection();
					
					//Getting 
					String query ="select mms_out_info_tmp.BATCH_ID,mms_out_info_tmp.CDTR_ACCT_NO ,mms_out_info_tmp.CDTR_AGNT_NAME,"
							+ "mms_out_info_tmp.mms_type from mms_img_mas,mms_out_info_tmp where  mms_out_info_tmp.auth_date ='' "
							+ "and mms_out_info_tmp.CDTR_AGNT_NAME ='' and mms_out_info_tmp.CDTR_ACCT_NO='' ";
					System.out.println(query);
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, to_date);
					pst.setString(2, utility_name);
					pst.setString(3, utility_code);
				    ResultSet rs = pst.executeQuery();
				    
				   
				  //MANDATE COUNT 
					String query1 ="SELECT COUNT(CDTR_ACCT_NO) FROM mms_out_info_tmp where CDTR_AGNT_NAME ='' and CDTR_ACCT_NO='' GROUP BY CDTR_ACCT_NO ORDER BY COUNT(CDTR_ACCT_NO) DESC";
					System.out.println(query);
					PreparedStatement pst1 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs1 = pst.executeQuery();
				    
				    
				  //DATA ENTRY PENDING
					String query2 ="SELECT COUNT(FILE_STATUS),STATUS FROM mms_out_info_tmp where FILE_STATUS ='N' AND CDTR_AGNT_NAME ='' AND CDTR_ACCT_NO='' GROUP BY FILE_STATUS ORDER BY COUNT(FILE_STATUS) DESC";
					System.out.println(query);
					PreparedStatement pst2 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs2 = pst.executeQuery();
				    
				    
				    //PENDING VERIFICATION QUEUE
					String query3 ="SELECT COUNT(STATUS) FROM mms_out_info_tmp where STATUS ='MODA' AND CDTR_AGNT_NAME ='' AND CDTR_ACCT_NO='' GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
					System.out.println(query);
					PreparedStatement pst3 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs3 = pst.executeQuery();
				    
				    
				  //SENDBACK DATA ENTRY PENDING
					String query4 ="SELECT COUNT(STATUS),STATUS FROM mms_out_info_tmp where STATUS ='MOD' AND CDTR_AGNT_NAME ='' AND CDTR_ACCT_NO='' GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
					System.out.println(query);
					PreparedStatement pst4 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs4 = pst.executeQuery();
				  
					
				    //SENBACK BACK FOR VERIFICATION pending
					String query5 ="SELECT COUNT(STATUS),STATUS FROM mms_out_info_tmp where STATUS ='MODA' AND SENDBACK='Y' AND CDTR_AGNT_NAME ='' AND CDTR_ACCT_NO='' GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
					System.out.println(query);
					PreparedStatement pst5 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs5 = pst.executeQuery();
				    
				  //SENBACK BACK FOR VERIFICATION pending
					String query6 ="SELECT COUNT(STATUS),STATUS FROM mms_out_info_tmp where STATUS ='MODA' AND SENDBACK='Y' AND CDTR_AGNT_NAME ='' AND CDTR_ACCT_NO='' GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
					System.out.println(query);
					PreparedStatement pst6 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs6 = pst.executeQuery();
				    
				    
				  //verified
					String query7 ="SELECT COUNT(STATUS),STATUS FROM mms_out_info_tmp where STATUS ='CRV' AND SENDBACK='Y' AND CDTR_AGNT_NAME ='' AND CDTR_ACCT_NO='' GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
					System.out.println(query);
					PreparedStatement pst7 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs7 = pst.executeQuery();
				    
				    //Rejected
					String query8 ="SELECT COUNT(STATUS),STATUS FROM mms_out_info_tmp where STATUS ='CRVR' AND SENDBACK='Y' AND CDTR_AGNT_NAME ='' AND CDTR_ACCT_NO='' GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
					System.out.println(query);
					PreparedStatement pst8 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs8 = pst.executeQuery();
				    
				    
				    //ACTION PENDING
					String query9 ="SELECT COUNT(STATUS),STATUS FROM mms_out_info_tmp where STATUS ='MODA' AND CDTR_AGNT_NAME ='' AND CDTR_ACCT_NO='' GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
					System.out.println(query);
					PreparedStatement pst9 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs9 = pst.executeQuery();
				    
				    // ACK RECEVIED
				    String query10 ="SELECT COUNT(STATUS),STATUS FROM mms_out_info_tmp where STATUS ='ACKN' AND CDTR_AGNT_NAME ='' AND CDTR_ACCT_NO='' GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
					System.out.println(query);
					PreparedStatement pst10 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs10 = pst.executeQuery();
				    
				    // NACK RECEVIED
				    String query11 ="SELECT COUNT(STATUS),STATUS FROM mms_out_info_tmp where STATUS ='ACKN' AND CDTR_AGNT_NAME ='' AND CDTR_ACCT_NO='' GROUP BY STATUS ORDER BY COUNT(STATUS) DESC";
					System.out.println(query);
					PreparedStatement pst11 = con.prepareStatement(query);
					//pst.setString(1, to_date);
					pst.setString(1, utility_name);
					pst.setString(2, utility_code);
				    ResultSet rs11 = pst.executeQuery();
				    
				    
					
			        array = new JSONArray();
					
					while (rs.next()) {
						obj = new JSONObject();

						obj.put("FILE_NAME", rs.getString("BATCH_ID"));
						obj.put("UTILITY_NAME", rs.getString("CDTR_AGNT_NAME"));
						obj.put("UTILITY_CODE", rs.getString("CDTR_ACCT_NO"));
						
						array.put(obj.toString());
					}
					
					while (rs1.next()) {
						obj = new JSONObject();

						obj.put("MANDATE", rs.getString("COUNT(STATUS)"));
					
						array.put(obj.toString());
					}
					
					while (rs2.next()) {
						obj = new JSONObject();

						obj.put("FILE_NAME", rs.getString("BATCH_ID"));
						
						
						array.put(obj.toString());
					}
					
					while (rs3.next()) {
						obj = new JSONObject();

						obj.put("FILE_NAME", rs.getString("BATCH_ID"));
					
						
						array.put(obj.toString());
					}
					
					
					array.put(obj.toString());
					
					
					
					
					

				} catch (Exception ex) {
					ex.printStackTrace(System.out);
				} finally {

					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace(System.out);
						}

					}
					if (pst != null) {
						try {
							pst.close();
							pst = null;
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace(System.out);
						}

					}
					if (con != null) {
						try {
							con.close();
							con = null;
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace(System.out);
						}

					}
				}
				return array;

			}


		
		
		
	

	

}
