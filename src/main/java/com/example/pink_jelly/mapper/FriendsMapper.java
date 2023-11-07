package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.FriendsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendsMapper {
    List<FriendsVO> friendsList(Long mno); // 친구목록
    void insertFriend(FriendsVO friendsVO); // 친구추가
    void deleteFriend(Long fno); // 친구삭제
}
