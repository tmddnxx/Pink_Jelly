package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.service.MailSenderService;
import com.example.pink_jelly.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public void signup(){
        // 회원가입 뷰
    }

    @PostMapping("/signup")
    public String signup(MemberDTO memberDTO, BindingResult bindingResult, Model model){
        // 회원가입 처리
        String encPasswd = passwordEncoder.encode(memberDTO.getPasswd());
        memberDTO.setPasswd(encPasswd);

        /* 검증 */
//        if(bindingResult.hasErrors()) {
//            /* 회원가입 실패 시 입력 데이터 값 유지 */
//            model.addAttribute("memberDTO", memberDTO);
//
//            /* 유효성 검사를 통과하지 못 한 필드와 메시지 핸들링 */
//            Map<String, String> errorMap = new HashMap<>();
//
//            for(FieldError error : bindingResult.getFieldErrors()) {
//                errorMap.put("valid_"+error.getField(), error.getDefaultMessage());
//                log.info("error message : "+error.getDefaultMessage());
//            }
//
//            /* 회원가입 페이지로 리턴 */
//            return "/member/signup";
//        }
        memberService.registerMember(memberDTO);

        return "redirect:/member/welcome";
    }

    @PostMapping("/checkMemberId")
    @ResponseBody
    public String checkMemberId(@RequestParam("memberId") String memberId) {
        // 아이디 중복 검사
        boolean result = memberService.checkIdDuplicate(memberId);

        return result ? "true" : "false";
    }

    @GetMapping("/sendConfirmMail")
    @ResponseBody
    public String sendConfirmMail(String mailTo, HttpSession session) throws Exception {
        // 이메일 인증코드 전송
        if (mailSenderService.sendMailByAddMember(mailTo)) {
            session.setAttribute("confirmKey", mailSenderService.getConfirmKey());
            return "true";
        }

        return "false";
    }

    @PostMapping("/matchConfirmKey")
    @ResponseBody
    public String matchConfirmKey(@RequestParam("reqConfirmKey") String reqConfirmKey, HttpSession session) {
        // 이메일 인증코드 확인
        log.info("/matchConfirmKey(POST)...");
        log.info(reqConfirmKey);
        String confirmKey = (String) session.getAttribute("confirmKey");

        return reqConfirmKey.equals(confirmKey) ? "true" : "false";
    }

    @GetMapping("/removeConfirmKey")
    @ResponseBody
    public String removeConfirmKey(HttpSession session) {
        // 세션에 저장된 인증코드 삭제
        session.removeAttribute("confirmKey");

        log.info(session.getAttribute("confirmKey"));

        return "true";
    }

    @GetMapping("/welcome")
    public void welcome(){
        // 회원가입 완료 뷰
    }

    @GetMapping("/checkPW")
    public void checkPW(){
        // 회원 수정 전 비밀번호 확인 페이지 이동
    }
    @PostMapping("/checkPW")
    public String checkPWPost(String passwd, @AuthenticationPrincipal MemberDTO memberDTO) {
        log.info("/checkPW(POST)...");
        log.info("passwd: " + passwd);
        log.info("encPasswd: " + memberDTO.getPasswd());
        // 회원 수정 전 비밀번호 확인
        if (memberDTO != null) {
            if (passwordEncoder.matches(passwd, memberDTO.getPassword())) { // 로그인한 비밀번호와 입력한 비밀번호가 일치할 때
                return "redirect:/member/memberInfo"; // 회원정보 수정 페이지로 이동
            }
        }

        return "/member/checkPW";
    }

    @GetMapping("/memberInfo")
    public void memberInfo(@AuthenticationPrincipal MemberDTO memberDTO, Model model){
        // 회원정보 페이지 이동
        log.info("/member/memberInfo...");
        String email = memberDTO.getEmail();
        String phone = memberDTO.getPhone();

        String[] emails = email.split("@");
        String[] phones = phone.split("-");
        log.info("emails: " + emails);
        log.info("phones: " + phones);

        model.addAttribute("emails", emails);
        model.addAttribute("phones", phones);
    }


    @GetMapping("/modifyMember")
    public void modifyMember(@AuthenticationPrincipal MemberDTO memberDTO, Model model){
        // 회원정보 수정 페이지 이동
        log.info("/member/modifyMember...");
        String email = memberDTO.getEmail();
        String phone = memberDTO.getPhone();

        String[] emails = email.split("@");
        String[] phones = phone.split("-");
        log.info("emails: " + emails);
        log.info("phones: " + phones);

        model.addAttribute("emails", emails);
        model.addAttribute("phones", phones);
    }

    @PostMapping("/modifyMember")
    public String modifyMember(MemberDTO memberDTO){
        // 회원 정보 수정
        log.info("/member/modifyMember(POST)...");

        memberService.modifyMember(memberDTO);

        return "redirect:/member/memberInfo";
    }

    @GetMapping("/modifyPasswd")
    public void modifyPasswd(){
        // 비밀번호 변경 페이지
    }
    @PostMapping("/modifyPasswd")
    public String modifyPasswd(@AuthenticationPrincipal MemberDTO memberDTO, String newPasswd){
        // 비밀번호 변경
        String encPasswd = passwordEncoder.encode(newPasswd);
        memberDTO.setPasswd(encPasswd);
        memberService.modifyPasswd(memberDTO);

        return "redirect:/member/memberInfo";
    }

    @GetMapping("/modifyMyCat")
    public String modifyMyCat(@AuthenticationPrincipal MemberDTO memberDTO){
        // 고양이 정보수정 페이지 이동
        if (memberDTO.isHasCat()) { // 고양이 정보가 존재할때
            return "/member/modifyMyCat";
        }

        return "redirect:/member/memberInfo";
    }

    @PostMapping("/modifyMyCat")
    public String modifyMyCatPost(MemberDTO memberDTO){
        // 고양이 정보수정
        log.info(memberDTO);
        memberService.modifyMyCat(memberDTO);

        return "redirect:/member/memberInfo";
    }

    @PostMapping("/exit")
    public String exit(Long mno){
        // 회원탈퇴
        log.info("/member/exit");

        memberService.removeMember(mno);
        return "redirect:/logout";
    }
}

