package com.example.pink_jelly.catsMe.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CatsMeBoardVO {
    private Long cmbNo; // 입양소 고유번호
    private String memberId; // 회원 아이디
    private String nickName; // 닉네임
    private String profileImg; // 프로필 사진
    private String title; // 제목
    private String content; // 내용
    private String catsMeImg; // 입양소 사진
    private LocalDateTime addDate; // 등록 날짜
    private String status; // 입양 여부
    private int hit; // 조회수
    private Long mno; //회원 고유넘버
}
