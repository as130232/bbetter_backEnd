package com.future.bbetter.schedule.dto;

import java.util.Date;

import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleRegistrant;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public @Data @NoArgsConstructor class ScheduleHadDto {
	
	private Long scheduleHadId;
	//行程資訊
	private ScheduleDto scheduleDto;
	private int authority;
	private int accumulatedTime;
	private Date createdate;
	//行程擁有者
	private ScheduleOwnerDto scheduleOwnerDto;
	//private int isValid;
	//private Date updatedate;
	
	private static ScheduleHadDto from(@NonNull ScheduleHad scheduleHad){
		ScheduleHadDto scheduleHadDto = new ScheduleHadDto();
		scheduleHadDto.setScheduleHadId(scheduleHad.getScheduleHadId());
		scheduleHadDto.setAccumulatedTime(scheduleHad.getAccumulatedTime());
		scheduleHadDto.setAuthority(scheduleHad.getAuthority());
		scheduleHadDto.setCreatedate(scheduleHad.getCreatedate());
		//scheduleHadDto.setIsValid(scheduleHad.getIsValid());
		return scheduleHadDto;
	}
	
	public static ScheduleHadDto from(@NonNull ScheduleHad scheduleHad, 
			@NonNull ScheduleDto scheduleDto){
		ScheduleHadDto scheduleHadDto = ScheduleHadDto.from(scheduleHad);
		scheduleHadDto.setScheduleDto(scheduleDto);
		return scheduleHadDto;
	}
	
	public static ScheduleHadDto from(@NonNull ScheduleHad scheduleHad, 
			@NonNull ScheduleDto scheduleDto,
			@NonNull ScheduleOwnerDto scheduleOwnerDto){
		ScheduleHadDto scheduleHadDto = ScheduleHadDto.from(scheduleHad);
		scheduleHadDto.setScheduleDto(scheduleDto);
		scheduleHadDto.setScheduleOwnerDto(scheduleOwnerDto);
		return scheduleHadDto;
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
