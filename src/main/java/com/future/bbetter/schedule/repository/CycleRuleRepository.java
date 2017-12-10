package com.future.bbetter.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.future.bbetter.schedule.model.CycleRule;

public interface CycleRuleRepository extends 
	JpaRepository<CycleRule, Long> ,
	JpaSpecificationExecutor<CycleRule> {

	@Query("select c from CycleRule c where c.schedule.scheduleId = ?1")
	List<CycleRule> getCycleRulesByScheduleId(Long scheduleId);
	
}
