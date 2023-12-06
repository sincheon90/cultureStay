package com.abcde.cultureStay.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping({"","/"})
    public String Hello() {
        return "hello 3조~ 하이하이";
    }

}
