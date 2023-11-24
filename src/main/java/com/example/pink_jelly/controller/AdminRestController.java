package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.service.AdminService;
import com.example.pink_jelly.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/rest")
@RequiredArgsConstructor
public class AdminRestController {
    private final AdminService adminService;

    //리스트 불러오기
    @GetMapping(value = "/memberList")
    public List<MemberDTO> getMemberList(AdminSearchDTO adminSearchDTO){
        log.info(adminSearchDTO);
        log.info("--------------");

        List<MemberDTO> memberDTOList = adminService.adminMemberSearch(adminSearchDTO);

        log.info(memberDTOList);
        log.info("--------------");
        return memberDTOList;
    }
    //낱개 삭제
    @DeleteMapping(value = "/removeMember/{mno}")
    public void deleteMember(@PathVariable("mno")Long mno){
        adminService.removeMember(mno);
    }
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
