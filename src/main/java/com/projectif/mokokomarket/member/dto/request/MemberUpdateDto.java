package com.projectif.mokokomarket.member.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MemberUpdateDto {

    // 회원 수정 Dto
    @NotBlank(message = "이름은 필수입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글과 영문자만 사용할 수 있습니다.")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Email(message = "유효한 이메일 형식을 입력해주세요.")
    @Size(min = 2, max = 100)
    private String email;

    @NotNull
    @Pattern (regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "유효한 전화번호를 입력해주세요.")
    @Size(min = 2, max = 20)
    private String phone;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$", message = "비밀번호는 8~20자리의 영문자 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.")
    @Size(min = 2, max = 20)
    private String password;

    @NotBlank
    private String profileImage;
}
