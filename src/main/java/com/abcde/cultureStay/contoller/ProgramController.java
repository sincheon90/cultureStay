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
import org.springframework.web.bind.annotation.ResponseBody;

import com.abcde.cultureStay.dao.MemberDAO;
import com.abcde.cultureStay.service.ProgramService;
import com.abcde.cultureStay.vo.Image;
import com.abcde.cultureStay.vo.Member;
import com.abcde.cultureStay.vo.Program;
import com.abcde.cultureStay.vo.ProgramTag;
import com.abcde.cultureStay.vo.Review;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("program")
public class ProgramController {

    @Autowired
    ProgramService service;

    @Autowired
    MemberDAO mDao;

    //인기베스트 리스트
    @GetMapping("popular")
    public String popularList(Model model) {

        //인기게시물 -조회수+좋아요 ----sql 수정
        ArrayList<Program> popular = service.homePopular();
        log.debug("인기순{}", popular);

        model.addAttribute("programList", popular);


        return "program/list";
    }

    //추천 리스트-로그인 안되어 있으면 다르게 보이기로
    @GetMapping("recommend")
    public String recommendList(@AuthenticationPrincipal UserDetails user,
                                Model model) {

        //추천게시물 -최근방문+좋아요+북마크 ----sql 수정
        ArrayList<Program> recommend = service.homeRecommend(user.getUsername());
        model.addAttribute("programList", recommend);

        return "program/list";
    }

    //홈스테이 리스트
    @GetMapping("list")
    public String programHome(Model model,
                              @RequestParam(name = "address", defaultValue = "") String address,
                              @RequestParam(name = "searchWord", defaultValue = "") String searchWord,
                              @RequestParam(required = false) String start_date,
                              @RequestParam(required = false) String end_date,
                              ProgramTag programTag) {
        log.info("=======검색=======");
        log.debug("주소 {}", address);
        log.debug("시작날짜 {}", start_date);
        log.debug("끝나는 날짜 {}", end_date);
        log.debug("검색어 {}", searchWord);

        //검색조건
        Program searchProgram = new Program();
        searchProgram.setTitle(searchWord);
        searchProgram.setAddress(address);
        searchProgram.setEnd_date(end_date);
        searchProgram.setStart_date(start_date);

        ArrayList<Program> programList = service.programMainlist(searchProgram, programTag);

        model.addAttribute("programList", programList);
        model.addAttribute("searchProgram", searchProgram);
        model.addAttribute("programTag", programTag);
        return "program/list";
    }


    //홈스테이 글쓰기 폼
    @GetMapping("write")
    public String pWriteForm() {
        return "program/write";
    }


    //홈스테이 글쓰기 (호스트)
    @PostMapping("write")
    public String pWriteForm(Program program, Image img, ProgramTag tag,
                             @AuthenticationPrincipal UserDetails user) {


        log.debug("호스트아이디 : {}", user.getUsername());

        program.setUserid(user.getUsername());
        log.debug("홈스테이 : {}", program);


        int result = service.pWrite(program);
        log.debug("홈스테이 저장 성공 체크 : {}", result);
        int programNum = service.pnumCheck(user.getUsername());
        log.debug("홈스테이넘버 가져오기 : {}", programNum);
        tag.setProgramNum(programNum);
        log.debug("[write] 홈스테이 태그 : {}", tag);

        int resultTag = service.tagInsert(tag);
        log.debug("홈스테이 태그 성공 체크 : {}", resultTag);

        return "redirect:/program/list";
    }

    //홈스테이 디테일 조회
    @GetMapping("detail")
    public String read(@RequestParam(name = "programNum", defaultValue = "0") int programNum,
                       Model model, @AuthenticationPrincipal UserDetails user) {
        log.debug("홈스테이 넘버: {}", programNum);

        //호스트 정보 가져오기
        Member host = mDao.selectUser(user.getUsername());
        log.debug("호스트 디테일: {}", host);

        //홈스테이 정보 가져오기
        Program program = service.readProgram(programNum);
        log.debug("홈스테이 디테일: {}", program);

        if (user != null) {
            //최근방문에 추가
//			service.recentClick(programNum,user.getUsername());


            //좋아요 상태(좋아요:1, 없음:0)
            int program_like = service.likeCheck(programNum, user.getUsername());
            //	int program_like =  service.likeCheck(programNum,"aaa");//test용

            log.debug("좋아요 상태: {}", program_like);

            //북마크 상태(북마크:1, 없음:0)
            int program_bookmark = service.bookmarkCheck(programNum, user.getUsername());
            //	int program_bookmark = service.bookmarkCheck(programNum,"aaa");//test용
            log.debug("북마크 상태 : {}", program_bookmark);

            model.addAttribute("program_like", program_like);
            model.addAttribute("program_bookmark", program_bookmark);
        }
        //홈스테이 태그 가져오기
        ProgramTag programTag = service.readProgramTag(programNum);
        log.debug("홈스테이 태그: {}", programTag);


        //홈스테이 리뷰 가져오기
        ArrayList<Review> pReviewList = service.pReviewList(programNum);
        log.debug("리뷰 : {}", pReviewList);


        // model 객체를 이용해 detail.html에 출력하기
        model.addAttribute("program", program);

        model.addAttribute("programTag", programTag);
        model.addAttribute("pReviewList", pReviewList);


        //호스트정보,체크리스트-
        return "program/detail";
    }

    //추천post
    @ResponseBody
    @PostMapping("like")
    public int like(int programNum, @AuthenticationPrincipal UserDetails user) {
        log.debug("좋아요 홈스테이 넘버 {}", programNum);
        int program_like = service.likeCheck(programNum, user.getUsername());
        //	int program_like =  service.likeCheck(program.getProgramNum(),"aaa"); //test용
        if (program_like == 0) {
            //좋아요테이블 생성
            service.createLike(programNum, user.getUsername());
            return 1;
        } else {
            //테이블 삭제
            service.deleteLike(programNum, user.getUsername());
            return 0;
        }
    }

    //북마크post
    @ResponseBody
    @PostMapping("bookmark")
    public int bookmark(int programNum, @AuthenticationPrincipal UserDetails user) {
        int program_bookmark = service.bookmarkCheck(programNum, user.getUsername());
        //	int program_bookmark = service.bookmarkCheck(program.getProgramNum(),"aaa");//test용
        if (program_bookmark == 0) {
            //좋아요테이블 생성
            service.createBookmark(programNum, user.getUsername());
            return 1;
        } else {
            //테이블 삭제
            service.deleteBookmark(programNum, user.getUsername());
            return 0;
        }
    }


}
