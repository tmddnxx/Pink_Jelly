package com.example.pink_jelly.mapperTests;

import com.example.pink_jelly.member.vo.MemberVO;
import com.example.pink_jelly.member.mapper.MemberMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void insertTest(){
        MemberVO memberVO = MemberVO.builder()
                .memberId("TestId3")
                .passwd("1234")
                .email("test@naver.com")
                .memberName("TestName3")
                .phone("01012341234")
                .nickName("TestNick")
                .hasCat(true)
                .catAge("6")
                .catSex("남아")
                .variety("코리안숏헤어3")
                .profileImg("uuid+fileImg3")
                .build();
        memberMapper.insertMember(memberVO);
    }

    @Test
    public void delete(){
        memberMapper.deleteMember(2L);
    }

    @Test
    public void updateMemberTest(){
        MemberVO memberVO = MemberVO.builder()
                .email("updateEmail")
                .memberName("updateName")
                .phone("010-2222-2222")
                .mno(1L)
                .build();
        memberMapper.updateMember(memberVO);
    }

    @Test
    public void updatePasswdTest(){
        MemberVO memberVO = MemberVO.builder()
                .passwd("3333")
                .mno(1L)
                .build();
        memberMapper.updatePasswd(memberVO);
    }

    @Test
    public void updateMyCatTest() {
        MemberVO memberVO = MemberVO.builder()
                .hasCat(true)
                .catAge("10년")
                .catSex("여아")
                .variety("노르웨이숲")
                .profileImg("updateImg1")
                .mno(3L)
                .build();
        memberMapper.updateMyCat(memberVO);
    }

    @Test
    public void getMemberTest(){
        log.info(memberMapper.getMember(10L));
    }

    @Test
    public void loginTest(){
       log.info(memberMapper.login("test3", "3333"));
    }

    @Test
    public void exitsByIdTest() {
        boolean isMemberId = memberMapper.exitsById("admin");
        log.info(isMemberId);
    }

    @Test
    public void findByIdWithImagesTest() {
        Long mno = 3L;
        String fileName = memberMapper.findByMnoWithImg(mno);
        log.info(fileName);
    }

    @Test
    public void updateProfileImgTest() {
        Long mno = 3L;
        String profileImg = "updateImg.jpg";
        memberMapper.updateProfileImg(profileImg, mno);
    }
}
