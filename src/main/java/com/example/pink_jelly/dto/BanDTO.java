package com.example.pink_jelly.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BanDTO {
    private Long banNo; // 차단 고유번호 
    private String memberId; // 차단 아이디
    private String nickName; // 차단 닉네임
    private Long mno; // 회원 고유번호 ( 차단한 사람 )


}
