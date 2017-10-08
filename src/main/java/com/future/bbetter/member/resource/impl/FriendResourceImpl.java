package com.future.bbetter.member.resource.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.member.constant.FRIEND;
import com.future.bbetter.member.model.Friend;
import com.future.bbetter.member.model.FriendDTO;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.model.MemberDTO;
import com.future.bbetter.member.repository.FriendRepository;
import com.future.bbetter.member.repository.MemberRepository;
import com.future.bbetter.member.resource.FriendResource;
import com.future.bbetter.member.resource.MemberResource;

@Service
public class FriendResourceImpl implements FriendResource{
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private FriendRepository friendRepository;
	@Autowired
	private MemberResource memberResource;
	
	@Override
	public List<FriendDTO> getFriends(Long memberId) {
		List<FriendDTO> result = new ArrayList<>();
		MemberDTO memberDTO = memberResource.getMember(memberId);
		Member query = new Member();
		query.setMemberId(memberId);
		List<Friend> friends = friendRepository.findByMemberByMemberId(query);
		friends.stream().forEach(friend -> {
			Integer isBlockade = friend.getIsBlockade();
			Integer isAccept = friend.getIsAccept();
			//無被封鎖且接收邀請才會出現在好友列表
			if(FRIEND.IS_BLOCKADE_NO.value.equals(isBlockade) && FRIEND.IS_ACCEPT_YES.value.equals(isAccept)) {
				//取得朋友資訊
				Member member = friend.getMemberByFriendMemberId();
				FriendDTO friendDTO = FriendDTO.fromEntity(member, friend);
				result.add(friendDTO);
			}
		});
		return result;
	}
	
//	@Override
//	public FriendDTO getFriends(Long memberId) {
//		FriendDTO result = new FriendDTO();
//		List<MemberDTO> friendsDTO = new ArrayList<>();
//		MemberDTO memberDTO = memberResource.getMember(memberId);
//		//取得該會員的所有朋友
//		Member test = new Member();
//		test.setMemberId(memberId);
//		List<Friend> friends = friendRepository.findByMemberByMemberId(test);
//		//List<Friend> friends = friendRepository.findByMemberByMemberId(memberId);
//		friends.stream().forEach(friend -> {
//			Integer isBlockade = friend.getIsBlockade();
//			Integer isAccept = friend.getIsAccept();
//			//無被封鎖且接收邀請才會出現在好友列表
//			if(FRIEND.IS_BLOCKADE_NO.value.equals(isBlockade) && FRIEND.IS_ACCEPT_YES.value.equals(isAccept)) {
//				//取得朋友資訊
//				Member member = friend.getMemberByFriendMemberId();
//				MemberDTO friendDTO = MemberDTO.fromEntity(member) ;
//				friendsDTO.add(friendDTO);
//			}
//		});
//		result.setMemberDTO(memberDTO);
//		result.setFriendsDTO(friendsDTO);
////		List<Friend> filterFriends = friends.stream()
////			.filter(f -> FRIEND.IS_BLOCKADE_NO.equals(f.getIsBlockade()))
////			.filter(f -> FRIEND.IS_ACCEPT_YES.equals(f.getIsAccept()))
////			.collect(Collectors.toList());
//		return result;
//	}

	
}
