package com.example.pink_jelly.chat.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private Long cmNo; // 메세지 아이디
    private String roomId; // 채팅방 아이디
    private Long mno; // 회원 고유번호
    private String profileImg; // 프로필 이미지
    private String dateString; // 프로필 사진 저장 날짜
    private String writer; // 채팅 작성자
    private String message; // 채팅 메세지
    private LocalDateTime sendTime; // 채팅 보낸 시간

    public String getProfileImg() {
        String[] imgSplits = profileImg.split("/");
        return imgSplits[0];
    }

    public String getDateString() {
        String[] imgSplits = profileImg.split("/");
        return imgSplits[1];
    }
}
