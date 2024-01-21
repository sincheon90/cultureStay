package com.abcde.cultureStay.restController;

import com.abcde.cultureStay.service.MemberService;
import com.abcde.cultureStay.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    MemberService memberService;

    @GetMapping("searchUser")
    public ArrayList<Member> searchUser(@RequestParam String searchUser,
                                        @AuthenticationPrincipal UserDetails user) {
        System.out.println(searchUser);
        return memberService.searchUsersExcludeSelf(searchUser, user.getUsername());
    }
}
