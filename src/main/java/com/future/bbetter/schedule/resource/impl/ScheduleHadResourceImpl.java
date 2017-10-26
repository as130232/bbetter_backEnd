package com.future.bbetter.schedule.resource.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.constant.SCHEDULE;
import com.future.bbetter.schedule.constant.SCHEDULE_HAD;
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.dto.ScheduleDTO;
import com.future.bbetter.schedule.dto.ScheduleHadDTO;
import com.future.bbetter.schedule.dto.ScheduleOwnerDTO;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleRegistrant;
import com.future.bbetter.schedule.repository.ScheduleHadRepository;
import com.future.bbetter.schedule.resource.ScheduleHadResource;
import com.future.bbetter.schedule.resource.ScheduleOwnerResource;
import com.future.bbetter.schedule.resource.ScheduleResource;

@Service
public class ScheduleHadResourceImpl implements ScheduleHadResource{
	@Autowired
	private ScheduleHadRepository scheduleHadRepository;
	@Autowired
	private ScheduleOwnerResource scheduleOwnerResource;
	@Autowired
	private ScheduleResource scheduleResource;
	
	@Override
	public ScheduleHadDTO getScheduleHad(Long scheduleHadId) {
		ScheduleHadDTO scheduleHadDTO = null;
		Optional<ScheduleHad> option = scheduleHadRepository.findById(scheduleHadId);
		if(option.isPresent()) {
			ScheduleHad scheduleHad = option.get();
			//利用Hibernate manyToOne自動取出
			ScheduleDTO scheduleDTO = ScheduleDTO.fromEntity(scheduleHad.getSchedule());
			//取得行程擁有者資訊
			//ScheduleRegistrant scheduleRegistrant = scheduleOwnerResource.getScheduleRegistrant(scheduleHad.getScheduleOwner().getRegistrantId(), scheduleHad.getScheduleOwner().getSource());
			//ScheduleOwnerDTO scheduleOwnerDTO = ScheduleOwnerDTO.fromEntity(scheduleHad.getScheduleOwner(), scheduleRegistrant);
			//bind
			scheduleHadDTO = ScheduleHadDTO.fromEntity(scheduleHad, scheduleDTO);
		}else {
			throw new DataNotFoundException("scheduleHad id: " + scheduleHadId + " is not found.");
		}
		return scheduleHadDTO;
	}
	

	@Override
	public List<ScheduleHadDTO> getScheduleHads(Long scheduleOwnerId) {
		List<ScheduleHadDTO> result = new ArrayList<>();
		Integer isValid = SCHEDULE_OWNER.IS_VALID_YES.value;
		List<ScheduleHad> scheduleHads = scheduleHadRepository.findByScheduleOwnerIdAndIsValid(scheduleOwnerId, isValid);
		scheduleHads.stream()
		//必須為有效行程
		.filter(scheduleHad -> SCHEDULE.IS_VALID_YES.value.equals(scheduleHad.getSchedule().getIsValid()))
		//該擁有行程也必須為有效
		.filter(scheduleHad -> SCHEDULE_HAD.IS_VALID_YES.value.equals(scheduleHad.getIsValid()))
		.forEach(scheduleHad -> {
			ScheduleDTO scheduleDTO = ScheduleDTO.fromEntity(scheduleHad.getSchedule());
			//ScheduleRegistrant scheduleRegistrant = scheduleOwnerResource.getScheduleRegistrant(scheduleHad.getScheduleOwner().getRegistrantId(), scheduleHad.getScheduleOwner().getSource());
			//ScheduleOwnerDTO scheduleOwnerDTO = ScheduleOwnerDTO.fromEntity(scheduleHad.getScheduleOwner());
			result.add(ScheduleHadDTO.fromEntity(scheduleHad, scheduleDTO));
		});
		return result;
	}
	
	
	@Override
	public ScheduleHadDTO addScheduleHad(Long scheduleOwnerId, Long scheduleId, Integer authority) {
		ScheduleHadDTO scheduleHadDTO = this.insert(scheduleOwnerId, scheduleId, authority);
		return scheduleHadDTO;
	}

	/**
	 * 該行程擁有者新增一筆關聯行程的擁有行程
	 * @author Charles
	 * @date 2017年10月21日 下午4:24:25
	 * @param registrantId
	 * @param source
	 */
	private ScheduleHadDTO insert(Long scheduleOwnerId, Long scheduleId, Integer authority) {
		Integer accumulatedTime = 0;
		Integer isValid = SCHEDULE_HAD.IS_VALID_YES.value;
		ScheduleHad scheduleHad = new ScheduleHad();
		scheduleHad.setScheduleOwner(new ScheduleOwner(scheduleOwnerId));
		scheduleHad.setSchedule(new Schedule(scheduleId));
		scheduleHad.setAuthority(authority);
		scheduleHad.setAccumulatedTime(accumulatedTime);
		scheduleHad.setCreatedate(new Date());
		scheduleHad.setIsValid(isValid);
		scheduleHad.setUpdatedate(null);
		ScheduleHad newScheduleHad = scheduleHadRepository.saveAndFlush(scheduleHad);
		//取得行程資訊
		ScheduleDTO scheduleDTO = scheduleResource.getScheduleInfo(scheduleId);
		//取得行程擁有者資訊
		//ScheduleOwnerDTO scheduleOwnerDTO = scheduleOwnerResource.getScheduleOwner(scheduleOwnerId);
		//bind
		ScheduleHadDTO scheduleHadDTO = ScheduleHadDTO.fromEntity(newScheduleHad, scheduleDTO);
		return scheduleHadDTO;
	}

}
