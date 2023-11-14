package com.example.pink_jelly.controller;

import com.example.pink_jelly.service.MainBoardService;
import com.example.pink_jelly.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final MemberService memberService;
    private final MainBoardService mainBoardService;

    @GetMapping("/myProfile")
    public void myProfile(){
    }

    @GetMapping("/friendProfile")
    public void friendProfile(){

    }
}
