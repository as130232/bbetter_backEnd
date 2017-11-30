package com.future.bbetter.achievement.dto;

import java.util.Date;

import com.future.bbetter.achievement.model.AchievementHad;
import com.future.bbetter.achievement.model.AchievementType;
import com.future.bbetter.member.dto.MemberDto;
import com.future.bbetter.member.model.Member;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AchievementHadDto {
	private Integer achievementHadId;
	private AchievementType achievementType;
	private MemberDto memberDto;
	private Date getdate;
	
	
	/**
	 * 將entity轉換成dto的類別方法,若member屬性無法轉換成memberDto,則該屬性設定為null.
	 * @param data 從entity來的值
	 * @return AchievementHadDto 若member屬性無法轉換成memberDto,則設定為空.
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午3:20:01
	 */
	public static AchievementHadDto from(@NonNull AchievementHad data) {
		AchievementHadDto dto = new AchievementHadDto();
		dto.setAchievementHadId(data.getAchievementHadId());
		dto.setAchievementType(data.getAchievementType());
		dto.setGetdate(data.getGetdate());
		MemberDto memberDto = data.getMember() != null ? 
				MemberDto.from(data.getMember()) :
					null ;
		dto.setMemberDto(memberDto);
		return dto;
	}
	
	/**
	 * 將非null的屬性轉換成entity
	 * @return AchievementHad 
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午3:26:53
	 */
	public AchievementHad toEntity() {
		AchievementHad entity = new AchievementHad();
		entity.setAchievementHadId(this.getAchievementHadId());
		entity.setAchievementType(this.getAchievementType());
		entity.setGetdate(this.getGetdate());
		Member member = this.getMemberDto() != null ?
				this.getMemberDto().toEntity() :
					null;
		entity.setMember(member);
		return entity;
	}
}
