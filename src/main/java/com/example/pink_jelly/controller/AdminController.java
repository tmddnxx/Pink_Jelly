package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.AdminSearchDTO;
import com.example.pink_jelly.service.AdminService;
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

    @GetMapping("")
    public String adminLogin(){
        return "/admin/login";
    }

    @GetMapping("/boardList")
    public void boardList(){
    }
    @GetMapping("/memberList")
    public void memberList(@Valid AdminSearchDTO adminSearchDTO, BindingResult bindingResult, Model model){
        log.info(adminSearchDTO);
        if(bindingResult.hasErrors()){
            adminSearchDTO =AdminSearchDTO.builder().build();
        }
        model.addAttribute("adminSearchDTO", adminService.adminMemberSearch(adminSearchDTO));
    }

}
