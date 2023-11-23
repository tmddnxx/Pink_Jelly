package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.service.MailSenderService;
import com.example.pink_jelly.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @GetMapping("/kakaoSignup")
    public void kakaoSignup(MemberDTO memberDTO){
        // 카카오 로그인 후 회원가입 뷰
    }

    @PostMapping("/kakaoSignup")
    public String kakaoSignup(@Valid MemberDTO memberDTO, BindingResult bindingResult, @AuthenticationPrincipal MemberDTO principal, Model model){
        // 회원가입 처리

        log.info("/kakaoSignup...");


        if (bindingResult.hasErrors()) {
            return "/member/kakaoSignup";
        }

        // 비밀번호 암호화
        String encPasswd = passwordEncoder.encode(memberDTO.getPasswd());
        memberDTO.setPasswd(encPasswd);

        log.info("principal: " + principal);
        if (principal != null) {
            memberDTO.setMemberId(principal.getMemberId());
        }

        memberService.registerMember(memberDTO);

        /* 데이터가 저장된 후에 이미지 파일 이동 */
        if (memberDTO.getProfileImg() != null) {
            String splits[] = memberDTO.getProfileImg().split("/");

            moveFile(splits[0]);
            moveFile("s_" + splits[0]);
        }

        return "/member/welcome";
    }

    @GetMapping("/signup")
    public void signup(MemberDTO memberDTO){
        // 회원가입 뷰
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDTO memberDTO, BindingResult bindingResult, String isConfirm, @AuthenticationPrincipal MemberDTO principal, Model model){
        // 회원가입 처리

        log.info("/signup...");


        if (bindingResult.hasErrors() || isConfirm.equals("false")) {
            model.addAttribute("msg", "이메일 인증을 하지않았습니다.");
            return "/member/signup";
        }

        // 비밀번호 암호화
        String encPasswd = passwordEncoder.encode(memberDTO.getPasswd());
        memberDTO.setPasswd(encPasswd);

        log.info("principal: " + principal);
        if (principal != null) {
            memberDTO.setMemberId(principal.getMemberId());
        }

        memberService.registerMember(memberDTO);

        /* 데이터가 저장된 후에 이미지 파일 이동 */
        if (memberDTO.getProfileImg() != null) {
            String splits[] = memberDTO.getProfileImg().split("/");

            moveFile(splits[0]);
            moveFile("s_" + splits[0]);
        }

        return "/member/welcome";
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
    public String checkPWPost(@AuthenticationPrincipal MemberDTO memberDTO, String passwd, Model model) {
        log.info("/checkPW(POST)...");
        log.info("passwd: " + passwd);
        log.info("encPasswd: " + memberDTO.getPasswd());

        // 회원 수정 전 비밀번호 확인
        if (memberDTO != null) {
            if (passwordEncoder.matches(passwd, memberDTO.getPassword())) { // 로그인한 비밀번호와 입력한 비밀번호가 일치할 때
                return "redirect:/member/memberInfo"; // 회원정보 수정 페이지로 이동
            }
        }

        model.addAttribute("fail", "비밀번호가 일치하지않습니다.");

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
        
        // 전화번호 '-' 기준으로 분리
        String phone = memberDTO.getPhone();
        String[] phones = phone.split("-");

        model.addAttribute("phones", phones);
    }

    @PostMapping("/modifyMemberInfo")
    public String modifyMember(@AuthenticationPrincipal MemberDTO memberDTO, MemberDTO updateMemberDTO, String isConfirm, RedirectAttributes redirectAttributes){
        // 회원 정보 수정
        log.info("/member/modifyMemberInfo(POST)...");
        log.info(memberDTO.isHasCat());
        log.info(updateMemberDTO.isHasCat());

        if (isConfirm.equals("false")) {
            redirectAttributes.addFlashAttribute("msg", "이메일 인증을 하지않았습니다.");
            return "redirect:/member/modifyMemberInfo";
        }

        memberService.modifyMemberInfo(updateMemberDTO);

        // 세션 사용자 정보 업데이트
        sessionReset(memberDTO);

        return "/member/memberInfo";
    }

    @GetMapping("/modifyPasswd")
    public String modifyPasswd(String matchPw){
        // 비밀번호 변경 페이지

        return "/member/modifyPasswd";
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
    public String modifyMyCatPost(@AuthenticationPrincipal MemberDTO memberDTO, MemberDTO updateMemberDTO){
        // 고양이 정보수정
        log.info(updateMemberDTO);

        memberService.modifyMyCat(updateMemberDTO);

        /* 데이터가 저장된 후에 이미지 파일 이동 */
        if (updateMemberDTO.getProfileImg() != null) {
            String splits[] = updateMemberDTO.getProfileImg().split("/");

            moveFile(splits[0]);
            moveFile("s_" + splits[0]);
        }

        // 세션 사용자 정보 업데이트
        sessionReset(memberDTO);

        return "redirect:/member/memberInfo";
    }

    @GetMapping("/exit")
    public void exit(){
        // 회원탈퇴페이지
    }

    @PostMapping("/exit")
    public String exit(Long mno){
        // 회원탈퇴
        log.info("/member/exit");

        memberService.removeMember(mno);

        SecurityContextHolder.clearContext();

        return "redirect:/member/exit";
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

    private Map<String, Boolean> removeFile(String dateString, String fileName, String uploadPath) {
        // 파일 삭제
        String newUploadPath = uploadPath + "/" + dateString;
        Resource resource = new FileSystemResource(newUploadPath + File.separator + fileName);
        String resourceName = resource.getFilename();


        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;
        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            removed = resource.getFile().delete(); //resource.delete 메서드로 삭제

            //썸네일이 존재한다면
            if(contentType.startsWith("image")){
                File thumbFile = new File(newUploadPath + File.separator + "s_" + fileName);
                thumbFile.delete();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        resultMap.put("result", removed);
        return resultMap;
    }
}

