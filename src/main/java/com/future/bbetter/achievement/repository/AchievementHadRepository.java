package com.future.bbetter.achievement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.future.bbetter.achievement.model.AchievementHad;

public interface AchievementHadRepository extends 
	JpaRepository<AchievementHad, Integer> , 
	JpaSpecificationExecutor<AchievementHad> {

	
	@Query("select aHad from AchievementHad aHad where aHad.member.memberId = ?1")
	List<AchievementHad> findByMemberId(Long memberId);
}
