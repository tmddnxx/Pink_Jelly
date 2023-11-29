package com.example.pink_jelly.security;

import com.example.pink_jelly.member.dto.MemberRole;
import com.example.pink_jelly.member.vo.MemberVO;
import com.example.pink_jelly.member.dto.MemberDTO;
import com.example.pink_jelly.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
@Primary
public class CustomUserDetailService implements UserDetailsService {
    private final MemberMapper memberMapper;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        log.info("loadUserByUsername: " + memberId);

        MemberVO memberVO = memberMapper.findById(memberId);
        memberVO.addRole(MemberRole.USER);
//        log.info("passwd: " + memberVO.getPasswd());

        // 사용자 정보가 없는 경우
        if (memberVO == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);

        if (memberVO.getProfileImg() != null) {
            String[] splits = memberVO.getProfileImg().split("/");
            memberDTO.setProfileImg(splits[0]);
            memberDTO.setDateString(splits[1]);
        }

        log.info("loadUserByUsername...");
        log.info(memberDTO.isHasCat());

        return memberDTO;
    }
}