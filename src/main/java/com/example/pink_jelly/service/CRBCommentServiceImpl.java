package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.CRBCommentVO;
import com.example.pink_jelly.dto.CRBCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.mapper.CRBCommentMapper;
import com.example.pink_jelly.mapper.CatsMeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CRBCommentServiceImpl implements CRBCommentService{
    private final CRBCommentMapper crbCommentMapper;
    private final CatsMeMapper catsMeMapper;
    private final ModelMapper modelMapper;

    @Override
    public Long register(CRBCommentDTO crbCommentDTO) {
        CRBCommentVO crbCommentVO = modelMapper.map(crbCommentDTO, CRBCommentVO.class);

        int success = crbCommentMapper.insert(crbCommentVO);

        crbCommentMapper.updateParentNo();

        if(success == 1) {
            crbCommentMapper.updateCnt(crbCommentDTO.getCrbNo());
        }
        return crbCommentVO.getComNo();
    }

    @Override
    public void remove(Long comNo, Long crbNo) {
        int success;
        if(crbCommentMapper.checkParents(comNo) > 0){
            success= crbCommentMapper.deleteAll(comNo);
        }
         else {
         success = crbCommentMapper.deleteOne(comNo);
        }
        log.info("comNo : " + comNo + "crbNo : " + crbNo);
        if( success == 1) {
            crbCommentMapper.updateCnt(crbNo);
        }
    }

    @Override
    public PageResponseDTO<CRBCommentDTO> getListCRBComment(Long crbNo, PageRequestDTO pageRequestDTO) {
        List<CRBCommentVO> crbCommentVOList = crbCommentMapper.selectList(crbNo, pageRequestDTO.getSkip(), pageRequestDTO.getSize());
        List<CRBCommentDTO> crbCommentDTOList = new ArrayList<>();
        crbCommentMapper.updateCnt(crbNo);
        crbCommentVOList.forEach(crbCommentVO -> crbCommentDTOList.add(modelMapper.map(crbCommentVO, CRBCommentDTO.class)));
        crbCommentDTOList.forEach(crbCommentDTO -> {
            String[] splits = crbCommentDTO.getProfileImg().split("/");
            String profileImg = splits[0];
            String dateString = splits[1];
            crbCommentDTO.setProfileImg(profileImg);
            crbCommentDTO.setDateString(dateString);
        });

        return PageResponseDTO.<CRBCommentDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(crbCommentDTOList)
                .total(crbCommentMapper.getCount(crbNo))
                .build();
    }

}
