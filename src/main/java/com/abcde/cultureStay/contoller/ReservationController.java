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
import com.abcde.cultureStay.vo.Reservation;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("program")
public class ReservationController {
	
	@Autowired
	ProgramService service;
	
	@GetMapping("apply")
	public String apply(Model model,
		String start_date, String end_date, int programNum) {
		model.addAttribute("start_date", start_date);
		model.addAttribute("end_date", end_date);
		Program program = service.readProgram(programNum);
		
		model.addAttribute("program", program);
		return "program/apply";
	}
	
	//고객체크리스트 저장
	@PostMapping("checklist")
	public String checklist(@AuthenticationPrincipal UserDetails user,Checklist checklist) {
		checklist.setUserid(user.getUsername());
		log.debug("체크리스트{}",checklist);
		
		service.reserveChecklist(checklist);
		
		return "redirect:/program/apply";
	}
	
	
	@PostMapping("payment")
	public String payment(Model model,String request,
			String start_date, String end_date, int programNum, int totalPrice) {
 
		log.debug("끝{}",request);
		model.addAttribute("start_date", start_date);
		model.addAttribute("end_date", end_date);
		model.addAttribute("totalPrice", totalPrice);
		Program program = service.readProgram(programNum);
		
		model.addAttribute("program", program);
		model.addAttribute("request", request);

		return "program/payment";
	}
	
	@PostMapping("success")
	public String success(Model model,@AuthenticationPrincipal UserDetails user,
		String request,String start_date, String end_date, int programNum, int totalPrice) {
		log.debug("끝success{}",request);
		log.debug("끝success{}",end_date);
		Reservation reserveForm = new Reservation();
		Program program = service.readProgram(programNum);
		reserveForm.setProgramNum(programNum);
		reserveForm.setHostid(program.getUserid());
		reserveForm.setUserid(user.getUsername());
		reserveForm.setStart_date(start_date);
		reserveForm.setEnd_date(end_date);
		reserveForm.setRequest(request);
		int result = service.insertReserveForm(reserveForm);
		log.debug("예약체크리스트 전 예약테이블확인{}",reserveForm);
		int reserNum = service.getReserNum(programNum,user.getUsername());
		log.debug("성공 reserNum{}",reserNum);

		service.setReserNum(reserNum,programNum,user.getUsername());
		
		return "program/success";
	}
	
	@GetMapping("check")
	public String check(@AuthenticationPrincipal UserDetails user, Model model) {
		
		//내프로그램 리스트
		ArrayList<Program> programList = service.myProgram(user.getUsername());
		model.addAttribute("programList",programList);

		
		//예약 리스트
		ArrayList<Reservation> reservation = service.newReser(user.getUsername());
		model.addAttribute("reservation",reservation);
		
		
		//내가 예약한 리스트
		ArrayList<Reservation> myReservation = service.myReservation(user.getUsername());
		model.addAttribute("myReservation",myReservation);
		
		
		return "program/check";
	}
	
	@GetMapping("accept")
	public String accept(@AuthenticationPrincipal UserDetails user, Model model,
			int reserNum) {
		log.debug("예약번호 {}",	reserNum);
		
		Reservation reservation = service.getReservation(reserNum);
		log.debug("예약신청정보 {}", reservation);
		model.addAttribute("reservation", reservation);
		
		Program program = service.readProgram(reservation.getProgramNum());
		log.debug("프로그램정보 {}",program);
		model.addAttribute("program",program);

		return "program/accept";
	}
	
	//예약수락
	@PostMapping("accept")
    public String accept(int reserNum) {
		log.debug("예약하기 {}",	reserNum);
        service.acceptReser(reserNum); 
        
        return "redirect:/program/accept?reserNum="+reserNum;
    }
	
}
