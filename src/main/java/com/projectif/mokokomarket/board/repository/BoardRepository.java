package com.projectif.mokokomarket.board.repository;

import com.projectif.mokokomarket.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    List<Board> findAllByIsDeletedIsOrderByCreatedDateTimeDesc(boolean isDeleted);
}
