package com.future.bbetter.schedule.resource;

import java.util.List;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.dto.ScheduleDto;
import com.future.bbetter.schedule.dto.ScheduleTypeDto;

public interface ScheduleResource {
	
	/**
	 * 新增一筆行程
	 * @author Charles
	 * @date 2017年10月21日 下午9:11:48
	 * @param scheduleDTO
	 * @return ScheduleDTO
	 */
	public ScheduleDto addSchedule(ScheduleDto scheduleDto);
	public void updateSchedule(ScheduleDto scheduleDto);
	public void deleteSchedule(Long scheduleId);
	
	/**
	 * 取得該行程資訊
	 * @author Charles
	 * @date 2017年10月21日 下午6:33:56
	 * @return ScheduleDTO
	 */
	public ScheduleDto getScheduleInfo(Long scheduleId);
	
	



}
