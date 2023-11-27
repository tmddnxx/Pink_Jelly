package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.mapper.FriendsMapper;
import com.example.pink_jelly.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final ModelMapper modelMapper;

    @Override
    public void registerMember(MemberDTO memberDTO) { // 회원가입
        MemberVO memberVO = modelMapper.map(memberDTO, MemberVO.class);

        memberMapper.insertMember(memberVO);
    }

    @Override
    public void removeMember(Long mno) { // 회원탈퇴
        memberMapper.deleteMember(mno);
    }

    @Override
    public void modifyMemberInfo(MemberDTO memberDTO) { // 회원정보수정(이메일, 이름, 전화번호, 닉네임, 소개글)
        MemberVO memberVO = modelMapper.map(memberDTO, MemberVO.class);

        log.info("modifyMemberInfo,,,");
        log.info(memberVO.isHasCat());

        if (memberVO.isHasCat()) { // 고양이를 보유 하지 않는경우
            memberMapper.catInfoDel(); // 고양이 정보 삭제
        }

        memberMapper.updateMember(memberVO);
    }

    @Override
    public void modifyPasswd(MemberDTO memberDTO) { // 비밀번호 변경
        MemberVO memberVO = modelMapper.map(memberDTO, MemberVO.class);

        memberMapper.updatePasswd(memberVO);
    }

    @Override
    public void modifyMyCat(MemberDTO memberDTO) { // 고양이 정보 수정
        MemberVO memberVO = modelMapper.map(memberDTO, MemberVO.class);

        memberMapper.updateMyCat(memberVO);
    }

    @Override
    public MemberDTO getMember(Long mno) { // 멤버 정보 가져오기
        MemberVO memberVO = memberMapper.getMember(mno);

        MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);

        return memberDTO;
    }

    @Override
    public MemberDTO login(String memberId, String passwd) { // 로그인처리
        MemberVO memberVO = memberMapper.login(memberId, passwd);

        MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);

        return memberDTO;
    }

    @Override
    public MemberDTO findById(String memberId) {
        // 아이디로 회원 정보 조회
        MemberVO memberVO = memberMapper.findById(memberId);

        MemberDTO memberDTO = null;
        if (memberVO != null) {
            memberDTO = modelMapper.map(memberVO, MemberDTO.class);
            String[] splits = memberDTO.getProfileImg().split("/");
            memberDTO.setProfileImg(splits[0]);
            memberDTO.setDateString(splits[1]);
        }

        return memberDTO;
    }

    @Override
    public boolean checkIdDuplicate(String memberId){
        // 아이디 중복검사

        return memberMapper.exitsById(memberId);
    }

    @Override
    public String getFileName(Long mno) {
        // 프로필 이미지 파일명 가져오기
        return memberMapper.findByMnoWithImg(mno);
    }

    @Override
    public void updateIntroduce(MemberDTO memberDTO) {
        // 회원 소개글 수정
        MemberVO memberVO = modelMapper.map(memberDTO, MemberVO.class);

        memberMapper.updateIntroduce(memberVO);

    }
}
