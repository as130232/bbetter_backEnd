package com.future.bbetter.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.future.bbetter.schedule.model.ScheduleType;

@Repository
public interface ScheduleTypeRepository extends JpaRepository<ScheduleType, Integer> , JpaSpecificationExecutor<ScheduleType> {

}
