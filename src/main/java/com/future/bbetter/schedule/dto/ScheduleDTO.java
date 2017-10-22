package com.future.bbetter.schedule.dto;

import java.util.Date;

import com.future.bbetter.schedule.model.Schedule;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


/***
 * 公開頁面顯示用的DTO
 * @author alfread
 *
 */
public @Data @NoArgsConstructor class ScheduleDTO {
	// schedule.attributes
	private Long scheduleId;
	private ScheduleTypeDTO scheduleTypeInfo;
	private Integer skillId;
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
	private Date createdate;
	//private Integer isValid;
	//private Date updatedate;
	
	/***
	 * 從entity轉換成ScheduleDTO物件
	 * @param schedule non null.
	 * @return ScheduleDTO object
	 */
	public static ScheduleDTO fromEntity(@NonNull Schedule schedule) {
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setScheduleId(schedule.getScheduleId());
		scheduleDTO.setSkillId(schedule.getSkillId());
		scheduleDTO.setStartTime(schedule.getStartTime());
		scheduleDTO.setEndTime(schedule.getEndTime());
		scheduleDTO.setName(schedule.getName());
		scheduleDTO.setLocation(schedule.getLocation());
		scheduleDTO.setStatus(schedule.getStatus());
		scheduleDTO.setVisibility(schedule.getVisibility());
		scheduleDTO.setIsCycle(schedule.getIsCycle());
		scheduleDTO.setIsNeedRemind(schedule.getIsNeedRemind());
		scheduleDTO.setIsTeamSchedule(schedule.getIsTeamSchedule());
		scheduleDTO.setScheduleTypeInfo(ScheduleTypeDTO.fromEntity(schedule.getScheduleType()));
		scheduleDTO.setCreatedate(schedule.getCreatedate());
		//scheduleDTO.setIsValid(schedule.getIsValid());
		//scheduleDTO.setUpdatedate(schedule.getUpdatedate());
		return scheduleDTO;
	}
	
	/***
	 * 將該物件轉成schedule entity 
	 * @return Schedule object
	 */
//	public Schedule toSchedule(){
//		Schedule sch = new Schedule();
//		sch.setStartTime(this.getStartTime());
//		sch.setEndTime(this.getEndTime());
//		sch.setName(this.getName());
//		sch.setLocation(this.getLocation());
//		sch.setStatus(this.getStatus());
//		sch.setIsCycle(this.getIsCycle());
//		sch.setIsNeedRemind(this.getIsNeedRemind());
//		sch.setIsTeamSchedule(this.getIsTeamSchedule());
//		if(this.getType() != null){
//			sch.setScheduleType(this.getType().toType());
//		}
//		return sch;
//	}
	
	
}
