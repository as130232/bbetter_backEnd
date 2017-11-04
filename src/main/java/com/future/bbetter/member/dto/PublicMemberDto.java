package com.future.bbetter.member.dto;

import java.util.Date;

import com.future.bbetter.member.model.Member;

import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor class PublicMemberDto {
	private String email;
	private String name;
	private Date birthday;
	private String address;
	private Integer gender;
	private String imageUrl;
	
	public static PublicMemberDto from(MemberDto memberDto) {
		PublicMemberDto publicMemberDto = new PublicMemberDto();
		publicMemberDto.setName(memberDto.getName());
		publicMemberDto.setEmail(memberDto.getEmail());
		publicMemberDto.setBirthday(memberDto.getBirthday());
		publicMemberDto.setGender(memberDto.getGender());
		publicMemberDto.setAddress(memberDto.getAddress());
		publicMemberDto.setImageUrl(memberDto.getImageUrl());
		return publicMemberDto;
	}
}
