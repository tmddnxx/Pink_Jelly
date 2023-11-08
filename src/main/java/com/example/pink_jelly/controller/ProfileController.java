package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.FriendsDTO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.service.MainBoardService;
import com.example.pink_jelly.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final MemberService memberService;
    private final MainBoardService mainBoardService;


    @GetMapping("")
    public String profile(Model model, HttpSession session){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("logInfo");


        model.addAttribute("memberDTO", memberDTO);

        return "profile/profile";
    }

    @GetMapping("/friendProfile")
    public void friendProfile(){

    }
}
