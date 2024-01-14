package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.Message;

import java.util.ArrayList;

public interface MessengerService {
    ArrayList<ChatRoom> getChatRoomList(String username);

    int createChatRoom(String id, String 테스트_채팅방);

    ChatRoom getChatRoom(int chatRoomId);

    boolean isChatRoomPresent(String id);

    ArrayList<Message> getMessages(int chatRoomId);

    int saveMessages(Message message);
}
