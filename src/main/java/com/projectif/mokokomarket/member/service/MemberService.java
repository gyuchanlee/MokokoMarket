package com.projectif.mokokomarket.member.service;

import com.projectif.mokokomarket.member.domain.LoginType;
import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.domain.Role;
import com.projectif.mokokomarket.member.dto.request.MemberJoinDto;
import com.projectif.mokokomarket.member.dto.request.MemberUpdateDto;
import com.projectif.mokokomarket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 찾기 - userId
    public Member findMemberByUserId(String userId) {
        return memberRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("user not found, detected user id: " + userId));
    }

    // 회원 찾기 - memberId
    public Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("user not found, detected user pk: " + memberId));
    }

    // 회원 등록
    @Transactional
    public boolean join(MemberJoinDto dto) {

        Member checkMember = memberRepository.findByUserId(dto.getUserId()).orElse(null);
        if (checkMember != null) {
            throw new RuntimeException("already joined member exception");
        }

        Member save = memberRepository.save(
                Member.builder()
                        .userId(dto.getUserId())
                        .name(dto.getName())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .email(dto.getEmail())
                        .phone(dto.getPhone())
                        .role(Role.valueOf(dto.getRole()))
                        .loginType(LoginType.valueOf(dto.getLoginType()))
                        .build()
        );
        return save.getId() != null;
    }

    // 회원 수정
    @Transactional
    public boolean update(MemberUpdateDto dto, Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("member not found"));
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        findMember.updateMember(dto);
        return true;
    }

    // 회원 탈퇴
    @Transactional
    public boolean delete(Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("member not found"));
        findMember.changeIsDeleted();
        // 로직 수행이 되었다면 flag가 true
        return findMember.isDeleted();
    }

    // 스프링 시큐리티 인증을 위한 메서드

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("member not found"));
//        return User.builder()
//                .username(findMember.getUserId())
//                .password(findMember.getPassword())
//                .disabled(!findMember.isEnabled())
//                .accountExpired(!findMember.isAccountNonExpired())
//                .accountLocked(!findMember.isAccountNonLocked())
//                .roles(findMember.getRole().name())
//                .build();
        return findMember;
    }
}
