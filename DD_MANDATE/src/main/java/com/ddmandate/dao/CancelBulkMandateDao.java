package com.ddmandate.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.ddmandate.util.ExcelTemplateVO;

public class CancelBulkMandateDao {

	
	
	
	public boolean checkFileExist(String name) {
		Connection con = ACHSponsorDao.getConnection();
		boolean isExist = false;
		try {

			//query = "select count(1) from nach_dd_in_file_info where upload_file_name =?";
			String query = "select count(1) from nach_dd_mms_in_file_info where upload_file_name =?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
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


	public static boolean uploadFileDatils(String filePath, String filename, String utility_code,
			ArrayList<ExcelTemplateVO> txnList) {
		boolean upload_filestatus = false;
		System.out.println("<<< uploadFileDatils >>>");
		Connection con = ACHSponsorDao.getConnection();

		FileInputStream fin = null;
		/* String settlementDate = ""; */
		/*
		 * settlementDate = txnList.get(0).getSETTLEMENTDATE();
		 * System.out.println("settlement_date(valid file):" + settlementDate);
		 */

		try {
			fin = new FileInputStream(filePath + File.separator + filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			 con.setAutoCommit(false);
			//query = "insert into nach_dd_in_file_info(upload_file_name,file_structure,upload_date,utility_name,utility_code,email_id,file_id,SETTLEMENT_DATE) values (?,?,sysdate,?,?,?,LPAD(NACH_DD_TXN_FILE_ID.NEXTVAL,4,'0'),?)";
			String query = "insert into nach_dd_mms_in_file_info(upload_file_name,file_structure,upload_date,utility_code,file_id) values (?,?,sysdate,?,LPAD(NACH_DD_TXN_FILE_ID.NEXTVAL,4,'0'))";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, filename);
			pst.setBinaryStream(2, fin, fin.available()); // write file
			pst.setString(3, utility_code);
			
			/* pst.setString(6, settlementDate); */
			int i = pst.executeUpdate();
			if (i > 0) {
				con.commit();
				// insert record
				upload_filestatus = true;
				System.out.println("<<< insert record >>>");
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

	// get transaction file id
		public static String getTransactionFileId(String filename) {

			Connection con = ACHSponsorDao.getConnection();
			String file_id = "";

			//query = "select file_id from nach_dd_in_file_info where UPLOAD_FILE_NAME =?";
			String query = "select file_id from nach_dd_mms_in_file_info where UPLOAD_FILE_NAME =?";
			try {
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, filename);
				ResultSet rs = pst.executeQuery();
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
	
	
	//UMRN logic
	public static boolean insertexcelFileRecords(ArrayList<ExcelTemplateVO> txnList, String filename,String headerLine) {
		System.out.println("headerLine:" + headerLine);
		boolean status = false;
		String file_id = getTransactionFileId(filename);
		Connection con = ACHSponsorDao.getConnection();
		int count = 0;
		int batchSize = 20;
		
        if (headerLine.trim().equals("UTILITY_CODE|UMRN|CANCELLATION_CODE")) {
			try {
				con.setAutoCommit(false);
				
				
				//query = "insert into nach_dd_txn_dr_inp(TXN_SEQ_NO,UTILITY_CODE,SETTLEMENT_DATE,settlement_amount,UMRN,UPLOAD_DATE,FILE_ID,dr_inw_status) values ('DD'|| TO_CHAR(SYSDATE,'DDMMYYYY') ||LPAD(NACH_DD_TXN_SEQ_NO.NEXTVAL,6,'0'),?,?,?,?,sysdate,?,?)";
				/*
				 * String query =
				 * "insert into nach_dd_mms_reg_details(MANDATE_DATE,MANDATE_ID,UMRN,CUST_REF_NO,SCH_REF_NO,CUSTOMER_NAME,BANK,BRANCH,BANK_CODE,ACCOUNT_TYPE"
				 * + ",ACCOUNT_NUMBER,AMOUNT,\r\n" +
				 * "FREQUENCY,DEBIT_TYPE,START_DATE,END_DATE,UNTIL_CANCEL,TEL_NO,MOBILE_NUMBER,MAIL_ID,UTILITY_CODE,UTILITY_NAME,STATUS,"
				 * + "STATUS_CODE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				 */
				
				String query ="";
				PreparedStatement pst = con.prepareStatement(query);

				for (com.ddmandate.util.ExcelTemplateVO vo : txnList) {

					//System.out.println("vo.getSETTLEMENTDATE():" + vo.getSETTLEMENTDATE());
					pst.setString(1, vo.getMANDATE_DATE());// utility code
					pst.setString(2, vo.getMANDATE_ID());// settlement date
					pst.setString(3, vo.getUMRN());// amount
				
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
			updateHdrTxnCount(count, file_id);

		return status;

	}


	// delete file from database
		public static int deleteDDFile(String filename) {

			int t = 0;
			Connection con = ACHSponsorDao.getConnection();
			try {
				con.setAutoCommit(false);
				String query = "delete from nach_dd_mms_in_file_info where upload_file_name =?";
				PreparedStatement pst = con.prepareStatement(query);
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
	
	public static int updateHdrTxnCount(int count, String file_id) {

		Connection con = ACHSponsorDao.getConnection();
		int i = 0;
		try {
			con.setAutoCommit(false);
			//query = "update nach_dd_in_file_info set total_count=?,total_amount=? where file_id =?";
			String query = "update nach_dd_mms_in_file_info set total_count=? where file_id =?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, String.valueOf(count));
			pst.setString(2, file_id);
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
}
