package com.future.bbetter.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.future.bbetter.schedule.model.ScheduleOwner;

@Repository
public interface ScheduleOwnerRepository extends JpaRepository<ScheduleOwner, Long> , JpaSpecificationExecutor<ScheduleOwner>{
	
	@Query("select sOwner.scheduleOwnerId from ScheduleOwner sOwner where sOwner.registrantId = ?1 and sOwner.source = ?1")
	public Long findScheduleOwnerIdByRegistrantIdAndSource(Long registrantId, Integer source);
	public ScheduleOwner findByRegistrantIdAndSource(Long registrantId, Integer source);

}
