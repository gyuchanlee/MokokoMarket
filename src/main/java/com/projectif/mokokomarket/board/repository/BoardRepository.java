package com.projectif.mokokomarket.board.repository;

import com.projectif.mokokomarket.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
