package com.future.bbetter.member.resource;

import java.util.List;

import com.future.bbetter.member.model.MemberDTO;

public interface FriendsResource {

	/**
	 * 取的朋友清單
	 * @author Charles
	 * @date 2017年9月11日 下午11:08:40
	 * @param memberId
	 * @return List<MemberDTO>
	 */
	public List<MemberDTO> getFriends(Long memberId);
}
