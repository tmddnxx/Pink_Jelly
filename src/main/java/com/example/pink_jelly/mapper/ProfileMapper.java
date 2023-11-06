package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {

    int groomingCnt(MemberVO memberVO);
    int groomerCnt(MemberVO memberVO);
}
