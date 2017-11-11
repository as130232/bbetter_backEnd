package com.future.bbetter.member.resource.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.member.constant.FRIEND;
import com.future.bbetter.member.dto.FriendDto;
import com.future.bbetter.member.model.Friend;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.resource.FriendResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class FriendResourceImplTest {
	
	@TestConfiguration
	static class FriendResourceImplTestContextConfiguration{
		@Bean("testFriendRs")
		public FriendResource friendResource(){
			return new FriendResourceImpl();
		}
	}
	
	@Autowired
	private TestEntityManager entityMgr;

	@Autowired
	@Qualifier("testFriendRs")
	private FriendResource friendRs;
	
	Member peter;
	Member john;
	
	@Before
	public void setup(){
		//先產生兩位會員
		peter = new Member();
		peter.setName("peter");
		peter.setPassword("1234");
		peter.setAddress("Taiwan");
		peter.setBirthday(new Date());
		peter.setEmail("peter@bbetter.com");
		peter.setGender(1);
		peter.setMoney(new BigDecimal(0));
		entityMgr.persist(peter);

		john = new Member();
		john.setName("john");
		john.setPassword("1234");
		john.setAddress("Taiwan");
		john.setBirthday(new Date());
		john.setEmail("john@bbetter.com");
		john.setGender(1);
		john.setMoney(new BigDecimal(0));
		entityMgr.persist(john);
		
	}
	
	/**
	 * 測試新增好友(發出好友邀請，但對方未同意>IS_ACCEPT_NO)
	 * peter 向 john發出好友邀請
	 * @author Charles
	 * @date 2017年11月5日 下午6:37:46
	 */
	@Test
	public void whenAddFriend_thenCanFoundOneRecord() {
		// given
		Integer source = FRIEND.SOURCE_BBETTER.value;
		//還未接收好友邀請
		Integer isAccept = FRIEND.IS_ACCEPT_NO.value;
		
		// when
		FriendDto result = friendRs.addFriend(peter.getMemberId(), john.getMemberId(), source, isAccept);
		
		// then
		Friend found = entityMgr.find(Friend.class, result.getFriendId());
		assertThat(found.getMemberByFriendMemberId().getMemberId()).isEqualTo(john.getMemberId());
		assertThat(found.getSource()).isEqualTo(source);
		//無被封鎖
		Integer isBlockade = FRIEND.IS_BLOCKADE_NO.value;
		assertThat(found.getIsBlockade()).isEqualTo(isBlockade);
		assertThat(found.getIsAccept()).isEqualTo(isAccept);
	}
	

	/**
	 * 測試取得該會員還未接受成為好友的會員列表
	 * john取得還未接受邀請好友的會員列表(peter)
	 * @author Charles
	 * @date 2017年11月5日 下午6:37:46
	 */
	@Test
	public void whenGetFriendsInAcceptNotYet_thenCanFoundOneRecord() {
		// given
		// peter 向 john發出好友邀請
		friendRs.addFriend(peter.getMemberId(), john.getMemberId(), 
				FRIEND.SOURCE_BBETTER.value, FRIEND.IS_ACCEPT_NO.value);
		
		// when
		List<FriendDto> friendDtosInAcceptNotYet = friendRs.getFriendsInAcceptNotYet(john.getMemberId());
		FriendDto result = friendDtosInAcceptNotYet.get(0);
		
		// then
		Friend found = entityMgr.find(Friend.class, result.getFriendId());
		Long friendMemberId = found.getMemberByMemberId().getMemberId();
		assertThat(friendMemberId).isEqualTo(peter.getMemberId());
		assertThat(found.getIsAccept()).isEqualTo(FRIEND.IS_ACCEPT_NO.value);
	}
	
	/**
	 * 測試接收好友邀請
	 * john接收peter 發出的好友邀請，雙方成為好友
	 * @author Charles
	 * @date 2017年11月5日 下午6:38:31
	 */
	@Test
	public void whenAcceptFriendApply_thenBothBecomeFriends() {
		// given
		// peter 向 john發出好友邀請
		friendRs.addFriend(peter.getMemberId(), john.getMemberId(), 
				FRIEND.SOURCE_BBETTER.value, FRIEND.IS_ACCEPT_NO.value);
		
		// john 取得還未接受成為好友的會員列表(peter)
		List<FriendDto> friendDtosInAcceptNotYet = friendRs.getFriendsInAcceptNotYet(john.getMemberId());
		FriendDto friendDtoInAcceptNotYet = friendDtosInAcceptNotYet.get(0);
		
		// john 同意peter的好友邀請
		// when
		friendRs.acceptFriendApply(john.getMemberId(), friendDtoInAcceptNotYet.getFriendId());
		
		// then
		Friend found = entityMgr.find(Friend.class, friendDtoInAcceptNotYet.getFriendId());
		// peter好友列表中john邀請狀態為已接受狀態
		assertThat(found.getIsAccept()).isEqualTo(FRIEND.IS_ACCEPT_YES.value);
		
		// john好友列表中新增了peter且邀請狀態也是已接受狀態
	}
}
	