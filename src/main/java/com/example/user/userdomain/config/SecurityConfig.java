package com.example.user.userdomain.config;

import com.example.user.userdomain.filter.JWTFilter;
import com.example.user.userdomain.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    /*
      Authentication 은 프론트에서 외부 API를 통해 진행
      서버측에서는 프론트에서 사용자 id, pw, 이름, 학과, 학번, 학생상태 가 넘어오면 JWT access token 발급

      이후 프론트에서 요청이 올 경우 헤더의 JWT를 JWT Filter를 통해 사용자 정보를 확인
     */

    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(auth -> auth.disable());

        http
                .formLogin(auth -> auth.disable());

        http
                .httpBasic(auth -> auth.disable());

        http
                .cors(auth -> auth.disable());

        /* 우선은 모든 경로에 인증X -> 추후 수정 예정 */
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/users/login", "/users/login/**", "/docs/**",
//                                "/v3/api-docs/**", "/swagger-ui/**").permitAll()
//                        .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated());

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll());

        // 필터 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);


        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}
