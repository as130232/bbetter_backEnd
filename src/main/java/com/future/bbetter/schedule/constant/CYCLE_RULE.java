package com.future.bbetter.schedule.constant;

import static java.util.stream.Collectors.toSet;

import java.util.EnumSet;
import java.util.Set;

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

	
	public static Set<CYCLE_RULE> periodSet;
	private static Set<Integer> periodIntValues;
	static {
		periodSet = EnumSet.range(PERIOD_DAILY, PERIOD_ANNUALLY);

		periodIntValues = periodSet.stream()
				.map(p -> p.value)
				.collect(toSet());
	}
	
	/**
	 * 驗證傳入的參數值是否符合定義的週期值常數資料
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年12月23日 下午6:16:31
	 * @param value 欲驗證的值
	 * @return boolean true, if value is legal; false, otherwise.
	 */
	public static boolean validatePeriod(int value) {
		return periodIntValues.contains(value);
	}
}
