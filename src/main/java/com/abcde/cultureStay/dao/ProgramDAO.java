package com.abcde.cultureStay.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.abcde.cultureStay.vo.Checklist;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Reservation;
import com.abcde.cultureStay.vo.Review;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProgramDAO {
	
	ArrayList<Program> homePopular();

	ArrayList<Program> homeRecommend(String id);
	
	ArrayList<Program> programMainlist(Map<String, Object> map);

	int pWrite(Program program);

	Program readProgram(int programNum);

	ArrayList<Review> pReviewList(int programNum);

	void p_updateHits(int programNum);

	void recentClick(HashMap<String, Object> map);

	ProgramTag readProgramTag(int programNum);

	Integer likeCheck(HashMap<String, Object> map);

	Integer bookmarkCheck(HashMap<String, Object> map);

	void createLike(HashMap<String, Object> map);

	void deleteLike(HashMap<String, Object> map);

	void createBookmark(HashMap<String, Object> map);

	void deleteBookmark(HashMap<String, Object> map);

	void insertChlist(Checklist chlist);

	int insertReserveForm(Reservation reserveForm);

	ArrayList<Reservation> newReser(String userid);

	int pnumCheck(String username);

	int tagInsert(ProgramTag tag);

	void reserveChecklist(Checklist checklist);

	int getReserNum(HashMap<String, Object> map);

	void setReserNum(HashMap<String, Object> map);

	ArrayList<Program> myProgram(String userid);

	Reservation getReservation(int reserNum);

	void acceptReser(int reserNum);

	ArrayList<Reservation> myReservation(String userid);

	void guestReview(Review review);

	void hostReview(Review review);

	void programReview(Review review);

	ArrayList<Review> getHostReview(String userid);

	ArrayList<Review> getProgramReview(int programNum);

	ArrayList<Review> getGuestReview(String userid);

	ArrayList<Review> getMyReview(String userid);

	ArrayList<Program> getmyBookmark(String userid);

	Checklist getChecklist(int reserNum);

	double hostAvg(String userid);

	ArrayList<Review> myProgramReview(String userid);

	ArrayList<Program> searchWithTags(@Param("program") Program program, @Param("tag") ProgramTag tag);

	Double programAvg(int programNum);

	


}
