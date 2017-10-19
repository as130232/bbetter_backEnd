package com.future.bbetter.member.constant;

public enum THIRD_PART_AUTH {
	
	SOURCE_FACEBOOK(1, "Facebook"),
	SOURCE_GOOGLE(2, "Google"),
	SOURCE_TWITTER(3, "Twitter");
	
	public final Integer value;
	public final String depiction;
	private THIRD_PART_AUTH(Integer value, String depiction){
		this.value = value;
		this.depiction = depiction;
	}
}
