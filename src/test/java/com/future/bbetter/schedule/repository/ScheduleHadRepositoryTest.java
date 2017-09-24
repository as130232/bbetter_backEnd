package com.future.bbetter.schedule.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.member.constant.MEMBER;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleDTO;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleSubType;
import com.future.bbetter.schedule.model.ScheduleType;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class ScheduleHadRepositoryTest {
	
	@Autowired
	private TestEntityManager entityMgr;
	
	@Autowired
	private ScheduleHadRepository schHadRepo;
	
	private Long john_id = 0L;
	private Long jay_id = 0L;
	private Long schedule1_id = 0L;
	private Long schedule2_id = 0L;
	
	private Member john = null;
	private Member jay = null;
	private Schedule sch1 = null;
	private Schedule sch2 = null;
	
	
	@Before
	public void setup(){
		//add member data
		Member john = new Member();
		john.setName("john");
		john.setEmail("john@gmail.com");
		john.setPassword("password");
		john.setGender(1);
		john.setMoney(0.0);
		john.setAddress("North Korea");
		entityMgr.persistAndFlush(john);
		
		Member jay = new Member();
		jay.setName("jay");
		jay.setEmail("jay@gmail.com");
		jay.setPassword("password");
		jay.setGender(1);
		jay.setMoney(100.0);
		jay.setAddress("South Korea");
		entityMgr.persistAndFlush(jay);
		
//		this.john = john;
//		this.jay = jay;
		
		john_id = john.getMemberId();
		jay_id = jay.getMemberId();
		
		
		//add schedule type data
		ScheduleType type = new ScheduleType();
		type.setName("test_type");
		entityMgr.persistAndFlush(type);
		
		//add schedule sub type data
		ScheduleSubType subType = new ScheduleSubType();
		subType.setName("test_subtype");
		subType.setScheduleType(type);
		entityMgr.persistAndFlush(subType);
		
		//add schedule data
		Instant now = Instant.now();
		Instant afterTwoHrs = LocalDateTime.now().plusHours(2)
				.toInstant(ZoneOffset.UTC);
		Instant afterFourHrs = LocalDateTime.now().plusHours(4)
				.toInstant(ZoneOffset.UTC);
		
		Schedule schedule1= new Schedule();
		schedule1.setScheduleSubType(subType);
		schedule1.setStartTime(Date.from(now));
		schedule1.setEndTime(Date.from(afterTwoHrs));
		schedule1.setName("Test_Schedule");
		schedule1.setStatus(1);
		schedule1.setVisibility(2);
		schedule1.setIsCycle(0);
		schedule1.setIsNeedRemind(0);
		schedule1.setIsTeamSchedule(0);
		schedule1.setIsValid(0);
		entityMgr.persistAndFlush(schedule1);
		
		
		Schedule schedule2= new Schedule();
		schedule2.setScheduleSubType(subType);
		schedule2.setStartTime(Date.from(now));
		schedule2.setEndTime(Date.from(afterFourHrs));
		schedule2.setName("Test_Schedule");
		schedule2.setStatus(1);
		schedule2.setVisibility(2);
		schedule2.setIsCycle(0);
		schedule2.setIsNeedRemind(0);
		schedule2.setIsTeamSchedule(0);
		schedule2.setIsValid(0);
		entityMgr.persistAndFlush(schedule2);
		
//		this.sch1 = schedule1;
//		this.sch2 = schedule2;
		
		schedule1_id = schedule1.getScheduleId();
		schedule2_id = schedule2.getScheduleId();
	}
	
	@Test
	public void whenFindByScheduleId_thenReturnTwoDifferentRecords(){
		//given
		Member john = entityMgr.find(Member.class, john_id);
		Member jay = entityMgr.find(Member.class, jay_id);
		Schedule sch1 = entityMgr.find(Schedule.class, schedule1_id);
		
		ScheduleHad had = new ScheduleHad();
		had.setMember(john);
		had.setSchedule(sch1);
		had.setAuthority(1);
		had.setIsValid(1);
		had.setAccumulatedTime(0);
		entityMgr.persistAndFlush(had);
		
		ScheduleHad had2 = new ScheduleHad();
		had2.setMember(jay);
		had2.setSchedule(sch1);
		had2.setAuthority(1);
		had2.setIsValid(1);
		had2.setAccumulatedTime(0);
		entityMgr.persistAndFlush(had2);
		
		
		//when
		List<ScheduleHad> founds =  schHadRepo.findByScheduleId(schedule1_id);
		
		//then
		assertThat(founds.size()).isEqualTo(2);

		for(ScheduleHad found : founds){
			assertThat(found.getSchedule())
				.isEqualTo(had.getSchedule());
		}
		
		assertThat(founds.get(0).getScheduleHadId())
			.isNotEqualTo(founds.get(1).getScheduleHadId());
	}
	
	
	@Test
	public void whenFindByMemberId_thenReturnTwoDifferentRecords(){
		//given
		Member john = entityMgr.find(Member.class, john_id);
		Schedule sch1 = entityMgr.find(Schedule.class, schedule1_id);
		Schedule sch2 = entityMgr.find(Schedule.class, schedule2_id);
		
		ScheduleHad had = new ScheduleHad();
		had.setMember(john);
		had.setSchedule(sch1);
		had.setAuthority(1);
		had.setIsValid(1);
		had.setAccumulatedTime(0);
		entityMgr.persistAndFlush(had);
		
		ScheduleHad had2 = new ScheduleHad();
		had2.setMember(john);
		had2.setSchedule(sch2);
		had2.setAuthority(1);
		had2.setIsValid(1);
		had2.setAccumulatedTime(0);
		entityMgr.persistAndFlush(had2);
		
		
		//when
		List<ScheduleHad> founds =  schHadRepo.findByMemberId(john_id);
		//then
		assertThat(founds.size()).isEqualTo(2);

		for(ScheduleHad found : founds){
			assertThat(found.getMember())
				.isEqualTo(had.getMember());
		}
		
		assertThat(founds.get(0).getScheduleHadId())
			.isNotEqualTo(founds.get(1).getScheduleHadId());
	}
}
