package com.example.pink_jelly.chat.service;

import com.example.pink_jelly.chat.dto.ChatMessageDTO;
import com.example.pink_jelly.chat.dto.ChatRoomDTO;
import com.example.pink_jelly.chat.mapper.ChatMessageMapper;
import com.example.pink_jelly.chat.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatService {
    private final ChatRoomMapper chatRoomMapper;
    private final ChatMessageMapper chatMessageMapper;

    // 채팅 메세지
    public ChatMessageDTO sendMessage(ChatMessageDTO messageDTO) {
        chatMessageMapper.insertMessage(messageDTO); // db에 채팅 메세지 정보 저장
        log.info("sendMessage...");
        log.info(messageDTO.getCmNo());

        return messageDTO;
    }

    public List<ChatMessageDTO> getMessages(String roomId) {

        return chatMessageMapper.findAllByRoomId(roomId);
    }

    // 채팅방
    public ChatRoomDTO createChatRoom(Long toMno, Long fromMno, String name) {
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.create(name);
        chatRoomMapper.insertChatRoom(chatRoomDTO);
        chatRoomMapper.insertChatList(toMno, fromMno, chatRoomDTO.getRoomId());

        return chatRoomDTO;
    }

    public List<ChatRoomDTO> getRooms(Long mno) {
        return chatRoomMapper.selectAllRooms(mno);
    }


    public ChatRoomDTO getRoom(String roomId) {
        return chatRoomMapper.findRoomById(roomId);
    }
}
