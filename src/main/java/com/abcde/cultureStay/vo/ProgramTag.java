package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramTag {
	//프로그램 태그, 특징, 서치필터
	//1,0으로 특징 구분
	int programNum;
	int apartment;
	int detached; //단독주택
	//추가예정
}