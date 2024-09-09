package com.example.user.userdomain.filter;

import com.example.user.userdomain.dto.CustomUserDetails;
import com.example.user.userdomain.dto.UserDTO;
import com.example.user.userdomain.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request 에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.warn("토큰이 존재하지 않습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("토큰이 존재합니다.");

        // Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            log.warn("토큰이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 username, nickname, role 획득
        String username = jwtUtil.getUsername(token);
        String nickname = jwtUtil.getNickname(token);
        String role = jwtUtil.getRole(token);

        UserDTO userDTO = new UserDTO(username, nickname, role);

        // CustomUserDetails에 회원 정보 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userDTO);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
