package com.github.studym.studymarathon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactTestController {


    @GetMapping("/api/hello")
    public String test(){

        return "안녕하세요용 스프링부트와 React를 proxy 설정으로 연결했음 ";
    }
}
