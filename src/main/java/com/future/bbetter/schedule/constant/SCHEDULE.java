package com.future.bbetter.schedule.constant;

public enum SCHEDULE {
	
	/** 是否需要提醒:否 **/
	IS_NEED_REMIND_NO(0, "否"),
	/** 是否需要提醒:是 **/
	IS_NEED_REMIND_YES(0, "是"),
	
	/** 是否為團體行程:否 **/
	IS_TEAM_SCHEDULE_NO(0, "否"),
	/** 是否為團體行程:是 **/
	IS_TEAM_SCHEDULE_YES(1, "是"),
	
	/** 無效(0) **/
	IS_VALID_NO(0, "無效"),
	/** 有效(1) **/
	IS_VALID_YES(1, "有效");
	
	
	public final Integer value;
	public final String depiction;
	private SCHEDULE(Integer value, String depiction){
		this.value = value;
		this.depiction = depiction;
	}
	
}
