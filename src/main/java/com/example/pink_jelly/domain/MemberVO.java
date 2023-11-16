package com.example.pink_jelly.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    private boolean hasCat; // 고양이 여부
    private String catAge; // 고양이 나이 (개월/년)
    private String catSex; // 고양이 성별
    private String variety; // 고양이 품종
    private String profileImg; // 프로필이미지 (고양이사진)
    private int gmingCnt; // 그루밍 수
    private int gmerCnt; // 그루머 수
    private String introduce; // 소개글
    private boolean del; // 회원 탈퇴 여부
    private boolean social; // 소셜 로그인

    @Builder.Default
    private Set<MemberRole> roleSet=new HashSet<>();

    public void changeProfileImg(String profileImg) {
        this.profileImg=profileImg;
    }

    public void changeEmail(String email) {
        this.email=email;
    }

    public void changeDel(boolean del) {
        this.del=del;
    }

    public void addRole(MemberRole memberRole) {
        this.roleSet.add(memberRole);
    }

    public void clearRoles() {
        this.roleSet.clear();
    }

    public void changeSocial(boolean social) {
        this.social=social;
    }
}
