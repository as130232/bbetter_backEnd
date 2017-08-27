package com.future.bbetter.member.resource.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.model.MemberDTO;
import com.future.bbetter.member.repository.MemberRepository;
import com.future.bbetter.member.resource.MemberResource;

@Service
public class MemberResourceImpl implements MemberResource{
	
	@Autowired
	private MemberRepository memberRepository;
	
	
	@Override
	public MemberDTO getMember(Long memberId){
		MemberDTO memberDTO = new MemberDTO();
		Optional<Member> optional = memberRepository.findById(memberId);
		if(optional.isPresent()) {
			Member member = optional.get();
			BeanUtils.copyProperties(member, memberDTO);
		}
		return memberDTO;
	}
	
	@Override
	public List<MemberDTO> getAllMembers(){
		List<MemberDTO> memberDTOs = new ArrayList<>();
		List<Member> members = memberRepository.findAll();
		for(Member member:members) {
			MemberDTO memberDTO = new MemberDTO();
			BeanUtils.copyProperties(member, memberDTO);
		}
		return memberDTOs;
	}
	
	@Override
	public void addMember(MemberDTO memberDTO){
		Member member = new Member();
		BeanUtils.copyProperties(memberDTO, member);
		Date createdate = new Date();
		Double money = 0.00D;
		member.setMoney(money);
		member.setCreatedate(createdate);
		memberRepository.save(member);
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
	
}
