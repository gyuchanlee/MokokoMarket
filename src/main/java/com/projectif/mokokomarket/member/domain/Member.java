package com.projectif.mokokomarket.member.domain;

import com.projectif.mokokomarket.board.domain.Board;
import com.projectif.mokokomarket.global.auditing.BaseEntity;
import com.projectif.mokokomarket.member.dto.request.MemberUpdateDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"boardList"})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String password;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();

    // 회원 등록
    @Builder
    public Member(String userId, String name, String email, String phone, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // 회원 수정
    public void updateMember(MemberUpdateDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.password = dto.getPassword();
    }
}
