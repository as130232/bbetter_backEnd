package com.future.bbetter.schedule.resource.impl;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.schedule.constant.CYCLE_RULE;
import com.future.bbetter.schedule.dto.CycleRuleDto;
import com.future.bbetter.schedule.model.CycleRule;
import com.future.bbetter.schedule.repository.CycleRuleRepository;
import com.future.bbetter.schedule.resource.CycleRuleResource;

import lombok.NonNull;

@Repository
public class CycleRuleResourceImpl implements CycleRuleResource{
	
	@Autowired
	private CycleRuleRepository cycleRuleRepo;

	@Override
	public CycleRuleDto addCycleRule(@NonNull CycleRuleDto dto) {
		if(dto.getScheduleDto() == null)
			throw new InsertOrUpdateDataFailureException("It can't found Schedule data in CycleRuleDto: " + dto);
		if(! CYCLE_RULE.validatePeriod(dto.getPeriod()))
			throw new InsertOrUpdateDataFailureException("The period value is illegal in CycleRuleDto: " + dto);
		
		CycleRule data = dto.toEntity();
		data.setCreatedate(new Date());
		data.setUpdatedate(new Date());
		CycleRule result = cycleRuleRepo.save(data);
		return CycleRuleDto.from(result);
	}

	@Override
	public void updateCycleRule(@NonNull CycleRuleDto dto) {
		if(dto.getScheduleDto() == null)
			throw new InsertOrUpdateDataFailureException("It can't found Schedule data in CycleRuleDto: " + dto);
		if(! CYCLE_RULE.validatePeriod(dto.getPeriod()))
			throw new InsertOrUpdateDataFailureException("The period value is illegal in CycleRuleDto: " + dto);
		
		Long cycleId = dto.getCycleRuleId();
		Optional<CycleRule> optData = cycleRuleRepo.findById(cycleId);
		if(optData.isPresent()) {
			CycleRule dataInDb = optData.get();
			dataInDb.setPeriod(dto.getPeriod());
			dataInDb.setTimePoint(dto.getTimePoint());
			dataInDb.setIsValid(dto.getIsValid());
			dataInDb.setUpdatedate(new Date());
			dataInDb.setSchedule(dto.getScheduleDto().toEntity());
			cycleRuleRepo.save(dataInDb);
		}
		
	}

	@Override
	public void deleteCycleRule(@NonNull Long id) {
		cycleRuleRepo.deleteById(id);
	}

	@Override
	public CycleRuleDto getCycleRule(@NonNull Long id) {
		Optional<CycleRule> optData = cycleRuleRepo.findById(id);
		
		return CycleRuleDto.from(optData.orElseThrow(()-> 
			new DataNotFoundException("There is no CycleRule data in database, id:" + id)));
	}

	@Override
	public List<CycleRuleDto> getCycleRulesByScheduleId(@NonNull Long scheduleId) {
		List<CycleRule> results = cycleRuleRepo.getCycleRulesByScheduleId(scheduleId);
		
		return results.stream()
					.map(data -> CycleRuleDto.from(data))
					.collect(toList());
	}

	@Override
	public Optional<CycleRuleDto> getCycleRuleForOptional(@NonNull Long id) {
		Optional<CycleRule> optData = cycleRuleRepo.findById(id);
		return optData.map(d -> CycleRuleDto.from(d));
	}

}
