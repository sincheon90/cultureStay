package com.abcde.cultureStay.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abcde.cultureStay.service.ProgramService;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.Reservation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("program")
@Controller
public class ReviewController {
	

	@Autowired
	ProgramService service;
	
		//호스트가 게스트 후기
		@GetMapping("guestReview")
		public String guestReview(@AuthenticationPrincipal UserDetails user, Model model,
				int reserNum) {
			log.debug("예약번호 {}",	reserNum);
			
			Reservation reservation = service.getReservation(reserNum);
			log.debug("예약신청정보 {}", reservation);
			model.addAttribute("reservation", reservation);
			
			Program program = service.readProgram(reservation.getProgramNum());
			log.debug("프로그램정보 {}",program);
			model.addAttribute("program",program);

			return "program/guestReview";
			}
		
		//게스트가 호스트 후기
		@GetMapping("hostReview")
		public String hostReview(@AuthenticationPrincipal UserDetails user, Model model,
				int reserNum) {
			log.debug("예약번호 {}",	reserNum);
			
			Reservation reservation = service.getReservation(reserNum);
			log.debug("예약신청정보 {}", reservation);
			model.addAttribute("reservation", reservation);
			
			Program program = service.readProgram(reservation.getProgramNum());
			log.debug("프로그램정보 {}",program);
			model.addAttribute("program",program);

			return "program/hostReview";
			}
		
		//게스트가 프로그램 후기
		@GetMapping("programReview")
		public String programReview(@AuthenticationPrincipal UserDetails user, Model model,
				int reserNum) {
			log.debug("예약번호 {}",	reserNum);
			
			Reservation reservation = service.getReservation(reserNum);
			log.debug("예약신청정보 {}", reservation);
			model.addAttribute("reservation", reservation);
			
			Program program = service.readProgram(reservation.getProgramNum());
			log.debug("프로그램정보 {}",program);
			model.addAttribute("program",program);

			return "program/programReview";
			}
		
		
		
}
