package com.future.bbetter.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.member.constant.FRIEND;
import com.future.bbetter.member.dto.FriendDto;
import com.future.bbetter.member.resource.FriendResource;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_USER')")
public class FriendController {
	
	@Autowired
	private FriendResource friendsResource;
	
	/**
	 * 取得該會員所有朋友列表
	 * @author Charles
	 * @date 2017年10月2日 下午11:18:10
	 */
	@GetMapping("/member/me/friends")
	public List<FriendDto> getFriends(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<FriendDto> friends = friendsResource.getFriends(new Long(auth.getName()));
		return friends;
	}
	
	/**
	 * 取得該會員所有邀請中朋友列表
	 * @author Charles
	 * @date 2017年10月2日 下午11:18:10
	 */
	@GetMapping("/member/me/friendsInAcceptNotYet")
	public List<FriendDto> getFriendsInAcceptNotYet(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<FriendDto> friendsInAcceptNotYet = friendsResource.getFriendsInAcceptNotYet(new Long(auth.getName()));
		return friendsInAcceptNotYet;
	}
	
	/**
	 * 新增好友
	 * @author Charles
	 * @date 2017年10月2日 下午11:18:10
	 */
	@PostMapping("/member/me/friend/{friendMemberId}")
	public void addFriend(@PathVariable Long friendMemberId) throws InsertOrUpdateDataFailureException{
		Integer source = FRIEND.SOURCE_BBETTER.value;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		friendsResource.addFriend(new Long(auth.getName()), friendMemberId, source);
	}
}
