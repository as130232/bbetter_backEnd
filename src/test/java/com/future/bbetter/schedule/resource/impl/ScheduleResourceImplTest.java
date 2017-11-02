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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.schedule.constant.SCHEDULE;
import com.future.bbetter.schedule.dto.ScheduleDTO;
import com.future.bbetter.schedule.dto.ScheduleTypeDTO;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.resource.ScheduleResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ScheduleResourceImplTest {

	@TestConfiguration
	static class ScheduleResourceImplTestContextConfiguration {
		@Bean
		public ScheduleResource scheduleResource() {
			return new ScheduleResourceImpl();
		}
	}

	@Autowired
	private TestEntityManager entityMgr;

	@Autowired
	private ScheduleResource schRs;

	@Test
	public void whenAddSchedule_thenCanFoundOneRecord() {
		// given
		Instant now = Instant.now();
		Instant afterTwoHrs = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);

		ScheduleType type = addFakeData2ScheduleType();
		ScheduleTypeDTO typeDto = ScheduleTypeDTO.from(type);
		ScheduleDTO s = new ScheduleDTO();
		s.setStartTime(Date.from(now));
		s.setEndTime(Date.from(afterTwoHrs));
		s.setName("Test_Schedule");
		s.setStatus(1);
		s.setVisibility(2);
		s.setIsCycle(0);
		s.setIsNeedRemind(0);
		s.setIsTeamSchedule(0);
		s.setScheduleTypeInfo(typeDto);

		// when
		ScheduleDTO result = schRs.addSchedule(s);

		// then
		Schedule found = entityMgr.find(Schedule.class, result.getScheduleId());
		assertThat(found.getEndTime()).isEqualTo(s.getEndTime());
		assertThat(found.getName()).isEqualTo(s.getName());
		assertThat(found.getStatus()).isEqualTo(s.getStatus());
		assertThat(found.getIsValid()).isEqualTo(SCHEDULE.IS_VALID_YES.value);
	}

	@Test
	public void whenUpdateSchedule_thenOldDataShouldBeChanged() {
		// given
		Instant now = Instant.now();
		Instant afterTwoHrs = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);

		ScheduleType type = addFakeData2ScheduleType();
		Schedule original = new Schedule();
		original.setScheduleType(type);
		original.setStartTime(Date.from(now));
		original.setEndTime(Date.from(afterTwoHrs));
		original.setName("Test_Schedule");
		original.setStatus(1);
		original.setVisibility(2);
		original.setIsCycle(0);
		original.setIsNeedRemind(0);
		original.setIsTeamSchedule(0);
		original.setIsValid(0);
		original.setCreatedate(Date.from(now));
		entityMgr.persistAndFlush(original);
		entityMgr.detach(original); // 因為複製屬性關係，所以如果不分離會一起被改動值

		// when
		ScheduleDTO updateBean = new ScheduleDTO();
		BeanUtils.copyProperties(original, updateBean);
		updateBean.setName("Updated");
		updateBean.setScheduleId(original.getScheduleId());
		schRs.updateSchedule(updateBean);

		// then
		Schedule found = entityMgr.find(Schedule.class, original.getScheduleId());
		assertThat(found.getName()).isNotEqualTo(original.getName());
	}

	private ScheduleType addFakeData2ScheduleType() {
		ScheduleType type = new ScheduleType();
		type.setName("test_type");
		entityMgr.persistAndFlush(type);
		return type;
	}

}
