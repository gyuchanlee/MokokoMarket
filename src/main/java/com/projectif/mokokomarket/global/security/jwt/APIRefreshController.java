package com.projectif.mokokomarket.global.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class APIRefreshController {

    @PostMapping("/members/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader, @RequestBody String refreshToken) {

        log.info("authHeader: " + authHeader);
        log.info("Refresh token: " + refreshToken);

        if (refreshToken == null) {
            throw new IllegalArgumentException("Refresh token is mandatory");
        }
        if (authHeader == null) {
            throw new IllegalArgumentException("Authorization is mandatory");
        }
        if (authHeader.length() < 7) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }
        String accessToken = authHeader;

        // access 토큰이 만료되지 않은 분기
        if (!checkExpiredToken(accessToken)) {
            return Map.of("accessToken",accessToken, "refreshToken",refreshToken);
        }
        // RefreshToken 검증
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
        String newAccessToken = JWTUtil.generateToken(claims, 10);
        String newRefreshToken = checkTime((Integer) claims.get("exp")) ? JWTUtil.generateToken(claims, 60*24) : refreshToken;

        return Map.of("accessToken",newAccessToken, "refreshToken",newRefreshToken);
    }

    // 시간이 1시간 미만으로 남았는 지 판단
    private boolean checkTime(Integer exp) {
        // JWT exp -> 날짜 변환
        Date expDate = new Date((long) exp * (1000));
        // 현재 시간과의 차이 계산
        long gap = expDate.getTime() - System.currentTimeMillis();
        // 분 단위 계산
        long leftMin = gap / (1000 * 60);
        // 1시간 미만 남았는 지 참/거짓 판별
        return leftMin < 60;
    }

    private boolean checkExpiredToken(String token) {
        try {
            JWTUtil.validateToken(token);
        } catch (Exception e) {
            if (e.getMessage().equals("expiredJwtException")) {
                return true;
            }
        }
        return false;
    }
}
