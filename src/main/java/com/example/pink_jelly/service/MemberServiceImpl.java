package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;
    private final MemberMapper memberMapper;

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
    public void modifyMember(MemberDTO memberDTO) { // 회원정보수정(이메일, 이름, 전화번호, 닉네임)
        MemberVO memberVO = modelMapper.map(memberDTO, MemberVO.class);

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


}
