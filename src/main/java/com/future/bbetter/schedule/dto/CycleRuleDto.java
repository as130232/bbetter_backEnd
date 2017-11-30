package com.future.bbetter.schedule.dto;

import java.util.Date;

import com.future.bbetter.schedule.model.CycleRule;
import com.future.bbetter.schedule.model.Schedule;
import com.future.bbetter.schedule.model.ScheduleHad;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CycleRuleDto {
	
	private Long cycleRuleId;
	private ScheduleHadDto scheduleHadDto;
	private int period;
	private int timePoint;
	private int isValid;
	private Date createdate;
	private Date updatedate;
	
	/**
	 * 將參數data對應的屬性轉換成dto
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月30日 下午10:31:38
	 * @param data
	 * @throws NullPointerException 若參數內的ScheduleHad為null,及其內的Schedule為null,則丟出.
	 * @return CycleRuleDto 
	 */
	public static CycleRuleDto from(CycleRule data) {
		CycleRuleDto dto = new CycleRuleDto();
		dto.setCycleRuleId(data.getCycleRuleId());
		dto.setPeriod(data.getPeriod());
		dto.setTimePoint(data.getTimePoint());
		dto.setIsValid(data.getIsValid());
		dto.setCreatedate(data.getCreatedate());
		dto.setUpdatedate(data.getUpdatedate());
		
		ScheduleHad had = data.getScheduleHad() != null ? data.getScheduleHad() : null ;
		Schedule s = had.getSchedule() != null ? had.getSchedule() : null;
		ScheduleDto sDto = ScheduleDto.from(s);
		dto.setScheduleHadDto(ScheduleHadDto.from(had, sDto));
		
		return dto;
	}

	/**
	 * 將目前的dto物件轉換成對應的entity
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月30日 下午10:37:37
	 * @return CycleRule 
	 */
	public CycleRule toEntity() {
		CycleRule entity = new CycleRule();
		entity.setCycleRuleId(this.getCycleRuleId());
		entity.setPeriod(this.getPeriod());
		entity.setTimePoint(this.getTimePoint());
		entity.setIsValid(this.getIsValid());
		entity.setCreatedate(this.getCreatedate());
		entity.setUpdatedate(this.getUpdatedate());
		entity.setScheduleHad(this.getScheduleHadDto().toEntity());
		return entity;
	}
}
