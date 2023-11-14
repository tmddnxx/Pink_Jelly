package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.FriendsVO;
import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.FriendsDTO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.mapper.FriendsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class FriendsServiceImpl implements FriendsService{

    private final FriendsMapper friendsMapper;
    private final ModelMapper modelMapper;


    @Override
    public List<FriendsDTO> gmingList(Long mno) { // 친구 목록
        List<FriendsVO> gmingVOList = friendsMapper.gmingList(mno);
        List<FriendsDTO> friendsDTOList = new ArrayList<>();
        gmingVOList.forEach(friendsVO -> friendsDTOList.add(modelMapper.map(friendsVO, FriendsDTO.class)));

        return friendsDTOList;
    }

    @Override
    public boolean addGming(FriendsDTO friendsDTO) { // 친구추가
        FriendsVO friendsVO = modelMapper.map(friendsDTO, FriendsVO.class);

        friendsMapper.gmingCntUpdate(friendsVO.getMno(), true); // 내가 친구추가하면 내 mno의 gmingCnt + 1

        return friendsMapper.insertGming(friendsVO);
    }

    @Override
    public boolean removeGming(Long mno, String memberId) { // 친구 삭제

        friendsMapper.gmingCntUpdate(mno, false); // 내가 친구추가하면 내 mno의 gmingCnt - 1

        return friendsMapper.deleteGming(mno, memberId);
    }

    @Override
    public boolean isGming(Long mno, String memberId) { // 친구여부

        return friendsMapper.isFriend(mno, memberId);
    }

    @Override
    public List<MemberDTO> gmerList(String memberId) {
        List<MemberVO> memberVOList = friendsMapper.gmerList(memberId);
        List<MemberDTO> memberDTOList = new ArrayList<>();
        memberVOList.forEach(memberVO -> memberDTOList.add(modelMapper.map(memberVO, MemberDTO.class)));

        return memberDTOList;
    }

    @Override
    public void gmerCntUpdate(String memberId) {
        friendsMapper.gmerCntUpdate(memberId);
    }


}
