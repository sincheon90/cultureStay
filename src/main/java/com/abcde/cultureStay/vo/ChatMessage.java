package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
	
	private int roomId;
	private int bbno;
	private String writer;
	private String message;
	private String created_day;
	private String name;
	private String board_id;

}
