package com.future.bbetter.schedule.resource.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleDTO;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleSubType;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.repository.ScheduleHadRepository;
import com.future.bbetter.schedule.repository.ScheduleRemindRepository;
import com.future.bbetter.schedule.repository.ScheduleRepository;
import com.future.bbetter.schedule.repository.ScheduleSubTypeRepository;
import com.future.bbetter.schedule.repository.ScheduleTypeRepository;
import com.future.bbetter.schedule.resource.ScheduleResource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleResourceImpl implements ScheduleResource {

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
	public ScheduleDTO addSchedule(ScheduleDTO scheduleDTO) {
		Schedule newData = new Schedule();
		BeanUtils.copyProperties(scheduleDTO, newData);
		newData.setCreatedate(new Date());
		Schedule afterData = schRepo.save(newData);
		ScheduleDTO result = new ScheduleDTO();
		BeanUtils.copyProperties(afterData, result);
		return result;
	}

	@Override
	public void updateSchedule(ScheduleDTO scheduleDTO) {
		Long scheduleId = scheduleDTO.getScheduleId();
		log.info("get data,id:{}",scheduleId);
		Optional<Schedule> optData =  schRepo.findById(scheduleId);
		if (optData.isPresent()){
			Schedule data = optData.get();
			BeanUtils.copyProperties(scheduleDTO, data);
			schRepo.save(data);
		}
	}

	/***
	 * 從schedule_remind > schedule_had > schedule
	 */
	@Override
	public void deleteSchedule(Long scheduleId) {
		List<ScheduleHad> hads = schHadRepo.findByScheduleId(scheduleId);
		
		schHadRepo.deleteAll(hads);
		schRepo.deleteById(scheduleId);
		//TODO delete schedule_remind
	}

	@Override
	public ScheduleDTO addScheduleType(ScheduleDTO scheduleDTO) {
		ScheduleType newData = new ScheduleType();
		newData.setName(scheduleDTO.getTypeName());
		ScheduleType afterData = schTypeRepo.save(newData);
		ScheduleDTO result = new ScheduleDTO();
		result.setTypeName(scheduleDTO.getTypeName());
		result.setScheduleTypeId(afterData.getScheduleTypeId());
		return result;
	}

	@Override
	public void updateScheduleType(ScheduleDTO scheduleDTO) {
		Integer scheduleTypeId = scheduleDTO.getScheduleTypeId();
		Optional<ScheduleType> optData = schTypeRepo.findById(scheduleTypeId);
		if(optData.isPresent()){
			ScheduleType data = optData.get();
			data.setName(scheduleDTO.getTypeName());
			schTypeRepo.save(data);
		}
	}

	@Override
	public void deleteScheduleType(Integer scheduleTypeId) {
		log.info("delete schedule type data, id:{}", scheduleTypeId);
		schTypeRepo.deleteById(scheduleTypeId);
	}

	@Override
	public ScheduleDTO addScheduleSubType(ScheduleDTO scheduleDTO) {
		ScheduleSubType newData = new ScheduleSubType();
		newData.setName(scheduleDTO.getSubTypeName());
		newData.setScheduleTypeId(scheduleDTO.getScheduleTypeId());
		ScheduleSubType afterData = schSubTypeRepo.save(newData);
		ScheduleDTO result = new ScheduleDTO();
		result.setSubTypeName(afterData.getName());
		result.setScheduleTypeId(afterData.getScheduleTypeId());
		result.setScheduleSubTypeId(afterData.getScheduleSubTypeId());
		return result;
	}

	@Override
	public void updateScheduleSubType(ScheduleDTO scheduleDTO) {
		Integer scheduleSubTypeId = scheduleDTO.getScheduleSubTypeId();
		Optional<ScheduleSubType> optData = schSubTypeRepo.findById(scheduleSubTypeId);
		if(optData.isPresent()){
			ScheduleSubType data = optData.get();
			data.setName(scheduleDTO.getSubTypeName());
			data.setScheduleTypeId(scheduleDTO.getScheduleTypeId());
			schSubTypeRepo.save(data);
		}
	}

	@Override
	public void deleteScheduleSubType(Integer scheduleSubTypeId) {
		log.info("delete schedule sub type data, id:{}", scheduleSubTypeId);
		schSubTypeRepo.deleteById(scheduleSubTypeId);
	}

	@Override
	public ScheduleDTO getScheduleInfo(Long scheduleId) throws DataNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScheduleDTO> getScheduleByMemberId(Long memberId) throws DataNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
