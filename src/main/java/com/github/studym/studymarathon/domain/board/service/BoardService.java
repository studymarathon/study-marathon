package com.github.studym.studymarathon.domain.board.service;

import com.github.studym.studymarathon.domain.board.dto.BoardDTO;
import com.github.studym.studymarathon.domain.board.dto.PageRequestDTO;
import com.github.studym.studymarathon.domain.board.dto.PageResultDTO;
import com.github.studym.studymarathon.domain.board.entity.Board;

public interface BoardService {
    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO);


    default Board dtoToEntity(BoardDTO dto) {
        Board entity = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .build();

        return entity;
    }

    default BoardDTO entityToDto(Board entity) {
        BoardDTO dto = BoardDTO.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .author(entity.getAuthor())
                .regDate(entity.getCreated_at())
                .modDate(entity.getModified_at())
                .build();

        return dto;
    }
}
