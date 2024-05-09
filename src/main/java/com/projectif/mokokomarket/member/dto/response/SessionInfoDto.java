package com.projectif.mokokomarket.member.dto.response;

import com.projectif.mokokomarket.member.domain.LoginType;
import com.projectif.mokokomarket.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    // jwt 토큰
    private String accessToken;
    private String refreshToken;

    public SessionInfoDto(Long memberId, String userId, String name, String email, String profileImage, String phone, Role role, LoginType loginType) {
        this.memberId = memberId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.phone = phone;
        this.role = role;
        this.loginType = loginType;
    }
}
