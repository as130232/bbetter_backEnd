package com.future.bbetter.member.resource;

import java.util.List;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.member.model.MemberDTO;

public interface MemberResource {


	public void addMember(MemberDTO memberDTO);
	public void updateMember(MemberDTO updateMemberDTO);
	public void deleteMember(Long memberId);

	public MemberDTO getMember(Long memberId) throws DataNotFoundException;
	public MemberDTO getMember(String email) throws DataNotFoundException;
	
	public List<MemberDTO> getAllMembers();
	


}
