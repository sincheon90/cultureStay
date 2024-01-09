package com.abcde.cultureStay.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.abcde.cultureStay.vo.ChatRoom;



@Service
public interface ChatService {

	List<ChatRoom> findAllRoom();

	ChatRoom createRoom(String name);

	ChatRoom findRoomById(String roomId);

}
