package com.example.pink_jelly.mapperTests;

import com.example.pink_jelly.mainComment.vo.MainCommentVO;
import com.example.pink_jelly.mainComment.mapper.MainCommentMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Log4j2
@SpringBootTest
public class MainCommentTests {
    @Autowired
    private MainCommentMapper mainCommentMapper;
    //완
    @Test
    public void insertComment() {
        for(int i = 0; i < 30; i++) {
            MainCommentVO mainCommentVO = MainCommentVO.builder()
                    .mbNo(1L)
                    .memberId("321")
                    .nickName("321")
                    .comment("321")
                    .build();
            mainCommentMapper.insert(mainCommentVO);
        }
    }

//    @Test
//    public void selectAll() {
//        List<MainCommentVO> commentVOList = mainCommentMapper.selectAll(1L, PageRequestDTO.builder()
//                .page(1)
//                .size(3)
//                .build());
//        commentVOList.forEach(log::info);
//    }
    @Test
    public void getCountTest() {
        int count = mainCommentMapper.getCount(1L);
        log.info(count);
    }
    @Test
    public void selectListTest() {
        List<MainCommentVO> commentVOList = mainCommentMapper.selectList(1L,1,3);
        commentVOList.forEach(log::info);
    }
}
