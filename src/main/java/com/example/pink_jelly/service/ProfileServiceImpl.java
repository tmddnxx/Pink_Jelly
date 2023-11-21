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
        voList.forEach(mainBoardVO -> {
            MainBoardDTO mainBoardDTO = modelMapper.map(mainBoardVO, MainBoardDTO.class);
            List<String> imgs = List.of(mainBoardVO.getMainImg().split(", "));

            List<String> dateList = new ArrayList<>();
            List<String> fileList = new ArrayList<>();

            for (String img : imgs) {
                String[] parts = img.split("/");
                if (parts.length == 2) {
                    fileList.add(parts[0]);
                    dateList.add(parts[1]);
                }
            }
            //프로필 이미지 분리
            String[] profile = mainBoardVO.getProfileImg().split("/");

            mainBoardDTO.setProfileImg(profile[0]);
            mainBoardDTO.setDateString(profile[1]);
            mainBoardDTO.setMainImg(fileList);
            mainBoardDTO.setBoardDateString(dateList);
            dtoList.add(mainBoardDTO);
        });

        return dtoList;
    }

    @Override
    public List<CatsMeBoardDTO> catsMeBoardList(String memberId) { // 입양소 게시물 목록
        List<CatsMeBoardVO> voList = profileMapper.catsMeBoardList(memberId);
        List<CatsMeBoardDTO> dtoList = new ArrayList<>();
        voList.forEach(catsMeBoardVO -> {
            CatsMeBoardDTO catsMeBoardDTO = modelMapper.map(catsMeBoardVO, CatsMeBoardDTO.class);
            List<String> imgs = List.of(catsMeBoardVO.getCatsMeImg().split(", "));

            List<String> dateList = new ArrayList<>();
            List<String> fileList = new ArrayList<>();

            for(String img : imgs) {
                String[] parts = img.split("/");
                if(parts.length == 2) {
                    fileList.add(parts[0]);
                    dateList.add(parts[1]);
                }
            }
            catsMeBoardDTO.setBoardDateString(dateList);
            catsMeBoardDTO.setCatsMeImg(fileList);

            String[] profileImg = catsMeBoardVO.getProfileImg().split("/");
            catsMeBoardDTO.setProfileImg(profileImg[0]);
            catsMeBoardDTO.setDateString(profileImg[1]);

            dtoList.add(catsMeBoardDTO);
        });

        return dtoList;
    }

    @Override
    public List<CatsReviewBoardDTO> reviewBoardList(String memberId) { // 입양 후기 게시물 목록
        List<CatsReviewBoardVO> voList = profileMapper.reviewBoardList(memberId);
        List<CatsReviewBoardDTO> dtoList = new ArrayList<>();
        voList.forEach(catsReviewBoardVO -> {
            CatsReviewBoardDTO catsReviewBoardDTO = modelMapper.map(catsReviewBoardVO, CatsReviewBoardDTO.class);
            List<String> imgs = List.of(catsReviewBoardVO.getCatsMeImg().split(", "));

            List<String> dateList = new ArrayList<>();
            List<String> fileList = new ArrayList<>();

            for(String img : imgs) {
                String[] parts = img.split("/");
                if(parts.length == 2) {
                    fileList.add(parts[0]);
                    dateList.add(parts[1]);
                }
            }
            catsReviewBoardDTO.setBoardDateString(dateList);
            catsReviewBoardDTO.setCatsMeImg(fileList);

            String[] profileImg = catsReviewBoardDTO.getProfileImg().split("/");
            catsReviewBoardDTO.setProfileImg(profileImg[0]);
            catsReviewBoardDTO.setDateString(profileImg[1]);

            dtoList.add(catsReviewBoardDTO);
        });

        return dtoList;
    }

}
