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
	    int likehit=0;

}
