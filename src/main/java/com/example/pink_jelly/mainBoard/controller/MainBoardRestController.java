package com.example.pink_jelly.mainBoard.controller;

import com.example.pink_jelly.mainBoard.service.MainBoardService;
import com.example.pink_jelly.mainBoard.dto.MainBoardDTO;
import com.example.pink_jelly.member.dto.MemberDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getBoard/{mbNo}")
    public MainBoardDTO getBoard(@PathVariable("mbNo")Long mbNo, @AuthenticationPrincipal MemberDTO memberDTO) {
        log.info("/getBoard/{mbNo}...");
        log.info(mbNo);

        MainBoardDTO mainBoardDTO = null;

        if (memberDTO == null) {
            mainBoardDTO = mainBoardService.getBoard(mbNo, "no", 0L);
        }
        else {
            mainBoardDTO = mainBoardService.getBoard(mbNo, "view", memberDTO.getMno());
        }


        return  mainBoardDTO;
    }
}