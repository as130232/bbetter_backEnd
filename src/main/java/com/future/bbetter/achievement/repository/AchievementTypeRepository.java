package com.future.bbetter.achievement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.future.bbetter.achievement.model.AchievementType;

public interface AchievementTypeRepository extends 
	JpaRepository<AchievementType, Integer> , 
	JpaSpecificationExecutor<AchievementType> {

}
