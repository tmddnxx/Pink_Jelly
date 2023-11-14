package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void insertMember(MemberVO memberVO); // 회원가입

    void deleteMember(Long mno); // 회원탈퇴

    void updateMember(MemberVO memberVO); // 회원정보수정 ( 이메일, 이름, 전화번호, 닉네임, 소개글)

    void updatePasswd(MemberVO memberVO); // 비밀번호 변경

    void updateMyCat(MemberVO memberVO); // 고양이 정보 수정

    MemberVO getMember(Long mno); // 회원정보 들고오기

    MemberVO login(String memberId, String passwd); // 로그인 처리

    MemberVO findById(String memberId); // 아이디로 회원정보 들고오기

    boolean exitsById(String memberId); // 아이디 존재 여부



}
