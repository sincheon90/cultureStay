package com.abcde.cultureStay.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.ProgramDAO;
import com.abcde.cultureStay.vo.Checklist;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Reservation;
import com.abcde.cultureStay.vo.Review;

import lombok.extern.slf4j.Slf4j;

import static com.abcde.cultureStay.util.HtmlUtils.*;

@Service
@Slf4j
public class ProgramServiceImpl implements ProgramService{

	@Autowired
	ProgramDAO dao;

	//추천게시물 -최근방문+좋아요+북마크
	@Override
	public ArrayList<Program> homeRecommend(String id) {
		ArrayList<Program> result = dao.homeRecommend(id);

		result = extractPrgramPreview(result);

		log.debug("추천id:{}",id);
		return result;
	}
	//인기게시물 -조회수+좋아요
	@Override
	public ArrayList<Program> homePopular() {
		ArrayList<Program> result = dao.homePopular();

		result = extractPrgramPreview(result);

		return result;
	}
	
	//홈스테이 리스트 화면
	@Override
	public ArrayList<Program> programMainlist(Program searchProgram, ProgramTag tag) {
		log.debug("홈스테이 검색 {}",searchProgram);
		log.debug("태그 검색 {}",tag);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("address", searchProgram.getAddress());
		map.put("searchWord", searchProgram.getTitle());
		map.put("start_date", searchProgram.getStart_date());
		map.put("end_date", searchProgram.getEnd_date());

//		ArrayList<Program> result = dao.programMainlist(map);
		ArrayList<Program> result = dao.searchWithTags(searchProgram, tag);

		// 미리보기용 이미지 및 텍스트 추출
		result = extractPrgramPreview(result);

		log.debug("검색결과 {}",result);
	
		return result;
	}

	//홈스테이 만들기(호스트)
	@Override
	public int pWrite(Program program) {
		int result = dao.pWrite(program);		
		return result;
	}
	
	@Override
	public int pnumCheck(String username) {
		int result = dao.pnumCheck(username);		
		return result;
	}
	
	@Override
	public int tagInsert(ProgramTag tag) {
		int result = dao.tagInsert(tag);		
		return result;
	}
	//홈스테이 상세화면
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
	
	//홈스테이 태그 가져오기
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
		log.debug("홈스테이넘버 {}",programNum);

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

//	
//	@Override
//	public void insertChlist(Checklist chlist) {
//		dao.insertChlist(chlist);
//		
//	}
	@Override
	public int insertReserveForm(Reservation reserveForm) {
		int result = dao.insertReserveForm(reserveForm);		
		return result;
	}
	@Override
	public ArrayList<Reservation> newReser(String userid) {
		ArrayList<Reservation> result = dao.newReser(userid);
		return result;
	}
	
	@Override
	public void reserveChecklist(Checklist checklist) {
		dao.reserveChecklist(checklist);		
	}
	@Override
	public int getReserNum(int programNum, String userid) {
		HashMap<String, Object> map = getMap(programNum, userid);
		int result = dao.getReserNum(map);	
		return result;
	}
	@Override
	public void setReserNum(int reserNum, int programNum, String userid) {
		HashMap<String, Object> map = getMap(programNum, userid);
		map.put("reserNum", reserNum);
		dao.setReserNum(map);	
	}
	
	@Override
	public ArrayList<Program> myProgram(String userid) {
		ArrayList<Program> programList = dao.myProgram(userid);
		

		programList=extractPrgramPreview(programList);

		
		return programList;
	}
	@Override
	public Reservation getReservation(int reserNum) {
		Reservation result = dao.getReservation(reserNum);
		return result;
	}
	
	@Override
	public void acceptReser(int reserNum) {
		dao.acceptReser(reserNum);		
	}
	
	@Override
	public ArrayList<Reservation> myReservation(String userid) {
		ArrayList<Reservation> result = dao.myReservation(userid);
		return result;
	}

}
