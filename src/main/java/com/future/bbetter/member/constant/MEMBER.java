package com.future.bbetter.member.constant;

public enum MEMBER {
	
	GENDER_MALE(1, "男"),
	GENDER_FEMALE(2, "女");
	
	public final Integer value;
	public final String depiction;
	private MEMBER(Integer value, String depiction){
		this.value = value;
		this.depiction = depiction;
	}
	
}
