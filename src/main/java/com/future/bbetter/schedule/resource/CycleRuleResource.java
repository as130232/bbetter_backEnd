package com.future.bbetter.schedule.resource;

import java.util.List;
import java.util.Optional;

import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.schedule.dto.CycleRuleDto;

public interface CycleRuleResource {

	/**
	 * 新增一筆資料,dto參數內應含有schedulehaddto資料(不為null)
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年12月9日 下午1:57:26
	 * @param dto 
	 * @throws InsertOrUpdateDataFailureException 若參數內的ScheduleHadDto為null,則拋出
	 * @return CycleRuleDto 回傳含有該筆id的物件
	 */
	CycleRuleDto addCycleRule(CycleRuleDto dto);
	
	
	/**
	 * 更新一筆資料,dto參數內應含有schedulehaddto資料(不為null)
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年12月9日 下午1:57:26
	 * @param dto
	 * @throws InsertOrUpdateDataFailureException 若參數內的ScheduleHadDto為null,則拋出
	 */
	void updateCycleRule(CycleRuleDto dto);
	
	
	/**
	 * 刪除一筆資料
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年12月9日 下午1:57:26
	 * @param id CycleRule的id
	 */
	void deleteCycleRule(Long id);
	
	
	/**
	 * 取得一筆資料
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年12月9日 下午1:57:26
	 * @param id CycleRule的id
	 * @throws DataNotFoundException 若資料庫沒有該筆資料則丟出
	 * @return CycleRuleDto 該筆資料
	 */
	CycleRuleDto getCycleRule(Long id);
	
	
	/**
	 * 取得一筆資料,Optional版本,若找不到資料不丟出例外
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年12月9日 下午1:57:26
	 * @param id CycleRule的id
	 * @return Optional<CycleRuleDto> 回傳的資訊有可能為null,取得時可先檢查
	 */
	Optional<CycleRuleDto> getCycleRuleForOptional(Long id);
	
	
	/**
	 * 給予ScheduleHadId取得對應資料
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年12月9日 下午1:57:26
	 * @param scheduleHadId ScheduleHad的id
	 * @return List<CycleRuleDto> CycleRuleDto集合,若找不到則回傳emptyList (not null)
	 */
	List<CycleRuleDto> getCycleRulesByScheduleHadId(Long scheduleHadId);
	
}
