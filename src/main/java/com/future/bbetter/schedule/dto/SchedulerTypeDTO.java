package com.future.bbetter.schedule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor class SchedulerTypeDTO {
	
	// ScheduleType.attributes
	private String typeName;
	private Integer scheduleTypeId;

	// ScheduleSubType.attributes
	private String subTypeName;
	private Integer scheduleSubTypeId;
	
	
}
