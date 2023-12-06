package com.abcde.cultureStay.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abcde.cultureStay.service.MemberService;
import com.abcde.cultureStay.vo.Member;


@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {

    @Autowired
    MemberService service;

    @GetMapping("join")
    public String joinForm() {
        return "/member/joinForm";
    }

    @PostMapping("join")
    public String join(Member member) {

        return "/member/joinForm";
    }

    @GetMapping("loginForm")
    public String loginForm() {
        return "/member/loginForm";
    }

}
