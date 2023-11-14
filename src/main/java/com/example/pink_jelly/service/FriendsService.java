package com.example.pink_jelly.service;

import com.example.pink_jelly.dto.FriendsDTO;
import com.example.pink_jelly.dto.MemberDTO;

import java.util.List;

public interface FriendsService {
    List<FriendsDTO> gmingList(Long mno); // 그루밍 목록
    boolean addGming(FriendsDTO friendsDTO); // 그루밍 추가
    boolean removeGming(Long mno, String memberId); // 그루밍 삭제
    boolean isGming(Long mno, String memberId); // 친구 여부

    List<MemberDTO> gmerList(String memberId); // 그루머 목록
    void gmerCntUpdate(String memberId); // 그루머 수 업데이트

}
