package com.abcde.cultureStay.contoller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abcde.cultureStay.service.ChatService;
import com.abcde.cultureStay.vo.ChatMessage;
import com.abcde.cultureStay.vo.ChatRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("chat")
public class ChatController {
	
	@Autowired
	ChatService service;
	
	
	@GetMapping("showChatRoom")
	public String showChatRoom(@AuthenticationPrincipal UserDetails user, Model model) {
		log.info("user id : {}", user.getUsername());   //한번만 호출하도록 수정
		ArrayList<ChatRoom> chatRooms = service.showChatRoomAll(user.getUsername());
		
		log.info("chatriin {}", chatRooms);
		
		if(chatRooms != null && !chatRooms.isEmpty()) {
			model.addAttribute("roomList", chatRooms);
			log.info("roomList : {}", chatRooms);
			model.addAttribute("room", chatRooms.get(0));
			
			ArrayList<ChatMessage> chatmessage = service.findByMessage(chatRooms.get(0));
			
			if(chatmessage != null) {
				model.addAttribute("chatMessage", chatmessage);
			}
			
			log.info("로그 확인 {}", chatmessage);
		}else {
			chatRooms = null;
			model.addAttribute("roomList", chatRooms);
		}
		return "chat/room";
	}
	
	@PostMapping("chatRoom")
	public String rooms(Model model, ChatRoom chatRoom, @AuthenticationPrincipal UserDetails user, int bbno,
			String boardId) {
		
		chatRoom.setUserid(user.getUsername());
		chatRoom.setHost_id(boardId);
		chatRoom.setChat_pbno(bbno);
		log.info("# All Chat Rooms");

		log.info("chatRoom :  {}", chatRoom);
		int chatRoomNum = service.selectChatRoom(chatRoom);

		if (chatRoom.getHost_id().equals(user.getUsername())) {
			
			ArrayList<ChatRoom> chatRoomList = service.showChatRoomAll(user.getUsername());
			
			model.addAttribute("roomList", chatRoomList);
			
			ArrayList<ChatRoom> chatRoomByBoard = service.showChatRoom(bbno);
			model.addAttribute("room", chatRoomByBoard.get(0));
			log.info(boardId);
			ArrayList<ChatMessage> chatmessage = service.findByMessage(chatRoomByBoard.get(0));

			if (chatmessage != null) {
				model.addAttribute("chatMessage", chatmessage);
			}

			log.info("로그 확인 {}", chatmessage);

			return "chat/room";

		} else {
			if (chatRoomNum == 0) {

				service.createChatRoom(chatRoom);
			}
			
			ArrayList<ChatRoom> chatRoomList = service.showChatRoomAll(user.getUsername());

			ChatRoom chatrooms = service.findRoomById(chatRoom);
			
			model.addAttribute("roomList", chatRoomList);

			model.addAttribute("room", chatrooms);

			ArrayList<ChatMessage> chatmessage = service.findByMessage(chatrooms);

			if (chatmessage != null) {
				model.addAttribute("chatMessage", chatmessage);
			}

			log.info("로그 확인 {}", chatmessage);

			return "chat/room";
		}
	}
	
	@PostMapping("BoardchatRoom")
	public String rooms(Model model, ChatRoom chatRoom, @AuthenticationPrincipal UserDetails user, int roomId,
			int bbno) {
		ArrayList<ChatRoom> chatRoomList = service.showChatRoomAll(user.getUsername());

		model.addAttribute("roomList", chatRoomList);
		ChatRoom selectRoom = service.selectByChatRoom(roomId);
		model.addAttribute("room", selectRoom);
		ArrayList<ChatMessage> chatmessage = service.findByMessage(selectRoom);

		if (chatmessage != null) {
			model.addAttribute("chatMessage", chatmessage);
		}

		log.info("로그 확인 {}", chatmessage);

		return "chat/room";
	}
	

}
