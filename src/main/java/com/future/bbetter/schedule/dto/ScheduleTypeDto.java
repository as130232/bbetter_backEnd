package com.future.bbetter.schedule.dto;

import java.util.Date;

import com.future.bbetter.schedule.model.ScheduleType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public @Data @NoArgsConstructor class ScheduleTypeDto {
	
	// ScheduleType.attributes
	private Integer scheduleTypeId;
	private String typeName;
	private Date createdate;
	
	public static ScheduleTypeDto from(@NonNull ScheduleType scheduleType){
		ScheduleTypeDto dto = new ScheduleTypeDto();
		dto.setTypeName(scheduleType.getName());
		dto.setScheduleTypeId(scheduleType.getScheduleTypeId());
		dto.setCreatedate(scheduleType.getCreatedate());
		return dto;
	}
	
	
	public ScheduleType toEntity(){
		ScheduleType type = new ScheduleType();
		type.setName(this.getTypeName());
		type.setScheduleTypeId(this.getScheduleTypeId());
		type.setCreatedate(this.getCreatedate());
		return type;
	}
	
}
