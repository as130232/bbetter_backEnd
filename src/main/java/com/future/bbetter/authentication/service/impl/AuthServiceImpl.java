package com.future.bbetter.authentication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.future.bbetter.authentication.service.AuthService;
import com.future.bbetter.exception.customize.ValidateFailException;
import com.future.bbetter.member.dto.MemberDTO;
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
	public void login(String email, String password) throws AuthenticationException{
		/*
        UserDetails user = myUserDetailsService.loadUserByUsername(email);
          //檢查該會員是否登入超過失敗次數(若登入失敗超過次數將會記錄該IP，十分鐘內防止其再登入)
  		String hostAddress = "";
  		try {
  			InetAddress ip = InetAddress.getLocalHost();
  			hostAddress = ip.getHostAddress();
  		} catch (UnknownHostException e) {}
  		if(loginAttemptService.isBlocked(hostAddress)) {
  			//寄email，表示有人嘗試登入，且失敗，提醒用戶更改密碼
  			throw new LockedException("This IP has been blocked. Please wait a moment.");
  		}
        //前台用戶輸入的密碼與數據庫儲存用戶密碼(加密過)，驗證是否正確
        if (!Password.encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        */
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(email, password);
        //驗證交由MyAuthenticationProvider執行
        Authentication authentication = authenticationManager.authenticate(upToken);
        //SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	/**
	 * 註冊
	 * @author Charles
	 * @date 2017年9月17日 下午1:57:28
	 * @param MemberDTO
	 * @return void
	 */
	@Override
	public void register(MemberDTO memberDTO) throws ValidateFailException{
		//錯誤檢查
		List<String> errorList = memberService.checkAddUser(memberDTO);
		if(!errorList.isEmpty()) {
			throw new ValidateFailException(errorList.toString());
		}else {
			memberResource.addMember(memberDTO);
		}
	}
	

	@Override
	public void refresh(String oldToken) {
		// TODO Auto-generated method stub
	}
	
}
