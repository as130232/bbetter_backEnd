package com.future.bbetter.member.resource.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.member.constant.FRIEND;
import com.future.bbetter.member.dto.FriendDto;
import com.future.bbetter.member.model.Friend;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.repository.FriendRepository;
import com.future.bbetter.member.repository.MemberRepository;
import com.future.bbetter.member.resource.FriendResource;

@Service
@Primary
public class FriendResourceImpl implements FriendResource{
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private FriendRepository friendRepository;
	
	@Override
	public FriendDto addFriend(Long memberId, Long friendMemberId, Integer source, Integer isAccept) {
		Friend friend = friendRepository.findByMemberByMemberIdAndMemberByFriendMemberId(new Member(memberId), new Member(friendMemberId));
		if(friend != null && FRIEND.IS_ACCEPT_NO.value.equals(friend.getIsAccept())) {
			throw new InsertOrUpdateDataFailureException("已提出好友申請");
		}
		if(friend != null && FRIEND.IS_ACCEPT_YES.value.equals(friend.getIsAccept())) {
			throw new InsertOrUpdateDataFailureException("該會員已是好友");
		}
		
		//無被封鎖
		Integer isBlockade = FRIEND.IS_BLOCKADE_NO.value;
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

	@Transactional
	@Override
	public void acceptFriendApply(Long friendMemberId, Long friendId) {
		Optional<Friend> optional = friendRepository.findById(friendId);
		if(!optional.isPresent()) {
			throw new DataNotFoundException("friendId: " + friendId.toString() + " is not found.");
		}
		Friend friend = optional.get();
		//檢查接受好友邀請的會員(發出此url的人)是不是該發出邀請的朋友本人
		if(!friendMemberId.equals(friend.getMemberByFriendMemberId().getMemberId())) {
			throw new BadCredentialsException("This request is not allowed.");
		}
		//同意該好友
		Integer isAccept = FRIEND.IS_ACCEPT_YES.value;
		friend.setIsAccept(isAccept);
		//將同意的好友也新增到該會員的好友列表，即將該會員Id與好友Id互換，且isAccept直接為接受
		this.addFriend(friend.getMemberByFriendMemberId().getMemberId(), 
				friend.getMemberByMemberId().getMemberId(), friend.getSource(), isAccept);
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
			Member memberByFriend = friend.getMemberByMemberId();
			FriendDto friendDto = FriendDto.from(memberByFriend, friend);
			result.add(friendDto);
		});
		return result;
	}

	
	
}
