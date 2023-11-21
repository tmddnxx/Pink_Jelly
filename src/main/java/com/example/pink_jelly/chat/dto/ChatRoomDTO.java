package com.example.pink_jelly.chat.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoomDTO {
    private String roomId; // 채팅방 아이디
    private String name; // 채팅방 이름
    private Set<WebSocketSession> sessionSet = new HashSet<>();
    //WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션

    public static ChatRoomDTO create(String name) { // 채팅방 생성
        ChatRoomDTO room  = new ChatRoomDTO();

        room.roomId = UUID.randomUUID().toString(); // uuid 생성해서 채팅방 아이디로
        room.name = name; // 채팅방 이름

        return room;
    }
}
