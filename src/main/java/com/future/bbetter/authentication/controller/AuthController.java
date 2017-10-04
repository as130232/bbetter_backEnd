package com.future.bbetter.authentication.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.future.bbetter.authentication.jwt.TokenProvider;
import com.future.bbetter.authentication.service.AuthService;
import com.future.bbetter.exception.customize.ValidateFailException;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.model.MemberDTO;


@CrossOrigin
@RestController
public class AuthController {

	@Autowired
	private TokenProvider tokenProvider;
    @Autowired
    private AuthService authService;
    

    @GetMapping("/authenticate")
    @CrossOrigin
    public void authenticate() {
    	// we don't have to do anything here
    	// this is just a secure endpoint and the JWTFilter
    	// validates the token
    	// this service is called at startup of the app to check 
    	// if the jwt token is still valid
    	System.out.println("test");
    }
    
    @GetMapping("/secret")
	@CrossOrigin
	public String secretService() {
		return "A secret message";
	}
    /**
	 * 登入
	 * @author Charles
	 * @date 2017年9月16日 下午8:45:18
	 */
	@PostMapping("/login")
	public String authorize(@Valid @RequestBody Member loginMember,
			HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
		authService.login(loginMember.getEmail(), loginMember.getPassword());
        //成功登入回傳token
		final String token = tokenProvider.createToken(loginMember.getEmail());
		return token;
	}
	
	/**
	 * 註冊
	 * 只處理要求 Content-Type標頭為"application/json"的POST請求
	 * @author Charles
	 * @date 2017年9月16日 下午8:45:18
	 */
	@PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)	
	@ResponseStatus(HttpStatus.CREATED)	//201:除了回傳OK，告訴客戶端該次也產生新的資源
	public String signup(@RequestBody MemberDTO memberDTO) throws ValidateFailException{
		authService.register(memberDTO);
		return tokenProvider.createToken(memberDTO.getEmail());
	}
}
