package com.abcde.cultureStay.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.ChatDAO;
import com.abcde.cultureStay.vo.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
	@Autowired
	ChatDAO dao;
	
	@Autowired
	ObjectMapper objectMapper;
	
	//private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

	@Override
	public List<ChatRoom> findAllRoom() {
		return new ArrayList<>(chatRooms.values());
	}

	@Override
	public ChatRoom createRoom(String name) {
		  String randomId = UUID.randomUUID().toString();
	        ChatRoom chatRoom = ChatRoom.builder()
	                .roomId(randomId)
	                .name(name)
	                .build();
	        chatRooms.put(randomId, chatRoom);
	        return chatRoom;
	}

	@Override
	public ChatRoom findRoomById(String roomId) {
		return chatRooms.get(roomId);
	}

	

}
