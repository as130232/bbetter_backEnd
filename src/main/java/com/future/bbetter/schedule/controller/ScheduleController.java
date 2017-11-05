package com.future.bbetter.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.dto.ScheduleDto;
import com.future.bbetter.schedule.dto.ScheduleHadDto;
import com.future.bbetter.schedule.resource.ScheduleHadResource;
import com.future.bbetter.schedule.resource.ScheduleOwnerResource;
import com.future.bbetter.schedule.resource.ScheduleResource;
import com.future.bbetter.schedule.service.ScheduleService;

@RestController
@RequestMapping("/api")
public class ScheduleController {
	@Autowired
	private ScheduleOwnerResource scheduleOwnerResource;
	@Autowired
	private ScheduleHadResource scheduleHadResource;
	@Autowired
	private ScheduleResource scheduleResource;
	
	@Autowired
	private ScheduleService scheduleService;
	/**
	 * 取得該會員的擁有行程
	 * @author Charles
	 * @date 2017年10月22日 下午12:07:30
	 */
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/member/me/scheduleHads")
	public List<ScheduleHadDto> getSchedules() throws DataNotFoundException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;
		Long registrantId = new Long(auth.getName());
		//取得註冊的行程擁有者ID
		Long scheduleOwnerId = scheduleOwnerResource.getScheduleOwnerId(registrantId, source);
	    List<ScheduleHadDto> scheduleHadDto = scheduleHadResource.getScheduleHads(scheduleOwnerId);
		return scheduleHadDto;
	}
	
	/**
	 * 會員"主動建立"行程
	 * @author Charles
	 * @date 2017年10月22日 下午12:06:35
	 */
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/member/me/schedule")
	public ScheduleHadDto addSchedule(@RequestBody ScheduleDto scheduleDto) throws InsertOrUpdateDataFailureException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;
		Long registrantId = new Long(auth.getName());
		ScheduleHadDto newScheduleHadDto = scheduleService.createOwnSchedule(registrantId, source, scheduleDto);
		return newScheduleHadDto;
	}
	
	/**
	 * "加入"行程
	 * @author Charles
	 * @date 2017年10月22日 下午12:06:35
	 */
}
