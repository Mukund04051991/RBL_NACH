package com.ddmandate.bean;

public class UtilityDetails {

	private String utility_name;
	private String utility_code;
	private String email_id;
	
	private String CDTR_ACCT_NO;
	private String CDTR_AGNT_NAME;
	private String DATA_ENTRY_PENDING_STATUS;
	private String MANDATE_COUNT; 
	
	
	public String getMANDATE_COUNT() {
		return MANDATE_COUNT;
	}

	public void setMANDATE_COUNT(String mANDATE_COUNT) {
		MANDATE_COUNT = mANDATE_COUNT;
	}

	public String getDATA_ENTRY_PENDING_STATUS() {
		return DATA_ENTRY_PENDING_STATUS;
	}

	public void setDATA_ENTRY_PENDING_STATUS(String dATA_ENTRY_PENDING_STATUS) {
		DATA_ENTRY_PENDING_STATUS = dATA_ENTRY_PENDING_STATUS;
	}

	public String getCDTR_ACCT_NO() {
		return CDTR_ACCT_NO;
	}

	public void setCDTR_ACCT_NO(String cDTR_ACCT_NO) {
		CDTR_ACCT_NO = cDTR_ACCT_NO;
	}

	public String getCDTR_AGNT_NAME() {
		return CDTR_AGNT_NAME;
	}

	public void setCDTR_AGNT_NAME(String cDTR_AGNT_NAME) {
		CDTR_AGNT_NAME = cDTR_AGNT_NAME;
	}

	public String getUtility_name() {
		return utility_name;
	}

	public void setUtility_name(String utility_name) {
		this.utility_name = utility_name;
	}

	public String getUtility_code() {
		return utility_code;
	}

	public void setUtility_code(String utility_code) {
		this.utility_code = utility_code;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

}
