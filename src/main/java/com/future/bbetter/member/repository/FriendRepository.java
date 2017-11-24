package com.future.bbetter.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.future.bbetter.member.model.Friend;
import com.future.bbetter.member.model.Member;

public interface FriendRepository  extends JpaRepository<Friend, Long> , JpaSpecificationExecutor<Friend>{
	public Friend findByMemberByMemberIdAndMemberByFriendMemberId(Member member, Member memberByFriend);
	public List<Friend> findByMemberByMemberId(Member member);
	public List<Friend> findByMemberByMemberIdAndIsBlockadeAndIsAccept(Member member, Integer isBlockade,
			Integer isAccept);
	public List<Friend> findByMemberByFriendMemberIdAndIsBlockadeAndIsAccept(Member member, Integer isBlockade,
			Integer isAccept);
}
