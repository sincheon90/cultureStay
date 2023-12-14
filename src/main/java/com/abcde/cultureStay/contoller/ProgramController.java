package com.abcde.cultureStay.contoller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abcde.cultureStay.service.ProgramService;
import com.abcde.cultureStay.vo.Program;

@Controller
@RequestMapping("program")
public class ProgramController {

	@Autowired
	ProgramService service;
	
	@GetMapping("list")
	public String homeList(Model model) {
		
		ArrayList<Program> programList = service.mainSelect();	
		
		model.addAttribute("programList", programList);
		
		return "program/list";
	}
}
