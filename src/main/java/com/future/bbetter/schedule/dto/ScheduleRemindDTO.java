package com.future.bbetter.schedule.dto;

import java.util.Date;

import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleRemind;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public @Data @NoArgsConstructor class ScheduleRemindDTO {
	
	private Long scheduleRemindId;
	private ScheduleHadDTO scheduleHadInfo;
	private Date remindTime;
	private int remindWay;
	private String remark;
	
	/***
	 * 從ScheduleRemind Entity轉換成DTO物件傳回
	 * @param remind
	 * @return ScheduleRemindDTO 其中的ScheduleHadDTO只有scheduleHadId
	 */
	public static ScheduleRemindDTO from(@NonNull ScheduleRemind remind){
		ScheduleRemindDTO dto = new ScheduleRemindDTO();
		if(remind.getScheduleHad() != null){
			ScheduleHadDTO hadDto = new ScheduleHadDTO();
			hadDto.setScheduleHadId(remind.getScheduleHad().getScheduleHadId());
			dto.setScheduleHadInfo(hadDto);
		}
		dto.setScheduleRemindId(remind.getScheduleRemindId());
		dto.setRemark(remind.getRemark());
		dto.setRemindTime(remind.getRemindTime());
		dto.setRemindWay(remind.getRemindWay());
		return dto;
	}

	/***
	 * 將目前DTO物件轉換成Entity物件
	 * 若ScheduleHadInfo不為空則轉換成Entity並塞入ScheduleRemind Entity
	 * @return ScheduleRemind 
	 */
	public ScheduleRemind toEntity(){
		ScheduleRemind entity = new ScheduleRemind();
		entity.setScheduleRemindId(this.getScheduleRemindId());
		entity.setRemark(this.getRemark());
		entity.setRemindTime(this.getRemindTime());
		entity.setRemindWay(this.getRemindWay());
		if(this.getScheduleHadInfo() != null){
			ScheduleHad had = this.getScheduleHadInfo().toEntity();
			entity.setScheduleHad(had);
		}
		return entity;
	}
}
