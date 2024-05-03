package com.projectif.mokokomarket.item.domain.response;

import com.projectif.mokokomarket.item.domain.Brand;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponseDto {

    private Long itemId;
    private String title;
    private String content;
    private String imageSource;
    private Integer price;
    private Long quantity;
    private Brand brand;
}
