package com.future.bbetter.schedule.resource;

import java.util.List;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.model.ScheduleDTO;

public interface ScheduleResource {
	
	// schedule CRUD
	public void addSchedule(ScheduleDTO scheduleDTO);
	public void updateSchedule(ScheduleDTO scheduleDTO);
	public void deleteSchedule(Long scheduleId);
	
	// scheduleType CRUD
	public void addScheduleType(ScheduleDTO scheduleDTO);
	public void updateScheduleType(ScheduleDTO scheduleDTO);
	public void deleteScheduleType(Long scheduleTypeId);
	
	// scheduleSubType CRUD
	public void addScheduleSubType(ScheduleDTO scheduleDTO);
	public void updateScheduleSubType(ScheduleDTO scheduleDTO);
	public void deleteScheduleSubType(Long scheduleSubTypeId);

	public ScheduleDTO getScheduleInfo(Long scheduleid) throws DataNotFoundException;
	public List<ScheduleDTO> getScheduleByMemberId(Long memberId) throws DataNotFoundException;
}
