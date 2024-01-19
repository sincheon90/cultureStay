package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.Message;
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
    public Long createChatRoom(String createdUser, String roomName) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setCreatedUserId(createdUser);
        chatRoom.setChatRoomName(roomName);
        return dao.createChatRoom(chatRoom);
    }

    @Override
    public ChatRoom getChatRoom(Long chatRoomId) {
        return dao.getChatRoom(chatRoomId);
    }

    @Override
    public boolean isChatRoomPresent(String id) {
        return false;
    }

    @Override
    public ArrayList<Message> getMessages(Long chatRoomId) {
        return dao.getMessages(chatRoomId);
    }

    @Override
    public Long saveMessages(Message message) {
        return dao.saveMessages(message);
    }
}
