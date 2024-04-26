package com.projectif.mokokomarket.member.service;

import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.dto.request.MemberJoinDto;
import com.projectif.mokokomarket.member.dto.request.MemberUpdateDto;
import com.projectif.mokokomarket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 등록
    public boolean join(MemberJoinDto dto) {

        Member checkMember = memberRepository.findByUserId(dto.getUserId()).orElse(null);
        if (checkMember != null) {
            throw new RuntimeException("already joined member exception");
        }

        Member save = memberRepository.save(
                Member.builder()
                        .userId(dto.getUserId())
                        .name(dto.getName())
                        .password(dto.getPassword())
                        .email(dto.getEmail())
                        .phone(dto.getPhone())
                        .build()
        );
        return save.getId() != null;
    }

    // 회원 수정
    public boolean update(MemberUpdateDto dto, Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("member not found"));
        findMember.updateMember(dto);
        return true;
    }

    // 회원 탈퇴
    public boolean delete(Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("member not found"));
        findMember.changeIsDeleted();
        // 로직 수행이 되었다면 flag가 true
        return findMember.isDeleted();
    }
}
