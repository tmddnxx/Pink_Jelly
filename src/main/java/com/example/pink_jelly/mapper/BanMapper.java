package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.BanVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BanMapper {

    List<BanVO> banList(Long mno); // 차단 목록

    boolean insertBan(BanVO banVO); // 차단하기

    boolean deleteBan(Long mno, String memberId); // 차단해제

    boolean isBan(Long mno, String memberId); // 차단여부

    boolean banned(String memberId); // 차단당함
}

