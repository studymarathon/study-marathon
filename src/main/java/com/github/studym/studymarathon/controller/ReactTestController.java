package com.github.studym.studymarathon.controller;

import com.github.studym.studymarathon.domain.board.dto.BoardDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
public class ReactTestController {


    @GetMapping("/api/hello")
    public String test(){

        return "안녕하세요용 스프링부트와 React를 proxy 설정으로 연결했음 ";
    }

}
