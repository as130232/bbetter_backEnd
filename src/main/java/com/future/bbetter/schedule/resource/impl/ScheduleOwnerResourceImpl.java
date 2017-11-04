package com.future.bbetter.schedule.resource.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.member.resource.MemberResource;
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.dto.ScheduleDTO;
import com.future.bbetter.schedule.dto.ScheduleOwnerDTO;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleRegistrant;
import com.future.bbetter.schedule.repository.ScheduleOwnerRepository;
import com.future.bbetter.schedule.resource.ScheduleOwnerResource;

@Service
public class ScheduleOwnerResourceImpl implements ScheduleOwnerResource{
	@Autowired
	private MemberResource memberResource;
	@Autowired
	private ScheduleOwnerRepository scheduleOwnerRepository;

	@Override
	public ScheduleOwnerDTO addScheduleOwner(Long registrantId, Integer source){
		ScheduleOwnerDTO scheduleOwnerDTO = null;
		//檢查是否
		Integer isValid = SCHEDULE_OWNER.IS_VALID_YES.value;
		Date createdate = new Date();
		ScheduleOwner insert = new ScheduleOwner();	
		insert.setRegistrantId(registrantId);
		insert.setSource(source);
		insert.setIsValid(isValid);
		insert.setCreatedate(createdate);
		ScheduleOwner newScheduleOwner = scheduleOwnerRepository.saveAndFlush(insert);
		//取得註冊者資訊
		ScheduleRegistrant scheduleRegistrant = this.getScheduleRegistrant(registrantId, source);
		scheduleOwnerDTO = ScheduleOwnerDTO.from(newScheduleOwner, scheduleRegistrant);
		return scheduleOwnerDTO;
	}
	
	@Override
	public ScheduleOwnerDTO getScheduleOwner(Long scheduleOwnerId) {
		ScheduleOwnerDTO scheduleOwnerDTO = null;
		Optional<ScheduleOwner> option = scheduleOwnerRepository.findById(scheduleOwnerId);
		if(option.isPresent()) {
			ScheduleOwner scheduleOwner = option.get();
			ScheduleRegistrant scheduleRegistrant = this.getScheduleRegistrant(scheduleOwner.getRegistrantId(), scheduleOwner.getSource());
			scheduleOwnerDTO = ScheduleOwnerDTO.from(scheduleOwner, scheduleRegistrant);
		}else {
			throw new DataNotFoundException("scheduleOwner id: " + scheduleOwnerId + " is not found.");
		}
		return scheduleOwnerDTO;
	}

	
	@Override
	public ScheduleOwnerDTO getScheduleOwner(Long registrantId, Integer source) {
		ScheduleOwnerDTO scheduleOwnerDTO = null;
		ScheduleOwner scheduleOwner = scheduleOwnerRepository.findByRegistrantIdAndSource(registrantId, source);
		//取得註冊者資訊
		ScheduleRegistrant scheduleRegistrant = this.getScheduleRegistrant(registrantId, source);
		scheduleOwnerDTO = ScheduleOwnerDTO.from(scheduleOwner, scheduleRegistrant);
 		return scheduleOwnerDTO;
	}
	
	@Override
	public Long getScheduleOwnerId(Long registrantId, Integer source) {
		Long scheduleOwnerId = scheduleOwnerRepository.findScheduleOwnerIdByRegistrantIdAndSource(registrantId, source);
		if(scheduleOwnerId == null) {
			throw new DataNotFoundException("registrant id: " + registrantId + ", source:" + source + " is not found.");
		}
		//return scheduleOwner.getScheduleOwnerId();
		return scheduleOwnerId;
	}

	@Override
	public ScheduleRegistrant getScheduleRegistrant(Long registrantId, Integer source) {
		ScheduleRegistrant scheduleRegistrant = null;
		//若是會員
		if(SCHEDULE_OWNER.SOURCE_MEMBER.value.equals(source)) {
			scheduleRegistrant = memberResource.getMember(registrantId);
		}
		
		return scheduleRegistrant;
	}
	
	@Override
	public boolean checkIsScheduleOwnerRegister(Long registrantId, Integer source) {
		boolean isScheduleOwnerExist = false;
		ScheduleOwner scheduleOwner = scheduleOwnerRepository.findByRegistrantIdAndSource(registrantId, source);
		if(scheduleOwner != null) {
			isScheduleOwnerExist = true;
		}
		return isScheduleOwnerExist;
	}
	
	
}
