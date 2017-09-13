package com.future.bbetter.schedule.resource.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleDTO;
import com.future.bbetter.schedule.model.ScheduleSubType;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.resource.ScheduleResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ScheduleResourceImplTest {
	
	@TestConfiguration
	static class ScheduleResourceImplTestContextConfiguration{
		@Bean
		public ScheduleResource scheduleResource(){
			return new ScheduleResourceImpl();
		}
	}

	@Autowired
	private TestEntityManager entityMgr;
	
	@Autowired
	private ScheduleResource schRs;
	
	@Test
	public void whenDeleteScheduleType_thenSuccess(){
		//given
		ScheduleType type = addFakeData2ScheduleType();
		//插入之後entity自動會塞入從資料庫給的pk
		log.info("insert data and typeId:{}", type.getScheduleTypeId());
		
		//when
		schRs.deleteScheduleType( type.getScheduleTypeId());

		//then
		ScheduleType found = entityMgr.find(ScheduleType.class, type.getScheduleTypeId());
		assertThat(found).isNull();
	}
	
	
	@Test
	public void whenDeleteScheduleSubType_thenSuccess(){
		//given
		ScheduleSubType subType = addFakeData2ScheduleSubType_ScheduleType();
		
		//when
		schRs.deleteScheduleSubType(subType.getScheduleSubTypeId());
		
		//then
		ScheduleSubType found = entityMgr.find(ScheduleSubType.class,
				subType.getScheduleSubTypeId());
		assertThat(found).isNull();
	}
	
	
	@Test
	public void whenAddSchedule_thenCanFoundOneRecord(){
		//given
		Instant now = Instant.now();
		Instant afterTwoHrs = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);
		
		ScheduleSubType subType = addFakeData2ScheduleSubType_ScheduleType();
		ScheduleDTO s = new ScheduleDTO();
		s.setScheduleSubTypeId(subType.getScheduleSubTypeId());
		s.setStartTime(Date.from(now));
		s.setEndTime(Date.from(afterTwoHrs));
		s.setName("Test_Schedule");
		s.setStatus(1);
		s.setVisibility(2);
		s.setIsCycle(0);
		s.setIsNeedRemind(0);
		s.setIsTeamSchedule(0);
		s.setIsValid(0);
		
		//when
		ScheduleDTO result = schRs.addSchedule(s);
		
		//then
		Schedule found = entityMgr.find(Schedule.class, result.getScheduleId());
		assertThat(found.getScheduleSubTypeId()).isEqualTo(s.getScheduleSubTypeId());
		assertThat(found.getEndTime()).isEqualTo(s.getEndTime());
	}
	
	@Test
	public void whenUpdateSchedule_thenOldDataShouldBeChanged(){
		//given
		Instant now = Instant.now();
		Instant afterTwoHrs = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);
		
		ScheduleSubType subType = addFakeData2ScheduleSubType_ScheduleType();
		Schedule orginal = new Schedule();
		orginal.setScheduleSubTypeId(subType.getScheduleSubTypeId());
		orginal.setStartTime(Date.from(now));
		orginal.setEndTime(Date.from(afterTwoHrs));
		orginal.setName("Test_Schedule");
		orginal.setStatus(1);
		orginal.setVisibility(2);
		orginal.setIsCycle(0);
		orginal.setIsNeedRemind(0);
		orginal.setIsTeamSchedule(0);
		orginal.setIsValid(0);		
		entityMgr.persistAndFlush(orginal);
		entityMgr.detach(orginal); //因為複製屬性關係，所以如果不分離會一起被改動值
		
		//when
		ScheduleDTO updateBean = new ScheduleDTO();
		BeanUtils.copyProperties(orginal, updateBean);
		updateBean.setName("Updated");
		updateBean.setScheduleId(orginal.getScheduleId());
		schRs.updateSchedule(updateBean);
		
		//then
		Schedule found = entityMgr.find(Schedule.class, orginal.getScheduleId());
		assertThat(found.getName()).isNotEqualTo(orginal.getName());
	}
	
	@Test
	public void whenAddScheduleType_thenNameFieldShouldBeNull_TypeNameShouldExists(){
		//given
		ScheduleDTO type = new ScheduleDTO();
		type.setTypeName("test_type");
		
		//when
		ScheduleDTO result = schRs.addScheduleType(type);
		
		//then
		assertThat(result.getName()).isEqualTo(type.getName()).isNull();
		assertThat(result.getTypeName()).isEqualTo(type.getTypeName());
	}
	
	
	private ScheduleType addFakeData2ScheduleType(){
		ScheduleType type = new ScheduleType();
		type.setName("test_type");
		entityMgr.persistAndFlush(type);
		return type;
	}
	
	private ScheduleSubType addFakeData2ScheduleSubType_ScheduleType(){
		ScheduleType type = addFakeData2ScheduleType();
		
		ScheduleSubType subType = new ScheduleSubType();
		subType.setName("test_subtype");
		subType.setScheduleTypeId(type.getScheduleTypeId());
		entityMgr.persistAndFlush(subType);
		return subType;
	}
}
