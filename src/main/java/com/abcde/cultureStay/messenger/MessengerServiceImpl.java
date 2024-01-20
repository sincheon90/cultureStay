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
    public Long saveMessage(Message message) {
        Long memberCount = dao.getMemberCount(message.getChatRoomId());
        Long unReadCount = (memberCount > 0 ? memberCount : 1) - 1; // 작성자는 읽었기 때문에 -1
        message.setIsRead(unReadCount);
        dao.saveMessage(message);
        return unReadCount;
    }

    @Override
    public void updateIsRead(Long chatRoomId, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("chatRoomId", chatRoomId);
        map.put("userId", userId);
        dao.updateIsRead(map);
    }
}
