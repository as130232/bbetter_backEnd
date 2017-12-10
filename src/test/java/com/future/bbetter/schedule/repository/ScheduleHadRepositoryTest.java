package com.future.bbetter.schedule.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.member.model.Member;
import com.future.bbetter.schedule.constant.SCHEDULE_HAD;
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleType;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ScheduleHadRepositoryTest {
	
	@Autowired
	private TestEntityManager entityMgr;
	
	@Autowired
	private ScheduleHadRepository schHadRepo;
	
	Member john ;
	Member jay;
	Schedule schedule1;
	Schedule schedule2;
	ScheduleOwner johnOwner;
	ScheduleOwner jayOwner;
	
	@Before
	public void setup(){
		//add member data
		john = new Member();
		john.setName("john");
		john.setEmail("john@gmail.com");
		john.setPassword("password");
		john.setGender(1);
		john.setAddress("North Korea");
		john.setMoney(new BigDecimal(100));
		entityMgr.persistAndFlush(john);
		
		jay = new Member();
		jay.setName("jay");
		jay.setEmail("jay@gmail.com");
		jay.setPassword("password");
		jay.setGender(1);
		jay.setAddress("South Korea");
		jay.setMoney(new BigDecimal(5000));
		entityMgr.persistAndFlush(jay);
		
		
		//add schedule type data
		ScheduleType type = new ScheduleType();
		type.setName("test_type");
		entityMgr.persistAndFlush(type);
		
		
		//add schedule data
		Instant now = Instant.now();
		Instant afterTwoHrs = LocalDateTime.now().plusHours(2)
				.toInstant(ZoneOffset.UTC);
		Instant afterFourHrs = LocalDateTime.now().plusHours(4)
				.toInstant(ZoneOffset.UTC);
		
		schedule1 = new Schedule();
		schedule1.setScheduleType(type);
		schedule1.setStartTime(Date.from(now));
		schedule1.setEndTime(Date.from(afterTwoHrs));
		schedule1.setName("Test_Schedule");
		schedule1.setStatus(1);
		schedule1.setVisibility(2);
		schedule1.setIsCycle(0);
		schedule1.setIsNeedRemind(0);
		schedule1.setIsTeamSchedule(0);
		schedule1.setIsValid(0);
		schedule1.setCreatedate(new Date());
		entityMgr.persistAndFlush(schedule1);
		
		
		schedule2 = new Schedule();
		schedule2.setScheduleType(type);
		schedule2.setStartTime(Date.from(now));
		schedule2.setEndTime(Date.from(afterFourHrs));
		schedule2.setName("Test_Schedule2");
		schedule2.setStatus(1);
		schedule2.setVisibility(2);
		schedule2.setIsCycle(0);
		schedule2.setIsNeedRemind(0);
		schedule2.setIsTeamSchedule(0);
		schedule2.setIsValid(0);
		schedule2.setCreatedate(new Date());
		entityMgr.persistAndFlush(schedule2);
		
		
		johnOwner = new ScheduleOwner();
		johnOwner.setRegistrantId(john.getMemberId());
		johnOwner.setIsValid(SCHEDULE_OWNER.IS_VALID_YES.value);
		johnOwner.setSource(SCHEDULE_OWNER.SOURCE_MEMBER.value);
		johnOwner.setCreatedate(new Date());
		johnOwner.setUpdatedate(new Date());
		entityMgr.persistAndFlush(johnOwner);
		
		jayOwner = new ScheduleOwner();
		jayOwner.setRegistrantId(jay.getMemberId());
		jayOwner.setIsValid(SCHEDULE_OWNER.IS_VALID_YES.value);
		jayOwner.setSource(SCHEDULE_OWNER.SOURCE_MEMBER.value);
		jayOwner.setCreatedate(new Date());
		jayOwner.setUpdatedate(new Date());
		entityMgr.persistAndFlush(jayOwner);
	}
	
	/***
	 * test findByScheduleId() method
	 * 給予正確的schedule id 資料應回傳2筆行程資料並被不同人擁有
	 */
	@Test
	public void whenFindByScheduleId_thenReturnTwoDifferentRecords(){
		//given
		int had1_authority = SCHEDULE_HAD.AUTHORITY_LEADER.value;
		int had2_authority = SCHEDULE_HAD.AUTHORITY_MEMBER.value;
		int valid = SCHEDULE_HAD.IS_VALID_YES.value;
		ScheduleHad had = new ScheduleHad();
		had.setScheduleOwner(johnOwner);
		had.setSchedule(schedule1);
		had.setAuthority(had1_authority);
		had.setIsValid(valid);
		had.setAccumulatedTime(0);
		had.setCreatedate(new Date());
		entityMgr.persistAndFlush(had);
		
		ScheduleHad had2 = new ScheduleHad();
		had2.setScheduleOwner(jayOwner);
		had2.setSchedule(schedule1);
		had2.setAuthority(had2_authority);
		had2.setIsValid(valid);
		had2.setAccumulatedTime(0);
		had2.setCreatedate(new Date());
		entityMgr.persistAndFlush(had2);
		
		long schedule1Id = schedule1.getScheduleId();
		
		//when
		List<ScheduleHad> founds =  schHadRepo.findByScheduleId(schedule1Id);
		
		//then
		assertThat(founds.size()).isEqualTo(2);

		for(ScheduleHad found : founds){
			assertThat(found.getSchedule())
				.isEqualTo(had.getSchedule());
		}
		
		assertThat(founds.get(0).getScheduleHadId())
			.isNotEqualTo(founds.get(1).getScheduleHadId());
	}
	
	
	/***
	 * test findByMemberId() method
	 * 給予正確的member id 資料應回傳2筆行程資料
	 */
	@Test
	public void whenFindByMemberId_thenReturnTwoDifferentRecords(){
		//given
		int had1_authority = SCHEDULE_HAD.AUTHORITY_LEADER.value;
		int had2_authority = SCHEDULE_HAD.AUTHORITY_MEMBER.value;
		int valid = SCHEDULE_HAD.IS_VALID_YES.value;
		ScheduleHad had = new ScheduleHad();
		had.setScheduleOwner(johnOwner);
		had.setSchedule(schedule1);
		had.setAuthority(had1_authority);
		had.setIsValid(valid);
		had.setAccumulatedTime(0);
		had.setCreatedate(new Date());
		entityMgr.persistAndFlush(had);
		
		ScheduleHad had2 = new ScheduleHad();
		had2.setScheduleOwner(johnOwner);
		had2.setSchedule(schedule2);
		had2.setAuthority(had2_authority);
		had2.setIsValid(valid);
		had2.setAccumulatedTime(0);
		had2.setCreatedate(new Date());
		entityMgr.persistAndFlush(had2);
		
		long johnId = john.getMemberId();
		
		//when
		List<ScheduleHad> founds =  schHadRepo.findByMemberId(johnId);
		
		//then
		assertThat(founds.size()).isEqualTo(2);

		
		assertThat(founds.get(0).getScheduleHadId())
			.isNotEqualTo(founds.get(1).getScheduleHadId());
	}
	
	/***
	 * test findByScheduleOwnerIdAndIsValid() method
	 * 給予正確資料應回傳一筆資料
	 */
	@Test
	public void whenFindByScheduleOwnerIdAndIsValid_thenReturnOneRecord() {
		//given
		int had1_authority = SCHEDULE_HAD.AUTHORITY_LEADER.value;
		int valid = SCHEDULE_HAD.IS_VALID_YES.value;
		ScheduleHad had = new ScheduleHad();
		had.setScheduleOwner(johnOwner);
		had.setSchedule(schedule1);
		had.setAuthority(had1_authority);
		had.setIsValid(valid);
		had.setAccumulatedTime(0);
		had.setCreatedate(new Date());
		entityMgr.persistAndFlush(had);
		
		long johnOwnerId = johnOwner.getScheduleOwnerId();

		//when
		List<ScheduleHad> results = schHadRepo.findByScheduleOwnerIdAndIsValid(johnOwnerId, valid);

		//then
		assertThat(results).isNotNull();
		assertThat(results.size()).isEqualTo(1);
		assertThat(results.get(0).getSchedule().getScheduleId())
			.isEqualTo(schedule1.getScheduleId());
	}
}
