package com.future.bbetter.schedule.constant;

public enum SCHEDULE_HAD {
	/** 未開始(0) **/
	STATUS_NOT_START(0, "未開始"),
	/** 未開始(1) **/
	STATUS_SUCCESS(1, "成功"),
	/** 失敗(2) **/
	STATUS_FAIL(2, "失敗"),
	
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
