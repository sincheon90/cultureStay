package com.abcde.cultureStay.contoller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abcde.cultureStay.dao.ProgramDAO;
import com.abcde.cultureStay.service.ProgramService;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.Reservation;
import com.abcde.cultureStay.vo.Review;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MypageController {

	
	@Autowired
	ProgramService pService;
	
	@Autowired
	ProgramDAO dao;
	
	
	  @GetMapping("member/myProgram")
	  public String myProgram(@AuthenticationPrincipal UserDetails user, Model model) {
			
			//내프로그램 리스트
			ArrayList<Program> programList = pService.myProgram(user.getUsername());
			model.addAttribute("programList",programList);

			
			//예약 리스트
			ArrayList<Reservation> reservation = pService.newReser(user.getUsername());
			model.addAttribute("reservation",reservation);
			
			
			//내가 예약한 리스트
			ArrayList<Reservation> myReservation = pService.myReservation(user.getUsername());
			model.addAttribute("myReservation",myReservation);
			
			
		  
	        return "member/myProgram";
	    }
	 
	    @GetMapping("member/myReview")
	 	public String myReview(@AuthenticationPrincipal UserDetails user, Model model) {
	 		
	    	//내 호스트 리뷰
	    	//ArrayList<Review> hostReview = dao.getHostReview(user.getUsername());
			//model.addAttribute("hostReview",hostReview);
			//내 프로그램 리뷰
	    	//ArrayList<Review> programReview = dao.getProgramReview(user.getUsername());
		//	model.addAttribute("programReview",programReview);
			//내 게스트 리뷰
	    	ArrayList<Review> guestReview = dao.getGuestReview(user.getUsername());
			model.addAttribute("guestReview",guestReview);
			//내가 작성한 리뷰
	    	ArrayList<Review> myReview = dao.getMyReview(user.getUsername());
			model.addAttribute("myReview",myReview);
			//내가 예약한 리스트
			ArrayList<Reservation> myReservation = pService.myReservation(user.getUsername());
			model.addAttribute("myReservation",myReservation);
			
	 		return "member/myReview";
	 	}
	    
	    @GetMapping("program/review")
		  public String myProgramreview(@RequestParam(name = "programNum", defaultValue = "0") int programNum,
				  @AuthenticationPrincipal UserDetails user, Model model) {
				
	    	log.debug("programNum: {}",programNum);
	    	//프로그램 정보
	    	
			//호스트 리뷰 리스트
				ArrayList<Review> hostReview = dao.getHostReview(programNum);
				model.addAttribute("hostReview",hostReview);
				
				double hostAvg = dao.hostAvg(user.getUsername());
				model.addAttribute("hostAvg",hostAvg);

			//프로그램 리뷰 리스트
				ArrayList<Review> programReview = dao.getProgramReview(programNum);
				model.addAttribute("programReview",programReview);
				
			
		        return "member/programReview";
		    }
	    @GetMapping("member/myBookmark")
		  public String myBookmark(
				   @AuthenticationPrincipal UserDetails user, Model model
				  ) {
				
				//내 북마크 리스트
				ArrayList<Program> bookmarkList = dao.getmyBookmark(user.getUsername());
				model.addAttribute("bookmarkList",bookmarkList);

//				
//				//예약 리스트
//				ArrayList<Reservation> reservation = pService.newReser(user.getUsername());
//				model.addAttribute("reservation",reservation);
//				
//				
//				//내가 예약한 리스트
//				ArrayList<Reservation> myReservation = pService.myReservation(user.getUsername());
//				model.addAttribute("myReservation",myReservation);
//				
				
			  
		        return "member/myBookmark";
		    }
		 
}
