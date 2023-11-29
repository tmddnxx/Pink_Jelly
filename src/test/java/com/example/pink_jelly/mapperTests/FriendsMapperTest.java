package com.example.pink_jelly.mapperTests;

import com.example.pink_jelly.friends.vo.FriendsVO;
import com.example.pink_jelly.friends.mapper.FriendsMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class FriendsMapperTest {

    @Autowired
    private FriendsMapper friendsMapper;

    @Test
    public void insertFriends(){
        for(int i=1; i<10; i++){
            FriendsVO friendsVO = FriendsVO.builder()
                    .memberId("friendId" + i)
                    .nickName("friendName" +i)
                    .mno(17L)
                    .build();
            friendsMapper.insertGming(friendsVO);
        }
    }

    @Test
    public void deleteFriend(){
        friendsMapper.deleteGming(17L, "");
    }

    @Test
    public void isFriend(){
        log.info(friendsMapper.isFriend(17L, "friendId1"));
    }

    @Test
    public void friendList(){
        List<FriendsVO> voList = friendsMapper.gmingList(17L);

        for( FriendsVO friendsVO : voList ){
            log.info(friendsVO);
        }
    }

    @Test
    public void cntTest(){
        friendsMapper.gmingCntUpdate(16L, true);
    }


    @Test
    public void gmerList(){
        log.info(friendsMapper.gmerList("lsw6878"));
    }

    @Test
    public void gmerCnt(){
        friendsMapper.gmerCntUpdate("lsw6878");
    }

}
