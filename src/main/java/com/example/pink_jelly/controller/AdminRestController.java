package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.AdminSearchDTO;
import com.example.pink_jelly.dto.MemberDTO;
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
@RequestMapping("/adminRest")
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
}
