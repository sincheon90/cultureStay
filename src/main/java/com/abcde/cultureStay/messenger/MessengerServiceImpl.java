package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        Long unReadCount = dao.getMemberCount(message.getChatRoomId()) - 1; // 작성자는 읽었기 때문에
        message.setIsRead(unReadCount);
        return dao.saveMessages(message);
    }

    @Override
    public void updateIsRead(Long chatRoomId, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("chatRoomId", chatRoomId);
        map.put("userId", userId);
        dao.updateIsRead(map);
    }
}
