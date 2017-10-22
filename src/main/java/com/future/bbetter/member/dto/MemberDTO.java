package com.future.bbetter.member.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.future.bbetter.member.model.Member;
import com.future.bbetter.schedule.model.ScheduleRegistrant;

import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor class MemberDTO implements ScheduleRegistrant{
	//member.attributes
	private Long memberId;
	private String email;
	private String name;
	private Integer gender;
	private BigDecimal money;
	private Date birthday;
	private String address;
	private Date createdate;
	//新增會員時輸入的密碼(明碼)
	private String password;
	private String imageUrl;
		
	public static MemberDTO fromEntity(Member member) {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(member.getMemberId());
		memberDTO.setName(member.getName());
		memberDTO.setEmail(member.getEmail());
		memberDTO.setBirthday(member.getBirthday());
		memberDTO.setGender(member.getGender());
		memberDTO.setAddress(member.getAddress());
		memberDTO.setMoney(member.getMoney());
		memberDTO.setImageUrl(member.getImageUrl());
		return memberDTO;
	}
	
}
