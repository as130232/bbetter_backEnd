//package com.future.bbetter.cors;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
///**
//* 解決跨域，使用CORS協議標準，此設定為全域配置
//* 只要有url中有api，則允許取得資源
//* 允許瀏覽器向跨源服務器，發出XMLHttpRequest請求，從而克服了AJAX只能同源使用的限制。
//* @author Charles
//* @date 2017年9月21日 下午5:29:04
//*/
//@Configuration
//public class CorsConfig {
//	private final static String ALLOWORIGIN_CORS_A = "http://localhost:8100";
//	
//	@Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        //設置允許訪問的網域，若全不允許則設為 * 
//        config.addAllowedOrigin("*");
//        //可限制HEADER 或 METHOD
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        //順序很重要，避免麻煩設置在最前面
//        bean.setOrder(0);
//        return bean;
//    }
//}
