package com.example.pink_jelly.mapperTests;

import com.example.pink_jelly.ban.vo.BanVO;
import com.example.pink_jelly.ban.mapper.BanMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class BanMapperTest {

    @Autowired
    private BanMapper banMapper;

    @Test
    public void insertBan(){
        BanVO banVO = BanVO.builder()
                .memberId("test3")
                .nickName("테스트")
                .mno(16L).build();

        banMapper.insertBan(banVO);
    }

    @Test
    public void banList(){
        List<BanVO> banVOList = banMapper.banList(16L);
        for(BanVO banList : banVOList){
            log.info(banList);
        }
    }

    @Test
    public void isBan(){
        boolean isBan = banMapper.isBan(16L, "test3");
        log.info("로그인한 16번이 밴을 했나요 ? " +isBan);
    }

    @Test
    public void deleteBan(){
        banMapper.deleteBan(16L, "lsw6878");
    }
}
