package com.abcde.cultureStay.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.abcde.cultureStay.vo.Program;

@Mapper
public interface ProgramDAO {

	ArrayList<Program> mainSelect();

}
