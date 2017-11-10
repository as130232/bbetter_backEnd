package com.future.bbetter.member.resource.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

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

	Long memberId = 0L;
	Long memberIdByFriend = 0L;
	
	@Before
	public void setup(){
		//先產生兩位會員
		Member peter = new Member();
		peter.setName("peter");
		peter.setPassword("1234");
		peter.setAddress("Taiwan");
		peter.setBirthday(new Date());
		peter.setEmail("peter@bbetter.com");
		peter.setGender(1);
		peter.setMoney(new BigDecimal(0));
		memberId = (Long) entityMgr.persistAndGetId(peter);

		Member john = new Member();
		john.setName("john");
		john.setPassword("1234");
		john.setAddress("Taiwan");
		john.setBirthday(new Date());
		john.setEmail("john@bbetter.com");
		john.setGender(1);
		john.setMoney(new BigDecimal(0));
		memberIdByFriend = (Long) entityMgr.persistAndGetId(john);
		
	}
	
	@Test
	public void whenAddFriend_thenCanFoundOneRecord() {
		// given
		Integer source = FRIEND.SOURCE_BBETTER.value;
		//還未接收好友邀請
		Integer isAccept = FRIEND.IS_ACCEPT_NO.value;
		// when
		FriendDto result = friendRs.addFriend(memberId, memberIdByFriend, source, isAccept);
		
		// then
		Friend found = entityMgr.find(Friend.class, result.getFriendId());
		assertThat(found.getMemberByFriendMemberId().getMemberId()).isEqualTo(memberIdByFriend);
		assertThat(found.getSource()).isEqualTo(source);
		//無被封鎖
		Integer isBlockade = FRIEND.IS_BLOCKADE_NO.value;
		assertThat(found.getIsBlockade()).isEqualTo(isBlockade);
		
		assertThat(found.getIsAccept()).isEqualTo(isAccept);
		
	}
	
}
	