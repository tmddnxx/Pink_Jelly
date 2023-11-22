package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.MainBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping({"/main"})
@RequiredArgsConstructor
public class MainBoardRestController {
    private final MainBoardService mainBoardService;

    @GetMapping("/getList")
    public PageResponseDTO<MainBoardDTO> getList(Model model, PageRequestDTO pageRequestDTO, Long mno, String memberId, @AuthenticationPrincipal MemberDTO memberDTO) {
        log.info("main GET ...");

        PageResponseDTO<MainBoardDTO> response = null;

        if (memberDTO != null) {
            String loginId = memberDTO.getMemberId();
            Long loginMno = memberDTO.getMno();
            response = mainBoardService.getList(pageRequestDTO, loginMno, loginId);
            response.getDtoList().forEach(log::info);
        } else {
            log.info("메인 멤버디티오 : " + memberDTO);
            response = mainBoardService.getList(pageRequestDTO, mno, memberId);
            response.getDtoList().forEach(log::info);
        }

        return response;
    }
}