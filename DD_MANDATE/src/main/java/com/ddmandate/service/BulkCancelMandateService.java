package com.ddmandate.service;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
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

import com.ddmandate.dao.CancelBulkMandateDao;
import com.ddmandate.util.ExcelTemplateVO;

public class BulkCancelMandateService {

	CancelBulkMandateDao cancelBulkMandateDao;

	
	//validate Amount of excel cell
	
	
	
	
	
	
	// validate null values of excel cell

	public int validateNullValues(List<com.ddmandate.util.ExcelTemplateVO> txnList) {

		int status = 10;
		for (com.ddmandate.util.ExcelTemplateVO vo : txnList) {

			

				if (vo.getUTILITY_CODE() == null) {
					status = 11;
				} else if (vo.getUMRN() == null) {
					status = 11;
				} else if (vo.getCANCELLATION_CODE() == null) {
					status = 11;
				} 

			
			

		}
		return status;

	}

		public int uploadFileData(String inputFilePath, String filePath, String utility_code, String filename, String uMRN, String cancellation_code) throws NumberFormatException, SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SQLException {
		int upload_status = 0;
		System.out.println("inputFilePath:" + inputFilePath);
		
		// boolean upload_status = false;
		Workbook workbook = null;
		Sheet sheet = null;
		

			workbook = getWorkBook(new File(inputFilePath));
			sheet = workbook.getSheetAt(0);
			String headerDetails = "";
			String[] headerNames = null;
			String header = "";
			ArrayList<ExcelTemplateVO> txnList = null;
			Iterator<Row> rowIterator = null;
			/* Build the header portion of the Output File */
			
				System.out.println("in Mandate Cancellation ");
				// headerDetails = "Sr no.,User Number (utility Code),Settlement
				// Date,UMRN,Amount";
				//headerDetails = "SRNO,UTILITYCODE,SETTLEMENTDATE,UMRN,AMOUNT";
				headerDetails = "UTILITY_CODE,UMRN,CANCELLATION_CODE";		
				
				headerNames = headerDetails.split(",");

				txnList = new ArrayList<>();
				rowIterator = sheet.iterator();

				System.out.println("1:" + sheet.getRow(0).getCell(0));
				System.out.println("2:" + sheet.getRow(0).getCell(1));
				System.out.println("3:" + sheet.getRow(0).getCell(2));
				
				header = sheet.getRow(0).getCell(0) + "|" + sheet.getRow(0).getCell(1).toString().trim() + "|"
						+ sheet.getRow(0).getCell(2) ;
				

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

				cancelBulkMandateDao = new CancelBulkMandateDao();

				// validate null values from excel cell
				int nullStatus = validateNullValues(txnList);
				
			
				//int nullStatus = 10;
				if (nullStatus == 10) {
						// validate settlement date
						//boolean statusStlStatus = validateSettlementDate(txnList);
							System.out.println("validateUtilityCode txnList:"+txnList.size()+"Going to the Dao Class");
							//boolean status = CancelBulkMandateDao.validateUtilityCode(txnList, utility_code);
							
							
							
							/* boolean status = false; */
							 nullStatus = 10;
							if (nullStatus == 10) {
								System.out.println("Valid");
								boolean uploadStatus = CancelBulkMandateDao.uploadFileDatils(filePath, filename,
										utility_code, txnList);
								// insert excel file details
								if (uploadStatus) {
									upload_status = 1;
									boolean insert_status = CancelBulkMandateDao.insertexcelFileRecords(txnList, filename, header);
									System.out.println("insert_status:"+insert_status);
									//insert_status =true;
									if (insert_status) {
										upload_status = 1;
									} else {
										System.out.println("CancelBulkMandateDao.deleteDDFile");
										CancelBulkMandateDao.deleteDDFile(filename);
										upload_status = 9;
									}

								} else {
									// if any exception while uploading then delete the file
									CancelBulkMandateDao.deleteDDFile(filename);
									upload_status = 0;
								}

							} else {
								System.out.println("Invalid");
								upload_status = 0;
							}
						
						
				} else {
					// if null value in cell
					upload_status = 11;
				}
				return upload_status;

			
			}
			// achSponsorDao.saveFileDataInDB(txnList);

		

			/* return upload_status; */
	

	

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
		
		String excelHeader1 = "UTILITY_CODE,UMRN,CANCELLATION_CODE"
		+ "";
		
		if (excelHeader1.trim().equals(header.trim())) {
			System.out.println("7");
			System.out.println("excelHeader1:" + excelHeader1);
			result[0] = "1";
			result[1] = excelHeader1.trim();
	        } else {
			System.out.println("excelHeader1:" + excelHeader1);
			System.out.println("9");
			result[0] = "0";
			result[1] = "Invalid Format";

		}
		return result;
	}

}