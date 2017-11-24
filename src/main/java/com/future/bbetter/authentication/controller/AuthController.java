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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.future.bbetter.authentication.jwt.TokenProvider;
import com.future.bbetter.authentication.model.AuthResponse;
import com.future.bbetter.authentication.service.AuthService;
import com.future.bbetter.authentication.service.thirdpartauth.FacebookAuthService;
import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;
import com.future.bbetter.exception.customize.ThirdVerificationException;
import com.future.bbetter.exception.customize.ValidateFailureException;
import com.future.bbetter.member.constant.THIRD_PART_AUTH;
import com.future.bbetter.member.dto.MemberDto;
import com.future.bbetter.member.resource.MemberResource;
import com.future.bbetter.member.resource.ThirdPartAuthResource;


@CrossOrigin
@RestController
public class AuthController {
	@Autowired
	private TokenProvider tokenProvider;
    @Autowired
    private AuthService authService;
    @Autowired
    private FacebookAuthService facebookAuthService;
    
    @Autowired
    private ThirdPartAuthResource thirdPartAuthResource;
    @Autowired
    private MemberResource memberResource;

    @GetMapping("/authenticate")
    @CrossOrigin
    public void authenticate() {
    	// we don't have to do anything here
    	// this is just a secure endpoint and the JWTFilter
    	// validates the token
    	// this service is called at startup of the app to check 
    	// if the jwt token is still valid
    }
    
    /**
	 * 登入
	 * @author Charles
	 * @date 2017年9月16日 下午8:45:18
	 */
	@PostMapping("/login")
	public String login(@Valid @RequestBody MemberDto authMemberDto,
			HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, DataNotFoundException{
		MemberDto memberDTO = memberResource.getMember(authMemberDto.getEmail());
		authService.login(memberDTO.getMemberId(), authMemberDto.getPassword());
        //成功登入回傳token
		final String token = tokenProvider.createToken(memberDTO.getMemberId().toString());
		return token;
	}
	
	/**
	 * 檢查該email是否已被註冊
	 * @author Charles
	 * @date 2017年11月7日 下午11:30:38
	 */
	@GetMapping(value = "/public/checkIsEmailRegistered")	
	public boolean checkIsEmailRegistered(@RequestParam String email){
		boolean isEmailRegistered = false;
		if(memberResource.checkIsEmailExist(email)) {
			isEmailRegistered = true;
		}
		return isEmailRegistered;
	}
	
	/**
	 * 註冊
	 * 只處理要求 Content-Type標頭為"application/json"的POST請求
	 * @author Charles
	 * @date 2017年9月16日 下午8:45:18
	 */
	@PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)	
	@ResponseStatus(HttpStatus.CREATED)	//201:除了回傳OK，告訴客戶端該次也產生新的資源
	public String signup(@RequestBody MemberDto memberDto) throws ValidateFailureException, InsertOrUpdateDataFailureException{
		authService.register(memberDto);
		return tokenProvider.createToken(memberDto.getEmail());
	}
	
	/**
	 * Facebook登入
	 * 前端會員登入後會接收facebook回傳的userID、accessToken，將此資訊傳遞給後端後，後台藉由此token判斷是否為該會員(userID)
	 * 若正確且狀態為連線中，表示為合法會員
	 * 進一步判斷是否擁有該會員，若有回傳自定義token，若無則註冊該會員
	 * @author Charles
	 * @date 2017年9月16日 下午8:45:18
	 */
	@PostMapping("/signin/facebook")
	public String loginWithFb(@RequestBody AuthResponse authResponse) throws ThirdVerificationException, InsertOrUpdateDataFailureException{
		facebookAuthService.login(authResponse);
		Integer source = THIRD_PART_AUTH.SOURCE_FACEBOOK.value;
		Long memberId = thirdPartAuthResource.getMemberId(authResponse.getUserID(), source);
		return tokenProvider.createToken(memberId.toString());
	}
	
}
