package com.example.pink_jelly.mainBoard.controller;

import com.example.pink_jelly.mainBoard.dto.MainBoardDTO;
import com.example.pink_jelly.member.dto.MemberDTO;
import com.example.pink_jelly.mainBoard.service.MainBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/mainModal")
@RequiredArgsConstructor //의존성 주입
public class MainModalController {

    private final MainBoardService mainBoardService;

    @GetMapping(value = "/{mbNo}")
    public MainBoardDTO getBoard(@PathVariable("mbNo")Long mbNo, @AuthenticationPrincipal MemberDTO memberDTO) {
        MainBoardDTO mainBoardDTO = mainBoardService.getBoard(mbNo, "view", memberDTO.getMno());

        return  mainBoardDTO;
    }
}
