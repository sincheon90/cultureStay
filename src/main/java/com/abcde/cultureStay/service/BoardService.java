package com.abcde.cultureStay.service;

import java.util.ArrayList;

import com.abcde.cultureStay.util.PageNavigator;
import com.abcde.cultureStay.vo.Board;

public interface BoardService {

	PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);

	ArrayList<Board> selectList(PageNavigator navi, String type, String searchWord);

	int writeBoard(Board board);

	Board readBoard(int boardnum);

	int deleteBoard(Board board);

	int updateBoard(Board board);

	int recommendCheck(int boardnum, String userid);

	void createRecommend(int boardnum, String userid);

	void deleteRecommend(int boardnum, String userid);

	int recommendCnt(int boardnum);

}
