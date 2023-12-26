package com.abcde.cultureStay.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Program {
	int programNum;
	String userid;
	String title;
	String content;
	String address;
	String detailed_address;

	int price;
	String start_date;
	String end_date;
	String inputdate;
	int hits;
	
  
}