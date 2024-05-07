package com.projectif.mokokomarket.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private Long memberId;
    private String userId;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createdDateTime;
}
