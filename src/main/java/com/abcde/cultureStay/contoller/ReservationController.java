package com.abcde.cultureStay.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abcde.cultureStay.service.ProgramService;
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
	
	@PostMapping("payment")
	public String payment(Model model,String request,
			String start_date, String end_date, int programNum, int totalPrice) {
//		log.debug("끝{}",programNum);
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
		reserveForm.setProgramNum(programNum);
		reserveForm.setUserid(user.getUsername());
		reserveForm.setStart_date(start_date);
		reserveForm.setEnd_date(end_date);
		reserveForm.setRequest(request);
		int result = service.insertReserveForm(reserveForm);
		
		return "program/success";
	}
	
	
	
//	service.insertChlist(chlist); //체크리스트 디비 저장
}
