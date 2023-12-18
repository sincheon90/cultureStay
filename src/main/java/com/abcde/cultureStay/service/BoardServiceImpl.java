package com.abcde.cultureStay.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.BoardDAO;
import com.abcde.cultureStay.util.PageNavigator;
import com.abcde.cultureStay.vo.Board;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardDAO dao;

	@Override
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type,
			String searchWord) {
		
				HashMap<String, String> map = getMap(type, searchWord);
				// 검색 키워드가 있든 없든 전체글수를 DB로 부터 조회하기
				// 전체 글 수
				int total = dao.countTotal(map);
				
				PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total);
				
				return navi;
	}

	@Override
	public ArrayList<Board> selectList(PageNavigator navi, String type, String searchWord) {
		HashMap<String, String> map = getMap(type, searchWord);
		
		// MyBatis 에서 제공해주는 record를 관리하는 객체 RowBounds
		// param 2개 : 1=시작레코드, 2=몇개가져올지 
		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());
		
		ArrayList<Board> result = dao.selectList(map, rb);
		return result;
	}

	private HashMap<String, String> getMap(String type, String searchWord) {
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		return map;
	}

	@Override
	public int writeBoard(Board board) {
		int result = dao.writeBoard(board);
		return result;
	}

	@Override
	public Board readBoard(int boardnum) {
		//조회수 증가
				dao.updateHits(boardnum);
				//글정보 가져오기
				Board board = dao.readBoard(boardnum);
				return board;
	}

	@Override
	public int deleteBoard(Board board) {
		int result = dao.deleteBoard(board);
		return result;
	}

}
