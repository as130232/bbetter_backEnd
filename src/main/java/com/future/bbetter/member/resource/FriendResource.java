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
	 * @param isAccept(是否接受好友邀請)，主動發出好友邀請新增時為No，若是另一方接受為Yes
	 * @return FriendDTO
	 */
	public FriendDto addFriend(Long memberId, Long friendMemberId, Integer source, Integer isAccept);
	
	/**
	 * 接受好友邀請
	 * @author Charles
	 * @date 2017年11月4日 下午7:25:11
	 * @param friendMemberId 接受好友邀請的會員
	 * @param friendId 發出好友邀情及被邀請對象的PK
	 * @return void
	 */
	public void acceptFriendApply(Long friendMemberId, Long friendId);
	
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
