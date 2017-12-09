package com.future.bbetter.schedule.resource.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.dto.CycleRuleDto;
import com.future.bbetter.schedule.dto.ScheduleDto;
import com.future.bbetter.schedule.dto.ScheduleHadDto;
import com.future.bbetter.schedule.model.CycleRule;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.resource.CycleRuleResource;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(CycleRuleResourceImpl.class)
public class CycleRuleResourceImplTest {
	
	@Autowired
	private TestEntityManager entityMgr;
	
	@Autowired
	private CycleRuleResource cycleRs;
	
	ScheduleHad had;
	CycleRule cycleRule;
	int isValid = 1;
	int period = 2;
	int timePoint = 10;
	
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
		

		cycleRule = new CycleRule();
		cycleRule.setScheduleHad(had);
		cycleRule.setIsValid(isValid);
		cycleRule.setPeriod(period);
		cycleRule.setTimePoint(timePoint);
		cycleRule.setCreatedate(new Date());
		cycleRule.setUpdatedate(new Date());
		entityMgr.persistAndFlush(cycleRule);
	}

	
	/***
	 * test addCycleRule() method
	 * 給予正確資料應可成功塞入資料庫
	 */
	@Test
	public void whenAddCycleRule_thenSuccess() {
		//given
		CycleRuleDto data = new CycleRuleDto();
		data.setIsValid(isValid);
		data.setPeriod(period);
		data.setTimePoint(timePoint);
		data.setScheduleHadDto(ScheduleHadDto.from(had, ScheduleDto.from(had.getSchedule())));
		
		//when
		CycleRuleDto result = cycleRs.addCycleRule(data);
		
		//then
		assertThat(result).isNotNull();
		assertThat(result.getCycleRuleId()).isNotNull();
		assertThat(result.getPeriod()).isEqualTo(period);
		assertThat(result.getTimePoint()).isEqualTo(timePoint);
	}
	
	
	/***
	 * test addCycleRule() method
	 * 給予資料中必要的資料為null,則應丟出例外
	 */
	@Test
	public void whenAddCycleRuleAndNoScheduleHadData_thenThrowsException() {
		//given
		CycleRuleDto data = new CycleRuleDto();
		data.setIsValid(isValid);
		data.setPeriod(period);
		data.setTimePoint(timePoint);
		data.setScheduleHadDto(null);
		
		//when
		Throwable thrown = catchThrowable(()->{
			cycleRs.addCycleRule(data);
		});
		
		//then
		assertThat(thrown).isInstanceOf(InsertOrUpdateDataFailureException.class)
			.hasMessageContaining("It can't found ScheduleHad data in CycleRuleDto: ");
	}

	
	/***
	 * test updateCycleRule() method
	 * 給予正確資料應可成功塞入資料庫
	 */
	@Test
	public void whenUpdateCycleRule_thenSuccess() {
		//given
		long oldId = cycleRule.getCycleRuleId();
		CycleRuleDto newData = CycleRuleDto.from(cycleRule);
		int newPeriod = 20;
		newData.setPeriod(newPeriod);

		
		//when
		cycleRs.updateCycleRule(newData);
		
		//then
		CycleRule found = entityMgr.find(CycleRule.class, oldId);
		assertThat(found).isNotNull();
		assertThat(found.getCycleRuleId()).isEqualTo(oldId);
		assertThat(found.getScheduleHad().getScheduleHadId())
			.isEqualTo(had.getScheduleHadId());
		assertThat(found.getPeriod())
			.isEqualTo(newPeriod);
	}
	
	
	/***
	 * test updateCycleRule() method
	 * 給予資料中必要的資料為null,則應丟出例外
	 */
	@Test
	public void whenUpdateCycleRuleAndNoScheduleHadData_thenThrowsException() {
		//given
		CycleRuleDto newData = CycleRuleDto.from(cycleRule);
		newData.setScheduleHadDto(null);

		
		//when
		Throwable thrown = catchThrowable(()->{
			cycleRs.updateCycleRule(newData);
		});
		
		//then
		assertThat(thrown).isInstanceOf(InsertOrUpdateDataFailureException.class)
			.hasMessageContaining("It can't found ScheduleHad data in CycleRuleDto: ");
	}

	
	/***
	 * test deleteCycleRule() method
	 * 給予正確資料應可成功塞入資料庫
	 */
	@Test
	public void whenDeleteCycleRule_thenSuccess() {
		//given
		long cycleRuleId = cycleRule.getCycleRuleId();
		
		//when
		cycleRs.deleteCycleRule(cycleRuleId);
		
		//then
		CycleRule result = entityMgr.find(CycleRule.class, cycleRuleId);
		assertThat(result).isNull();
	}

	
	/***
	 * test getCycleRule() method
	 * 給予正確id應回傳一筆資料
	 */
	@Test
	public void whenGetCycleRule_thenReturnOneRecord() {
		//given
		long cycleRuleId = cycleRule.getCycleRuleId();
		
		//when
		CycleRuleDto result = cycleRs.getCycleRule(cycleRuleId);
		
		//then
		assertThat(result).isNotNull();
		assertThat(result.getCycleRuleId()).isEqualTo(cycleRuleId);
	
	}
	
	
	/***
	 * test getCycleRule() method
	 * 給予資料中必要的資料為null,則應丟出例外
	 */
	@Test
	public void whenGetCycleRuleAndNotFound_thenThrowsException() {
		//given
		long cycleRuleId = -99L;
		assertThat(entityMgr.find(CycleRule.class, cycleRuleId)).isNull();
		
		//when
		Throwable thrown = catchThrowable(()->{
			cycleRs.getCycleRule(-99L);
		});
		
		//then
		assertThat(thrown).isInstanceOf(DataNotFoundException.class)
			.hasMessageContaining("There is no CycleRule data in database, id:");
	
	}

	/***
	 * test getCycleRulesByScheduleHadId() method
	 * 給予正確id資料應回傳含有一筆資料的list
	 */
	@Test
	public void whenGetCycleRulesByScheduleHadId_thenReturnOneRecord() {
		//given
		Long hadId = had.getScheduleHadId();
		
		
		//when
		List<CycleRuleDto> results = cycleRs.getCycleRulesByScheduleHadId(hadId);
		
		//then
		assertThat(results).isNotNull();
		assertThat(results.size()).isEqualTo(1);
		
		CycleRuleDto result = results.get(0);
		assertThat(result
				.getScheduleHadDto()
				.getScheduleHadId())
			.isEqualTo(hadId);
		assertThat(result.getTimePoint()).isEqualTo(timePoint);
	}
	
	
	/***
	 * test getCycleRulesByScheduleHadId() method
	 * 給予找不到資料的id的資料應回傳無資料的list
	 */
	@Test
	public void whenGetCycleRulesByScheduleHadIdAndNotFound_thenReturnEmptyList() {
		//given
		long hadId = -99;
		assertThat(entityMgr.find(ScheduleHad.class, hadId)).isNull();
		
		//when
		List<CycleRuleDto> results = cycleRs.getCycleRulesByScheduleHadId(hadId);
		
		//then
		assertThat(results).isNotNull();
		assertThat(results.size()).isEqualTo(0);
	}

	
	/***
	 * test getCycleRuleForOptional() method
	 * 給予找不到資料的id的資料則使用時會丟出例外
	 */
	@Test
	public void whenGetCycleRuleForOptionalAndNotFound_thenThrowsException() {
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
		
		//when
		Optional<CycleRuleDto> result = cycleRs.getCycleRuleForOptional(-99L);
		Throwable thrown = catchThrowable(()->{
			result.get();
		});
		
		//then
		assertThat(result).isNotNull();
		assertThat(thrown).isInstanceOf(NoSuchElementException.class);
	}

}
