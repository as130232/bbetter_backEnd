package com.future.bbetter.authentication.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.future.bbetter.authentication.service.LoginAttemptService;
import com.future.bbetter.email.SmtpMailService;

/**
 * 登入成功的監聽器
 * @author Charles
 * @date 2017年9月2日 下午2:34:49
 */
@Component
public class AuthenticationSuccessEventListener
        implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;
    
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails)
                e.getAuthentication().getDetails();
        try {
			InetAddress ip = InetAddress.getLocalHost();
			String hostAddress = ip.getHostAddress();
			//將block中登入失敗的ip移除
			loginAttemptService.loginSucceeded(hostAddress);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
    }
}
