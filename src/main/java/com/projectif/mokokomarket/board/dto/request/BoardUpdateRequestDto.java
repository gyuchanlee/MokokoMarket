package com.projectif.mokokomarket.board.dto.request;

import com.projectif.mokokomarket.item.domain.Brand;
import lombok.Data;

@Data
public class BoardUpdateRequestDto {

    // 글 수정용 Dto

    private String title;
    private String content;
    private Brand category;
}
