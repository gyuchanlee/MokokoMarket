package com.projectif.mokokomarket.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateDto {

    private Long memberId;
    private String paymentMethod; // 결제 수단
    private Integer totalPrice; // 총 가격
    private String requests; // 요청 사항
    private String orderStatus; // 주문 상태
    private List<CartCreateDto> cartList; // 장바구니 상품들
}
