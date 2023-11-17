package com.example.pink_jelly.mapperTests;

import com.example.pink_jelly.domain.EternalRestBoardVO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.mapper.EternalRestBoardMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class RestBoardMapperTest {

    @Autowired
    private EternalRestBoardMapper eternalRestBoardMapper;

    @Test
    public void insert(){
        EternalRestBoardVO eternalRestBoardVO = EternalRestBoardVO.builder()
                .memberId("lsw6878")
                .nickName("tmddnnick")
                .profileImg("default.png")
                .title("장례식 게시판 입니다")
                .content("아래는 예시입니다")
                .mno(17L)
                .build();
        eternalRestBoardMapper.insert(eternalRestBoardVO);
    }

    @Test
    public void getCount(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                        .page(1)
                                .size(10).build();

        log.info(eternalRestBoardMapper.getCount(pageRequestDTO));
    }

    @Test
    public void selectList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10).build();
        List<EternalRestBoardVO> voList = eternalRestBoardMapper.selectList(pageRequestDTO);
        for (EternalRestBoardVO list : voList){
            log.info(list);
        }
    }

    @Test
    public void getOne(){
        log.info(eternalRestBoardMapper.getOne(1L));
    }

    @Test
    public void insertSad(){
        eternalRestBoardMapper.insertRestSad(17L, 1L);
    }

    @Test
    public void isSad(){
        log.info(eternalRestBoardMapper.isRestSad(17L, 1L));
    }

    @Test
    public void deleteSad(){
        eternalRestBoardMapper.removeRestSad(17L, 1L);
    }

}
