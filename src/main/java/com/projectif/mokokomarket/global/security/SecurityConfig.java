package com.projectif.mokokomarket.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.PrintWriter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint =
            (request, response, authException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security 인증 실패!!!");

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
                ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security 인가 실패!!!");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                                .successForwardUrl("/loginSuccess")
                                .failureForwardUrl("/loginFailure")
                                .permitAll()
                )
//                .oauth2Login(oauth2 -> {
//                    oauth2.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService));
//                    oauth2.successHandler(oAuth2SuccessHandler);
//                    oauth2.failureUrl("/login/oauth2/fail");
//                })
                .logout(logoutConfigurer -> {
                    logoutConfigurer.logoutSuccessUrl("/");
                    logoutConfigurer.invalidateHttpSession(true); // 세션 무효화 설정
                    logoutConfigurer.deleteCookies("JSESSIONID");
                })
                .authorizeHttpRequests((authorizeRequests) ->
                                authorizeRequests
                                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                                        .anyRequest().permitAll()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionConcurrency((sessionConcurrency) ->
                                        sessionConcurrency
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(true)
                                                .expiredUrl("/login?expired")
                                )
                )
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig
                                .authenticationEntryPoint(authenticationEntryPoint) // 인증 예외 - 401
                                .accessDeniedHandler(accessDeniedHandler) // 인가 예외 - 403
                );

        return http.build();
    }


    @Getter
    @RequiredArgsConstructor
    public static class ErrorResponse {

        private final HttpStatus status;
        private final String message;
    }
}
