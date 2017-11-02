package com.future.bbetter.schedule.resource.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.dto.ScheduleRemindDTO;
import com.future.bbetter.schedule.model.ScheduleRemind;
import com.future.bbetter.schedule.repository.ScheduleRemindRepository;
import com.future.bbetter.schedule.resource.ScheduleRemindResource;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleRemindResourceImpl implements ScheduleRemindResource {
	
	@Autowired
	private ScheduleRemindRepository schRemindRepo;
	

	@Override
	public ScheduleRemindDTO addScheduleRemind(@NonNull ScheduleRemindDTO remindDto) {
		ScheduleRemind remind = remindDto.toEntity();
		ScheduleRemind result = schRemindRepo.saveAndFlush(remind);
		return ScheduleRemindDTO.from(result);
	}

	@Override
	public void updateScheduleRemind(@NonNull ScheduleRemindDTO remindDto) {
		Long id = remindDto.getScheduleRemindId();
		Optional<ScheduleRemind> optRemind = schRemindRepo.findById(id);
		if(optRemind.isPresent()){
			ScheduleRemind data = optRemind.get();
			data.setRemark(remindDto.getRemark());
			data.setRemindTime(remindDto.getRemindTime());
			data.setRemindWay(remindDto.getRemindWay());
			if(remindDto.getScheduleHadInfo() != null){
				data.setScheduleHad(remindDto.getScheduleHadInfo().toEntity());
			}
			schRemindRepo.save(data);
		}
	}

	@Override
	public void deleteScheduleRemind(Long scheduleRemindId) {
		schRemindRepo.deleteById(scheduleRemindId);
	}

	@Override
	public ScheduleRemindDTO getScheduleRemind(Long scheduleRemindId) {
		Optional<ScheduleRemind> optRemind = schRemindRepo.findById(scheduleRemindId);
		return ScheduleRemindDTO.from(optRemind.orElseThrow(() -> 
				new DataNotFoundException("It can not find data,ScheduleRemindId:"+scheduleRemindId)));
	}

}
