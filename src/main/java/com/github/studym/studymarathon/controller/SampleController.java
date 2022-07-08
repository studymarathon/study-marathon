package com.github.studym.studymarathon.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/api")
public class SampleController {

    @GetMapping("/hello")
    public String hello(){
        return "Testing hello XD";
    }

    @GetMapping("main")
    public String mainpage(){
        return "로그인이 필요한 메인페이지임";
    }

}
