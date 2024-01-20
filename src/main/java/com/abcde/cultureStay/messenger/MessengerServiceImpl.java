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

    /*
    [updateIsRead]
    단체방일때는 읽은 메시지 현황을 테이블로 추가하여 로직을 만들어야 함.
    (현재는 2명으로 제한하여 개발중)

    읽으면 -1 또 읽으면 0 플러스.
    내 아이디로 읽었는지 안읽었는지 테이블 추가가 필요.
    어떤 메시지까지는 읽은 메시지(0) 어떤 메시부터는 안 읽은 메시지(-1)인지
    timestamp 기준으로 update하면 성능에 유리할듯.
    
    작성자 : 오정권
    작성일자 : 2024-01-20
    */
    @Override
    public void updateIsRead(Long chatRoomId, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("chatRoomId", chatRoomId);
        map.put("userId", userId);
        dao.updateIsRead(map);
    }
}
