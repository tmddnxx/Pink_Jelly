package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.mapper.MemberMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
//@Service
//@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        log.info("loadUserByUsername...");
        log.info("memberId: " + memberId);

        MemberVO memberVO = memberMapper.findById(memberId);
        log.info("passwd: " + memberVO.getPasswd());

        // 사용자 정보가 없는 경우
        if (memberVO == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        log.info(modelMapper.map(memberVO, MemberDTO.class));

        return modelMapper.map(memberVO, MemberDTO.class);
    }
}