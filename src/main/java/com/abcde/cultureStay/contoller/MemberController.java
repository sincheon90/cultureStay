package com.abcde.cultureStay.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.abcde.cultureStay.service.MemberService;
import com.abcde.cultureStay.vo.Member;
import com.abcde.cultureStay.vo.Board;
import com.abcde.cultureStay.util.FileService;

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
    
    @Value("${spring.servlet.multipart.location}")
	String uploadPath;
    
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
    public String join(Member member, MultipartFile upload, @AuthenticationPrincipal UserDetails user) {
        log.debug("join_param: {}", member);

        // 프로필 사진 저장
        if(!upload.isEmpty()) {
			String savedfile = FileService.saveFile(upload, uploadPath);
			member.setOgProfileImage(upload.getOriginalFilename());
			member.setSvProfileImage(savedfile);
		}

        service.joinMember(member);
        return "redirect:/";
    }
    
    @GetMapping("loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
    
    @GetMapping("updateForm")
	public String updateForm(@AuthenticationPrincipal UserDetails user
			, Model model) {
		log.debug("update경로_UserDetails 정보: {}", user);
		String userId = user.getUsername();
		Member member = service.selectUser(userId);
		log.debug("DB에서 가져온 Member 정보: {}", member);
		model.addAttribute("member", member);
		
		return "member/updateForm";
	}
    @PostMapping("update")
	public String update(@AuthenticationPrincipal UserDetails user
			, Member member, MultipartFile upload) {
		
		member.setUserid(user.getUsername());
		log.debug(user.getUsername());
		int result = service.updateUser(member);
		log.debug("update 결과: {}", result);
		
		Member oldMember = null;
		String oldOgProfileImage = null;
		String svProfileImage = null;

		// 프로필 이미지가 있는 경우 기존 파일 삭제 후 새 파일 저장
		if (upload != null && !upload.isEmpty()) {
		    oldMember = service.selectUser(member.getUserid());
		    oldOgProfileImage = oldMember == null ? null : oldMember.getOgProfileImage();

		    svProfileImage = FileService.saveFile(upload, uploadPath);
		    member.setOgProfileImage(upload.getOriginalFilename());
		    member.setSvProfileImage(svProfileImage);
		    log.debug("새 프로필 이미지:{}, 구 프로필 이미지:{}", svProfileImage, oldOgProfileImage);
		}

		int result1 = service.updateUser(member);

		// 프로필 이미지 수정 성공 and 첨부된 파일이 있는 경우 파일도 삭제
		if (result1 == 1 && svProfileImage != null) {
		    FileService.deleteFile(uploadPath + "/" + oldOgProfileImage);
		}
		
		return "redirect:/";
	}
  
  
   
    
}