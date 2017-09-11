package com.future.bbetter.member.constant;

public enum FRIEND {
	/** 還未接收好友邀請 **/
	IS_ACCEPT_NO(0, "還未接收邀請"),
	/** 已接收好友邀請 **/
	IS_ACCEPT_YES(1, "已接收邀請"),
	/** 是否被封鎖:無 **/
	IS_BLOCKADE_NO(0, "正常"),
	/** 是否被封鎖:已封鎖 **/
	IS_BLOCKADE_YES(1, "已封鎖");
	
	public final Integer value;
	public final String depiction;
	private FRIEND(Integer value, String depiction){
		this.value = value;
		this.depiction = depiction;
	}
}
