package com.ddmandate.bean;

public class UtilityDetails {

	private String utility_name;
	private String utility_code;
	private String email_id;
	
	private String CDTR_ACCT_NO;
	private String CDTR_AGNT_NAME;
	private String DATA_ENTRY_PENDING_STATUS;
	private String MANDATE_COUNT; 
	
	
	private String PENDING_VERIFICATION_QUEUE;
	private String SENDBACK_DATA_ENTRY_PENDING;
	private String PENDING_VERIFICATION;
	private String SENBACK_BACK_FOR_VERIFICATION;
	private String VERIFIED;
	private String REJECTED;
	private String ACTION_PENDING;
	private String ACK_RECEVIED;
	private String NACK_RECEIVED;
	private String BATCH_ID;
	public String getBATCH_ID() {
		return BATCH_ID;
	}

	public String setBATCH_ID(String bATCH_ID) {
		return BATCH_ID = bATCH_ID;
	}

	public String getMMS_TYPE() {
		return MMS_TYPE;
	}

	public String setMMS_TYPE(String mMS_TYPE) {
		return MMS_TYPE = mMS_TYPE;
	}

	private String MMS_TYPE;
	
	
	public String getPENDING_VERIFICATION_QUEUE() {
		return PENDING_VERIFICATION_QUEUE;
	}

	public String setPENDING_VERIFICATION_QUEUE(String pENDING_VERIFICATION_QUEUE) {
		return PENDING_VERIFICATION_QUEUE = pENDING_VERIFICATION_QUEUE;
	}

	public String getSENDBACK_DATA_ENTRY_PENDING() {
		return SENDBACK_DATA_ENTRY_PENDING;
	}

	public String setSENDBACK_DATA_ENTRY_PENDING(String sENDBACK_DATA_ENTRY_PENDING) {
		return SENDBACK_DATA_ENTRY_PENDING = sENDBACK_DATA_ENTRY_PENDING;
	}

	public String getPENDING_VERIFICATION() {
		return PENDING_VERIFICATION;
	}

	public String setPENDING_VERIFICATION(String pENDING_VERIFICATION) {
		return PENDING_VERIFICATION = pENDING_VERIFICATION;
	}

	public String getSENBACK_BACK_FOR_VERIFICATION() {
		return SENBACK_BACK_FOR_VERIFICATION;
	}

	public String setSENBACK_BACK_FOR_VERIFICATION(String sENBACK_BACK_FOR_VERIFICATION) {
		return SENBACK_BACK_FOR_VERIFICATION = sENBACK_BACK_FOR_VERIFICATION;
	}

	public String getVERIFIED() {
		return VERIFIED;
	}

	public String setVERIFIED(String vERIFIED) {
		return VERIFIED = vERIFIED;
	}

	public String getREJECTED() {
		return REJECTED;
	}

	public String setREJECTED(String rEJECTED) {
		return REJECTED = rEJECTED;
	}

	public String getACTION_PENDING() {
		return ACTION_PENDING;
	}

	public String setACTION_PENDING(String aCTION_PENDING) {
		return ACTION_PENDING = aCTION_PENDING;
	}

	public String getACK_RECEVIED() {
		return ACK_RECEVIED;
	}

	public String setACK_RECEVIED(String aCK_RECEVIED) {
		return ACK_RECEVIED = aCK_RECEVIED;
	}

	public String getNACK_RECEIVED() {
		return NACK_RECEIVED;
	}

	public String setNACK_RECEIVED(String nACK_RECEIVED) {
		return NACK_RECEIVED = nACK_RECEIVED;
	}

	public String getMANDATE_COUNT() {
		return MANDATE_COUNT;
	}

	public String setMANDATE_COUNT(String mANDATE_COUNT) {
		return MANDATE_COUNT = mANDATE_COUNT;
	}

	public String getDATA_ENTRY_PENDING_STATUS() {
		return DATA_ENTRY_PENDING_STATUS;
	}

	public String setDATA_ENTRY_PENDING_STATUS(String dATA_ENTRY_PENDING_STATUS) {
		return DATA_ENTRY_PENDING_STATUS = dATA_ENTRY_PENDING_STATUS;
	}

	public String getCDTR_ACCT_NO() {
		return CDTR_ACCT_NO;
	}

	public String setCDTR_ACCT_NO(String cDTR_ACCT_NO) {
		return CDTR_ACCT_NO = cDTR_ACCT_NO;
	}

	public String getCDTR_AGNT_NAME() {
		return CDTR_AGNT_NAME;
	}

	public String setCDTR_AGNT_NAME(String cDTR_AGNT_NAME) {
		return CDTR_AGNT_NAME = cDTR_AGNT_NAME;
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
