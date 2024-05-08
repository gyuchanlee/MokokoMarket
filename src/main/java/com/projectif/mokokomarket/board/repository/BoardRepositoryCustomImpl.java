package com.projectif.mokokomarket.board.repository;

import com.projectif.mokokomarket.board.dto.response.BoardResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.projectif.mokokomarket.board.domain.QBoard.board;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardResponseDto> findAllPaging(Pageable pageable) {

        List<BoardResponseDto> contents = findBoardList(pageable);
        long counts = countBoardList();

        return new PageImpl<>(contents, pageable, counts);
    }

    private List<BoardResponseDto> findBoardList(Pageable pageable) {

        return queryFactory.select(
                        Projections.constructor(
                                BoardResponseDto.class,
                                board.id,
                                board.title,
                                board.content,
                                board.category,
                                board.level,
                                board.ref,
                                board.member.id,
                                board.member.userId,
                                board.createdDateTime,
                                board.modifiedDateTime
                        )
                )
                .from(board)
                .where(
                        isDeletedIsFalse()
                )
                .orderBy(
                        board.ref.desc(),
                        board.level.asc(),
                        board.createdDateTime.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression isDeletedIsFalse() {
//        return StringUtils.hasText() ? board.isDeleted.eq(0) : null;
        return board.isDeleted.eq(false);
    }

    // 페이징을 위한 카운트 쿼리
    private Long countBoardList() {
        return queryFactory.select(board.count())
                .from(board)
                .where(isDeletedIsFalse())
                .fetchOne();
    }
}
