package com.future.bbetter.schedule.constant;

public enum SCHEDULE_OWNER {
	
	/** 來源:其它(0) **/
	SOURCE_OTHERS(0, "其它"),
	
	/** 來源:會員(1) **/
	SOURCE_MEMBER(1, "會員"),
	
	/** 無效(0) **/
	IS_VALID_NO(0, "無效"),
	/** 有效(1) **/
	IS_VALID_YES(1, "有效");
	
	
	public final Integer value;
	public final String depiction;
	private SCHEDULE_OWNER(Integer value, String depiction){
		this.value = value;
		this.depiction = depiction;
	}
}
