package com.projectif.mokokomarket.global.security.oauth;


import com.projectif.mokokomarket.member.domain.LoginType;
import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.domain.Role;
import com.projectif.mokokomarket.member.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        /*
         * 이 부분에 oAuth2User 정보를 회원 엔티티나 Dto에 넣기
         */
        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);

        Map<String, Object> attributes = oAuth2User.getAttributes();
//        attributes.forEach((key, value) -> {
//            log.info("key = {} / value = {}", key, value);
//        });

        String auth = String.valueOf(oAuth2User.getAuthorities().toArray()[0]);
        auth = auth.substring(5);
//        log.info(auth);

        // 최초 로그인이라면 회원가입 처리를 한다.
        Member findMember = null;
        try {
            findMember = memberRepository.findByUserId(oAuth2User.getName())
                    .orElseThrow(() -> new RuntimeException("member not found"));
        } catch (RuntimeException e) {
            log.info("[OAuth2SuccessHandler] : 찾는 회원이 없으므로 null 반환 / 최초 로그인이므로 회원 가입 수행");
        }


        // PK값 받아오기
        Long id = null;

        if (findMember == null) {
            // 최초 로그인이므로 회원가입 처리하기
            Member member = Member.builder()
                    .userId(oAuth2User.getName())
                    .name((String) attributes.get("name"))
                    .email((String) attributes.get("email"))
                    .profileImage((String) attributes.get("picture"))
                    .phone((String) attributes.get("phone"))
                    .role(Role.valueOf(auth))
                    .password(null)
                    .loginType(LoginType.valueOf(((String) attributes.get("loginType")).toUpperCase()))
                    .build();

            findMember = memberRepository.save(member);

        } else if (!findMember.getName().equals(attributes.get("name")) || !findMember.getProfileImage().equals(attributes.get("picture"))
                || !findMember.getPhone().equals(attributes.get("phone")) || !findMember.getRole().name().equals(auth)) {
            // 이 후 로그인이라도 혹시 naver 계정의 정보가 바뀔 수 있으니 바꾸는 로직을 추가할 지 고민해보기.
            // if문으로 지금 로그인한 oauth2user 정보와 db에 저장된 sns 회원 정보 중 변경될 가능성이 있는 필드만 비교 (프로필사진, 이름, 폰번호)
            // JPA Dirty Checking
            findMember.oauth2ChangeFields((String) attributes.get("name"), (String) attributes.get("picture"), (String) attributes.get("phone"), Role.valueOf(auth));
        }
        id = findMember.getId();

        String targetUrl = UriComponentsBuilder.fromUriString("/login/oauth2/success")
//                .queryParam("token", "token")
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
