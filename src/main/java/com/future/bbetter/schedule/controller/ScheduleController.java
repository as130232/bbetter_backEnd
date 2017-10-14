package com.future.bbetter.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.member.dto.MemberDTO;
import com.future.bbetter.member.resource.MemberResource;
import com.future.bbetter.schedule.dto.ScheduleDTO;
import com.future.bbetter.schedule.resource.ScheduleResource;

@RestController
@RequestMapping("/api")
public class ScheduleController {
	@Autowired
	private MemberResource memberResource;
	@Autowired
	private ScheduleResource scheduleResource;
	
	@PreAuthorize("hasRole('USER')")
	@PostAuthorize("returnObject.email == principal.username")
	@GetMapping("/member/self/schedules")
	public List<ScheduleDTO> getSchedules() throws DataNotFoundException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String email = auth.getName();
	    MemberDTO memberInfo = memberResource.getMember(email);
	    List<ScheduleDTO> schedules = scheduleResource.getSchedulesByMemberId(memberInfo.getMemberId());
		return schedules;
	}
}
