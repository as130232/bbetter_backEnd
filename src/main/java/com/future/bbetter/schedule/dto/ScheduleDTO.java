package com.future.bbetter.schedule.dto;

import java.util.Date;

import com.future.bbetter.member.model.Member;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleSubType;
import com.future.bbetter.schedule.model.ScheduleType;

import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor class ScheduleDTO {

	public static ScheduleDTO fromEntity(Schedule schedule, ScheduleHad scheduleHad) {
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setStartTime(schedule.getStartTime());
		scheduleDTO.setEndTime(schedule.getEndTime());
		scheduleDTO.setName(schedule.getName());
		scheduleDTO.setLocation(schedule.getLocation());
		scheduleDTO.setStatus(schedule.getStatus());
		scheduleDTO.setIsCycle(schedule.getIsCycle());
		scheduleDTO.setIsNeedRemind(schedule.getIsNeedRemind());
		scheduleDTO.setIsTeamSchedule(schedule.getIsTeamSchedule());
		return scheduleDTO;
	}
	
	// Schedule.attributes
	private Long scheduleId;
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
	
//	// ScheduleHad.attributes
//	private Long scheduleHadId;
//	// 擁有該行程的會員id
//	private Long ownerId;	
//	private Integer authority;
//	private Integer accumulatedTime;
}
