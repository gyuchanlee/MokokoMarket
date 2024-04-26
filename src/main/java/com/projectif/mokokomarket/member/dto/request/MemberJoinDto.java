package com.projectif.mokokomarket.member.dto.request;

import lombok.Data;

@Data
public class MemberJoinDto {

    // 회원 가입 Dto
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String password;
}
