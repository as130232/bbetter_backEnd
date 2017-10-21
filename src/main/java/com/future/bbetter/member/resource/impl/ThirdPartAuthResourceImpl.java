package com.future.bbetter.member.resource.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.member.model.ThirdPartAuth;
import com.future.bbetter.member.repository.ThirdPartAuthRepository;
import com.future.bbetter.member.resource.ThirdPartAuthResource;

@Service
public class ThirdPartAuthResourceImpl implements ThirdPartAuthResource{
	
	@Autowired
	private ThirdPartAuthRepository thirdPartAuthRepository;
	
	@Override
	public Long getMemberId(String providerId, Integer source) {
		ThirdPartAuth thirdPartAuth = thirdPartAuthRepository.findByProviderIdAndSource(providerId, source);
		return thirdPartAuth.getMemberId();
	}
	
	@Override
	public boolean checkIsExist(String providerId, Integer source) {
		boolean isExist = false;
		ThirdPartAuth thirdPartAuth = thirdPartAuthRepository.findByProviderIdAndSource(providerId, source);
		if(thirdPartAuth != null) {
			isExist = true;
		}
		return isExist;
	}
	
	@Override
	public void addThirdPartAuth(Long memberId, String providerId, Integer source) {
		boolean isExist = this.checkIsExist(providerId, source);
		if(isExist) {
			throw new InsertOrUpdateDataFailureException("該第三方帳戶已被註冊！");
		}
		this.insert(memberId, providerId, source);
	}
	
	private void insert(Long memberId, String providerId, Integer source) {
		ThirdPartAuth thirdPartAuth = new ThirdPartAuth();
		thirdPartAuth.setMemberId(memberId);
		thirdPartAuth.setProviderId(providerId);
		thirdPartAuth.setSource(source);
		thirdPartAuthRepository.saveAndFlush(thirdPartAuth);
	}



	

}
