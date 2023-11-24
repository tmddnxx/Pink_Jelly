package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
@Log4j2
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/boardList")
    public void boardList(Model model, PageRequestDTO pageRequestDTO){
        PageResponseDTO<MainBoardDTO> mainDTOList = adminService.mainList(pageRequestDTO);
        PageResponseDTO<CatsMeBoardDTO> catsDTOList = adminService.catsList(pageRequestDTO);
        PageResponseDTO<CatsReviewBoardDTO> reviewDTOList = adminService.reviewList(pageRequestDTO);

        model.addAttribute("mainDTOList", mainDTOList);
        model.addAttribute("catsDTOList", catsDTOList);
        model.addAttribute("reviewDTOList", reviewDTOList);
    }
}
