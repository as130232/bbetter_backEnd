package com.future.bbetter.member.resource;

import java.util.List;

import com.future.bbetter.member.dto.FriendDto;

public interface FriendResource {

	/**
	 * 新增一筆朋友(未接受邀請)
	 * @author Charles
	 * @date 2017年11月4日 下午5:08:49
	 * @param memberId
	 * @param friendMemberId
	 * @param source 來源:一般、Facebook、Google
	 * @return FriendDTO
	 */
	public FriendDto addFriend(Long memberId, Long friendMemberId, Integer source);
	
	/**
	 * 接受好友邀請
	 * @author Charles
	 * @date 2017年11月4日 下午7:25:11
	 * @param memberId
	 * @param friendMemberId
	 * @return void
	 */
	public void acceptFriendApply(Long memberId, Long friendMemberId);
	
	/**
	 * 取得該會員的朋友清單
	 * @author Charles
	 * @date 2017年9月11日 下午11:08:40
	 * @param memberId
	 * @return FriendDTO
	 */
	public List<FriendDto> getFriends(Long memberId);
	
	
	/**
	 * 取得該會原還未同意接受邀請的好友列表
	 * @author Charles
	 * @date 2017年11月4日 下午7:42:34
	 * @param memberId
	 * @return List<FriendDTO>
	 */
	public List<FriendDto> getFriendsInAcceptNotYet(Long memberId);
}
