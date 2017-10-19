package com.future.bbetter.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.future.bbetter.authentication.jwt.JWTFilter;
import com.future.bbetter.authentication.jwt.JwtAuthenticationEntryPoint;
import com.future.bbetter.authentication.password.Password;
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
//開啟可設定該資源需要什麼權限角色(@PreAuthorize("")
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private MyAuthenticationProvider myAuthenticationProvider;
	@Autowired
	private JWTFilter jwtFilter;
	
	/**
	 * 配置如何通過攔截器保護請求
	 * @author Charles
	 * @date 2017年9月1日 下午10:12:37
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//使用spring security，若有跨域這裡需配置cors
			.cors()
			.and()
			//使用的是JWT，因此不需要csrf
			.csrf().disable()
			//當jwt驗證失敗時，作例外處理
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
			.and()
			//基於token，因此將session設置為STATELESS，防止Spring Security創建HttpSession對象
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			//.httpBasic() 	// this is optional, if you want to access 
			//.and()     	// the services from a browser
			//啟用默認的登錄頁面
			//.formLogin()
			.authorizeRequests()
		 	//可以訪問而不進行身份驗證
			.antMatchers("/login").permitAll()
			.antMatchers("/signup").permitAll()
			//第三方登入(facebook、google)
			.antMatchers("/signin/**").permitAll()
			.antMatchers("/public/**").permitAll()
			//其他端點將被保護並且需要有效的JWT秘鑰
			.anyRequest().authenticated();
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}


	/**
	 * 配置user-detail、身份驗證，用於注入自定義身分驗證和密碼校驗規則
	 * @author Charles
	 * @date 2017年9月1日 下午10:12:37
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			//加載授權信息
			.authenticationProvider(myAuthenticationProvider)
	        //加載用戶信息
	        .userDetailsService(myUserDetailsService)
	        //使用BCrypt進行密碼的hash
	        .passwordEncoder(Password.encoder);
		
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
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	@Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("*")
//                .allowedOrigins("*")
//        	    .allowedOrigins("*")
//        	    .allowedHeaders("*")
//        	    .allowedMethods("*");
//            }
//        };
//    }
	
}