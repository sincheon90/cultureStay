package com.abcde.cultureStay.service;

import java.util.ArrayList;

import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Review;

public interface ProgramService {
	ArrayList<Program> mainSelect(Program searchProgram, ProgramTag tag);

	int pWrite(Program program);

	Program readProgram(int programNum);

	ArrayList<Review> pReviewList(int programNum);
}
