package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public void register(){
        // 회원가입 뷰
    }

    @PostMapping("/register")
    public String registerMember(MemberDTO memberDTO){
        memberService.registerMember(memberDTO);

        return "/member/welcome.html";
    }

    @GetMapping("/checkPW")
    public void checkPW(){
        // 회원 수정 전 비밀번호 확인
    }


    @GetMapping("/modifyMember")
    public void modifyMember(){
        // 회원 수정 뷰

    }

    @PostMapping("/modify")
    public String modifyMember(MemberDTO memberDTO){

        memberService.modifyMember(memberDTO);

        return "/profile/profile";
    }
}

