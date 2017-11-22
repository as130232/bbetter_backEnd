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
import com.future.bbetter.member.dto.MemberDto;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.repository.MemberRepository;
import com.future.bbetter.member.resource.MemberResource;

@Service
public class MemberResourceImpl implements MemberResource{
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public MemberDto addMember(MemberDto memberDto){
		Date createdate = new Date();
		BigDecimal money = new BigDecimal(0.0);
		String password = memberDto.getPassword();
		//密碼加密
		String encryptPassword = Password.encrypt(password);
		
		Member member = new Member();
		member.setAddress(memberDto.getAddress());
		member.setBirthday(memberDto.getBirthday());
		member.setEmail(memberDto.getEmail());
		member.setGender(memberDto.getGender());
		member.setImageUrl(memberDto.getImageUrl());
		member.setName(memberDto.getName());
		member.setMoney(money);
		member.setPassword(encryptPassword);
		member.setCreatedate(createdate);
		Member newMember = memberRepository.saveAndFlush(member);
		MemberDto newMemberDTO = MemberDto.from(newMember);
		return newMemberDTO;
	}
	
	@Override
	public void updateMember(MemberDto updateMemberDto){
		Long memberId = updateMemberDto.getMemberId();
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
	public MemberDto getMember(Long memberId) throws DataNotFoundException{
		MemberDto memberDto = new MemberDto();
		Optional<Member> optional = memberRepository.findById(memberId);
		if(optional.isPresent()) {
			Member member = optional.get();
			memberDto = MemberDto.from(member);
		}else {
			throw new DataNotFoundException("member id: " + memberId.toString() + " is not found.");
		}
		return memberDto;
	}
	
	@Override
	public MemberDto getMember(String email) throws DataNotFoundException {
		MemberDto memberDTO = new MemberDto();
		Member member = memberRepository.findByEmail(email);
		if(member != null) {
			memberDTO = MemberDto.from(member);
		}else {
			throw new DataNotFoundException("email: " + email + " is not found.");
		}
		return memberDTO;
	}
	
	@Override
	public List<MemberDto> getAllMembers(){
		List<MemberDto> memberDtos = new ArrayList<>();
		List<Member> members = memberRepository.findAll();
		MemberDto memberDto = null;
		for(Member member:members) {
			memberDto = new MemberDto();
			BeanUtils.copyProperties(member, memberDto);
			memberDtos.add(memberDto);
		}
		return memberDtos;
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
