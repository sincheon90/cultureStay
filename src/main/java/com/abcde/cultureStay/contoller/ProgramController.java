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
	
	//홈화면 프로그램- 나중에 링크 수정
	@GetMapping("")
	public String homeList(@AuthenticationPrincipal UserDetails user,
			Model model) {
		
		//추천게시물 -최근방문+좋아요+북마크 ----sql 수정
		ArrayList<Program> recommend = service.homeRecommend(user.getUsername());
		model.addAttribute("recommend", recommend);
		
		//인기게시물 -조회수+좋아요 ----sql 수정
		ArrayList<Program> popular = service.homePopular();
		model.addAttribute("popular", popular);

		
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
	public String pWriteForm(Program program, @AuthenticationPrincipal UserDetails user) {
		
		log.debug("프로그램 : {}",program);
		log.debug("호스트아이디 : {}",user.getUsername());
		
		program.setUserid(user.getUsername());
	
		//일단 파일은 어떻게 첨부할지 고민해보기
		int result = service.pWrite(program);
		log.debug("프로그램 저장 성공 체크 : {}",result);

		return "redirect:/program/list";
	}
	
	//프로그램 조회
	@GetMapping("detail")
	public String read(@RequestParam(name = "programNum", defaultValue = "0") int programNum,
			Model model) {
		log.debug("read_param: {}", programNum);

		// DB에서 프로그램번호에 일치하는 프로그램 정보를 가져오기
		Program program = service.readProgram(programNum);
	
		//프로그램 리뷰 가져오기
		ArrayList<Review> pReviewList = service.pReviewList(programNum);
		
		
		// model 객체를 이용해 readForm.html에 출력하기
		model.addAttribute("program", program);
		model.addAttribute("pReviewList", pReviewList);
		
		return "program/detail";
	}
	
	//프로그램 신청화면
		@GetMapping("apply")
		public String applyForm() {
			return "program/apply";
		}
	//프로그램 신청
		@PostMapping("apply")
		public String applyForm(ProgramTag tag,Reservation reserveForm) {
			//태그, 예약테이블 넘기기
			
			return "program/apply";
		}
	
	
	
}
