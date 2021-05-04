package com.test.spring.controller;

import com.test.spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService;

    //spring 컨테이너에 있는 MemberService 를 연결해주는 기능
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
