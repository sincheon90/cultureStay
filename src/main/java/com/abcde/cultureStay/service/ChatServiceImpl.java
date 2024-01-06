package com.abcde.cultureStay.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.ChatDAO;
import com.abcde.cultureStay.vo.ChatMessage;
import com.abcde.cultureStay.vo.ChatRoom;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService{
	@Autowired
	ChatDAO dao;

	@Override
	public ArrayList<ChatRoom> showChatRoomAll(String userid) {
		 log.info("id: {}", userid);
		    ArrayList<ChatRoom> chatRooms = dao.showChatRoomAll(userid);
		    log.info("chatRoom service: {}", chatRooms);
			return chatRooms;
	}

	@Override
	public ArrayList<ChatMessage> findByMessage(ChatRoom chatRoom) {
		ArrayList<ChatMessage> chatMessage = dao.findByMessage(chatRoom);
		return chatMessage;
	}

	@Override
	public int selectChatRoom(ChatRoom chatRoom) {
		int ChatRoomNum = dao.selectChatRoom(chatRoom);
		return ChatRoomNum;
	}

	@Override
	public ArrayList<ChatRoom> showChatRoom(int bbno) {
		ArrayList<ChatRoom> chatRoom = dao.showChatRoom(bbno);
		return chatRoom;
	}

	@Override
	public void createChatRoom(ChatRoom chatRoom) {
		dao.createChatRoom(chatRoom);
		
	}

	@Override
	public ChatRoom findRoomById(ChatRoom chatRoom) {
		ChatRoom result = dao.findRoomById(chatRoom);
		return result;
	}

	@Override
	public ChatRoom selectByChatRoom(int roomId) {
		ChatRoom chatRoom = dao.selectByChatRoom(roomId);
		return chatRoom;
	}

	@Override
	public void saveMessage(ChatMessage message) {
		log.info("board_id {}", message.getWriter());
		dao.saveMessage(message);
		
	}

	@Override
	public String findByBoardId(int roomId) {
		String boardId = dao.findByBoardId(roomId);
		return boardId;
	}

	@Override
	public String findByMemberId(int roomId) {
		String memberId = dao.findByMemberId(roomId);
		return memberId;
	}

	@Override
	public int findByBbno(int roomId) {
		int bbno = dao.findByBbno(roomId);
		return bbno;
	}

}
