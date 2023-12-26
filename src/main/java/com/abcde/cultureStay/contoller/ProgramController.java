package com.abcde.cultureStay.contoller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abcde.cultureStay.service.ProgramService;
import com.abcde.cultureStay.vo.Checklist;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Reservation;
import com.abcde.cultureStay.vo.Review;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("program")
public class ProgramController {

	@Autowired
	ProgramService service;
	
	//인기베스트 리스트
	@GetMapping("popular")
	public String popularList(@AuthenticationPrincipal UserDetails user,
			Model model) {
	
		//인기게시물 -조회수+좋아요 ----sql 수정
		ArrayList<Program> popular = service.homePopular();
		model.addAttribute("popular", popular);

		
		return "program/list";
	}
	
	//추천 리스트
		@GetMapping("recommend")
		public String recommendList(@AuthenticationPrincipal UserDetails user,
				Model model) {
			
			//추천게시물 -최근방문+좋아요+북마크 ----sql 수정
			ArrayList<Program> recommend = service.homeRecommend(user.getUsername());
			model.addAttribute("recommend", recommend);
		
			return "program/list";
		}
	
	//프로그램 리스트
		@GetMapping("list")
		public String programHome(Model model
				, String start_date
				,String end_date,
				String searchWord,String address, ProgramTag tag) {
			
			//검색조건
			Program searchProgram = new Program();
			searchProgram.setAddress(address);
			searchProgram.setEnd_date(end_date);
			searchProgram.setStart_date(start_date);
			
			tag.setSearchWord(searchWord);
			
			ArrayList<Program> programList = service.programMainlist(searchProgram,tag);	

			
			model.addAttribute("programList", programList);
			
			return "program/list";
		}
		
	
	//프로그램 글쓰기 폼
	@GetMapping("write")
	public String pWriteForm() {
		return "program/write";
	}
	
	
	//프로그램 글쓰기 (호스트)
	@PostMapping("write")
	public String pWriteForm(Program program, //이미지추가
@AuthenticationPrincipal UserDetails user) {
		
		log.debug("프로그램 : {}",program);
		log.debug("호스트아이디 : {}",user.getUsername());
		
		program.setUserid(user.getUsername());
	
		//일단 파일은 어떻게 첨부할지 고민해보기, 고객 체크용 체크리스트 생성해야됨
		int result = service.pWrite(program);
		log.debug("프로그램 저장 성공 체크 : {}",result);

		return "redirect:/program/list";
	}
	
	//프로그램 디테일 조회
	@GetMapping("detail")
	public String read(@RequestParam(name = "programNum", defaultValue = "0") int programNum,
			Model model,@AuthenticationPrincipal UserDetails user) {
		log.debug("프로그램 넘버: {}", programNum);

		//프로그램 정보 가져오기
		Program program = service.readProgram(programNum);
		log.debug("프로그램 디테일: {}", program);

		//좋아요 상태(좋아요:1, 없음:0)
	//	int program_like =  service.likeCheck(programNum,user.getUsername());
		int program_like =  service.likeCheck(programNum,"aaa");//test용

		log.debug("좋아요 상태: {}", program_like);

		//북마크 상태(북마크:1, 없음:0)
	//	int program_bookmark = service.bookmarkCheck(programNum,user.getUsername());
		int program_bookmark = service.bookmarkCheck(programNum,"aaa");//test용
		log.debug("북마크 상태 : {}", program_bookmark);

		//프로그램 태그 가져오기
		ProgramTag programTag = service.readProgramTag(programNum);
		log.debug("프로그램 태그: {}", programTag);
		
		
		//프로그램 리뷰 가져오기
		ArrayList<Review> pReviewList = service.pReviewList(programNum);
		log.debug("리뷰 : {}", pReviewList);

		
		// model 객체를 이용해 detail.html에 출력하기
		model.addAttribute("program", program);
		model.addAttribute("program_like", program_like);
		model.addAttribute("program_bookmark", program_bookmark);
		model.addAttribute("programTag", programTag);
		model.addAttribute("pReviewList", pReviewList);
	
		return "program/detail";
	}
	
	//좋아요post
	@PostMapping("like")
	public String like(Program program,@AuthenticationPrincipal UserDetails user) {
//		int program_like =  service.likeCheck(program.getProgramNum(),user.getUsername());
		int like =  service.likeCheck(program.getProgramNum(),"aaa"); //test용
		if(like==0) {
			//좋아요테이블 생성
			service.createLike(program.getProgramNum(),user.getUsername());
		}
		else {
			//테이블 삭제
			service.deleteLike(program.getProgramNum(),user.getUsername());

		}
		
		return "redirect:/program/detail?programNum="+program.getProgramNum();	
	}
	
	//북마크post
	@PostMapping("bookmark")
	public String bookmark(Program program,@AuthenticationPrincipal UserDetails user) {
		//	int program_bookmark = service.bookmarkCheck(program.getProgramNum(),user.getUsername());
		int bookmark = service.bookmarkCheck(program.getProgramNum(),"aaa");//test용
		if(bookmark==0) {
			//좋아요테이블 생성
			service.createBookmark(program.getProgramNum(),user.getUsername());

		}
		else {
			//테이블 삭제
			service.deleteBookmark(program.getProgramNum(),user.getUsername());

		}
		
		
		return "redirect:/program/detail?programNum="+program.getProgramNum();
	}
	
	
	//프로그램 신청화면
		@GetMapping("apply")
		public String applyForm() {
			return "program/apply";
		}
	//프로그램 신청
		@PostMapping("apply")
		public String applyForm(Checklist chlist, Reservation reserveForm) {
			//태그x 체크리스트, 예약테이블 넘기기
			
			return "program/apply";
		}
	
	
	
}
