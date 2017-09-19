package com.future.bbetter.authentication.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.future.bbetter.authentication.service.LoginAttemptService;

/**
 * 登入失敗的監聽器
 * @author Charles
 * @date 2017年9月2日 下午2:34:49
 */
@Component
public class AuthenticationFailureListener
        implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			String hostAddress = ip.getHostAddress();
			loginAttemptService.loginFailed(hostAddress);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
    }
}