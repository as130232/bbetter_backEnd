package com.future.bbetter.member.resource;

import java.util.List;

import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.model.MemberDTO;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface MemberResource {


	public void addMember(MemberDTO memberDTO);
	public void updateMember(MemberDTO updateMemberDTO);
	public void deleteMember(Long memberId);

	public MemberDTO getMember(Long memberId);
	public List<MemberDTO> getAllMembers();



}
