package com.future.bbetter.schedule.resource;

import java.util.List;
import java.util.Optional;

import com.future.bbetter.schedule.dto.CycleRuleDto;

public interface CycleRuleResource {

	CycleRuleDto addCycleRule(CycleRuleDto dto);
	
	void updateCycleRule(CycleRuleDto dto);
	
	void deleteCycleRule(Long id);
	
	CycleRuleDto getCycleRule(Long id);
	
	Optional<CycleRuleDto> getCycleRuleForOptional(Long id);
	
	List<CycleRuleDto> getCycleRulesByScheduleHadId(Long scheduleHadId);
	
}
