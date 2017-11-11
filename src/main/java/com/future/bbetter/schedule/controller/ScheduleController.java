package com.future.bbetter.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	private ScheduleOwnerResource scheduleOwnerRs;
	@Autowired
	private ScheduleHadResource scheduleHadRs;
	@Autowired
	private ScheduleResource scheduleRs;
	
	@Autowired
	private ScheduleService scheduleSvc;
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
		Long scheduleOwnerId = scheduleOwnerRs.getScheduleOwnerId(registrantId, source);
	    List<ScheduleHadDto> scheduleHadDto = scheduleHadRs.getScheduleHads(scheduleOwnerId);
		return scheduleHadDto;
	}
	
	/**
	 * 會員"主動建立"行程
	 * @author Charles
	 * @date 2017年10月22日 下午12:06:35
	 */
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/member/me/schedule")
	@ResponseStatus(HttpStatus.CREATED)
	public ScheduleHadDto addSchedule(@RequestBody ScheduleDto scheduleDto) throws InsertOrUpdateDataFailureException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;
		Long registrantId = new Long(auth.getName());
		ScheduleHadDto newScheduleHadDto = scheduleSvc.createOwnSchedule(registrantId, source, scheduleDto);
		return newScheduleHadDto;
	}
	
	/**
	 * 更新會員自己的行程資訊
	 * @author alfread
	 * @date 2017年11月10日 下午8:34:39
	 */
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/member/me/schedule/{scheduleId}")
	@ResponseStatus(HttpStatus.OK)
	public void updateSchedule(@RequestBody ScheduleDto scheduleDto){
		scheduleRs.updateSchedule(scheduleDto);
	}
	
	/**
	 * "加入"行程
	 * @author Charles
	 * @date 2017年10月22日 下午12:06:35
	 */
}
