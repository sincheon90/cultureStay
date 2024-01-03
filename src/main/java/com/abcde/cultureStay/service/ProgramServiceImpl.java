package com.abcde.cultureStay.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.ProgramDAO;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Review;

import lombok.extern.slf4j.Slf4j;

import static com.abcde.cultureStay.util.HtmlUtils.extractText;

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
	
	//프로그램 리스트 화면
	@Override
	public ArrayList<Program> programMainlist(Program searchProgram, ProgramTag tag) {
		log.debug("프로그램 검색 {}",searchProgram);
		log.debug("태그 검색 {}",tag);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("address", searchProgram.getAddress());
		map.put("searchWord", searchProgram.getTitle());
		map.put("start_date", searchProgram.getStart_date());
		map.put("end_date", searchProgram.getEnd_date());

		ArrayList<Program> result = dao.programMainlist(map);

		for (Program r : result) {
			r.setContent(extractText(r.getContent()));
		}
		log.debug("검색결과 {}",result);
	
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
		
		return program;
	}
	
	//최근방문에 추가 
	@Override
	public void recentClick(int programNum, String userid) {
		HashMap<String, Object> map = getMap(programNum, userid);
		log.debug("recentClick map 결과 {}",map);

		
		dao.recentClick(map);

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
		log.debug("유저아이디 {}",userid);
		log.debug("프로그램넘버 {}",programNum);

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
