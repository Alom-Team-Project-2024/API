package com.example.user.userdomain.jwt;

import com.example.user.userdomain.entity.Role;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;


/* JWTUtil 0.12.3 */
@Component
public class JWTUtil {

    private final SecretKey secretKey;

    /* access token 유효시간 */
    private final long accessTokenValidity = 1000 * 60 * 30; // 30분


    /* SecretKey 설정 */
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /* token에서 username 꺼내기 */
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    /* token에서 nickname 꺼내기 */
    public String getNickname(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("nickname", String.class);
    }

    /* token에서 role 꺼내기 */
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    /* token 만료시간 확인 */
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    /* 유효한 token인지 확인 */
    public boolean validateToken(String token, String username) {
        return (username.equals(getUsername(token)) && !isExpired(token));
    }

    /* AccessToken 발급 */
    public String createAccessToken(String username, String nickname, Enum<Role> role) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role.name())
                .claim("nickname", nickname)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(secretKey)
                .compact();
    }
}
