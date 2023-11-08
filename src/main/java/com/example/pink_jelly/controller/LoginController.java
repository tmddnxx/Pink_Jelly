package com.example.pink_jelly.controller;


import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Log4j2
@RequestMapping({"/login", "/"})
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("")
    public String login(){

        return "/login/login";
    }

    @PostMapping("")
    public String loginPost(HttpServletRequest req, Model model){
        HttpSession session = req.getSession();
        session.invalidate(); // 로그인 전 세션 초기화
        String memberId = req.getParameter("memberId");
        String passwd = req.getParameter("passwd");

        MemberDTO memberDTO = memberService.login(memberId, passwd);
        log.info(memberDTO);
        if(memberDTO != null){
            session = req.getSession();
            session.setAttribute("logInfo", memberDTO);
        }
        model.addAttribute("memberDTO", memberDTO);

        return "redirect:/main";
    }
}
