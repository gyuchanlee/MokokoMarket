package com.projectif.mokokomarket.global.security.jwt;

import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 세션을 사용하지 않고 현재 JWT를 통해 인가 기능을 수행하기 위한 필터 생성
 */
@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {

    /**
     * 필터로 체크하지 않을 경로나 메서드(GET, POST) 등을 지정하기 위해 사용
     * @param request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        // Preflight 요청은 체크 X.
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String path = request.getRequestURI();
        log.info("check uri.... {}", path);

        // 체크할 필요 없는 경로들을 나열
        if (path.startsWith("/items") || path.startsWith("/boards") || path.startsWith("/login") || path.startsWith("/logout")) {
            return true;
        }

        return false;
    }

    /**
     * 모든 요청에 한 번씩만 수행되는 필터 -> JWT 체크 로직 수행
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeaderStr = request.getHeader("Authorization");

        try {
            // Bearer access Token
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims : " + claims);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT claims error : {}", e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.println(msg);
            writer.close();
        }
    }
}
