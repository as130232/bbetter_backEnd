package com.future.bbetter.member.model;

import java.io.Serializable;

public class FriendsPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long memberId;
	private Long friendMemberId;
	
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getFriendMemberId() {
		return friendMemberId;
	}
	public void setFriendMemberId(Long friendMemberId) {
		this.friendMemberId = friendMemberId;
	}
	
}
