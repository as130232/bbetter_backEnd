package com.future.bbetter.schedule.dto;

import java.util.Date;
import java.util.Optional;

import com.future.bbetter.schedule.model.CycleRule;
import com.future.bbetter.schedule.model.Schedule;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class CycleRuleDto {
	
	private Long cycleRuleId;
	private ScheduleDto scheduleDto;
	private int period;
	private int timePoint;
	private int isValid;
	private Date createdate;
	private Date updatedate;
	
	/**
	 * 將參數data對應的屬性轉換成dto
	 * 若參數內的Schedule為null,則轉換後的dto內的scheduleDto為新產生且無值的dto
	 * @author alfred <alfreadx@gmail.com>
	 * @date 2017年11月30日 下午10:31:38
	 * @param data
	 * @return CycleRuleDto 
	 */
	public static CycleRuleDto from(@NonNull CycleRule data) {
		CycleRuleDto dto = new CycleRuleDto();
		dto.setCycleRuleId(data.getCycleRuleId());
		dto.setPeriod(data.getPeriod());
		dto.setTimePoint(data.getTimePoint());
		dto.setIsValid(data.getIsValid());
		dto.setCreatedate(data.getCreatedate());
		dto.setUpdatedate(data.getUpdatedate());
		
		Optional<Schedule> opts = Optional.ofNullable(data.getSchedule());
		ScheduleDto sDto = ScheduleDto.from(opts.orElse(new Schedule()));
		dto.setScheduleDto(sDto);
		
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
		entity.setSchedule(this.getScheduleDto().toEntity());
		return entity;
	}
}
