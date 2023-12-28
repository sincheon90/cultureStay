package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//최근 방문 프로그램 5개 테이블
@Data
@AllArgsConstructor
@NoArgsConstructor
public class recentClick {
	int clickNum;
	String userid;
	int programNum;
	String inputdate;  
}
