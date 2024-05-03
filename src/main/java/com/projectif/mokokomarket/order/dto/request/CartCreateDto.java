package com.projectif.mokokomarket.order.dto.request;

import lombok.Data;

@Data
public class CartCreateDto {

    private Integer count; // 주문 수량
    private Long memberId;
    private Long itemId;
}
