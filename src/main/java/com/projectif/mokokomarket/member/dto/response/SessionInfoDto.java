package com.projectif.mokokomarket.member.dto.response;

import com.projectif.mokokomarket.member.domain.LoginType;
import com.projectif.mokokomarket.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SessionInfoDto {

    private Long memberId;
    private String userId;
    private String name;
    private String email;
    private String profileImage;
    private String phone;
    private Role role;
    private LoginType loginType;

}
