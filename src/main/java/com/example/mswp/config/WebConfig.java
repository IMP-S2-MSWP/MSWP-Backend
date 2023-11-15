package com.example.mswp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // CORS 적용 URL 패턴
                .allowedOrigins("*") // 자원 공유를 허락할 Origin 지정
                .allowedMethods("GET", "POST", "PUT", "DELETE") // HTTP Method 지정
                .allowedHeaders("*") // 클라이언트 측 CORS 요청에 허용되는 헤더
                //.allowCredentials(true) // 클라이언트 측에 대한 응답에 쿠키 등 포함 여부 지정
                .maxAge(MAX_AGE_SECS); // 원하는 시간만큼 pre-flight 요청에 대한 응답 브라우저에 캐싱
    }
}
