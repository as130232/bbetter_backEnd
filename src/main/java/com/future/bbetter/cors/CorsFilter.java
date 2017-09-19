//package com.future.bbetter.cors;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
///**
// * 跨域將資源回傳給客戶端時，response Header必須包含該請求回應的資訊，利用filter設置Header屬性
// * 否則瀏覽器將會不允許取得該資源(瀏覽器默認都會基於安全原因而阻止跨域的ajax請求)
// * @author Charles
// * @date 2017年9月16日 下午5:29:04
// */
//@Configuration
//public class CorsFilter extends OncePerRequestFilter {
//    private final static String ALLOWORIGIN_CORS_A = "http://localhost:8100";
////    private final static String ALLOWORIGIN_CORS_b = "http://127.0.0.1:8082";
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // Access-Control-Allow-Origin: 指定授權訪問的域，表明它允許"http://localhost:8100"發起跨域請求 
//        response.addHeader("Access-Control-Allow-Origin", ALLOWORIGIN_CORS_A);  //此優先級高於controller @CrossOrigin配置
//        // Access-Control-Allow-Methods: 授權請求的方法（GET, POST, PUT, DELETE，OPTIONS等)
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
//        // 表明它允許跨域請求包含content-type头
//        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
//        // 表明1800秒內，不需要再發送預檢驗請求，可以緩存該結果（CROS協議中，一個AJAX請求被分成了第一步的OPTION預檢測請求和正式請求）
//        response.addHeader("Access-Control-Max-Age", "1800"); //30min
////        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
////        response.setHeader("Access-Control-Allow-Credentials", "true");
//        filterChain.doFilter(request, response);
//    }
//
//}
