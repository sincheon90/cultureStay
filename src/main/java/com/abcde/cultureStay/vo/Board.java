package com.abcde.cultureStay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	 	int boardnum;
	    String userid;
	    String title;
	    String contents;
	    String inputdate;
	    int hits;
	    String originalfile;
	    String savedfile;
	    int recommend;
	    
	    //추출한 이미지파일 경로
	    String imagePath1;
	    String imagePath2;

}
