package com.projectif.mokokomarket.board.domain;

import com.projectif.mokokomarket.board.dto.request.BoardUpdateRequestDto;
import com.projectif.mokokomarket.global.auditing.BaseEntity;
import com.projectif.mokokomarket.item.domain.Brand;
import com.projectif.mokokomarket.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private Brand category;
    private Integer level;
    private Long ref;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    // ref 설정 메서드
    public void changeRef(Long ref) {
        this.ref = ref;
    }

    // 글 등록
    @Builder
    public Board(String title, String content, Brand category, Member member) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.level = 0;
        this.member = member;
    }

    public Board(String title, String content, Brand category, Long ref, Member member) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.level = 0;
        this.ref = ref;
        this.member = member;
    }

    // 글 변경
    public void changeBoard(BoardUpdateRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.category = dto.getCategory();
    }
}
