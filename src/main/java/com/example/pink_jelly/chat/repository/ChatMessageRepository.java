package com.example.pink_jelly.chat.repository;

import com.example.pink_jelly.chat.dto.ChatMessageDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageDTO, String> {
    public List<ChatMessageDTO> findAllByRoomId(String roomId);
}
