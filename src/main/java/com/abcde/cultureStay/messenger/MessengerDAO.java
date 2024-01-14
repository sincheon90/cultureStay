package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.messenger.vo.ChatRoom;
import com.abcde.cultureStay.messenger.vo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface MessengerDAO {
    int createChatRoom(ChatRoom chatRoom);

    ArrayList<ChatRoom> getChatRoomList(String username);

    ChatRoom getChatRoom(int chatRoomId);

    ArrayList<Message> getMessages(int chatRoomId);

    int saveMessages(Message message);
}
