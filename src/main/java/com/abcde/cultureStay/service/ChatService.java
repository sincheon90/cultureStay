package com.abcde.cultureStay.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.abcde.cultureStay.vo.ChatMessage;
import com.abcde.cultureStay.vo.ChatRoom;

@Service
public interface ChatService {

	ArrayList<ChatRoom> showChatRoomAll(String userid);

	ArrayList<ChatMessage> findByMessage(ChatRoom chatRoom);

	int selectChatRoom(ChatRoom chatRoom);

	ArrayList<ChatRoom> showChatRoom(int bbno);

	void createChatRoom(ChatRoom chatRoom);

	ChatRoom findRoomById(ChatRoom chatRoom);

	ChatRoom selectByChatRoom(int roomId);

	void saveMessage(ChatMessage message);

	String findByBoardId(int roomId);

	String findByMemberId(int roomId);

	int findByBbno(int roomId);

}
