package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface MessengerDAO {
    Long createChatRoom(Map<String, Object> chatRoom);

    ArrayList<ChatRoom> getChatRoomList(String username);

    ChatRoom getChatRoom(Long chatRoomId);

    ArrayList<Message> getMessages(Long chatRoomId);

    Long saveMessage(Message message);

    Long getMemberCount(Long chatRoomId);

    void updateIsRead(Map<String, Object> params);

    Long checkChatRoom(Map<String, Object> map);
}
