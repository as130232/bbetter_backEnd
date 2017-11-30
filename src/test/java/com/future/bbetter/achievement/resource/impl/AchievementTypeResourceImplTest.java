package com.future.bbetter.achievement.resource.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.achievement.dto.AchievementTypeDto;
import com.future.bbetter.achievement.model.AchievementType;
import com.future.bbetter.achievement.resource.AchievementTypeResource;
import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.UnimplementedException;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(AchievementTypeResourceImpl.class)
public class AchievementTypeResourceImplTest {

	@Autowired
	private TestEntityManager entityMgr;
	
	@Autowired
	private AchievementTypeResource achTypeRs;
	
	AchievementType test_type;
	
	@Before
	public void setup() {
		test_type = new AchievementType("test_achievement_type");
		entityMgr.persistAndFlush(test_type);
	}

	@Test
	public void whenAddAchievementType_thenSuccess() {
		//given
		String typeName = "test_type";
		
		//when
		AchievementTypeDto result = achTypeRs.addAchievementType(typeName);
		
		//then
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(typeName);
		assertThat(result.getAchievementTypeId()).isNotNull();
	}

	@Test
	public void whenUpdateAchievementType_thenSuccess() {
		//given
		String newName = "new_test_type";
		Integer typeId = test_type.getAchievementTypeId();
		
		AchievementTypeDto newDto = new AchievementTypeDto();
		newDto.setName(newName);
		newDto.setAchievementTypeId(typeId);
		
		//when
		achTypeRs.updateAchievementType(newDto);
		
		//then
		AchievementType found = entityMgr.find(AchievementType.class, typeId);
		assertThat(found).isNotNull();
		assertThat(found.getAchievementTypeId()).isEqualTo(typeId);
		assertThat(found.getName()).isEqualTo(newName);
	}

	@Test
	public void whenDeleteAchievementType_thenThrowsUnimplementedException() {
		//given
		Integer typeId = -99;
		
		//when
		Throwable thrown = catchThrowable(()->{
			achTypeRs.deleteAchievementType(typeId);
		});
		
		//then
		assertThat(thrown).isInstanceOf(UnimplementedException.class)
			.hasMessageContaining("This method: deleteAchievementType() is unimplemented.");
	}

	@Test
	public void whenGetAchievementType_thenSuccess() {
		//given
		Integer typeId = test_type.getAchievementTypeId();
		
		//when
		AchievementTypeDto result = achTypeRs.getAchievementType(typeId);
		
		//then
		assertThat(result).isNotNull();
		assertThat(result.getAchievementTypeId()).isEqualTo(typeId);
		assertThat(result.getName()).isEqualTo(test_type.getName());
	}

	@Test
	public void whenGetAchievementTypeAndNotFound_thenThrowsException() {
		//given
		Integer typeId = -99;
		assertThat(entityMgr.find(AchievementType.class, typeId)).isNull();
		
		//when
		Throwable thrown = catchThrowable(()->{
			achTypeRs.getAchievementType(typeId);
		});
		
		//then
		assertThat(thrown).isInstanceOf(DataNotFoundException.class)
			.hasMessageContaining("There is no AchievementType data in database, id:");
	}
}
