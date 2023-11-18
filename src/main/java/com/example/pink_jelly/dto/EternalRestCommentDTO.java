package com.example.pink_jelly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EternalRestCommentDTO { //추모 게시판 DTO
    private Long comNo; //댓글 고유번호
    private Long erbNo; //게시판 번호
    private String memberId; //회원 아이디
    private String nickName; //회원 닉네임
    private String comment; //댓글 내용
    private Long parentNo; //대댓글 작성시 댓글고유번호
    private LocalDateTime addDate; //작성 시간
    private Long mno; //회원 고유번호
    private String profileImg; //프로필 이미지
    private String dateString; //이미지 폴더
}
