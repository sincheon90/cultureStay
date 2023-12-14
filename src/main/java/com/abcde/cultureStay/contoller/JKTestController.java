package com.abcde.cultureStay.contoller;

import com.abcde.cultureStay.service.TranslateService;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("jk")
public class JKTestController {

    @Autowired
    TranslateService service;

    @GetMapping("translate")
    public String translate(){
        String text = "이것은 번역 테스트 렛츠고!";
        System.out.println("translate 컨트롤러 실행");
//        System.out.println(service.translate("ko", "ja", text));

        return "jk/translation";
    }
//http://localhost:3333/jk/translate

    @GetMapping("list")
    public String list() {
        return "jk/list";
    }
}
