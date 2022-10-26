package com.github.studym.studymarathon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/test")
    public String test() {

        return "test";
    }


    @GetMapping("/join")
    public void joinGET(){
        log.info("join get....");
    }


}
