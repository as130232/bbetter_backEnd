package com.future.bbetter.member.resource.impl;

import java.util.ArrayList;
import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.member.constant.FRIEND;
import com.future.bbetter.member.model.Friend;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.model.MemberDTO;
import com.future.bbetter.member.repository.FriendRepository;
import com.future.bbetter.member.resource.FriendsResource;
import com.future.bbetter.member.resource.MemberResource;

@Service
public class FriendResourceImpl implements FriendsResource{
	
	@Autowired
	private FriendRepository friendRepository;
	@Autowired
	private MemberResource memberResource;
	
	@Override
	public List<MemberDTO> getFriends(Long memberId) {
		List<MemberDTO> result = new ArrayList<>();
//		//取得該會員的所有朋友
//		List<Friend> friends = friendRepository.findByMemberId(memberId);
//		friends.stream().forEach(friend -> {
//			Integer isBlockade = friend.getIsBlockade();
//			Integer isAccept = friend.getIsAccept();
//			//無被封鎖且接收邀請才會出現在好友列表
//			if(FRIEND.IS_BLOCKADE_NO.value.equals(isBlockade) && FRIEND.IS_ACCEPT_YES.value.equals(isAccept)) {
//				MemberDTO memberDTO = new MemberDTO();
//				//取得朋友資訊
//				Member member = friend.getMemberByFriendMemberId();
//				MemberDTO friendInfo = memberResource.getMember(friend.getFriendMemberId());
//				memberDTO.setName(friendInfo.getName());
//				memberDTO.setGender(friendInfo.getGender());
//				memberDTO.setEmail(friendInfo.getEmail());
//				memberDTO.setBirthday(friendInfo.getBirthday());
//				memberDTO.setCreatedate(friendInfo.getCreatedate());
//				result.add(memberDTO);
//			}
//		});
//		
//		List<Friend> newFriends = friends.stream()
//			.filter(f -> FRIEND.IS_BLOCKADE_NO.equals(f.getIsBlockade()))
//			.filter(f -> FRIEND.IS_ACCEPT_YES.equals(f.getIsAccept()))
//			.collect(Collectors.toList());
		
		
		return result;
	}

}
