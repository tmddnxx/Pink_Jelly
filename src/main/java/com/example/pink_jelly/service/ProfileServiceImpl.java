package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.CatsMeBoardVO;
import com.example.pink_jelly.domain.CatsReviewBoardVO;
import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.CatsMeBoardDTO;
import com.example.pink_jelly.dto.CatsReviewBoardDTO;
import com.example.pink_jelly.dto.MainBoardDTO;
import com.example.pink_jelly.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProfileServiceImpl implements ProfileService {
    private final ProfileMapper profileMapper;
    private final ModelMapper modelMapper;

    @Override
    public List<MainBoardDTO> mainBoardList(String memberId) { // 메인 게시물 목록
        List<MainBoardVO> voList = profileMapper.mainBoardList(memberId);
        List<MainBoardDTO> dtoList = new ArrayList<>();
        voList.forEach(mainBoardVO -> dtoList.add(modelMapper.map(mainBoardVO, MainBoardDTO.class)));

        return dtoList;
    }

    @Override
    public List<CatsMeBoardDTO> catsMeBoardList(String memberId) { // 입양소 게시물 목록
        List<CatsMeBoardVO> voList = profileMapper.catsMeBoardList(memberId);
        List<CatsMeBoardDTO> dtoList = new ArrayList<>();
        voList.forEach(catsMeBoardVO -> dtoList.add(modelMapper.map(catsMeBoardVO, CatsMeBoardDTO.class)));

        return dtoList;
    }

    @Override
    public List<CatsReviewBoardDTO> reviewBoardList(String memberId) { // 입양 후기 게시물 목록
        List<CatsReviewBoardVO> voList = profileMapper.reviewBoardList(memberId);
        List<CatsReviewBoardDTO> dtoList = new ArrayList<>();
        voList.forEach(catsReviewBoardVO -> dtoList.add(modelMapper.map(catsReviewBoardVO, CatsReviewBoardDTO.class)));

        return dtoList;
    }
}
