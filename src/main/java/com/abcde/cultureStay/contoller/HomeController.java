package com.abcde.cultureStay.contoller;

import java.util.ArrayList;

import com.abcde.cultureStay.service.BoardService;
import com.abcde.cultureStay.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.abcde.cultureStay.service.ProgramService;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.Reservation;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class HomeController {

//    @GetMapping({"", "/"})
//    public String hello() {
//        return "home";
//
//    }

    @Autowired
    ProgramService pService;

    @Autowired
    BoardService bService;

    @GetMapping("member/mypage")
    public String mypage() {

        return "member/mypage";
    }

    //홈화면
    @GetMapping("")
    public String homeList(@AuthenticationPrincipal UserDetails user,
                           Model model) {
        int maxElements = 3;
        ArrayList<Program> limitedRecommends = new ArrayList<>();
        ArrayList<Program> limitedPopulars = new ArrayList<>();
        ArrayList<Board> limitedPopularBoards = new ArrayList<>();

        //추천게시물 -최근방문+좋아요+북마크 ----sql 수정
        ArrayList<Program> recommends = new ArrayList<>();

        if(user != null && user.getUsername() != null){
            recommends = pService.homeRecommend(user.getUsername());
        }

        for (int i = 0; i < Math.min(recommends.size(), maxElements); i++) {
            limitedRecommends.add(recommends.get(i));
        }
        model.addAttribute("recommends", limitedRecommends);

        //인기게시물 -조회수+좋아요 ----sql 수정
        ArrayList<Program> populars = pService.homePopular();
        for (int i = 0; i < Math.min(populars.size(), maxElements); i++) {
            limitedPopulars.add(populars.get(i));
        }
        model.addAttribute("populars", limitedPopulars);


        // 인기 게시글(커뮤니티)
        ArrayList<Board> popularBoards = bService.popularBoards();
        for (int i = 0; i < Math.min(populars.size(), 2); i++) {
            limitedPopularBoards.add(popularBoards.get(i));
        }
        model.addAttribute("popularBoards", limitedPopularBoards);

        return "home";
    }


}
