package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
	
	private long roomId; // 채팅방 아이디
	private String userid; //id;// 사용자 아이디
	private String host_id;//board_id; // 블로그 사용자
	private long chat_pbno; //채팅방 프로그램넘버                 //bbno; // 블로그 번호
	private String created_day; // 생성일
	private String name; // 사용자 이름
	private String title;
	private String thumbnail;
	private long pbno; //program number
	// private String roomName;

}
