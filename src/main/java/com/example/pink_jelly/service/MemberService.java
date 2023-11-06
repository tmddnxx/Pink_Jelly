package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.MemberDTO;

public interface MemberService {

    void registerMember(MemberDTO memberDTO); // 회원가입

    void removeMember(Long mno); // 회원탈퇴

    void modifyMember(MemberDTO memberDTO);  // 회원수정(이메일, 이름, 전화번호, 닉네임)

    void modifyPasswd(MemberDTO memberDTO); // 비밀번호변경

    void modifyMyCat(MemberDTO memberDTO); // 고양이 정보 수정
}
