package com.projectif.mokokomarket.board.controller;

import com.projectif.mokokomarket.board.domain.Board;
import com.projectif.mokokomarket.board.dto.request.BoardUpdateRequestDto;
import com.projectif.mokokomarket.board.dto.request.BoardWriteRequestDto;
import com.projectif.mokokomarket.board.dto.response.BoardResponseDto;
import com.projectif.mokokomarket.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    public List<BoardResponseDto> getBoardList() {
        List<Board> boardList = boardService.getBoardList();
        return boardList.stream().map((board) ->
             BoardResponseDto.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .category(board.getCategory())
                    .level(board.getLevel())
                     .ref(board.getRef())
                    .memberId(board.getMember().getId())
                     .userId(board.getMember().getUserId())
                    .createdDateTime(board.getCreatedDateTime())
                    .modifiedDateTime(board.getModifiedDateTime())
                    .build()
        ).toList();
    }

    // 예) /boards?page=0&size=3&sort=id,desc&sort=boardTitle,desc
    @GetMapping("/sorted")
    public Page<BoardResponseDto> getBoardList(@PageableDefault(size = 5, page = 0, sort = "boardPk", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<BoardResponseDto> boardPage = boardService.getBoardSortedList(pageable);

        return boardPage;
    }

        @GetMapping("/{id}")
    public BoardResponseDto getBoard(@PathVariable("id") Long id) {
        Board board = boardService.getBoard(id);

        return BoardResponseDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .level(board.getLevel())
                .ref(board.getRef())
                .memberId(board.getMember().getId())
                .userId(board.getMember().getUserId())
                .createdDateTime(board.getCreatedDateTime())
                .modifiedDateTime(board.getModifiedDateTime())
                .build();
    }

    @PostMapping("")
    public boolean writeBoard(@RequestBody BoardWriteRequestDto dto) {
        boardService.saveBoard(dto);
        return true;
    }

    @PutMapping("/{id}")
    public boolean updateBoard(@PathVariable("id") Long id, @RequestBody BoardUpdateRequestDto dto) {
        boardService.updateBoard(dto, id);
        return true;
    }

    @DeleteMapping("/{id}")
    public boolean deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return true;
    }
}
