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
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.model.CycleRule;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleType;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class CycleRuleRepositoryTest {
	
	@Autowired
	private TestEntityManager entityMgr;
	
	@Autowired
	private CycleRuleRepository cycleRepo;
	
	ScheduleHad had;

	@Before
	public void setup() throws Exception {
		Member john = new Member();
		john.setName("john");
		john.setEmail("john@gmail.com");
		john.setPassword("password");
		john.setGender(1);
		john.setAddress("North Korea");
		john.setMoney(new BigDecimal(100));
		entityMgr.persistAndFlush(john);
		
		ScheduleOwner owner = new ScheduleOwner();
		owner.setRegistrantId(john.getMemberId());
		owner.setSource(SCHEDULE_OWNER.SOURCE_MEMBER.value);
		owner.setIsValid(1);
		owner.setCreatedate(new Date());
		owner.setUpdatedate(new Date());
		entityMgr.persist(owner);
		
		ScheduleType type = new ScheduleType();
		type.setName("test_type");
		entityMgr.persistAndFlush(type);
		
		Instant now = Instant.now();
		Instant afterTwoHrs = LocalDateTime.now().plusHours(2)
				.toInstant(ZoneOffset.UTC);
		
		Schedule schedule1 = new Schedule();
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
		
		had = new ScheduleHad();
		had.setScheduleOwner(owner);
		had.setSchedule(schedule1);
		had.setAuthority(1);
		had.setIsValid(1);
		had.setAccumulatedTime(0);
		had.setCreatedate(new Date());
		entityMgr.persistAndFlush(had);
		
	}

	@Test
	public void whenGetCycleRulesByScheduleHadId_thenSuccess() {
		//given
		assertThat(had).isNotNull();
		
		CycleRule cycle = new CycleRule();
		cycle.setScheduleHad(had);
		cycle.setPeriod(10);
		cycle.setIsValid(1);
		cycle.setTimePoint(0307);
		cycle.setCreatedate(new Date());
		cycle.setUpdatedate(new Date());
		entityMgr.persist(cycle);
		
		Long hadId = had.getScheduleHadId();
		
		//when
		List<CycleRule> results = cycleRepo.getCycleRulesByScheduleHadId(hadId);
		
		//then
		assertThat(results).isNotNull();
		assertThat(results.size()).isEqualTo(1);
		assertThat(results.get(0)
				.getScheduleHad()
				.getScheduleHadId())
			.isEqualTo(hadId);
		
	}

}
