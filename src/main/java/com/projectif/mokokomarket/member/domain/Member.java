package com.projectif.mokokomarket.member.domain;

import com.projectif.mokokomarket.board.domain.Board;
import com.projectif.mokokomarket.global.auditing.BaseEntity;
import com.projectif.mokokomarket.member.dto.request.MemberUpdateDto;
import com.projectif.mokokomarket.order.domain.Cart;
import com.projectif.mokokomarket.order.domain.Order;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"boardList"})
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String userId;
    private String name;
    private String email;
    private String profileImage;
    private String phone;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private LoginType loginType;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Order> orderList = new ArrayList<>();
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Cart> cartList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> role.name());
        return collection;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // 기본 회원 등록
    @Builder
    public Member(String userId, String name, String email, String profileImage, String phone, String password, Role role, LoginType loginType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        if (profileImage == null) {
            this.profileImage = "https://kr.object.ncloudstorage.com/dodobird-bk1/thanksBean.gif"; // 기본 회원가입 -> 기본 프로필
        } else {
            this.profileImage = profileImage;
        }
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.loginType = loginType;
    }

    // oAuth2 회원 수정 메서드
    public void oauth2ChangeFields(String name, String profileImage, String phone, Role role) {
        this.name = name;
        this.profileImage = profileImage;
        this.phone = phone;
        this.role = role;
    }

    // 회원 수정
    public void updateMember(MemberUpdateDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.password = dto.getPassword();
    }

}
