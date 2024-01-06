package com.abcde.cultureStay.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.abcde.cultureStay.vo.ChatMessage;
import com.abcde.cultureStay.vo.ChatRoom;

@Mapper
public interface ChatDAO {

	ArrayList<ChatRoom> showChatRoomAll(String id);

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
