package com.future.bbetter.schedule.constant;

public enum SCHEDULE {
	
	/** 行程狀態:單次型(1) **/
	STATUS_ONCE(1, "單次型"),
	/** 行程狀態:持續型(2) **/
	STATUS_CONTINUOUS(2, "持續型"),
	
	/** 是否為周期性行程:否(0) 預設 **/
	IS_CYCLE_NO(0, "否"),
	/** 是否為周期性行程:是(1) **/
	IS_CYCLE_YES(1, "是"),
	
	/** 是否需要提醒:否(0) 預設 **/
	IS_NEED_REMIND_NO(0, "否"),
	/** 是否需要提醒:是(1) **/
	IS_NEED_REMIND_YES(1, "是"),
	
	/** 是否為團體行程:否(0) 預設 **/
	IS_TEAM_SCHEDULE_NO(0, "否"),
	/** 是否為團體行程:是(1) **/
	IS_TEAM_SCHEDULE_YES(1, "是"),
	
	/** 公開性:私人(1) 預設 **/
	VISIBILITY_PRIVATE(1, "私人"),
	/** 公開性:朋友(2) **/
	VISIBILITY_FRIEND(2, "朋友"),
	/** 公開性:公會(3) **/
	VISIBILITY_GUILD(3, "公會"),
	/** 公開性:公開(4) **/
	VISIBILITY_PUBLIC(4, "公開"),
	
	/** 無效(0) **/
	IS_VALID_NO(0, "無效"),
	/** 有效(1) 預設 **/
	IS_VALID_YES(1, "有效");
	
	
	public final Integer value;
	public final String depiction;
	private SCHEDULE(Integer value, String depiction){
		this.value = value;
		this.depiction = depiction;
	}
	
}
