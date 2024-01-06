package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
	
	private long ano; //알람 넘버
	private String member_id;
	private String code;
	private String checked;
	private String created_day;
	private String prefix;
	private long bbno;
	private long room_id;

}
