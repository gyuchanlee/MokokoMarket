package com.projectif.mokokomarket.member.dto.response;

import lombok.Data;

@Data
public class MemberResponseDto {

    private Long memberId;
    private String userId;
    private String name;
    private String email;
    private String phone;
}
