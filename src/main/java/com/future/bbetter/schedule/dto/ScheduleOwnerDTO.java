package com.future.bbetter.schedule.dto;

import java.util.Date;

import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleRegistrant;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Schedule擁有者
 * @author Charles
 * @date 2017年10月21日 下午2:23:33
 */
public @Data @NoArgsConstructor class ScheduleOwnerDTO {
	private Long scheduleOwnerId;
	private Long registrantId;
	private int source;
	private int isValid;
	private Date createdate;
	
	//會員資訊..
	private ScheduleRegistrant scheduleRegistrant;
	
	public static ScheduleOwnerDTO from(@NonNull ScheduleOwner scheduleOwner){
		ScheduleOwnerDTO scheduleOwnerDTO = new ScheduleOwnerDTO();
		scheduleOwnerDTO.setScheduleOwnerId(scheduleOwner.getScheduleOwnerId());
		scheduleOwnerDTO.setIsValid(scheduleOwner.getIsValid());
		scheduleOwnerDTO.setSource(scheduleOwner.getSource());
		scheduleOwnerDTO.setCreatedate(scheduleOwner.getCreatedate());
		return scheduleOwnerDTO;
	}
	
	//若有會員
	public static ScheduleOwnerDTO from(@NonNull ScheduleOwner scheduleOwner, ScheduleRegistrant scheduleRegistrant){
		ScheduleOwnerDTO scheduleOwnerDTO = ScheduleOwnerDTO.from(scheduleOwner);
		scheduleOwnerDTO.setScheduleRegistrant(scheduleRegistrant);
		return scheduleOwnerDTO;
	}
	
	public ScheduleOwner toEntity(){
		ScheduleOwner owner = new ScheduleOwner();
		owner.setScheduleOwnerId(this.getScheduleOwnerId());
		owner.setIsValid(this.getIsValid());
		owner.setSource(this.getSource());
		owner.setCreatedate(this.getCreatedate());
		return owner;
	}
	
}
