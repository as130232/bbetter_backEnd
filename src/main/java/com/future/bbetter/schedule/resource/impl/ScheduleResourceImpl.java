package com.future.bbetter.schedule.resource.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.dto.ScheduleDTO;
import com.future.bbetter.schedule.dto.ScheduleTypeDTO;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleSubType;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.repository.ScheduleHadRepository;
import com.future.bbetter.schedule.repository.ScheduleRemindRepository;
import com.future.bbetter.schedule.repository.ScheduleRepository;
import com.future.bbetter.schedule.repository.ScheduleSubTypeRepository;
import com.future.bbetter.schedule.repository.ScheduleTypeRepository;
import com.future.bbetter.schedule.resource.ScheduleResource;

import lombok.NonNull;
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
	
	/***
	 * 當資料庫找不到資料時，呼叫該方法取得預設資料
	 * @return
	 */
	private ScheduleType defaultScheduleType(){
		ScheduleType defaultType = new ScheduleType("Default Type!!!!");
		defaultType.setScheduleTypeId(Integer.MIN_VALUE);
		return defaultType;
	}

	
	@Override
	public ScheduleDTO addSchedule(@NonNull ScheduleDTO scheduleDTO) {
		Schedule newData = scheduleDTO.toSchedule();
		newData.setCreatedate(new Date());
		Schedule afterData = schRepo.save(newData);
		ScheduleDTO result = ScheduleDTO.from(afterData);
		return result;
	}

	@Override
	public void updateSchedule(@NonNull ScheduleDTO scheduleDTO) {
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
//		List<ScheduleHad> hads = schHadRepo.findByScheduleId(scheduleId);
//		
//		schHadRepo.deleteAll(hads);
//		schRepo.deleteById(scheduleId);
		//TODO delete schedule_remind
	}

	@Override
	public ScheduleDTO addScheduleType(@NonNull ScheduleDTO scheduleDTO) {
		ScheduleTypeDTO typeDTO = new ScheduleTypeDTO();
		if(scheduleDTO.getType() != null){
			typeDTO = addScheduleType(scheduleDTO.getType());
			scheduleDTO.setType(typeDTO);
			return scheduleDTO;
		}
		//TODO 可改為拋出exception
		return null;
	}

	@Override
	public ScheduleTypeDTO addScheduleType(@NonNull ScheduleTypeDTO typeDTO) {
		ScheduleType newData = typeDTO.toType();
		ScheduleType afterData = schTypeRepo.save(newData);
		ScheduleTypeDTO result = ScheduleTypeDTO.from(afterData);
		return result;
	}


	@Override
	public void updateScheduleType(@NonNull ScheduleTypeDTO typeDTO) {
		Integer scheduleTypeId = typeDTO.getScheduleTypeId();
		Optional<ScheduleType> optData = schTypeRepo.findById(scheduleTypeId);
		if(optData.isPresent()){
			ScheduleType data = optData.get();
			data.setName(typeDTO.getTypeName());
			schTypeRepo.save(data);
		}
	}

	@Override
	public void deleteScheduleType(Integer scheduleTypeId) {
		log.info("delete schedule type data, id:{}", scheduleTypeId);
		schTypeRepo.deleteById(scheduleTypeId);
	}

	@Override
	public ScheduleTypeDTO addScheduleSubType(@NonNull ScheduleTypeDTO subTypeDTO) {
		ScheduleSubType newData = subTypeDTO.toSubType();
		ScheduleSubType afterData = schSubTypeRepo.save(newData);
		ScheduleTypeDTO result = ScheduleTypeDTO.from(afterData);
		return result;
	}

	@Override
	public void updateScheduleSubType(ScheduleTypeDTO subTypeDTO) {
		Integer scheduleSubTypeId = subTypeDTO.getScheduleSubTypeId();
		Optional<ScheduleSubType> optData = schSubTypeRepo.findById(scheduleSubTypeId);
		if(optData.isPresent()){
			Optional<ScheduleType> type = schTypeRepo.findById(subTypeDTO.getScheduleTypeId());
			ScheduleSubType data = optData.get();
			data.setName(subTypeDTO.getSubTypeName());
			data.setScheduleType(type.orElse(defaultScheduleType()));
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
