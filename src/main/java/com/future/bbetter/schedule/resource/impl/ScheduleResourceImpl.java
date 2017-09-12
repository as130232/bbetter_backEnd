package com.future.bbetter.schedule.resource.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.model.ScheduleDTO;
import com.future.bbetter.schedule.repository.ScheduleHadRepository;
import com.future.bbetter.schedule.repository.ScheduleRemindRepository;
import com.future.bbetter.schedule.repository.ScheduleRepository;
import com.future.bbetter.schedule.repository.ScheduleSubTypeRepository;
import com.future.bbetter.schedule.repository.ScheduleTypeRepository;
import com.future.bbetter.schedule.resource.ScheduleResource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleResourceImpl implements ScheduleResource{

	@Autowired
	private ScheduleRepository schRepo;
	@Autowired
	private ScheduleHadRepository schHadRepo;
	@Autowired
	private ScheduleTypeRepository schTypeRepo;
	@Autowired
	private ScheduleSubTypeRepository schSubTypeRepo;
	@Autowired
	private ScheduleRemindRepository schRemindRepo;
	
	@Override
	public void addScheduleInfo(ScheduleDTO scheduleDTO) {
		// TODO Auto-generated method stub
		log.info("");
	}

	@Override
	public void updateScheduleInfo(ScheduleDTO scheduleDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteScheduleInfo(Long scheduleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteScheduleType(Long scheduleTypeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteScheduleSubType(Long scheduleSubTypeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScheduleDTO getScheduleInfo(Long scheduleid) throws DataNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScheduleDTO> getScheduleByMemberId(Long memberId) throws DataNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
