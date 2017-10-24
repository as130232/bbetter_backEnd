package com.future.bbetter.schedule.dto;

import com.future.bbetter.schedule.model.ScheduleSubType;
import com.future.bbetter.schedule.model.ScheduleType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public @Data @NoArgsConstructor class ScheduleTypeDTO {
	
	public static ScheduleTypeDTO from(@NonNull ScheduleType type){
		ScheduleTypeDTO dto = new ScheduleTypeDTO();
		dto.setTypeName(type.getName());
		dto.setScheduleTypeId(type.getScheduleTypeId());
		return dto;
	}
	
	public static ScheduleTypeDTO from(@NonNull ScheduleSubType subType){
		ScheduleTypeDTO dto = new ScheduleTypeDTO();
		if(subType.getScheduleType() != null){
			dto = from(subType.getScheduleType());
		}
		dto.setSubTypeName(subType.getName());
		dto.setScheduleSubTypeId(subType.getScheduleSubTypeId());
		return dto;
	}
	
	public ScheduleSubType toSubType(){
		ScheduleSubType subType = new ScheduleSubType(this.toType(), this.getSubTypeName());
		subType.setScheduleSubTypeId(this.getScheduleSubTypeId());
		return subType;
		
	}
	
	public ScheduleType toType(){
		ScheduleType type = new ScheduleType(this.getTypeName());
		type.setScheduleTypeId(this.getScheduleTypeId());
		return type;
	}
	
	
	// ScheduleType.attributes
	private String typeName;
	private Integer scheduleTypeId;

	// ScheduleSubType.attributes
	private String subTypeName;
	private Integer scheduleSubTypeId;
	
	
}
