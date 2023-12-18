package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	int 	replynum;			//댓글번호
	int 	boardnum;			//본문 글번호
	String 	userid;			//작성자 ID
	String 	content;			//내용
	String 	inputdate;			//작성일
	int     recommend;			//추천
}
