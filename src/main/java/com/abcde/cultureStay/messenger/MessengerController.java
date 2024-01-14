package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("messenger")
public class MessengerController {
    @Autowired
    MessengerService service;

    @GetMapping("chat")
    public String chatRoomList(Model model
                              ) {
        System.out.println("chatRoomList entered");
        String id = "111";
//        ArrayList<ChatRoom> chatRooms = service.getChatRoomList(user.getUsername());
//        if(!service.isChatRoomPresent(id)) service.createChatRoom(id, "테스트 채팅방");
        ArrayList<ChatRoom> chatRooms = service.getChatRoomList(id);
        model.addAttribute("chatRooms", chatRooms);
        return "messenger/chatRoomList";
    }
    @GetMapping("chatRoom")
    public String getChatRoom(Model model, int chatRoomId){
        ChatRoom chatRoom = service.getChatRoom(chatRoomId);
        model.addAttribute("chatRoom", chatRoom);
        return "chatRoom";
    }

    @MessageMapping("/sendMessage") // 클라이언트가 메시지를 보낼 경로
    @SendTo("/topic/messages") // 브로드캐스트할 경로
    public String sendMessage(String jsonMessage) {
        // JSON 문자열을 파싱하여 실제 메시지 내용을 추출
//        JsonObject jsonObject = new JsonParser().parse(jsonMessage).getAsJsonObject();
        return jsonMessage;
    }

}
