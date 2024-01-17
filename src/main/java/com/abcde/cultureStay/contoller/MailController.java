package com.abcde.cultureStay.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abcde.cultureStay.service.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MailController {
	@Autowired
	MailService mailService;

    @GetMapping("mail")
    public String MailPage(){
        return "mail/Main";
    }
    
    @ResponseBody
    @PostMapping("mail")
    public String MailSend(String mail){
       log.debug("mail:{}", mail);

       int number = mailService.sendMail(mail);

       String num = "" + number;

       return num;
    }

}
