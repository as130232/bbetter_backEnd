package com.future.bbetter.schedule.resource.impl;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.member.dto.MemberDto;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.resource.MemberResource;
import com.future.bbetter.member.resource.impl.MemberResourceImpl;
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.dto.ScheduleOwnerDTO;
import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleRegistrant;
import com.future.bbetter.schedule.resource.ScheduleOwnerResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ScheduleOwnerResourceImplTest {
	
	
	@TestConfiguration
	static class ScheduleOwnerResourceImplTestContextConfiguration{
		@Bean
		public ScheduleOwnerResource scheduleOwnerResource(){
			return new ScheduleOwnerResourceImpl();
		}
		@Bean
		public MemberResource memberResource(){
			return new MemberResourceImpl();
		}
	}
	
	
	@Autowired
	private TestEntityManager entityMgr;
	
	@Autowired
	private ScheduleOwnerResource schOwnerRs;
	
	Long memId = 0L;
	Long ownerId = 0L;
	
	@Before
	public void setup(){
		Member john = new Member();
		john.setName("john");
		john.setPassword("1234");
		john.setAddress("London");
		john.setBirthday(new Date());
		john.setEmail("john@bbetter.com");
		john.setGender(1);
		john.setMoney(new BigDecimal(500));
		memId = (Long) entityMgr.persistAndGetId(john);
		
		ScheduleOwner owner = new ScheduleOwner();
		owner.setRegistrantId(memId);
		owner.setSource(SCHEDULE_OWNER.SOURCE_MEMBER.value);
		owner.setIsValid(1);
		ownerId = (Long) entityMgr.persistAndGetId(owner);
	}
	
	/***
	 * 測試addScheduleOwner(),輸入尚未註冊的資料,應會新增一筆資料至資料庫
	 */
	@Test
	public void whenAddScheduleOwnerAndNotRegister_thenInsertData2Db(){
		//given 
		Long registrantId = 1L;
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;
		
		//when 
		ScheduleOwnerDTO result = schOwnerRs.addScheduleOwner(registrantId, source);
		
		//then
		assertThat(result).isNotNull();
		log.info("owner:{}",result);
	}
	
	/***
	 * 測試addScheduleOwner(),輸入已註冊的資料,應會拋出Exception
	 * InsertOrUpdateDataFailureException("該行程擁有者已被註冊！");
	 */
	@Test
	public void whenAddScheduleOwnerAndRegistered_thenThrowsException(){
		//given 
		Long registrantId = memId;
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;

		//when 
		Throwable thrown = catchThrowable(() -> {
			schOwnerRs.addScheduleOwner(registrantId, source);
		});
		
		//then
		assertThat(thrown).isInstanceOf(InsertOrUpdateDataFailureException.class)
			.hasMessageContaining("該行程擁有者已被註冊！");
	}

	/***
	 * 測試getScheduleOwner()
	 */
	@Test
	public void whenGetScheduleOwner_thenReturnOneRecord() {
		//given
		Long registrantId = memId;
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;

		//when
		ScheduleOwnerDTO result = schOwnerRs.getScheduleOwner(registrantId,source);
		
		//then
		assertThat(result).isNotNull();
		assertThat(result.getScheduleRegistrant()).isNotNull();
		MemberDto memDto = (MemberDto) result.getScheduleRegistrant();
		assertThat(memDto.getMemberId()).isEqualTo(memId);
	}
	
	/***
	 * 測試getScheduleRegistrant()
	 * 取得一筆資料庫的資料且是會員來源時,應可以找到一筆資料
	 */
	@Test
	public void whenGetScheduleRegistrantIsMemberSource_thenReturnOneRecord() {
		//given
		//從測試資料取得
		Long registrantId = 1L;
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;
		
		//when
		ScheduleRegistrant result = schOwnerRs.getScheduleRegistrant(registrantId, source);
		
		//then
		assertThat(result).isInstanceOf(MemberDto.class);
		assertThat( ((MemberDto) result).getMemberId()).isEqualTo(registrantId);
	
	}
	
	/***
	 * 測試getScheduleRegistrant()
	 * 取得一筆資料庫的資料且是其它來源時,應會傳回null
	 */
	@Test
	public void whenGetScheduleRegistrantNotMemberSource_thenReturnNull() {
		//given
		//從測試資料取得
		Long registrantId = 1L;
		Integer source = SCHEDULE_OWNER.SOURCE_OTHERS.value;

		//when
		ScheduleRegistrant result = schOwnerRs.getScheduleRegistrant(registrantId, source);
		
		//then
		assertThat(result).isNull();
	}
}
