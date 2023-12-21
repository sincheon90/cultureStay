package com.abcde.cultureStay.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.ProgramDAO;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Review;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProgramServiceImpl implements ProgramService{

	@Autowired
	ProgramDAO dao;
	
	//추천게시물 -최근방문+좋아요+북마크
	@Override
	public ArrayList<Program> homeRecommend(String id) {
		ArrayList<Program> result = dao.homeRecommend(id);
		log.debug("추천id:{}",id);
		return result;
	}
	//인기게시물 -조회수+좋아요
	@Override
	public ArrayList<Program> homePopular() {
		ArrayList<Program> result = dao.homePopular();
		
		return result;
	}
	
	//프로그램 메인화면
	@Override
	public ArrayList<Program> programMainlist(Program searchProgram, ProgramTag tag) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("searchProgram", searchProgram);
		map.put("tag", tag);

		ArrayList<Program> result = dao.programMainlist(map);
		
	
		return result;
	}

	//프로그램 만들기(호스트)
	@Override
	public int pWrite(Program program) {
		int result = dao.pWrite(program);		
		return result;
	}
	
	//프로그램 상세화면
	@Override
	public Program readProgram(int programNum) {
		
		Program program = dao.readProgram(programNum);
		//조회수
		dao.p_updateHits(programNum);
		//최근방문
		dao.recentClick(programNum);
		return program;
	}
	//상세화면 리뷰
	@Override
	public ArrayList<Review> pReviewList(int programNum) {
		ArrayList<Review> pReviewList = dao.pReviewList(programNum);
		return pReviewList;
	}

	


}
