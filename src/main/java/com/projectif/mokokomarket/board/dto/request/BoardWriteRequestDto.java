package com.projectif.mokokomarket.board.dto.request;

import com.projectif.mokokomarket.item.domain.Brand;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardWriteRequestDto {

    // 글 등록용 Dto

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private Brand category;
    private Integer level;
    private Long memberId;
}
