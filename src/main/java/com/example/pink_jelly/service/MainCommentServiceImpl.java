package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.MainCommentVO;
import com.example.pink_jelly.dto.MainCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
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
    private final ModelMapper modelMapper;
    @Override
    public Long register(MainCommentDTO mainCommentDTO) {
        MainCommentVO mainCommentVO = modelMapper.map(mainCommentDTO, MainCommentVO.class);
        mainCommentMapper.insert(mainCommentVO);
        return mainCommentVO.getComNo();
    }

    @Override
    public void remove(Long comNo) {
        mainCommentMapper.deleteOne(comNo);
    }

    @Override
    public PageResponseDTO<MainCommentDTO> getListMainComment(Long mbNo, PageRequestDTO pageRequestDTO) {
        List<MainCommentVO> mainCommentVOList = mainCommentMapper.selectList(mbNo, pageRequestDTO.getSkip(), pageRequestDTO.getSize());
        List<MainCommentDTO> mainCommentDTOList = new ArrayList<>();

        mainCommentVOList.forEach(mainCommentVO -> mainCommentDTOList.add(modelMapper.map(mainCommentVO, MainCommentDTO.class)));

        return PageResponseDTO.<MainCommentDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(mainCommentDTOList)
                .total(mainCommentMapper.getCount(mbNo))
                .build();
    }
}
