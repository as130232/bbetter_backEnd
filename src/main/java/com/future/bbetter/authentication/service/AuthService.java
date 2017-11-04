package com.future.bbetter.authentication.service;

import org.springframework.security.core.AuthenticationException;

import com.future.bbetter.exception.customize.ValidateFailureException;
import com.future.bbetter.member.dto.MemberDto;

public interface AuthService {
	void register(MemberDto memberDto)  throws ValidateFailureException;
    void login(Long memberId, String password) throws AuthenticationException;
    void refresh(String oldToken);
}
