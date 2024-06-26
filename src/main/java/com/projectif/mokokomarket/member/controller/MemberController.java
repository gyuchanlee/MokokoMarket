package com.projectif.mokokomarket.member.controller;

import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.dto.request.MemberJoinDto;
import com.projectif.mokokomarket.member.dto.request.MemberUpdateDto;
import com.projectif.mokokomarket.member.dto.response.MemberResponseDto;
import com.projectif.mokokomarket.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 정보 조회
    @GetMapping("/{id}")
    public MemberResponseDto getMember(@PathVariable("id") Long id) {

        Member findMember = memberService.findMemberByMemberId(id);

        return MemberResponseDto.builder()
                .memberId(findMember.getId())
                .userId(findMember.getUserId())
                .name(findMember.getName())
                .email(findMember.getEmail())
                .phone(findMember.getPhone())
                .createdDateTime(findMember.getCreatedDateTime())
                .build();
    }

    // 회원 리스트 조회
    @GetMapping("")
    public List<MemberResponseDto> getMemberList() {
        return null;
    }

    // 회원 가입
    @PostMapping("")
    public boolean joinMember(@RequestBody @Validated MemberJoinDto dto) {
        return memberService.join(dto);
    }

    // 회원 수정
    @PutMapping("/{id}")
    public boolean updateMember(@RequestBody @Validated MemberUpdateDto dto, @PathVariable("id") Long id) {
        return memberService.update(dto, id);
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public boolean deleteMember(@PathVariable("id") Long id) {
        return memberService.delete(id);
    }

}
