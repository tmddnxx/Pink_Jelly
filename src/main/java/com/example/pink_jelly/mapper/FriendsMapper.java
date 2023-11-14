package com.example.pink_jelly.mapper;

import com.example.pink_jelly.domain.FriendsVO;
import com.example.pink_jelly.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Mapper
public interface FriendsMapper {
    List<FriendsVO> gmingList(Long mno); // 그루밍목록
    boolean insertGming(FriendsVO friendsVO); // 그루밍추가
    boolean deleteGming(Long mno, String memberId); // 그루밍삭제
    boolean isFriend(Long mno, String memberId); // 친구 등록 되있는지 확인 (내 mno, 상대 memberId)

    void gmingCntUpdate(Long mno, boolean flag); // 그루밍 수 업데이트 (내가 친구 추가한 사람수)


    List<MemberVO> gmerList(String memberId); // 그루머목록

    void gmerCntUpdate(String memberId); // 그루머 수 업데이트 (상대가 나를 추가한 사람수)



}
