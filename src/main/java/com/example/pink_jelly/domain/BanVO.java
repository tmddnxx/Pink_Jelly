package com.example.pink_jelly.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BanVO {
    private Long banNo; // 차단 고유번호 
    private String memberId; // 차단회원 아이디
    private String nickName; // 차단회원 닉네임
    private Long mno; // 회원 고유번호

}
