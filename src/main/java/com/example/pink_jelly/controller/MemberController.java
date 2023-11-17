package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.service.MailSenderService;
import com.example.pink_jelly.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Log4j2
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    @Value("${com.example.tempUpload.path}")
    private String tempPath;

    @Value("${com.example.profileUpload.path}")
    private String profilePath; // 프로필 저장 경로

    private final MemberService memberService;
    private final MailSenderService mailSenderService;
    private final UserDetailsService userDetailsService;
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

        log.info(memberDTO.getProfileImg());

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

        /* 데이터가 저장된 후에 이미지 파일 이동 */
        if (memberDTO.getProfileImg() != null) {
            String splits[] = memberDTO.getProfileImg().split("/");

            moveFile(splits[0]);
            moveFile("s_" + splits[0]);
        }

        memberService.registerMember(memberDTO);

        return "redirect:/member/welcome";
    }

    @PostMapping("/checkMemberId")
    @ResponseBody
    public String checkMemberId(@RequestParam("memberId") String memberId) {
        // 아이디 중복 검사
        log.info("checkMemberId...");
        log.info(memberId);

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
    public void memberInfo(){
        // 회원정보 페이지 이동

    }


    @GetMapping("/modifyMemberInfo")
    public void modifyMember(@AuthenticationPrincipal MemberDTO memberDTO, Model model){
        // 회원정보 수정 페이지 이동
        log.info("/member/modifyMemberInfo...");
        String phone = memberDTO.getPhone();

        String[] phones = phone.split("-");
        log.info("phones: " + phones);

        model.addAttribute("phones", phones);
    }

    @PostMapping("/modifyMemberInfo")
    public String modifyMember(@AuthenticationPrincipal MemberDTO memberDTO, MemberDTO updateMemberDTO){
        // 회원 정보 수정
        log.info("/member/modifyMemberInfo(POST)...");

        memberService.modifyMemberInfo(updateMemberDTO);

        sessionReset(memberDTO);

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

    private void sessionReset(MemberDTO memberDTO) { 
        // 세션 사용자 정보 업데이트
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberDTO.getMemberId());
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, new HashSet<GrantedAuthority>());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }

    private void moveFile(String fileName) {
        /* 등록시에 첨부 파일을 이동 */

        // 오늘 날짜로 폴더 생성
        LocalDate currentDate = LocalDate.now(); // 오늘 날짜 가져오기
        // 날짜 포맷 지정(원하는 형식으로)

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(formatter); // 날짜를 문자열로 변환

        String newTempPath = tempPath + "/" + dateString;
        String newProfilePath = profilePath + "/" + dateString;

        File file = new File(newProfilePath);
        if (!file.exists()) { // 폴더가 존재하지 않으면
            file.mkdirs(); // 폴더 생성
        }

        try {
            // 읽을 파일과 쓰기 파일을 구분해서 객체 생성.
            FileInputStream fileInputStream = new FileInputStream(newTempPath + File.separator + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(newProfilePath + File.separator + fileName);

            // 파일 복사의 경우 buffer를 사용해야 속도가 빠름.
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            int i;
            while ((i = bufferedInputStream.read()) != -1) { // 파일 끝까지 읽기
                bufferedOutputStream.write(i); // 읽은 만큼 쓰기
            }
            bufferedInputStream.close();
            bufferedOutputStream.close();
            fileInputStream.close();
            fileOutputStream.close();

            Files.delete(Paths.get(newTempPath + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

