package com.abcde.cultureStay.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.ProgramDAO;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.Review;

@Service
public class ProgramServiceImpl implements ProgramService{

	@Autowired
	ProgramDAO dao;
	
	@Override
	public ArrayList<Program> mainSelect() {
		ArrayList<Program> result = dao.mainSelect();
		return result;
	}

	@Override
	public int pWrite(Program program) {
		int result = dao.pWrite(program);		
		return result;
	}
	
	
	@Override
	public Program readProgram(int programNum) {
		
		Program program = dao.readProgram(programNum);
		return program;
	}
	
	@Override
	public ArrayList<Review> pReviewList(int programNum) {
		ArrayList<Review> pReviewList = dao.pReviewList(programNum);
		return pReviewList;
	}
}
