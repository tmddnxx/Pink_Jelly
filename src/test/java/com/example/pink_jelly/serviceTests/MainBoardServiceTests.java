package com.example.pink_jelly.serviceTests;

import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.MainBoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class MainBoardServiceTests {
    @Autowired
    private MainBoardService mainBoardService;

    //완
    @Test
    public void registerTest() {
        MainBoardDTO mainBoardDTO = MainBoardDTO.builder()
                .memberId("test")
                .nickName("test")
                .profileImg("test")
                .title("test")
                .content("test")
                .mainImg("test")
                .myCat("true")
                .variety("캣")
                .build();
        mainBoardService.register(mainBoardDTO);
    }
    //완
    @Test
    public void getListTest(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(3)
                .build();
        PageResponseDTO<MainBoardDTO> pageResponseDTO = mainBoardService.getList(pageRequestDTO);
        pageResponseDTO.getDtoList().forEach(log::info);
    }

    @Test
    public void getBoardTest() {
        MainBoardDTO mainBoardDTO = mainBoardService.getBoard(3L);
        log.info(mainBoardDTO);
    }
}
