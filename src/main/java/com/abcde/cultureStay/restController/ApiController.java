package com.abcde.cultureStay.restController;

import com.abcde.cultureStay.service.MemberService;
import com.abcde.cultureStay.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    MemberService memberService;

    @GetMapping("searchUser/{searchUser}")
    public ArrayList<Member> searchUser(@PathVariable String searchUser,
                                        @AuthenticationPrincipal UserDetails user) {
        return memberService.searchUsersExcludeSelf(searchUser, user.getUsername());
    }
}
