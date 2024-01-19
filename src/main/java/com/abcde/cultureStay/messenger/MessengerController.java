package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.Message;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    @GetMapping({"","/"})
    public String chatRoomList(Model model
            , @AuthenticationPrincipal UserDetails user) {
        model.addAttribute("userId", user.getUsername());
        return "messenger/view";
    }

    @MessageMapping("/getChatRoomList")
    @SendTo("/topic/chatRoomList")
    public ArrayList<ChatRoom> sendChatRoomUpdate(String jsonMessage) {
        JsonObject jsonObject = new JsonParser().parse(jsonMessage).getAsJsonObject();
        String userId = String.valueOf(jsonObject.get("userId"));
        ArrayList<ChatRoom> chatRooms = service.getChatRoomList(userId);
        return chatRooms;
    }

    @MessageMapping("/getChatMessages")
    @SendTo("/topic/chatMessages")
    public ArrayList<Message> getChatMessages(String jsonMessage){
        JsonObject jsonObject = new JsonParser().parse(jsonMessage).getAsJsonObject();
        int chatRoomId = jsonObject.get("chatRoomId").getAsInt();

        ArrayList<Message> messages = service.getMessages(chatRoomId);

        return messages;
    }

    @MessageMapping("/sendMessage") // 클라이언트가 메시지를 보낼 경로
    @SendTo("/topic/messages") // 브로드캐스트할 경로
    public String sendMessage(String jsonMessage) {
        // JSON 문자열을 파싱하여 실제 메시지 내용을 추출
        JsonObject jsonObject = new JsonParser().parse(jsonMessage).getAsJsonObject();
        Message message = new Message();
        message.setMessageText(String.valueOf(jsonObject.get("messageText")));
        message.setChatRoomId(jsonObject.get("chatRoomId").getAsLong());
        message.setSenderId(String.valueOf(jsonObject.get("userId")));
        message.setMessageType("text");
        service.saveMessages(message);
        return jsonMessage;
    }

//    @GetMapping({"","/"})
//    public String chatRoomList(Model model
//                              , @AuthenticationPrincipal UserDetails user) {
//        System.out.println("chatRoomList entered");
////        System.out.println("[chatRoomList] get user name : " + user.getUsername());
//        String id = "111";
////        ArrayList<ChatRoom> chatRooms = service.getChatRoomList(user.getUsername());
////        if(!service.isChatRoomPresent(id)) service.createChatRoom(id, "테스트 채팅방");
////        ArrayList<ChatRoom> chatRooms = service.getChatRoomList(id);
////        model.addAttribute("chatRooms", chatRooms);
//        model.addAttribute("userId", user.getUsername());
//        return "messenger/chatRoomList";
//    }


    //    @GetMapping("chatRoom")
//    public String getChatRoom(Model model, int chatRoomId
//            , @AuthenticationPrincipal UserDetails user ){
//        ChatRoom chatRoom = service.getChatRoom(chatRoomId);
//        ArrayList<Message> messages = service.getMessages(chatRoomId);
//        model.addAttribute("chatRoom", chatRoom);
//        model.addAttribute("messages", messages);
//        model.addAttribute("userId", user.getUsername());
//        return "messenger/chatRoom";
//    }
}
