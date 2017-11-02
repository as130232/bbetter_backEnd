package com.future.bbetter.schedule.resource.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.member.dto.MemberDTO;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.schedule.constant.SCHEDULE;
import com.future.bbetter.schedule.dto.ScheduleDTO;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.repository.ScheduleHadRepository;
import com.future.bbetter.schedule.repository.ScheduleRemindRepository;
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
	public ScheduleDTO getScheduleInfo(Long scheduleId) throws DataNotFoundException {
		ScheduleDTO scheduleDTO = null;
		Optional<Schedule> option = scheduleRepository.findById(scheduleId);
		if(option.isPresent()) {
			Schedule schedule = option.get();
			scheduleDTO = ScheduleDTO.from(schedule);
		}else {
			throw new DataNotFoundException("schedule id: " + scheduleId + " is not found.");
		}
		return scheduleDTO;
	}
	
	@Override
	public ScheduleDTO addSchedule(@NonNull ScheduleDTO scheduleDTO) {
		ScheduleDTO newScheduleDTO = this.insert(scheduleDTO);
		return newScheduleDTO;
	}

	private ScheduleDTO insert(ScheduleDTO scheduleDTO) {
		Integer isValid = SCHEDULE.IS_VALID_YES.value;
		Schedule insert = new Schedule();
		insert.setContinuousTime(scheduleDTO.getContinuousTime());
		insert.setEndTime(scheduleDTO.getEndTime());
		insert.setIsCycle(scheduleDTO.getIsCycle());
		insert.setIsNeedRemind(scheduleDTO.getIsNeedRemind());
		insert.setIsTeamSchedule(scheduleDTO.getIsTeamSchedule());
		insert.setIsValid(isValid);
		insert.setLocation(scheduleDTO.getLocation());
		insert.setName(scheduleDTO.getName());
		insert.setScheduleType(new ScheduleType(scheduleDTO.getScheduleTypeInfo().getScheduleTypeId()));
		insert.setSkillId(scheduleDTO.getSkillId());
		insert.setStartTime(scheduleDTO.getStartTime());	//注意時間轉換問題
		insert.setStatus(scheduleDTO.getStatus());
		insert.setVisibility(scheduleDTO.getVisibility());
		insert.setCreatedate(new Date());
		insert.setUpdatedate(null);
		Schedule newSchedule = scheduleRepository.saveAndFlush(insert);
		return ScheduleDTO.from(newSchedule);
	}
	
	@Override
	public void updateSchedule(@NonNull ScheduleDTO scheduleDTO) {
		Long scheduleId = scheduleDTO.getScheduleId();
		Optional<Schedule> optData =  scheduleRepository.findById(scheduleId);
		if (optData.isPresent()){
			Schedule data = optData.get();
			BeanUtils.copyProperties(scheduleDTO, data);
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

}
