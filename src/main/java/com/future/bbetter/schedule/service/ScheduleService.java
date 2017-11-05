package com.future.bbetter.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.constant.SCHEDULE_HAD;
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.dto.ScheduleDto;
import com.future.bbetter.schedule.dto.ScheduleHadDto;
import com.future.bbetter.schedule.dto.ScheduleOwnerDto;
import com.future.bbetter.schedule.resource.ScheduleHadResource;
import com.future.bbetter.schedule.resource.ScheduleOwnerResource;
import com.future.bbetter.schedule.resource.ScheduleResource;

@Service
public class ScheduleService {
	@Autowired
	private ScheduleResource scheduleResource;
	@Autowired
	private ScheduleOwnerResource scheduleOwnerResource;
	@Autowired
	private ScheduleHadResource scheduleHadResource;
	
	/**
	 * 建立該行程擁有者的行程
	 * @author Charles
	 * @date 2017年10月22日 下午2:39:35
	 * @param registrantId
	 * @param source
	 * @param scheduleDTO
	 * @throws exceptionType 如果...將拋出exceptionType異常.
	 * @return ScheduleHadDTO
	 */
	@Transactional
	public ScheduleHadDto createOwnSchedule(Long registrantId, Integer source, ScheduleDto scheduleDto) {
		//1.取得該會員對應行程擁有者ID
		Long scheduleOwnerId = null;
		//先檢查該會員是否已註冊對應行程擁有者，若找不到，註冊一個
		try {
			scheduleOwnerId = scheduleOwnerResource.getScheduleOwnerId(registrantId, source);
		}catch(DataNotFoundException e) {
			ScheduleOwnerDto scheduleOwnerDTO = scheduleOwnerResource.addScheduleOwner(registrantId, source);
			scheduleOwnerId = scheduleOwnerDTO.getScheduleOwnerId();
		}
		
		//2.建立該行程
		ScheduleDto newScheduleDto = scheduleResource.addSchedule(scheduleDto);
		
		//3.將該行程加入到該行程擁有者的擁有行程中
		Integer authority = SCHEDULE_HAD.AUTHORITY_LEADER.value;
		ScheduleHadDto newScheduleHadDto = scheduleHadResource.addScheduleHad(scheduleOwnerId, newScheduleDto.getScheduleId(), authority);
		return newScheduleHadDto;
	}
}
