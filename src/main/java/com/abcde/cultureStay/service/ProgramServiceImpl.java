package com.abcde.cultureStay.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.ProgramDAO;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Review;

@Service
public class ProgramServiceImpl implements ProgramService{

	@Autowired
	ProgramDAO dao;
	
	private HashMap<Program, ProgramTag> getMap(Program type, ProgramTag searchWord) {
		HashMap<Program, ProgramTag> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		return map;
	}
	
	@Override
	public ArrayList<Program> mainSelect(Program searchProgram, ProgramTag tag) {
		HashMap<Program, ProgramTag> map = getMap(searchProgram, tag);

		ArrayList<Program> result = dao.mainSelect(map);
		
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
