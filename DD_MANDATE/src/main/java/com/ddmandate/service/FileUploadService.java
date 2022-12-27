package com.ddmandate.service;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ddmandate.dao.ACHSponsorDao;
import com.ddmandate.util.ExcelTemplateVO;

public class FileUploadService {

	ACHSponsorDao achSponsorDao;

	
	//validate Amount of excel cell
	
	public int validateAmount(List<com.ddmandate.util.ExcelTemplateVO> txnList) {
		
		int status=10;
		
		for (com.ddmandate.util.ExcelTemplateVO vo : txnList) {
		
		String str=vo.getAMOUNT();
		System.out.println("vo.getAMOUNT() >>>>>>"+vo.getAMOUNT());
	    String[] dotSub=str.split("\\.|,");
	    if(dotSub.length==2){
	         if(dotSub[1].length()<=2){
	             System.out.println("valid Amount");
	         }
	         else{
	             System.out.println("not a valid Amount");
	             status = 11;
	         }
	    }
	    else{
	        System.out.println("not a valid Amount");
	        status = 11;
	    }
		}
		
		return status;
		
	}
	
	
	
	
	// validate null values of excel cell

	public int validateNullValues(List<com.ddmandate.util.ExcelTemplateVO> txnList, String utility_name) {

		int status = 10;
		for (com.ddmandate.util.ExcelTemplateVO vo : txnList) {

			// for bajaj
			if (utility_name.contains("BAJAJ")) {

				if (vo.getIMAGE_FILE_NAME() == null) {
					status = 11;
				} else if (vo.getUTILITYCODE() == null) {
					status = 11;
				} else if (vo.getACTION() == null) {
					status = 11;
				} else if (vo.getMANDATE_DATE() == null) {
					status = 11;
				} else if (vo.getAC_TYPE() == null) {
					status = 11;
				} else if (vo.getACCNUMBER() == null) {
					status = 11;
				} else if (vo.getDEBIT_TYPE() == null) {
					status = 11;
				} else if (vo.getAMOUNT() == null) {
					status = 11;
				} else if (vo.getFREQUENCY() == null) {
					status = 11;
				} else if (vo.getSTART_DATE() == null) {
					status = 11;
				} else if (vo.getCUST_NAME() == null) {
					status = 11;
				}

			}
			// for dhani
			/*
			 * if (utility_name.contains("DHANI")) {
			 * 
			 * if (vo.getSRNO() == null) { status = 11; } else if (vo.getUTILITYCODE() ==
			 * null) { status = 11; } else if (vo.getBENEFICIARYACNO() == null) { status =
			 * 11; } else if (vo.getUMRN() == null) { status = 11; } else if (vo.getAMOUNT()
			 * == null) { status = 11; }
			 * 
			 * }
			 */

		}
		return status;

	}

	/*
	 * public static boolean
	 * validateSettlementDate(List<com.ach.sponsor.util.ExcelTemplateVO> txnList) {
	 * 
	 * boolean status = true; int countInvalidDate = 0; SimpleDateFormat
	 * formatUploadDate = new SimpleDateFormat("dd-MM-yyyy"); Date date = new
	 * Date(); String uploadDate = formatUploadDate.format(date);
	 * System.out.println("uploadDate:" + uploadDate); String settlmentDate = "";
	 * 
	 * for (com.ach.sponsor.util.ExcelTemplateVO vo : txnList) {
	 * System.out.println("vo.getSETTLEMENTDATE()1:" + vo.getSETTLEMENTDATE());
	 * 
	 * if (vo.getSETTLEMENTDATE() == null) {
	 * System.out.println("settlment date shouldn't blank"); status = false; } else
	 * if (vo.getSETTLEMENTDATE().equals("")) {
	 * System.out.println("settlment date shouldn't blank"); status = false; } else
	 * {
	 * 
	 * try { SimpleDateFormat sdfrmt = new SimpleDateFormat("dd-MM-yy");
	 * SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy"); Date
	 * dUploadDate = sdformat.parse(uploadDate); Date dStlmDate =
	 * sdformat.parse(txnList.get(0).getSETTLEMENTDATE());
	 * 
	 * Date stlmntDate = sdfrmt.parse(vo.getSETTLEMENTDATE()); String sDate =
	 * sdfrmt.format(stlmntDate); System.out.println("stlmntDate:" + stlmntDate);
	 * System.out.println("formatted date:" + sDate);
	 * System.out.println("txnList.get(0).getSETTLEMENTDATE():" +
	 * txnList.get(0).getSETTLEMENTDATE());
	 * 
	 * if (dUploadDate.compareTo(stlmntDate) > 0) {
	 * System.out.println("Past Settlement Date not allowed"); status = false; }
	 * else { if (txnList.get(0).getSETTLEMENTDATE().equals(sDate)) {
	 * 
	 * status = true; } else { System.out.println("Invalid Date");
	 * countInvalidDate++; status = false; } }
	 * 
	 * } catch (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); status = false; } } }
	 * System.out.println("countInvalidDate:" + countInvalidDate); if
	 * (countInvalidDate > 0) {
	 * 
	 * status = false; } return status; }
	 */
	public int uploadFileData(String inputFilePath, String filePath, String utility_code, String filename,
			String utility_name, String email_id) {
		int upload_status = 0;
		System.out.println("inputFilePath:" + inputFilePath);
		System.out.println("utility_name:" + utility_name);
		// boolean upload_status = false;
		Workbook workbook = null;
		Sheet sheet = null;
		try {

			workbook = getWorkBook(new File(inputFilePath));
			sheet = workbook.getSheetAt(0);
			String headerDetails = "";
			String[] headerNames = null;
			String header = "";
			ArrayList<ExcelTemplateVO> txnList = null;
			Iterator<Row> rowIterator = null;
			/* Build the header portion of the Output File */
			if (utility_name.contains("BAJAJ")) {
				System.out.println("in bajaj");
				// headerDetails = "Sr no.,User Number (utility Code),Settlement
				// Date,UMRN,Amount";
				//headerDetails = "SRNO,UTILITYCODE,SETTLEMENTDATE,UMRN,AMOUNT";
				headerDetails = "IMAGE_FILE_NAME,UTILITYCODE,ACTION,MANDATE_DATE,AC_TYPE,ACCNUMBER,IFSC_CODE,MICR_CODE,DEBIT_TYPE,AMOUNT,REFNO1,REFNO2,FREQUENCY,START_DATE,END_DATE,UNTILCANCEL,CUST_NAME,MOBILE,MAIL,Additional_1,Additional_2,Additional_3,Additional_4,Additional_5";		
				
				headerNames = headerDetails.split(",");

				txnList = new ArrayList<>();
				rowIterator = sheet.iterator();

				System.out.println("1:" + sheet.getRow(0).getCell(0));
				System.out.println("2:" + sheet.getRow(0).getCell(1));
				System.out.println("3:" + sheet.getRow(0).getCell(2));
				System.out.println("4:" + sheet.getRow(0).getCell(3));
				System.out.println("5:" + sheet.getRow(0).getCell(4));
				System.out.println("6:" + sheet.getRow(0).getCell(5));
				System.out.println("7:" + sheet.getRow(0).getCell(6));
				System.out.println("8:" + sheet.getRow(0).getCell(7));
				System.out.println("9:" + sheet.getRow(0).getCell(8));
				System.out.println("10:" + sheet.getRow(0).getCell(9));
				System.out.println("11:" + sheet.getRow(0).getCell(10));
				System.out.println("12:" + sheet.getRow(0).getCell(11));
				System.out.println("13:" + sheet.getRow(0).getCell(12));
				System.out.println("14:" + sheet.getRow(0).getCell(13));
				System.out.println("15:" + sheet.getRow(0).getCell(14));
				System.out.println("16:" + sheet.getRow(0).getCell(15));
				System.out.println("17:" + sheet.getRow(0).getCell(16));
				System.out.println("18:" + sheet.getRow(0).getCell(17));
				System.out.println("19:" + sheet.getRow(0).getCell(18));
				System.out.println("20:" + sheet.getRow(0).getCell(19));
				System.out.println("21:" + sheet.getRow(0).getCell(20));
				System.out.println("22:" + sheet.getRow(0).getCell(21));
				System.out.println("23:" + sheet.getRow(0).getCell(22));
				System.out.println("24:" + sheet.getRow(0).getCell(23));
				/* System.out.println("5:" + sheet.getRow(0).getCell(24)); */

				header = sheet.getRow(0).getCell(0) + "|" + sheet.getRow(0).getCell(1).toString().trim() + "|"
						+ sheet.getRow(0).getCell(2) + "|" + sheet.getRow(0).getCell(3) + "|"
						+ sheet.getRow(0).getCell(4) + "|" + sheet.getRow(0).getCell(5)+ "|" + sheet.getRow(0).getCell(6) + "|"
				+ sheet.getRow(0).getCell(7) + "|" + sheet.getRow(0).getCell(8)+ "|" + sheet.getRow(0).getCell(9) + "|"
				+ sheet.getRow(0).getCell(10) + "|" + sheet.getRow(0).getCell(11)+ "|" + sheet.getRow(0).getCell(12) + "|"
				+ sheet.getRow(0).getCell(13) + "|" + sheet.getRow(0).getCell(14)+ "|" + sheet.getRow(0).getCell(15) + "|"
				+ sheet.getRow(0).getCell(16) + "|" + sheet.getRow(0).getCell(17)+ "|" + sheet.getRow(0).getCell(18) + "|"
				+ sheet.getRow(0).getCell(19) + "|" + sheet.getRow(0).getCell(20)+ "|" + sheet.getRow(0).getCell(21) + "|"
				+ sheet.getRow(0).getCell(22) + "|" + sheet.getRow(0).getCell(23) ;
				

				rowIterator.next();
				while (rowIterator.hasNext()) {
					System.out.println("1");

					Row row = rowIterator.next();

					// Read and process each column in row
					ExcelTemplateVO excelTemplateVO = new ExcelTemplateVO();
					int count = 0;
					while (count < headerNames.length) {
						String methodName = "set" + headerNames[count];
						System.out.println("methodName:" + methodName);
						String inputCellValue = getCellValueBasedOnCellType(row, count++);
						setValueIntoObject(excelTemplateVO, ExcelTemplateVO.class, methodName, "java.lang.String",
								inputCellValue);
					}

					txnList.add(excelTemplateVO);
				}

			} else if (utility_name.contains("DHANI")) {
				System.out.println("in dhani");
				headerDetails = "MANDATE_DATE,MANDATE_ID,UMRN,CUST_REFNO,SCH_REFNO,CUST_NAME,BANK,BRANCH,BANK_CODE,AC_TYPE,ACNO,AMOUNT,FREQUENCY,DEBIT_TYPE,START_DATE,END_DATE,UNTILCANCEL,TEL_NO,MOBILE_NO,MAIL_ID,UTILITYCODE,UTILITY_NAME,STATUS,STATUS_CODE";
				headerNames = headerDetails.split(",");

				/* Read and process each Row */
				txnList = new ArrayList<>();
				rowIterator = sheet.iterator();

				System.out.println("1:" + sheet.getRow(0).getCell(0));
				System.out.println("2:" + sheet.getRow(0).getCell(1));
				System.out.println("3:" + sheet.getRow(0).getCell(2));
				System.out.println("4:" + sheet.getRow(0).getCell(3));
				System.out.println("5:" + sheet.getRow(0).getCell(4));
				System.out.println("6:" + sheet.getRow(0).getCell(5));
				System.out.println("7:" + sheet.getRow(0).getCell(6));
				System.out.println("8:" + sheet.getRow(0).getCell(7));
				System.out.println("9:" + sheet.getRow(0).getCell(8));
				System.out.println("10:" + sheet.getRow(0).getCell(9));
				System.out.println("11:" + sheet.getRow(0).getCell(10));
				System.out.println("12:" + sheet.getRow(0).getCell(11));
				System.out.println("13:" + sheet.getRow(0).getCell(12));
				System.out.println("14:" + sheet.getRow(0).getCell(13));
				System.out.println("15:" + sheet.getRow(0).getCell(14));
				System.out.println("16:" + sheet.getRow(0).getCell(15));
				System.out.println("17:" + sheet.getRow(0).getCell(16));
				System.out.println("18:" + sheet.getRow(0).getCell(17));
				System.out.println("19:" + sheet.getRow(0).getCell(18));
				System.out.println("20:" + sheet.getRow(0).getCell(19));
				System.out.println("21:" + sheet.getRow(0).getCell(20));
				System.out.println("22:" + sheet.getRow(0).getCell(21));
				System.out.println("23:" + sheet.getRow(0).getCell(22));
				System.out.println("24:" + sheet.getRow(0).getCell(23));
				 


				header = sheet.getRow(0).getCell(0) + "|" + sheet.getRow(0).getCell(1) + "|"
						+ sheet.getRow(0).getCell(2) + "|" + sheet.getRow(0).getCell(3) + "|"
						+ sheet.getRow(0).getCell(4) + "|" + sheet.getRow(0).getCell(5) + "|"
						+ sheet.getRow(0).getCell(6)+ "|"+ sheet.getRow(0).getCell(7) + "|" +
						sheet.getRow(0).getCell(8)+ "|" + sheet.getRow(0).getCell(9) + "|"
						+ sheet.getRow(0).getCell(10) + "|" + sheet.getRow(0).getCell(11)+ "|" + sheet.getRow(0).getCell(12) + "|"
					    + sheet.getRow(0).getCell(13) + "|" + sheet.getRow(0).getCell(14)+ "|" + sheet.getRow(0).getCell(15) + "|"
						+ sheet.getRow(0).getCell(16) + "|" + sheet.getRow(0).getCell(17)+ "|" + sheet.getRow(0).getCell(18) + "|"
						+ sheet.getRow(0).getCell(19) + "|" + sheet.getRow(0).getCell(20)+ "|" + sheet.getRow(0).getCell(21) + "|"
						+ sheet.getRow(0).getCell(22) + "|" + sheet.getRow(0).getCell(23)  ;

				rowIterator.next();
				while (rowIterator.hasNext()) {
					System.out.println("1");

					Row row = rowIterator.next();

					// Read and process each column in row
					ExcelTemplateVO excelTemplateVO = new ExcelTemplateVO();
					int count = 0;
					while (count < headerNames.length) {
						String methodName = "set" + headerNames[count];
						System.out.println("methodName:" + methodName);
						
						String inputCellValue = getCellValueBasedOnCellType(row, count++);
						setValueIntoObject(excelTemplateVO, ExcelTemplateVO.class, methodName, "java.lang.String",
								inputCellValue);
					}

					txnList.add(excelTemplateVO);
				}

			}

			String[] headerStatus = headerFormat(header);
			System.out.println("header format:" + headerStatus[0]);
			// check file format

			if (headerStatus[0].trim().equals("1")) {

				achSponsorDao = new ACHSponsorDao();

				// validate null values from excel cell
				int nullStatus = validateNullValues(txnList, utility_name);
				
			
				//int nullStatus = 10;
				if (nullStatus == 10) {
					
					// validate Amount from excel cell
					int Status = validateAmount(txnList);
					if (Status == 10) {
						
						
						// validate settlement date
						//boolean statusStlStatus = validateSettlementDate(txnList);
							System.out.println("validateUtilityCode txnList:"+txnList.size()+"-utility_code:"+utility_code);
							boolean status = achSponsorDao.validateUtilityCode(txnList, utility_code);
							
							
							
							//boolean status = false;
							if (status) {
								System.out.println("Valid");
								boolean uploadStatus = achSponsorDao.uploadFileDatils(filePath, filename, utility_name,
										utility_code, email_id, txnList);
								// insert excel file details
								if (uploadStatus) {
									upload_status = 1;
									boolean insert_status = achSponsorDao.insertexcelFileRecords(txnList, filename,
											headerStatus[1]);
									System.out.println("insert_status:"+insert_status);
									if (insert_status) {
										upload_status = 1;
									} else {
										achSponsorDao.deleteDDFile(filename);
										upload_status = 9;
									}

								} else {
									// if any exception while uploading then delete the file
									achSponsorDao.deleteDDFile(filename);
									upload_status = 0;
								}

							} else {
								System.out.println("Invalid");
								upload_status = 0;
							}
						
						
						
					}else {
						
						System.out.println("Invalid Amount");
						upload_status = 12;
					}
					

					 
				} else {
					// if null value in cell
					upload_status = 11;
				}

			} else {
				// invalid header format
				upload_status = 3;
			}
			// achSponsorDao.saveFileDataInDB(txnList);

		} catch (Exception ex) {
			ex.printStackTrace(System.out);
			upload_status = 0;
		}

		return upload_status;
	}

	public static Workbook getWorkBook(File fileName) {
		System.out.println("fileName:" + fileName);
		Workbook workbook = null;
		try {
			String myFileName = fileName.getName();
			System.out.println("myFileName:" + myFileName);
			String extension = myFileName.substring(myFileName.lastIndexOf("."));
			System.out.println("extension:" + extension);
			if (extension.equalsIgnoreCase(".xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(fileName));
			} else if (extension.equalsIgnoreCase(".xlsx")) {
				workbook = new XSSFWorkbook(new FileInputStream(fileName));
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
		return workbook;
	}

	public static String getCellValueBasedOnCellType(Row rowData, int columnPosition) {
		String cellValue = null;
		Cell cell = rowData.getCell(columnPosition);
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				String inputCellValue = cell.getStringCellValue();
				if (inputCellValue.endsWith(".0")) {
					inputCellValue = inputCellValue.substring(0, inputCellValue.length() - 2);
				}
				cellValue = inputCellValue;
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

				if (DateUtil.isCellDateFormatted(cell)) {
					System.out.println("date");
					// cellValue = cell.getDateCellValue().toString();
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date date = cell.getDateCellValue();
					System.out.println("df.format(date):" + df.format(date));
					cellValue = df.format(date);

				} else {
					System.out.println("not date");
					Double doubleVal = new Double(cell.getNumericCellValue());
					cellValue = Integer.toString(doubleVal.intValue());
				}
			}

		}
		return cellValue;
	}

	private static <T> void setValueIntoObject(Object obj, Class<T> clazz, String methodNameForField, String dataType,
			String inputCellValue) throws SecurityException, NoSuchMethodException, ClassNotFoundException,
			NumberFormatException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		Method meth = clazz.getMethod(methodNameForField, Class.forName(dataType));
		T t = clazz.cast(obj);
		System.out.println("inputCellValue:" + inputCellValue);
		if ("java.lang.Double".equalsIgnoreCase(dataType)) {
			meth.invoke(t, Double.parseDouble(inputCellValue));
		} else if (!"java.lang.Integer".equalsIgnoreCase(dataType)) {
			meth.invoke(t, inputCellValue);
		} else {
			System.out.println("else data format");
			meth.invoke(t, Integer.parseInt(inputCellValue));
		}
	}

	private String[] headerFormat(String header) {

		String[] result = new String[2];

		System.out.println("header:" + header);

		/*String excelHeader1 = "UTILITYCODE|TRANSACTIONTYPE|SETTLEMENTDATE|BENEFICIARYACHOLDERNAME|AMOUNT|BENEFICIARYACNO|UMRN\r\n"
				+ "";*/
		
		String excelHeader1 = "Image File Name|UTILITYCODE|ACTION|MANDATE_DATE|AC_TYPE|ACCNUMBER|IFSC CODE|MICR CODE|DEBIT TYPE|AMOUNT|REFNO1|REFNO2|FREQUENCY|START_DATE|END_DATE|UNTILCANCEL|CUST_NAME|MOBILE|MAIL|Additional_1|Additional_2|Additional_3|Additional_4|Additional_5\r\n"
		+ "";
		
		/*
		 * String excelHeader1 =
		 * "IMAGE_FILE_NAME|UTILITYCODE|ACTION|MANDATE_DATE|AC_TYPE|ACCNUMBER|IFSC_CODE|MICR_CODE|DEBIT_TYPE|AMOUNT|REFNO1|REFNO2|FREQUENCY|START_DATE|END_DATE|UNTILCANCEL|CUST_NAME|MOBILE|MAIL|Additional_1|Additional_2|Additional_3|Additional_4|Additional_5\\r\\n\"
		 * + "";
		 */

		/*
		 * String excelHeader2 =
		 * "Sr no.|User Number (utility Code)|Settlement Date|UMRN|Amount";
		 */

		String excelHeader2 = "MANDATE_DATE|MANDATE_ID|UMRN|CUST_REFNO|SCH_REFNO|CUST_NAME|BANK|BRANCH|BANK_CODE|AC_TYPE|ACNO|AMOUNT|FREQUENCY|DEBIT_TYPE|START_DATE|END_DATE|UNTILCANCEL|TEL_NO|MOBILE_NO|MAIL_ID|UTILITYCODE|UTILITY_NAME|STATUS|STATUS_CODE";
		
		if (excelHeader1.trim().equals(header.trim())) {
			System.out.println("7");
			System.out.println("excelHeader1:" + excelHeader1);
			result[0] = "1";
			result[1] = excelHeader1.trim();
		} else if (excelHeader2.trim().equals(header.trim())) {
			System.out.println("excelHeader2:" + excelHeader2);
			System.out.println("8");
			result[0] = "1";
			result[1] = excelHeader2.trim();

		} else {
			System.out.println("excelHeader1:" + excelHeader1);
			System.out.println("excelHeader2:" + excelHeader2);
			System.out.println("9");
			result[0] = "0";
			result[1] = "Invalid Format";

		}
		return result;
	}

}