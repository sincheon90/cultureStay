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
import com.abcde.cultureStay.vo.Review;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("program")
public class ProgramController {

	@Autowired
	ProgramService service;
	
	//메인 프로그램 불러오기
	@GetMapping("list")
	public String homeList(Model model) {
		
		ArrayList<Program> programList = service.mainSelect();	
		//카테고리화 추가하기 
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
	
	
	
	
	
	
}
