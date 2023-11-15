package com.example.pink_jelly.mapperTests;

import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.mapper.MainBoardMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@Log4j2
@SpringBootTest
public class MainBoardMapperTests {
    @Autowired
    private MainBoardMapper mainBoardMapper;

    //완
    @Test
    public void insertMainBoardTest(){
        MainBoardVO mainBoardVO = MainBoardVO.builder()
                .memberId("adsfnss")
                .nickName("토미")
                .profileImg("test")
                .title("제목 입니당~~")
                .content("내용 블라블라블라 블라 블라")
                .mainImg("sadfasfd.jpg")
                .myCat("on")
                .variety("브리티쉬 숏")
                .mno(1L)
                .build();
        mainBoardMapper.insert(mainBoardVO);
    }
    //완
    @Test
    public void getCountTest() {
        int total = mainBoardMapper.getCount(PageRequestDTO.builder()
                .page(1)
                .size(3)
                .build());
        log.info(total);
    }
    //완
    @Test
    public void selectListTest() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(3)
                .build();
        List<MainBoardVO> list = mainBoardMapper.selectList(1, 9, null, null, null, null);
        list.forEach(log::info);
    }
    //완
    @Test
    public void selectOneTest() {
        MainBoardVO mainBoardVO = mainBoardMapper.getOne(40L);
        log.info(mainBoardVO);
    }
    //완
    @Test
    public void updateHitTest() {
        mainBoardMapper.updateHit(4L);
    }
    //완
    @Test
    public void deleteOneTest(){
        mainBoardMapper.deleteOne(19L);
    }
    //완
    @Test
    public void updateBoardTest() {
        MainBoardVO mainBoardVO = MainBoardVO.builder()
                .mbNo(5L)
                .title("수정됨")
                .content("수정 컨텐트")
                .myCat("on")
//                .mainImg("이미지")
                .variety("고얌미")
                .build();
        mainBoardMapper.updateBoard(mainBoardVO);
    }
    @Test
    public void upCnt() {
        mainBoardMapper.upCommentCnt(21L);
    }
}
