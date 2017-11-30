package com.future.bbetter.achievement.resource.impl;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.future.bbetter.achievement.dto.AchievementHadDto;
import com.future.bbetter.achievement.model.AchievementHad;
import com.future.bbetter.achievement.model.AchievementType;
import com.future.bbetter.achievement.repository.AchievementHadRepository;
import com.future.bbetter.achievement.repository.AchievementTypeRepository;
import com.future.bbetter.achievement.resource.AchievementHadResource;
import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.repository.MemberRepository;

import lombok.NonNull;

@Repository
public class AchievementHadResourceImpl implements AchievementHadResource{
	
	@Autowired
	private AchievementHadRepository achHadRepo;
	
	@Autowired
	private AchievementTypeRepository achTypeRepo;
	
	@Autowired
	private MemberRepository memRepo;

	@Override
	public AchievementHadDto addAchievementHad(@NonNull Long memberId,
			@NonNull Integer achievementTypeId) {
		AchievementType type = achTypeRepo.findById(achievementTypeId)
				.orElseThrow(()->
					new DataNotFoundException("It can't found AchievementType data for id:" + achievementTypeId));
		Member mem = memRepo.findById(memberId)
				.orElseThrow(()->
					new DataNotFoundException("It can't found Member data for id:" + memberId));
		AchievementHad data = new AchievementHad(type,mem,new Date());
		AchievementHad afterData = achHadRepo.save(data);
		return AchievementHadDto.from(afterData);
	}

	@Override
	public void updateAchievementHad(@NonNull AchievementHadDto data) throws InsertOrUpdateDataFailureException {
		if(data.getMemberDto() == null)	throw new 
			InsertOrUpdateDataFailureException("It can't found member data in AchievementHadDto: " + data);
		
		Integer id = data.getAchievementHadId();
		Optional<AchievementHad> optData = achHadRepo.findById(id);
		if(optData.isPresent()) {
			AchievementHad dataInDb = optData.get();
			dataInDb.setAchievementType(data.getAchievementType());
			dataInDb.setGetdate(data.getGetdate());
			dataInDb.setMember(data.getMemberDto().toEntity());
			achHadRepo.save(dataInDb);
		}
	}

	@Override
	public void deleteAchievementHad(Integer achievementHadId) {
		achHadRepo.deleteById(achievementHadId);
	}

	@Override
	public List<AchievementHadDto> getAchievementHadsByMemberId(@NonNull Long memberId) {
		List<AchievementHad> result = achHadRepo.findByMemberId(memberId);
		return result.stream()
				.map(data -> AchievementHadDto.from(data))
				.collect(toList());
	}

}
