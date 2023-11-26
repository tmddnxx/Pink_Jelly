package com.example.pink_jelly.chat.mapper;

import com.example.pink_jelly.chat.dto.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ChatRoomMapper {
    public void insertChatRoom(ChatRoomDTO chatRoom);
    public List<ChatRoomDTO> selectAllRooms(Long mno);

    public ChatRoomDTO findRoomById(String roomId);

    public void insertChatList(@Param("toMno") Long toMno, @Param("fromMno") Long fromMno, @Param("roomId") String roomId);

}
