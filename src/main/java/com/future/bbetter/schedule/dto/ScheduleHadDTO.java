package com.future.bbetter.schedule.dto;

import java.util.Date;

import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleRegistrant;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public @Data @NoArgsConstructor class ScheduleHadDTO {
	
	private Long scheduleHadId;
	//行程資訊
	private ScheduleDTO scheduleDto;
	private int authority;
	private int accumulatedTime;
	private Date createdate;
	//行程擁有者
	private ScheduleOwnerDTO scheduleOwnerDto;
	//private int isValid;
	//private Date updatedate;
	
	private static ScheduleHadDTO from(@NonNull ScheduleHad scheduleHad){
		ScheduleHadDTO scheduleHadDTO = new ScheduleHadDTO();
		scheduleHadDTO.setScheduleHadId(scheduleHad.getScheduleHadId());
		scheduleHadDTO.setAccumulatedTime(scheduleHad.getAccumulatedTime());
		scheduleHadDTO.setAuthority(scheduleHad.getAuthority());
		scheduleHadDTO.setCreatedate(scheduleHad.getCreatedate());
		//scheduleHadDTO.setIsValid(scheduleHad.getIsValid());
		return scheduleHadDTO;
	}
	
	public static ScheduleHadDTO from(@NonNull ScheduleHad scheduleHad, 
			@NonNull ScheduleDTO scheduleDto){
		ScheduleHadDTO scheduleHadDTO = ScheduleHadDTO.from(scheduleHad);
		scheduleHadDTO.setScheduleDto(scheduleDto);
		return scheduleHadDTO;
	}
	
	public static ScheduleHadDTO from(@NonNull ScheduleHad scheduleHad, 
			@NonNull ScheduleDTO scheduleDto,
			@NonNull ScheduleOwnerDTO scheduleOwnerDto){
		ScheduleHadDTO scheduleHadDTO = ScheduleHadDTO.from(scheduleHad);
		scheduleHadDTO.setScheduleDto(scheduleDto);
		scheduleHadDTO.setScheduleOwnerDto(scheduleOwnerDto);
		return scheduleHadDTO;
	}
	
	/***
	 * 將目前的DTO物件轉換成Entity物件,
	 * 若ScheduleInfo和ScheduleOwnerInfo不為空則將轉換成Entity塞入ScheduleHad Entity
	 * @return ScheduleHad 
	 */
	public ScheduleHad toEntity(){
		ScheduleHad had = new ScheduleHad();
		had.setScheduleHadId(this.getScheduleHadId());
		had.setAccumulatedTime(this.getAccumulatedTime());
		had.setAuthority(this.getAuthority());
		had.setCreatedate(this.getCreatedate());
		if(this.getScheduleDto() != null){
			Schedule sch = this.getScheduleDto().toEntity();
			had.setSchedule(sch);
		}
		if(this.getScheduleOwnerDto() != null){
			ScheduleOwner owner = this.getScheduleOwnerDto().toEntity();
			had.setScheduleOwner(owner);
		}
		return had;
	}
}
