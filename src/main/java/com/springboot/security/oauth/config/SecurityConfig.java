package com.springboot.security.oauth.config;

import com.springboot.domain.member.entity.Role;
import com.springboot.security.jwt.filter.JwtAuthenticationProcessingFilter;
import com.springboot.security.jwt.filter.JwtExceptionFilter;
import com.springboot.security.oauth.handler.OAuth2LoginFailureHandler;
import com.springboot.security.oauth.handler.OAuth2LoginSuccessHandler;
import com.springboot.security.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private  final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    private final JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable() // httpBasic 사용 X
                .csrf().disable() // csrf 보안 사용 X
                .headers().frameOptions().disable()
                .and()
                // 세션 사용하지 않으므로 STATELESS로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeHttpRequests()//
                    .requestMatchers("/","/css/**","/images/**","/js/**","/swagger-ui/**","/v3/api-docs/**",
                                "/swagger*/**","/favicon.ico", "/login/oauth2/code/google", "/auth/**", "/oauth2/**").permitAll()
                    .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                    .successHandler(oAuth2LoginSuccessHandler) // 동의하고 계속하기를 눌렀을 때 Handler 설정
                    .failureHandler(oAuth2LoginFailureHandler) // 소셜 로그인 실패 시 핸들러 설정
                    .userInfoEndpoint().userService(customOAuth2UserService); // customUserService 설정
        // [PART4]
        // 원래 스프링 시큐리티 필터 순서가 LogoutFilter 이후에 로그인 필터 동작
        // 따라서, LogoutFilter 이후에 우리가 만든 필터 동작하도록 설정
        // 순서 : LogoutFilter -> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationProcessingFilter.class);
        return http.build();
    }
}
