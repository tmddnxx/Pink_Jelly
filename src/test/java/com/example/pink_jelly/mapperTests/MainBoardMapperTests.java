package com.example.pink_jelly.mapperTests;

import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.mapper.MainBoardMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
                .mainImg("test")
                .myCat("on")
                .variety("브리티쉬 숏")
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
        List<MainBoardVO> list = mainBoardMapper.selectList(pageRequestDTO);
        list.forEach(log::info);
    }
    //완
    @Test
    public void selectOneTest() {
        MainBoardVO mainBoardVO = mainBoardMapper.getOne(3L);
        log.info(mainBoardVO);
    }
}
