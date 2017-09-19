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
	
	/**
	 * 檢查該email是否已存在
	 * @author Charles
	 * @date 2017年9月18日 下午10:42:23
	 * @param email
	 * @return Boolean
	 */
	public Boolean checkIsEmailExist(String email);

}
