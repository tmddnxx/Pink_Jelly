package com.example.pink_jelly.chat.controller;

import com.example.pink_jelly.chat.dto.ChatMessageDTO;
import com.example.pink_jelly.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; // 특정 Broker로 메세지를 전달
    private final ChatMessageRepository chatMessageRepository;

    // Client가 SEND할 수 있는 경로
    // stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    // "/pub/chat/enter"
    @MessageMapping("/chat/enter") // WebSocket으로 들어오는 메세지 발행 처리
    public void enter(ChatMessageDTO message) {
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageDTO message) {
        // 메세지를 db에 저장
        chatMessageRepository.save(message);

        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
