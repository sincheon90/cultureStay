package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.vo.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

public interface MessengerService {
    ArrayList<ChatRoom> getChatRoomList(String username);

    ChatRoom createChatRoom();

    ChatRoom getChatRoom(int chatRoomId);
}
