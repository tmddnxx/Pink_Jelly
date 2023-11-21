package com.example.pink_jelly.chat.service;

import com.example.pink_jelly.chat.dto.ChatMessageDTO;
import com.example.pink_jelly.chat.dto.ChatRoomDTO;
import com.example.pink_jelly.chat.mapper.ChatRoomMapper;
import com.example.pink_jelly.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomMapper chatRoomMapper;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅 메세지
    public ChatMessageDTO sendMessage(String roomId, ChatMessageDTO messageDTO) {
        messageDTO.setRoomId(roomId);

        return chatMessageRepository.save(messageDTO); // db에 채팅 메세지 정보 저장
    }

    public List<ChatMessageDTO> getMessages(String roomId) {
        return chatMessageRepository.findAllByRoomId(roomId);
    }

    // 채팅방
    public ChatRoomDTO createChatRoom(Long toMno, Long fromMno, String name) {
        ChatRoomDTO chatRoomDTO = ChatRoomDTO.create(name);
        chatRoomMapper.insertChatRoom(chatRoomDTO);
        chatRoomMapper.insertChatList(toMno, fromMno, chatRoomDTO.getRoomId());

        return chatRoomDTO;
    }

    public List<ChatRoomDTO> getRooms() {
        return chatRoomMapper.selectAllRooms();
    }


    public ChatRoomDTO getRoom(String roomId) {
        return chatRoomMapper.findRoomById(roomId);
    }
}
