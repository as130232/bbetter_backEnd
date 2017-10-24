package com.future.bbetter.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.future.bbetter.member.model.ThirdPartAuth;

public interface ThirdPartAuthRepository extends JpaRepository<ThirdPartAuth, Long> , JpaSpecificationExecutor<ThirdPartAuth>{
	public ThirdPartAuth findByProviderIdAndSource(String userId, Integer source);
}
