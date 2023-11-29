package com.example.pink_jelly.ban.service;

import com.example.pink_jelly.ban.dto.BanDTO;

import java.util.List;

public interface BanService {

    List<BanDTO> banList(Long mno); // 차단 목록
    boolean addBan(BanDTO banDTO); // 차단 추가
    boolean removeBan(Long mno, String memberId); // 차단 삭제
    boolean isBan(Long mno, String memberId); // 차단 여부

    boolean banned(String memberId); // 내가 차단당함

}
