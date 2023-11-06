package com.example.pink_jelly;

import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.mapper.ProfileMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ProfileMapperTest {
    @Autowired
    private ProfileMapper profileMapper;

    @Test
    public void groomingCntTest(){
        MemberVO memberVO = MemberVO.builder()
                .gmingCnt(10)
                .mno(1L)
                .build();
        profileMapper.groomingCnt(memberVO);
    }

    @Test
    public void groomerCntTest(){
        MemberVO memberVO = MemberVO.builder()
                .gmerCnt(3)
                .mno(1L)
                .build();
        profileMapper.groomerCnt(memberVO);
    }
}
