package com.future.bbetter.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.future.bbetter.schedule.model.ScheduleHad;

@Repository
public interface ScheduleHadRepository extends JpaRepository<ScheduleHad, Long> , JpaSpecificationExecutor<ScheduleHad>{

}
