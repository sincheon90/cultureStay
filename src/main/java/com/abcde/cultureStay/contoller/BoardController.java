package com.abcde.cultureStay.contoller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.abcde.cultureStay.service.BoardService;
import com.abcde.cultureStay.service.ReplyService;
import com.abcde.cultureStay.util.PageNavigator;
import com.abcde.cultureStay.vo.Board;
import com.abcde.cultureStay.vo.Reply;
import com.abcde.cultureStay.util.FileService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("board")
public class BoardController {
	
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
	@Value("${user.board.page}")
	int countPerPage;
	
	@Value("${user.board.group}")
	int pagePerGroup;
	
	@Autowired
	BoardService service;
	
	@Autowired
	ReplyService rService;
	
	@GetMapping("boardList")
	public String boardList(Model model
			, @RequestParam(name = "page", defaultValue = "1") int page
			, String type
			, String searchWord) {
		
		// PageNavigator 객체 생성
		PageNavigator navi = service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);
		
		// DB의 spring5_board 테이블의 전 데이터를 가져오기
		ArrayList<Board> boardList = service.selectList(navi, type, searchWord);
		// model에다가 저장하기
		model.addAttribute("navi", navi);
		model.addAttribute("boardList", boardList);
		model.addAttribute("type", type);
		model.addAttribute("searchWord", searchWord);
		
		return "board/boardList";
	}
	
	/**
	 * 글쓰기 폼으로 이동
	 * @return
	 */
	@GetMapping("write")
	public String writeForm() {
		return "board/writeForm";
	}
	
	/**
	 * 글쓰기 입력데이터 저장
	 * @return
	 */
	@PostMapping("write")
	public String writeForm(Board board, MultipartFile upload
			, @AuthenticationPrincipal UserDetails user) {
		
		log.debug("write_board: {}", board);
		log.debug("write_upload: {}", upload.getOriginalFilename());
		
		board.setUserid(user.getUsername());
		
		if(!upload.isEmpty()) {
			String savedfile = FileService.saveFile(upload, uploadPath);
			board.setOriginalfile(upload.getOriginalFilename());
			board.setSavedfile(savedfile);
		}
		
		int result = service.writeBoard(board);
		
		return "redirect:/board/boardList";
	}
	
	@GetMapping("read")
	public String read(@RequestParam(name = "boardnum", defaultValue = "0") int boardnum,
			Model model) {
		log.debug("read_param: {}", boardnum);

		// DB에서 글번호에 일치하는 글정보를 가져오기
		Board board = service.readBoard(boardnum);
		
		//리플 목록 가져오기
		ArrayList<Reply> replyList = rService.replyList(boardnum);
		
		// model 객체를 이용해 readForm.html에 출력하기
		model.addAttribute("board", board);
		model.addAttribute("replyList", replyList);
		
		return "board/readForm";
	}
	
	@GetMapping("download")
	public void fileDownload(int boardnum, Model model, HttpServletResponse response) {
		//전달된 글 번호로 글 정보 조회
		Board board = service.readBoard(boardnum);
		if (board == null) return;
		
		//원래의 파일명
		String originalfile = new String(board.getOriginalfile());
		try {
			response.setHeader("Content-Disposition", " attachment;filename="+ URLEncoder.encode(originalfile, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//저장된 파일 경로
		String fullPath = uploadPath + "/" + board.getSavedfile();
		
		//서버의 파일을 읽을 입력 스트림과 클라이언트에게 전달할 출력스트림
		FileInputStream filein = null;
		ServletOutputStream fileout = null;
		
		try {
			filein = new FileInputStream(fullPath);
			fileout = response.getOutputStream();
			
			//Spring의 파일 관련 유틸 이용하여 출력
			FileCopyUtils.copy(filein, fileout);
			
			filein.close();
			fileout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("delete")
	public String delete(int boardnum
			, @AuthenticationPrincipal UserDetails user) {
		
		Board board = service.readBoard(boardnum);
		String savedfile = board.getSavedfile();
		
		if(board.getUserid().equals(user.getUsername())) {
			//삭제처리
			int result = service.deleteBoard(board);
			
			if(result == 1 && savedfile != null) {
				FileService.deleteFile(uploadPath + "/" + savedfile);
			}
		}
		
		
		return "redirect:/board/boardList";
	}
	

}
