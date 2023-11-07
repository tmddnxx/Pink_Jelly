package com.example.pink_jelly.controller;

import com.example.pink_jelly.dto.FriendsDTO;
import com.example.pink_jelly.service.FriendsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendsController {

    private final FriendsService friendsService;

    @ApiOperation(value = "Friends of Member", notes = "Get 방식으로 특정 회원의 친구 목록")
    @GetMapping(value = "/list/{mno}")
    public List<FriendsDTO> getFriendsList(@PathVariable("mno") Long mno){ // mno로 특정 회원의 친구 목록 츨략
        List<FriendsDTO> dtoList = friendsService.friendsList(mno);

        return dtoList;
    }

    @ApiOperation(value = "Friend Add POST", notes = "POST 방식으로 친구 추가")
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE) // 특정 mno 회원에 친구 추가
    public Map<String, Long> register(@Valid @RequestBody FriendsDTO friendsDTO, BindingResult bindingResult) throws BindException {
        log.info(friendsDTO);

        if(bindingResult.hasErrors()) {
            throw new BindException((bindingResult));
        }

        Map<String, Long> resultMap = new HashMap<>();
        Long fno = friendsService.addFriend(friendsDTO);
        resultMap.put("fno", fno);

        return resultMap;
    }

    @ApiOperation(value = "Delete Friend", notes = "DELETE방식으로 특정 친구 삭제")
    @DeleteMapping(value = "/remove/{fno}")
    public Map<String, Long> remove(@PathVariable("fno") Long fno){
        friendsService.removeFriend(fno);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("fno", fno);

        return  resultMap;
    }


}
