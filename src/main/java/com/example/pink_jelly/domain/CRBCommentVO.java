package com.example.pink_jelly.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CRBCommentVO {
    private Long comNo; // 댓글 고유번호
    private Long crbNo; // 입양후기 고유번호
    private String memberId; // 회원 아이디
    private String nickName; // 닉네임
    private String comment; // 댓글 내용
    private Long parentNo; // 대댓글 고유 번호
    private LocalDateTime addDate; // 등록 날짜
    private Long mno; //회원 고유넘버
    private String profileImg; //프로필 이미지
}
