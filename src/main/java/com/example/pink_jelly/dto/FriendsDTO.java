package com.example.pink_jelly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendsDTO {
    private Long fno; // 친구 고유번호
    private String memberId; // 회원 아이디
    private String friendId; // 친구 아이디
    private String friendName; // 친구 닉네임
    private Long mno; // 회원 고유번호
}
