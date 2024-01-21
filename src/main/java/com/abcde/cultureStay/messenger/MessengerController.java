package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.Message;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping({"","/"})
    public String chatRoomList(Model model
            , @AuthenticationPrincipal UserDetails user) {
        model.addAttribute("userId", user.getUsername());
        return "messenger/view";
    }

    @MessageMapping("/getChatRoomList")
    public ArrayList<ChatRoom> sendChatRoomUpdate(String jsonMessage) {
        JsonObject jsonObject = new JsonParser().parse(jsonMessage).getAsJsonObject();
        String userId = jsonObject.get("userId").getAsString();
        ArrayList<ChatRoom> chatRooms = service.getChatRoomList(userId);

        template.convertAndSend("/topic/chatRoomList/" + userId, chatRooms);
        return chatRooms;
    }

    @MessageMapping("/getMessages")
    public ArrayList<Message> getChatMessages(String jsonMessage){
        JsonObject jsonObject = new JsonParser().parse(jsonMessage).getAsJsonObject();
        Long chatRoomId = jsonObject.get("chatRoomId").getAsLong();
        String userId = jsonObject.get("userId").getAsString();

        service.updateIsRead(chatRoomId, userId);
        ArrayList<Message> messages = service.getMessages(chatRoomId);

        // 특정 채팅방 구독자들에게 메시지 리스트 전송
        template.convertAndSend("/topic/chatRoom/" + chatRoomId, messages);
        return messages;
    }

    @MessageMapping("/sendMessage") // 클라이언트가 메시지를 보낼 경로
    public String sendMessage(String jsonMessage) {
        // JSON 문자열을 파싱하여 실제 메시지 내용을 추출
        JsonObject jsonObject = new JsonParser().parse(jsonMessage).getAsJsonObject();
        Message message = new Message();
        message.setMessageText(jsonObject.get("messageText").getAsString());
        message.setChatRoomId(jsonObject.get("chatRoomId").getAsLong());
        message.setSenderId(jsonObject.get("senderId").getAsString());
        message.setMessageType("text");

        Long isRead = service.saveMessage(message);
        jsonObject.addProperty("isRead", isRead);

        // 특정 채팅방 구독자들에게 메시지 전송
        template.convertAndSend("/topic/sendMessage/" + message.getChatRoomId(), message);
        return jsonObject.toString();
    }
}
