package com.projectif.mokokomarket.member.dto.request;

import lombok.Data;

@Data
public class MemberUpdateDto {

    // 회원 수정 Dto
    private String name;
    private String email;
    private String phone;
    private String password;
}
