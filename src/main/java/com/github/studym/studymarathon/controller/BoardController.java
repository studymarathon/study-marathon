package com.github.studym.studymarathon.controller;

import com.github.studym.studymarathon.domain.board.dto.BoardDTO;
import com.github.studym.studymarathon.domain.board.dto.PageRequestDTO;
import com.github.studym.studymarathon.domain.board.dto.PageResultDTO;
import com.github.studym.studymarathon.domain.board.entity.Board;
import com.github.studym.studymarathon.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping("/list")
    public PageResultDTO<BoardDTO, Board> list(){

        PageRequestDTO  pageRequestDTO = new PageRequestDTO();

        log.info("list.........." + pageRequestDTO);

        return service.getList(pageRequestDTO);

    }

    @PostMapping("/register")
    public String register(BoardDTO dto){

        return "";
    }

}
