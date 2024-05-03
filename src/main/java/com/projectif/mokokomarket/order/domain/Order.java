package com.projectif.mokokomarket.order.domain;

import com.projectif.mokokomarket.global.auditing.BaseEntity;
import com.projectif.mokokomarket.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String paymentMethod; // 결제 수단
    private Integer totalPrice; // 총 가격
    private String requests; // 요청 사항
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Cart> cartList = new ArrayList<>();

    @Builder
    public Order(String paymentMethod, Integer totalPrice, String requests, OrderStatus status, Member member) {
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.requests = requests;
        this.status = status;
        this.member = member;
    }

    // 장바구니 상품 추가 메서드
    public void addCart(Cart cart) {
        cart.addToOrder(this);
    }

    // 총 가격 계산
    public void calcTotalPrice() {
        totalPrice = 0;
        for (Cart cart : cartList) {
            totalPrice += (cart.getItem().getPrice() * cart.getCount());
        }
    }

    // 주문 취소 메서드
    public void cancelOrder() {
        if (status.equals(OrderStatus.READY)) {
            // 주문 상태 취소
            status = OrderStatus.CANCELED;
            // 장바구니 아이템 취소
            for (Cart cart : cartList) {
                cart.cancelCart();
            }
        }
    }
}
