package com.abcde.cultureStay.messenger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    @GetMapping("simpleChat")
    public String chatTest(){
        return "/messenger/simpleChat";
    }

    @MessageMapping("/sendSimpleMessage") // 클라이언트가 메시지를 보낼 경로
    @SendTo("/topic/messages") // 브로드캐스트할 경로
    public String sendMessage(String jsonMessage) {
        // JSON 문자열을 파싱하여 실제 메시지 내용을 추출
//        JsonObject jsonObject = new JsonParser().parse(jsonMessage).getAsJsonObject();
        return jsonMessage;
    }
}

