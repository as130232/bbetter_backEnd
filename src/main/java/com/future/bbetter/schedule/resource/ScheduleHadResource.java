package com.future.bbetter.schedule.resource;

import java.util.List;

import com.future.bbetter.schedule.dto.ScheduleHadDTO;

public interface ScheduleHadResource {
	
	/**
	 * 取得該行程擁有者擁有的行程資訊
	 * @author Charles
	 * @date 2017年10月22日 上午11:48:32
	 * @param scheduleHadId
	 * @throws exceptionType 如果...將拋出exceptionType異常.
	 * @return ScheduleHadDTO
	 */
	public ScheduleHadDTO getScheduleHad(Long scheduleHadId);
	
	/**
	 * 取得該行程擁有者所有的行程
	 * @author Charles
	 * @date 2017年10月22日 下午3:43:09
	 * @param scheduleOwnerId
	 * @return List<ScheduleHadDTO>
	 */
	public List<ScheduleHadDTO> getScheduleHads(Long scheduleOwnerId);
	
	/**
	 * 該行程擁有者新增一筆關聯行程的擁有行程，並將新增資訊回傳
	 * @author Charles
	 * @date 2017年10月21日 下午4:36:26
	 * @param registrantId
	 * @param source
	 * @return boolean
	 */
	public ScheduleHadDTO addScheduleHad(Long scheduleOwnerId, Long scheduleId, Integer authority);
}
