package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MainCommentVO;
import com.example.pink_jelly.dto.MainCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.mapper.MainBoardMapper;
import com.example.pink_jelly.mapper.MainCommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MainCommentServiceImpl implements MainCommentService{
    private final MainCommentMapper mainCommentMapper;
    private final MainBoardMapper mainBoardMapper;

    private final ModelMapper modelMapper;
    @Override
    public Long register(MainCommentDTO mainCommentDTO) {


        MainCommentVO mainCommentVO = modelMapper.map(mainCommentDTO, MainCommentVO.class);

        int success = mainCommentMapper.insert(mainCommentVO);

        mainCommentMapper.updateParentNo();

        if(success == 1) {
            mainBoardMapper.upCommentCnt(mainCommentDTO.getMbNo());
        }
        return mainCommentVO.getComNo();
    }

    @Override
    public void remove(Long comNo, Long mbNo) {
        int success = mainCommentMapper.deleteOne(comNo);
        log.info("comNo : " + comNo + "mbNo : " + mbNo);
        if( success == 1) {
            mainBoardMapper.downCommentCnt(mbNo);
        }

    }

    @Override
    public PageResponseDTO<MainCommentDTO> getListMainComment(Long mbNo, PageRequestDTO pageRequestDTO) {
        List<MainCommentVO> mainCommentVOList = mainCommentMapper.selectList(mbNo, pageRequestDTO.getSkip(), pageRequestDTO.getSize());
        List<MainCommentDTO> mainCommentDTOList = new ArrayList<>();

        mainCommentVOList.forEach(mainCommentVO -> {
            mainCommentDTOList.add(modelMapper.map(mainCommentVO, MainCommentDTO.class));
        });
        mainCommentDTOList.forEach(mainCommentDTO -> {
            String[] splits = mainCommentDTO.getProfileImg().split("/");
            String profileImg = splits[0];
            String dateString = splits[1];
            mainCommentDTO.setProfileImg(profileImg);
            mainCommentDTO.setDateString(dateString);
            log.info(dateString);
        });


        mainCommentDTOList.forEach(log::info);
        return PageResponseDTO.<MainCommentDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(mainCommentDTOList)
                .total(mainCommentMapper.getCount(mbNo))
                .build();
    }

    @Override
    public PageResponseDTO<MainCommentDTO> getMainCommentList(Long mbNo, PageRequestDTO pageRequestDTO) {
        // 댓글 목록 최신순
        List<MainCommentVO> mainCommentVOList = mainCommentMapper.selectListOrderByDESC(
                mbNo, pageRequestDTO.getSkip(), pageRequestDTO.getSize());
        List<MainCommentDTO> mainCommentDTOList = new ArrayList<>();

        mainCommentVOList.forEach(mainCommentVO -> {
            String profileImg = null;
            String dateString = null;
            if(mainCommentVO.getProfileImg() != null) { // 프로필 이미지가 존재하는 경우
                String[] imgSplits = mainCommentVO.getProfileImg().split("/");
                profileImg = imgSplits[0];
                dateString = imgSplits[1];
            }

            MainCommentDTO mainCommentDTO = modelMapper.map(mainCommentVO, MainCommentDTO.class);
            mainCommentDTO.setProfileImg(profileImg);
            mainCommentDTO.setDateString(dateString);

            mainCommentDTOList.add(mainCommentDTO);
        });

        return PageResponseDTO.<MainCommentDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(mainCommentDTOList)
                .total(mainCommentMapper.getCount(mbNo))
                .build();
    }
}
