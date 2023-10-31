package com.example.pink_jelly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BanDTO {
    private Long banNo; // 차단 고유번호 
    private String memberId; // 회원 아이디
    private String banId; // 차단 아이디
    private String banName; // 차단 닉네임
    private String hidden; // 숨김 아이디
    private Long mno; // 회원 고유번호
}
