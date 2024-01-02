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
	String userid;	
	String start_date;
	String end_date;
	String request;	
    int payment; 	
    int status;	
    String inputdate;
}
