package com.future.bbetter.schedule.resource;

import com.future.bbetter.schedule.dto.ScheduleRemindDTO;

public interface ScheduleRemindResource {
	
	/***
	 * 新增一筆行程提醒
	 * @param remindDto
	 * @return ScheduleRemindDTO 含有資料庫給予的ScheduleRemindId
	 */
	public ScheduleRemindDTO addScheduleRemind(ScheduleRemindDTO remindDto);
	
	/***
	 * 更新一筆行程提醒
	 * @param remindDto
	 */
	public void updateScheduleRemind(ScheduleRemindDTO remindDto);
	
	/***
	 * 刪除一筆行程提醒
	 * @param scheduleRemindId
	 */
	public void deleteScheduleRemind(Long scheduleRemindId);
	
	
	/***
	 * 取得一筆行程提醒
	 * @param scheduleRemindId
	 * @return
	 */
	public ScheduleRemindDTO getScheduleRemind(Long scheduleRemindId);
	
}
