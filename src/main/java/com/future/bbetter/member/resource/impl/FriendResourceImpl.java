package com.future.bbetter.member.resource.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.member.constant.FRIEND;
import com.future.bbetter.member.dto.FriendDto;
import com.future.bbetter.member.model.Friend;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.repository.FriendRepository;
import com.future.bbetter.member.repository.MemberRepository;
import com.future.bbetter.member.resource.FriendResource;

@Service
public class FriendResourceImpl implements FriendResource{
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private FriendRepository friendRepository;
	
	@Override
	public FriendDto addFriend(Long memberId, Long friendMemberId, Integer source) {
		//無被封鎖
		Integer isBlockade = FRIEND.IS_BLOCKADE_NO.value;
		//還未接收好友邀請
		Integer isAccept = FRIEND.IS_ACCEPT_NO.value;
		Friend insert = new Friend();
		insert.setMemberByMemberId(new Member(memberId));
		insert.setMemberByFriendMemberId(new Member(friendMemberId));
		insert.setSource(source);
		insert.setIsAccept(isAccept);
		insert.setIsBlockade(isBlockade);
		insert.setCreatedate(new Date());
		Friend newFriend = friendRepository.saveAndFlush(insert);
		//取得該好友的會員資訊
		Member memberByFriend = memberRepository.findById((friendMemberId)).get();
		FriendDto friendDto = FriendDto.from(memberByFriend, newFriend);
		return friendDto;
	}

	@Override
	public void acceptFriendApply(Long memberId, Long friendMemberId) {
		// TODO Auto-generated method stub
		//同意該好友
		
		//將同意的好友也新增到自己好友列表
		
	}
	
	@Override
	public List<FriendDto> getFriends(Long memberId) {
		List<FriendDto> result = new ArrayList<>();
		//無被封鎖
		Integer isBlockade = FRIEND.IS_BLOCKADE_NO.value;
		//接收好友邀請
		Integer isAccept = FRIEND.IS_ACCEPT_YES.value;
		List<Friend> friends = friendRepository.findByMemberByMemberIdAndIsBlockadeAndIsAccept(new Member(memberId), isBlockade, isAccept);
		friends.stream()
		.forEach(friend -> {
			Member memberByFriend = friend.getMemberByFriendMemberId();
			FriendDto friendDto = FriendDto.from(memberByFriend, friend);
			result.add(friendDto);
		});
		return result;
	}


	@Override
	public List<FriendDto> getFriendsInAcceptNotYet(Long memberId) {
		List<FriendDto> result = new ArrayList<>();
		Integer isBlockade = FRIEND.IS_BLOCKADE_NO.value;
		Integer isAccept = FRIEND.IS_ACCEPT_NO.value;
		List<Friend> friendsInAcceptNotYet = friendRepository.findByMemberByFriendMemberIdAndIsBlockadeAndIsAccept(new Member(memberId), isBlockade, isAccept);
		friendsInAcceptNotYet.stream()
		.forEach(friend -> {
			Member memberByFriend = friend.getMemberByFriendMemberId();
			FriendDto friendDto = FriendDto.from(memberByFriend, friend);
			result.add(friendDto);
		});
		return result;
	}

	
	
}
