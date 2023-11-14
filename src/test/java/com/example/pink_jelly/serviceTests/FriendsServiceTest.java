package com.example.pink_jelly.serviceTests;

import com.example.pink_jelly.dto.FriendsDTO;
import com.example.pink_jelly.service.FriendsService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class FriendsServiceTest {

    @Autowired
    private FriendsService friendsService;

    @Test
    public void friendsList(){
        List<FriendsDTO> friendsDTOList = friendsService.gmingList(17L);
        for (FriendsDTO friendsDTO : friendsDTOList){
            log.info(friendsDTO);
        }
    }

    @Test
    public void addFriend(){
        FriendsDTO friendsDTO = FriendsDTO.builder()
                .memberId("친구아이디1")
                .nickName("친구닉네임1")
                .mno(16L).build();
        friendsService.addGming(friendsDTO);
    }

    @Test
    public void remove(){
        friendsService.removeGming(16L, "");
    }

    @Test
    public void isFriend(){
        log.info(friendsService.isGming(17L, "friendId2"));
    }
}
