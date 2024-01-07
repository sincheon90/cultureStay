package com.abcde.cultureStay.vo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
	
	  private String roomId;
	    private String name;
	    private Set<WebSocketSession> sessions = new HashSet<>();
	    @Builder
	    public ChatRoom(String roomId, String name) {
	        this.roomId = roomId;
	        this.name = name;
	    }

}
