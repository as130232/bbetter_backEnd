package com.future.bbetter.achievement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.achievement.model.AchievementHad;
import com.future.bbetter.achievement.model.AchievementType;
import com.future.bbetter.member.model.Member;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AchievementHadRepositoryTest {
	
	@Autowired
	private TestEntityManager entityMgr;
	
	@Autowired
	private AchievementHadRepository achHadRepo;
	
	Member john;
	AchievementType test_type;
	
	@Before
	public void setup() {
		john = new Member();
		john.setName("john");
		john.setEmail("john@gmail.com");
		john.setPassword("password");
		john.setGender(1);
		john.setAddress("North Korea");
		john.setMoney(new BigDecimal(100));
		entityMgr.persistAndFlush(john);
		
		test_type = new AchievementType("test_achievement_type");
		entityMgr.persistAndFlush(test_type);
	}
	
	@Test
	public void whenFindByMember_thenReturnOneRecord() {
		//given
		assertThat(john).isNotNull();
		assertThat(test_type).isNotNull();
		
		AchievementHad had = new AchievementHad();
		had.setAchievementType(test_type);
		had.setMember(john);
		had.setGetdate(new Date());
		entityMgr.persist(had);

		//when
		Long johnId = john.getMemberId();
		List<AchievementHad> founds = achHadRepo.findByMemberId(johnId);
		
		//then
		assertThat(founds).isNotNull();
		assertThat(founds.size()).isEqualTo(1);
		assertThat(founds.get(0).getAchievementHadId()).isEqualTo(had.getAchievementHadId());
		
	}
}
