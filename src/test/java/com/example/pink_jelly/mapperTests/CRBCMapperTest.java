package com.example.pink_jelly.mapperTests;

import com.example.pink_jelly.crbComment.vo.CRBCommentVO;
import com.example.pink_jelly.crbComment.mapper.CRBCommentMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class CRBCMapperTest {
    @Autowired
    private CRBCommentMapper crbCommentMapper;

    @Test
    public void insert(){
        for(int i = 0; i < 10; i++) {
            CRBCommentVO crbCommentVO = CRBCommentVO.builder()
                    .crbNo(1L)
                    .memberId("13")
                    .nickName("13")
                    .comment("32")
                    .mno(1L)
                    .build();
            crbCommentMapper.insert(crbCommentVO);
        }
    }

//    @Test
//    public void selectAll() {
//        List<CRBCommentVO> cr = crbCommentMapper.selectAll(1L, PageRequestDTO.builder()
//                .page(1)
//                .size(3)
//                .build());
//        cr.forEach(log::info);
//    }

    @Test
    public void delete(){
        crbCommentMapper.deleteOne(2L);
    }
}
