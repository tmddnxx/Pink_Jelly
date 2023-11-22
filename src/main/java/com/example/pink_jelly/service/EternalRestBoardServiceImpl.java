package com.example.pink_jelly.service;

import com.example.pink_jelly.domain.EternalRestBoardVO;
import com.example.pink_jelly.dto.CatsMeBoardDTO;
import com.example.pink_jelly.dto.EternalRestBoardDTO;
import com.example.pink_jelly.dto.PageRequestDTO;
import com.example.pink_jelly.dto.PageResponseDTO;
import com.example.pink_jelly.mapper.EternalRestBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class EternalRestBoardServiceImpl implements EternalRestBoardService{

    private final EternalRestBoardMapper eternalRestBoardMapper;

    private final ModelMapper modelMapper;


    @Override
    public void register(EternalRestBoardDTO eternalRestBoardDTO) { // 게시판 등록
        EternalRestBoardVO eternalRestBoardVO = modelMapper.map(eternalRestBoardDTO, EternalRestBoardVO.class);

        eternalRestBoardMapper.insert(eternalRestBoardVO);
    }

    @Override
    public PageResponseDTO<EternalRestBoardDTO> getList(PageRequestDTO pageRequestDTO) { // 게시물 목록
        pageRequestDTO.setSize(10);
        List<EternalRestBoardVO> voList = eternalRestBoardMapper.selectList(pageRequestDTO);
        List<EternalRestBoardDTO> dtoList = new ArrayList<>();
        voList.forEach(eternalRestBoardVO -> dtoList.add(modelMapper.map(eternalRestBoardVO, EternalRestBoardDTO.class)));

        int total = eternalRestBoardMapper.getCount(pageRequestDTO);

        PageResponseDTO<EternalRestBoardDTO> pageResponseDTO = PageResponseDTO.<EternalRestBoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

        return pageResponseDTO;
    }

    @Override
    public EternalRestBoardDTO getBoard(Long erbNo, Long mno) { // 게시글 상세
        boolean flag = false;
        EternalRestBoardVO eternalRestBoardVO = eternalRestBoardMapper.getOne(erbNo);
        flag = eternalRestBoardMapper.isRestSad(mno, erbNo); // 슬퍼요 눌렀는지
        EternalRestBoardDTO eternalRestBoardDTO = modelMapper.map(eternalRestBoardVO, EternalRestBoardDTO.class);
        eternalRestBoardDTO.setFlag(flag);

        return eternalRestBoardDTO;
    }

    @Override
    public void remove(Long erbNo) { // 게시글 삭제
        eternalRestBoardMapper.delete(erbNo);

    }

    @Override
    public void catInfoDel(Long mno) { // 고양이 정보 삭제
        eternalRestBoardMapper.catInfoDel(mno);
    }

    @Override
    public boolean addSad(Long mno, Long erbNo) { // 슬퍼요 추가

        boolean registerSad = eternalRestBoardMapper.insertRestSad(mno, erbNo);

        eternalRestBoardMapper.sadCntUpdate(erbNo, true);

        return registerSad;
    }

    @Override
    public boolean removeSad(Long mno, Long erbNo) { // 슬퍼요 제거
        boolean deleteSad = eternalRestBoardMapper.removeRestSad(mno, erbNo);

        eternalRestBoardMapper.sadCntUpdate(erbNo, false);
        return deleteSad;
    }

    @Override
    public boolean isRestSad(Long mno, Long erbNo) { // 슬퍼요 여부
        log.info("서비스 flag : " + eternalRestBoardMapper.isRestSad(mno,erbNo));
        return eternalRestBoardMapper.isRestSad(mno, erbNo);
    }
}
