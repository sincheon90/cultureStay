package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/messenger")
public class messengerApiController {

    @Autowired
    MessengerService service;

    @GetMapping("createChatRoom")
    ArrayList<Message> createChatRoom(@RequestParam String chatPartner,
                                      @AuthenticationPrincipal UserDetails user) {
        ArrayList<Message> messages;
        Long chatRoomId = Long.valueOf(-1);
        chatRoomId = service.checkChatRoom(chatPartner, user.getUsername());
        if(chatRoomId > 0) {
            return service.getMessages(chatRoomId);
        }
        else {
            service.createChatRoom(user.getUsername() , chatPartner + "와의 대화", chatPartner);
            return null;
        }
    }
}
