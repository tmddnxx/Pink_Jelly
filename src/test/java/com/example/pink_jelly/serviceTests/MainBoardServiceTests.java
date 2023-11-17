package com.example.pink_jelly.serviceTests;

import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.MainBoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@SpringBootTest
public class MainBoardServiceTests {
    @Autowired
    private MainBoardService mainBoardService;

    //완
    @Test
    public void registerTest() {
        String img1 = "123";
        String img2 = "345";
        List<String> img = new ArrayList<>();
        img.add(img1);
        img.add(img2);
        MainBoardDTO mainBoardDTO = MainBoardDTO.builder()
                .memberId("test")
                .nickName("test")
                .profileImg("test")
                .title("test")
                .content("test")
                .mainImg(img)
                .myCat("true")
                .variety("캣")
                .mno(1L)
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
//        PageResponseDTO<MainBoardDTO> pageResponseDTO = mainBoardService.getList(pageRequestDTO);
//        pageResponseDTO.getDtoList().forEach(log::info);
    }
    //완
    @Test
    public void getBoardTest() {
        MainBoardDTO mainBoardDTO = mainBoardService.getBoard(81L,"read", 27L);;
        log.info(mainBoardDTO);
    }
    //완
    @Test
    public void upHitTest() {
        mainBoardService.upHit(4L);
    }
    //완
    @Test
    public void removeOneTest() {
        mainBoardService.removeOne(18L);
    }

    @Test
    public void modifyBoardTest() {
        MainBoardDTO mainBoardDTO = MainBoardDTO.builder()
                .mbNo(6L)
                .title("수정됨")
                .content("수정 컨텐트")
                .myCat("on")
//                .mainImg("이미지")
                .variety("고얌미")
                .build();
        mainBoardService.modifyBoard(mainBoardDTO);
    }
    @Test
    public void boardlistTest() {
        mainBoardService.getBoard(82L, "read", 27L);
    }
}
