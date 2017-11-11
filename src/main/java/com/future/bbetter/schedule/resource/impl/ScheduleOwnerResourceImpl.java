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
import com.future.bbetter.schedule.dto.ScheduleDto;
import com.future.bbetter.schedule.dto.ScheduleOwnerDto;
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
	public ScheduleOwnerDto addScheduleOwner(Long registrantId, Integer source){
		ScheduleOwnerDto scheduleOwnerDTO = null;
		if(checkIsScheduleOwnerRegister(registrantId, source)){
			throw new InsertOrUpdateDataFailureException("該行程擁有者已被註冊！");
		}
		
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
		scheduleOwnerDTO = ScheduleOwnerDto.from(newScheduleOwner, scheduleRegistrant);
		return scheduleOwnerDTO;
	}
	
	@Override
	public ScheduleOwnerDto getScheduleOwner(Long scheduleOwnerId) {
		ScheduleOwnerDto scheduleOwnerDto = null;
		Optional<ScheduleOwner> option = scheduleOwnerRepository.findById(scheduleOwnerId);
		if(option.isPresent()) {
			ScheduleOwner scheduleOwner = option.get();
			ScheduleRegistrant scheduleRegistrant = this.getScheduleRegistrant(scheduleOwner.getRegistrantId(), scheduleOwner.getSource());
			scheduleOwnerDto = ScheduleOwnerDto.from(scheduleOwner, scheduleRegistrant);
		}else {
			throw new DataNotFoundException("scheduleOwner id: " + scheduleOwnerId + " is not found.");
		}
		return scheduleOwnerDto;
	}

	
	@Override
	public ScheduleOwnerDto getScheduleOwner(Long registrantId, Integer source) {
		ScheduleOwnerDto scheduleOwnerDto = null;
		ScheduleOwner scheduleOwner = scheduleOwnerRepository.findByRegistrantIdAndSource(registrantId, source);
		//取得註冊者資訊
		ScheduleRegistrant scheduleRegistrant = this.getScheduleRegistrant(registrantId, source);
		scheduleOwnerDto = ScheduleOwnerDto.from(scheduleOwner, scheduleRegistrant);
 		return scheduleOwnerDto;
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
