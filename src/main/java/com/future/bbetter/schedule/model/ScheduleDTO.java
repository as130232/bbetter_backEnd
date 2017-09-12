package com.future.bbetter.schedule.model;

import java.util.Date;

import lombok.Data;

public @Data class ScheduleDTO {

	
	// Schedule.attributes
	private Long scheduleId;
	private Integer scheduleSubTypeId;
	private Date startTime;
	private Date endTime;
	private String name;
	private String location;
	private Integer status;
	private Integer continuousTime;
	private Integer visibility;
	private Integer isCycle;
	private Integer isNeedRemind;
	private Integer isTeamSchedule;
	private Integer isValid;
	private Date createdate;
	private Date updatedate;
	
	// ScheduleType.attributes
	private String typeName;
	
	// ScheduleSubType.attributes
	private String subTypename;
	
	// ScheduleHad.attributes
	private Long scheduleHadId;
	// 擁有該行程的會員id
	private Long ownerId;	
	private Integer authority;
	private Integer accumulatedTime;
}
