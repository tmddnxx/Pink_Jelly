package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.CatsCommentVO;
import com.example.pink_jelly.dto.CatsCommentDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.mapper.CRBCommentMapper;
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

    private final ModelMapper modelMapper;
    private final CRBCommentMapper commentMapper;

    @Override
    public void register(CatsCommentDTO catsCommentDTO) {
        CatsCommentVO catsCommentVO = modelMapper.map(catsCommentDTO, CatsCommentVO.class);

        commentMapper.insert(catsCommentVO);
    }

    @Override
    public void remove(Long comNo) {
        commentMapper.delete(comNo);
    }

    @Override
    public PageResponseDTO<CatsCommentDTO> getListOfBoard(Long crbNo, PageRequestDTO pageRequestDTO) {
        List<CatsCommentVO> voList = commentMapper.selectList(crbNo, pageRequestDTO.getSkip(), pageRequestDTO.getSize());

        List<CatsCommentDTO> dtoList = new ArrayList<>();

        for(CatsCommentVO catsCommentVO : voList){
            dtoList.add(modelMapper.map(catsCommentVO, CatsCommentDTO.class));
        }

        int total = commentMapper.getCount(crbNo,pageRequestDTO);

        return PageResponseDTO.<CatsCommentDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
