package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
@Log4j2
public class AdminRestController {
    private final AdminService adminService;

    @GetMapping("/mainList")
    public PageResponseDTO<MainBoardDTO> mainList(PageRequestDTO pageRequestDTO) {
        log.info("main GET ...");

        PageResponseDTO<MainBoardDTO> response = null;

        response = adminService.mainList(pageRequestDTO);

        return response;
    }


    @GetMapping("/catsMeList")
    public PageResponseDTO<CatsMeBoardDTO> catsMeList(PageRequestDTO pageRequestDTO) {
        log.info("catsMe GET ...");

        PageResponseDTO<CatsMeBoardDTO> response = null;

        response = adminService.catsList(pageRequestDTO);

        return response;
    }

    @GetMapping("/reviewList")
    public PageResponseDTO<CatsReviewBoardDTO> reviewList(PageRequestDTO pageRequestDTO) {
        log.info("review GET ...");

        PageResponseDTO<CatsReviewBoardDTO> response = null;

        response = adminService.reviewList(pageRequestDTO);

        return response;
    }
}
