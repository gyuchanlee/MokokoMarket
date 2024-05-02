package com.projectif.mokokomarket.order.dto.response;

import com.projectif.mokokomarket.item.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartListResponseDto {

    private Long cartId;
    private Integer count; // 주문 수량
    private Long itemId;
    private String title;
    private String content;
    private String imageSource;
    private Integer price;
    private Brand brand;
}
