package com.future.bbetter.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.future.bbetter.schedule.model.ScheduleSubType;

@Repository
public interface ScheduleSubTypeRepository extends JpaRepository<ScheduleSubType, Integer> , JpaSpecificationExecutor<ScheduleSubType> {

}
