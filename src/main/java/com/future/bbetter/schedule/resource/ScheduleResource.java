package com.future.bbetter.schedule.resource;

import java.util.List;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.dto.ScheduleDTO;
import com.future.bbetter.schedule.dto.ScheduleTypeDTO;

public interface ScheduleResource {
	
	/**
	 * 新增一筆行程
	 * @author Charles
	 * @date 2017年10月21日 下午9:11:48
	 * @param scheduleDTO
	 * @return ScheduleDTO
	 */
	public ScheduleDTO addSchedule(ScheduleDTO scheduleDTO);
	public void updateSchedule(ScheduleDTO scheduleDTO);
	public void deleteSchedule(Long scheduleId);
	
	/**
	 * 取得該行程資訊
	 * @author Charles
	 * @date 2017年10月21日 下午6:33:56
	 * @return ScheduleDTO
	 */
	public ScheduleDTO getScheduleInfo(Long scheduleId);
	
	



}
