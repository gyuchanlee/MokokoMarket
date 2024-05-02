package com.projectif.mokokomarket.order.dto.response;

import com.projectif.mokokomarket.order.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderResponseDto {
    
    // 한 주문 내역

    private Long orderId;
    private String paymentMethod; // 결제 수단
    private Integer totalPrice; // 총 가격
    private String requests; // 요청 사항
    private OrderStatus status; // 주문 상태
    private Long memberId;
    private String userId;
    private List<CartListResponseDto> cartList;
}
