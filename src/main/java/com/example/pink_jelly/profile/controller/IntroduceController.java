package com.example.pink_jelly.profile.controller;

import com.example.pink_jelly.member.dto.MemberDTO;
import com.example.pink_jelly.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/introduce")
public class IntroduceController {

    private final MemberService memberService;
    private final UserDetailsService userDetailsService;

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> update(@RequestBody MemberDTO memberDTO){

        Map<String, Object> resultMap = new HashMap<>();
        memberService.updateIntroduce(memberDTO);
        sessionReset(memberDTO);
        String introduce = memberDTO.getIntroduce();
        resultMap.put("introduce", introduce);
        return resultMap;

    }
    private void sessionReset(MemberDTO memberDTO) {
        // 세션 사용자 정보 업데이트
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberDTO.getMemberId());
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, new HashSet<GrantedAuthority>());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }
}
