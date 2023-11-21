package com.example.pink_jelly.chat.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@ToString
@Document(collection = "chat_messages")
public class ChatMessageDTO {
    @Id
    private String messageId; // 메세지 아이디
    private String roomId; // 채팅방 아이디
    private String writer; // 채팅 작성자
    private String message; // 채팅 메세지
    private String sendTime; // 채팅 보낸 시간

    public ChatMessageDTO() {
        this.messageId = UUID.randomUUID().toString();
        this.sendTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }
}
