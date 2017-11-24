package com.future.bbetter.schedule.resource.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.constant.SCHEDULE;
import com.future.bbetter.schedule.constant.SCHEDULE_HAD;
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.dto.ScheduleDto;
import com.future.bbetter.schedule.dto.ScheduleHadDto;
import com.future.bbetter.schedule.dto.ScheduleOwnerDto;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleRegistrant;
import com.future.bbetter.schedule.repository.ScheduleHadRepository;
import com.future.bbetter.schedule.resource.ScheduleHadResource;
import com.future.bbetter.schedule.resource.ScheduleOwnerResource;
import com.future.bbetter.schedule.resource.ScheduleResource;

@Service
public class ScheduleHadResourceImpl implements ScheduleHadResource{
	@Autowired
	private ScheduleHadRepository scheduleHadRepository;
	@Autowired
	private ScheduleOwnerResource scheduleOwnerResource;
	@Autowired
	private ScheduleResource scheduleResource;
	
	@Override
	public ScheduleHadDto addScheduleHad(Long scheduleOwnerId, Long scheduleId, Integer authority) {
		Integer accumulatedTime = 0;
		Integer isValid = SCHEDULE_HAD.IS_VALID_YES.value;
		ScheduleHad scheduleHad = new ScheduleHad();
		scheduleHad.setScheduleOwner(new ScheduleOwner(scheduleOwnerId));
		scheduleHad.setSchedule(new Schedule(scheduleId));
		scheduleHad.setAuthority(authority);
		scheduleHad.setAccumulatedTime(accumulatedTime);
		scheduleHad.setCreatedate(new Date());
		scheduleHad.setIsValid(isValid);
		scheduleHad.setUpdatedate(null);
		ScheduleHad newScheduleHad = scheduleHadRepository.saveAndFlush(scheduleHad);
		//取得行程資訊
		ScheduleDto scheduleDto = scheduleResource.getScheduleInfo(scheduleId);
		//取得行程擁有者資訊
		//ScheduleOwnerDto scheduleOwnerDto = scheduleOwnerResource.getScheduleOwner(scheduleOwnerId);
		//bind
		ScheduleHadDto scheduleHadDto = ScheduleHadDto.from(newScheduleHad, scheduleDto);
		return scheduleHadDto;
	}

	@Override
	public ScheduleHadDto getScheduleHad(Long scheduleHadId) {
		ScheduleHadDto scheduleHadDto = null;
		Optional<ScheduleHad> option = scheduleHadRepository.findById(scheduleHadId);
		if(option.isPresent()) {
			ScheduleHad scheduleHad = option.get();
			//利用Hibernate manyToOne自動取出
			ScheduleDto scheduleDto = ScheduleDto.from(scheduleHad.getSchedule());
			//取得行程擁有者資訊
			//ScheduleRegistrant scheduleRegistrant = scheduleOwnerResource.getScheduleRegistrant(scheduleHad.getScheduleOwner().getRegistrantId(), scheduleHad.getScheduleOwner().getSource());
			//ScheduleOwnerDTO scheduleOwnerDTO = ScheduleOwnerDTO.fromEntity(scheduleHad.getScheduleOwner(), scheduleRegistrant);
			//bind
			scheduleHadDto = ScheduleHadDto.from(scheduleHad, scheduleDto);
		}else {
			throw new DataNotFoundException("scheduleHad id: " + scheduleHadId + " is not found.");
		}
		return scheduleHadDto;
	}
	

	@Override
	public List<ScheduleHadDto> getScheduleHads(Long scheduleOwnerId) {
		List<ScheduleHadDto> result = new ArrayList<>();
		Integer isValid = SCHEDULE_OWNER.IS_VALID_YES.value;
		List<ScheduleHad> scheduleHads = scheduleHadRepository.findByScheduleOwnerIdAndIsValid(scheduleOwnerId, isValid);
		scheduleHads.stream()
		//必須為有效行程
		.filter(scheduleHad -> SCHEDULE.IS_VALID_YES.value.equals(scheduleHad.getSchedule().getIsValid()))
		//該擁有行程也必須為有效
		.filter(scheduleHad -> SCHEDULE_HAD.IS_VALID_YES.value.equals(scheduleHad.getIsValid()))
		.forEach(scheduleHad -> {
			ScheduleDto scheduleDto = ScheduleDto.from(scheduleHad.getSchedule());
			//ScheduleRegistrant scheduleRegistrant = scheduleOwnerResource.getScheduleRegistrant(scheduleHad.getScheduleOwner().getRegistrantId(), scheduleHad.getScheduleOwner().getSource());
			//ScheduleOwnerDto scheduleOwnerDto = ScheduleOwnerDto.fromEntity(scheduleHad.getScheduleOwner());
			result.add(ScheduleHadDto.from(scheduleHad, scheduleDto));
		});
		return result;
	}
	
	
}
