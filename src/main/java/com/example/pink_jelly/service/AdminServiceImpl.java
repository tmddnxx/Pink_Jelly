package com.example.pink_jelly.service;

import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminServiceImpl implements AdminService{
    private ModelMapper modelMapper;
    private final MemberMapper memberMapper;


    @Override
    public List<MemberDTO> memberInfo() {

        return null;
    }
}
