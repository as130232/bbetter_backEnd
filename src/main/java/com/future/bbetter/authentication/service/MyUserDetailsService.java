package com.future.bbetter.authentication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.future.bbetter.authentication.constant.AUTHORITY;
import com.future.bbetter.member.model.Member;
import com.future.bbetter.member.repository.MemberRepository;

/**
 * 自行定義載入用戶訊息(數據庫取得)，並賦予權限
 * @author Charles
 * @date 2017年9月2日 下午2:45:43
 */
@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//查找是否數據庫是否擁有該會員(email)
		Member member = memberRepository.findByEmail(email);
		
		if(member != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			//創建權限列表(該用戶的權限，可賦予多個權限)
			authorities.add(new SimpleGrantedAuthority(AUTHORITY.USER.role));
			
			return new org.springframework.security.core.userdetails.User(
					member.getEmail(), member.getPassword(), 
                    true, //是否可用
                    true, //是否过期
                    true, //證書不過期為true
                    true, //帳戶未鎖定為true
                    authorities);
			//另一種實作
//			return org.springframework.security.core.userdetails.User
//					.withUsername(member.getEmail())
//					.password(member.getPassword())
//					.authorities(authorities)
//					.accountExpired(false)
//					.accountLocked(false)
//					.credentialsExpired(false)
//					.disabled(false)
//					.build();
		}
		throw new UsernameNotFoundException("User email not found.");
		
	}

}
