package com.example.pink_jelly.serviceTests;

import com.example.pink_jelly.mainComment.dto.MainCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.mainComment.service.MainCommentService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class MainCommentServiceTests {
    @Autowired
    private MainCommentService mainCommentService;

    //완
    @Test
    public void insertTest() {
        MainCommentDTO mainCommentDTO = MainCommentDTO.builder()
                .mbNo(1L)
                .memberId("123")
                .nickName("123")
                .comment("123")
                .build();
        mainCommentService.register(mainCommentDTO);
    }
    //삭제 테스트 완
    @Test
    public void deleteTest() {
//        mainCommentService.remove(34L);
    }
    @Test
    public void getListTest() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(5)
                .build();
        PageResponseDTO<MainCommentDTO> list = mainCommentService.getListMainComment(1L, pageRequestDTO);
        list.getDtoList().forEach(log::info);
    }
}
