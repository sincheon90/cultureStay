package com.abcde.cultureStay.contoller;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abcde.cultureStay.dao.MemberDAO;
import com.abcde.cultureStay.dao.ProgramDAO;
import com.abcde.cultureStay.service.ProgramService;
import com.abcde.cultureStay.vo.Member;
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
	
	@Autowired
	MemberDAO mDao;
	
	
	  @GetMapping("member/myProgram")
	  public String myProgram(@AuthenticationPrincipal UserDetails user, Model model) {
		  	Member member = mDao.searchId(user.getUsername());
		 
			model.addAttribute("ID",user.getUsername());

		  
		  	//내 호스트 평균 별점
		  	double hostAvg = dao.hostAvg(user.getUsername());
			model.addAttribute("hostAvg",hostAvg);
		  
			//호스트 리뷰 리스트
			ArrayList<Review> hostReview = dao.getHostReview(user.getUsername());
			model.addAttribute("hostReview",hostReview);
			
			
		  
			//내홈스테이 리스트
			ArrayList<Program> programList = pService.myProgram(user.getUsername());
			model.addAttribute("programList",programList);

			
//			//내 홈스테이 리뷰 리스트
//			ArrayList<Review> programReview = dao.myProgramReview(user.getUsername());
//			model.addAttribute("programWithReviews", programWithReviews);
	
			
			//예약 리스트
			ArrayList<Reservation> reservation = pService.newReser(user.getUsername());
			model.addAttribute("reservation",reservation);
			
			//내가 예약한 리스트
			ArrayList<Reservation> myReservation = pService.myReservation(user.getUsername());
			model.addAttribute("myReservation",myReservation);
			
	        return "member/myProgram";
	    }
	 
	    @GetMapping("member/myReservation")
	 	public String myReview(@AuthenticationPrincipal UserDetails user, Model model) {
	 		
	    	//내 호스트 리뷰
	    	//ArrayList<Review> hostReview = dao.getHostReview(user.getUsername());
			//model.addAttribute("hostReview",hostReview);
			//내 홈스테이 리뷰
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
			
	 		return "member/myReservation";
	 	}
	    
	    @GetMapping("program/request")
		  public String myProgramreview(
				  @AuthenticationPrincipal UserDetails user, Model model) {
				
	    	
			//예약 리스트
			ArrayList<Reservation> reservation = pService.newReser(user.getUsername());
			log.debug("예약리스트 {}",reservation);
			model.addAttribute("reservation",reservation);
//			
			
		        return "program/request";
		    }
	    @GetMapping("member/myBookmark")
		  public String myBookmark(
				   @AuthenticationPrincipal UserDetails user, Model model
				  ) {
				
				//내 북마크 리스트
				ArrayList<Program> bookmarkList = dao.getmyBookmark(user.getUsername());
				model.addAttribute("bookmarkList",bookmarkList);

//			
//				
//				//내가 예약한 리스트
//				ArrayList<Reservation> myReservation = pService.myReservation(user.getUsername());
//				model.addAttribute("myReservation",myReservation);
//				
				
			  
		        return "member/myBookmark";
		    }
		 
}
