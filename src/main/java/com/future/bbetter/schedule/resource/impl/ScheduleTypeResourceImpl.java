package com.future.bbetter.schedule.resource.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.schedule.dto.ScheduleDTO;
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
	public ScheduleDTO addScheduleType(@NonNull ScheduleDTO scheduleDTO) {
		ScheduleTypeDTO typeDTO = new ScheduleTypeDTO();
//		if(scheduleDTO.getType() != null){
//			typeDTO = addScheduleType(scheduleDTO.getType());
//			scheduleDTO.setType(typeDTO);
//			return scheduleDTO;
//		}
		//TODO 可改為拋出exception
		return null;
	}

	@Override
	public ScheduleTypeDTO addScheduleType(@NonNull ScheduleTypeDTO typeDTO) {
//		ScheduleType newData = typeDTO.toType();
//		ScheduleType afterData = schTypeRepo.save(newData);
//		ScheduleTypeDTO result = ScheduleTypeDTO.from(afterData);
		return null;
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
	
}
