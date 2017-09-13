package com.future.bbetter.schedule.resource;

import java.util.List;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.model.ScheduleDTO;

public interface ScheduleResource {
	
	// schedule CRUD
	/***
	 * 
	 * @param scheduleDTO
	 * @return 回傳成功插入schedule表的值物件DTO,內有scheduleId
	 */
	public ScheduleDTO addSchedule(ScheduleDTO scheduleDTO);
	public void updateSchedule(ScheduleDTO scheduleDTO);
	public void deleteSchedule(Long scheduleId);
	
	// scheduleType CRUD
	public ScheduleDTO addScheduleType(ScheduleDTO scheduleDTO);
	public void updateScheduleType(ScheduleDTO scheduleDTO);
	public void deleteScheduleType(Integer scheduleTypeId);
	
	// scheduleSubType CRUD
	public void addScheduleSubType(ScheduleDTO scheduleDTO);
	public void updateScheduleSubType(ScheduleDTO scheduleDTO);
	public void deleteScheduleSubType(Integer scheduleSubTypeId);

	public ScheduleDTO getScheduleInfo(Long scheduleid) throws DataNotFoundException;
	public List<ScheduleDTO> getScheduleByMemberId(Long memberId) throws DataNotFoundException;
}
