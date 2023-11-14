package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.BanVO;
import com.example.pink_jelly.dto.BanDTO;
import com.example.pink_jelly.mapper.BanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BanServiceImpl implements BanService{

    private final BanMapper banMapper;
    private final ModelMapper modelMapper;

    @Override
    public List<BanDTO> banList(Long mno) { // 차단 목록
        List<BanVO> banVOList = banMapper.banList(mno);
        List<BanDTO> banDTOList = new ArrayList<>();
        banVOList.forEach(banVO -> banDTOList.add(modelMapper.map(banVO, BanDTO.class)));

        return banDTOList;
    }

    @Override
    public boolean addBan(BanDTO banDTO) { // 차단 추가
        BanVO banVO = modelMapper.map(banDTO, BanVO.class);

        return banMapper.insertBan(banVO);
    }

    @Override
    public boolean removeBan(Long mno, String memberId) { // 차단해제

        return banMapper.deleteBan(mno, memberId);
    }

    @Override
    public boolean isBan(Long mno, String memberId) { // 차단 여부

        return banMapper.isBan(mno, memberId);
    }

    @Override
    public boolean banned(String memberId) { // 내가 차단당함

        return banMapper.banned(memberId);
    }
}
