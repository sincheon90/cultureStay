package com.abcde.cultureStay.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.BoardDAO;
import com.abcde.cultureStay.util.PageNavigator;
import com.abcde.cultureStay.vo.Board;
import com.abcde.cultureStay.vo.Program;

import static com.abcde.cultureStay.util.HtmlUtils.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

		ArrayList<Board> boards = dao.selectList(map, rb);

		boards = extractBoardPreview(boards);

		for (Board board: boards) {
			board.setContents(extractText(board.getContents()));
		}

		return boards;
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

	@Override
	public int updateBoard(Board board) {
		int result = dao.updateBoard(board);
		return result;
	}

//	@Override
//	public int recommendCheck(int boardnum, String userid) {
//		HashMap<String, Object> recommendMap =  getMap(boardnum, userid);
//		log.debug("맵 {}",recommendMap);
//		Integer result =  dao.recommendCheck(recommendMap);
//		if(result == null) {
//			result = 0;
//		}
//		return result;
//	}

//	private HashMap<String, Object> getMap(int boardnum, String userid) {
//		HashMap<String, Object> recommendMap = new HashMap<>();
//		recommendMap.put("boardnum", boardnum);
//		recommendMap.put("userid", userid);
//		return recommendMap;
//	}

//	@Override
//	public void createRecommend(int boardnum, String userid) {
//
//
//		HashMap<String, Object> recommendMap = getMap(boardnum, userid);
//		dao.createRecommend(recommendMap);
//
//	}
//
//	@Override
//	public void deleteRecommend(int boardnum, String userid) {
//		HashMap<String, Object> recommendMap = getMap(boardnum, userid);
//		dao.deleteRecommend(recommendMap);
//
//	}
//
//	@Override
//	public int recommendCnt(int boardnum) {
//		Integer result =  dao.recommendCnt(boardnum);
//		if(result == null) {
//			result = 0;
//		}
//		return result;
//	}

	@Override
	public void addLike(int boardnum, String id) {
		HashMap<String, Object> map = getMap(boardnum, id);
		dao.addLike(map);
	}

	private HashMap<String, Object> getMap(int boardnum, String id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("boardnum", boardnum);
		map.put("id" , id);
		return map;
	}
	
	@Override
	public int checkLike(int boardnum, String id) {
		HashMap<String, Object> map = getMap(boardnum, id);
		int check = dao.checkLike(map);
		return check;
	}

	@Override
	public void upLike(int boardnum) {
		dao.upLike(boardnum);
		
	}

	@Override
	public int selectCnt(int boardnum) {
		int cnt = dao.selectCnt(boardnum);
		return cnt;
	}

	@Override
	public void deleteLike(int boardnum, String id) {
		HashMap<String, Object> map = getMap(boardnum, id);
		dao.deleteLike(map);
		
	}

	@Override
	public void downLike(int boardnum) {
		dao.downLike(boardnum);
		
	}

	@Override
	public ArrayList<Board> popularBoards() {
		ArrayList<Board> result = dao.popularBoards();

		if(result != null) result = extractBoardPreview(result);

		return result;
	}

}
