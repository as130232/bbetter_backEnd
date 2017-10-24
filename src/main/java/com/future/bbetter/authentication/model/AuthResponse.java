package com.future.bbetter.authentication.model;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 第三方驗證從前端傳遞來的Bean
 * @author Charles
 * @date 2017年10月19日 上午12:17:49
 */
public @Data @NoArgsConstructor class AuthResponse {
	private boolean session_key;
    private String accessToken;
    private Integer expiresIn;
    private String sig;
    private String secret;
    private String  userID;
}
