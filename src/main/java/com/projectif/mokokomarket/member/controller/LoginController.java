package com.projectif.mokokomarket.member.controller;

import com.projectif.mokokomarket.member.domain.LoginType;
import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.dto.response.SessionInfoDto;
import com.projectif.mokokomarket.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final HttpSession session;

    @PostMapping("/login/success")
    public SessionInfoDto success() {
        // 인증된 사용자 정보
        return getSessionInfoDto(LoginType.BASIC);
    }

    @GetMapping("/login/oauth2/success")
    public SessionInfoDto oAuthSuccess() {
        // 인증된 사용자 정보
        return getSessionInfoDto(LoginType.NAVER);
    }

    @PostMapping("/login/failure")
    public ResponseEntity<?> failure(HttpSession session) {

        log.info("로그인 실패");

        session.invalidate();

        // 나중에 401 Error로 이쁘게 보내보기
        return ResponseEntity.ok("로그인 실패");
    }

    private SessionInfoDto getSessionInfoDto(LoginType loginType) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth = {}", auth);

        String userId = "";
        if (loginType.name().equalsIgnoreCase("BASIC")) {
            userId = ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            userId = ((OAuth2User) auth.getPrincipal()).getAttribute("email");
        }

        // DB 회원 찾기
        Member member = memberService.findMemberByUserId(userId);
        // 세션에 필수 정보 넣기

        return SessionInfoDto.builder()
                .memberId(member.getId())
                .userId(member.getUserId())
                .name(member.getName())
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .phone(member.getPhone())
                .role(member.getRole())
                .loginType(member.getLoginType())
                .build();
    }
}
