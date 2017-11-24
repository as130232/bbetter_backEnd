package com.future.bbetter.schedule.resource.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.constant.SCHEDULE;
import com.future.bbetter.schedule.dto.ScheduleDto;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.repository.ScheduleRepository;
import com.future.bbetter.schedule.resource.ScheduleResource;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleResourceImpl implements ScheduleResource {

	@Autowired
	private ScheduleRepository scheduleRepository;

	
	@Override
	public ScheduleDto addSchedule(@NonNull ScheduleDto scheduleDto) {
		Integer isValid = SCHEDULE.IS_VALID_YES.value;
		Schedule insert = new Schedule();
		insert.setContinuousTime(scheduleDto.getContinuousTime());
		insert.setEndTime(scheduleDto.getEndTime());
		insert.setIsCycle(scheduleDto.getIsCycle());
		insert.setIsNeedRemind(scheduleDto.getIsNeedRemind());
		insert.setIsTeamSchedule(scheduleDto.getIsTeamSchedule());
		insert.setIsValid(isValid);
		insert.setLocation(scheduleDto.getLocation());
		insert.setName(scheduleDto.getName());
		insert.setScheduleType(new ScheduleType(scheduleDto.getScheduleTypeDto().getScheduleTypeId()));
		insert.setSkillId(scheduleDto.getSkillId());
		insert.setStartTime(scheduleDto.getStartTime());	//注意時間轉換問題
		insert.setStatus(scheduleDto.getStatus());
		insert.setVisibility(scheduleDto.getVisibility());
		insert.setCreatedate(new Date());
		insert.setUpdatedate(null);
		Schedule newSchedule = scheduleRepository.saveAndFlush(insert);
		return ScheduleDto.from(newSchedule);
	}
	
	@Override
	public void updateSchedule(@NonNull ScheduleDto scheduleDto) {
		Long scheduleId = scheduleDto.getScheduleId();
		Optional<Schedule> optData =  scheduleRepository.findById(scheduleId);
		if (optData.isPresent()){
			Schedule data = optData.get();
			BeanUtils.copyProperties(scheduleDto, data);
			scheduleRepository.save(data);
		}
	}

	/***
	 * 從schedule_remind > schedule_had > schedule
	 */
	@Override
	public void deleteSchedule(Long scheduleId) {
//		List<ScheduleHad> hads = schHadRepo.findByScheduleId(scheduleId);
//		
//		schHadRepo.deleteAll(hads);
//		scheduleRepository.deleteById(scheduleId);
		//TODO delete schedule_remind
	}
	
	@Override
	public ScheduleDto getScheduleInfo(Long scheduleId) throws DataNotFoundException {
		ScheduleDto scheduleDto = null;
		Optional<Schedule> option = scheduleRepository.findById(scheduleId);
		if(option.isPresent()) {
			Schedule schedule = option.get();
			scheduleDto = ScheduleDto.from(schedule);
		}else {
			throw new DataNotFoundException("schedule id: " + scheduleId + " is not found.");
		}
		return scheduleDto;
	}


}
