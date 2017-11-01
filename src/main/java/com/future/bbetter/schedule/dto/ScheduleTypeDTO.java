package com.future.bbetter.schedule.dto;

import com.future.bbetter.schedule.model.ScheduleType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public @Data @NoArgsConstructor class ScheduleTypeDTO {
	
	// ScheduleType.attributes
	private Integer scheduleTypeId;
	private String typeName;
	
	public static ScheduleTypeDTO from(@NonNull ScheduleType scheduleType){
		ScheduleTypeDTO dto = new ScheduleTypeDTO();
		dto.setTypeName(scheduleType.getName());
		dto.setScheduleTypeId(scheduleType.getScheduleTypeId());
		return dto;
	}
	
}
