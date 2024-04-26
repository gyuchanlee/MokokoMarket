package com.projectif.mokokomarket;

import com.projectif.mokokomarket.board.domain.Board;
import com.projectif.mokokomarket.board.domain.Brand;
import com.projectif.mokokomarket.board.repository.BoardRepository;
import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class MokokoMarketApplication {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public static void main(String[] args) {
        SpringApplication.run(MokokoMarketApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // 테스트용 회원 세팅
        for (int i = 1; i <= 3; i++) {
            memberRepository.save(
                    Member.builder()
                            .userId("test" + i)
                            .name("test" + i)
                            .password("1234")
                            .email("test" + i + "@gmail.com")
                            .phone("010-1234-123" + i)
                            .build()
            );
        }

        Member member = memberRepository.findByUserId("test1").orElseThrow(() -> new RuntimeException("no member found exception"));
        // 테스트용 커뮤니티 글 세팅
        for (int i = 1; i <= 3; i++) {
            boardRepository.save(
                    Board.builder()
                            .title("안녕하세요 " + i + "트")
                            .content("테스트입니다 " + i)
                            .category(Brand.Mokoko)
                            .member(member)
                            .build()
            );
        }
    }

}
