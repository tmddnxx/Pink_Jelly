package com.example.pink_jelly.security;

import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberMapper memberMapper;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        log.info("loadUserByUsername: " + memberId);

        MemberVO memberVO = memberMapper.findById(memberId);
        log.info("passwd: " + memberVO.getPasswd());

        // 사용자 정보가 없는 경우
        if (memberVO == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        MemberDTO memberDTO = new MemberDTO(
                memberVO.getMno(),
                memberVO.getMemberId(),
                memberVO.getPasswd(),
                memberVO.getEmail(),
                memberVO.getMemberName(),
                memberVO.getPhone(),
                memberVO.getNickName(),
                memberVO.isHasCat(),
                memberVO.getCatAge(),
                memberVO.getCatSex(),
                memberVO.getVariety(),
                memberVO.getProfileImg(),
                memberVO.getGmingCnt(),
                memberVO.getGmerCnt(),
                memberVO.getIntroduce(),
                memberVO.isDel(),
                memberVO.isSocial(),
                memberVO.getRoleSet().stream()
                        .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                        .collect(Collectors.toList())
        );
        log.info(memberDTO);

        return memberDTO;
    }
}