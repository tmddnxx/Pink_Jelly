package com.example.pink_jelly.chat.mapper;

import com.example.pink_jelly.chat.dto.ChatMessageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    public void insertMessage(ChatMessageDTO chatMessageDTO); // 메세지 정보 DB에 추가
    public List<ChatMessageDTO> findAllByRoomId(String roomId); // 채팅방 번호로 채팅 메시지 모두 가져오기
    public ChatMessageDTO selectMessage(Long cmNo); // 메세지 정보 가져오기
}
