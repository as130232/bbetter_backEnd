package com.future.bbetter.schedule.resource;

import com.future.bbetter.schedule.dto.ScheduleOwnerDto;
import com.future.bbetter.schedule.model.ScheduleRegistrant;

public interface ScheduleOwnerResource {
	
	/**
	 * 新增一位行程擁有者
	 * @author Charles
	 * @date 2017年10月21日 下午4:36:26
	 * @param registrantId
	 * @param source
	 * @return ScheduleOwnerDTO
	 */
	public ScheduleOwnerDto addScheduleOwner(Long registrantId, Integer source);

	/**
	 * 取得行程擁有者
	 * @author Charles
	 * @date 2017年10月21日 下午9:58:53
	 * @param registrantId(memberId..)
	 * @param source
	 * @return ScheduleOwnerDTO
	 */
	public ScheduleOwnerDto getScheduleOwner(Long scheduleOwnerId);
	public ScheduleOwnerDto getScheduleOwner(Long registrantId, Integer source);
	
	/**
	 * 取得行程擁有者ID
	 * @author Charles
	 * @date 2017年10月21日 下午6:24:59
	 * @param registrantId(memberId..)
	 * @param source
	 * @return Long
	 */
	public Long getScheduleOwnerId(Long registrantId, Integer source);
	
	/**
	 * 根據來源判斷該註冊者ID為哪種身份類型，並取得對應身份類型的行程註冊者資訊
	 * @author Charles
	 * @date 2017年10月22日 上午10:57:32
	 * @param registrantId
	 * @param source
	 * @return ScheduleRegistrant
	 */
	public ScheduleRegistrant getScheduleRegistrant(Long registrantId, Integer source);
	
	/**
	 * 檢查是否已註冊該行程擁有者
	 * @author Charles
	 * @date 2017年10月21日 下午4:36:26
	 * @param registrantId
	 * @param source
	 * @return boolean
	 */
	public boolean checkIsScheduleOwnerRegister(Long registrantId, Integer source);
	
	

}
