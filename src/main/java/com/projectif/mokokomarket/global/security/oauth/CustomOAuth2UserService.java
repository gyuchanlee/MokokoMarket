package com.projectif.mokokomarket.global.security.oauth;

import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//        log.info("registrationId = {}", registrationId);
//        log.info("userNameAttributeName = {}", userNameAttributeName);

        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member findMember = null;
        try {
            findMember = memberRepository.findByUserId((String) oAuth2Attribute.getAttributes().get("email"))
                    .orElseThrow(() -> new RuntimeException("member not found"));
        } catch (RuntimeException e) {
            log.info("[CustomOAuth2UserService] : 찾는 회원이 없으므로 findMember = NULL 반환");
        }
        String role = "USER";
        if (findMember != null) {
            log.info("[OAuth2UserService] memberRepository 이미 회원가입된 회원의 권한이 있는 지 확인 findMember의 권한 = {}", findMember.getRole());
            role = findMember.getRole().name();
        }

        var memberAttribute = oAuth2Attribute.convertToMap();

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role)),
                memberAttribute,
                "email"
        );
    }


}
