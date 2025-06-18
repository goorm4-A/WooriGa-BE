package com.example.server.utils.jwt;

import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey key;

    @PostConstruct
    public void init() {
        // 의존성 주입된 secretKey로 실제 사용할 key 객체 생성
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private static final Long ACCESS_TOKEN_EXPIRATION =  60 * 60 * 1000L; // 1시간
    private static final Long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L;  // 7일

    public String createAccessToken(Long id) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long id) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key)
                .compact();
    }

    public Long validateToken(String token) {
        try{
            Claims claims = Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Long.parseLong(claims.getSubject());
        } catch (ExpiredJwtException e){
            throw new CustomException(ErrorStatus.JWT_EXPIRED);
        } catch (JwtException e) {
            throw new CustomException(ErrorStatus.JWT_INVALID);
        } catch (Exception e) {
            throw new CustomException(ErrorStatus.JWT_UNKNOWN_ERROR); // 그외 예외
        }
    }
}
