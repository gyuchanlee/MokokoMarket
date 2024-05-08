package com.projectif.mokokomarket.board.repository;

import com.projectif.mokokomarket.board.dto.response.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<BoardResponseDto> findAllPaging(Pageable pageable);
}
