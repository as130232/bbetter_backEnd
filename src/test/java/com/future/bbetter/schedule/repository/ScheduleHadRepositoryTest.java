package com.future.bbetter.schedule.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.schedule.model.ScheduleHad;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class ScheduleHadRepositoryTest {
	
	@Autowired
	private TestEntityManager entityMgr;
	
	@Autowired
	private ScheduleHadRepository schHadRepo;
	
	
	@Test
	public void whenFindByScheduleId_thenReturnRecords(){
		//given
		ScheduleHad had = new ScheduleHad();
		had.setMemberId(1L);
		had.setScheduleId(3L);
		had.setAuthority(1);
		had.setIsValid(1);
		had.setAccumulatedTime(0);
		entityMgr.persistAndFlush(had);
		
		ScheduleHad had2 = new ScheduleHad();
		had2.setMemberId(2L);
		had2.setScheduleId(3L);
		had2.setAuthority(1);
		had2.setIsValid(1);
		had2.setAccumulatedTime(0);
		entityMgr.persistAndFlush(had2);
		
		
		//when
		List<ScheduleHad> founds =  schHadRepo.findByScheduleId(3L);
		
		//then
		assertThat(founds.size()).isEqualTo(2);

		for(ScheduleHad found : founds){
			assertThat(found.getScheduleId())
			.isEqualTo(had.getScheduleId());
		}
		
		assertThat(founds.get(0).getScheduleHadId())
			.isNotEqualTo(founds.get(1).getScheduleHadId());
	}
}
