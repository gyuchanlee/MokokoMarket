package com.projectif.mokokomarket.member.repository;

import com.projectif.mokokomarket.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);
    Optional<Member> findByName(String name);

    // 이름 + 이메일 확인

}
