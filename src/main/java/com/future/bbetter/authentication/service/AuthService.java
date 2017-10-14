package com.future.bbetter.authentication.service;

import org.springframework.security.core.AuthenticationException;

import com.future.bbetter.exception.customize.ValidateFailException;
import com.future.bbetter.member.dto.MemberDTO;

public interface AuthService {
	void register(MemberDTO memberDTO)  throws ValidateFailException;
    void login(String email, String password) throws AuthenticationException;
    void refresh(String oldToken);
}
