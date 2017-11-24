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
public @Data @NoArgsConstructor class ScheduleOwnerDto {
	private Long scheduleOwnerId;
	private Long registrantId;
	private int source;
	private int isValid;
	private Date createdate;
	
	//會員資訊..
	private ScheduleRegistrant scheduleRegistrant;
	
	public static ScheduleOwnerDto from(@NonNull ScheduleOwner scheduleOwner){
		ScheduleOwnerDto scheduleOwnerDto = new ScheduleOwnerDto();
		scheduleOwnerDto.setScheduleOwnerId(scheduleOwner.getScheduleOwnerId());
		scheduleOwnerDto.setIsValid(scheduleOwner.getIsValid());
		scheduleOwnerDto.setSource(scheduleOwner.getSource());
		scheduleOwnerDto.setCreatedate(scheduleOwner.getCreatedate());
		return scheduleOwnerDto;
	}
	
	//若有會員
	public static ScheduleOwnerDto from(@NonNull ScheduleOwner scheduleOwner, ScheduleRegistrant scheduleRegistrant){
		ScheduleOwnerDto scheduleOwnerDto = ScheduleOwnerDto.from(scheduleOwner);
		scheduleOwnerDto.setScheduleRegistrant(scheduleRegistrant);
		return scheduleOwnerDto;
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
