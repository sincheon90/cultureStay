package com.abcde.cultureStay.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abcde.cultureStay.service.ReplyService;
import com.abcde.cultureStay.vo.Reply;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("reply")
public class ReplyController {
	@Autowired
	ReplyService service;
	
	@PostMapping("writeReply")
	public String writeReply(Reply reply, @AuthenticationPrincipal UserDetails user) {
		reply.setUserid(user.getUsername());
		int result = service.writeReply(reply);
		return "redirect:/board/read?boardnum=" + reply.getBoardnum();
	}
	@GetMapping("deleteReply")
	public String deleteReply(Reply reply, @AuthenticationPrincipal UserDetails user) {
		reply.setUserid(user.getUsername());
		int result = service.deleteReply(reply);
		return "redirect:/board/read?boardnum=" + reply.getBoardnum();
	}

}
