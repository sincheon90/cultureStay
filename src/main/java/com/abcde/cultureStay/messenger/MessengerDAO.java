package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MessengerDAO {
    Long createChatRoom(ChatRoom chatRoom);

    ArrayList<ChatRoom> getChatRoomList(String username);

    ChatRoom getChatRoom(Long chatRoomId);

    ArrayList<Message> getMessages(Long chatRoomId);

    Long saveMessages(Message message);

    Long getMemberCount(Long chatRoomId);

    void updateIsRead(Long chatRoomId, String userId);
}
