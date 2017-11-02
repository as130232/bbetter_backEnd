package com.future.bbetter.schedule.resource;

import com.future.bbetter.schedule.dto.ScheduleTypeDTO;

public interface ScheduleTypeResource {
	// scheduleType CRUD
	/***
	 * 從ScheduleDTO的type取出，並新增至資料庫，若type為null,則回傳的物件亦為null
	 * 新增完資料庫後會將type設定回原本的參數物件。
	 * @param scheduleDTO 物件內的type必須要有值
	 * @return ScheduleDTO 新增完資料庫的type設回原本的參數物件,若參數內的type為null,則回傳的物件亦為null
	 */
	public ScheduleTypeDTO addScheduleType(ScheduleTypeDTO typeDTO);
	public void updateScheduleType(ScheduleTypeDTO typeDTO);
	public void deleteScheduleType(Integer scheduleTypeId);
	
	/***
	 * 取得對應的ScheduleTypeId的資料,若找不到資料則拋出DataNotFoundException
	 * @param scheduleTypeId
	 * @return ScheduleTypeDTO 
	 * @throws DataNotFoundException if it can not found.
	 */
	public ScheduleTypeDTO getScheduleType(Integer scheduleTypeId);
}
