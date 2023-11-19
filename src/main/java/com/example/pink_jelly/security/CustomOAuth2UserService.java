package com.example.pink_jelly.security;

import com.example.pink_jelly.domain.MemberRole;
import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberMapper memberMapper;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("userRequest...");
        log.info(userRequest);

        // 사용자의 OAuth2 클라이언트 등록 정보를 가져옵니다.
        log.info("oauth2 user...");

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        log.info("NAME: " + clientName);

        // OAuth2User 인터페이스를 구현한 객체를 가져옵니다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2 사용자의 속정 정보를 가져와서 로그에 출력합니다.
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        paramMap.forEach((k, v) -> {
            log.info("---------");
            log.info(k + ":" + v);
        });

        /* 변경되는 부분 */
        String email = null;
        String memberName = null;
        switch (clientName) {
            case "kakao":
                email = getKakaoEmail(paramMap);
                break;
        }

        log.info("===============");
        log.info(email);
        log.info("===============");

        return generateDTO(email, paramMap);
    }

    private MemberDTO generateDTO(String email, Map<String, Object> params) {
        MemberVO memberVO = memberMapper.findByEmail(email);

        // 데이터 베이스에 해당 이메일의 사용자가 없다면
        if (memberVO == null) {
            log.info("social member");

            String uuid = UUID.randomUUID().toString();
            String[] emailSplits = email.split("@");
            String[] uuidSplits = uuid.split("-");

            // 회원 아이디 - kakao + 이메일 앞자리 + uuid 마지막 자리
            String memberId = "kakao" + emailSplits[0] + uuidSplits[uuidSplits.length - 1];

            // 회원 추가 -- memberId는 이메일 주소 / 패스워드는 1111
            MemberVO newMember = MemberVO.builder()
                    .memberId(memberId)
                    .passwd(passwordEncoder.encode("1111"))
                    .email(email)
                    .hasCat(false)
                    .social(true)
                    .build();
            newMember.addRole(MemberRole.USER);

            // MemberDTO 구성 및 반환
            MemberDTO memberDTO = modelMapper.map(newMember, MemberDTO.class);
            memberDTO.setProps(params);

            return memberDTO;
        }
        else {
            MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);

            if (memberVO.getProfileImg() != null) { // 프로필 이미지 존재할 때
                String profileImg = memberVO.getProfileImg();
                String[] splits = profileImg.split("/"); // 날짜와 이미지 이름으로 나누기
                memberDTO.setDateString(splits[1]);
                memberDTO.setProfileImg(splits[0]);
            }

            memberDTO.setSocial(true);

            return memberDTO;
        }
    }

    private String getKakaoEmail(Map<String, Object> paramMap) {
        log.info("KAKAO-----------");

        Object value = paramMap.get("kakao_account");
        log.info(value);

        LinkedHashMap accountMap = (LinkedHashMap) value;
        String email = (String) accountMap.get("email");

        log.info("email..." + email);
        return email;
    }
}
