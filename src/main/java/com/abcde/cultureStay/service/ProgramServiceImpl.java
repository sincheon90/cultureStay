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
	
	//프로그램 태그 가져오기
	@Override
	public ProgramTag readProgramTag(int programNum) {
		ProgramTag result = dao.readProgramTag(programNum);

		return result;
	}
	
	private HashMap<String, Object> getMap(int programNum, String userid) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("programNum", programNum);
		map.put("userid", userid);
		return map;
	}
	
	//좋아요상태
	@Override
	public int likeCheck(int programNum, String userid) {
		HashMap<String, Object> map =  getMap(programNum, userid);

		Integer result =  dao.likeCheck(map);
		if(result == null) {
			result = 0;
		}
		return result;
	}
	//북마크상태
	@Override
	public int bookmarkCheck(int programNum, String userid) {
		HashMap<String, Object> map = getMap(programNum, userid);
		
		Integer result = dao.bookmarkCheck(map);
		if(result == null) {
			result = 0;
		}
		return result;
		
	}
	//좋아요
	@Override
	public void createLike(int programNum, String userid) {
		HashMap<String, Object> map = getMap(programNum, userid);
		dao.createLike(map);
	}
	@Override
	public void deleteLike(int programNum, String userid) {
		HashMap<String, Object> map = getMap(programNum, userid);
		dao.deleteLike(map);
	}
	//북마크
	@Override
	public void createBookmark(int programNum, String userid) {
		HashMap<String, Object> map = getMap(programNum, userid);
		dao.createBookmark(map);
	}
	@Override
	public void deleteBookmark(int programNum, String userid) {
		HashMap<String, Object> map = getMap(programNum, userid);
		dao.deleteBookmark(map);
	}

	


}
