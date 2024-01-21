package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.Message;

import java.util.ArrayList;

public interface MessengerService {
    ArrayList<ChatRoom> getChatRoomList(String username);

    Long createChatRoom(String createdUser, String chatRoomName, String chatPartner);

    ChatRoom getChatRoom(Long chatRoomId);

    boolean isChatRoomPresent(String id);

    ArrayList<Message> getMessages(Long chatRoomId);

    Long saveMessage(Message message);

    void updateIsRead(Long chatRoomId, String userId);

    Long checkChatRoom(String chatPartner, String username);
}
