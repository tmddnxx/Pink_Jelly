package com.example.pink_jelly.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberVO { // 회원정보

    private Long mno; // 회원 고유 넘버
    private String memberId; // 회원 아이디
    private String passwd; // 회원 비밀번호
    private String email; // 회원 이메일
    private String memberName; // 회원이름
    private String phone; // 회원 전화번호
    private String nickName; // 회원 닉네임
    private boolean isCat; // 고양이 여부
    private String catAge; // 고양이 나이 (개월/년)
    private String catSex; // 고양이 성별
    private String variety; // 고양이 품종
    private String profileImg; // 프로필이미지 (고양이사진)
    private int gmingCnt; // 그루밍 수
    private int gmerCnt; // 그루머 수
}
