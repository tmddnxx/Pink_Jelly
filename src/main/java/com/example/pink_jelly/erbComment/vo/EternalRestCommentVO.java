package com.example.pink_jelly.erbComment.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EternalRestCommentVO {
    private Long comNo; //댓글 고유번호
    private Long erbNo; //게시판 번호
    private String memberId; //회원 아이디
    private String nickName; //회원 닉네임
    private String comment; //댓글 내용
    private Long parentNo; //대댓글 작성시 댓글고유번호
    private LocalDateTime addDate; //작성 시간
    private Long mno; //회원 고유번호
    private String profileImg; //프로필 이미지
}
