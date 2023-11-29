package com.example.pink_jelly.ban.mapper;

import com.example.pink_jelly.ban.vo.BanVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BanMapper {

    List<BanVO> banList(Long mno); // 차단 목록

    boolean insertBan(BanVO banVO); // 차단하기

    boolean deleteBan(@Param("mno")Long mno,@Param("memberId") String memberId); // 차단해제

    boolean isBan(@Param("mno") Long mno,@Param("memberId") String memberId); // 차단여부

    boolean banned(String memberId); // 차단당함

    void deleteBanAll(Long mno); //전체 삭제
}

