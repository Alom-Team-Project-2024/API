package com.example.user.userdomain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    /*
      Authentication 은 프론트에서 외부 API를 통해 진행
      서버측에서는 프론트에서 사용자 id, pw, 이름, 학과, 학번, 학생상태 가 넘어오면 JWT access token 발급

      이후 프론트에서 요청이 올 경우 헤더의 JWT를 JWT Filter를 통해 사용자 정보를 확인
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(auth -> auth.disable());

        http
                .formLogin(auth -> auth.disable());

        http
                .httpBasic(auth -> auth.disable());

        /* 우선은 모든 경로에 인증X -> 추후 수정 예정 */

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "users/login", "users/login/**").permitAll()
                        .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());

        return http.build();
    }


}
