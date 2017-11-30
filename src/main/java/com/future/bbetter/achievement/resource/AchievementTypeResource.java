package com.future.bbetter.achievement.resource;

import com.future.bbetter.achievement.dto.AchievementTypeDto;

public interface AchievementTypeResource {

	/**
	 * 新增一筆AchievementType
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午6:40:05
	 * @param name
	 * @return AchievementTypeDto 內含新增完資料的id
	 */
	AchievementTypeDto addAchievementType(String name);
	
	/**
	 * 更新一筆AchievementType
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午6:44:00
	 * @param data
	 * @return void 
	 */
	void updateAchievementType(AchievementTypeDto data);
	
	/**
	 * 刪除一筆AchievementType,應先刪除所有外鍵的資料,在刪除該筆資料.
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午6:46:12
	 * @param id AchievementType主鍵
	 * @return void 
	 */
	void deleteAchievementType(Integer id);
	
	/**
	 * 取得一筆AchievementType資料,若在資料庫找不到該筆id的資料則丟出{@link DataNotFoundException}
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午6:47:58
	 * @throws DataNotFoundException 若找不到id的資料則丟出DataNotFoundException
	 * @param id
	 * @return AchievementTypeDto 
	 */
	AchievementTypeDto getAchievementType(Integer id);
	
}
