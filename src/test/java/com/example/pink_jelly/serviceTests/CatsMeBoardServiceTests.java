package com.example.pink_jelly.serviceTests;

import com.example.pink_jelly.dto.CatsMeBoardDTO;
import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.CatsMeService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class CatsMeBoardServiceTests {
    @Autowired
    private CatsMeService catsMeService;
    //완
    @Test
    public void registerTest() {
        CatsMeBoardDTO catsMeBoardDTO = CatsMeBoardDTO.builder()
                .memberId("adsfnss")
                .nickName("토미")
                .profileImg("test")
                .title("제목 입니당~~")
                .content("내용 블라블라블라 블라 블라")
                .cmbImg("on")
                .status("입양전")
                .build();
        catsMeService.register(catsMeBoardDTO);
    }
    //완
    @Test
    public void getListTest(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(3)
                .build();
//        PageResponseDTO<CatsMeBoardDTO> pageResponseDTO = catsMeService.getList(pageRequestDTO);
//        pageResponseDTO.getDtoList().forEach(log::info);
    }
    //완
    @Test
    public void getBoardTest() {
        CatsMeBoardDTO catsMeBoardDTO = catsMeService.getBoard(1L,"read");
        log.info(catsMeBoardDTO);
    }
    //완
    @Test
    public void upHitTest() {
        catsMeService.upHit(2L);
    }
}
