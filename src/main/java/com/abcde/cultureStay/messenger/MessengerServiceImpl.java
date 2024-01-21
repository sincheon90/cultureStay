package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.ChatRoomMember;
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
    public Long createChatRoom(String createdUser, String chatRoomName, String chatPartner) {
        Map<String, Object> map = new HashMap<>();
        map.put("createdUser", createdUser);
        map.put("chatRoomName", chatRoomName);
        map.put("chatPartner", chatPartner);

        return dao.createChatRoom(map);
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
    현재는 채팅방 수의 인원을 2명으로 제한하여 개발중
    sender가 아니면 모두 -1. 0이면 안함.

    [단체방으로 변경시]
    읽은 메시지 현황을 테이블로 추가하여 로직을 만들어야 함.

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

    @Override
    public Long checkChatRoom(String chatPartner, String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("chatPartner", chatPartner);
        map.put("username", username);
        return dao.checkChatRoom(map);
    }
}
