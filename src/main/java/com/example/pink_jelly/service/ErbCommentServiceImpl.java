package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.EternalRestCommentVO;
import com.example.pink_jelly.dto.EternalRestCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.mapper.EternalRestBoardMapper;
import com.example.pink_jelly.mapper.EternalRestCommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ErbCommentServiceImpl implements ErbCommentService{
    private final EternalRestCommentMapper eternalRestCommentMapper;
    private final EternalRestBoardMapper eternalRestBoardMapper;
    private final ModelMapper modelMapper;


    @Override
    public Long register(EternalRestCommentDTO eternalRestCommentDTO) {
        EternalRestCommentVO eternalRestCommentVO = modelMapper.map(eternalRestCommentDTO, EternalRestCommentVO.class);
        int success = eternalRestCommentMapper.insert(eternalRestCommentVO);
        eternalRestCommentMapper.updateParentNo();
        if(success == 1) {
            eternalRestBoardMapper.upCommentCnt(eternalRestCommentDTO.getErbNo());
        }
        return eternalRestCommentVO.getErbNo();
    }

    @Override
    public void remove(Long comNo, Long erbNo) {
        int success;
        if(eternalRestCommentMapper.checkParents(comNo)>0){
            success = eternalRestCommentMapper.deleteAll(comNo);
        }
        else{
            success = eternalRestCommentMapper.deleteOne(comNo);
        }
        if( success == 1) {
            eternalRestBoardMapper.downCommentCnt(erbNo);
        }
    }

    @Override
    public PageResponseDTO<EternalRestCommentDTO> getListERBComment(Long erbNo, PageRequestDTO pageRequestDTO) {
        List<EternalRestCommentVO> erbCommentVOList = eternalRestCommentMapper.selectList(erbNo, pageRequestDTO.getSkip(), pageRequestDTO.getSize());
        List<EternalRestCommentDTO> erbCommentDTOList = new ArrayList<>();

        erbCommentVOList.forEach(eternalRestCommentVO -> erbCommentDTOList.add(modelMapper.map(eternalRestCommentVO, EternalRestCommentDTO.class)));
        erbCommentDTOList.forEach(eternalRestCommentDTO -> {
            String[] splits = eternalRestCommentDTO.getProfileImg().split("/");
            String profileImg = splits[0];
            String dateString = splits[1];
            eternalRestCommentDTO.setProfileImg(profileImg);
            eternalRestCommentDTO.setDateString(dateString);
        });

        return PageResponseDTO.<EternalRestCommentDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(erbCommentDTOList)
                .total(eternalRestCommentMapper.getCount(erbNo))
                .build();
    }
}
