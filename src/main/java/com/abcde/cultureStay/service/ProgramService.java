package com.abcde.cultureStay.service;

import java.util.ArrayList;

import com.abcde.cultureStay.vo.Checklist;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Reservation;
import com.abcde.cultureStay.vo.Review;

public interface ProgramService {
	ArrayList<Program> programMainlist(Program searchProgram, ProgramTag tag);

	int pWrite(Program program);

	Program readProgram(int programNum);

	ArrayList<Review> pReviewList(int programNum);

	ArrayList<Program> homeRecommend(String id);

	ArrayList<Program> homePopular();

	ProgramTag readProgramTag(int programNum);

	int likeCheck(int programNum, String userid);

	int bookmarkCheck(int programNum, String userid);

	void createLike(int programNum, String userid);

	void deleteLike(int programNum, String userid);

	void createBookmark(int programNum, String userid);

	void deleteBookmark(int programNum, String userid);

	void recentClick(int programNum, String userid);

	int insertReserveForm(Reservation reserveForm);

	ArrayList<Reservation> newReser(String username);

	int pnumCheck(String username);

	int tagInsert(ProgramTag tag);

	void reserveChecklist(Checklist checklist);

	int getReserNum(int programNum, String userid);

	void setReserNum(int reserNum, int programNum, String userid);

	ArrayList<Program> myProgram(String userid);

	Reservation getReservation(int reserNum);

	void acceptReser(int reserNum);

	ArrayList<Reservation> myReservation(String userid);



}
