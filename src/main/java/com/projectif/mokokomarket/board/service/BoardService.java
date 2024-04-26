package com.projectif.mokokomarket.board.service;

import com.projectif.mokokomarket.board.domain.Board;
import com.projectif.mokokomarket.board.dto.request.BoardUpdateRequestDto;
import com.projectif.mokokomarket.board.dto.request.BoardWriteRequestDto;
import com.projectif.mokokomarket.board.repository.BoardRepository;
import com.projectif.mokokomarket.member.domain.Member;
import com.projectif.mokokomarket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found Exception"));
    }

    @Transactional
    public void saveBoard(BoardWriteRequestDto dto) {

        // 테스트용으로 id 0 member 넣기

        Member findMember = memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found Exception"));

        boardRepository.save(
                Board.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .category(dto.getCategory())
                    .member(findMember)
                    .build()
        );
    }

    @Transactional
    public void updateBoard(BoardUpdateRequestDto dto, Long updateId) {
        Board findBoard = boardRepository.findById(updateId).orElseThrow(() -> new RuntimeException("Board not found Exception"));
        findBoard.changeBoard(dto);
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board findBoard = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found Exception"));
        findBoard.changeIsDeleted();
        log.info("Board flag isDeleted changed to {}", findBoard.isDeleted());
    }
}
