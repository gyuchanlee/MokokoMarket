package com.projectif.mokokomarket.global.security.jwt;

import com.projectif.mokokomarket.member.dto.response.SessionInfoDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtil {

    // 암호키 지정 -> 30 이상의 문자열을 지정하는 편이 좋음
    private static String key = "emfkkgdkekaskfkvmekfgkgkgnfkrmfkr12304050405";

    public static String generateToken(SessionInfoDto sessionInfoDto, int min) {

        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", sessionInfoDto.getMemberId());
        claims.put("userId", sessionInfoDto.getUserId());
        claims.put("name", sessionInfoDto.getName());
        claims.put("email", sessionInfoDto.getEmail());
        claims.put("profileImage", sessionInfoDto.getProfileImage());
        claims.put("phone", sessionInfoDto.getPhone());
        claims.put("role", sessionInfoDto.getRole());
        claims.put("loginType", sessionInfoDto.getLoginType());

        return Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
    }

    public static String generateToken(Map<String, Object> claims, int min) {

        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
    }

    public static Map<String, Object> validateToken(String token) {

        Map<String, Object> claims = null;

        try {
            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes(StandardCharsets.UTF_8));

            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token) // 파싱 및 검증
                    .getBody();

        } catch (MalformedJwtException malformedJwtException) {
            throw new RuntimeException("malformedJwtException");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new RuntimeException("expiredJwtException");
        } catch (InvalidClaimException invalidClaimException) {
            throw new RuntimeException("invalidClaimException");
        } catch (JwtException jwtException) {
            throw new RuntimeException("jwtException");
        } catch (Exception e) {
            throw new RuntimeException("Error");
        }

        return claims;
    }
}
