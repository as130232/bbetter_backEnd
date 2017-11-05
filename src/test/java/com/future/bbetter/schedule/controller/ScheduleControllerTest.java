package com.future.bbetter.schedule.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.resource.ScheduleHadResource;
import com.future.bbetter.schedule.resource.ScheduleOwnerResource;
import com.future.bbetter.schedule.resource.ScheduleResource;
import com.future.bbetter.schedule.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = ScheduleController.class,secure = false)
@SpringBootTest
@ActiveProfiles("test")
public class ScheduleControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;

	@MockBean
	private ScheduleService schSvc;

	@MockBean
	private ScheduleOwnerResource ownerRs;
	@MockBean
	private ScheduleHadResource hadRs;
	@MockBean
	private ScheduleResource schRs;
	
	
	@Before
	public void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	private List<ScheduleHad> getFakeScheduleHadList(){
		List<ScheduleHad> hads = new ArrayList<>();
//		ScheduleHad had1 = new ScheduleHad(schedule, scheduleOwner, authority, isValid, accumulatedTime, createdate);
		
		
		return null;
	}
	
	@Test
	@WithMockUser(username = "1",roles={"USER"})
	public void testGetSchedules() throws Exception {
		//given
		Long registrantId = 1L;
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;
		Long ownerId = 10L;
		
		when(ownerRs.getScheduleOwnerId(registrantId, source))
			.thenReturn(ownerId);
		
		when(hadRs.getScheduleHads(ownerId))
			.thenReturn(new ArrayList<>(0));
		//TODO 補回傳的假資料
		
		//when-then
		MvcResult result = mockMvc.perform(get("/api//member/me/scheduleHads"))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		
		
		assertThat(result).isNotNull();
		//TODO 補更多斷言
	}

}
