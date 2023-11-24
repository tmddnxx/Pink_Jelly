package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MemberVO;
import com.example.pink_jelly.dto.AdminSearchDTO;
import com.example.pink_jelly.dto.MemberDTO;
import com.example.pink_jelly.domain.CatsMeBoardVO;
import com.example.pink_jelly.domain.CatsReviewBoardVO;
import com.example.pink_jelly.domain.MainBoardVO;
import com.example.pink_jelly.dto.*;
import com.example.pink_jelly.mapper.CatsMeMapper;
import com.example.pink_jelly.mapper.MainBoardMapper;
import com.example.pink_jelly.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminServiceImpl implements AdminService{
    private final ModelMapper modelMapper;
    private final MemberMapper memberMapper;
    private final MainBoardMapper mainBoardMapper;
    private final CatsMeMapper catsMeMapper;

    @Override
    public List<MemberDTO> adminMemberSearch(AdminSearchDTO adminSearchDTO) {
        List<MemberVO> memberVOList = memberMapper.searchAll(adminSearchDTO);
        List<MemberDTO> memberDTOList = new ArrayList<>();
        memberVOList.forEach(memberVO -> {
            MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);
            memberDTOList.add(memberDTO);
        });

        return memberDTOList;
    }

    @Override
    public void removeMember(Long mno) {
        memberMapper.deleteMember(mno);
    }

    @Override
    public PageResponseDTO<MainBoardDTO> mainList(PageRequestDTO pageRequestDTO) { // 메인 리스트
        List<MainBoardVO> voList = mainBoardMapper.selectAll(pageRequestDTO);
        List<MainBoardDTO> dtoList = new ArrayList<>();

        voList.forEach(mainBoardVO -> {
            MainBoardDTO mainBoardDTO = modelMapper.map(mainBoardVO, MainBoardDTO.class);
            List<String> imgs = List.of(mainBoardVO.getMainImg().split(", "));

            List<String> dateList = new ArrayList<>();
            List<String> fileList = new ArrayList<>();

            for(String img : imgs){
                String[] parts = img.split("/");
                if(parts.length == 2){
                    fileList.add(parts[0]);
                    dateList.add(parts[1]);
                }
            }

            mainBoardDTO.setBoardDateString(dateList);
            mainBoardDTO.setMainImg(fileList);
            dtoList.add(mainBoardDTO);
        });

        int total = mainBoardMapper.getCount(pageRequestDTO);

        PageResponseDTO<MainBoardDTO> pageResponseDTO = PageResponseDTO.<MainBoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public PageResponseDTO<CatsMeBoardDTO> catsList(PageRequestDTO pageRequestDTO) { // 캣츠미 리스트
        List<CatsMeBoardVO> voList = catsMeMapper.selectAll(pageRequestDTO);
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
            dtoList.add(catsMeBoardDTO);
        });

        int total = catsMeMapper.getCount(pageRequestDTO);

        PageResponseDTO<CatsMeBoardDTO> pageResponseDTO = PageResponseDTO.<CatsMeBoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public PageResponseDTO<CatsReviewBoardDTO> reviewList(PageRequestDTO pageRequestDTO) { // 리뷰 리스트
        List<CatsReviewBoardVO> voList = catsMeMapper.selectReviewBoardAll(pageRequestDTO);
        List<CatsReviewBoardDTO> dtoList = new ArrayList<>();
        voList.forEach(catsReviewBoardVO -> {
            CatsReviewBoardDTO catsReviewBoardDTO = modelMapper.map(catsReviewBoardVO, CatsReviewBoardDTO.class);
            List<String> imgs = List.of(catsReviewBoardVO.getCatsMeImg().split(", "));

            List<String> dateList = new ArrayList<>();
            List<String> fileList = new ArrayList<>();

            for (String img : imgs) {
                String[] parts = img.split("/");
                if (parts.length == 2) {
                    fileList.add(parts[0]);
                    dateList.add(parts[1]);
                }
            }

            catsReviewBoardDTO.setCatsMeImg(fileList);
            catsReviewBoardDTO.setBoardDateString(dateList);

            dtoList.add(catsReviewBoardDTO);
        });

        int total = catsMeMapper.getReviewBoardCount(pageRequestDTO);

        PageResponseDTO<CatsReviewBoardDTO> pageResponseDTO = PageResponseDTO.<CatsReviewBoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }
}
