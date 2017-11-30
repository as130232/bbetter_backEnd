package com.future.bbetter.achievement.resource;

import java.util.List;

import com.future.bbetter.achievement.dto.AchievementHadDto;
import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;

public interface AchievementHadResource {

	/**
	 * 新增一筆該會員所擁有的成就資料,
	 * 若資料庫找不到參數所給的會員id及成就類型id資料將丟出{@link DataNotFoundException}
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午4:02:21
	 * @param memberId 會員id
	 * @param achievementTypeId 成就類型id
	 * @return AchievementHadDto 新增完成資料庫後傳回帶有AchievementHadId的dto
	 * @throws DataNotFoundException 
	 */
	public AchievementHadDto addAchievementHad(Long memberId, Integer achievementTypeId);
	
	/**
	 * 更新會有所擁有的成就資料,data內需有member資料,若無則丟出{@link InsertOrUpdateDataFailureException}
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午4:08:53
	 * @param data 
	 * @return void 
	 * @throws InsertOrUpdateDataFailureException 若data內無member資料則丟出
	 */
	public void updateAchievementHad(AchievementHadDto data) throws InsertOrUpdateDataFailureException;
	
	
	/**
	 * 刪除該筆擁有的成就資料
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午4:14:39
	 * @param achievementHadId 
	 * @return void 
	 */
	public void deleteAchievementHad(Integer achievementHadId);
	
	/**
	 * 取得該會員id所有的成就資料
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月29日 下午4:02:21
	 * @param memberId 會員id
	 * @return List<AchievementHadDto> 回傳符合的資料集
	 */
	public List<AchievementHadDto> getAchievementHadsByMemberId(Long memberId);
}
