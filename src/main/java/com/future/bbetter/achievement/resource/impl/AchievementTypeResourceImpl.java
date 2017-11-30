package com.future.bbetter.achievement.resource.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.future.bbetter.achievement.dto.AchievementTypeDto;
import com.future.bbetter.achievement.model.AchievementType;
import com.future.bbetter.achievement.repository.AchievementTypeRepository;
import com.future.bbetter.achievement.resource.AchievementTypeResource;
import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.UnimplementedException;

import lombok.NonNull;

@Repository
public class AchievementTypeResourceImpl implements AchievementTypeResource{
	
	@Autowired
	private AchievementTypeRepository achTypeRepo;
	
	@Override
	public AchievementTypeDto addAchievementType(@NonNull String name) {
		AchievementType data = new AchievementType(name);
		AchievementType afterData = achTypeRepo.save(data);
		return AchievementTypeDto.from(afterData);
	}

	@Override
	public void updateAchievementType(@NonNull AchievementTypeDto data) {
		Integer id = data.getAchievementTypeId();
		Optional<AchievementType> optData = achTypeRepo.findById(id);
		if(optData.isPresent()) {
			AchievementType dataInDb = optData.get();
			dataInDb.setName(data.getName());
			achTypeRepo.save(dataInDb);
		}
	}

	@Override
	public void deleteAchievementType(Integer id) {
		throw new UnimplementedException("This method: deleteAchievementType() is unimplemented.");
	}

	@Override
	public AchievementTypeDto getAchievementType(@NonNull Integer id) {
		AchievementType data = achTypeRepo.findById(id)
				.orElseThrow(()->
					new DataNotFoundException("There is no AchievementType data in database, id:" + id));
		
		return AchievementTypeDto.from(data);
	}

}
