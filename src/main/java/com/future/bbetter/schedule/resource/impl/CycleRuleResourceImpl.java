package com.future.bbetter.schedule.resource.impl;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
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
		if(dto.getScheduleHadDto() == null)
			throw new InsertOrUpdateDataFailureException("It can't found ScheduleHad data in CycleRuleDto: " + dto);
		
		CycleRule data = dto.toEntity();
		data.setCreatedate(new Date());
		data.setUpdatedate(new Date());
		CycleRule result = cycleRuleRepo.save(data);
		return CycleRuleDto.from(result);
	}

	@Override
	public void updateCycleRule(@NonNull CycleRuleDto dto) {
		if(dto.getScheduleHadDto() == null)
			throw new InsertOrUpdateDataFailureException("It can't found ScheduleHad data in CycleRuleDto: " + dto);
		
		Long cycleId = dto.getCycleRuleId();
		Optional<CycleRule> optData = cycleRuleRepo.findById(cycleId);
		if(optData.isPresent()) {
			CycleRule dataInDb = optData.get();
			dataInDb.setPeriod(dto.getPeriod());
			dataInDb.setTimePoint(dto.getTimePoint());
			dataInDb.setIsValid(dto.getIsValid());
			dataInDb.setUpdatedate(dto.getUpdatedate());
			dataInDb.setScheduleHad(dto.getScheduleHadDto().toEntity());
			cycleRuleRepo.save(dataInDb);
		}
		
	}

	@Override
	public void deleteCycleRule(Long id) {
		cycleRuleRepo.deleteById(id);
	}

	@Override
	public CycleRuleDto getCycleRule(Long id) {
		Optional<CycleRule> optData = cycleRuleRepo.findById(id);
		
		return CycleRuleDto.from(optData.orElseThrow(()-> 
			new DataNotFoundException("There is no CycleRule data in database, id:" + id)));
	}

	@Override
	public List<CycleRuleDto> getCycleRulesByScheduleHadId(Long scheduleHadId) {
		List<CycleRule> results = cycleRuleRepo.getCycleRulesByScheduleHadId(scheduleHadId);
		
		return results.stream()
					.map(data -> CycleRuleDto.from(data))
					.collect(toList());
	}

	@Override
	public Optional<CycleRuleDto> getCycleRuleForOptional(Long id) {
		Optional<CycleRule> optData = cycleRuleRepo.findById(id);
		return optData.map(d -> CycleRuleDto.from(d));
	}

}
