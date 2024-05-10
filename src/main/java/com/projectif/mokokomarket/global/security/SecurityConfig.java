package com.projectif.mokokomarket.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectif.mokokomarket.global.security.jwt.JWTCheckFilter;
import com.projectif.mokokomarket.member.domain.Role;
import com.projectif.mokokomarket.member.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint =
            (request, response, authException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security 인증 실패");

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.sendRedirect("/login");

                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security 인가 실패");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    private final MemberRepository memberRepository;
    /**
     * OAuth2UserService - OAuth2 인증 처리 서비스
     * OAuth2SuccessHandler - OAuth2 인증이 성공했을 시 처리하는 핸들러 설정
     * TokenService - jwt 등록 서비스
     */


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // api 서버에서는 보통 쓰지 않음
                .csrf(AbstractHttpConfigurer::disable)
                // 끄고 필터로 대체
                .cors(AbstractHttpConfigurer::disable)
                .addFilter(corsFilter())
                // 시큐리티가 username/password 기반 인증 필터를 수행하기 전에 JWT 인가 필터 수행
                .addFilterBefore(new JWTCheckFilter(memberRepository),
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                                .successForwardUrl("/login/success")
                                .failureForwardUrl("/login/failure")
                                .permitAll()
                )
//                .oauth2Login(oauth2 -> {
//                    oauth2.userInfoEndpoint(userInfo -> userInfo.userService());
//                    oauth2.successHandler();
//                    oauth2.failureUrl("/login/oauth2/failure");
//                })
                .logout(logoutConfigurer -> {
                    logoutConfigurer.logoutSuccessHandler(logoutSuccessHandler());
                    logoutConfigurer.invalidateHttpSession(true); // 세션 무효화 설정
                    logoutConfigurer.deleteCookies("JSESSIONID");
                })
                .authorizeHttpRequests((authorizeRequests) ->
                                authorizeRequests
                                        .requestMatchers("/oauth2/authorization/**", "/login/**", "logout/**", "/boards","/static/**",
                                                "/boards/{id}", "/items/**", "/error").permitAll()
                                        .requestMatchers("/token/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/members").permitAll() // 회원 가입
                                        .requestMatchers("/members/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.POST, "/boards").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.PUT, "/boards/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.DELETE, "/boards/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                                        .requestMatchers("/orders/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name()) // 주문 관련 api
                                        .anyRequest().authenticated()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 추가하지 않도록 (무상태)
                )
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig
                                .authenticationEntryPoint(authenticationEntryPoint) // 인증 예외 - 401
                                .accessDeniedHandler(accessDeniedHandler) // 인가 예외 - 403
                );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler();
    }

    @Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // React 애플리케이션의 origin
        config.addAllowedOrigin("http://223.130.134.185:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


    @Getter
    @RequiredArgsConstructor
    public static class ErrorResponse {

        private final HttpStatus status;
        private final String message;
    }
}
