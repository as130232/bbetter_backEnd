package com.future.bbetter.member.dto;

import java.util.Date;
import java.util.List;

import com.future.bbetter.member.model.Friend;
import com.future.bbetter.member.model.Member;

import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor class FriendDTO {
	
//	public static FriendDTO fromDTO(MemberDTO memberDTO, List<MemberDTO> friendsDTO) {
//		FriendDTO friendDTO = new FriendDTO();
//		friendDTO.setMemberDTO(memberDTO);
//		friendDTO.setFriendsDTO(friendsDTO);
//		return friendDTO;
//	}
//	
//	private MemberDTO memberDTO;
//	private List<MemberDTO> friendsDTO;
	
	public static FriendDTO fromEntity(Member member, Friend friend) {
		FriendDTO friendDTO = new FriendDTO();
		friendDTO.setName(member.getName());
		friendDTO.setEmail(member.getEmail());
		friendDTO.setGender(member.getGender());
		friendDTO.setBirthday(member.getBirthday());
		friendDTO.setCreatedate(friend.getCreatedate());
		return friendDTO;
	}
	
	private String email;
	private String name;
	private Integer gender;
	private Date birthday;
	private Date createdate;
	
}
