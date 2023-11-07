package com.example.pink_jelly.service;

import com.example.pink_jelly.dto.FriendsDTO;

import java.util.List;

public interface FriendsService {

    Long addFriend(FriendsDTO friendsDTO);
    void removeFriend(Long fno);
    List<FriendsDTO> friendsList(Long mno);
}
