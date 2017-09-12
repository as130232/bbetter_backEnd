package com.future.bbetter.schedule.resource.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleDTO;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleRemind;
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
	public void addSchedule(ScheduleDTO scheduleDTO) {
		Schedule newData = new Schedule();
		BeanUtils.copyProperties(scheduleDTO, newData);
		newData.setCreatedate(new Date());
		schRepo.save(newData);
	}

	@Override
	public void updateSchedule(ScheduleDTO scheduleDTO) {
		Long scheduleId = scheduleDTO.getScheduleId();
		Optional<Schedule> optData =  schRepo.findById(scheduleId);
		if (optData.isPresent()){
			log.info("get data,id:{}",scheduleId);
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
		List<ScheduleRemind> reminds = schRemindRepo.findByScheduleId(scheduleId);
		List<ScheduleHad> hads = schHadRepo.findByScheduleId(scheduleId);
		
		schRemindRepo.deleteAll(reminds);
		schHadRepo.deleteAll(hads);
		schRepo.deleteById(scheduleId);
		
	}

	@Override
	public void addScheduleType(ScheduleDTO scheduleDTO) {
		ScheduleType newData = new ScheduleType();
		BeanUtils.copyProperties(scheduleDTO, newData);
		schTypeRepo.save(newData);
	}

	@Override
	public void updateScheduleType(ScheduleDTO scheduleDTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteScheduleType(Long scheduleTypeId) {
		log.info("delete schedule type data, id:{}", scheduleTypeId);
		schTypeRepo.deleteById(scheduleTypeId);
	}

	@Override
	public void addScheduleSubType(ScheduleDTO scheduleDTO) {
		ScheduleSubType newData = new ScheduleSubType();
		BeanUtils.copyProperties(scheduleDTO, newData);
		schSubTypeRepo.save(newData);
	}

	@Override
	public void updateScheduleSubType(ScheduleDTO scheduleDTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteScheduleSubType(Long scheduleSubTypeId) {
		log.info("delete schedule sub type data, id:{}", scheduleSubTypeId);
		schSubTypeRepo.deleteById(scheduleSubTypeId);
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
