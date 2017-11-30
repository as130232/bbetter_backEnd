package com.future.bbetter.achievement.resource.impl;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.future.bbetter.achievement.dto.AchievementHadDto;
import com.future.bbetter.achievement.model.AchievementHad;
import com.future.bbetter.achievement.model.AchievementType;
import com.future.bbetter.achievement.resource.AchievementHadResource;
import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.member.dto.MemberDto;
import com.future.bbetter.member.model.Member;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(AchievementHadResourceImpl.class)
public class AchievementHadResourceImplTest {
	
	@Autowired
	private TestEntityManager entityMgr;
	
	@Autowired
	private AchievementHadResource achHadRs;
	
	Member john;
	AchievementType test_type;
	AchievementHad achievementByJohn;
	
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
		
		achievementByJohn = new AchievementHad();
		achievementByJohn.setAchievementType(test_type);
		achievementByJohn.setMember(john);
		achievementByJohn.setGetdate(new Date());
		entityMgr.persistAndFlush(achievementByJohn);
	}
	
	/***
	 * test addAchievementHad() method,
	 * 給予正常資料後應新增成功
	 */
	@Test
	public void whenAddAchievementHad_thenSuccess() {
		//given
		Long memberId = john.getMemberId();
		Integer typeId = test_type.getAchievementTypeId();
		
		//when
		AchievementHadDto result = achHadRs.addAchievementHad(memberId, typeId);
	
		//then
		assertThat(result).isNotNull();
		assertThat(result.getAchievementHadId()).isNotNull();
		assertThat(result.getAchievementType()).isEqualTo(test_type).isNotNull();
		assertThat(result.getMemberDto().getMemberId())
			.isEqualTo(memberId).isNotNull();
	}

	/***
	 * test addAchievementHad() method,
	 * 給予找不到資料庫的memberId,應丟出DataNotFoundException
	 */
	@Test
	public void whenAddAchievementHadAndMemberIdNotFound_thenThrowsException() {
		//given
		Long memberId = -99L;
		//確保該值在測試資料庫是找不到資料的
		assertThat(entityMgr.find(Member.class, memberId)).isNull();
		Integer typeId = test_type.getAchievementTypeId();
		
		//when
		Throwable thrown = catchThrowable(()->{
			achHadRs.addAchievementHad(memberId, typeId);
		});
		
		//then
		assertThat(thrown).isInstanceOf(DataNotFoundException.class)
			.hasMessageContaining("It can't found Member data for id:");
	}
	
	
	/***
	 * test addAchievementHad() method,
	 * 給予找不到資料庫的achievementTypeId,應丟出DataNotFoundException
	 */
	@Test
	public void whenAddAchievementHadAndAchievementTypeIdNotFound_thenThrowsException() {
		//given
		Long memberId = john.getMemberId();
		Integer typeId = -99;
		//確保該值在測試資料庫是找不到資料的
		assertThat(entityMgr.find(AchievementType.class, typeId)).isNull();
		
		//when
		Throwable thrown = catchThrowable(()->{
			achHadRs.addAchievementHad(memberId, typeId);
		});
		
		//then
		assertThat(thrown).isInstanceOf(DataNotFoundException.class)
			.hasMessageContaining("It can't found AchievementType data for id:");
	}
	
	/***
	 * test updateAchievementHad() method,
	 * 給予正常資料後應新增成功
	 */
	@Test
	public void whenUpdateAchievementHad_thenSuccess() {
		//given
		AchievementType newType = new AchievementType("new_test_type");
		entityMgr.persistAndFlush(newType);
		
		Integer hadId = achievementByJohn.getAchievementHadId();
		AchievementHadDto newHad = new AchievementHadDto();
		newHad.setAchievementHadId(hadId);
		newHad.setAchievementType(newType);
		newHad.setGetdate(new Date());
		newHad.setMemberDto(MemberDto.from(john));

		//when
		achHadRs.updateAchievementHad(newHad);
		
		//then
		AchievementHad found = entityMgr.find(AchievementHad.class, hadId);
		assertThat(found).isNotNull();
		assertThat(found.getAchievementHadId()).isEqualTo(hadId);
		assertThat(found.getAchievementType()).isEqualTo(newType);
		assertThat(found.getMember().getMemberId()).isEqualTo(john.getMemberId());
	}
	
	/***
	 * test updateAchievementHad() method,
	 * 給予參數AchievementHadDto當中的MemberDto為null,應丟出InsertOrUpdateDataFailureException
	 */
	@Test
	public void whenUpdateAchievementHadAndMemberDtoInArgumentIsNull_thenThrowsException() {
		//given
		entityMgr.detach(achievementByJohn);
		achievementByJohn.setMember(null);
		AchievementHadDto data = AchievementHadDto.from(achievementByJohn);
		
		//when
		Throwable thrown = catchThrowable(()->{
			achHadRs.updateAchievementHad(data);
		});
		
		//then
		assertThat(thrown).isInstanceOf(InsertOrUpdateDataFailureException.class)
			.hasMessageContaining("It can't found member data in AchievementHadDto: ");
	}
	
	/***
	 * test deleteAchievementHad() method,
	 * 給予正確資料應刪除成功且其他資料並沒有被刪除
	 */
	@Test
	public void whenDeleteAchievementHad_thenSuccessAndOtherRecordDidNotDelete() {
		//given
		Integer achievementTypeId = achievementByJohn.getAchievementType().getAchievementTypeId();
		Long johnId = john.getMemberId();
		Integer hadId = achievementByJohn.getAchievementHadId();
		
		//when
		achHadRs.deleteAchievementHad(hadId);
		
		//then
		AchievementHad found = entityMgr.find(AchievementHad.class, hadId);
		assertThat(found).isNull();
		AchievementType typeFound = entityMgr.find(AchievementType.class, achievementTypeId);
		assertThat(typeFound).isNotNull();
		Member memFound = entityMgr.find(Member.class, johnId);
		assertThat(memFound).isNotNull();
	}
	
	/***
	 * test getAchievementHadsByMemberId() method.
	 * 給予正確的參數資料應可回傳含一筆資料的集合
	 */
	@Test
	public void whenGetAchievementHadByMemberId_thenSuccess() {
		//given
		Long memberId = john.getMemberId();

		//when
		List<AchievementHadDto> results = achHadRs.getAchievementHadsByMemberId(memberId);
		
		//then
		assertThat(results).isNotNull();
		assertThat(results.size()).isEqualTo(1);
		assertThat(results.get(0).
				getMemberDto()
				.getMemberId())
			.isEqualTo(memberId);
	}
	
	/***
	 * test getAchievementHadsByMemberId() method.
	 * 給予資料庫找不到資料的會員id應回傳零資料的集合.
	 */
	@Test
	public void whenGetAchievementHadByMemberIdAndMemberIdNotFound_thenReturnZeroRecord() {
		//given
		Long memberId = -99L;
		//確保該值在測試資料庫是找不到資料的
		assertThat(entityMgr.find(Member.class, memberId)).isNull();
		
		//when
		List<AchievementHadDto> results = achHadRs.getAchievementHadsByMemberId(memberId);
		
		//then
		assertThat(results).isNotNull();
		assertThat(results.size()).isEqualTo(0);
	}
}
