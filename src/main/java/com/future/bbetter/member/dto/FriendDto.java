package com.future.bbetter.member.dto;

import java.util.Date;
import java.util.List;

import com.future.bbetter.member.model.Friend;
import com.future.bbetter.member.model.Member;

import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor class FriendDto {
	
	private Long friendId;
	private String email;
	private String name;
	private Integer gender;
	private Date birthday;
	private Date createdate;
	private String imageUrl;
	private Integer isAccept;
	
	
	public static FriendDto from(Member memberByFriend, Friend friend) {
		FriendDto friendDto = new FriendDto();
		friendDto.setFriendId(friend.getFriendId());
		friendDto.setName(memberByFriend.getName());
		friendDto.setEmail(memberByFriend.getEmail());
		friendDto.setGender(memberByFriend.getGender());
		friendDto.setBirthday(memberByFriend.getBirthday());
		friendDto.setImageUrl(memberByFriend.getImageUrl());
		friendDto.setCreatedate(friend.getCreatedate());
		friendDto.setIsAccept(friend.getIsAccept());
		return friendDto;
	}
}
