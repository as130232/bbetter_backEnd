package com.future.bbetter.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.future.bbetter.schedule.model.ScheduleHad;

@Repository
public interface ScheduleHadRepository extends JpaRepository<ScheduleHad, Long> , JpaSpecificationExecutor<ScheduleHad>{

	public List<ScheduleHad> findByScheduleId(Long scheduleId);
	
	public List<ScheduleHad> findByMemberId(Long memberId);
}
