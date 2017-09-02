package com.future.bbetter.authentication.service;

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
    	//可取得用戶登入時的資訊
        WebAuthenticationDetails wad = (WebAuthenticationDetails) authentication.getDetails();
        String userIPAddress = wad.getRemoteAddress();
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("userIPAddress: " + userIPAddress + " , username: " + username + " , password: " + password);
 
        //檢查該IP是否被攔截(登入失敗超過三次將會記錄該IP，一分鐘內防止其再登入)
        if(loginAttemptService.isBlocked(userIPAddress)) {
            throw new LockedException("This IP has been blocked");
        }
        
        //載入用戶資訊
        UserDetails user = myUserDetailsService.loadUserByUsername(username);
        if(user == null){
            throw new BadCredentialsException("User Email not found.");
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