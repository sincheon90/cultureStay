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

	Integer recommendCheck(HashMap<String, Object> recommendMap);

	void createRecommend(HashMap<String, Object> recommendMap);

	void deleteRecommend(HashMap<String, Object> recommendMap);

	Integer recommendCnt(int boardnum);

	void addLike(HashMap<String, Object> map);

	int checkLike(HashMap<String, Object> map);

	void upLike(int boardnum);

	int selectCnt(int boardnum);

	void deleteLike(HashMap<String, Object> map);

	void downLike(int boardnum);

	ArrayList<Board> popularBoards();
	

}
