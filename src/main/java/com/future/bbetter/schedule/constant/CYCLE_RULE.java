package com.future.bbetter.schedule.constant;

public enum CYCLE_RULE {
	
	
	/** 循環週期: 每日(1) **/
	PERIOD_DAILY(1, "每日"),
	/** 循環週期: 每週(2) **/
	PERIOD_WEEKLY(2, "每週"),
	/** 循環週期: 每月(3) **/
	PERIOD_MONTHLY(3, "每月"),
	/** 循環週期: 每年(4) **/
	PERIOD_ANNUALLY(4, "每年"),
	
	
	/** 無效(0) **/
	IS_VALID_NO(0, "無效"),
	/** 有效(1) **/
	IS_VALID_YES(1, "有效");
	
	public final Integer value;
	public final String depiction;
	private CYCLE_RULE(Integer value, String depiction){
		this.value = value;
		this.depiction = depiction;
	}

}
