package com.abcde.cultureStay.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abcde.cultureStay.service.ChatAlarmService;

@Controller
@RequestMapping("alarm")
public class ChatAlarmController {
	
	@Autowired
	ChatAlarmService service;
	
//	@PostMapping("showAlarm")
//	public ArrayList<Alarm> showAlarm(Model model ,String id) {
//		ArrayList<Alarm> alarm = service.showAlarm(id);
//		model.addAttribute("alarm", alarm);
//		return alarm;
//	}

}
