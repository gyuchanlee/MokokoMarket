package com.projectif.mokokomarket.order.domain;

import com.projectif.mokokomarket.global.auditing.BaseEntity;
import com.projectif.mokokomarket.item.domain.Item;
import com.projectif.mokokomarket.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Table(name = "cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    private Integer count; // 주문 수량
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public Cart(Integer count, Member member, Item item) {
        this.count = count;
        this.status = CartStatus.ORDERED; // 처음 장바구니 아이템이 들어온다면 결제되서 주문된 상태.
        this.member = member;
        this.item = item;
    }

    public Cart(Integer count, CartStatus status, Member member, Item item) {
        this.count = count;
        this.status = status;
        this.member = member;
        this.item = item;
    }

    // 연관관계 편의 메서드
    public void addToOrder(Order order) {
        this.order = order;
        order.getCartList().add(this);
    }

    // 주문 상태 취소
    public void cancelCart() {
        if (status == CartStatus.FAILED) {
            return;
        }
        this.status = CartStatus.CANCELED;
        this.item.addStock(this.count);
    }
}
