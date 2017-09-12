package com.future.bbetter.schedule.resource;

import java.util.List;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.model.ScheduleDTO;

public interface ScheduleResource {
	
	
	public void addScheduleInfo(ScheduleDTO scheduleDTO);
	public void updateScheduleInfo(ScheduleDTO scheduleDTO);
	public void deleteScheduleInfo(Long scheduleId);
	
	public void deleteScheduleType(Long scheduleTypeId);
	
	public void deleteScheduleSubType(Long scheduleSubTypeId);

	public ScheduleDTO getScheduleInfo(Long scheduleid) throws DataNotFoundException;
	public List<ScheduleDTO> getScheduleByMemberId(Long memberId) throws DataNotFoundException;
}
