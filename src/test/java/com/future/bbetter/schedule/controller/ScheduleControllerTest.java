package com.future.bbetter.schedule.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.future.bbetter.JsonUtils;
import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.schedule.constant.SCHEDULE;
import com.future.bbetter.schedule.constant.SCHEDULE_HAD;
import com.future.bbetter.schedule.constant.SCHEDULE_OWNER;
import com.future.bbetter.schedule.dto.ScheduleDto;
import com.future.bbetter.schedule.dto.ScheduleHadDto;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleHad;
import com.future.bbetter.schedule.model.ScheduleOwner;
import com.future.bbetter.schedule.model.ScheduleType;
import com.future.bbetter.schedule.resource.ScheduleHadResource;
import com.future.bbetter.schedule.resource.ScheduleOwnerResource;
import com.future.bbetter.schedule.resource.ScheduleResource;
import com.future.bbetter.schedule.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
// @WebMvcTest(controllers = ScheduleController.class,secure = false)
@SpringBootTest
//@ActiveProfiles("test")
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
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	private Schedule getFakeScheduleData() {
		ScheduleType type = new ScheduleType("test_type");
		type.setScheduleTypeId(1);

		Instant now = Instant.now();
		Instant afterTwoHrs = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);

		Schedule schedule = new Schedule();
		schedule.setScheduleType(type);
		schedule.setStartTime(Date.from(now));
		schedule.setEndTime(Date.from(afterTwoHrs));
		schedule.setName("Test_Schedule");
		schedule.setStatus(SCHEDULE.STATUS_ONCE.value);
		schedule.setVisibility(SCHEDULE.VISIBILITY_PRIVATE.value);
		schedule.setIsCycle(SCHEDULE.IS_CYCLE_NO.value);
		schedule.setIsNeedRemind(SCHEDULE.IS_NEED_REMIND_NO.value);
		schedule.setIsTeamSchedule(SCHEDULE.IS_TEAM_SCHEDULE_NO.value);
		schedule.setIsValid(SCHEDULE.IS_VALID_YES.value);
		schedule.setCreatedate(new Date());

		return schedule;
	}

	private ScheduleOwner getFakeScheduleOwnerData(Long memberId) {
		ScheduleOwner owner = new ScheduleOwner();
		owner.setScheduleOwnerId(10L);
		owner.setRegistrantId(memberId);
		owner.setSource(SCHEDULE_OWNER.SOURCE_MEMBER.value);
		owner.setIsValid(SCHEDULE_OWNER.IS_VALID_YES.value);

		return owner;
	}

	private List<ScheduleHadDto> getFakeScheduleHadList( ScheduleOwner owner) {
		List<ScheduleHadDto> hads = new ArrayList<>();
		ScheduleHad had1 = new ScheduleHad(getFakeScheduleData(), owner, 
				SCHEDULE_HAD.AUTHORITY_MEMBER.value, 
				SCHEDULE_HAD.IS_VALID_YES.value, 0,
				new Date());
		had1.setScheduleHadId(10L);
		ScheduleHad had2 = new ScheduleHad(getFakeScheduleData(), owner, 
				SCHEDULE_HAD.AUTHORITY_MEMBER.value, 
				SCHEDULE_HAD.IS_VALID_NO.value, 0,
				new Date());
		had2.setScheduleHadId(11L);
		hads.add(ScheduleHadDto.from(had1, ScheduleDto.from(had1.getSchedule())) );
		hads.add(ScheduleHadDto.from(had2, ScheduleDto.from(had2.getSchedule())) );

		return hads;
	}

	@Test
	@WithMockUser(username = "1", roles = { "USER" })
	public void testGetSchedules_thenSuccess() throws Exception {
		// given
		Long registrantId = 1L;  //和@WithMockUser(username = "1")的username對應
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;
		ScheduleOwner owner = getFakeScheduleOwnerData(registrantId);
		Long ownerId = owner.getScheduleOwnerId();

		when(ownerRs.getScheduleOwnerId(registrantId, source)).thenReturn(ownerId);

		when(hadRs.getScheduleHads(ownerId))
			.thenReturn(getFakeScheduleHadList(owner));

		// when-then
		mockMvc.perform(get("/api//member/me/scheduleHads"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].scheduleHadId", is(10)))
			.andExpect(jsonPath("$[1].scheduleHadId", is(11)))
			.andDo(print());
				
				
//		verifyNoMoreInteractions(ownerRs);
//		verifyNoMoreInteractions(hadRs);
	}
	
	@Test
	@WithMockUser(username = "1", roles = { "USER" })
	public void testGetSchedulesAndNotFound_thenReturn404() throws Exception {
		// given
		Long registrantId = 1L;
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;

		when(ownerRs.getScheduleOwnerId(registrantId, source))
			.thenThrow(DataNotFoundException.class);
		
		// when-then
		mockMvc.perform(get("/api//member/me/scheduleHads"))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.code", is(404)))
			.andDo(print());
				
		verifyNoMoreInteractions(hadRs);
	}

	@Test
	@WithMockUser(username = "1", roles = { "USER" })
	public void testAddSchedule_thenSuccess() throws Exception {
		//given 
		Long registrantId = 1L;
		Integer source = SCHEDULE_OWNER.SOURCE_MEMBER.value;
		
		Schedule schedule = getFakeScheduleData();
		ScheduleDto scheduleDto = ScheduleDto.from(schedule);
		
		ScheduleHad had = new ScheduleHad(schedule,
						getFakeScheduleOwnerData(registrantId),
						SCHEDULE_HAD.AUTHORITY_MEMBER.value, 
						SCHEDULE_HAD.IS_VALID_NO.value, 0,
						new Date());
		had.setScheduleHadId(12L);
		ScheduleHadDto hadDto = ScheduleHadDto.from(had, scheduleDto);
		
		when(schSvc.createOwnSchedule(registrantId, source, scheduleDto))
			.thenReturn(hadDto);
		
		//when-then
		mockMvc.perform(post("/api/member/me/schedule")
					.contentType(MediaType.APPLICATION_JSON)
					.content(JsonUtils.asJsonString(scheduleDto))
				)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.scheduleHadId", is(12)))
			.andExpect(jsonPath("$.scheduleDto", notNullValue()))
			.andDo(print());
	}
}
