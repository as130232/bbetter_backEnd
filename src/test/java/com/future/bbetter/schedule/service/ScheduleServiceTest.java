package com.future.bbetter.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.constant.SCHEDULE_HAD;
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.dto.ScheduleDto;
import com.future.bbetter.schedule.dto.ScheduleHadDto;
import com.future.bbetter.schedule.dto.ScheduleOwnerDto;
import com.future.bbetter.schedule.dto.ScheduleTypeDto;
import com.future.bbetter.schedule.resource.ScheduleHadResource;
import com.future.bbetter.schedule.resource.ScheduleOwnerResource;
import com.future.bbetter.schedule.resource.ScheduleResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ScheduleServiceTest {

	@TestConfiguration
	static class ScheduleServiceTestContextConfiguration{
		@Bean
		public ScheduleResource scheduleResource(){
			return Mockito.mock(ScheduleResource.class);
		}
		@Bean
		public ScheduleOwnerResource scheduleOwnerResource(){
			return Mockito.mock(ScheduleOwnerResource.class);
		}
		@Bean
		public ScheduleHadResource scheduleHadResource(){
			return Mockito.mock(ScheduleHadResource.class);
		}
		@Bean
		public ScheduleService scheduleService(){
			return new ScheduleService();
		}
	}
	
	@Autowired
	private ScheduleResource schRs;

	@Autowired
	private ScheduleHadResource schHadRs;
	
	@Autowired
	private ScheduleOwnerResource schOwnerRs;
	
	
	@Autowired
	private ScheduleService schSvc;
	
	
	private ScheduleDto getFakeScheduleData(){
		Instant now = Instant.now();
		Instant afterTwoHrs = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);
		
		ScheduleTypeDto typeDto = new ScheduleTypeDto();
		typeDto.setScheduleTypeId(5);
		typeDto.setTypeName("test_type");
		
		ScheduleDto schedule = new ScheduleDto();
		schedule.setStartTime(Date.from(now));
		schedule.setEndTime(Date.from(afterTwoHrs));
		schedule.setName("Test_Schedule");
		schedule.setStatus(1);
		schedule.setVisibility(2);
		schedule.setIsCycle(0);
		schedule.setIsNeedRemind(0);
		schedule.setIsTeamSchedule(0);
		schedule.setScheduleTypeDto(typeDto);
		return schedule;
	}
	
	private ScheduleOwnerDto getFakeScheduleOwnerData(){
		ScheduleOwnerDto owner = new ScheduleOwnerDto();
		owner.setScheduleOwnerId(100L);
		owner.setRegistrantId(1L);
		owner.setSource(SCHEDULE_OWNER.SOURCE_MEMBER.value);
		owner.setIsValid(SCHEDULE_OWNER.IS_VALID_YES.value);
		owner.setCreatedate(new Date());
		return owner;
	}
	
	private ScheduleHadDto getFakeScheduleHadData(ScheduleDto scheduleDto,ScheduleOwnerDto ownerDto){
		ScheduleHadDto had = new ScheduleHadDto();
		had.setScheduleHadId(10L);
		had.setScheduleDto(scheduleDto);
		had.setScheduleOwnerDto(ownerDto);
		had.setAuthority(SCHEDULE_HAD.AUTHORITY_NO_PERMISSION.value);
		had.setCreatedate(new Date());
		return had;
	}
	
	/***
	 * 測試createOwnSchedule(),並且尚未註冊行程擁有者,應該會回傳一筆ScheduleHad
	 */
	@Test
	public void whenCreateOwnScheduleAndNotRegistered_thenReturnScheduleHadData() {
		//given
		Long registrantId = 1L;
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;
		ScheduleDto inputSchedule = getFakeScheduleData();
		
		Mockito.when(schOwnerRs.getScheduleOwnerId(registrantId, source))
			.thenThrow(DataNotFoundException.class);
		
		ScheduleOwnerDto mockOwner = getFakeScheduleOwnerData();
		Mockito.when(schOwnerRs.addScheduleOwner(registrantId, source))
			.thenReturn(mockOwner);
		
		Long mockScheduleId = 30L;
		ScheduleDto scheduleWithId = ScheduleDto.from(inputSchedule.toEntity());
		scheduleWithId.setScheduleId(mockScheduleId);
		
		Mockito.when(schRs.addSchedule(inputSchedule))
			.thenReturn(scheduleWithId);
		
		
		Integer authority = SCHEDULE_HAD.AUTHORITY_LEADER.value;
		Long mockOwnerId = mockOwner.getScheduleOwnerId();
		ScheduleHadDto mockHad = getFakeScheduleHadData(scheduleWithId, mockOwner);
		
		Mockito.when(schHadRs.addScheduleHad(mockOwnerId, mockScheduleId, authority))
			.thenReturn(mockHad);
		
		
		//when
		ScheduleHadDto result = schSvc.createOwnSchedule(registrantId, source, inputSchedule);
		

		//then
		assertThat(result).isNotNull();
		assertThat(result.getScheduleHadId()).isEqualTo(mockHad.getScheduleHadId());
		assertThat(result.getScheduleDto()).isEqualTo(scheduleWithId);
		assertThat(result.getScheduleOwnerDto()).isEqualTo(mockOwner);
	}
	
	
	/***
	 * 測試createOwnSchedule(),並且已註冊行程擁有者,應該會回傳一筆ScheduleHad
	 */
	@Test
	public void whenCreateOwnScheduleAndRegistered_thenReturnScheduleHadData() {
		//given
		Long registrantId = 1L;
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;
		ScheduleDto inputSchedule = getFakeScheduleData();
		ScheduleOwnerDto mockOwner = getFakeScheduleOwnerData();
		
		Mockito.when(schOwnerRs.getScheduleOwnerId(registrantId, source))
			.thenReturn(mockOwner.getScheduleOwnerId());
		
		Long mockScheduleId = 30L;
		ScheduleDto scheduleWithId = ScheduleDto.from(inputSchedule.toEntity());
		scheduleWithId.setScheduleId(mockScheduleId);
		
		Mockito.when(schRs.addSchedule(inputSchedule))
			.thenReturn(scheduleWithId);
		
		
		Integer authority = SCHEDULE_HAD.AUTHORITY_LEADER.value;
		Long mockOwnerId = mockOwner.getScheduleOwnerId();
		ScheduleHadDto mockHad = getFakeScheduleHadData(scheduleWithId, mockOwner);
		
		Mockito.when(schHadRs.addScheduleHad(mockOwnerId, mockScheduleId, authority))
			.thenReturn(mockHad);
		
		
		//when
		ScheduleHadDto result = schSvc.createOwnSchedule(registrantId, source, inputSchedule);
		

		//then
		assertThat(result).isNotNull();
		assertThat(result.getScheduleHadId()).isEqualTo(mockHad.getScheduleHadId());
		assertThat(result.getScheduleDto()).isEqualTo(scheduleWithId);
	}
}
