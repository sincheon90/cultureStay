package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
	int reserNum;
	int programNum;	
	String hostid;
	String userid;	
	String start_date;
	String end_date;
	String request;	
    int payment; 	
    int status;	
    String inputdate;
  //추출한 이미지파일 경로
    String imagePath1;
    String imagePath2;
}
