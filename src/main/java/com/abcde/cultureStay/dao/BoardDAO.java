package com.abcde.cultureStay.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.abcde.cultureStay.vo.Board;

@Mapper
public interface BoardDAO {

	ArrayList<Board> selectList(HashMap<String, String> map, RowBounds rb);

	int countTotal(HashMap<String, String> map);

	int writeBoard(Board board);

	void updateHits(int boardnum);

	Board readBoard(int boardnum);

	int deleteBoard(Board board);

	int updateBoard(Board board);

	void updateLikehit(int boardnum);

	int selectLikehit(int boardnum);

	
	

	

}
