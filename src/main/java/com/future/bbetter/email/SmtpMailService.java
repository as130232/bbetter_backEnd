package com.future.bbetter.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.future.bbetter.BbetterApplication;

/**
 * 寄信服務
 * @author Charles
 * @date 2017年9月2日 下午2:43:10
 */
@Service
public class SmtpMailService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void send(String to, String subject, String content){
		try{
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper;
			
			helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			
			javaMailSender.send(mimeMessage);
			System.out.println("Mail :　Send Succes!");
			
		}catch (MessagingException e){
		     System.out.println("Mail :　Send Fail!");
		     e.printStackTrace();
	    }
	}
	public static void main(String[] args) {
		SmtpMailService smtpMailService = new SmtpMailService();
		smtpMailService.send("as130232@gmail.com", "test", "test");
	}
}
