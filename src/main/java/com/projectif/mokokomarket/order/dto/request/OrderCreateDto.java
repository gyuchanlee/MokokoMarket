package com.projectif.mokokomarket.order.dto.request;

import com.projectif.mokokomarket.order.domain.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateDto {

    private String paymentMethod; // 결제 수단
    private Integer totalPrice; // 총 가격
    private String requests; // 요청 사항
    private String orderStatus; // 주문 상태
}
