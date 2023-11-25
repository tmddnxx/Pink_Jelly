package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.AdminSearchDTO;
import com.example.pink_jelly.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    void insertMember(MemberVO memberVO); // 회원가입

    void deleteMember(Long mno); // 회원탈퇴

    void updateMember(MemberVO memberVO); // 회원정보수정 ( 이메일, 이름, 전화번호, 닉네임, 소개글)

    void updatePasswd(MemberVO memberVO); // 비밀번호 변경

    void updateMyCat(MemberVO memberVO); // 고양이 정보 수정

    MemberVO getMember(Long mno); // 회원정보 들고오기

    MemberVO login(String memberId, String passwd); // 로그인 처리

    MemberVO findById(String memberId); // 아이디로 회원정보 찾기

    boolean exitsById(String memberId); // 아이디 존재 여부

    MemberVO findByEmail(String email); // 이메일로 회원정보 찾기

    String findByMnoWithImg(Long mno); // 회원번호와 일치하는 프로필 이미지 반환

    void updateProfileImg(@Param("profileImg") String profileImg, @Param("mno") Long mno); // 회원번호와 일치하는 프로필 이미지 모두 삭제

    void updateIntroduce(MemberVO memberVO); // 회원소개글 수정

    List<MemberVO> searchAll(AdminSearchDTO adminSearchDTO); //관리자 검색기능
}
