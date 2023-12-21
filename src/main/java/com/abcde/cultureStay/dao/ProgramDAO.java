package com.abcde.cultureStay.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Review;

@Mapper
public interface ProgramDAO {
	
	ArrayList<Program> homePopular();

	ArrayList<Program> homeRecommend(String id);
	
	ArrayList<Program> programMainlist(HashMap<String, Object> map);

	int pWrite(Program program);

	Program readProgram(int programNum);

	ArrayList<Review> pReviewList(int programNum);

	void p_updateHits(int programNum);

	void recentClick(int programNum);



}
