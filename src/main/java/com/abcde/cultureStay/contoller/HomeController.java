package com.abcde.cultureStay.contoller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.abcde.cultureStay.service.ProgramService;
import com.abcde.cultureStay.vo.Program;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class HomeController {

//    @GetMapping({"","/"})
//    public String hello() {
//        return "home";
//        
//    }
    
	@Autowired
	ProgramService pService;
	
	
	//홈화면
	@GetMapping("")
	public String homeList(@AuthenticationPrincipal UserDetails user,
			Model model) {
		
		//추천게시물 -최근방문+좋아요+북마크 ----sql 수정
		//ArrayList<Program> recommend = service.homeRecommend(user.getUsername());
		ArrayList<Program> recommend = pService.homeRecommend("aaa");
		log.debug("추천:{}",recommend);
		model.addAttribute("recommend", recommend);
		
		//인기게시물 -조회수+좋아요 ----sql 수정
		ArrayList<Program> popular = pService.homePopular();
		model.addAttribute("popular{}", popular);
		log.debug("인기: {}",popular);

		
		 return "home";
	}
    
    

}
