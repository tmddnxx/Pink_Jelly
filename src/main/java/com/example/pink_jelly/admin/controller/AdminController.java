package com.example.pink_jelly.admin.controller;

import com.example.pink_jelly.admin.AdminSearchDTO;
import com.example.pink_jelly.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Log4j2
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/login")
    public void login(){
        // 관리자 로그인 페이지
    }


    @GetMapping("/main")
    public void main(){

    }
    @GetMapping("/boards")
    public void boardList() { // 게시물 목록

    }

    @GetMapping("/members")
    public void memberList(@Valid AdminSearchDTO adminSearchDTO, BindingResult bindingResult, Model model){
        log.info(adminSearchDTO);
        if(bindingResult.hasErrors()){
            adminSearchDTO =AdminSearchDTO.builder().build();
        }
        model.addAttribute("adminSearchDTO", adminService.adminMemberSearch(adminSearchDTO));
    }

}
