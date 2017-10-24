package com.future.bbetter.member.resource.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.member.model.ThirdPartAuth;
import com.future.bbetter.member.repository.ThirdPartAuthRepository;
import com.future.bbetter.member.resource.ThirdPartAuthResource;

@Service
public class ThirdPartAuthResourceImpl implements ThirdPartAuthResource{
	
	@Autowired
	private ThirdPartAuthRepository thirdPartAuthRepository;
	
	@Override
	public boolean checkIsExist(String userId, Integer source) {
		boolean isExist = false;
		ThirdPartAuth thirdPartAuth = thirdPartAuthRepository.findByProviderIdAndSource(userId, source);
		if(thirdPartAuth != null) {
			isExist = true;
		}
		return isExist;
	}
	
	@Override
	public Long getMemberId(String providerId, Integer source) {
		ThirdPartAuth thirdPartAuth = thirdPartAuthRepository.findByProviderIdAndSource(providerId, source);
		return thirdPartAuth.getMemberId();
	}
	
	@Override
	public void insert(Long memberId, String providerId, Integer source) {
		ThirdPartAuth thirdPartAuth = new ThirdPartAuth();
		thirdPartAuth.setMemberId(memberId);
		thirdPartAuth.setProviderId(providerId);
		thirdPartAuth.setSource(source);
		thirdPartAuthRepository.saveAndFlush(thirdPartAuth);
	}

	

}
