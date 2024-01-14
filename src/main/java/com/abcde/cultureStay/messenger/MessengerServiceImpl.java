package com.abcde.cultureStay.messenger;

import com.abcde.cultureStay.vo.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessengerServiceImpl implements MessengerService{
    @Override
    public ArrayList<ChatRoom> getChatRoomList(String username) {
        return null;
    }

    @Override
    public ChatRoom createChatRoom() {
        return null;
    }

    @Override
    public ChatRoom getChatRoom(int chatRoomId) {
        return null;
    }
}
