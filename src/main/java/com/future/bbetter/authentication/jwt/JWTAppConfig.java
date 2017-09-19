package com.future.bbetter.authentication.jwt;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @ConfigurationProperties
 * 外部配置註釋。如果要綁定並驗證某些外部屬性（例如:application.properties文件），
 * 這裡讀取jwt屬性-密鑰(secret)與有效時間(tokenValidityInSeconds)等值
 * @author Charles
 * @date 2017年9月16日 下午7:19:20
 */
@ConfigurationProperties(prefix = "jwt")
@Component
public class JWTAppConfig {
	private String secret;

	private long tokenValidityInSeconds;

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public long getTokenValidityInSeconds() {
		return this.tokenValidityInSeconds;
	}

	public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
		this.tokenValidityInSeconds = tokenValidityInSeconds;
	}

}