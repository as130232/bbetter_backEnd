package com.future.bbetter.schedule.resource.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.dto.ScheduleTypeDto;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.repository.ScheduleTypeRepository;
import com.future.bbetter.schedule.resource.ScheduleTypeResource;

import lombok.NonNull;

@Service
public class ScheduleTypeResourceImpl implements ScheduleTypeResource{
	
	@Autowired
	private ScheduleTypeRepository schTypeRepo;
	

	@Override
	public ScheduleTypeDto addScheduleType(@NonNull ScheduleTypeDto typeDto) {
		ScheduleType newData = typeDto.toEntity();
		newData.setCreatedate(new Date());
		ScheduleType afterData = schTypeRepo.save(newData);
		ScheduleTypeDto result = ScheduleTypeDto.from(afterData);
		return result;
	}


	@Override
	public void updateScheduleType(@NonNull ScheduleTypeDto typeDto) {
		Integer scheduleTypeId = typeDto.getScheduleTypeId();
		Optional<ScheduleType> optData = schTypeRepo.findById(scheduleTypeId);
		if(optData.isPresent()){
			ScheduleType data = optData.get();
			data.setName(typeDto.getTypeName());
			schTypeRepo.save(data);
		}
	}

	@Override
	public void deleteScheduleType(Integer scheduleTypeId) {
		schTypeRepo.deleteById(scheduleTypeId);
	}
	
	@Override
	public ScheduleTypeDto getScheduleType(@NonNull Integer scheduleTypeId){
		Optional<ScheduleType> optType = schTypeRepo.findById(scheduleTypeId);
		return ScheduleTypeDto.from(optType.orElseThrow(() -> 
			new DataNotFoundException("It can not find data, ScheduleTypeId:"+scheduleTypeId)));
	}
	
}
