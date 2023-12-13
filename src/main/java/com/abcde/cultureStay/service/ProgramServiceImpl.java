package com.abcde.cultureStay.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.ProgramDAO;
import com.abcde.cultureStay.vo.Program;

@Service
public class ProgramServiceImpl implements ProgramService{

	@Autowired
	ProgramDAO dao;
	
	@Override
	public ArrayList<Program> mainSelect() {
		ArrayList<Program> result = dao.mainSelect();
		return result;
	}
}
