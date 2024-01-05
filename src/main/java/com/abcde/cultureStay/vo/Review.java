package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
	int reviewNum;
	int programNum;
	String customerID;
	String hostID;
	String reviewerID;
	int reserNum;
	 
	int stars;
	String content;
	String inputdate;
	String who;
}
