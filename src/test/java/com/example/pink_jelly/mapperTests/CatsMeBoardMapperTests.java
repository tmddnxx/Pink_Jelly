package com.example.pink_jelly.mapperTests;

import com.example.pink_jelly.catsMe.vo.CatsMeBoardVO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.catsMe.mapper.CatsMeMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class CatsMeBoardMapperTests {
    @Autowired
    private CatsMeMapper catsMeMapper;

    //완
    @Test
    public void insertCatsMeBoardTest(){
        CatsMeBoardVO catsMeBoardVO = CatsMeBoardVO.builder()
                .memberId("adsfnss")
                .nickName("토미")
                .profileImg("test")
                .title("제목 입니당~~")
                .content("내용 블라블라블라 블라 블라")
//                .cmbImg("on")
                .status("입양전")
                .hit(1)
                .build();
        catsMeMapper.insert(catsMeBoardVO);
    }
    //완
    @Test
    public void getCountTest() {
        int total = catsMeMapper.getCount(PageRequestDTO.builder()
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
//        List<CatsMeBoardVO> list = catsMeMapper.selectList(pageRequestDTO);
//        list.forEach(log::info);
    }
    //완
    @Test
    public void selectOneTest() {
        CatsMeBoardVO catsMeBoardVO = catsMeMapper.getOne(3L);
        log.info(catsMeBoardVO);
    }
    //완
    @Test
    public void updateHit() {
        catsMeMapper.updateHit(3L);
    }
}
