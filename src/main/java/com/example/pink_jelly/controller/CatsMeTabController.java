package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.service.CatsMeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/catsMeTab")
@RequiredArgsConstructor
@Log4j2
public class CatsMeTabController {
    private final CatsMeService catsMeService;

    @GetMapping("/board")
    public ResponseEntity<PageResponseDTO<CatsMeBoardDTO>>catsMeTab(PageRequestDTO pageRequestDTO, Long mno, String memberId,@AuthenticationPrincipal MemberDTO memberDTO){
        log.info("/board...");
        log.info(pageRequestDTO);

        PageResponseDTO<CatsMeBoardDTO> response;
        if(memberDTO != null){
            String loginId = memberDTO.getMemberId();
            Long loginMno = memberDTO.getMno();
            response = catsMeService.getList(pageRequestDTO, loginMno, loginId);
        }else{
            response = catsMeService.getList(pageRequestDTO, mno, memberId);
        }

       return ResponseEntity.ok(response);
    }

    @GetMapping("/review")
    public ResponseEntity<PageResponseDTO<CatsReviewBoardDTO>>catsReviewTab(@RequestParam int page, @RequestParam int size, Long mno, String memberId,@AuthenticationPrincipal MemberDTO memberDTO){
        log.info("사이즈 " + size);
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(page);
        pageRequestDTO.setSize(size);
        PageResponseDTO<CatsReviewBoardDTO> response;

        if(memberDTO != null){
            String loginId = memberDTO.getMemberId();
            Long loginMno = memberDTO.getMno();
            response = catsMeService.getReviewBoardList(pageRequestDTO, loginMno, loginId);
        }else{
            response = catsMeService.getReviewBoardList(pageRequestDTO, mno, memberId);
        }

        return ResponseEntity.ok(response);
    }
}
