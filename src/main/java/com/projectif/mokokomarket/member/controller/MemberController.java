package com.projectif.mokokomarket.member.controller;

import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.dto.request.MemberJoinDto;
import com.projectif.mokokomarket.member.dto.request.MemberUpdateDto;
import com.projectif.mokokomarket.member.dto.response.MemberResponseDto;
import com.projectif.mokokomarket.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public MemberResponseDto getMember(@PathVariable("id") int id) {
        return null;
    }

    // 회원 리스트 조회
    @GetMapping("")
    public List<MemberResponseDto> getMemberList() {
        return null;
    }

    // 회원 가입
    @PostMapping("")
    public boolean joinMember(@RequestBody MemberJoinDto dto) {
        return memberService.join(dto);
    }

    // 회원 수정
    @PutMapping("/{id}")
    public boolean updateMember(@RequestBody MemberUpdateDto dto, @PathVariable("id") Long id) {
        return memberService.update(dto, id);
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public boolean deleteMember(@PathVariable("id") Long id) {
        return memberService.delete(id);
    }

}
