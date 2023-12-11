package com.abcde.cultureStay.contoller;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("jk")
public class JKTestController {

    @GetMapping("translate")
    public String translate(){
        return "utils/translation";
    }

}
