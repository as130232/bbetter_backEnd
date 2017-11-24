package com.future.bbetter.member.resource;

import java.util.List;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.member.dto.MemberDto;

public interface MemberResource {
	
	/**
	 * 新增一筆會員，並回傳新增會員
	 * @author Charles
	 * @date 2017年10月21日 下午5:59:23
	 * @return MemberDTO
	 */
	public MemberDto addMember(MemberDto memberDto);
	
	public void updateMember(MemberDto updateMemberDto);
	public void deleteMember(Long memberId);

	/**
	 * 取得該會員資訊
	 * @author Charles
	 * @date 2017年10月21日 下午5:58:26
	 * @return MemberDTO
	 */
	public MemberDto getMember(Long memberId) throws DataNotFoundException;
	public MemberDto getMember(String email) throws DataNotFoundException;
	
	/**
	 * 取得所有會員
	 * @author Charles
	 * @date 2017年10月21日 下午5:58:53
	 * @return List<MemberDTO>
	 */
	public List<MemberDto> getAllMembers();
	
	/**
	 * 檢查該email是否已存在
	 * @author Charles
	 * @date 2017年9月18日 下午10:42:23
	 * @param email
	 * @return Boolean
	 */
	public Boolean checkIsEmailExist(String email);
	
	

}
