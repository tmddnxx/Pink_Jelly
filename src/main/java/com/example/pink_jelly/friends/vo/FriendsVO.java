package com.example.pink_jelly.friends.vo;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FriendsVO {
    private Long fno; // 친구 고유번호
    private String memberId; // 친구 아이디
    private String nickName; // 친구 닉네임
    private Long mno; // 회원 고유번호

}
