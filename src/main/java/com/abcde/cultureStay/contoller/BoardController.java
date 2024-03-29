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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.abcde.cultureStay.dao.BoardDAO;
import com.abcde.cultureStay.service.BoardService;
import com.abcde.cultureStay.service.ReplyService;
import com.abcde.cultureStay.util.PageNavigator;
import com.abcde.cultureStay.vo.Board;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Reply;
import com.abcde.cultureStay.vo.Review;
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
	
	@Autowired
	BoardDAO dao;
	
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

		// 인기 게시글(커뮤니티)
		ArrayList<Board> limitedRecommends = new ArrayList<>();
		ArrayList<Board> popularBoards = service.popularBoards();

		limitedRecommends = new ArrayList<>();
		for (int i = 0; i < Math.min(popularBoards.size(), 2); i++) {
			limitedRecommends.add(popularBoards.get(i) != null ? popularBoards.get(i) : null);
		}

		model.addAttribute("popularBoards", limitedRecommends);
		
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
		
		board.setUserid(user.getUsername());

		if(upload != null && !upload.isEmpty()) {
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
		
		//좋아요 상태(좋아요:1, 없음:0)
//		int recommendCnt =  service.recommendCnt(boardnum);
//
//		log.debug("좋아요 상태: {}", recommendCnt);
//
//		model.addAttribute("board_recommend", recommendCnt);
		
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
	
	@GetMapping("update")
	public String update(
			int boardnum
			, @AuthenticationPrincipal UserDetails user
			, Model model) {
		
		Board board = service.readBoard(boardnum);
		if (!board.getUserid().equals(user.getUsername())) {
			return "redirect:/board/boardList";
		}
		model.addAttribute("board", board);
		
		return "/board/updateForm";
	}
	
	@PostMapping("update")
	public String update(
			Board board
			, @AuthenticationPrincipal UserDetails user
			, MultipartFile upload) {
		
		log.debug("수정할 글정보 : {}", board);
		log.debug("파일 정보: {}", upload);
		
		//작성자 아이디 추가
		board.setUserid(user.getUsername());
		
		Board oldBoard = null;
		String oldSavedfile = null;
		String savedfile = null;
		
		//첨부파일이 있는 경우 기존파일 삭제 후 새 파일 저장
		if (upload != null && !upload.isEmpty()) {
			oldBoard = service.readBoard(board.getBoardnum());
			oldSavedfile = oldBoard == null ? null : oldBoard.getSavedfile();
			
			savedfile = FileService.saveFile(upload, uploadPath);
			board.setOriginalfile(upload.getOriginalFilename());
			board.setSavedfile(savedfile);
			log.debug("새파일:{}, 구파일:{}", savedfile, oldSavedfile);
		}
		
		int result = service.updateBoard(board);
		
		//글 수정 성공 and 첨부된 파일이 있는 경우 파일도 삭제
		if (result == 1 && savedfile != null) {
			FileService.deleteFile(uploadPath + "/" + oldSavedfile);
		}
		
		return "redirect:/board/read?boardnum=" + board.getBoardnum();
	}
//	@PostMapping("recommend")
//	public String recommend(int boardnum,@AuthenticationPrincipal UserDetails user) {
//		log.debug("추천 게시판 넘버 {}",boardnum);
//			int board_recommend =  service.recommendCheck(boardnum,user.getUsername());
//		//	int program_like =  service.likeCheck(program.getProgramNum(),"aaa"); //test용
//			if(board_recommend==0) {
//				//추천테이블 생성
//				service.createRecommend(boardnum,user.getUsername());
//				log.debug("췤케");
//				}
//			else {
//				//테이블 삭제
//				service.deleteRecommend(boardnum,user.getUsername());
//				}
//			
//		return "redirect:/board/read?boardnum="+boardnum;	
//	}
	
	@ResponseBody
	@PostMapping("recommend")
	public int recommend(int boardnum, @AuthenticationPrincipal UserDetails user) {
		System.out.println("recomkmend excuted");

		// 현재 로그인한 유저의 id를 세팅
		String id = user.getUsername();	
		log.debug("아이디:{}", id);
		log.debug("보드넘:{}", boardnum);
		
//		좋아요 테이블에 게시글번호, 유저아이디가 동시에 매칭되는 열이 있는지 체크 있으면 1 없음면 0
		int check = service.checkLike(boardnum, id);
		log.debug("체크:{}", check);
//		좋아요 수를 담을 cnt
		int cnt = 0;
		
		if (check == 0) {
			log.debug("추천안했음");
			service.addLike(boardnum, id);
			service.upLike(boardnum);
//			cnt = service.selectCnt(boardnum);
			return 1;
		} else {
			log.debug("추천이미했음");
			service.deleteLike(boardnum, id);
			service.downLike(boardnum);
//			cnt = service.selectCnt(boardnum);
			return 0;
		}
		
	}
	
	@GetMapping("popularBoards")
    public String popularBoards(@AuthenticationPrincipal UserDetails user,
                           Model model) {
//        int maxElements = 3;
//        ArrayList<Board> limitedRecommends = new ArrayList<>();

//        //추천게시물 -최근방문+좋아요+북마크 ----sql 수정
//        ArrayList<Program> recommends = new ArrayList<>();
//
//        if(user != null && user.getUsername() != null){
//            recommends = pService.homeRecommend(user.getUsername());
//        }
//
//        for (int i = 0; i < Math.min(recommends.size(), maxElements); i++) {
//            limitedRecommends.add(recommends.get(i));
//        }
//        model.addAttribute("recommends", limitedRecommends);
//
//        //인기게시물 -조회수+좋아요 ----sql 수정
//        ArrayList<Program> populars = pService.homePopular();
//
//        limitedRecommends = new ArrayList<>();
//        for (int i = 0; i < Math.min(populars.size(), maxElements); i++) {
//            limitedRecommends.add(populars.get(i));
//        }
//        model.addAttribute("populars", limitedRecommends);


		ArrayList<Board> limitedRecommends = new ArrayList<>();
        // 인기 게시글(커뮤니티)
        ArrayList<Board> popularBoards = service.popularBoards();

        limitedRecommends = new ArrayList<>();
        for (int i = 0; i < Math.min(popularBoards.size(), 2); i++) {
            limitedRecommends.add(popularBoards.get(i));
        }
        model.addAttribute("popularBoards", limitedRecommends);

        return "boardList";
    }
	
	
	
	
	

}
