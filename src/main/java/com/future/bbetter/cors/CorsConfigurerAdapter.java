//package com.future.bbetter.cors;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * 解決跨域，使用CORS協議標準，此設定為全域配置
// * 只要有url中有api，則允許取得資源
// * 允許瀏覽器向跨源服務器，發出XMLHttpRequest請求，從而克服了AJAX只能同源使用的限制。
// * @author Charles
// * @date 2017年9月16日 下午5:29:04
// */
//@Component
//@Configuration
//public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter{ 
//	
//	@Override 
//	public void addCorsMappings(CorsRegistry registry) {
//	    registry.addMapping("/api/*")
//	    .allowedOrigins("*")
//	    .allowedHeaders("Content-Type")
//	    .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
//        .allowCredentials(true).maxAge(3600);
//	    //registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000");
//    }
//}
