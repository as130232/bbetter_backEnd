package com.future.bbetter.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.future.bbetter.schedule.model.ScheduleHad;

@Repository
public interface ScheduleHadRepository extends JpaRepository<ScheduleHad, Long> , JpaSpecificationExecutor<ScheduleHad>{

	@Query(value = "select sHad from ScheduleHad sHad where sHad.schedule.scheduleId = ?1" )
	public List<ScheduleHad> findByScheduleId(Long scheduleId);
	
	@Query(value = "select sHad from ScheduleHad sHad where sHad.scheduleOwner.registrantId = ?1" )
	public List<ScheduleHad> findByMemberId(Long memberId);
	
	@Query(value = "select sHad from ScheduleHad sHad where sHad.scheduleOwner.scheduleOwnerId = ?1 and isValid = ?2")
	public List<ScheduleHad> findByScheduleOwnerIdAndIsValid(Long scheduleOwnerId, Integer isValid);
}
