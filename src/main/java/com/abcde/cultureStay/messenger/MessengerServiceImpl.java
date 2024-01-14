package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessengerServiceImpl implements MessengerService{

    @Autowired
    MessengerDAO dao;

    @Override
    public ArrayList<ChatRoom> getChatRoomList(String username) {
        return dao.getChatRoomList(username);
    }

    @Override
    public int createChatRoom(String createdUser, String roomName) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setCreatedUserId(createdUser);
        chatRoom.setChatRoomName(roomName);
        return dao.createChatRoom(chatRoom);
    }

    @Override
    public ChatRoom getChatRoom(int chatRoomId) {
        return null;
    }

    @Override
    public boolean isChatRoomPresent(String id) {
        return false;
    }
}
