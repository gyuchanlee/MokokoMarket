package com.projectif.mokokomarket;

import com.projectif.mokokomarket.board.domain.Board;
import com.projectif.mokokomarket.item.domain.Brand;
import com.projectif.mokokomarket.board.repository.BoardRepository;
import com.projectif.mokokomarket.item.domain.Item;
import com.projectif.mokokomarket.item.repository.ItemRepository;
import com.projectif.mokokomarket.member.domain.LoginType;
import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.domain.Role;
import com.projectif.mokokomarket.member.repository.MemberRepository;
import com.projectif.mokokomarket.order.repository.CartRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class MokokoMarketApplication {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ItemRepository itemRepository;

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
                            .role(Role.USER)
                            .loginType(LoginType.BASIC)
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

        // 아이템 5개 세팅
        itemRepository.save(
                Item.builder()
                        .title("감사콩")
                        .content("감사한 모코코")
                        .imageSource("/img/thanksBean.gif")
                        .price(120000)
                        .quantity(30L)
                        .brand(Brand.Mokoko)
                        .build()
        );
        itemRepository.save(
                Item.builder()
                        .title("뭐 되는게 없네")
                        .content("하나도 되는게 없는 모코코")
                        .imageSource("/img/뭐되는게없네콘.png")
                        .price(110000)
                        .quantity(30L)
                        .brand(Brand.Mokoko)
                        .build()
        );
        itemRepository.save(
                Item.builder()
                        .title("방긋로아콘")
                        .content("해피한 모코코들")
                        .imageSource("/img/방긋로아콘.jpg")
                        .price(130000)
                        .quantity(30L)
                        .brand(Brand.Mokoko)
                        .build()
        );
        itemRepository.save(
                Item.builder()
                        .title("모코코킥")
                        .content("매우 아프다")
                        .imageSource("/img/모코코킥.png")
                        .price(150000)
                        .quantity(30L)
                        .brand(Brand.Mokoko)
                        .build()
        );
        itemRepository.save(
                Item.builder()
                        .title("잘자콩")
                        .content("꿀잠자는 모코코")
                        .imageSource("/img/드르렁모코코.png")
                        .price(200000)
                        .quantity(30L)
                        .brand(Brand.Mokoko)
                        .build()
        );
    }
}
