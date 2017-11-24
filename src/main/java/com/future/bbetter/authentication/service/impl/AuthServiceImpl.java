package com.future.bbetter.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.future.bbetter.authentication.service.AuthService;
import com.future.bbetter.exception.customize.ValidateFailureException;
import com.future.bbetter.member.dto.MemberDto;
import com.future.bbetter.member.resource.MemberResource;
import com.future.bbetter.member.service.MemberService;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MemberService memberService;
	@Autowired
    private MemberResource memberResource;
	
	/**
	 * 登入
	 * @author Charles
	 * @date 2017年9月17日 下午1:57:28
	 * @param email
	 * @param password
	 * @return void
	 */
	@Override
	public void login(Long memberId, String password) throws AuthenticationException{
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(memberId, password);
        //驗證交由MyAuthenticationProvider執行
        Authentication authentication = authenticationManager.authenticate(upToken);
        //SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	/**
	 * 註冊
	 * @author Charles
	 * @date 2017年9月17日 下午1:57:28
	 * @param MemberDto
	 * @return void
	 */
	@Override
	public void register(MemberDto memberDto){
		//錯誤檢查
		List<String> errorList = memberService.checkAddUser(memberDto);
		if(!errorList.isEmpty()) {
			throw new ValidateFailureException(errorList.toString());
		}else {
			memberResource.addMember(memberDto);
		}
	}
	

	@Override
	public void refresh(String oldToken) {
		// TODO Auto-generated method stub
	}
	
}
