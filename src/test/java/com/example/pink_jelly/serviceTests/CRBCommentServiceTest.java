package com.example.pink_jelly.serviceTests;

import com.example.pink_jelly.dto.CRBCommentDTO;
import com.example.pink_jelly.dto.MainCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.service.CRBCommentService;
import com.example.pink_jelly.service.MainCommentService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class CRBCommentServiceTest {
    @Autowired
    private CRBCommentService crbCommentService;

    //완
    @Test
    public void insertTest() {
        CRBCommentDTO crbCommentDTO = CRBCommentDTO.builder()
                .crbNo(1L)
                .memberId("123")
                .nickName("123")
                .comment("123")
                .mno(1L)
                .build();
        crbCommentService.register(crbCommentDTO);
    }
    //삭제 테스트 완
    @Test
    public void deleteTest() {
        crbCommentService.remove(1L, 1L);
    }
    @Test
    public void getListTest() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(5)
                .build();
        PageResponseDTO<CRBCommentDTO> list = crbCommentService.getListCRBComment(2L, pageRequestDTO);
        list.getDtoList().forEach(log::info);
    }
}
