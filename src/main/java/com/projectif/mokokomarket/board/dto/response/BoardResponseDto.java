package com.projectif.mokokomarket.board.dto.response;

import com.projectif.mokokomarket.item.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private Brand category;
    private Integer level;
    private Long ref;
    private Long memberId;
    private String userId;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
}
