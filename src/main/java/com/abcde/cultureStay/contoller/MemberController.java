package com.abcde.cultureStay.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.abcde.cultureStay.Service.MemberService;
import com.abcde.cultureStay.vo.Member;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {
    @Autowired
    MemberService service;

    private static String UPLOADED_FOLDER = "/path/to/your/uploaded/folder/"; // 이 경로를 조정하세요

    @GetMapping("join")
    public String joinForm() {
        return "member/joinForm";
    }

    @GetMapping("idCheck")
    public String idCheckForm() {
        return "member/idCheck";
    }

    @PostMapping("idCheck")
    public String idCheck(String searchId, Model model) {
        // DB로부터 가져옵니다
        boolean result = service.searchId(searchId);

        if (result) {
            model.addAttribute("searchId", searchId);
            model.addAttribute("result", "사용할 수 있는 ID입니다.");
        } else {
            model.addAttribute("result", "이미 사용중인 ID입니다.");
        }

        return "member/idCheck";
    }

    @PostMapping("join")
    public String join(Member member, @RequestParam("profileImage") MultipartFile profileImage) {
        log.debug("join_param: {}", member);

        // 프로필 사진 저장
        if (!profileImage.isEmpty()) {
            try {
                byte[] bytes = profileImage.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + profileImage.getOriginalFilename());
                Files.write(path, bytes);

                // Member 객체에 프로필 이미지 경로 설정
                member.setProfileImagePath(path.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.joinMember(member);
        return "redirect:/";
    }
    
    @GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
  
}