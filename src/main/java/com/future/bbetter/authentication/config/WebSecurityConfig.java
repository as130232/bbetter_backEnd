package com.future.bbetter.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.future.bbetter.authentication.service.MyAuthenticationProvider;
import com.future.bbetter.authentication.service.MyUserDetailsService;


/**
 * @EnableWebSecurity啟用Web安全功能(即為Enable SpringSecurityFilterChain)
 * 設定網頁存取權限，http物件就是負責這部分，它是一個builder pattern，
 * 目前先呼叫anonymous()使任何人都可以存取網頁
 * @author Charles
 * @date 2017年9月1日 下午10:08:02
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private MyAuthenticationProvider myAuthenticationProvider;

	/**
	 * 配置如何通過攔截器保護請求
	 * @author Charles
	 * @date 2017年9月1日 下午10:12:37
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		//啟用默認的登錄頁面
		.formLogin()
		.and()
		.authorizeRequests()
		.antMatchers("/login").authenticated()
		.anyRequest().permitAll();
//		.and()
//		.loginPage("/login/login.html");
//		.loginPage("/login");
//		http.csrf().disable();
		
	}

	/**
	 * 配置user-detail、身份驗證，用於注入自定義身分驗證和密碼校驗規則
	 * @author Charles
	 * @date 2017年9月1日 下午10:12:37
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//加載用戶信息
		auth.userDetailsService(myUserDetailsService);
		//加載授權信息
		auth.authenticationProvider(myAuthenticationProvider);

		//自行定義創建使用者，用於測試
		auth.inMemoryAuthentication()
		.withUser("user").password("0000").roles("USER")
		.and()
		.withUser("admin").password("0000").roles("USER","ADMIN");
	}
	
	/**
	 * Web層面的配置，一般用来配無需要安全檢查的路徑
	 * @author Charles
	 * @date 2017年9月1日 下午10:12:37
	 */
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/js/**", "/css/**", "/images/**", "/**/favicon.ico");
    }
	
	/**
	 * 加載自行定義的用戶信息
	 * @author Charles
	 * @date 2017年9月2日 下午2:32:28
	 * @return UserDetailsService
	 */
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return myUserDetailsService;
	}
}