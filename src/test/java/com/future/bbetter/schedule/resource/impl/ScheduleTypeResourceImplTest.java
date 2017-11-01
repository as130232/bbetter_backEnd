package com.future.bbetter.schedule.resource.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.schedule.dto.ScheduleTypeDTO;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.resource.ScheduleTypeResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ScheduleTypeResourceImplTest {

	@TestConfiguration
	static class ScheduleTypeResourceImplTestContextConfiguration {
		@Bean
		public ScheduleTypeResource scheduleTypeResource() {
			return new ScheduleTypeResourceImpl();
		}
	}

	@Autowired
	private TestEntityManager entityMgr;

	@Autowired
	private ScheduleTypeResource schTypeRs;

	@Test
	public void whenAddScheduleType_thenNameFieldShouldBeNull_TypeNameShouldExists() {
		// given
		ScheduleTypeDTO type = new ScheduleTypeDTO();
		type.setTypeName("test_type");

		// when
		ScheduleTypeDTO result = schTypeRs.addScheduleType(type);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getTypeName()).isEqualTo(type.getTypeName());
		assertThat(result.getScheduleTypeId()).isNotNull();
	}

	@Test
	public void whenUpdateScheduleType_thenOldDataShouldBeChanged() {
		// given
		ScheduleType old = addFakeData2ScheduleType();
		entityMgr.detach(old);

		ScheduleTypeDTO updateBean = new ScheduleTypeDTO();
		updateBean.setScheduleTypeId(old.getScheduleTypeId());
		updateBean.setTypeName("Update_data");
		log.info("old.name:{}, updateBean.name:{}", old.getName(), updateBean.getTypeName());

		// when
		schTypeRs.updateScheduleType(updateBean);

		// then
		ScheduleType found = entityMgr.find(ScheduleType.class, old.getScheduleTypeId());

		assertThat(found).isNotNull();
		assertThat(found.getScheduleTypeId()).isEqualTo(old.getScheduleTypeId()).isNotNull();
		assertThat(found.getName()).isNotEqualTo(old.getName()).isNotNull();

	}

	@Test
	public void whenDeleteScheduleType_thenSuccess() {
		// given
		ScheduleType type = addFakeData2ScheduleType();
		// 插入之後entity自動會塞入從資料庫給的pk
		log.info("insert data and typeId:{}", type.getScheduleTypeId());

		// when
		schTypeRs.deleteScheduleType(type.getScheduleTypeId());

		// then
		ScheduleType found = entityMgr.find(ScheduleType.class, type.getScheduleTypeId());
		assertThat(found).isNull();
	}

	private ScheduleType addFakeData2ScheduleType() {
		ScheduleType type = new ScheduleType();
		type.setName("test_type");
		entityMgr.persistAndFlush(type);
		return type;
	}

}
