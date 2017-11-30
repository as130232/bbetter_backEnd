package com.future.bbetter.achievement.dto;

import com.future.bbetter.achievement.model.AchievementType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AchievementTypeDto {
	
	private Integer achievementTypeId;
	private String name;
	
	
	/**
	 * 將entity轉換成dto的類別方法
	 * @param data achievementType entity
	 * @return AchievementTypeDto 
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午3:36:53
	 */
	public static AchievementTypeDto from(AchievementType data) {
		AchievementTypeDto dto = new AchievementTypeDto();
		dto.setAchievementTypeId(data.getAchievementTypeId());
		dto.setName(data.getName());
		return dto;
	}

	
	public AchievementType toEntity() {
		AchievementType entity = new AchievementType();
		entity.setAchievementTypeId(this.getAchievementTypeId());
		entity.setName(this.getName());
		return entity;
	}
}
