package com.abcde.cultureStay.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.Review;

@Mapper
public interface ProgramDAO {

	ArrayList<Program> mainSelect();

	int pWrite(Program program);

	Program readProgram(int programNum);

	ArrayList<Review> pReviewList(int programNum);

}
