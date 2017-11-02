package com.future.bbetter.schedule.resource.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.dto.ScheduleTypeDTO;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.repository.ScheduleTypeRepository;
import com.future.bbetter.schedule.resource.ScheduleTypeResource;

import lombok.NonNull;

@Service
public class ScheduleTypeResourceImpl implements ScheduleTypeResource{
	
	@Autowired
	private ScheduleTypeRepository schTypeRepo;
	

	@Override
	public ScheduleTypeDTO addScheduleType(@NonNull ScheduleTypeDTO typeDTO) {
		ScheduleType newData = typeDTO.toEntity();
		newData.setCreatedate(new Date());
		ScheduleType afterData = schTypeRepo.save(newData);
		ScheduleTypeDTO result = ScheduleTypeDTO.from(afterData);
		return result;
	}


	@Override
	public void updateScheduleType(@NonNull ScheduleTypeDTO typeDTO) {
		Integer scheduleTypeId = typeDTO.getScheduleTypeId();
		Optional<ScheduleType> optData = schTypeRepo.findById(scheduleTypeId);
		if(optData.isPresent()){
			ScheduleType data = optData.get();
			data.setName(typeDTO.getTypeName());
			schTypeRepo.save(data);
		}
	}

	@Override
	public void deleteScheduleType(Integer scheduleTypeId) {
		schTypeRepo.deleteById(scheduleTypeId);
	}
	
	@Override
	public ScheduleTypeDTO getScheduleType(@NonNull Integer scheduleTypeId){
		Optional<ScheduleType> optType = schTypeRepo.findById(scheduleTypeId);
		return ScheduleTypeDTO.from(optType.orElseThrow(() -> 
			new DataNotFoundException("It can not find data,ScheduleTypeId:"+scheduleTypeId)));
	}
	
}
