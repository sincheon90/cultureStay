package com.abcde.cultureStay.messenger;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    @GetMapping("chat")
    public String chatTest(){
        return "simpleChat";
    }

    @MessageMapping("/sendMessage") // 클라이언트가 메시지를 보낼 경로
    @SendTo("/topic/messages") // 브로드캐스트할 경로
    public Message sendMessage(Message message) {
        return message; // 메시지를 브로드캐스트
    }
}

