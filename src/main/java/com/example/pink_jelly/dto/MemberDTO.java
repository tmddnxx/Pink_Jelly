package com.example.pink_jelly.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO implements UserDetails {
//    private MemberVO memberVO;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>();
    }

    @Override
    public String getPassword() {
        return this.passwd;
    }

    @Override
    public String getUsername() {
        return this.memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
