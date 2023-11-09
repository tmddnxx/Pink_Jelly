package com.example.pink_jelly.mapperTests;

import com.example.pink_jelly.domain.CatsCommentVO;
import com.example.pink_jelly.dto.CatsCommentDTO;
import com.example.pink_jelly.mapper.CRBCommentMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class CRBCMapperTest {
    @Autowired
    private CRBCommentMapper commentMapper;

    @Test
    public void insert(){
        CatsCommentVO catsCommentVO = CatsCommentVO.builder()
                .crbNo(1L)
                .memberId("testId")
                .nickName("testNick")
                .comment("testComment")
                .build();
        commentMapper.insert(catsCommentVO);
    }

    @Test
    public void list(){
       log.info( commentMapper.selectList(1L, 0, 10));
    }

    @Test
    public void delete(){
        commentMapper.delete(2L);
    }
}
