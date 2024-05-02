package com.projectif.mokokomarket.board.dto.request;

import com.projectif.mokokomarket.item.domain.Brand;
import lombok.Data;

@Data
public class BoardWriteRequestDto {

    // 글 등록용 Dto

    private String title;
    private String content;
    private Brand category;
    private Integer level;
    private Long memberId;
}
