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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public void signup(){
        // 회원가입 뷰
    }

    @PostMapping("/signup")
    public String signup(MemberDTO memberDTO, HttpServletRequest request){ // 회원가입 처리
        String emailId = request.getParameter("emailId");
        String selectEmail = request.getParameter("emailSelect");

        String ageText = request.getParameter("catAge1");
        String ageRadio = request.getParameter("catAge2");

        log.info(memberDTO);
        String catAge = ageText + ageRadio;
        String email = emailId + selectEmail;

        memberDTO.setCatAge(catAge);
        memberDTO.setEmail(email);

        memberService.registerMember(memberDTO);

        return "redirect:/member/welcome";
    }
    @GetMapping("/welcome")
    public void welcome(){
        // 회원가입 완료 뷰
    }

    @GetMapping("/checkPW")
    public void checkPW(){
        // 회원 수정 전 비밀번호 확인
    }
    @PostMapping("/checkPW")
    public String checkPWPost(String passwd, HttpSession session){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("logInfo");
        if (memberDTO.getPasswd().equals(passwd)){
            return "/member/memberInfo";
        }
        return "/member/checkPW";
    }

    @GetMapping("/memberInfo")
    public void memberInfo(){

    }


    @GetMapping("/modifyMember")
    public void modifyMember(MemberDTO memberDTO, Model model){
        // 회원 수정 뷰

        memberDTO = memberService.getMember(memberDTO.getMno());

        String email = memberDTO.getEmail();
        String[] emailParts = email.split("@");
        memberDTO.setEmail(emailParts[0]);
        model.addAttribute("memberDTO", memberDTO);
    }

    @PostMapping("/modifyMember")
    public String modifyMember(MemberDTO memberDTO, HttpServletRequest request){ // 회원 수정 처리
        String emailId = request.getParameter("emailId");
        String selectEmail = request.getParameter("emailSelect");

        String email = emailId + selectEmail;

        memberDTO.setEmail(email);
        memberService.modifyMember(memberDTO);

        return "/member/memberInfo";
    }

    @GetMapping("/modifyPasswd")
    public void modifyPasswd(MemberDTO memberDTO, Model model){
        // 비밀번호 수정 뷰
        memberDTO = memberService.getMember(memberDTO.getMno());

        model.addAttribute("memberDTO", memberDTO);
    }
    @PostMapping("/modifyPasswd")
    public String modifyPasswd(MemberDTO memberDTO){ // 비밀번호 수정 처리

        memberService.modifyPasswd(memberDTO);

        return "/member/memberInfo";
    }

    @GetMapping("/modifyMyCat")
    public void modifyMyCat(MemberDTO memberDTO, Model model){
        // 고양이 정보 수정
        memberDTO = memberService.getMember(memberDTO.getMno());

        model.addAttribute("memberDTO", memberDTO);
    }

    @PostMapping("/modifyMyCat")
    public String modifyMyCat(MemberDTO memberDTO){ // 고양이 정보수정 처리
        memberService.modifyMyCat(memberDTO);

        return "/member/memberInfo";
    }

    @GetMapping("/exit")
    public void exit(){
        // 회원탈퇴 뷰
    }

    @PostMapping("/exit")
    public String exit(Long mno){
        memberService.removeMember(mno);
        return "/member/exit";
    }
}

