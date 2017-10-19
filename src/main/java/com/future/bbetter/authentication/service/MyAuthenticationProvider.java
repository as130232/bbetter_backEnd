package com.future.bbetter.authentication.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.future.bbetter.authentication.password.Password;

/**
 * 可以自行實作AuthenticationProvider定義認證方式
 * 一般Spring Security 默認使用 DaoAuthenticationProvider
 * @author Charles
 * @date 2017年9月1日 下午11:12:37
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private LoginAttemptService loginAttemptService;
    
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
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

		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        //載入用戶資訊
        UserDetails user = myUserDetailsService.loadUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User ID not found.");
        }
        
        //前台用戶輸入的密碼與數據庫儲存用戶密碼(加密過)，驗證是否正確
        if (!Password.encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        
        //取得該用戶的權限(myUserDetailsService中定義賦予的權限)
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        //將用戶放入Authentication物件，代表已通過驗證
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    public boolean supports(Class<?> authentication) {
        return true;
    }
}