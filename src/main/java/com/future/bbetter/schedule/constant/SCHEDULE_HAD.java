package com.future.bbetter.schedule.constant;

public enum SCHEDULE_HAD {
	/** 未開始(0) **/
	STATUS_NOT_START(0, "未開始"),
	/** 未開始(1) **/
	STATUS_SUCCESS(1, "成功"),
	/** 失敗(2) **/
	STATUS_FAIL(2, "失敗"),
	
	
	/** 該行程中的權限:無權限(0) **/
	AUTHORITY_NO_PERMISSION(0, "無權限"),
	/** 該行程中的權限:團員(1) **/
	AUTHORITY_MEMBER(1, "團員"),
	/** 該行程中的權限:團長(2) **/
	AUTHORITY_LEADER(2, "團長"),
	
	
	/** 無效(0) **/
	IS_VALID_NO(0, "無效"),
	/** 有效(1) **/
	IS_VALID_YES(1, "有效");
	
	
	public final Integer value;
	public final String depiction;
	private SCHEDULE_HAD(Integer value, String depiction){
		this.value = value;
		this.depiction = depiction;
	}
	
}
