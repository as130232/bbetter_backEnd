package com.future.bbetter.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.future.bbetter.schedule.model.ScheduleRemind;

@Repository
public interface ScheduleRemindRepository extends JpaRepository<ScheduleRemind, Long> , JpaSpecificationExecutor<ScheduleRemind>{

	public List<ScheduleRemind> findByScheduleId(Long scheduleId);
}
