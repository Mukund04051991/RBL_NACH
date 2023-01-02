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
import java.util.ResourceBundle;

import javax.naming.spi.DirStateFactory.Result;

import com.ddmandate.util.ExcelTemplateVO;

public class CancelBulkMandateDao {

	
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
	
	public boolean checkFileExist(String name) {
		Connection con = CancelBulkMandateDao.getConnection();
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
		Connection con = CancelBulkMandateDao.getConnection();

		FileInputStream fin = null;
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
	public static boolean insertexcelFileRecords(ArrayList<ExcelTemplateVO> txnList, String filename,String headerLine) throws SQLException {
		
		
		boolean status = false;
		String file_id = getTransactionFileId(filename);
		Connection con = CancelBulkMandateDao.getConnection();
		
        System.out.println("Inside the insertexcelFileRecords");
				con.setAutoCommit(false);
				for (com.ddmandate.util.ExcelTemplateVO vo : txnList) {
				//UMRN  Available Or Not
				ResultSet rs = null;
				String query ="select count (1) from MMS_OUT_INFO_TMP WHERE UMRN =?";
				System.out.println(query);
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, vo.getUMRN());
				//rs = pst.executeBatch();
				 //rs = pst.executeQuery();
				 ResultSet rs2 = pst.executeQuery();
				
				
				//Check for already cancel or not
				String CanStatus ="Cancel";
				String query2 = "select count (1) from MMS_OUT_INFO_TMP WHERE UMRN =? AND MMS_TYPE=?";
				PreparedStatement pst1 = con.prepareStatement(query2);
				pst1.setString(1, vo.getUMRN());// UMRN
				pst1.setString(2, CanStatus);
				ResultSet rs1 = pst1.executeQuery();
				
				
				int count = 0;
				while (rs2.next()) {
					count = rs2.getInt(1);
				}
				System.out.println("file exist count:" + count);
				if (count > 0) {
					rs1.next();
					System.out.println("rs1.next :->");
					int y = rs1.getInt(1);
					
					System.out.println("y >>>" + y);	
					
				if(y > 0) {
				String updateQry ="UPDATE MMS_OUT_INFO_TMP SET REJECT_REASON ='Already Cancel' WHERE UMRN=?";	
				PreparedStatement pst2 = con.prepareStatement(updateQry);
				pst2.setString(1,vo.getUMRN());
				System.out.println("UMRN"+vo.getUMRN());
				ResultSet rs3 = pst2.executeQuery();
				con.commit();
				status =true;
				
				} else 
					  {
						//already not cancelled so CNV
					   System.out.println("already not cancelled so CNV");
						String updateQry ="UPDATE MMS_OUT_INFO_TMP SET REJECT_REASON ='CNV' WHERE UMRN=?";	
						PreparedStatement pst3 = con.prepareStatement(updateQry);
						pst3.setString(1,vo.getUMRN());
						System.out.println("UMRN"+vo.getUMRN());
						ResultSet rs4 = pst3.executeQuery();
						con.commit();
						status =true;
					  }	
				} else {
					System.out.println("UMRN Not Exixt");
				}
				
		}
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
