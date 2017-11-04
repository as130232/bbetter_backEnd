package com.future.bbetter.member.resource.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.authentication.password.Password;
import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.exception.customize.ThirdVerificationException;
import com.future.bbetter.member.dto.MemberDTO;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.repository.MemberRepository;
import com.future.bbetter.member.resource.FriendResource;
import com.future.bbetter.member.resource.MemberResource;

@Service
public class MemberResourceImpl implements MemberResource{
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public MemberDTO addMember(MemberDTO memberDTO){
		Date createdate = new Date();
		BigDecimal money = new BigDecimal(0.0);
		String password = memberDTO.getPassword();
		//密碼加密
		String encryptPassword = Password.encrypt(password);
		
		Member member = new Member();
		member.setAddress(memberDTO.getAddress());
		member.setBirthday(memberDTO.getBirthday());
		member.setEmail(memberDTO.getEmail());
		member.setGender(memberDTO.getGender());
		member.setImageUrl(memberDTO.getImageUrl());
		member.setName(memberDTO.getName());
		member.setMoney(money);
		member.setPassword(encryptPassword);
		member.setCreatedate(createdate);
		Member newMember = memberRepository.saveAndFlush(member);
		MemberDTO newMemberDTO = MemberDTO.fromEntity(newMember);
		return newMemberDTO;
	}
	
	@Override
	public void updateMember(MemberDTO updateMemberDTO){
		Long memberId = updateMemberDTO.getMemberId();
		Optional<Member> optional = memberRepository.findById(memberId);
		if(optional.isPresent()) {
			Member member = optional.get();
			memberRepository.save(member);
		}
	}
	
	@Override
	public void deleteMember(Long memberId){
		memberRepository.deleteById(memberId);
	}
	
	@Override
	public MemberDTO getMember(Long memberId) throws DataNotFoundException{
		MemberDTO memberDTO = new MemberDTO();
		Optional<Member> optional = memberRepository.findById(memberId);
		if(optional.isPresent()) {
			Member member = optional.get();
			memberDTO = MemberDTO.fromEntity(member);
		}else {
			throw new DataNotFoundException("member id: " + memberId.toString() + " is not found.");
		}
		return memberDTO;
	}
	
	@Override
	public MemberDTO getMember(String email) throws DataNotFoundException {
		MemberDTO memberDTO = new MemberDTO();
		Member member = memberRepository.findByEmail(email);
		if(member != null) {
			memberDTO = MemberDTO.fromEntity(member);
		}else {
			throw new DataNotFoundException("email: " + email + " is not found.");
		}
		return memberDTO;
	}
	
	@Override
	public List<MemberDTO> getAllMembers(){
		List<MemberDTO> memberDTOs = new ArrayList<>();
		List<Member> members = memberRepository.findAll();
		MemberDTO memberDTO = null;
		for(Member member:members) {
			memberDTO = new MemberDTO();
			BeanUtils.copyProperties(member, memberDTO);
			memberDTOs.add(memberDTO);
		}
		return memberDTOs;
	}

	@Override
	public Boolean checkIsEmailExist(String email) {
		Boolean isEmailExist = false;
		Member member = memberRepository.findByEmail(email);
		if(member != null) {
			isEmailExist = true;
		}
		return isEmailExist;
	}
	

	
}
