package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.FriendsVO;
import com.example.pink_jelly.dto.FriendsDTO;
import com.example.pink_jelly.mapper.FriendsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class FriendsServiceImpl implements FriendsService{

    private final FriendsMapper friendsMapper;
    private final ModelMapper modelMapper;

    @Override
    public Long addFriend(FriendsDTO friendsDTO) { // 친구 추가
        FriendsVO friendsVO = modelMapper.map(friendsDTO, FriendsVO.class);

        friendsMapper.insertFriend(friendsVO);
        Long fno = friendsVO.getFno();

        return fno;
    }

    @Override
    public void removeFriend(Long fno) {
        friendsMapper.deleteFriend(fno);
    }

    @Override
    public List<FriendsDTO> friendsList(Long mno) {
        List<FriendsVO> voList = friendsMapper.friendsList(mno);

        List<FriendsDTO> dtoList = new ArrayList<>();

        for(FriendsVO friendsVO : voList){
            dtoList.add(modelMapper.map(friendsVO, FriendsDTO.class));
        }

        return dtoList;
    }
}
