package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://patient-care-diary.fly.dev", "https://patient-care-diary.dev", "https://patient-care-diary.dev:8000") //로컬
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowCredentials(true) // 쿠키 허용
                .allowedHeaders("*")
                .maxAge(3600); // preflight(사전 요청)의 캐시 시간 설정

    }
}
