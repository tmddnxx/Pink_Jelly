package com.example.pink_jelly.friends.controller;

import com.example.pink_jelly.friends.dto.FriendsDTO;
import com.example.pink_jelly.friends.service.FriendsService;
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
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendsController {

    private final FriendsService friendsService;

    @ApiOperation(value = "Grooming of Member", notes = "Get 방식으로 특정 회원의 그루밍 목록")
    @GetMapping(value = "/list/{mno}")
    public List<FriendsDTO> getFriendsList(@PathVariable("mno") Long mno){ // mno로 특정 회원의 친구 목록 츨략
        List<FriendsDTO> dtoList = friendsService.gmingList(mno);

        return dtoList;
    }

    @ApiOperation(value = "Add Friends", notes = "Post 방식으로 특정 회원의 그루밍 추가")
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> add(Long mno, @RequestBody FriendsDTO friendsDTO){
        Map<String, Object> response = new HashMap<>();
            log.info("로그인 mno : " + mno);
        try {
            friendsDTO.setMno(mno);
            log.info("프렌즈디티오의 mno : " + friendsDTO.getMno());
            boolean result = friendsService.addGming(friendsDTO);
            friendsService.gmerCntUpdate(friendsDTO.getMemberId());
            response.put("result", result);
            log.info("Friend added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("result", false);
            log.error("Error occurred while adding friend: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @ApiOperation(value = "Remove Friends", notes = "Post 방식으로 특정 회원의 그루밍 삭제")
    @PostMapping("/remove")
    public ResponseEntity<Map<String, Object>> remove(Long mno, @RequestParam String memberId) {
        Map<String, Object> response = new HashMap<>();
        log.info("remove 파라미터 mno : "+mno);
        try {

            boolean result = friendsService.removeGming(mno, memberId);
            friendsService.gmerCntUpdate(memberId);
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
