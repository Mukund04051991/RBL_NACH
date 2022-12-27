package com.ddmandate.util;

public class ExcelTemplateVO {

	private String UTILITYCODE;
	private String TRANSACTIONTYPE;
	private String SETTLEMENTDATE;
	private String BENEFICIARYACHOLDERNAME;
	private String AMOUNT;
	private String BENEFICIARYACNO;
	private String UMRN;
	private String SRNO;

	public String getUTILITYCODE() {
		return UTILITYCODE;
	}

	public void setUTILITYCODE(String uTILITYCODE) {
		UTILITYCODE = uTILITYCODE;
	}

	public String getTRANSACTIONTYPE() {
		return TRANSACTIONTYPE;
	}

	public void setTRANSACTIONTYPE(String tRANSACTIONTYPE) {
		TRANSACTIONTYPE = tRANSACTIONTYPE;
	}

	public String getSETTLEMENTDATE() {
		return SETTLEMENTDATE;
	}

	public void setSETTLEMENTDATE(String sETTLEMENTDATE) {
		SETTLEMENTDATE = sETTLEMENTDATE;
	}

	public String getBENEFICIARYACHOLDERNAME() {
		return BENEFICIARYACHOLDERNAME;
	}

	public void setBENEFICIARYACHOLDERNAME(String bENEFICIARYACHOLDERNAME) {
		BENEFICIARYACHOLDERNAME = bENEFICIARYACHOLDERNAME;
	}

	public String getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public String getBENEFICIARYACNO() {
		return BENEFICIARYACNO;
	}

	public void setBENEFICIARYACNO(String bENEFICIARYACNO) {
		BENEFICIARYACNO = bENEFICIARYACNO;
	}

	public String getUMRN() {
		return UMRN;
	}

	public void setUMRN(String uMRN) {
		UMRN = uMRN;
	}

	public String getSRNO() {
		return SRNO;
	}

	public void setSRNO(String sRNO) {
		SRNO = sRNO;
	}

	private String IMAGE_FILE_NAME;
	private String ACTION;
	private String MANDATE_DATE;
	private String AC_TYPE;
	//private String account_number;
	private String ACCNUMBER;
	public String getACCNUMBER() {
		return ACCNUMBER;
	}

	public void setACCNUMBER(String aCCNUMBER) {
		ACCNUMBER = aCCNUMBER;
	}

	private String IFSC_CODE;
	private String MICR_CODE;
	public String getAC_TYPE() {
		return AC_TYPE;
	}

	public void setAC_TYPE(String aC_TYPE) {
		AC_TYPE = aC_TYPE;
	}

	private String DEBIT_TYPE;
	private String AMOUNT1;
	private String REFNO1;
	private String REFNO2;
	private String FREQUENCY;
	private String START_DATE;
	private String END_DATE;
	private String UNTILCANCEL;
	private String CUST_NAME;
	private String MOBILE;
	private String MAIL;
	private String Additional_1;
	private String Additional_2;
	private String Additional_3;
	private String Additional_4;
	private String Additional_5;
	

	public String getIMAGE_FILE_NAME() {
		return IMAGE_FILE_NAME;
	}

	public void setIMAGE_FILE_NAME(String iMAGE_FILE_NAME) {
		IMAGE_FILE_NAME = iMAGE_FILE_NAME;
	}

	public String getACTION() {
		return ACTION;
	}

	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}

	public String getMANDATE_DATE() {
		return MANDATE_DATE;
	}

	public void setMANDATE_DATE(String mANDATE_DATE) {
		MANDATE_DATE = mANDATE_DATE;
	}

	/*
	 * public String getAccount_type() { return AC_TYPE; }
	 * 
	 * public void setAccount_type(String account_type) { this.AC_TYPE =
	 * account_type; }
	 */

	

	public String getIFSC_CODE() {
		return IFSC_CODE;
	}

	public void setIFSC_CODE(String iFSC_CODE) {
		IFSC_CODE = iFSC_CODE;
	}

	public String getMICR_CODE() {
		return MICR_CODE;
	}

	public void setMICR_CODE(String mICR_CODE) {
		MICR_CODE = mICR_CODE;
	}

	public String getDEBIT_TYPE() {
		return DEBIT_TYPE;
	}

	public void setDEBIT_TYPE(String dEBIT_TYPE) {
		DEBIT_TYPE = dEBIT_TYPE;
	}

	public String getAMOUNT1() {
		return AMOUNT1;
	}

	public void setAMOUNT1(String aMOUNT1) {
		AMOUNT1 = aMOUNT1;
	}

		public String getFREQUENCY() {
		return FREQUENCY;
	}

	public void setFREQUENCY(String fREQUENCY) {
		FREQUENCY = fREQUENCY;
	}

	public String getUNTILCANCEL() {
		return UNTILCANCEL;
	}

	public void setUNTILCANCEL(String uNTILCANCEL) {
		UNTILCANCEL = uNTILCANCEL;
	}

	public String getSTART_DATE() {
		return START_DATE;
	}

	public String getREFNO1() {
		return REFNO1;
	}

	public void setREFNO1(String rEFNO1) {
		REFNO1 = rEFNO1;
	}

	public String getREFNO2() {
		return REFNO2;
	}

	public void setREFNO2(String rEFNO2) {
		REFNO2 = rEFNO2;
	}

	public void setSTART_DATE(String sTART_DATE) {
		START_DATE = sTART_DATE;
	}

	public String getEND_DATE() {
		return END_DATE;
	}

	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}

	 public String getCUST_NAME() {
		return CUST_NAME;
	}

	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}

	

	

	public String getMOBILE() {
		return MOBILE;
	}

	public String getMAIL() {
		return MAIL;
	}

	public void setMAIL(String mAIL) {
		MAIL = mAIL;
	}

	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}

	public String getAdditional_1() {
		return Additional_1;
	}

	public void setAdditional_1(String additional_1) {
		Additional_1 = additional_1;
	}

	public String getAdditional_2() {
		return Additional_2;
	}

	public void setAdditional_2(String additional_2) {
		Additional_2 = additional_2;
	}

	public String getAdditional_3() {
		return Additional_3;
	}

	public void setAdditional_3(String additional_3) {
		Additional_3 = additional_3;
	}

	public String getAdditional_4() {
		return Additional_4;
	}

	public void setAdditional_4(String additional_4) {
		Additional_4 = additional_4;
	}

	public String getAdditional_5() {
		return Additional_5;
	}

	public void setAdditional_5(String additional_5) {
		Additional_5 = additional_5;
	}

	
	//added for DHANI
	
	//private String MANDATE_DATE;
	private String MANDATE_ID;
	//private String UMRN;
	private String CUST_REFNO;
	private String SCH_REFNO;
	private String CUSTOMER_NAME;
	private String BANK;
	private String BRANCH;
	private String BANK_CODE;
	private String ACCOUNT_TYPE;
	private String ACNO;
	//private String AMOUNT;
	//private String FREQUENCY;
	//private String DEBIT_TYPE;
	//private String START_DATE;
	//private String END_DATE;
	private String UNTIL_CANCEL;
	private String TEL_NO;
	private String MOBILE_NO;
	private String MAIL_ID;
	
	private String UTILITY_NAME;
	private String STATUS;
	private String STATUS_CODE;

	public String getMANDATE_ID() {
		return MANDATE_ID;
	}

	public void setMANDATE_ID(String mANDATE_ID) {
		MANDATE_ID = mANDATE_ID;
	}

	

	public String getCUST_REFNO() {
		return CUST_REFNO;
	}

	public void setCUST_REFNO(String cUST_REFNO) {
		CUST_REFNO = cUST_REFNO;
	}

	

	public String getSCH_REFNO() {
		return SCH_REFNO;
	}

	public void setSCH_REFNO(String sCH_REFNO) {
		SCH_REFNO = sCH_REFNO;
	}

	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}

	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}

	public String getBANK() {
		return BANK;
	}

	public void setBANK(String bANK) {
		BANK = bANK;
	}

	public String getBRANCH() {
		return BRANCH;
	}

	public void setBRANCH(String bRANCH) {
		BRANCH = bRANCH;
	}

	public String getBANK_CODE() {
		return BANK_CODE;
	}

	public void setBANK_CODE(String bANK_CODE) {
		BANK_CODE = bANK_CODE;
	}

	public String getACCOUNT_TYPE() {
		return ACCOUNT_TYPE;
	}

	public void setACCOUNT_TYPE(String aCCOUNT_TYPE) {
		ACCOUNT_TYPE = aCCOUNT_TYPE;
	}

	
	public String getACNO() {
		return ACNO;
	}

	public void setACNO(String aCNO) {
		ACNO = aCNO;
	}

	public String getUNTIL_CANCEL() {
		return UNTIL_CANCEL;
	}

	public void setUNTIL_CANCEL(String uNTIL_CANCEL) {
		UNTIL_CANCEL = uNTIL_CANCEL;
	}

	public String getTEL_NO() {
		return TEL_NO;
	}

	public void setTEL_NO(String tEL_NO) {
		TEL_NO = tEL_NO;
	}

	

	public String getMOBILE_NO() {
		return MOBILE_NO;
	}

	public void setMOBILE_NO(String mOBILE_NO) {
		MOBILE_NO = mOBILE_NO;
	}

	public String getMAIL_ID() {
		return MAIL_ID;
	}

	public void setMAIL_ID(String mAIL_ID) {
		MAIL_ID = mAIL_ID;
	}

	

	public String getUTILITY_NAME() {
		return UTILITY_NAME;
	}

	public void setUTILITY_NAME(String uTILITY_NAME) {
		UTILITY_NAME = uTILITY_NAME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getSTATUS_CODE() {
		return STATUS_CODE;
	}

	public void setSTATUS_CODE(String sTATUS_CODE) {
		STATUS_CODE = sTATUS_CODE;
	}

	
	
	
	

}