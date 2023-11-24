package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.AdminSearchDTO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminServiceImpl implements AdminService{
    private final ModelMapper modelMapper;
    private final MemberMapper memberMapper;


    @Override
    public List<MemberDTO> adminMemberSearch(AdminSearchDTO adminSearchDTO) {
        List<MemberVO> memberVOList = memberMapper.searchAll(adminSearchDTO);
        List<MemberDTO> memberDTOList = new ArrayList<>();
        memberVOList.forEach(memberVO -> {
            MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);
            memberDTOList.add(memberDTO);
        });

        return memberDTOList;
    }

    @Override
    public void removeMember(Long mno) {
        memberMapper.deleteMember(mno);
    }
}
