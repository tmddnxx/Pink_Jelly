package com.example.pink_jelly.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EternalRestBoardVO {

    private Long erbNo; // 장례식게시판 고유넘버
    private String memberId; // 작성자 아이디
    private String nickName; // 작성자 닉네임
    private String profileImg; //작성자 프로필 사진(본인고양이사진)
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private LocalDateTime addDate; // 작성일
    private Long sad; // 슬퍼요 갯수
    private Long commentCnt; // 댓글수
    private Long mno; // 작성자 고유넘버
}
