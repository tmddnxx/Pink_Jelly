package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.BanDTO;
import com.example.pink_jelly.dto.FriendsDTO;
import com.example.pink_jelly.service.BanService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ban")
@RequiredArgsConstructor
@Log4j2
public class BanController {

    private final BanService banService;

    @ApiOperation(value = "Grooming of Member", notes = "Get 방식으로 특정 회원의 차단 목록")
    @GetMapping(value = "/list/{mno}")
    public List<BanDTO> getBanList(@PathVariable("mno") Long mno){ // mno로 특정 회원의 차단 목록 츨략
        List<BanDTO> dtoList = banService.banList(mno);

        return dtoList;
    }

    @ApiOperation(value = "Add Ban", notes = "Post 방식으로 특정 회원의 차단 추가")
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> add(Long mno, @RequestBody BanDTO banDTO){
        Map<String, Object> response = new HashMap<>();
        log.info("로그인 mno : " + mno);
        try {
            banDTO.setMno(mno);
            log.info("프렌즈디티오의 mno : " + banDTO.getMno());
            boolean result = banService.addBan(banDTO);

            response.put("result", result);
            log.info("Friend added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("result", false);
            log.error("Error occurred while adding friend: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @ApiOperation(value = "Remove Ban", notes = "Post 방식으로 특정 회원의 차단 삭제")
    @PostMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(Long mno, @RequestParam String memberId) {
        Map<String, Object> response = new HashMap<>();
        log.info("remove 파라미터 mno : "+mno);
        try {

            boolean result = banService.removeBan(mno, memberId);

            response.put("result", result);
            log.info("friend removed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("result", false);
            log.error("Error occurred while removing friend: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
