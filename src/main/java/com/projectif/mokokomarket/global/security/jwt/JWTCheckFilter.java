package com.projectif.mokokomarket.global.security.jwt;

import com.nimbusds.jose.shaded.gson.Gson;
import com.projectif.mokokomarket.member.domain.LoginType;
import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.domain.Role;
import com.projectif.mokokomarket.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 세션을 사용하지 않고 현재 JWT를 통해 인가 기능을 수행하기 위한 필터 생성
 */
@Slf4j
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;

    /**
     * 필터로 체크하지 않을 경로나 메서드(GET, POST) 등을 지정하기 위해 사용
     *
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
        if (
                path.startsWith("/items") || path.startsWith("/login") || path.startsWith("/logout") || (path.equals("/boards") && request.getMethod().equals("GET"))
                || path.equals("/members/refresh")
        ) {
            return true;
        }

        return false;
    }

    /**
     * 모든 요청에 한 번씩만 수행되는 필터 -> JWT 체크 로직 수행
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    @Transactional(readOnly = true)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeaderStr = request.getHeader("Authorization");
        log.info("authHeaderStr: {}", authHeaderStr);

        try {
            // Bearer access Token
            String accessToken = authHeaderStr;
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            Member findMember = memberRepository.findByUserId((String) claims.get("userId"))
                    .orElseThrow(() -> new RuntimeException("member not found"));

            String role = "";
            Collection<? extends GrantedAuthority> findAuthorities = findMember.getAuthorities();
            for (GrantedAuthority authority : findAuthorities) {
                role = authority.getAuthority();
            }

            Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

            // 시큐리티 컨텍스트에 인증 정보 저장
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims, findMember.getPassword(), authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT claims error : {}", e.getMessage());

            // accessToken이 만료되었을 시 -> refreshToken으로 갱신 요청하기

//            throw new RuntimeException("ERROR_ACCESS_TOKEN : " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
